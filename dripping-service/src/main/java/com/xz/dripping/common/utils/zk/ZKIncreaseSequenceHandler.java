package com.xz.dripping.common.utils.zk;

import com.xz.dripping.common.utils.DateUtils;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 基于zk的永久型自增节点PERSISTENT_SEQUENTIAL实现
 * 每次生成节点后会使用线程池执行删除节点任务,以减小zk的负担
 */
public class ZKIncreaseSequenceHandler extends SequenceHandler implements PooledObjectFactory<CuratorFramework> {
    private static final Logger logger = LoggerFactory.getLogger(ZKIncreaseSequenceHandler.class);
    private static ZKIncreaseSequenceHandler instance = new ZKIncreaseSequenceHandler();
    private static ExecutorService fixedThreadPool = Executors.newSingleThreadExecutor();
    private GenericObjectPool genericObjectPool;
    private static final Map<String, Queue<Long>> preNodeMap = new HashMap<>();
    private static final Set<Long> ymdNodeSet = new HashSet<>();
    private static String ZK_ADDRESS = "";//192.168.0.65
    private static String PATH = "";//sequence/p2p
    private static String SEQ = "";//seq;

    /**
     * 私有化构造方法,单例模式
     */
    private ZKIncreaseSequenceHandler() {
        GenericObjectPoolConfig config = new GenericObjectPoolConfig();
        config.setMaxTotal(4);
        genericObjectPool = new GenericObjectPool(this, config);
    }

    /**
     * 获取sequence工具对象的唯一方法
     *
     * @return
     */
    public static ZKIncreaseSequenceHandler getInstance(String zkAddress, String path, String seq) {
        ZK_ADDRESS = zkAddress;
        PATH = path;
        SEQ = seq;
        //实例化待删除Map
        for (SequenceEnum sequenceEnum : SequenceEnum.values()) {
            Queue<Long> queue = new ConcurrentLinkedQueue<>();
            preNodeMap.put(sequenceEnum.getCode(), queue);
        }

        //增加asset日期待删除节点
        Long ymd = Long.parseLong(DateUtils.format(new Date(), DateUtils.DATE_FORMAT_YYMMDD));
        ymdNodeSet.add(ymd);
        return instance;
    }

    @Override
    public long nextId(final SequenceEnum sequenceEnum) {
        String result = createNode(sequenceEnum.getCode());
        final String idstr = result.substring((PATH + "/" + sequenceEnum.getCode() + "/" + SEQ).length());
        final long id = Long.parseLong(idstr);
        final Queue<Long> preNodeQueue = preNodeMap.get(sequenceEnum.getCode());//获取对应的待删除队列
        preNodeQueue.add(id);
        //删除上一个节点
        fixedThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                Iterator<Long> iterator = preNodeQueue.iterator();
                if (iterator.hasNext()) {
                    long preNode = iterator.next();
                    if (preNode < id) {
                        final String format = "%0" + idstr.length() + "d";
                        String preIdstr = String.format(format, preNode);
                        final String prePath = PATH + "/" + sequenceEnum.getCode() + "/" + SEQ + preIdstr;
                        CuratorFramework client = null;
                        try {
                            client = (CuratorFramework) genericObjectPool.borrowObject();
                            client.delete().forPath(prePath);
                            logger.debug("删除" + prePath);
                            preNodeQueue.remove(preNode);
                        } catch (Exception e) {
                            logger.error("delete preNode error", e);
                        } finally {
                            if (client != null)
                                genericObjectPool.returnObject(client);
                        }
                    }
                }
            }
        });
        return id;
    }

    /**
     * 按日期生成
     * @return
     */
    @Override
    public long nextYmdId(final SequenceEnum sequenceEnum,final String ymd) {
        CuratorFramework client = null;
        try {
            client = (CuratorFramework) genericObjectPool.borrowObject();
            Stat stat = client.checkExists().forPath(PATH + "/" + sequenceEnum.getCode() + "/" + ymd);
            if(null == stat){//如果是新的一天，则将前一天的preNodeMap清空
                Queue<Long> queue = new ConcurrentLinkedQueue<>();
                preNodeMap.put(sequenceEnum.getCode(), queue);
            }
        } catch (Exception e) {
            logger.error("delete preNode error", e);
        }finally {
            if (client != null)
                genericObjectPool.returnObject(client);
        }

        String result = createNode(sequenceEnum.getCode() + "/" + ymd);
        final String idstr = result.substring((PATH + "/" + sequenceEnum.getCode() + "/" + ymd + "/" + SEQ).length());
        final long id = Long.parseLong(idstr);

        final Queue<Long> preNodeQueue = preNodeMap.get(sequenceEnum.getCode());//获取对应的待删除队列
        preNodeQueue.add(id);

        final long nowYmd = Long.parseLong(ymd);
        ymdNodeSet.add(nowYmd);

        //删除上一个节点
        fixedThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                CuratorFramework client = null;
                try {
                    client = (CuratorFramework) genericObjectPool.borrowObject();
                    Iterator<Long> iterator = preNodeQueue.iterator();
                    if (iterator.hasNext()) {
                        long preNode = iterator.next();
                        if (preNode < id) {
                            final String format = "%0" + idstr.length() + "d";
                            String preIdstr = String.format(format, preNode);
                            final String prePath = PATH + "/" + sequenceEnum.getCode() + "/" + ymd + "/" + SEQ + preIdstr;
                            try {
                                Stat stat = client.checkExists().forPath(prePath);
                                if(null != stat){
                                    client.delete().forPath(prePath);
                                    logger.debug("删除" + prePath);
                                    preNodeQueue.remove(preNode);
                                }

                                if(preNodeMap.containsKey(sequenceEnum.getCode() + preNode)){
                                    preNodeMap.remove(sequenceEnum.getCode() + preNode);
                                }
                            } catch (Exception e) {
                                logger.error("delete preNode error", e);
                            }
                        }
                    }

                    Iterator<Long> ymdIterator = ymdNodeSet.iterator();
                    if (ymdIterator.hasNext()) {
                        long preYmd = ymdIterator.next();
                        if (preYmd < nowYmd) {
                            try {
                                final String prePath = PATH + "/" + sequenceEnum.getCode() + "/" + preYmd;
                                Stat stat = client.checkExists().forPath(prePath);
                                if(null != stat){
                                    client.delete().deletingChildrenIfNeeded().forPath(prePath);
                                    logger.debug("删除" + prePath);
                                    ymdNodeSet.remove(preYmd);
                                }
                            } catch (Exception e) {
                                logger.error("delete ymdNode error", e);
                            }
                        }
                    }
                } catch (Exception e) {
                    logger.error("delete preNode error", e);
                } finally {
                    if (client != null)
                        genericObjectPool.returnObject(client);
                }
            }
        });

        return id;
    }

    private String createNode(String prefixName) {
        CuratorFramework client = null;
        try {
            client = (CuratorFramework) genericObjectPool.borrowObject();
            String result = client.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT_SEQUENTIAL)
                    .forPath(PATH + "/" + prefixName + "/" + SEQ, String.valueOf(0).getBytes());
            return result;
        } catch (Exception e) {
            throw new RuntimeException("create zookeeper node error", e);
        } finally {
            if (client != null)
                genericObjectPool.returnObject(client);
        }
    }

    public String getNodeId(String prefixName) {
        CuratorFramework client = null;
        try {
            client = (CuratorFramework) genericObjectPool.borrowObject();

            String prePath = PATH + "/" + SequenceEnum.ASSET_CODE.getCode() + "/" + "seq0000000017";
            Stat stat = client.checkExists().forPath(prePath);
            if(null != stat){
                int version = stat.getVersion();
                System.out.println(version);
                System.out.println(stat.getCversion());

                client.setData().forPath(prePath,"123".getBytes());

                String data = new String(client.getData().forPath(prePath));
                System.out.println(data);

                System.out.println(stat.getVersion());
                System.out.println(stat.getCversion());
//                stat.getCversion();
//                String result = client.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT_SEQUENTIAL)
//                        .forPath(PATH + "/" + prefixName + "/" + SEQ, String.valueOf(0).getBytes());
            }
            return "";
        } catch (Exception e) {
            throw new RuntimeException("create zookeeper node error", e);
        } finally {
            if (client != null)
                genericObjectPool.returnObject(client);
        }
    }

    public static void main(String[] args) throws Exception{
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        long startTime = System.currentTimeMillis();   //获取开始时间--192.168.0.65 --127.0.0.1
        final ZKIncreaseSequenceHandler sequenceHandler = ZKIncreaseSequenceHandler.getInstance("192.168.0.65", "/sequence/asset", "seq");
        CuratorFramework client = null;
        try{
            client = (CuratorFramework) sequenceHandler.genericObjectPool.borrowObject();
            client.getChildren();
            String stat = client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL_SEQUENTIAL).forPath("/sequence/asset/123");
            if(null != stat){
//                client.delete().forPath("/sequence/asset/123");
                System.out.println(stat);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            client.close();
        }

//        int count = 10;
//
//        final CountDownLatch cd = new CountDownLatch(count);
//        for (int i = 0; i < count; i++) {
//            executorService.execute(new Runnable() {
//                public void run() {
//                    System.out.printf("线程1 %s %d \n", Thread.currentThread().getId(), sequenceHandler.nextYmdId(SequenceEnum.ASSET_CODE,"20171129"));
//                    cd.countDown();
//                }
//            });
//            executorService.execute(new Runnable() {
//                public void run() {
//                    System.out.printf("线程2 %s %d \n", Thread.currentThread().getId(), sequenceHandler.nextId(SequenceEnum.ASSET_CODE));
//                    cd.countDown();
//                }
//            });
//        }
//        try {
//            cd.await();
//        } catch (InterruptedException e) {
//            logger.error("Interrupted thread", e);
//            Thread.currentThread().interrupt();
//        }
        long endTime = System.currentTimeMillis(); //获取结束时间
        System.out.println("程序运行时间： " + (endTime - startTime) + "ms");

    }

    @Override
    public PooledObject<CuratorFramework> makeObject() throws Exception {
        CuratorFramework client = CuratorFrameworkFactory.newClient(ZK_ADDRESS, new ExponentialBackoffRetry(1000, 3));
        client.start();
        return new DefaultPooledObject<>(client);
    }

    @Override
    public void destroyObject(PooledObject<CuratorFramework> p) throws Exception {

    }

    @Override
    public boolean validateObject(PooledObject<CuratorFramework> p) {
        return false;
    }

    @Override
    public void activateObject(PooledObject<CuratorFramework> p) throws Exception {

    }

    @Override
    public void passivateObject(PooledObject<CuratorFramework> p) throws Exception {

    }
}

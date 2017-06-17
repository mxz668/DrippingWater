package com.xz.dripping.common.utils;

/**
 * Created by MABIAO on 2017/6/17 0017.
 */
import com.alibaba.fastjson.JSON;
import com.aliyun.oss.*;
import com.aliyun.oss.model.*;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.List;

/**
 * ClassName: OssUploadUtil <br/>
 * Function: Oss上传工具. <br/>
 * Date: 2016年12月15日 下午6:34:55 <br/>
 *
 * @author mabiao@zillionfortune.com
 * @since JDK 1.7
 */
public class OssFileUtil {

    protected static Logger LOGGER = (Logger) LoggerFactory.getLogger(OssFileUtil.class);

    /**
     * 阿里云ACCESS_ID
     */
    public static final String ACCESS_KEY_ID = "LTAIHCgkz0SQn4c9";

    /**
     * 阿里云ACCESS_KEY
     */
    public static final String ACCESS_KEY_SECRET = "G8I2BpLp9iC5Wx0J6nlmrvkjacqAKt";

    /**
     * 阿里云OSS_ENDPOINT Url
     */
    public static final String OSS_ENDPOINT = "http://oss-cn-shanghai.aliyuncs.com";

    /**
     * 阿里云BUCKET_NAME OSS
     */
    public static final String BUCKET_NAME = "bucket-zb";

    /**
     * 是否是调试模式
     */
    public static final boolean IS_DEBUG_MODE = false;

    /**
     * 调试模式文件存储目录路径
     */
    public static final String DEBUG_FILE_STORAGE_PATH = "";

    private static OSSClient client = new OSSClient(OSS_ENDPOINT, ACCESS_KEY_ID, ACCESS_KEY_SECRET);


    /**
     * 创建bucket
     */
    public static void createBucket() {
        if (!IS_DEBUG_MODE) {
            LOGGER.info("OssUploadUtil.createBucket.OSSClient:"
                    + JSON.toJSONString(client)
                    + "/n OssUploadUtil.createBucket.bucketName:" + BUCKET_NAME);
            try {
                client.createBucket(BUCKET_NAME);
            } catch (ServiceException e) {
                if (!OSSErrorCode.BUCKET_ALREADY_EXISTS.equals(e.getErrorCode())) {
                    throw e;
                }
            }
        } else {
            LOGGER.info("使用DEBUG_MODE,跳过创建Bucket");
        }
    }

    /**
     * 上传文件
     *
     * @param input    文件流
     * @param fileName 本地文件名
     */
    public static String uploadFile(InputStream input, String fileName) {
        if (!IS_DEBUG_MODE) {
            ObjectMetadata objectMeta = new ObjectMetadata();//元数据对象
            try {
                objectMeta.setContentLength(Long.parseLong(input.available() + ""));//设置传输文件长度
                objectMeta.setContentType(readFileType(fileName.substring(fileName.lastIndexOf("."))));//设置文件类型
            } catch (NumberFormatException e) {
                LOGGER.info(e.getMessage(), e);
            } catch (IOException e) {
                LOGGER.info(e.getMessage(), e);
            }
            try {
                PutObjectResult result = client.putObject(BUCKET_NAME, fileName, input, objectMeta);
                //上传成功返回结果
                if (null != result) {
                    return result.getETag();//上传成功
                }
            } catch (Exception e) {
                LOGGER.info(e.getMessage(), e);
            }
        } else {
            LOGGER.info("使用DEBUG_MODE,跳过上传文件");
        }
        return "";
    }

    /**
     * 追加文件/或创建可追加文件
     *
     * @param content  追加内容
     *                 content为要追加的内容,一定要有换行符号
     *                 例: 1|Tom|2017031222|300.00\n2|Jack|2017031222|400.00\n3|Tony|2017031222|5000.00\n
     * @param fileName 文件名
     * @return
     */
    public static void appendFile(String content, String fileName) {
        if (IS_DEBUG_MODE) {
            appendLocalFile(content, fileName);
        } else {
            appendOssFile(content, fileName);
        }
    }

    /**
     * 追加文件/或创建可追加文件
     *
     * @param content  追加内容
     *                 content为要追加的内容,一定要有换行符号
     *                 例: 1|Tom|2017031222|300.00\n2|Jack|2017031222|400.00\n3|Tony|2017031222|5000.00\n
     * @param fileName 文件名
     * @return
     */
    public static void appendOssFile(String content, String fileName) {
        AppendObjectRequest appendObjectRequest = new AppendObjectRequest(BUCKET_NAME, fileName, new ByteArrayInputStream(content.getBytes()));
        if (!isInternalExist(fileName)) {
            //文件不存在则创建可追加文件
            appendObjectRequest.setPosition(0L);
            client.appendObject(appendObjectRequest);
        } else {
            //文件已存在
            SimplifiedObjectMeta objectMeta = client.getSimplifiedObjectMeta(BUCKET_NAME, fileName);
            long size = objectMeta.getSize();//文件大小
            appendObjectRequest.setPosition(size);
            client.appendObject(appendObjectRequest);
        }
    }

    /**
     * 追加文件/或创建可追加文件
     *
     * @param content  追加内容
     *                 content为要追加的内容,一定要有换行符号
     *                 例: 1|Tom|2017031222|300.00\n2|Jack|2017031222|400.00\n3|Tony|2017031222|5000.00\n
     * @param fileName 文件名
     * @return
     */
    public static void appendLocalFile(String content, String fileName) {
        LOGGER.info("使用DEBUG_MODE追加文件");
        FileWriter writer = null;
        try {
            String fileFullName = FilenameUtils.concat(DEBUG_FILE_STORAGE_PATH, fileName);
            //打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
            writer = new FileWriter(fileFullName, true);
            writer.write(content);
        } catch (IOException e) {
            LOGGER.error("追加写入文件异常", e);
        } finally {
            IOUtils.closeQuietly(writer);
        }
    }

    /**
     * 删除一个Bucket和其中的Objects
     *
     * @param client OSSClient对象
     * @throws OSSException
     * @throws ClientException
     */
    public static void deleteBucket(OSSClient client) {
        if (!IS_DEBUG_MODE) {
            ObjectListing ObjectListing = client.listObjects(BUCKET_NAME);
            List<OSSObjectSummary> listDeletes = ObjectListing.getObjectSummaries();
            for (int i = 0; i < listDeletes.size(); i++) {
                String objectName = listDeletes.get(i).getKey();
                // 如果不为空，先删除bucket下的文件
                client.deleteObject(BUCKET_NAME, objectName);
                LOGGER.info(" delete file " + objectName);
            }
            client.deleteBucket(BUCKET_NAME);
        } else {
            LOGGER.info("使用DEBUG_MODE,跳过删除Bucket");
        }
    }

    /**
     * 下载文件
     *
     * @param fileName 上传到OSS起的名
     * @param savePath 文件下载到本地保存的路径
     * @throws FileNotFoundException
     */

    public static void downloadFile(String fileName, String savePath)
            throws FileNotFoundException {
        if (!IS_DEBUG_MODE) {
            // 获取Object，返回结果为OSSObject对象
            OSSObject object = client.getObject(BUCKET_NAME, fileName);
            // 获取Object的输入流
            InputStream objectContent = object.getObjectContent();
            // 输出流
            FileOutputStream out = new FileOutputStream(savePath);
            int BUFFER_SIZE = 8096;// 缓冲大小
            byte[] buf = new byte[BUFFER_SIZE];
            int size = 0;
            try {
                while ((size = objectContent.read(buf)) != -1) {
                    out.write(buf, 0, size);
                }
                objectContent.close();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    objectContent.close();
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            LOGGER.info("使用DEBUG_MODE,跳过下载文件");
        }
    }

    /**
     * readFileStream:读取文件流. <br/>
     *
     * @param fileName
     * @return
     */
    public static InputStream readFileStream(String fileName) {
        if (IS_DEBUG_MODE) {
            return readLocalFileStream(fileName);
        } else {
            return readOssFileStream(fileName);
        }
    }

    private static InputStream readLocalFileStream(String fileName) {
        LOGGER.info("使用DEBUG_MODE读取文件");
        try {
            String fileFullName = FilenameUtils.concat(DEBUG_FILE_STORAGE_PATH, fileName);
            LOGGER.info("读取本地文件:" + fileFullName);
            File file = new File(fileFullName);
            if (file.exists()) {
                FileInputStream is = new FileInputStream(file);
                return is;
            } else {
                LOGGER.info("本地文件不存在");
            }
        } catch (Exception e) {
            LOGGER.info("读取本地文件失败", e);
        }
        return null;
    }

    private static InputStream readOssFileStream(String fileName) {
        LOGGER.info("OssFileUtil.readFileStream.bucketName:" + BUCKET_NAME +
                ";OssFileUtil.readFileStream.FileName:" + fileName);

        // 获取Object，返回结果为OSSObject对象
        OSSObject object = null;
        try {
            object = client.getObject(BUCKET_NAME, fileName);
            if (null == object) {
                LOGGER.info("OssFileUtil.readFileStream.object:没有找到相应文件!");
                LOGGER.info("OssFileUtil.readFileStream.object:" + JSON.toJSONString(object));
                return null;
            }
        } catch (Exception e) {
            LOGGER.info(e.getMessage(), e);
        }
        return object.getObjectContent();
    }

    /**
     * 返回http头
     *
     * @param fileType
     * @return
     */
    public static String readFileType(String fileType) {
        if (fileType.toLowerCase().equals("bmp")) {
            return "image/bmp";
        }
        if (fileType.toLowerCase().equals("gif")) {
            return "image/gif";
        }
        if (fileType.toLowerCase().equals("jpeg")
                || fileType.toLowerCase().equals("jpg")
                || fileType.toLowerCase().equals("png")) {
            return "image/jpeg";
        }
        if (fileType.toLowerCase().equals("html")) {
            return "text/html";
        }
        if (fileType.toLowerCase().equals("txt")) {
            return "text/plain";
        }
        if (fileType.toLowerCase().equals("vsd")) {
            return "application/vnd.visio";
        }
        if (fileType.toLowerCase().equals("pptx")
                || fileType.toLowerCase().equals("ppt")) {
            return "application/vnd.ms-powerpoint";
        }
        if (fileType.toLowerCase().equals("xlsx")
                || fileType.toLowerCase().equals("xls")) {
            return "application/vnd.ms-excel";
        }
        if (fileType.toLowerCase().equals("docx")
                || fileType.toLowerCase().equals("doc")) {
            return "application/msword";
        }
        if (fileType.toLowerCase().equals("xml")) {
            return "text/xml";
        }
        return "text/html";
    }


    /**
     * isExist:判断文件是否存在. <br/>
     *
     * @param fileName
     * @return
     */
    public static boolean isExist(String fileName) {
        if (IS_DEBUG_MODE) {
            LOGGER.info("使用DEBUG_MODE,固定返回true");
            return true;
        } else {
            LOGGER.info("未使用DEBUG_MODE,读取OSS文件");
            return client.doesObjectExist(BUCKET_NAME, fileName);
        }
    }

    private static boolean isInternalExist(String fileName) {
        if (IS_DEBUG_MODE) {
            LOGGER.info("使用DEBUG_MODE");
            String fileFullName = FilenameUtils.concat(DEBUG_FILE_STORAGE_PATH, fileName);
            File file = new File(fileFullName);
            return file.exists();
        } else {
            return client.doesObjectExist(BUCKET_NAME, fileName);
        }
    }
}
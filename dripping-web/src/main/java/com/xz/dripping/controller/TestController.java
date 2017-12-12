package com.xz.dripping.controller;

import com.xz.dripping.facade.dto.req.vue.SendVueRequest;
import com.xz.dripping.facade.service.TestFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by MABIAO on 2017/6/2 0002.
 */
@RestController
@RequestMapping("testController")
public class TestController {

    Logger logger = LoggerFactory.getLogger(TestController.class);

    @Autowired
    private TestFacade testFacade;

    @RequestMapping(value = "saveTest",method = RequestMethod.POST)
    public String saveTest(@RequestBody SendVueRequest request){
        try {
//            testFacade.saveTest();
            System.out.println("Vue 请求");
            System.out.println("PoolCode:" + request.getPoolCode());
            System.out.println("ProductCode:" + request.getProductCode());

            return "{\"Amount_of_corporate_loan\":\"800000\",\"Basic_assets_are_briefly_introduced\":\"wqeqwe\",\"Capital_use_business\":\"资金周转\",\"Capital_use_personal\":\"123213\",\"Date_of_board_meeting\":\"2017-12-01\",\"Desensitization_Legal_person\":\"熊**\",\"Desensitization_certificate_number\":\"123\",\"Desensitization_loan_company_name\":\"上海YSSY化工有限公司\",\"Desensitization_registered_address\":\"中国（上海）自由贸易试验区**路**号**幢**层**室\",\"Despicable_borrower_name\":\"2323\",\"Established\":\"2017年7月16日\",\"Increased_Credit_Company\":\"卖方企业承担无条件回购\",\"Introduction_of_underlying_assets\":\"rrqw\",\"Item_Number\":\"P6901-1\",\"Main_business\":\"石油制品（除专控）、燃料油（除危险品）、润滑油、化工原料及产品（除危险化学品、监控化学品、烟花爆竹、民用爆炸物、易制毒化学品）、电子产品及配件、服装服饰及辅料、工艺品（除文物）、塑料制品、日用百货、鞋帽、建筑材料、汽车配件、五金交电、机械设备、工艺美术品（除文物）、金属材料、仪器仪表的销售，从事货物及技术的进出口业务。 【依法须经批准的项目，经相关部门批准后方可开展经营活动】\",\"Name_of_the_underlying_company\":\"333\",\"On_the_date\":\"2017-12-01\",\"Personal_borrowing_amount\":\"10000\",\"Product_specification_contract_number\":\"233\",\"Registered_capital\":\"11000\",\"Repayment_source_company\":\"第一还款来源：应收账款回款；       第二还款来源：卖方企业经营性收入\",\"Scale_of_operation\":\"150\",\"The_balance_of_accumulated_capital_gains_of_the_company\":\"1333\",\"The_number_of_underlying_assets\":\"3\",\"The_product_code_of_qiaojin\":\"1\",\"Transfer_of_the_total_amount_of_the_transfer_of_capital\":\"123\",\"Underlying_assets\":\"22222\",\"asset_introduction\":\"该项目由***公司，主要经营了大宗性**业务背景雄厚，用于建设什么什么……\",\"contract_asset_code\":\"test111611\",\"item_type\":\"企业\",\"personCredit\":\"123123\"}";
        }catch (Exception e){
            logger.info("创建失败");
            return "false";
        }
    }

    public static void main(String args[]){
//        String yearMonth = DateUtils.format(DateUtils.now(), DateUtils.DATE_FORMAT_YYMMDD);
//        Date d = DateUtils.parse("20170920", DateUtils.DATE_FORMAT_YYMMDD);\
//        String string = "AXD";
//        System.out.println(String.format("%0" + 5 + "d",2000000l));

        BigDecimal b = new BigDecimal("127867564.12");
        System.out.println(b.setScale(0,BigDecimal.ROUND_DOWN));
    }
}

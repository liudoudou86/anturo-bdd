package com.qa.data;

/**
 * @author Tesla Liu
 * @date 2023/01/31 16:59
 * 描述
 */
public class WsdlData {

    /**
     *  【发货&&退货正向BLN】
     * */
    public static String forwardBln(String deliverNo, String orderItem, String type, String data, String count, String amount, String blnNumber) {
        String soapXml = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ser=\"http://www.baidu.com/\">\n" +
                "   <soapenv:Header/>\n" +
                "   <soapenv:Body>\n" +
                "      <ser:acceptERPBillingInfo>\n" +
                "         <arg0>\n" +
                "            <AUBEL>"+deliverNo+"</AUBEL>  <!--ERP订单号-->\n" +
                "            <AUPOS>"+orderItem+"</AUPOS>  <!--订单行号-->\n" +
                "            <FKART>"+type+"</FKART>  <!--SAP开票类型，ZF2正常开票凭证，ZS1冲销凭证，ZRE退货正常开票凭证，ZS2退货冲销凭证，ZG2天士力贷项凭证发票-->\n" +
                "            <FKDAT>"+data+"</FKDAT>  <!--出具发票日期-->\n" +
                "            <FKIMG>"+count+"</FKIMG>  <!--开票数量-->\n" +
                "            <NETWR>"+amount+"</NETWR>  <!--含税净值-->\n" +
                "            <POSNR>"+orderItem+"</POSNR>  <!--出具发票项目-->\n" +
                "        <!--<SFAKN>?</SFAKN>-->  <!--冲销BLN没有的额话要删掉:-->\n" +
                "            <VBELN>"+blnNumber+"</VBELN>  <!--Optional:BLN号-->\n" +
                "         </arg0>\n" +
                "      </ser:acceptERPBillingInfo>\n" +
                "   </soapenv:Body>\n" +
                "</soapenv:Envelope>";
        return soapXml;
    }

    /**
     *  【发货&&退货冲销BLN】
     * */
    public static String reverseBln(String deliverNo, String orderItem, String type, String data, String count, String amount, String writeOffBlnNumber, String blnNumber) {
        String soapXml = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ser=\"http://www.baidu.com/\">\n" +
                "   <soapenv:Header/>\n" +
                "   <soapenv:Body>\n" +
                "      <ser:acceptERPBillingInfo>\n" +
                "         <arg0>\n" +
                "            <AUBEL>"+deliverNo+"</AUBEL>  <!--ERP订单号-->\n" +
                "            <AUPOS>"+orderItem+"</AUPOS>  <!--订单行号-->\n" +
                "            <FKART>"+type+"</FKART>  <!--SAP开票类型，ZF2正常开票凭证，ZS1冲销凭证，ZRE退货正常开票凭证，ZS2退货冲销凭证，ZG2天士力贷项凭证发票-->\n" +
                "            <FKDAT>"+data+"</FKDAT>  <!--出具发票日期-->\n" +
                "            <FKIMG>"+count+"</FKIMG>  <!--开票数量-->\n" +
                "            <NETWR>"+amount+"</NETWR>  <!--含税净值-->\n" +
                "            <POSNR>"+orderItem+"</POSNR>  <!--出具发票项目-->\n" +
                "            <SFAKN>"+writeOffBlnNumber+"</SFAKN>  <!--冲销BLN没有的额话要删掉:-->\n" +
                "            <VBELN>"+blnNumber+"</VBELN>  <!--Optional:BLN号-->\n" +
                "         </arg0>\n" +
                "      </ser:acceptERPBillingInfo>\n" +
                "   </soapenv:Body>\n" +
                "</soapenv:Envelope>";
        return soapXml;
    }

    /**
     *  【推送金税】
     * */
    public static String goldenTax(String eccId, String date, String number, String ticket, String item, String count, String bln) {
        String soapxml = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ser=\"http://www.baidu.com/\">\n" +
                "    <soapenv:Header/>\n" +
                "    <soapenv:Body>\n" +
                "        <ser:acceptErpZtaxReturnInfo>\n" +
                "            <!--Optional:-->\n" +
                "            <arg0>\n" +
                "                <!--Optional:-->\n" +
                "                <erpZtaxReturnInfoListDTO>\n" +
                "                    <!--Zero or more repetitions:-->\n" +
                "                    <erpZtaxReturnInfoDTOS>\n" +
                "                        <!--Optional:-->\n" +
                "                        <ECC_ID>"+eccId+"</ECC_ID>\n" +
                "                        <!--填写eccid 不修改 -->\n" +
                "                        <ERDAT>"+date+"</ERDAT>\n" +
                "                        <!--日期  改成当天-->\n" +
                "                        <ERNAM>823747</ERNAM>\n" +
                "                        <!--操作人 不修改-->\n" +
                "                        <ERZET>094319</ERZET>\n" +
                "                        <!--Optional: 不修改 -->\n" +
                "                        <FERDAT>"+date+"</FERDAT>\n" +
                "                        <!--日期 改成当天-->\n" +
                "                        <FERZET>094319</FERZET>\n" +
                "                        <!--Optional:-->\n" +
                "                        <FKIMG>"+number+"</FKIMG>\n" +
                "                        <!--数量 需改 对应FKIMG-->\n" +
                "                        <INVOICE>"+ticket+"</INVOICE>\n" +
                "                        <!--金税票号  需改 不重复就行-->\n" +
                "                        <INV_ITM_NO>"+item+"</INV_ITM_NO>\n" +
                "                        <!--金税行项目 10 20 多行时需要修改:-->\n" +
                "                        <NETWR>"+count+"</NETWR>\n" +
                "                        <!--行项目总金额   需改 对应NETWR-->\n" +
                "                        <POSNR>"+item+"</POSNR>\n" +
                "                        <!--billing行项目号  需改 10 20 30  对应POSNR-->\n" +
                "                        <POSNV>"+item+"</POSNV>\n" +
                "                        <!--订单行项目号 需改 需准确修改 10 20 对应AUPOS-->\n" +
                "                        <RESULT_MESSAGE>?</RESULT_MESSAGE>\n" +
                "                        <!--Optional:-->\n" +
                "                        <RSCODE>?</RSCODE>\n" +
                "                        <!--Optional:-->\n" +
                "                        <SIGN>01</SIGN>\n" +
                "                        <!--默认01 不需修改-->\n" +
                "                        <VBELN>"+bln+"</VBELN>\n" +
                "                        <!--billing号 对应VBELN-->\n" +
                "                        <VBELV>"+eccId+"</VBELV>\n" +
                "                        <!--eccid 对应订单的eccid:-->\n" +
                "                        <ZCURRENTID>?</ZCURRENTID>\n" +
                "                        <!--ZEFLAG 是否作废lzuofei    0shengxiao:-->\n" +
                "                        <ZEFLAG>0</ZEFLAG>\n" +
                "                    </erpZtaxReturnInfoDTOS>\n" +
                "                </erpZtaxReturnInfoListDTO>\n" +
                "            </arg0>\n" +
                "        </ser:acceptErpZtaxReturnInfo>\n" +
                "    </soapenv:Body>\n" +
                "</soapenv:Envelope>";
        return soapxml;
    }

}

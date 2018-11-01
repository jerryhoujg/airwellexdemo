package com.airwellex.demo.bank;

import com.airwellex.demo.BaseTestCase;
import com.tngtech.java.junit.dataprovider.DataProvider;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;
import java.util.Map;

/**
 * Created by houjiagang on 2018/10/30.
 */
public class BankTest extends BaseTestCase {

    /**
     * 正常场景枚举
     *
     * @param paymentMethod
     * @param bankCountryCode
     * @param accountName
     * @param accountNumber
     * @param bsb
     * @param aba
     */
    @Test
    @DataProvider({
            "LOCAL, US, 钢铁, 1234567890, , 123456789, ",
            "LOCAL, US, 钢铁侠, 1, , 123456789, ",
            "LOCAL, US, 钢铁aa侠钢铁侠侠侠, 12345678901234567, , 123456789, ",
            "LOCAL, AU, 钢铁, 123456, asdfgh , , ",
            "LOCAL, AU, 钢铁acv%^18, 123456, asdfgh , , ",
            "LOCAL, AU, 钢铁!@#$%^&*, <>?:{}+_^, @$fghu , , ",
            "LOCAL, CN, gt, asdfzxcv,  , , ",
            "LOCAL, CN, gangtiexia, qwertyuiopasdfghjklm,  , , ",
            "SWIFT, US, 钢铁, 1234567890, , 123456789, KDLSUS12",
            "SWIFT, US, 钢铁侠, 1, , 123456789, <>)(US12",
            "SWIFT, US, 钢铁aa侠钢铁侠侠侠, 12345678901234567, , 123456789, kdj&US23_)*",
            "SWIFT, AU, 钢铁, 123456, asdfgh , , kdj&AU23_)*",
            "SWIFT, AU, 钢铁acv%^18, 123456, asdfgh , , !@#$AU23_*",
            "SWIFT, AU, 钢铁!@#$%^&*, <>?:{}+_^, @$fghu , ,!@#$AU2_*",
            "SWIFT, CN, gt, asdfzxcv,  , , asdgCN12",
            "SWIFT, CN, gangtiexia, qwertyuiopasdfghjklm, , , adfgCN12",

    })
    public void testBank_success(String paymentMethod, String bankCountryCode,
                                 String accountName, String accountNumber,
                                 String bsb, String aba, String swiftCode) {
        //构造数据
        BankRequest request = new BankRequest();
        request.setPayment_method(paymentMethod);
        request.setBank_country_code(bankCountryCode);
        request.setAccount_name(accountName);
        request.setAccount_number(accountNumber);
        request.setBsb(bsb);
        request.setAba(aba);
        request.setSwift_code(swiftCode);
        //调接口
        Response results = target("/bank")
                .request()
                .post(Entity.entity(request, CONTENT_TYPE));
        //断言
        Map<String, String> returnBody = results.readEntity(Map.class);
        assertThat(results.getStatus()).isEqualTo(200);
        assertThat(returnBody.get("success")).contains("Bank details saved");

    }

    /**
     * payment_method 异常
     *
     * @param paymentMethod
     * @param bankCountryCode
     * @param accountName
     * @param accountNumber
     * @param bsb
     * @param aba
     */
    @Test
    @DataProvider({
            " , US, 钢铁, 1234567890, , 123456789, ",
            "L, AU, 钢铁, 123456, asdfgh , , ",
            "LOCALL, CN, gangtiexia, qwertyuiopasdfghjklm,  , , ",
            "S, US, 钢铁, 1234567890, , 123456789, KDLSUS12",
            "SWIFTTTTTTTTT, US, 钢铁侠, 1, , 123456789, <>)(US12",
    })
    public void testBank_failed01(String paymentMethod, String bankCountryCode,
                                  String accountName, String accountNumber,
                                  String bsb, String aba, String swiftCode) {
        //构造数据
        BankRequest request = new BankRequest();
        request.setPayment_method(paymentMethod);
        request.setBank_country_code(bankCountryCode);
        request.setAccount_name(accountName);
        request.setAccount_number(accountNumber);
        request.setBsb(bsb);
        request.setAba(aba);
        request.setSwift_code(swiftCode);
        //调接口
        Response results = target("/bank")
                .request()
                .post(Entity.entity(request, CONTENT_TYPE));
        //断言
        Map<String, String> returnBody = results.readEntity(Map.class);
        assertThat(results.getStatus()).isEqualTo(400);
        assertThat(returnBody.get("error")).contains("either 'LOCAL' or 'SWIFT'");

    }

    /**
     * bank_country_code 异常
     *
     * @param paymentMethod
     * @param bankCountryCode
     * @param accountName
     * @param accountNumber
     * @param bsb
     * @param aba
     */
    @Test
    @DataProvider({
            "LOCAL, US1, 钢铁, 1234567890, , 123456789, ",
            "LOCAL, , 钢铁, 123456, asdfgh , , ",
            "LOCAL, CNdjdjjdjdjdjj1123@@@##, gangtiexia, qwertyuiopasdfghjklm,  , , ",
            "SWIFT, U, 钢铁, 1234567890, , 123456789, KDLSUS12",
            "SWIFT, 12, 钢铁侠, 1, , 123456789, <>)(US12",
    })
    public void testBank_failed02(String paymentMethod, String bankCountryCode,
                                  String accountName, String accountNumber,
                                  String bsb, String aba, String swiftCode) {
        //构造数据
        BankRequest request = new BankRequest();
        request.setPayment_method(paymentMethod);
        request.setBank_country_code(bankCountryCode);
        request.setAccount_name(accountName);
        request.setAccount_number(accountNumber);
        request.setBsb(bsb);
        request.setAba(aba);
        request.setSwift_code(swiftCode);
        //调接口
        Response results = target("/bank")
                .request()
                .post(Entity.entity(request, CONTENT_TYPE));
        //断言
        Map<String, String> returnBody = results.readEntity(Map.class);
        assertThat(results.getStatus()).isEqualTo(400);
        assertThat(returnBody.get("error")).contains("one of 'US', 'AU', or 'CN'");

    }

    /**
     * account_name 异常
     *
     * @param paymentMethod
     * @param bankCountryCode
     * @param accountName
     * @param accountNumber
     * @param bsb
     * @param aba
     */
    @Test
    @DataProvider({
            "LOCAL, US, 钢, 1234567890, , 123456789, ",
//            "LOCAL, AU, , 123456, asdfgh , , ",
            "LOCAL, CN, gangtiexiaAAAAAA, qwertyuiopasdfghjklm,  , , ",
            "SWIFT, AU, 钢铁!@#$%^&*()_+=-, 1234567890, , 123456789, KDLSUS12",
            "SWIFT, US, 钢铁侠NCNCNNCNCNCNCNCNNCNCCN, 1, , 123456789, <>)(US12",
    })
    public void testBank_failed03(String paymentMethod, String bankCountryCode,
                                  String accountName, String accountNumber,
                                  String bsb, String aba, String swiftCode) {
        //构造数据
        BankRequest request = new BankRequest();
        request.setPayment_method(paymentMethod);
        request.setBank_country_code(bankCountryCode);
        request.setAccount_name(accountName);
        request.setAccount_number(accountNumber);
        request.setBsb(bsb);
        request.setAba(aba);
        request.setSwift_code(swiftCode);
        //调接口
        Response results = target("/bank")
                .request()
                .post(Entity.entity(request, CONTENT_TYPE));
        //断言
        Map<String, String> returnBody = results.readEntity(Map.class);
        assertThat(results.getStatus()).isEqualTo(400);
        assertThat(returnBody.get("error")).contains("between 2 and 10");

    }

    /**
     * account number 异常  - US
     *
     * @param paymentMethod
     * @param bankCountryCode
     * @param accountName
     * @param accountNumber
     * @param bsb
     * @param aba
     */
    @Test
    @DataProvider({
            "LOCAL, US, 钢铁侠, 1, , 1, ",
            "LOCAL, US, 钢铁aa侠钢铁侠侠侠, 1234567890@#$%^&*(, , 123456789, ",
    })
    public void testBank_failed04(String paymentMethod, String bankCountryCode,
                                  String accountName, String accountNumber,
                                  String bsb, String aba, String swiftCode) {
        //构造数据
        BankRequest request = new BankRequest();
        request.setPayment_method(paymentMethod);
        request.setBank_country_code(bankCountryCode);
        request.setAccount_name(accountName);
        request.setAccount_number(accountNumber);
        request.setBsb(bsb);
        request.setAba(aba);
        request.setSwift_code(swiftCode);
        //调接口
        Response results = target("/bank")
                .request()
                .post(Entity.entity(request, CONTENT_TYPE));
        //断言
        Map<String, String> returnBody = results.readEntity(Map.class);
        assertThat(results.getStatus()).isEqualTo(400);
        assertThat(returnBody.get("error")).contains("account_number should be between 7 and 11", "bank_country_code is 'US'");

    }

    /**
     * account number 异常  - AU
     *
     * @param paymentMethod
     * @param bankCountryCode
     * @param accountName
     * @param accountNumber
     * @param bsb
     * @param aba
     */
    @Test
    @DataProvider({
            "SWIFT, AU, 钢铁acv%^18, 12345, asdfgh , , !@#$AU23_*",
            "SWIFT, AU, 钢铁!@#$%^&*, <>?:{}+_^@**@*@*（@@*, @$fghu , ,!@#$AU2_*",
    })
    public void testBank_failed05(String paymentMethod, String bankCountryCode,
                                  String accountName, String accountNumber,
                                  String bsb, String aba, String swiftCode) {
        //构造数据
        BankRequest request = new BankRequest();
        request.setPayment_method(paymentMethod);
        request.setBank_country_code(bankCountryCode);
        request.setAccount_name(accountName);
        request.setAccount_number(accountNumber);
        request.setBsb(bsb);
        request.setAba(aba);
        request.setSwift_code(swiftCode);
        //调接口
        Response results = target("/bank")
                .request()
                .post(Entity.entity(request, CONTENT_TYPE));
        //断言
        Map<String, String> returnBody = results.readEntity(Map.class);
        assertThat(results.getStatus()).isEqualTo(400);
        assertThat(returnBody.get("error")).contains("account_number should be between 6 and 9", "bank_country_code is 'AU'");

    }


    /**
     * account number 异常  - CN
     *
     * @param paymentMethod
     * @param bankCountryCode
     * @param accountName
     * @param accountNumber
     * @param bsb
     * @param aba
     */
    @Test
    @DataProvider({
            "SWIFT, CN, gt, sdfzxcv,  , , asdgCN12",
            "SWIFT, CN, gangtiexia, qwertyuiopasdfghjkl（（（（（（m, , , adfgCN12",

    })
    public void testBank_failed06(String paymentMethod, String bankCountryCode,
                                  String accountName, String accountNumber,
                                  String bsb, String aba, String swiftCode) {
        //构造数据
        BankRequest request = new BankRequest();
        request.setPayment_method(paymentMethod);
        request.setBank_country_code(bankCountryCode);
        request.setAccount_name(accountName);
        request.setAccount_number(accountNumber);
        request.setBsb(bsb);
        request.setAba(aba);
        request.setSwift_code(swiftCode);
        //调接口
        Response results = target("/bank")
                .request()
                .post(Entity.entity(request, CONTENT_TYPE));
        //断言
        Map<String, String> returnBody = results.readEntity(Map.class);
        assertThat(results.getStatus()).isEqualTo(400);
        assertThat(returnBody.get("error")).contains("account_number should be between 8 and 20", "bank_country_code is 'CN'");

    }

    /**
     * swift_code wrong
     *
     * @param paymentMethod
     * @param bankCountryCode
     * @param accountName
     * @param accountNumber
     * @param bsb
     * @param aba
     */
    @Test
    @DataProvider({
//            "SWIFT, US, 钢铁, 1234567890, , 123456789, ",
            "SWIFT, US, 钢铁侠, 1, , 123456789, <>)(sk12",
            "SWIFT, AU, 钢铁, 123456, asdfgh , , kdj&AIU23_",
            "SWIFT, CN, gt, asdfzxcv,  , , asdgC121",

    })
    public void testBank_failed07(String paymentMethod, String bankCountryCode,
                                  String accountName, String accountNumber,
                                  String bsb, String aba, String swiftCode) {
        //构造数据
        BankRequest request = new BankRequest();
        request.setPayment_method(paymentMethod);
        request.setBank_country_code(bankCountryCode);
        request.setAccount_name(accountName);
        request.setAccount_number(accountNumber);
        request.setBsb(bsb);
        request.setAba(aba);
        request.setSwift_code(swiftCode);
        //调接口
        Response results = target("/bank")
                .request()
                .post(Entity.entity(request, CONTENT_TYPE));
        //断言
        Map<String, String> returnBody = results.readEntity(Map.class);
        assertThat(results.getStatus()).isEqualTo(400);
        assertThat(returnBody.get("error")).contains("The swift code is not valid", bankCountryCode);

    }

    /**
     * swift_code length
     *
     * @param paymentMethod
     * @param bankCountryCode
     * @param accountName
     * @param accountNumber
     * @param bsb
     * @param aba
     */
    @Test
    @DataProvider({
            "SWIFT, US, 钢铁侠, 1, , 123456789, <>)(US",
            "SWIFT, US, 钢铁侠, 1, , 123456789, <>)(US9902929929292",
            "SWIFT, AU, 钢铁, 123456, asdfgh , , kdj&AU",
            "SWIFT, AU, 钢铁, 123456, asdfgh , , kdj&AU23_DKJDKDK((((",
            "SWIFT, CN, gt, asdfzxcv,  , , asdgCN",
            "SWIFT, CN, gt, asdfzxcv,  , , asdgCNkdkdkkdkdkdk!@@121",

    })
    public void testBank_failed08(String paymentMethod, String bankCountryCode,
                                  String accountName, String accountNumber,
                                  String bsb, String aba, String swiftCode) {
        //构造数据
        BankRequest request = new BankRequest();
        request.setPayment_method(paymentMethod);
        request.setBank_country_code(bankCountryCode);
        request.setAccount_name(accountName);
        request.setAccount_number(accountNumber);
        request.setBsb(bsb);
        request.setAba(aba);
        request.setSwift_code(swiftCode);
        //调接口
        Response results = target("/bank")
                .request()
                .post(Entity.entity(request, CONTENT_TYPE));
        //断言
        Map<String, String> returnBody = results.readEntity(Map.class);
        assertThat(results.getStatus()).isEqualTo(400);
        assertThat(returnBody.get("error")).contains("Length of 'swift_code' should be either 8 or 11");

    }


    /**
     * bsb异常
     *
     * @param paymentMethod
     * @param bankCountryCode
     * @param accountName
     * @param accountNumber
     * @param bsb
     * @param aba
     */
    @Test
    @DataProvider({
//            "LOCAL, AU, 钢铁, 123456, , , ",
            "LOCAL, AU, 钢铁acv%^18, 123456, asdfg , , ",
            "LOCAL, AU, 钢铁!@#$%^&*, <>?:{}+_^, @$fghu**** , , ",
//            "SWIFT, AU, 钢铁, 123456, , , kdj&AU23_)*",
            "SWIFT, AU, 钢铁acv%^18, 123456, ash , , !@#$AU23_*",
            "SWIFT, AU, 钢铁!@#$%^&*, <>?:{}+_^, @$fghu大家觉得觉得基督教基督教的, ,!@#$AU2_*",
    })
    public void testBank_failed09(String paymentMethod, String bankCountryCode,
                                 String accountName, String accountNumber,
                                 String bsb, String aba, String swiftCode) {
        //构造数据
        BankRequest request = new BankRequest();
        request.setPayment_method(paymentMethod);
        request.setBank_country_code(bankCountryCode);
        request.setAccount_name(accountName);
        request.setAccount_number(accountNumber);
        request.setBsb(bsb);
        request.setAba(aba);
        request.setSwift_code(swiftCode);
        //调接口
        Response results = target("/bank")
                .request()
                .post(Entity.entity(request, CONTENT_TYPE));
        //断言
        Map<String, String> returnBody = results.readEntity(Map.class);
        assertThat(results.getStatus()).isEqualTo(400);
        assertThat(returnBody.get("error")).contains("'bsb' is required when bank country code is 'AU'");

    }


    /**
     * aba异常
     *
     * @param paymentMethod
     * @param bankCountryCode
     * @param accountName
     * @param accountNumber
     * @param bsb
     * @param aba
     */
    @Test
    @DataProvider({
//            "LOCAL, US, 钢铁, 1234567890, , , ",
            "LOCAL, US, 钢铁侠, 1, , 12345678, ",
            "LOCAL, US, 钢铁aa侠钢铁侠侠侠, 12345678901234567, , 等夸克贷款大口大口看到快点快点快点开快点快点看看, ",
            "SWIFT, US, 钢铁, 1234567890, , 12345678等夸克的看法看得开（@**@9, KDLSUS12",
//            "SWIFT, US, 钢铁侠, 1, , , <>)(US12",
            "SWIFT, US, 钢铁aa侠钢铁侠侠侠, 12345678901234567, , 12345678, kdj&US23_)*",
    })
    public void testBank_failed10(String paymentMethod, String bankCountryCode,
                                 String accountName, String accountNumber,
                                 String bsb, String aba, String swiftCode) {
        //构造数据
        BankRequest request = new BankRequest();
        request.setPayment_method(paymentMethod);
        request.setBank_country_code(bankCountryCode);
        request.setAccount_name(accountName);
        request.setAccount_number(accountNumber);
        request.setBsb(bsb);
        request.setAba(aba);
        request.setSwift_code(swiftCode);
        //调接口
        Response results = target("/bank")
                .request()
                .post(Entity.entity(request, CONTENT_TYPE));
        //断言
        Map<String, String> returnBody = results.readEntity(Map.class);
        assertThat(results.getStatus()).isEqualTo(400);
        assertThat(returnBody.get("error")).contains("Length of 'aba' should be 9");

    }


    /**
     * account name  判空
     *
     * @param paymentMethod
     * @param bankCountryCode
     * @param accountName
     * @param accountNumber
     * @param bsb
     * @param aba
     */
    @Test
    @DataProvider({
            "LOCAL, AU, , 123456, asdfgh , , ",
    })
    public void testBank_failed11(String paymentMethod, String bankCountryCode,
                                  String accountName, String accountNumber,
                                  String bsb, String aba, String swiftCode) {
        //构造数据
        BankRequest request = new BankRequest();
        request.setPayment_method(paymentMethod);
        request.setBank_country_code(bankCountryCode);
        request.setAccount_name(accountName);
        request.setAccount_number(accountNumber);
        request.setBsb(bsb);
        request.setAba(aba);
        request.setSwift_code(swiftCode);
        //调接口
        Response results = target("/bank")
                .request()
                .post(Entity.entity(request, CONTENT_TYPE));
        //断言
        Map<String, String> returnBody = results.readEntity(Map.class);
        assertThat(results.getStatus()).isEqualTo(400);
        assertThat(returnBody.get("error")).contains("'account_name' is required");

    }


    /**
     * account number  判空
     *
     * @param paymentMethod
     * @param bankCountryCode
     * @param accountName
     * @param accountNumber
     * @param bsb
     * @param aba
     */
    @Test
    @DataProvider({
            "LOCAL, US, 钢铁, , , 123456789, ",
            "SWIFT, AU, 钢铁, , asdfgh , , kdj&AU23_)*",
            "SWIFT, CN, gt, ,  , , asdgCN12",
    })
    public void testBank_failed12(String paymentMethod, String bankCountryCode,
                                  String accountName, String accountNumber,
                                  String bsb, String aba, String swiftCode) {
        //构造数据
        BankRequest request = new BankRequest();
        request.setPayment_method(paymentMethod);
        request.setBank_country_code(bankCountryCode);
        request.setAccount_name(accountName);
        request.setAccount_number(accountNumber);
        request.setBsb(bsb);
        request.setAba(aba);
        request.setSwift_code(swiftCode);
        //调接口
        Response results = target("/bank")
                .request()
                .post(Entity.entity(request, CONTENT_TYPE));
        //断言
        Map<String, String> returnBody = results.readEntity(Map.class);
        assertThat(results.getStatus()).isEqualTo(400);
        assertThat(returnBody.get("error")).contains("'account_number' is required");

    }

    /**
     * swift_code 判空
     *
     * @param paymentMethod
     * @param bankCountryCode
     * @param accountName
     * @param accountNumber
     * @param bsb
     * @param aba
     */
    @Test
    @DataProvider({
            "SWIFT, US, 钢铁, 1234567890, , 123456789, ",

    })
    public void testBank_failed13(String paymentMethod, String bankCountryCode,
                                  String accountName, String accountNumber,
                                  String bsb, String aba, String swiftCode) {
        //构造数据
        BankRequest request = new BankRequest();
        request.setPayment_method(paymentMethod);
        request.setBank_country_code(bankCountryCode);
        request.setAccount_name(accountName);
        request.setAccount_number(accountNumber);
        request.setBsb(bsb);
        request.setAba(aba);
        request.setSwift_code(swiftCode);
        //调接口
        Response results = target("/bank")
                .request()
                .post(Entity.entity(request, CONTENT_TYPE));
        //断言
        Map<String, String> returnBody = results.readEntity(Map.class);
        assertThat(results.getStatus()).isEqualTo(400);
        assertThat(returnBody.get("error")).contains("'swift_code' is required when payment method is 'SWIFT'");

    }

    /**
     * bsb 空
     *
     * @param paymentMethod
     * @param bankCountryCode
     * @param accountName
     * @param accountNumber
     * @param bsb
     * @param aba
     */
    @Test
    @DataProvider({
            "LOCAL, AU, 钢铁, 123456, , , ",
            "SWIFT, AU, 钢铁, 123456, , , kdj&AU23_)*",
    })
    public void testBank_failed14(String paymentMethod, String bankCountryCode,
                                  String accountName, String accountNumber,
                                  String bsb, String aba, String swiftCode) {
        //构造数据
        BankRequest request = new BankRequest();
        request.setPayment_method(paymentMethod);
        request.setBank_country_code(bankCountryCode);
        request.setAccount_name(accountName);
        request.setAccount_number(accountNumber);
        request.setBsb(bsb);
        request.setAba(aba);
        request.setSwift_code(swiftCode);
        //调接口
        Response results = target("/bank")
                .request()
                .post(Entity.entity(request, CONTENT_TYPE));
        //断言
        Map<String, String> returnBody = results.readEntity(Map.class);
        assertThat(results.getStatus()).isEqualTo(400);
        assertThat(returnBody.get("error")).contains("'bsb' is required when bank country code is 'AU'");

    }


    /**
     * aba 空
     *
     * @param paymentMethod
     * @param bankCountryCode
     * @param accountName
     * @param accountNumber
     * @param bsb
     * @param aba
     */
    @Test
    @DataProvider({
            "LOCAL, US, 钢铁, 1234567890, , , ",
            "SWIFT, US, 钢铁侠, 1, , , <>)(US12",
    })
    public void testBank_failed15(String paymentMethod, String bankCountryCode,
                                  String accountName, String accountNumber,
                                  String bsb, String aba, String swiftCode) {
        //构造数据
        BankRequest request = new BankRequest();
        request.setPayment_method(paymentMethod);
        request.setBank_country_code(bankCountryCode);
        request.setAccount_name(accountName);
        request.setAccount_number(accountNumber);
        request.setBsb(bsb);
        request.setAba(aba);
        request.setSwift_code(swiftCode);
        //调接口
        Response results = target("/bank")
                .request()
                .post(Entity.entity(request, CONTENT_TYPE));
        //断言
        Map<String, String> returnBody = results.readEntity(Map.class);
        assertThat(results.getStatus()).isEqualTo(400);
        assertThat(returnBody.get("error")).contains("'aba' is required when bank country code is 'US'");

    }

}

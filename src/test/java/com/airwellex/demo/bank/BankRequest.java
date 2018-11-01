package com.airwellex.demo.bank;

/**
 * Created by houjiagang on 2018/10/30.
 */
public class BankRequest {
    private String payment_method;
    private String bank_country_code;
    private String account_name;
    private String account_number;
    private String swift_code;
    private String bsb;
    private String aba;

    public String getBsb() {
        return bsb;
    }

    public void setBsb(String bsb) {
        this.bsb = bsb;
    }

    public String getPayment_method() {
        return payment_method;
    }

    public void setPayment_method(String payment_method) {
        this.payment_method = payment_method;
    }

    public String getBank_country_code() {
        return bank_country_code;
    }

    public void setBank_country_code(String bank_country_code) {
        this.bank_country_code = bank_country_code;
    }

    public String getAccount_name() {
        return account_name;
    }

    public void setAccount_name(String account_name) {
        this.account_name = account_name;
    }

    public String getAccount_number() {
        return account_number;
    }

    public void setAccount_number(String account_number) {
        this.account_number = account_number;
    }

    public String getSwift_code() {
        return swift_code;
    }

    public void setSwift_code(String swift_code) {
        this.swift_code = swift_code;
    }

    public String getAba() {
        return aba;
    }

    public void setAba(String aba) {
        this.aba = aba;
    }
}

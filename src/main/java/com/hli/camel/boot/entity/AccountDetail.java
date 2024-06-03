package com.hli.camel.boot.entity;

import lombok.Getter;
import lombok.ToString;
import org.apache.camel.dataformat.bindy.annotation.DataField;
import org.apache.camel.dataformat.bindy.annotation.FixedLengthRecord;

@Getter
@ToString
@FixedLengthRecord
public class AccountDetail {

    @DataField(pos = 1, length = 20, align = "L")
    private String bankAccount;

    @DataField(pos = 2, length = 10, align = "L")
    private String bankKey;

    @DataField(pos = 3, length = 10, align = "L")
    private String clntSel;

    @DataField(pos = 4, length = 30, align = "L")
    private String bankAccDsc;

    @DataField(pos = 5, length = 30, align = "L")
    private String bankDesc;

    @DataField(pos = 6, length = 30, align = "L")
    private String branchDesc;

    @DataField(pos = 7, length = 47, align = "L")
    private String cltName;

    @DataField(pos = 8, length = 3, align = "L")
    private String currCode;

    @DataField(pos = 9, length = 8, align = "L", trim = true, pattern = "ddMMyyyy")
    private int dateFrom;

    @DataField(pos = 10, length = 8, align = "L", trim = true, pattern = "ddMMyyyy")
    private int datTo;

    @DataField(pos = 11, length = 2, align = "L")
    private String factHous;

    @DataField(pos = 12, length = 1, align = "L")
    private String nreFlag;

    @DataField(pos = 13, length = 2, align = "L")
    private String zacType;

    @DataField(pos = 14, length = 3, align = "L")
    private String zfolio;

    @DataField(pos = 15, length = 15, align = "L")
    private String zifccde;

    @DataField(pos = 16, length = 15, align = "L", trim = true)
    private long zmicr;

    @DataField(pos = 17, length = 60, align = "L")
    private String resultAdd;

}

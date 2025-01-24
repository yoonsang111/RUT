package com.stream.tour.common.utils;

import com.stream.tour.domain.bank.entity.BankCode;
import com.stream.tour.domain.bank.enums.DataType;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Arrays;

public class BankCodeTestUtils {

    public static BankCode createBankCode() {
        return Arrays.stream(BankCode.class.getDeclaredConstructors())
                .filter(constructor -> constructor.getParameterCount() == 0)
                .map(constructor -> {
                    constructor.setAccessible(true);
                    BankCode bankCode = null;
                    try {
                        bankCode = (BankCode) constructor.newInstance();
                        ReflectionTestUtils.setField(bankCode, "dataType", DataType.BANK);
                        ReflectionTestUtils.setField(bankCode, "bankCode", "123");
                        ReflectionTestUtils.setField(bankCode, "vendor", "국민은행");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return bankCode;
                })
                .findAny().orElseThrow();
    }
}

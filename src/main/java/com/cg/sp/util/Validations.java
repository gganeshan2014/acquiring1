package com.cg.sp.util;

import com.cg.sp.exceptionhandler.InvalidRequestException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class Validations {
    public boolean validateEmptyOrNull(String value) {
        if (StringUtils.isBlank(value)) {
            throw new InvalidRequestException("At least one field is not present in request");
        } else return false;
    }

    public boolean validateTerminalId(String terminalId) {
        String regex = "^(?=.*[A-Za-z].*)(?=.*[0-9].*)[A-Za-z0-9]{10}$";
        if (terminalId == null || !(terminalId.trim().matches(regex)))
            throw new InvalidRequestException("Terminal ID is not valid");
        else return true;
    }
}

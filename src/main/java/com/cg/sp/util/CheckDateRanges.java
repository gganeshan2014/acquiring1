package com.cg.sp.util;

import com.cg.sp.exceptionhandler.InvalidDateRange;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.validator.GenericValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class CheckDateRanges {
    @Value("${timeGapInDays}")
    private int timeGapInDays;

    @Value(("${dateRangeInYears}"))
    private int dateRangeInYears;

    private Date startDate;
    private Date endDate;
    private long checkTimeGap;
    private static final Logger LOG = LoggerFactory.getLogger(CheckDateRanges.class);
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");


    public boolean exceededDateRange(String stDate, String eDate) {
        try {
            startDate = sdf.parse(stDate);
            endDate = sdf.parse(eDate);

            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.YEAR, -dateRangeInYears); // to calculate given years back - minimum date
            Date date8yearsBack = cal.getTime();

            Calendar cal1 = Calendar.getInstance();
            cal1.add(Calendar.DAY_OF_MONTH, -1); // to calculate one day before - maximum date
            Date today = cal1.getTime();

            if (startDate.before(date8yearsBack) || endDate.after(today))
                return false;
            else return true;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean invalidDateRange(String stDate, String eDate) {
        try {
            Date startDate = sdf.parse(stDate);
            Date endDate = sdf.parse(eDate);
            long differenceInTime = endDate.getTime() - startDate.getTime();
            checkTimeGap = TimeUnit.MILLISECONDS.toDays(differenceInTime);
            if (startDate.before(endDate) && (checkTimeGap <= timeGapInDays && checkTimeGap >= 1)) {
                return true;
            } else return false;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean validateRegEx(String date) {
        if(GenericValidator.isDate(date,"dd-MM-yyyy",true))
            return true;
        else
           return false;
    }
}


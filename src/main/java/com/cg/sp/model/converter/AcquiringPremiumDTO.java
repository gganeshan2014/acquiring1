package com.cg.sp.model.converter;

import java.util.Date;

public interface AcquiringPremiumDTO {
    Integer getTotalVolume();

    Double getTotalTransactionValue();

    String getRegion();

    String getLocation();

    String getCity();

    String getMcc();

    String getMccDescription();

    Date getTransactionDate();
}

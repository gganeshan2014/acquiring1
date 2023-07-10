package com.cg.sp.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AcquiringStandard {
    private String channel;

    private Integer transactionVolume;

    private Double transactionValue;

    private String terminalId;


    private String startDate;


    private String endDate;
}

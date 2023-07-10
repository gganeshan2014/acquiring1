package com.cg.sp.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AcquiringPremium {
    private String region;

    private String city;

    private String location;

    @Temporal(value = TemporalType.DATE)
    @JsonFormat(pattern = "dd-MM-yyyy", shape = JsonFormat.Shape.STRING)
    private Date transDate;

    private String mcc;

    private String mccDescription;

    private String channel;

    private Integer transactionVolume;

    private Double transactionValue;

    private String terminalId;
    

    private String startDate;

    private String endDate;
}

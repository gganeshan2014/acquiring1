package com.cg.sp.model.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AcquiringRequest {

    private String terminalId;

    @Column(nullable = false)
    private String startDate;

    @Column(nullable = false)
    private String endDate;
    private UserProfile userProfile;
}

package com.cg.sp.service;

import com.cg.sp.model.request.AcquiringRequest;

public interface AcquiringService {
    Object retrieveAcquiringData(AcquiringRequest acquiringRequest, int pageNo, int size);
}

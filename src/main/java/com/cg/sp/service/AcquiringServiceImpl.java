package com.cg.sp.service;

import com.cg.sp.constants.Subscriptions;
import com.cg.sp.exceptionhandler.InvalidDateRange;
import com.cg.sp.exceptionhandler.InvalidRequestException;
import com.cg.sp.exceptionhandler.InvalidSubscription;
import com.cg.sp.exceptionhandler.NoDataFound;
import com.cg.sp.model.converter.AcquiringPremiumDTO;
import com.cg.sp.model.converter.AcquiringStandardDTO;
import com.cg.sp.model.request.AcquiringRequest;
import com.cg.sp.model.request.UserProfile;
import com.cg.sp.model.response.AcquiringPremium;
import com.cg.sp.model.response.AcquiringStandard;
import com.cg.sp.repository.AcquiringPremiumRepository;
import com.cg.sp.repository.AcquiringStandardRepository;
import com.cg.sp.util.CheckDateRanges;
import com.cg.sp.util.Validations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class AcquiringServiceImpl implements AcquiringService {
	@Autowired
	AcquiringStandardRepository acquiringStandardRepository;
	@Autowired
	AcquiringPremiumRepository acquiringPremiumRepository;

	@Autowired
	CheckDateRanges checkDateRanges;

	@Autowired
	Validations validations;
	SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

	private static final Logger LOG = LoggerFactory.getLogger(AcquiringServiceImpl.class);

	private AcquiringStandard createStandardResponse(AcquiringRequest acquiringRequest) {
		validateRequestBody(acquiringRequest);
		validateDateRange(acquiringRequest.getStartDate(), acquiringRequest.getEndDate());

		AcquiringStandard acquiringStandard = new AcquiringStandard();
		AcquiringStandardDTO acquiringStandardDTO = null;
		try {
			acquiringStandardDTO = acquiringStandardRepository
					.fetchStandardDetails(acquiringRequest.getTerminalId(),
							sdf.parse(acquiringRequest.getStartDate()), sdf.parse(acquiringRequest.getEndDate()));
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
		LOG.info("acquiringStandardDTO : {}", acquiringStandardDTO);
		if (acquiringStandardDTO == null)
			throw new NoDataFound("No data found for given parameters");
		else {
			acquiringStandard.setTransactionVolume(acquiringStandardDTO.getTotalVolume());
			acquiringStandard.setTransactionValue(acquiringStandardDTO.getTotalTransactionValue());
			acquiringStandard.setChannel("PoS");
			acquiringStandard.setTerminalId(acquiringRequest.getTerminalId());
			acquiringStandard.setStartDate(acquiringRequest.getStartDate());
			acquiringStandard.setEndDate(acquiringRequest.getEndDate());
			return acquiringStandard;
		}
	}

	private List<AcquiringPremium> createPremiumResponse(AcquiringRequest acquiringRequest, int pageNo, int size) {
		validateRequestBody(acquiringRequest);
		validateDateRange(acquiringRequest.getStartDate(), acquiringRequest.getEndDate());

		List<AcquiringPremium> acquiringPremiumList = new ArrayList<>();

		Pageable pageable = PageRequest.of(pageNo, size);
		List<AcquiringPremiumDTO> acquiringPremiumDTOList = null;
		try {
			acquiringPremiumDTOList = acquiringPremiumRepository.fetchPremiumDetails(
					acquiringRequest.getTerminalId(), sdf.parse(acquiringRequest.getStartDate()),
					sdf.parse(acquiringRequest.getEndDate()), pageable);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
		if (acquiringPremiumDTOList.isEmpty())
			throw new NoDataFound("No data found for given parameters");
		else {
			for (AcquiringPremiumDTO acqPremium : acquiringPremiumDTOList) {
				AcquiringPremium acquiringPremium = new AcquiringPremium();
				acquiringPremium.setChannel("PoS");
				acquiringPremium.setTransactionValue(acqPremium.getTotalTransactionValue());
				acquiringPremium.setTransactionVolume(acqPremium.getTotalVolume());
				acquiringPremium.setRegion(acqPremium.getRegion());
				acquiringPremium.setCity(acqPremium.getCity());
				acquiringPremium.setLocation(acqPremium.getLocation());
				acquiringPremium.setMcc(acqPremium.getMcc());
				acquiringPremium.setMccDescription(acqPremium.getMccDescription());
				acquiringPremium.setTerminalId(acquiringRequest.getTerminalId());
				acquiringPremium.setTransDate(acqPremium.getTransactionDate());
				acquiringPremium.setStartDate(acquiringRequest.getStartDate());
				acquiringPremium.setEndDate(acquiringRequest.getEndDate());
				acquiringPremiumList.add(acquiringPremium);
			}
			LOG.info("acquiringPremiumList: {}", acquiringPremiumList);
			return acquiringPremiumList;
		}
	}

	@Override
	public Object retrieveAcquiringData(AcquiringRequest acquiringRequest, int pageNo, int size) {
		Subscriptions subscriptions = this.retrieveSubscription(acquiringRequest.getUserProfile());
		if (null != subscriptions && subscriptions.equals(Subscriptions.STANDARD))
			return createStandardResponse(acquiringRequest);
		else if (null != subscriptions && subscriptions.equals(Subscriptions.PREMIUM))
			return createPremiumResponse(acquiringRequest, pageNo, size);
		else
			throw new InvalidSubscription("Invalid Subscription Type");
	}

	private void validateDateRange(String startDate, String endDate) {
		if (!(checkDateRanges.validateRegEx(startDate) && checkDateRanges.validateRegEx(endDate)))
			throw new InvalidRequestException("Request format is incorrect");
		if (!checkDateRanges.exceededDateRange(startDate, endDate))
			throw new InvalidDateRange("Date Range exceeded the allowed limits");
		if (!checkDateRanges.invalidDateRange(startDate, endDate))
			throw new InvalidDateRange("Date Range is not valid  - Either minimum or maximum value");

	}
	private void validateRequestBody(AcquiringRequest acquiringRequest) {
		try {
			validations.validateTerminalId(acquiringRequest.getTerminalId());
			validations.validateEmptyOrNull(acquiringRequest.getStartDate());
			validations.validateEmptyOrNull(acquiringRequest.getEndDate());
		} catch (InvalidRequestException e) {
			throw new InvalidRequestException(e.getMessage());
		}

	}
	private Subscriptions retrieveSubscription(UserProfile userProfile) {
		if (userProfile == null) {
			throw new InvalidRequestException("Request format is incorrect");
		} else {
			Subscriptions subscription = Subscriptions.getSubscriptionByValue(userProfile.getSubscription());
			if (subscription != null)
				return subscription;
			else
				throw new InvalidSubscription("Invalid Subscription Type");
		}
	}

}

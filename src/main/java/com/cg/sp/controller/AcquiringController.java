package com.cg.sp.controller;

import com.cg.sp.model.request.AcquiringRequest;
import com.cg.sp.service.AcquiringService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
public class AcquiringController {
	
	@Autowired
	AcquiringService acquiringService;

	@PostMapping(value = "/acquiringPOSMada")
	public ResponseEntity<Object> createAcquiringDetails(@Valid @RequestBody AcquiringRequest acquiringRequest,
			@RequestParam(defaultValue = "1") int pageNo, @RequestParam(defaultValue = "10") int size) {
		return new ResponseEntity<>(acquiringService.retrieveAcquiringData(acquiringRequest, pageNo - 1, size), HttpStatus.OK);
	}
}

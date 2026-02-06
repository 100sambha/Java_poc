package com.test.rest_demo.tushar;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NPSGatewayController {
	
	
	@PostMapping("/test")
	public ResponseEntity<String> getAccountNumberKycStatus(@RequestBody GatewayReqDto<FetchKycReqDto> reqDto) {
		
		FetchKycReqDto dto = reqDto.getData();
		System.out.println("DTO"+reqDto);
		System.out.println("DTO"+dto);
		return ResponseEntity.status(200).body("KYC Done");
	}

}

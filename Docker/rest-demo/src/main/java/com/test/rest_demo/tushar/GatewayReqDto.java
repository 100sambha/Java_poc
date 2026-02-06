package com.test.rest_demo.tushar;

public class GatewayReqDto<T> {

	private String keyId;
	private T data;

	public GatewayReqDto() {
		super();
	}
	
	public GatewayReqDto(String keyId, T data) {
		super();
		this.keyId = keyId;
		this.data = data;
	}
	
	public String getKeyId() {
		return keyId;
	}
	public T getData() {
		return data;
	}	
	
}

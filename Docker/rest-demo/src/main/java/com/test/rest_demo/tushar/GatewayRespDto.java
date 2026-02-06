package com.test.rest_demo.tushar;

public class GatewayRespDto<T> {
    private String status;
    private String errorCode;
    private String errorMessage;
    private T payload;

    public GatewayRespDto() {}

    public GatewayRespDto(String status, String errorCode, String errorMessage, T payload) {
        this.status = status;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.payload = payload;
    }

}

package com.todo.solid;

import org.springframework.stereotype.Service;

@Service
public class ControllerService{
	
	public ControllerService(UPI_Impl upi_Impl) {
		Interface1 interface1 = upi_Impl;
		interface1.pay();
	}

}

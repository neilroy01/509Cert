package com.service_now.mysoap;

public class TestMit {

	public static void main(String[] args) {

		System.out.println("START");
		ServiceNowMySOAP serviceSoap = new ServiceNowMySOAP();
		ServiceNowSoap serviceNowSoap = serviceSoap.getServiceNowSoap();
		serviceNowSoap.execute();
		System.out.println("END");
		

	}

}

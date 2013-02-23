/**
 * SimpleServiceSkeleton.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */
package com.ttdev.ss;

/**
 * SimpleServiceSkeleton java skeleton for the axisService
 */
public class SimpleServiceSkeleton {

	/**
	 * Auto generated method signature
	 * 
	 * @param concatRequest
	 * @return concatResponse
	 */

	public com.ttdev.ss.ConcatResponse concat(
			com.ttdev.ss.ConcatRequest concatRequest) {
		String result = concatRequest.getS1() + concatRequest.getS2();
		ConcatResponse response = new ConcatResponse();
		response.setConcatResponse(result);
		return response;
	}

}

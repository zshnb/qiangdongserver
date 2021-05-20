package com.qiangdong.reader.request.pay;

import com.qiangdong.reader.request.BaseRequest;

public class ApplyCallbackRequest extends BaseRequest {

	/**商户订单号*/
	private String orderNumber;
	/**苹果票据*/
	private String receipt;

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getReceipt() {
		return receipt;
	}

	public void setReceipt(String receipt) {
		this.receipt = receipt;
	}
}

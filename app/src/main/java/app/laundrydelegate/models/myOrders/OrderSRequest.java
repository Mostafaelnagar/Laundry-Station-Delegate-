package app.laundrydelegate.models.myOrders;

import com.google.gson.annotations.SerializedName;

public class OrderSRequest{

	@SerializedName("msg")
	private String msg;

	@SerializedName("data")
	private OrderData orderData;

	@SerializedName("status")
	private int status;

	public void setMsg(String msg){
		this.msg = msg;
	}

	public String getMsg(){
		return msg;
	}

	public void setData(OrderData orderData){
		this.orderData = orderData;
	}

	public OrderData getData(){
		return orderData;
	}

	public void setStatus(int status){
		this.status = status;
	}

	public int getStatus(){
		return status;
	}

	@Override
 	public String toString(){
		return 
			"OrderSRequest{" + 
			"msg = '" + msg + '\'' + 
			",orderData = '" + orderData + '\'' +
			",status = '" + status + '\'' + 
			"}";
		}
}
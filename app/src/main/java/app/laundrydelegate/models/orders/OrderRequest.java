package app.laundrydelegate.models.orders;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class OrderRequest{

	@SerializedName("msg")
	private String msg;

	@SerializedName("data")
	private List<OrderRequestDetail> orderRequestDetails;

	@SerializedName("status")
	private int status;

	public void setMsg(String msg){
		this.msg = msg;
	}

	public String getMsg(){
		return msg;
	}

	public List<OrderRequestDetail> getOrderRequestDetails() {
		return orderRequestDetails;
	}

	public void setOrderRequestDetails(List<OrderRequestDetail> orderRequestDetails) {
		this.orderRequestDetails = orderRequestDetails;
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
			"OrderRequest{" + 
			"msg = '" + msg + '\'' + 
			",orderRequestDetails = '" + orderRequestDetails + '\'' +
			",status = '" + status + '\'' + 
			"}";
		}
}
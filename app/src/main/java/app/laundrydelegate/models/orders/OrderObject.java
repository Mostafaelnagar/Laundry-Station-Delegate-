package app.laundrydelegate.models.orders;

import com.google.gson.annotations.SerializedName;

public class OrderObject {

	@SerializedName("service_type")
	private String serviceType;

	@SerializedName("service_price")
	private String servicePrice;

	@SerializedName("service_name")
	private String serviceName;

	@SerializedName("service_id")
	private String serviceId;

	@SerializedName("service_count")
	private String serviceCount;

	@SerializedName("service_total")
	private int serviceTotal;

	public void setServiceType(String serviceType){
		this.serviceType = serviceType;
	}

	public String getServiceType(){
		return serviceType;
	}

	public void setServicePrice(String servicePrice){
		this.servicePrice = servicePrice;
	}

	public String getServicePrice(){
		return servicePrice;
	}

	public void setServiceName(String serviceName){
		this.serviceName = serviceName;
	}

	public String getServiceName(){
		return serviceName;
	}

	public void setServiceId(String serviceId){
		this.serviceId = serviceId;
	}

	public String getServiceId(){
		return serviceId;
	}

	public void setServiceCount(String serviceCount){
		this.serviceCount = serviceCount;
	}

	public String getServiceCount(){
		return serviceCount;
	}

	public void setServiceTotal(int serviceTotal){
		this.serviceTotal = serviceTotal;
	}

	public int getServiceTotal(){
		return serviceTotal;
	}

	@Override
 	public String toString(){
		return 
			"OrderObject{" +
			"service_type = '" + serviceType + '\'' + 
			",service_price = '" + servicePrice + '\'' + 
			",service_name = '" + serviceName + '\'' + 
			",service_id = '" + serviceId + '\'' + 
			",service_count = '" + serviceCount + '\'' + 
			",service_total = '" + serviceTotal + '\'' + 
			"}";
		}
}
package null;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.asif.gsonpojogenerator")
public class ServicesItem implements Serializable{

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
}
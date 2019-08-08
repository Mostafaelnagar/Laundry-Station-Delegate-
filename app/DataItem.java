package null;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.asif.gsonpojogenerator")
public class DataItem implements Serializable{

	@SerializedName("reason")
	private String reason;

	@SerializedName("delivery")
	private String delivery;

	@SerializedName("address")
	private String address;

	@SerializedName("city")
	private String city;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("delegate_id")
	private int delegateId;

	@SerializedName("services")
	private List<ServicesItem> services;

	@SerializedName("delegate")
	private Delegate delegate;

	@SerializedName("order_status")
	private int orderStatus;

	@SerializedName("laundry")
	private Laundry laundry;

	@SerializedName("chat")
	private Object chat;

	@SerializedName("laundry_id")
	private int laundryId;

	@SerializedName("id")
	private int id;

	@SerializedName("user")
	private User user;

	public void setReason(String reason){
		this.reason = reason;
	}

	public String getReason(){
		return reason;
	}

	public void setDelivery(String delivery){
		this.delivery = delivery;
	}

	public String getDelivery(){
		return delivery;
	}

	public void setAddress(String address){
		this.address = address;
	}

	public String getAddress(){
		return address;
	}

	public void setCity(String city){
		this.city = city;
	}

	public String getCity(){
		return city;
	}

	public void setCreatedAt(String createdAt){
		this.createdAt = createdAt;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public void setDelegateId(int delegateId){
		this.delegateId = delegateId;
	}

	public int getDelegateId(){
		return delegateId;
	}

	public void setServices(List<ServicesItem> services){
		this.services = services;
	}

	public List<ServicesItem> getServices(){
		return services;
	}

	public void setDelegate(Delegate delegate){
		this.delegate = delegate;
	}

	public Delegate getDelegate(){
		return delegate;
	}

	public void setOrderStatus(int orderStatus){
		this.orderStatus = orderStatus;
	}

	public int getOrderStatus(){
		return orderStatus;
	}

	public void setLaundry(Laundry laundry){
		this.laundry = laundry;
	}

	public Laundry getLaundry(){
		return laundry;
	}

	public void setChat(Object chat){
		this.chat = chat;
	}

	public Object getChat(){
		return chat;
	}

	public void setLaundryId(int laundryId){
		this.laundryId = laundryId;
	}

	public int getLaundryId(){
		return laundryId;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setUser(User user){
		this.user = user;
	}

	public User getUser(){
		return user;
	}
}
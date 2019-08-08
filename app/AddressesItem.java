package null;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.asif.gsonpojogenerator")
public class AddressesItem implements Serializable{

	@SerializedName("address")
	private String address;

	@SerializedName("lng")
	private String lng;

	@SerializedName("id")
	private int id;

	@SerializedName("lat")
	private String lat;

	public void setAddress(String address){
		this.address = address;
	}

	public String getAddress(){
		return address;
	}

	public void setLng(String lng){
		this.lng = lng;
	}

	public String getLng(){
		return lng;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setLat(String lat){
		this.lat = lat;
	}

	public String getLat(){
		return lat;
	}
}
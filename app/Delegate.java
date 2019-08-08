package null;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.asif.gsonpojogenerator")
public class Delegate implements Serializable{

	@SerializedName("img")
	private String img;

	@SerializedName("phone")
	private Object phone;

	@SerializedName("jwt")
	private Object jwt;

	@SerializedName("name")
	private Object name;

	@SerializedName("id")
	private Object id;

	@SerializedName("email")
	private Object email;

	public void setImg(String img){
		this.img = img;
	}

	public String getImg(){
		return img;
	}

	public void setPhone(Object phone){
		this.phone = phone;
	}

	public Object getPhone(){
		return phone;
	}

	public void setJwt(Object jwt){
		this.jwt = jwt;
	}

	public Object getJwt(){
		return jwt;
	}

	public void setName(Object name){
		this.name = name;
	}

	public Object getName(){
		return name;
	}

	public void setId(Object id){
		this.id = id;
	}

	public Object getId(){
		return id;
	}

	public void setEmail(Object email){
		this.email = email;
	}

	public Object getEmail(){
		return email;
	}
}
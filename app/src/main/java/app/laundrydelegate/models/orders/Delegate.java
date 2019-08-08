package app.laundrydelegate.models.orders;

import com.google.gson.annotations.SerializedName;

public class Delegate{

	@SerializedName("img")
	private String img;

	@SerializedName("phone")
	private String phone;

	@SerializedName("jwt")
	private String jwt;

	@SerializedName("name")
	private String name;

	@SerializedName("id")
	private int id;

	@SerializedName("email")
	private String email;

	public void setImg(String img){
		this.img = img;
	}

	public String getImg(){
		return img;
	}

	public void setPhone(String phone){
		this.phone = phone;
	}

	public String getPhone(){
		return phone;
	}

	public void setJwt(String jwt){
		this.jwt = jwt;
	}

	public String getJwt(){
		return jwt;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return email;
	}

	@Override
 	public String toString(){
		return 
			"Delegate{" + 
			"img = '" + img + '\'' + 
			",phone = '" + phone + '\'' + 
			",jwt = '" + jwt + '\'' + 
			",name = '" + name + '\'' + 
			",id = '" + id + '\'' + 
			",email = '" + email + '\'' + 
			"}";
		}
}
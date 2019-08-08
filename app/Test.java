package null;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.asif.gsonpojogenerator")
public class Test implements Serializable{

	@SerializedName("msg")
	private String msg;

	@SerializedName("data")
	private Data data;

	@SerializedName("status")
	private int status;

	public void setMsg(String msg){
		this.msg = msg;
	}

	public String getMsg(){
		return msg;
	}

	public void setData(Data data){
		this.data = data;
	}

	public Data getData(){
		return data;
	}

	public void setStatus(int status){
		this.status = status;
	}

	public int getStatus(){
		return status;
	}
}
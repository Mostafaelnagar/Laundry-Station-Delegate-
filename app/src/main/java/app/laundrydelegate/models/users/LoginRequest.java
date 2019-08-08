package app.laundrydelegate.models.users;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginRequest {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private DelegatedData data;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DelegatedData getData() {
        return data;
    }

    public void setData(DelegatedData data) {
        this.data = data;
    }
}

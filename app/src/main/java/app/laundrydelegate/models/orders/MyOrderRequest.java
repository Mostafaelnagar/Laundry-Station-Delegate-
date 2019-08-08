package app.laundrydelegate.models.orders;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import app.laundrydelegate.models.orders.OrderRequestDetail;

public class MyOrderRequest {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private List<OrderRequestDetail> data = null;

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

    public List<OrderRequestDetail> getData() {
        return data;
    }

    public void setData(List<OrderRequestDetail> data) {
        this.data = data;
    }
}

package com.bottomline.aarot.models;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import java.util.Objects;

public class RequestItem {
    private String id;
    private String token;
    private String price;
    private String name;
    private String address;
    private String phone;
    private String process_date;
    private String delivery_date;
    private String agent_name;
    private String agent_phone;
    private Boolean status;
    private Boolean destroyed;

    public RequestItem() {
    }

    public RequestItem(String id, String token, String price, String name, String address, String phone, String process_date, String delivery_date, String agent_name, String agent_phone, Boolean status, Boolean destroyed) {
        this.id = id;
        this.token = token;
        this.price = price;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.process_date = process_date;
        this.delivery_date = delivery_date;
        this.agent_name = agent_name;
        this.agent_phone = agent_phone;
        this.status = status;
        this.destroyed = destroyed;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProcess_date() {
        return process_date;
    }

    public void setProcess_date(String process_date) {
        this.process_date = process_date;
    }

    public String getDelivery_date() {
        return delivery_date;
    }

    public void setDelivery_date(String delivery_date) {
        this.delivery_date = delivery_date;
    }

    public String getAgent_name() {
        return agent_name;
    }

    public void setAgent_name(String agent_name) {
        this.agent_name = agent_name;
    }

    public String getAgent_phone() {
        return agent_phone;
    }

    public void setAgent_phone(String agent_phone) {
        this.agent_phone = agent_phone;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Boolean getDestroyed() {
        return destroyed;
    }

    public void setDestroyed(Boolean destroyed) {
        this.destroyed = destroyed;
    }


    @Override
    public String toString() {
        return "PurchaseItem{" +
                "id='" + id + '\'' +
                ", token='" + token + '\'' +
                ", price='" + price + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", process_date='" + process_date + '\'' +
                ", delivery_date='" + delivery_date + '\'' +
                ", agent_name='" + agent_name + '\'' +
                ", agent_phone='" + agent_phone + '\'' +
                ", status=" + status +
                ", destroyed=" + destroyed +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestItem that = (RequestItem) o;
        return Objects.equals(getId(), that.getId()) &&
                Objects.equals(getToken(), that.getToken()) &&
                Objects.equals(getPrice(), that.getPrice()) &&
                Objects.equals(getName(), that.getName()) &&
                Objects.equals(getAddress(), that.getAddress()) &&
                Objects.equals(getPhone(), that.getPhone()) &&
                Objects.equals(getProcess_date(), that.getProcess_date()) &&
                Objects.equals(getDelivery_date(), that.getDelivery_date()) &&
                Objects.equals(getAgent_name(), that.getAgent_name()) &&
                Objects.equals(getAgent_phone(), that.getAgent_phone()) &&
                Objects.equals(getStatus(), that.getStatus()) &&
                Objects.equals(getDestroyed(), that.getDestroyed());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getToken(), getPrice(), getName(), getAddress(), getPhone(), getProcess_date(), getDelivery_date(), getAgent_name(), getAgent_phone(), getStatus(), getDestroyed());
    }

    public static DiffUtil.ItemCallback<RequestItem> itemCallback = new DiffUtil.ItemCallback<RequestItem>() {
        @Override
        public boolean areItemsTheSame(@NonNull RequestItem oldItem, @NonNull RequestItem newItem) {
            return oldItem.getId().equals(newItem.getId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull RequestItem oldItem, @NonNull RequestItem newItem) {
            return oldItem.equals(newItem);
        }
    };

}
package com.bottomline.aarot.models;

import java.util.Objects;

public class AgentItem {
    String id;
    String name;
    String email;
    String phone;
    String password;

    public AgentItem() {
    }

    public AgentItem(String id, String name, String email, String phone, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "AgentItem{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AgentItem agentItem = (AgentItem) o;
        return getId().equals(agentItem.getId()) &&
                getName().equals(agentItem.getName()) &&
                getEmail().equals(agentItem.getEmail()) &&
                getPhone().equals(agentItem.getPhone()) &&
                getPassword().equals(agentItem.getPassword());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getEmail(), getPhone(), getPassword());
    }
}

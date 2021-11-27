package com.bottomline.aarot.models;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import java.util.Objects;

public class PurchasedProductItem {
    private String id;
    private String name;
    private String price;
    private String quantity;

    public PurchasedProductItem() {
    }

    public PurchasedProductItem(String id, String name, String price, String quantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "PurchasedProductItem{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", price='" + price + '\'' +
                ", quantity='" + quantity + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PurchasedProductItem that = (PurchasedProductItem) o;
        return Objects.equals(getId(), that.getId()) &&
                Objects.equals(getName(), that.getName()) &&
                Objects.equals(getPrice(), that.getPrice()) &&
                Objects.equals(getQuantity(), that.getQuantity());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getPrice(), getQuantity());
    }

    public static DiffUtil.ItemCallback<PurchasedProductItem> itemCallback = new DiffUtil.ItemCallback<PurchasedProductItem>() {
        @Override
        public boolean areItemsTheSame(@NonNull PurchasedProductItem oldItem, @NonNull PurchasedProductItem newItem) {
            return oldItem.getId().equals(newItem.getId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull PurchasedProductItem oldItem, @NonNull PurchasedProductItem newItem) {
            return oldItem.equals(newItem);
        }
    };

}

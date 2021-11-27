package com.bottomline.aarot.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bottomline.aarot.databinding.PurchasedItemsRowBinding;
import com.bottomline.aarot.models.PurchasedProductItem;

public class PurchasedItemsListAdapter extends ListAdapter<PurchasedProductItem, PurchasedItemsListAdapter.PurchaseViewHolder> {

    PurchasedItemsInterface purchasedItemsInterface;
    public PurchasedItemsListAdapter(PurchasedItemsInterface purchasedItemsInterface) {
        super(PurchasedProductItem.itemCallback);
        this.purchasedItemsInterface = purchasedItemsInterface;
    }

    @NonNull
    @Override
    public PurchaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        PurchasedItemsRowBinding purchasedItemsRowBinding = PurchasedItemsRowBinding.inflate(layoutInflater,parent,false);
        return new PurchaseViewHolder(purchasedItemsRowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull PurchaseViewHolder holder, int position) {
        PurchasedProductItem purchaseItem = getItem(position);

        holder.purchasedItemsRowBinding.ProductName.setText(purchaseItem.getName());
        holder.purchasedItemsRowBinding.ProductPrice.setText(purchaseItem.getPrice()+" ৳");
        holder.purchasedItemsRowBinding.ProductQuantity.setText(purchaseItem.getQuantity());

    }

    class PurchaseViewHolder extends RecyclerView.ViewHolder{

        PurchasedItemsRowBinding purchasedItemsRowBinding;

        public PurchaseViewHolder(PurchasedItemsRowBinding binding) {
            super(binding.getRoot());
            this.purchasedItemsRowBinding = binding;
        }
    }

    public interface PurchasedItemsInterface {

    }
}

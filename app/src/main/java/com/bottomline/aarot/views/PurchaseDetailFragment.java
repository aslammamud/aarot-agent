package com.bottomline.aarot.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bottomline.aarot.adapters.OrderRequestListAdapter;
import com.bottomline.aarot.adapters.PurchasedItemsListAdapter;
import com.bottomline.aarot.databinding.FragmentPurchaseDetailBinding;
import com.bottomline.aarot.models.PurchasedProductItem;
import com.bottomline.aarot.viewmodels.ShopViewModel;

import java.util.ArrayList;
import java.util.List;

public class PurchaseDetailFragment extends Fragment implements PurchasedItemsListAdapter.PurchasedItemsInterface{
    private PurchasedProductItem item;
    FragmentPurchaseDetailBinding fragmentPurchaseDetailBinding;
    private ShopViewModel shopViewModel;
    private PurchasedItemsListAdapter adapter;

    public PurchaseDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentPurchaseDetailBinding = FragmentPurchaseDetailBinding.inflate(inflater, container, false);
        return fragmentPurchaseDetailBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new PurchasedItemsListAdapter(this);
        fragmentPurchaseDetailBinding.purchasedetailsRecyclerView.setAdapter(adapter);

        shopViewModel = new ViewModelProvider(requireActivity()).get(ShopViewModel.class);
        String token = shopViewModel.getOrderRequest().getValue().getToken();

        shopViewModel.reloadPurchasedItemsHistory(token);
        shopViewModel.getPurchasedItemsHistories(token).observe(getViewLifecycleOwner(), new Observer<List<PurchasedProductItem>>() {
            @Override
            public void onChanged(List<PurchasedProductItem> purchasedProductItems) {

                List<PurchasedProductItem> items = new ArrayList<>(purchasedProductItems);

                adapter.submitList(items);

            }
        });
        fragmentPurchaseDetailBinding.purchasedetailsRecyclerView.setAdapter(adapter);
        fragmentPurchaseDetailBinding.setPurchaseViewModel(shopViewModel);
    }

}
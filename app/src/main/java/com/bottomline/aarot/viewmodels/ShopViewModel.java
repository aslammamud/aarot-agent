package com.bottomline.aarot.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import com.bottomline.aarot.models.PurchasedProductItem;
import com.bottomline.aarot.models.RequestItem;
import com.bottomline.aarot.repositories.PurchasedItemsRepo;
import com.bottomline.aarot.repositories.RequestRepo;

import java.util.List;

public class ShopViewModel extends ViewModel {
    PurchasedItemsRepo purchasedItemsRepo = new PurchasedItemsRepo();
    RequestRepo requestRepo = new RequestRepo();

    MutableLiveData<RequestItem> mutableRequest = new MutableLiveData<>();
    MutableLiveData<PurchasedProductItem> mutablePurchasedItems = new MutableLiveData<>();


    public LiveData<List<PurchasedProductItem>> getPurchasedItemsHistories(String order_token){
        return purchasedItemsRepo.getPurchasedItemsHistories(order_token);
    }

    public void setPurchasedItems(PurchasedProductItem purchasedProductItem){
        mutablePurchasedItems.setValue(purchasedProductItem);
    }

    public LiveData<PurchasedProductItem> getPurchasedItems(){
        return mutablePurchasedItems;
    }



    public LiveData<List<RequestItem>> getPurchaseHistories(){
        return requestRepo.getOrderRequestHistories();
    }

    public void setOrderRequest(RequestItem requestItem){
        mutableRequest.setValue(requestItem);
    }

    public LiveData<RequestItem> getOrderRequest(){
        return mutableRequest;
    }


    public void requestCancelOrder(RequestItem requestItem) {
        RequestRepo.requestCancelOrderFromPurchase(requestItem);
    }


    public void reloadOrderRequestHistory(){
        requestRepo.reloadOrderRequestHistory();
    }

    public void reloadPurchasedItemsHistory(String order_token){
        PurchasedItemsRepo.reloadPurchasedItemsHistory(order_token);
    }

}

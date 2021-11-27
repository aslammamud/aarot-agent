package com.bottomline.aarot.repositories;

import android.content.Context;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bottomline.aarot.models.PurchasedProductItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.facebook.FacebookSdk.getApplicationContext;


public class PurchasedItemsRepo {
    static private MutableLiveData<List<PurchasedProductItem>> mutablePurchaseList;

    public LiveData<List<PurchasedProductItem>> getPurchasedItemsHistories(String order_token) {
        if (mutablePurchaseList == null) {
            mutablePurchaseList = new MutableLiveData<>();
            loadPurchasedItems(order_token);
        }
        return mutablePurchaseList;
    }


    public static void loadPurchasedItems(String order_token) {
        List<PurchasedProductItem> purchasedItemsList = new ArrayList<>();

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        //String url ="http://192.168.1.100/aarot_mela/shipment_req_user_purchased_items.php";
        String url ="https://server9.dhakawebhost.com/~aarotmel/aarotmela/shipment_req_user_purchased_items.php";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //productList.clear();
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");

                            JSONArray jsonArray = jsonObject.getJSONArray("data");

                            if(success.equals("1")){
                                for(int i=0;i<jsonArray.length();i++){
                                    JSONObject object = jsonArray.getJSONObject(i);

                                    String id = object.getString("id");
                                    String name = object.getString("name");
                                    String price = object.getString("price");
                                    String quantity = object.getString("quantity");

                                    purchasedItemsList.add(new PurchasedProductItem(id,name,price,quantity));

                                    mutablePurchaseList.setValue(purchasedItemsList);
                                }
                            }

                        }catch (JSONException jsonException){
                            jsonException.printStackTrace();
                        }

                        //Log.d("purchaseHistoryRepo: ",response);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("response: ",error.getMessage());
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String,String>();
                params.put("order_token",order_token);
                return params;
            }
        };

        // Add the request to the RequestQueue.
        queue.add(stringRequest);

    }

    public static void reloadPurchasedItemsHistory(String order_token){
        mutablePurchaseList = new MutableLiveData<>();
        loadPurchasedItems(order_token);

    }
}

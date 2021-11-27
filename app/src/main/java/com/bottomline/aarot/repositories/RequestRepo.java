package com.bottomline.aarot.repositories;

import android.util.Log;
import android.widget.Toast;

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
import com.bottomline.aarot.models.RequestItem;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.facebook.FacebookSdk.getApplicationContext;

public class RequestRepo {
    static private MutableLiveData<List<RequestItem>> mutableOrderRequestList;
    public LiveData<List<RequestItem>> getOrderRequestHistories() {
        if (mutableOrderRequestList == null) {
            mutableOrderRequestList = new MutableLiveData<>();
            loadOrderRequestHistories();
        }
        return mutableOrderRequestList;
    }


    public static void loadOrderRequestHistories() {
        List<RequestItem> orderRequestList = new ArrayList<>();

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        System.out.println(user.getEmail());

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url ="https://server9.dhakawebhost.com/~aarotmel/aarotmela/shipment_req_agent.php";

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

                                    String id = object.getString("ship_id");
                                    String agent_name = object.getString("ship_agent_name");
                                    String agent_phone = object.getString("ship_agent_phone");
                                    String token = object.getString("ship_order_token");
                                    String price = object.getString("ship_cost");
                                    String name = object.getString("ship_user_name");
                                    String address = object.getString("ship_address");
                                    String phone = object.getString("ship_phone");
                                    String status = object.getString("ship_status");
                                    String destroyed = object.getString("ship_destroyed");
                                    String process_date = object.getString("ship_process_date");
                                    String delivery_date = object.getString("ship_process_date");

                                    if(status.equals("1") && destroyed.equals("1")){
                                        orderRequestList.add(new RequestItem(id, token, price, name, address, phone, process_date, delivery_date, agent_name, agent_phone, true, true));
                                    }else if(status.equals("1") && destroyed.equals("0")){
                                        orderRequestList.add(new RequestItem(id, token, price, name, address, phone, process_date, delivery_date, agent_name, agent_phone, true, false));
                                    }else if(status.equals("0") && destroyed.equals("1")){
                                        orderRequestList.add(new RequestItem(id, token, price, name, address, phone, process_date, delivery_date, agent_name, agent_phone, false, true));
                                    }else {
                                        orderRequestList.add(new RequestItem(id, token, price, name, address, phone, process_date, delivery_date, agent_name, agent_phone, false, false));
                                    }
                                }
                                mutableOrderRequestList.setValue(orderRequestList);
                            }

                        }catch (JSONException jsonException){
                            jsonException.printStackTrace();
                        }

                        Log.d("purchaseHistoryRepo: ",response);

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
                params.put("email",user.getEmail());

                return params;
            }
        };

        // Add the request to the RequestQueue.
        queue.add(stringRequest);

    }

    public static void requestCancelOrderFromPurchase(RequestItem requestItem){
        System.out.println("shipment id : "+ requestItem.getId());

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url ="https://server9.dhakawebhost.com/~aarotmel/aarotmela/shipment_req_accept.php";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);

                            //System.out.println(jsonObject);
                            String success = jsonObject.getString("success");
                            if(success.equals("1")){
                                if (mutableOrderRequestList.getValue() == null) {
                                    return;
                                }

                                reloadOrderRequestHistory();
                                Toast.makeText(getApplicationContext(),"Order Delivered Successfully.",Toast.LENGTH_SHORT).show();
                            }

                        }catch (JSONException jsonException){
                            jsonException.printStackTrace();
                        }

                        //Log.d("Data: ",response);

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

                params.put("ship_id", requestItem.getId());

                return params;
            }
        };

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    public static void reloadOrderRequestHistory(){
        List<RequestItem> reqeustItemList = new ArrayList<>(mutableOrderRequestList.getValue());
        loadOrderRequestHistories();
        mutableOrderRequestList.setValue(reqeustItemList);
    }
}

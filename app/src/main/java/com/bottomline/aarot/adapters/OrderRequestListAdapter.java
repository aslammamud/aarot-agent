package com.bottomline.aarot.adapters;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bottomline.aarot.databinding.OrderRequestRowBinding;
import com.bottomline.aarot.models.RequestItem;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import static com.facebook.FacebookSdk.getApplicationContext;

public class OrderRequestListAdapter extends ListAdapter<RequestItem, OrderRequestListAdapter.OrderRequestViewHolder> {

    OrderRequestInterface orderRequestInterface;
    public OrderRequestListAdapter(OrderRequestInterface orderRequestInterface) {
        super(RequestItem.itemCallback);
        this.orderRequestInterface = orderRequestInterface;
    }

    @NonNull
    @Override
    public OrderRequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        OrderRequestRowBinding orderRequestRowBinding = OrderRequestRowBinding.inflate(layoutInflater,parent,false);
        orderRequestRowBinding.setOrderRequestInterface(orderRequestInterface);

        return new OrderRequestViewHolder(orderRequestRowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderRequestViewHolder holder, int position) {
        RequestItem requestItem = getItem(position);

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url ="https://server9.dhakawebhost.com/~aarotmel/aarotmela/shipment_user_info.php";

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
                                holder.orderRequestRowBinding.customerName.setText(jsonObject.getString("user_name"));
                                holder.orderRequestRowBinding.orderDeliveryDate.setText("Delivered: "+jsonObject.getString("order_delivery_date"));
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


        if (!requestItem.getStatus() && requestItem.getDestroyed()){
            holder.orderRequestRowBinding.orderStatus.setText("Order Canceled");
            holder.orderRequestRowBinding.orderStatus.setTextColor(Color.parseColor("#ff0000"));

            holder.orderRequestRowBinding.acceptOrderButton.setEnabled(false);
            holder.orderRequestRowBinding.acceptOrderButton.setTextColor(Color.parseColor("#6F6A6566"));
        }else{
            if(requestItem.getStatus()){
                holder.orderRequestRowBinding.acceptOrderButton.setEnabled(false);
                holder.orderRequestRowBinding.acceptOrderButton.setText("Delivered");
                holder.orderRequestRowBinding.acceptOrderButton.setTextColor(Color.parseColor("#6F6A6566"));
                holder.orderRequestRowBinding.orderStatus.setText("Order Delivered");
                holder.orderRequestRowBinding.orderStatus.setTextColor(Color.parseColor("#00C853"));
            }
        }


        holder.orderRequestRowBinding.setShipment(requestItem);
    }

    class OrderRequestViewHolder extends RecyclerView.ViewHolder{

        OrderRequestRowBinding orderRequestRowBinding;

        public OrderRequestViewHolder(OrderRequestRowBinding binding) {
            super(binding.getRoot());
            this.orderRequestRowBinding = binding;

            orderRequestRowBinding.acceptOrderButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    orderRequestInterface.requestCancel(getItem(getAdapterPosition()));
                }
            });
        }
    }

    public interface OrderRequestInterface {
        void requestCancel(RequestItem requestItem);
        void onItemClick(RequestItem requestItem);
    }
}

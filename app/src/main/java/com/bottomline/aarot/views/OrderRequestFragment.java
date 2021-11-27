package com.bottomline.aarot.views;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;

import com.bottomline.aarot.R;
import com.bottomline.aarot.adapters.OrderRequestListAdapter;
import com.bottomline.aarot.databinding.FragmentOrderRequestBinding;
import com.bottomline.aarot.models.RequestItem;
import com.bottomline.aarot.viewmodels.ShopViewModel;

import java.util.List;

public class OrderRequestFragment extends Fragment implements OrderRequestListAdapter.OrderRequestInterface{

    FragmentOrderRequestBinding fragmentOrderRequestBinding;
    private OrderRequestListAdapter orderRequestListAdapter;
    private ShopViewModel shopViewModel;
    private NavController navController;

    public OrderRequestFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentOrderRequestBinding = FragmentOrderRequestBinding.inflate(inflater,container,false);

        return fragmentOrderRequestBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        orderRequestListAdapter = new OrderRequestListAdapter(this);
        fragmentOrderRequestBinding.purchaseRecyclerView.setAdapter(orderRequestListAdapter);
        fragmentOrderRequestBinding.purchaseRecyclerView.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));
        shopViewModel = new ViewModelProvider(requireActivity()).get(ShopViewModel.class);
        shopViewModel.getPurchaseHistories().observe(getViewLifecycleOwner(), new Observer<List<RequestItem>>() {
            @Override
            public void onChanged(List<RequestItem> requestItems) {
                orderRequestListAdapter.submitList(requestItems);
            }
        });

        navController = Navigation.findNavController(view);
    }

    @Override
    public void requestCancel(RequestItem requestItem) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this.getContext());
        alertDialogBuilder.setMessage("Do you really want to cancel this order?");
                alertDialogBuilder.setPositiveButton("yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                //Toast.makeText(getContext(),"You clicked yes button",Toast.LENGTH_LONG).show();
                                shopViewModel.requestCancelOrder(requestItem);
                            }
                        });

        alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                //Toast.makeText(getContext(),"You clicked no button",Toast.LENGTH_LONG).show();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    @Override
    public void onItemClick(RequestItem requestItem) {
        shopViewModel.setOrderRequest(requestItem);
        navController.navigate(R.id.action_purchaseFragment_to_purchaseDetailFragment);
    }

}
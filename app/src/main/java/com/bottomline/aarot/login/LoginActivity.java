package com.bottomline.aarot.login;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bottomline.aarot.R;
import com.bottomline.aarot.views.HomeActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private EditText loginEmail, loginPass;
    private Button login, loginCancel;
    private ProgressDialog loadingBar;
    private static final int Request_code = 1011;

    FirebaseAuth mFirebaseAuth;
    FirebaseUser mUser;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().hide();

        //Paper.init();

        mFirebaseAuth = FirebaseAuth.getInstance();
        mUser = mFirebaseAuth.getCurrentUser();

        loginEmail = findViewById(R.id.loginEmailEditText);
        loginPass = findViewById(R.id.loginPassEditText);
        login = findViewById(R.id.login);
        loadingBar = new ProgressDialog(LoginActivity.this);
        loginCancel = findViewById(R.id.loginCancel);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Intent intent = new Intent(LoginActivity.this, DoctorHomeActivity.class);
                startActivity(intent);*/
                //System.out.println("hello");
                LoginAllow();
            }
        });
        loginCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginEmail.setText(null);
                loginPass.setText(null);
            }
        });

    }


    public void LoginAllow() {
        final String AgentEmail = loginEmail.getText().toString();
        final String AgentPass = loginPass.getText().toString();

        if (TextUtils.isEmpty(AgentEmail)) {
            Toast.makeText(LoginActivity.this, "Please enter valid email.", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(AgentPass)) {
            Toast.makeText(LoginActivity.this, "Please enter valid password.", Toast.LENGTH_SHORT).show();
        } else {
            //loadingBar.setTitle("Login Account");
            // loadingBar.setMessage("Please wait, while we are checking the credentials.");
            //loadingBar.setCanceledOnTouchOutside(false);
            //loadingBar.show();

            AllowAccount(AgentEmail, AgentPass);
        }
    }
    public void AllowAccount(final String AgentEmail, final String AgentPass) {

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url ="https://server9.dhakawebhost.com/~aarotmel/aarotmela/agent_check.php";


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
                                Toast.makeText(getApplicationContext(),"hey" ,Toast.LENGTH_SHORT).show();
                                if(mUser==null){
                                    updateUICreateUser(AgentEmail, AgentPass);
                                }

                                updateUISignInUser();

                            }else{
                                // in-case no agent found
                                Toast.makeText(LoginActivity.this,"Provide correct email and password.",Toast.LENGTH_SHORT).show();
                            }

                        }catch (JSONException jsonException){
                            jsonException.printStackTrace();
                        }

                        Log.d("Data: ",response);

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

                params.put("email",AgentEmail);
                params.put("password",AgentPass);

                return params;
            }
        };

        // Add the request to the RequestQueue.
        queue.add(stringRequest);


/*final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();
        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(parentDbName).child(UserPhone).exists()) {
                    AgentItem agentData = dataSnapshot.child(parentDbName).child(UserPhone).getValue(AgentItem.class);
                    if (agentData.getPhone().equals(UserPhone) || agentData.getId().equals(UserPhone)) {
                        if (agentData.getPassword().equals(UserPass)) {
                           if (parentDbName.equals("Agent")) {

                                Toast.makeText(LoginActivity.this, "logged in Successfully...", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();

                                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                finish();
                                startActivity(intent);

                            }

                        } else {
                            loadingBar.dismiss();
                            Toast.makeText(LoginActivity.this, "Password is incorrect", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "Account with this " + UserPhone + " number do not exists.", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/

    }

    private void updateUISignInUser() {
        final String AgentEmail = loginEmail.getText().toString();
        final String AgentPass = loginPass.getText().toString();

        mFirebaseAuth.signInWithEmailAndPassword(AgentEmail, AgentPass)
                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                            FirebaseUser user = mFirebaseAuth.getCurrentUser();
                            updateUI(user);
                            finish();
                            startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.d( "signInWithEmail: ", task.getException().toString());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }


    private void updateUICreateUser(String AgentEmail, String AgentPass) {

        mFirebaseAuth.createUserWithEmailAndPassword(AgentEmail, AgentPass)
                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mFirebaseAuth.getCurrentUser();
                            updateUI(user);
                        }
                    }
                });
    }

    private void updateUI(FirebaseUser user) {
        if(user!=null){
            mUser = user;
        }
    }



    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        LoginActivity.this.finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

    }
}

package com.example.onlinelearning12rpl;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {

    EditText txtemail;
    EditText txtpassword;
    Button btnlogin;
    TextView tvregister;

    private ProgressDialog PB;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PB = new ProgressDialog(this);

        txtemail = findViewById(R.id.txtusername);
        txtpassword = findViewById(R.id.txtpassword1);
        btnlogin = findViewById(R.id.btnlogin);
        tvregister = findViewById(R.id.tvregister);


        sp = getSharedPreferences("login",MODE_PRIVATE);
        sp.edit().putString("logged", sp.getString("logged", "missing")).apply();

        String admin = sp.getString("logged", "missing");
        String customer = sp.getString("logged", "missing");

        if(customer.equals("customer")){
            Intent intent = new Intent(MainActivity.this, CustomerActivity.class);
            startActivity(intent);
            finish();
        }else if (admin.equals("admin")){
            Intent intent = new Intent(MainActivity.this, AdminActivity.class);
            startActivity(intent);
            finish();
        }

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = txtemail.getText().toString();
                String password = txtpassword.getText().toString();
                PB.setTitle("Login...");
                PB.show();
                AndroidNetworking.post("http://192.168.43.79/login_costumer/Login.php")
                        .addBodyParameter("email" , email)
                        .addBodyParameter("password" , password)
                        .setPriority(Priority.LOW)
                        .build()
                        .getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.d("hasil", "onResponse: ");
                                try {
                                    JSONObject PAYLOAD = response.getJSONObject("PAYLOAD");
                                    boolean sukses = PAYLOAD.getBoolean("respon");
                                    String roleuser = PAYLOAD.getString("roleuser");
                                    Log.d("PAYLOAD", "onResponse: " + PAYLOAD);
                                    if (sukses && roleuser.equals("admin")) {
                                        sp.edit().putString("logged","admin").apply();
                                        Intent intent = new Intent(MainActivity.this, AdminActivity.class);
                                        startActivity(intent);
                                        finish();
                                        PB.dismiss();
                                    } else if (sukses && roleuser.equals("customer")){
                                        sp.edit().putString("logged","customer").apply();
                                        Intent intent = new Intent(MainActivity.this, CustomerActivity.class);
                                        startActivity(intent);
                                        finish();
                                        PB.dismiss();
                                    } else {
                                        Toast.makeText(MainActivity.this, "gagal", Toast.LENGTH_SHORT).show();
                                        PB.dismiss();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onError(ANError anError) {
                                PB.dismiss();
                                Log.d("test", "onError: " + anError.getErrorDetail());
                                Log.d("test", "onError: " + anError.getErrorBody());
                                Log.d("test", "onError: " + anError.getErrorCode());
                            }
                        });

            }

        });

        tvregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Registrasi.class);
                startActivity(intent);
            }
        });
    }



    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage("Do you want to Exit?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent a = new Intent(Intent.ACTION_MAIN);
                a.addCategory(Intent.CATEGORY_HOME);
                a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(a);                    }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

}
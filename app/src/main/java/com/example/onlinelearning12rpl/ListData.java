package com.example.onlinelearning12rpl;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ListData extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DataAdapter adapter;
    private ArrayList<Model> DataArrayList; //kit add kan ke adapter
    private ImageView tambah_data;

    ArrayList<Model> datalist;

    CardView cardview;

    TextView txtnama, txtemail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_data);
        txtnama = findViewById(R.id.tvname);
        txtemail = findViewById(R.id.tvdesc);

        cardview = findViewById(R.id.cardku);
        recyclerView = findViewById(R.id.rvdata);

//        Toolbar toolbar = findViewById(R.id.toolbar);
//
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setTitle("Data Customer");
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getData();

    }

    private void getData() {
        datalist = new ArrayList<>();
        Log.d("test", "onCreate: ");

        AndroidNetworking.post("http://192.168.43.79/login_costumer/getAllCustomer.php")
                .addBodyParameter("roleuser", "2")
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray data = response.getJSONArray("result");

                            for (int i = 0; i < data.length(); i++) {

                                Model model = new Model();
                                JSONObject object = data.getJSONObject(i);
                                model.setId(object.getString("id"));
                                model.setEmail(object.getString("email"));
                                model.setNama(object.getString("nama"));
                                model.setNohp(object.getString("nohp"));
                                model.setAlamat(object.getString("alamat"));
                                model.setNoktp(object.getString("noktp"));
                                datalist.add(model);

                            }

                            adapter = new DataAdapter(datalist);

                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ListData.this);

                            recyclerView.setLayoutManager(layoutManager);

                            recyclerView.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d("test", "onResponse: " + anError.toString());
                        Log.d("test", "onResponse: " + anError.getErrorBody());
                        Log.d("test", "onResponse: " + anError.getErrorCode());
                        Log.d("test", "onResponse: " + anError.getErrorDetail());
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 23 && data.getStringExtra("refresh") != null) {
            //refresh list
            getData();
            Toast.makeText(this, "test", Toast.LENGTH_SHORT).show();

        }
    }

}
package com.example.onlinelearning12rpl;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONException;
import org.json.JSONObject;

public class EditActivity extends AppCompatActivity {
    Button btnUpdate;
    EditText edEmail, edNmaLengkap, edNoKtp, edNoHp, edAlamat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        edEmail = findViewById(R.id.edEmail1);
        edNmaLengkap = findViewById(R.id.edNmaLengkap1);
        edNoKtp = findViewById(R.id.edNoKtp1);
        edNoHp = findViewById(R.id.edNoHp1);
        edAlamat = findViewById(R.id.edAlamat1);
        btnUpdate = findViewById(R.id.btnUpdate);

        Bundle extras = getIntent().getExtras();

        final String id = extras.getString("id");
        final String email = extras.getString("email");
        final String nama = extras.getString("nama");
        final String noktp = extras.getString("noktp");
        final String nohp = extras.getString("nohp");
        final String alamat = extras.getString("alamat");

        edEmail.setText(email);
        edNmaLengkap.setText(nama);
        edNoKtp.setText(noktp);
        edNoHp.setText(nohp);
        edAlamat.setText(alamat);


        Toolbar toolbar = findViewById(R.id.toolbar);

//        setSupportActionBar(toolbar);
//        getSupportActionBar().setTitle("Data Customer");
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AndroidNetworking.post("http://192.168.6.204/PTS/EditCustomer.php")
                        .addBodyParameter("id", id)
                        .addBodyParameter("email", edEmail.getText().toString())
                        .addBodyParameter("nama", edNmaLengkap.getText().toString())
                        .addBodyParameter("noktp", edNoKtp.getText().toString())
                        .addBodyParameter("nohp", edNoHp.getText().toString())
                        .addBodyParameter("alamat", edAlamat.getText().toString())
                        .setPriority(Priority.LOW)
                        .build()
                        .getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.e("", response.toString());
                                try {
                                    JSONObject hasil = response.getJSONObject("hasil");
                                    boolean sukses = hasil.getBoolean("respon");
                                    if (sukses) {
                                        Intent returnIntent = new Intent(EditActivity.this, AdminActivity.class);
                                        returnIntent.putExtra("refresh", "refresh");
                                        setResult(23, returnIntent);
                                        finish();
                                        Toast.makeText(EditActivity.this, "Edit Suskses", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(EditActivity.this, "Edit gagal", Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    System.out.println("DEA" + e.getMessage());
                                    Toast.makeText(EditActivity.this, "Edit gagal", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onError(ANError anError) {
                                Toast.makeText(EditActivity.this, anError.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

            }
        });

    }
}
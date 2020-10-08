package com.example.onlinelearning12rpl;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Detail extends AppCompatActivity {

    TextView tvId, tvNama, tvEmail, tvNohp, tvAlamat, tvNoktp;
    Button btnedit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        tvId = findViewById(R.id.tvId);
        tvNama = findViewById(R.id.tvNama);
        tvEmail = findViewById(R.id.tvEmail);
        tvNohp = findViewById(R.id.tvNohp);
        tvAlamat = findViewById(R.id.tvAlamat);
        tvNoktp = findViewById(R.id.tvNoktp);
        btnedit = findViewById(R.id.btnedit);


//        Toolbar toolbar = findViewById(R.id.toolbar);
//
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setTitle("Info Customer");
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle extras = getIntent().getExtras();
        final String id = extras.getString("id");
        final String nama = extras.getString("nama");
        final String email = extras.getString("email");
        final String nohp = extras.getString("nohp");
        final String alamat = extras.getString("alamat");
        final String noktp = extras.getString("noktp");

        tvId.setText("Id           : " + id);
        tvNama.setText("Nama      : " + nama);
        tvEmail.setText("Email      : " + email);
        tvNohp.setText("No Hp     : " + nohp);
        tvAlamat.setText("Alamat    : " + alamat);
        tvNoktp.setText("No KTP    : " + noktp);

        btnedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(Detail.this, EditActivity.class);
                in.putExtra("id", id);
                in.putExtra("nama", nama);
                in.putExtra("email", email);
                in.putExtra("nohp", nohp);
                in.putExtra("alamat", alamat);
                in.putExtra("noktp", noktp);
                startActivityForResult(in, 23);
            }
        });
    }
    }

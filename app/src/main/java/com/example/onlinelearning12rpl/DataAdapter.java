package com.example.onlinelearning12rpl;

import android.content.Intent;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.UserViewHolder> {

    private ArrayList<Model> dataList;
    View viewku;

    public DataAdapter(ArrayList<Model> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        viewku = layoutInflater.inflate(R.layout.adapterrv, parent, false);
        return new UserViewHolder(viewku);

    }

    @Override
    public void onBindViewHolder(@NonNull final UserViewHolder holder, final int position) {
        holder.txtemail.setText(dataList.get(position).getEmail());
        holder.txtnama.setText(dataList.get(position).getNama());
        holder.cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent dea = new Intent(holder.itemView.getContext(), EditActivity.class);
                dea.putExtra("id", dataList.get(position).getId());
                dea.putExtra("nama", dataList.get(position).getNama());
                dea.putExtra("email", dataList.get(position).getEmail());
                dea.putExtra("nohp", dataList.get(position).getNohp());
                dea.putExtra("alamat", dataList.get(position).getAlamat());
                dea.putExtra("noktp", dataList.get(position).getNoktp());
                holder.itemView.getContext().startActivity(dea);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class UserViewHolder extends RecyclerView.ViewHolder{
        private TextView txtemail, txtnama;
        CardView cardview;

        UserViewHolder(View itemView) {
            super(itemView);
            cardview = itemView.findViewById(R.id.cardku);
            txtemail = itemView.findViewById(R.id.tvname);
            txtnama = itemView.findViewById(R.id.tvdesc);
        }
    }

}
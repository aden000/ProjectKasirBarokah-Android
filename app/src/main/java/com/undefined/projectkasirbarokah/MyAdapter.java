package com.undefined.projectkasirbarokah;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyHolder> {

    Context context;
    ArrayList<DataBarang> dataBarang;

    public MyAdapter(Context context, ArrayList<DataBarang> dataBarang) {
        this.context = context;
        this.dataBarang = dataBarang;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, null);
        return new MyHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holder.mNamaBarang.setText(dataBarang.get(position).getNama_barang());
        holder.mNamaKategori.setText(dataBarang.get(position).getNama_kategori());
        holder.mHargaBarang.setText(dataBarang.get(position).getHarga_barang());

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClickListener(View v, int pos) {
                Intent i = new Intent(context, InfoBarangActivity.class);
                i.putExtra("id_barang", dataBarang.get(pos).getId_barang());
                i.putExtra("nama_barang" , dataBarang.get(pos).getNama_barang());
                i.putExtra("nama_kategori" , dataBarang.get(pos).getNama_kategori());
                i.putExtra("harga_barang", dataBarang.get(pos).getHarga_barang());
                i.putExtra("stok_barang", dataBarang.get(pos).getStok_barang());

                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataBarang.size();
    }
}

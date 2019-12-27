package com.undefined.projectkasirbarokah;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    TextView mNamaBarang, mNamaKategori, mHargaBarang;
    ItemClickListener itemClickListener;

    public MyHolder(@NonNull View itemView) {
        super(itemView);

        this.mNamaBarang = itemView.findViewById(R.id.nama_barangTVrow);
        this.mNamaKategori = itemView.findViewById(R.id.nama_kategoriTVrow);
        this.mHargaBarang = itemView.findViewById(R.id.harga_barangTVrow);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        this.itemClickListener.onItemClickListener(view, getLayoutPosition());
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
}

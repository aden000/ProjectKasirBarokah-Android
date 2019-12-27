package com.undefined.projectkasirbarokah;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataKategori {
    @SerializedName("ID_KATEGORI")
    @Expose
    private int id_kategori;

    @SerializedName("NAMA_KATEGORI")
    @Expose
    private String nama_kategori;

    public int getId_kategori() {
        return id_kategori;
    }

    public void setId_kategori(int id_kategori) {
        this.id_kategori = id_kategori;
    }

    public String getNama_kategori() {
        return nama_kategori;
    }

    public void setNama_kategori(String nama_kategori) {
        this.nama_kategori = nama_kategori;
    }
}

package com.undefined.projectkasirbarokah;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataBarang {
    @SerializedName("id_barang")
    @Expose
    private String id_barang;
    @SerializedName("nama_kategori")
    @Expose
    private String nama_kategori;
    @SerializedName("nama_barang")
    @Expose
    private String nama_barang;
    @SerializedName("harga_barang")
    @Expose
    private String harga_barang;
    @SerializedName("stok_barang")
    @Expose
    private String stok_barang;

    public String getId_barang() {
        return id_barang;
    }

    public void setId_barang(String id_barang) {
        this.id_barang = id_barang;
    }

    public String getNama_kategori() {
        return nama_kategori;
    }

    public void setNama_kategori(String nama_kategori) {
        this.nama_kategori = nama_kategori;
    }

    public String getNama_barang() {
        return nama_barang;
    }

    public void setNama_barang(String nama_barang) {
        this.nama_barang = nama_barang;
    }

    public String getHarga_barang() {
        return harga_barang;
    }

    public void setHarga_barang(String harga_barang) {
        this.harga_barang = harga_barang;
    }

    public String getStok_barang() {
        return stok_barang;
    }

    public void setStok_barang(String stok_barang) {
        this.stok_barang = stok_barang;
    }
}

package com.undefined.projectkasirbarokah;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MenuUtama extends AppCompatActivity {

    //ProgressDialog pDialog;
    RecyclerView recyclerView;
    MyAdapter adapter;
    ProgressBar progressBar;
    Button btnSync;
    static String whoisthis;

    static ArrayList<String> dataKategori;
    Spinner spinner;

    private String base_url = "http://192.168.43.172/pkb_rest/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_utama);

        Intent i = getIntent();
        whoisthis = i.getStringExtra("userName");

        TextView tv1 = findViewById(R.id.whoIsThis);
        Button aib = findViewById(R.id.addItemButton);
        btnSync = findViewById(R.id.btnSync);
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        progressBar = findViewById(R.id.progressbar);
        spinner = findViewById(R.id.spinner_kategori);

        dataKategori = new ArrayList<>();
        getAllKategori();

        //listBarang = new ArrayList<>();
        progressBar.setVisibility(View.VISIBLE);
        getAllData();

        tv1.setText("Dashboard: " + whoisthis);
        aib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder adb = new AlertDialog.Builder(MenuUtama.this);
                View view1 = getLayoutInflater().inflate(R.layout.layout_addbarang, null);
                Spinner spinner = view1.findViewById(R.id.spinner_kategori);
                ArrayAdapter<String> aa = new ArrayAdapter<>(view1.getContext(), R.layout.layout_spinner_kategori, R.id.layoutSpinnerKategoriTxt, dataKategori);
                spinner.setAdapter(aa);

                adb.setTitle("Tambah Barang");
                adb.setView(view1);
                adb.setPositiveButton("Tambahkan", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String id_barang = String.valueOf(recyclerView.getAdapter().getItemCount()+1);
                        String id_kategori = String.valueOf(spinner.getSelectedItemPosition()+1);
                        EditText etNamaBarang = view1.findViewById(R.id.etnamabarang);
                        String nama_barang = etNamaBarang.getText().toString();
                        EditText etHargaBarang = view1.findViewById(R.id.ethargabarang);
                        String harga_barang = etHargaBarang.getText().toString();
                        EditText etStokBarang = view1.findViewById(R.id.etstokbarang);
                        String stok_barang = etStokBarang.getText().toString();

                        progressBar.setVisibility(View.VISIBLE);
                        insertNewBarang(id_barang, id_kategori, nama_barang, harga_barang, stok_barang);
                    }
                });

                AlertDialog alert = adb.create();
                alert.show();
            }
        });

        btnSync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerView.invalidate();
                //recyclerView.removeAllViews();
                progressBar.setVisibility(View.VISIBLE);
                getAllData();
                getAllKategori();
            }
        });

    }

    private void getAllKategori() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(base_url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitClassAPI retrofitClassAPI = retrofit.create(RetrofitClassAPI.class);
        Call<MeGetKategoriResponse> response = retrofitClassAPI.getMeKategoriResponse();
        response.enqueue(new Callback<MeGetKategoriResponse>() {
            @Override
            public void onResponse(Call<MeGetKategoriResponse> call, Response<MeGetKategoriResponse> response) {
                ArrayList<DataKategori> dataKategori = response.body().getData();
                ArrayList<String> dataNamaKategori = new ArrayList<>();
                for(int i = 0; i < dataKategori.size(); i++){
                    dataNamaKategori.add(dataKategori.get(i).getNama_kategori());
                }
                Log.d("PKB", "onResponse: " + dataNamaKategori.get(0));
                MenuUtama.this.dataKategori.addAll(dataNamaKategori);
            }

            @Override
            public void onFailure(Call<MeGetKategoriResponse> call, Throwable t) {
                Toast.makeText(MenuUtama.this, "Failed to get Kategori\nPlease do Synchronize\nError : " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    /**
     * adapter = new MyAdapter(MenuUtama.this, temp);
     * recyclerView.setAdapter(adapter);
     * progressBar.setVisibility(View.GONE);
     */

    private void getAllData() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(base_url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitClassAPI retrofitClassAPI = retrofit.create(RetrofitClassAPI.class);
        Call<MeGetResponse> response = retrofitClassAPI.getMeGetResponse();
        response.enqueue(new Callback<MeGetResponse>() {
            @Override
            public void onResponse(Call<MeGetResponse> call, retrofit2.Response<MeGetResponse> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(MenuUtama.this, "Failed\nHTTPCode: " + response.code(), Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                    return;
                }
                adapter = new MyAdapter(MenuUtama.this, response.body().getData());
                recyclerView.setAdapter(adapter);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<MeGetResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(MenuUtama.this, "Check your connection!\nPlease do Synchronize\nError: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void insertNewBarang(String id_barang, String id_kategori, String nama_barang, String harga_barang, String stok_barang){
        /*Log.d("PKB", "insertNewBarang: \nid_barang = " + id_barang +
                "\nid_kategori = " + id_kategori +
                "\nnama_barang = " + nama_barang +
                "\nharga_barang = " + harga_barang +
                "\nstok_barang = " + stok_barang);*/
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(base_url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitClassAPI retrofitClassAPI = retrofit.create(RetrofitClassAPI.class);
        Call<Void> response = retrofitClassAPI.getMePoResponse(id_barang, id_kategori, nama_barang, harga_barang, stok_barang);
        response.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                progressBar.setVisibility(View.GONE);
                if(!response.isSuccessful()){
                    Toast.makeText(MenuUtama.this, "Failed to add barang!\nResponse: " + response.code(), Toast.LENGTH_LONG).show();
                    return;
                }

                Toast.makeText(MenuUtama.this, "Successfully added!", Toast.LENGTH_LONG).show();
                getAllData();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(MenuUtama.this, "Check your Connection!", Toast.LENGTH_LONG).show();
            }
        });

    }
}

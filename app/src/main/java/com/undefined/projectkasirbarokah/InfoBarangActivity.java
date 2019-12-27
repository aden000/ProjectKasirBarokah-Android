package com.undefined.projectkasirbarokah;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class InfoBarangActivity extends AppCompatActivity {

    TextView mIDBarang, mNamaBarang, mNamaKategori, mHargaBarang, mStokBarang;
    Button btnUbah, btnHapus;
    HashMap editData, postData;
    ProgressBar progressBar;

    private String base_url = "http://192.168.43.172/pkb_rest/";

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_barang);

        Intent i = getIntent();
        btnUbah = findViewById(R.id.btnUbahInfo);
        btnHapus = findViewById(R.id.btnHapusInfo);

        mIDBarang = findViewById(R.id.id_barangTVinfo);
        mNamaBarang = findViewById(R.id.nama_barangTVinfo);
        mNamaKategori = findViewById(R.id.nama_kategoriTVinfo);
        mHargaBarang = findViewById(R.id.harga_barangTVinfo);
        mStokBarang = findViewById(R.id.stok_barangTVinfo);

        progressBar = findViewById(R.id.progressbarInfoBarang);
        progressBar.setVisibility(ProgressBar.GONE);

        mIDBarang.setText(i.getStringExtra("id_barang"));
        mNamaBarang.setText(i.getStringExtra("nama_barang"));
        mNamaKategori.setText(i.getStringExtra("nama_kategori"));
        mHargaBarang.setText(i.getStringExtra("harga_barang"));
        mStokBarang.setText(i.getStringExtra("stok_barang"));

        btnUbah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder adb = new AlertDialog.Builder(InfoBarangActivity.this);
                View view1 = getLayoutInflater().inflate(R.layout.layout_addbarang, null);

                Spinner spinner = view1.findViewById(R.id.spinner_kategori);
                ArrayAdapter<String> aa = new ArrayAdapter<>(view1.getContext(), R.layout.layout_spinner_kategori, R.id.layoutSpinnerKategoriTxt, MenuUtama.dataKategori);
                spinner.setAdapter(aa);

                EditText etStokBarang = view1.findViewById(R.id.etstokbarang);
                EditText etNamaBarang = view1.findViewById(R.id.etnamabarang);
                EditText etHargaBarang = view1.findViewById(R.id.ethargabarang);

                for(int i = 0; i < spinner.getAdapter().getCount(); i++){
                    if(mNamaKategori.getText().toString().equals(spinner.getAdapter().getItem(i).toString())){
                        spinner.setSelection(i);
                        break;
                    }
                }

                etNamaBarang.setText(mNamaBarang.getText().toString());
                etHargaBarang.setText(mHargaBarang.getText().toString());
                etStokBarang.setVisibility(View.GONE);

                adb.setView(view1);
                adb.setTitle("Edit Barang")
                        .setPositiveButton("Edit", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String id = mIDBarang.getText().toString();
                                String id_kategori =String.valueOf(spinner.getSelectedItemPosition()+1);
                                String nama_barang = etNamaBarang.getText().toString();
                                String harga_barang = etHargaBarang.getText().toString();

                                progressBar.setVisibility(View.VISIBLE);
                                editBarang(id, id_kategori, nama_barang, harga_barang);
                            }
                        });

                AlertDialog alertDialog = adb.create();
                alertDialog.show();
            }
        });

        btnHapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder adb = new AlertDialog.Builder(InfoBarangActivity.this);
                adb.setMessage("Apakah anda yakin untuk menghapus data ini?").setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        progressBar.setVisibility(ProgressBar.VISIBLE);
                        deleteData();
                    }
                }).setNegativeButton("NO", null);
                AlertDialog alertDialog = adb.create();
                alertDialog.show();
            }
        });
    }

    private void editBarang(String where_id, String id_kategori, String nama_barang, String harga_barang) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(base_url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitClassAPI retrofitClassAPI = retrofit.create(RetrofitClassAPI.class);
        Call<Void> call = retrofitClassAPI.getMePutResponse(where_id, id_kategori, nama_barang, harga_barang);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                progressBar.setVisibility(View.GONE);
                if(!response.isSuccessful()){
                    Toast.makeText(InfoBarangActivity.this, "Failed to edit barang\ncode: " + response.code(), Toast.LENGTH_LONG).show();
                    return;
                }

                Toast.makeText(InfoBarangActivity.this, "Successfully edit barang", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(InfoBarangActivity.this, MenuUtama.class);
                intent.putExtra("userName", MenuUtama.whoisthis);
                //intent.putExtra("recreate", true);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(InfoBarangActivity.this, "Failed to edit barang\nCheck your connection\nError: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    /*

    TODO: CHANGE ALL TO RETROFIT
        DONE

     */
    private void deleteData(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(base_url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitClassAPI retrofitClassAPI = retrofit.create(RetrofitClassAPI.class);
        Call<Void> call = retrofitClassAPI.getMeDelResponse(Integer.parseInt(mIDBarang.getText().toString()));
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                progressBar.setVisibility(ProgressBar.GONE);
                Toast.makeText(InfoBarangActivity.this, "Successfully DELETED your barang", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(InfoBarangActivity.this, MenuUtama.class);
                intent.putExtra("userName", MenuUtama.whoisthis);
                //intent.putExtra("recreate", true);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                progressBar.setVisibility(ProgressBar.GONE);
                Toast.makeText(InfoBarangActivity.this, "Failed to delete...\n" + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}

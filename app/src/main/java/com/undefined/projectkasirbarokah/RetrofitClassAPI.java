package com.undefined.projectkasirbarokah;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface RetrofitClassAPI {

    @GET("RestBarang")
    Call<MeGetResponse> getMeGetResponse();

    @DELETE("RestBarang/{id}")
    Call<Void> getMeDelResponse(@Path("id") int id);

    @FormUrlEncoded
    @POST("RestBarang")
    Call<Void> getMePoResponse(@Field("id_barang") String id_barang,
                               @Field("id_kategori") String id_kategori,
                               @Field("nama_barang") String nama_barang,
                               @Field("harga_barang") String harga_barang,
                               @Field("stok_barang") String stok_barang);

    @FormUrlEncoded
    @PUT("RestBarang/{id}")
    Call<Void> getMePutResponse(@Path("id") String id,
                                @Field("id_kategori") String id_kategori,
                                @Field("nama_barang") String nama_barang,
                                @Field("harga_barang") String harga_barang);

    @GET("RestBarang/kategori")
    Call<MeGetKategoriResponse> getMeKategoriResponse();
}

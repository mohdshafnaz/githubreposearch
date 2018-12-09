package com.example.shaffz.mvvmgettest.model

import android.content.Context
import com.example.shaffz.mvvmgettest.view.MainActivity
import io.reactivex.Single
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

class MainModel(var mContext: Context) {
    private var mRetrofit: Retrofit? = null

    fun fetchAddress(address: String): Single<List<MainModel.ResultEntity1>>? {
        return getRetrofit()?.create(MainModel.AddressService::class.java)?.getProjectList(address)
    }


    private fun getRetrofit(): Retrofit? {
        if (mRetrofit == null) {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            val client = OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()
            mRetrofit = Retrofit.Builder().baseUrl("https://api.github.com/").addConverterFactory(GsonConverterFactory.create()).addCallAdapterFactory(RxJava2CallAdapterFactory.create()).client(client).build()
        }
        return mRetrofit
    }

   // class ResultEntity(val title: String, val rating: String, val date: String, val year: String)
    class ResultEntity1(val id: String, val name: String, val description: String, val owner: User)
    interface AddressService {


        @GET("users/{user}/repos")
         fun getProjectList(@Path("user") user: String): Single<List<ResultEntity1>>


    }


}
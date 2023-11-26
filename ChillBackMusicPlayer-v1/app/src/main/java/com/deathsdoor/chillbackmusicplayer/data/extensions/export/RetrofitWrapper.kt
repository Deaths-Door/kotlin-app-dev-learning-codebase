package com.deathsdoor.chillbackmusicplayer.data.extensions.export

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Deprecated("use from the repo one")
class RetrofitWrapper<T:Any>(private val baseURL:String, private val serviceClass: Class<T>){
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(baseURL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val service: T = retrofit.create(serviceClass)

    interface OnRequestMade<R>{
        fun onSuccess(response: Response<R>, body: R?)
        fun onError(response: Response<R>, errorBody: ResponseBody?)
        fun onFailure(call: Call<R>, throwable: Throwable)
    }
    fun <R> makeAsyncRequest(request: Call<R>,onRequestMade: OnRequestMade<R>) {
        request.enqueue(object :Callback<R> {
            override fun onResponse(call: Call<R>, response: Response<R>) =
                if(response.isSuccessful) onRequestMade.onSuccess(response,response.body())
                else onRequestMade.onError(response,response.errorBody())
            override fun onFailure(call: Call<R>, t: Throwable) = onRequestMade.onFailure(call,t)
        })
    }
    fun <R> makeSyncRequest(request: Call<R>,onRequestMade: OnRequestMade<R>) {
       val result =  request.execute()
        if(result.isSuccessful) onRequestMade.onSuccess(result,result.body())
        else onRequestMade.onError(result,result.errorBody())
    }
}
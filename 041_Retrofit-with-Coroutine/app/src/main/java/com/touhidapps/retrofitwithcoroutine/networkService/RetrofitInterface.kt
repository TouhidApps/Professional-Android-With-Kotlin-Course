package com.touhidapps.retrofitwithcoroutine.networkService

import com.touhidapps.retrofitwithcoroutine.model.ResultModel
import com.touhidapps.retrofitwithcoroutine.model.UserModel
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

@JvmSuppressWildcards  // helps to use Any in body Map param
interface RetrofitInterface {

    @POST(AllApi.USER)
    suspend fun saveUser(@Body body: Map<String, Any?>) : ResultModel

    @GET(AllApi.USER)
    suspend fun getUser(@Query("pageNo") pageNo: Int, @Query("perPageData") perPageData: Int): List<UserModel>


}
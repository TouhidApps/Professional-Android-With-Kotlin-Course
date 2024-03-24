package com.touhidapps.retrofitwithcoroutine.networkService

import com.touhidapps.retrofitwithcoroutine.model.LocationModel
import com.touhidapps.retrofitwithcoroutine.model.PhotoUploadResponse
import com.touhidapps.retrofitwithcoroutine.model.ResultModel
import com.touhidapps.retrofitwithcoroutine.model.UserModel
import com.touhidapps.retrofitwithcoroutine.model.UserUploadResponse
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Query

@JvmSuppressWildcards  // helps to use Any in body Map param
interface RetrofitInterface {

    @POST(AllApi.USER)
    suspend fun saveUser(@Body body: Map<String, Any?>) : ResultModel

    @GET(AllApi.USER)
    suspend fun getUser(@Query("pageNo") pageNo: Int, @Query("perPageData") perPageData: Int): List<UserModel>

    @PUT(AllApi.USER)
    suspend fun updateUser(@Body body: Map<String, Any?>) : ResultModel

  //  @DELETE(AllApi.USER)
    @HTTP(method = "DELETE", path = AllApi.USER, hasBody = true)
    suspend fun deleteUser(@Body body: Map<String, Any?>) : ResultModel

    @GET(AllApi.LOCATION)
    suspend fun getLocations(): List<LocationModel>

    @POST(AllApi.LOCATION)
    suspend fun saveLocation(@Body body: Map<String, Any?>): ResultModel

    @Multipart
    @POST(AllApi.UPLOAD_IMAGE)
    suspend fun uploadedPhotoData(@Part requestBody: List<MultipartBody.Part>): PhotoUploadResponse

    @GET(AllApi.UPLOAD_IMAGE)
    suspend fun getUploadedPhotoData(): List<UserUploadResponse>


}
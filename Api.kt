package com.learning.test1.rest

import com.learning.test1.models.DefaultResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * Interface API for POST
 */
interface Api {
    @FormUrlEncoded
    @POST("signUp")
    fun UserDetails(
        @Field("userName") userName:String,
        @Field("email") email:String,
        @Field("phoneNumber") phoneNumber:String,
        @Field("countryCode") countryCode:String,
        @Field("confirmPassword") confirmPassword:String,
        @Field("deviceId") deviceId:Int,
        @Field("platform") platform:Int,
        @Field("type") type:Int,
        @Field("currency") currency:String
    ):retrofit2.Call<DefaultResponse>
}
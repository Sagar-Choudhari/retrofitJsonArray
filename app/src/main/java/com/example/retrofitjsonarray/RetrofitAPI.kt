package com.example.retrofitjsonarray

import retrofit2.Call
import retrofit2.http.GET

interface RetrofitAPI {

    // as we are making get request
    // so we are displaying
    // GET as annotation.
    // and inside we are passing last parameter for our url.
    @GET("0RH6")
    fun getAllCourses(): Call<ArrayList<CourseRVModal>?>?
}
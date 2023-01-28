package com.example.retrofitjsonarray

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    // on below line we are creating variables.
    lateinit var courseRV: RecyclerView
    lateinit var loadingPB: ProgressBar
    lateinit var courseRVAdapter: CourseRVAdapter
    lateinit var courseList: ArrayList<CourseRVModal>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // on below line we are initializing
        // our variable with their ids.
        courseRV = findViewById(R.id.idRVCourses)
        loadingPB = findViewById(R.id.idPBLoading)

        // on below line we are initializing our list
        courseList = ArrayList()

        // on below line we are calling
        // get all courses method to get data.
        getAllCourses()

    }

    private fun getAllCourses() {
        // on below line we are creating a retrofit
        // builder and passing our base url
        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.jsonkeeper.com/b/")
            // on below line we are calling add
            // Converter factory as Gson converter factory.
            // at last we are building our retrofit builder.
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        // below line is to create an instance for our retrofit api class.
        val retrofitAPI = retrofit.create(RetrofitAPI::class.java)

        // on below line we are calling a method to get all the courses from API.
        val call: Call<ArrayList<CourseRVModal>?>? = retrofitAPI.getAllCourses()

        // on below line we are calling method to enqueue and calling
        // all the data from array list.
        call!!.enqueue(object : Callback<ArrayList<CourseRVModal>?> {
            override fun onResponse(
                call: Call<ArrayList<CourseRVModal>?>,
                response: Response<ArrayList<CourseRVModal>?>
            ) {
                if (response.isSuccessful) {
                    loadingPB.visibility = View.GONE
                    courseList = response.body()!!
                }

                // on below line we are initializing our adapter.
                courseRVAdapter = CourseRVAdapter(courseList)

                // on below line we are setting adapter to recycler view.
                courseRV.adapter = courseRVAdapter

            }

            override fun onFailure(call: Call<ArrayList<CourseRVModal>?>, t: Throwable) {
                // displaying an error message in toast
                Toast.makeText(this@MainActivity, "Fail to get the data..", Toast.LENGTH_LONG)
                    .show()
            }
        })
    }
}

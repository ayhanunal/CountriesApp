package com.example.kotlincountries.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.kotlincountries.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //data-binding nedir??:

        //kullanici arayuzu elementlerini observable nesleerile birbirine baglar. yapilabilmesi icin gerekli seyler:
        //1- gorunum xml lerinde layout tagi olmali
        //2- build.gradle de data-binding enabled olmali.




    }
}
package com.example.kotlincountries.util

import android.content.Context
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.kotlincountries.R

//extension --- Kotlin KTX

/*
fun String.myExtension(myParameter: String){

    //tum siniflara kendi extensionumuzu yazabiliriz. yani String sinifina boyle bir extension yazdigimizda
    //bir string olusturursak degisken_adi.myExtension("parametre") dedigimizde asagida yapilanlar yapilir yani println calisir.

    println(myParameter)

}

 */

//bizim yapmak istedigimiz her image nesnesine bir glide fonksiyonu cagirmak. -- her defasinda glide yazmamak icin.

fun ImageView.downloadFromUrl(url: String?, progressDrawable: CircularProgressDrawable){

    val options = RequestOptions()
            .placeholder(progressDrawable)
            .error(R.mipmap.ic_launcher_round)

    Glide.with(context)
            .setDefaultRequestOptions(options)
            .load(url)
            .into(this)


}

fun placeholderProgressBar(context: Context) : CircularProgressDrawable {
    return CircularProgressDrawable(context).apply {
        strokeWidth = 8f //ne kadar buyuklukte olucak
        centerRadius = 40f //yaricapi ne kadar olucak
        start()
    }
}


@BindingAdapter("android:downloadUrl")
fun downloadImage(view: ImageView, url: String?){
    view.downloadFromUrl(url, placeholderProgressBar(view.context))
}



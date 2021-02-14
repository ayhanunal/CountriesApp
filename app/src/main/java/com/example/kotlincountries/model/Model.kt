package com.example.kotlincountries.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "Country")
data class Country(

    @ColumnInfo(name = "name")
    @SerializedName("name") //apideki isim ve modeldeki isim farkli oldugi icin boyle yapiyoruz.
    val countryName: String?,

    @ColumnInfo(name = "region")
    @SerializedName("region")
    val countryRegion: String?,

    @ColumnInfo(name = "capital")
    @SerializedName("capital")
    val countryCapital: String?,

    @ColumnInfo(name = "currency")
    @SerializedName("currency")
    val countryCurrency: String?,

    @ColumnInfo(name = "language")
    @SerializedName("language")
    val countryLanguage: String?,

    @ColumnInfo(name = "flag")
    @SerializedName("flag")
    val flag: String?
    ) {

    //data class : veri tuttugumuz siniflardir. herhangi bir yerden veri cekerken cok fazla sinif olusturuldugundan sadece veri icin data class olusturulur.
    //en az 1 tane cons parametre vermemiz gerekiyor.

    @PrimaryKey(autoGenerate = true)
    var uuid: Int = 0


}
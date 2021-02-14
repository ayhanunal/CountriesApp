package com.example.kotlincountries.service

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.kotlincountries.model.Country

@Dao
interface CountryDao {

    //data access object

    @Insert
    suspend fun insertAll(vararg countries: Country) : List<Long>

    // Insert = insert into
    // suspend = coroutine, pause & resume
    // vararg = sayisi tam bilinmeyen objeyi vermek icin kullanilir. tek tek veriyoruz ama sayisi belli degil.
    // List<Long> = primaery key listesi donduruyor.


    @Query("SELECT * FROM country")
    suspend fun getAllCountries() : List<Country>

    @Query("SELECT * FROM country WHERE uuid = :countryID")
    suspend fun getCountry(countryID : Int) : Country

    @Query("DELETE FROM country")
    suspend fun deleteAllCountries()


}
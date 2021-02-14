package com.example.kotlincountries.service

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.kotlincountries.model.Country

import java.security.AccessControlContext

@Database(entities = [Country::class], version = 1)
abstract class CountryDatabase : RoomDatabase() {

    abstract fun countryDao() : CountryDao

    //Singleton mantigi ile olusturacagizz cunku farklı threadler db ye erismeye calirsa confict olacaktir.

    companion object {

        @Volatile private var instance : CountryDatabase? = null
        //@Volatile --> diger thredlere de farklı threadlere de gorunur hale geliyor.

        private val lock = Any()

        //synchronized = threadlerin ayni anda erismesini engelliyor yani farkli zamanalrda erisime izin veriyor.
        operator fun invoke(context: Context) = instance ?: synchronized(lock) {
            instance ?: makeDatabase(context).also {
                instance = it
            }
        }

        private fun makeDatabase(context: Context) = Room.databaseBuilder(
                context.applicationContext, CountryDatabase::class.java, "countrydatabase"
        ).build()

    }

}
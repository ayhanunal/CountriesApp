package com.example.kotlincountries.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlincountries.model.Country
import com.example.kotlincountries.service.CountryAPIService
import com.example.kotlincountries.service.CountryDatabase
import com.example.kotlincountries.util.CustomSharedPreferences
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class FeedViewModel(application: Application) : BaseViewModel(application) {

    private val countryAPIService = CountryAPIService()
    private val disposable = CompositeDisposable() //fragmentlar temizlendiginde yapilanlar call lardan kurtulmamiz gerekiyor. hafizayi rahatlatmak icin (Calllari disposable icine atiyoz en son disposable temizliyoruz.)

    private var customPreferences = CustomSharedPreferences(getApplication())

    private var refreshTime = 10 * 60 * 1000 * 1000 * 1000L //nano saniye cinsinden 10dk.

    val countries = MutableLiveData<List<Country>>() //mutable degistirebilir oldugu icin
    val countryError = MutableLiveData<Boolean>()
    val countryLoading = MutableLiveData<Boolean>()

    fun refreshData(){

        val updateTime = customPreferences.getTime()
        if (updateTime != null && updateTime != 0L && System.nanoTime() - updateTime < refreshTime){
            getDataFromSQLite() //10dk dan az ise sql den al
        } else{
            getDataFromAPI() //10dkdan fazla ise apiden al.

        }

    }

    fun refreshFromAPI(){
        getDataFromAPI()
    }

    private fun getDataFromSQLite() {

        countryLoading.value = true

        launch {

            val countries = CountryDatabase(getApplication()).countryDao().getAllCountries()
            showCountries(countries)
            Toast.makeText(getApplication(), "Countries from SQLite", Toast.LENGTH_LONG).show()
        }

    }

    private fun getDataFromAPI(){
        //apiden alinan veriler.
        countryLoading.value = true //ilk progress bar gozukun diye

        disposable.add(
                countryAPIService.getData()
                        .subscribeOn(Schedulers.newThread()) //nerede yapilacak --- yeni bir threadde
                        .observeOn(AndroidSchedulers.mainThread()) //nerede gozlemleyecegiz. -- ana threadde
                        .subscribeWith(object : DisposableSingleObserver<List<Country>>(){
                            override fun onSuccess(t: List<Country>) {
                                //basarili olursa .. Single da callback var cunku garanti bir sekilde veriyi alir observale de bu yok.
                                storeInSQLite(t)
                                Toast.makeText(getApplication(), "Countries from API", Toast.LENGTH_LONG).show()

                            }

                            override fun onError(e: Throwable) {
                                countryLoading.value = false
                                countryError.value = true
                                e.printStackTrace()
                            }

                        })
        )


    }

    private fun showCountries(countryList : List<Country>){
        countries.value = countryList
        countryError.value = false
        countryLoading.value = false
    }

    private fun storeInSQLite(list: List<Country>){

        launch {
            val dao = CountryDatabase(getApplication()).countryDao()
            dao.deleteAllCountries() //tum verileri silelim

            val listLong = dao.insertAll(*list.toTypedArray()) //listedeki her veriyi tek tek parametre verir. (primaet key listesi doner)
            var i = 0
            while (i < list.size){
                list[i].uuid = listLong[i].toInt()
                i++
            }

            showCountries(list)
        }

        customPreferences.saveTime(System.nanoTime())

    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

}
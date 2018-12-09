package com.example.shaffz.mvvmgettest.viewmodel

import android.annotation.SuppressLint
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.example.shaffz.mvvmgettest.model.MainModel
import com.example.shaffz.mvvmgettest.view.MainActivity
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.subjects.PublishSubject
import retrofit2.HttpException

class MainViewModel(val schedulersWrapper: SchedulersWrapper) : ViewModel() {
    val resultListObservable = MutableLiveData<List<MainModel.ResultEntity1>>()
    val resultListErrorObservable = MutableLiveData<HttpException>()
    val itemObservable = PublishSubject.create<MainModel.ResultEntity1>()
    fun getResultListObservable(): LiveData<List<MainModel.ResultEntity1>> = resultListObservable
    fun getResultListErrorObservable(): LiveData<HttpException> = resultListErrorObservable
    //    fun getItemObservable(): LiveData<MainModel.ResultEntity> = itemObservable
    lateinit var entityList: List<MainModel.ResultEntity1>
    lateinit var mainModel: MainModel

    @SuppressLint("RxLeakedSubscription")
    fun findAddress(address: String) {
        mainModel.fetchAddress(address)!!.subscribeOn(schedulersWrapper.io()).observeOn(schedulersWrapper.main())
            .subscribeWith(object : DisposableSingleObserver<List<MainModel.ResultEntity1>?>() {
                override fun onSuccess(t: List<MainModel.ResultEntity1>) {

                    entityList = t
                    resultListObservable.postValue(fetchItemTextFrom(t))

                }

                override fun onError(e: Throwable) {
                   //  resultListErrorObservable.postValue(e as HttpException)
                    Log.e("ERROR", "API")

                }
            })
    }

    private fun fetchItemTextFrom(it: List<MainModel.ResultEntity1>): List<MainModel.ResultEntity1>? {
        val li = arrayListOf<MainModel.ResultEntity1>()
        for (resultEntity in it) {
            // li.add("${resultEntity.name}: ${resultEntity.description}")
            li.add(resultEntity)
        }
        return li
    }

    fun doOnItemClick(position: Int) {
        itemObservable.onNext(entityList[position])
    }


}
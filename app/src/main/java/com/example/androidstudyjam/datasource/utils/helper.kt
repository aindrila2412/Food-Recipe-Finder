//package com.example.androidstudyjam.datasource.utils
//
//import androidx.lifecycle.LiveData
//import androidx.lifecycle.liveData
//import androidx.lifecycle.map
//import com.example.androidstudyjam.datasource.utils.Resource
//import kotlinx.coroutines.Dispatchers
//
///**
// * The database serves as the single source of truth.
// * Therefore UI can receive data updates from database only.
// * Function notify UI about:
// * [Result.Status.SUCCESS] - with data from database
// * [Result.Status.ERROR] - if error has occurred from any source
// * [Result.Status.LOADING]
// */
//fun <T, A> resultLiveData(
//    databaseQuery: () -> LiveData<T>,
//    networkCall: suspend () -> Result<A>,
//    saveCallResult: suspend (A) -> Unit
//): LiveData<Result<T>> =
//    liveData(Dispatchers.IO) {
//        emit(Resource.Loading())
//        val source = databaseQuery.invoke().map { Result.success(it) }
//        emitSource(source)
//        val responseStatus = networkCall.invoke()
//
//        when
//        if (responseStatus.status == Resource.SUCCESS) {
//            saveCallResult(responseStatus.data!!)
//        } else if (responseStatus.status == Result.Status.ERROR) {
//            emit(Result.error<T>(responseStatus.message!!, null))
//            emitSource(source)
//        }
//    }
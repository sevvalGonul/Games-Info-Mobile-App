package com.sevvalgonul.mobilvize

import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SubScribeFun {


    companion object {
        fun subscribeOnBackground(function: () -> Unit) {
            Single.fromCallable {
                function()
            }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
        }
    }
}
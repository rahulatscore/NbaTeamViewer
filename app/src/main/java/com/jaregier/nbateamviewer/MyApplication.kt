package com.jaregier.nbateamviewer

import android.app.Application
import com.jaregier.nbateamviewer.di.DaggerMyComponent
import com.jaregier.nbateamviewer.di.MyComponent

class MyApplication : Application() {

    companion object {
        private var instance: MyApplication? = null

        fun getInstance() = instance ?: throw IllegalStateException("Called getInstance before application was created")
    }

    init {
        instance = this
    }

    private var myComponent: MyComponent? = null

    fun getMyComponent(): MyComponent {
        val component = myComponent

        if (component == null) {
            createMyComponent().apply {
                myComponent = this
                return this
            }
        } else {
            return component
        }
    }

    private fun createMyComponent(): MyComponent {
        return DaggerMyComponent
                .builder()
                .build()
    }
}
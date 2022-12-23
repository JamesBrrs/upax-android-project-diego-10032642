package com.upax.androidproject.presentation

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import com.upax.androidproject.utils.ApplicationApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

@SuppressLint("StaticFieldLeak")
class MainViewModel : ViewModel() {

    private val context = ApplicationApp().getContext()

    private val parentJob = Job()
    private val coroutineContext: CoroutineContext get() = parentJob + Dispatchers.IO
    private val viewModelScope = CoroutineScope(coroutineContext)

    private fun getPokemon(){
        viewModelScope.launch {

        }
    }
}
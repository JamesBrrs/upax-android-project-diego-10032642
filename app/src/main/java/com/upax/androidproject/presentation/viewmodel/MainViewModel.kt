package com.upax.androidproject.presentation.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.upax.androidproject.core.Resource
import com.upax.androidproject.core.ServiceStatus
import com.upax.androidproject.domain.model.PokemonListResponse
import com.upax.androidproject.domain.usecase.PokemonUseCase
import com.upax.androidproject.utils.ApplicationApp
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

@SuppressLint("StaticFieldLeak")
class MainViewModel : ViewModel() {

    private val context = ApplicationApp().getContext()

    private val parentJob = Job()
    private val coroutineContext: CoroutineContext get() = parentJob + Dispatchers.IO
    private val viewModelScope = CoroutineScope(coroutineContext)
    private var pokemonUseCase = PokemonUseCase()

    private val _statusService = MutableLiveData<ServiceStatus>()
    val statusService: LiveData<ServiceStatus> get() = _statusService

    fun getPokemon() {
        setServiceStatus(ServiceStatus.LOADING)
        viewModelScope.launch {
            val response = pokemonUseCase.invoke()
            when (response.status) {
                Resource.Status.SUCCESS -> {
                    setServiceStatus(ServiceStatus.SUCCESS)
                    val data = Gson().fromJson(
                        Gson().toJson(response.data),
                        PokemonListResponse::class.java
                    )
                    println(data)
                }
                else -> {
                    setServiceStatus(ServiceStatus.ERROR)
                }
            }
        }
    }

    private fun setServiceStatus(status: ServiceStatus){
        viewModelScope.launch {
            withContext(Dispatchers.Main){
                _statusService.value = status
            }
        }
    }
}
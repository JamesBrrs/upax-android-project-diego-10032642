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
class PokemonListViewModel : ViewModel() {

    private val context = ApplicationApp().getContext()

    private val parentJob = Job()
    private val coroutineContext: CoroutineContext get() = parentJob + Dispatchers.IO
    private val viewModelScope = CoroutineScope(coroutineContext)
    private var pokemonUseCase = PokemonUseCase()

    private val _statusService = MutableLiveData<ServiceStatus>()
    val statusService: LiveData<ServiceStatus> get() = _statusService

    private val _image = MutableLiveData<Pair<String,String>>()
    val image: LiveData<Pair<String,String>> get() = _image

    private val _pokemonList = MutableLiveData<PokemonListResponse>()
    val pokemonList: LiveData<PokemonListResponse> get() = _pokemonList

    fun getPokemon(limit: Int) {
        setServiceStatus(ServiceStatus.LOADING)
        viewModelScope.launch {
            val response = pokemonUseCase.getPokemon(limit)
            when (response.status) {
                Resource.Status.SUCCESS -> {
                    setServiceStatus(ServiceStatus.SUCCESS)
                    val data = Gson().fromJson(
                        Gson().toJson(response.data),
                        PokemonListResponse::class.java
                    )
                    data.results?.mapIndexed { index, pokemonListModel ->
                        getPokemonData(index.toString(), pokemonListModel.name)
                    }
                    withContext(Dispatchers.Main){
                        _pokemonList.value = data
                    }
                    println(data)
                }
                else -> {
                    setServiceStatus(ServiceStatus.ERROR)
                }
            }
        }
    }

    private fun getPokemonData(id: String, name: String){
        viewModelScope.launch {
            val response = pokemonUseCase.getPokemonData(name)
            when (response.status) {
                Resource.Status.SUCCESS -> {
                    withContext(Dispatchers.Main){
                        if (response.data?.sprites?.backDefault.isNullOrEmpty().not()){
                            _image.value = Pair(response.data!!.sprites!!.backDefault,name)
                        }
                    }
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
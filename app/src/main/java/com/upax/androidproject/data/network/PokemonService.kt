package com.upax.androidproject.data.network

import com.upax.androidproject.core.ApiInterface
import com.upax.androidproject.core.BaseDataSource
import com.upax.androidproject.core.Resource
import com.upax.androidproject.domain.model.PokemonListResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PokemonService : BaseDataSource() {
    override fun getUrl() = "https://pokeapi.co/api/v2/"
    override fun getClazz() = ApiInterface::class.java

    suspend fun getPokemon(): Resource<PokemonListResponse> {
        return withContext(Dispatchers.IO) {
            getResult {
                getRetrofit<ApiInterface>().getPokemon(
                    "25",
                    "25"
                )
            }
        }
    }

}
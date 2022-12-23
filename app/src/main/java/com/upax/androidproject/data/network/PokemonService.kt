package com.upax.androidproject.data.network

import com.upax.androidproject.core.ApiInterface
import com.upax.androidproject.core.BaseDataSource
import com.upax.androidproject.core.Resource
import com.upax.androidproject.domain.model.PokemonDataModel
import com.upax.androidproject.domain.model.PokemonListResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PokemonService : BaseDataSource() {
    override fun getUrl() = "https://pokeapi.co/api/v2/"
    override fun getClazz() = ApiInterface::class.java

    suspend fun getPokemon(limit: Int): Resource<PokemonListResponse> {
        return withContext(Dispatchers.IO) {
            getResult {
                getRetrofit<ApiInterface>().getPokemon(
                    limit.toString(),
                    "25"
                )
            }
        }
    }

    suspend fun getPokemonData(name: String): Resource<PokemonDataModel>{
        return withContext(Dispatchers.IO) {
            getResult {
                getRetrofit<ApiInterface>().getPokemonData(
                    name
                )
            }
        }
    }

}
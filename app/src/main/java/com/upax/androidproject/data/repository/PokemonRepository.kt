package com.upax.androidproject.data.repository

import com.upax.androidproject.core.Resource
import com.upax.androidproject.data.network.PokemonService
import com.upax.androidproject.domain.model.PokemonDataModel
import com.upax.androidproject.domain.model.PokemonListResponse

class PokemonRepository {
    private val api = PokemonService()

    suspend fun getPokemon(limit: Int): Resource<PokemonListResponse> {
        return api.getPokemon(limit)
    }

    suspend fun getPokemonData(name: String): Resource<PokemonDataModel> {
        return api.getPokemonData(name)
    }
}
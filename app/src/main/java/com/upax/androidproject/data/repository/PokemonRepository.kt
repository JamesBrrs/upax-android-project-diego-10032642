package com.upax.androidproject.data.repository

import com.upax.androidproject.core.Resource
import com.upax.androidproject.data.network.PokemonService
import com.upax.androidproject.domain.model.PokemonListResponse

class PokemonRepository {
    val api = PokemonService()

    suspend fun getPokemon(): Resource<PokemonListResponse> {

    }
}
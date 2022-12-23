package com.upax.androidproject.domain.usecase

import com.upax.androidproject.core.Resource
import com.upax.androidproject.data.repository.PokemonRepository
import com.upax.androidproject.domain.model.PokemonDataModel
import com.upax.androidproject.domain.model.PokemonListResponse

class PokemonUseCase {
    private val repository = PokemonRepository()

    suspend fun getPokemon(limit: Int): Resource<PokemonListResponse> {
        return repository.getPokemon(limit)
    }

    suspend fun getPokemonData(name: String): Resource<PokemonDataModel> {
        return repository.getPokemonData(name)
    }
}
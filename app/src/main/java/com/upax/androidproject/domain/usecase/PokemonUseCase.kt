package com.upax.androidproject.domain.usecase

import com.upax.androidproject.core.Resource
import com.upax.androidproject.data.repository.PokemonRepository
import com.upax.androidproject.domain.model.PokemonListResponse

class PokemonUseCase {
    private val repository = PokemonRepository()

    suspend operator fun invoke(): Resource<PokemonListResponse> {
        return repository.getPokemon()
    }
}
package com.upax.androidproject.core

import com.upax.androidproject.domain.model.PokemonListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET()
    suspend fun getPokemon(
        @Query("limit") limit: String,
        @Query("offset") offset: String
    ): Response <PokemonListResponse>
}
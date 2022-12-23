package com.upax.androidproject.domain.model

data class PokemonListResponse(
    var count: Int = 0,
    var next: String = "",
    var previous: String = "",
    var results: PokemonListModel? = null
)
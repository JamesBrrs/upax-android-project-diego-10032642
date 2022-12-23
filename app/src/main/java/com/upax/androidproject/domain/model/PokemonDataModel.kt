package com.upax.androidproject.domain.model

data class PokemonDataModel(
    var sprites: Sprites? = null
)

data class Sprites(
    val backDefault: String,
    val backFemale: Any? = null,
    val backShiny: String,
    val backShinyFemale: Any? = null,
    val frontDefault: String,
    val frontFemale: Any? = null,
    val frontShiny: String,
    val frontShinyFemale: Any? = null,
    val other: Other
)

data class Other(
    val dreamWorld: DreamWorld,
    val home: Home,
    val officialArtwork: OfficialArtwork
)

data class DreamWorld(
    val frontDefault: String,
    val frontFemale: Any? = null
)

data class Home(
    val frontDefault: String,
    val frontFemale: Any? = null,
    val frontShiny: String,
    val frontShinyFemale: Any? = null
)

data class OfficialArtwork(
    val frontDefault: String
)
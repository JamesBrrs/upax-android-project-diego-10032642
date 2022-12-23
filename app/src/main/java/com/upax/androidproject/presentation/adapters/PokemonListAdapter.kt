package com.upax.androidproject.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.upax.androidproject.R
import com.upax.androidproject.databinding.PokemLayoutBinding
import com.upax.androidproject.domain.model.CircleImageModel
import com.upax.androidproject.domain.model.PokemonListModel
import com.upax.androidproject.presentation.components.CircleImageComponent

class PokemonListAdapter(private val pokemonList: List<PokemonListModel>) :
    RecyclerView.Adapter<PokemonListAdapter.PokemonListHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonListHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return PokemonListHolder(layoutInflater.inflate(R.layout.pokem_layout, parent, false))
    }

    override fun onBindViewHolder(holder: PokemonListHolder, position: Int) {
        holder.render(pokemonList[position])
    }

    override fun getItemCount() = pokemonList.size

    inner class PokemonListHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        fun render(pokemon: PokemonListModel) {
            val binding = PokemLayoutBinding.bind(view)
            val image = CircleImageModel(url = pokemon.url, name = pokemon.name)
            binding.apply {
                pokemonName.text = pokemon.name
                CircleImageComponent(pokemonImage, image)
            }
        }
    }
}
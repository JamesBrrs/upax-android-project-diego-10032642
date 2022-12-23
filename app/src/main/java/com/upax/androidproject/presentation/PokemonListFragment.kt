package com.upax.androidproject.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.upax.androidproject.core.ServiceStatus
import com.upax.androidproject.databinding.PokemonListFragmentBinding
import com.upax.androidproject.domain.model.PokemonListModel
import com.upax.androidproject.presentation.adapters.PokemonListAdapter
import com.upax.androidproject.presentation.viewmodel.PokemonListViewModel

class PokemonListFragment : Fragment() {

    private lateinit var _binding: PokemonListFragmentBinding
    private val binding get() = _binding
    private val viewModel: PokemonListViewModel by viewModels()
    private lateinit var adapter: PokemonListAdapter
    val dataList = mutableListOf<PokemonListModel>()
    private var validateScroll = true
    private var total = 25

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = PokemonListFragmentBinding.inflate(layoutInflater, container, false)
        initView()
        return binding.root
    }

    private fun initView() {
        viewModel.getPokemon(total)
        viewModel.pokemonList.observe(viewLifecycleOwner) { list->
            if (list.results?.isNotEmpty() == true) {
                list.results?.map {
                    dataList.add(it)
                }
                if (total == 25){
                    initRecycler()
                }else{
                    total += 25
                    updateInfo()
                }
            }
        }
        viewModel.statusService.observe(viewLifecycleOwner){
            validateScroll = it != ServiceStatus.LOADING
            if (it ==ServiceStatus.LOADING){
                Toast.makeText(requireContext(), "Cargando...", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun initRecycler() {
        total += 25
        binding.pokemonRecycler.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL, false
        )
        adapter = PokemonListAdapter(dataList)
        binding.pokemonRecycler.adapter = adapter
        binding.pokemonRecycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                val find =
                    (binding.pokemonRecycler.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                if (validateScroll) {
                    if (dataList.size <= total) {
                        if (find == dataList.size - 1) {
                            viewModel.getPokemon(total)
                        }
                    }
                }
            }
        })
    }

    private fun updateInfo(){
        dataList.mapIndexed { index, _ ->
            if (index >= total-25)
                adapter.notifyItemInserted(index)
        }
    }
}
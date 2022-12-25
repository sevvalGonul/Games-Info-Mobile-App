package com.sevvalgonul.mobilvize

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.sevvalgonul.mobilvize.databinding.FragmentGamesBinding
import com.sevvalgonul.mobilvize.util.Resource
import java.util.*
import kotlin.collections.ArrayList


class GamesFragment : Fragment() {
    private lateinit var binding : FragmentGamesBinding
    //private lateinit var gameList : ArrayList<Game>
    //private lateinit var adapter : Rv_adapter
    val TAG = "GamesFragment"

    private lateinit var viewModel: GamesViewModel  // ??? private olmamalı mı
    lateinit var gamesAdapter: GamesAdapter

  override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentGamesBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel  // To access view model in main activity
        setUpRecyclerView()
        // ??? Burada Observer'ı direkt tanımadı
        viewModel.games.observe(viewLifecycleOwner, androidx.lifecycle.Observer { response ->
            when(response) {
                is Resource.Success -> {
                    response.data?.let { gamesResponse ->
                    gamesAdapter.differ.submitList(gamesResponse.resultGames)

                    }
                }
                is Resource.Error -> {
                    response.message?.let { message ->
                        Log.e(TAG, "An error occured: $message")

                    }
                }

                is Resource.Loading -> {
                    //binding.noGameHas.text = "Loading..."  // ??? Ben ekledim
                }
                else -> {}  // ??? Compiler bunu zorla eklettirdi
            }

        })




        /*
        gameList = arrayListOf<Game>()
        var game1 = Game(R.drawable.a,"Grand Theft Auto V", 96, "Action, shooter")
        var game2 = Game(R.drawable.b,"Portal 2", 95, "Action, puzzle")
        var game3 = Game(R.drawable.c,"The Witcher 3: Wild Hunt", 89, "Action, puzzle")
        var game4 = Game(R.drawable.d,"Left 4 Dead 2", 89, "Action, puzzle")
        gameList.add(game1)
        gameList.add(game2)
        gameList.add(game3)
        gameList.add(game4)

        val layoutManager = LinearLayoutManager(context)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.setHasFixedSize(true)
        adapter = Rv_adapter(gameList, true)
        binding.recyclerView.adapter = adapter


        binding.searchField.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText)
                return true
            }
        })

    }

    private fun filterList(query : String?) {
        if(query != null) {
            val filteredList = ArrayList<Game>()

            for(item in gameList) {
                if(item.name.lowercase(Locale.ROOT).contains(query.lowercase()) ) {
                    filteredList.add(item)
                }
            }

            if(filteredList.isEmpty()) {
                adapter.setFilteredList(filteredList)
                binding.noGameHas.setText("This game doesn't exist")
            }
            else {
                adapter.setFilteredList(filteredList)
            }
        } */

    }

    private fun hideProgressBar() {
        // ??? Progress barımız yok. Bu durumda loading state de silinmeli miiii
    }

    private fun setUpRecyclerView() {
        gamesAdapter = GamesAdapter(true)
        binding.recyclerView.apply {
            adapter = gamesAdapter
            layoutManager = LinearLayoutManager(activity)

        }
    }

}
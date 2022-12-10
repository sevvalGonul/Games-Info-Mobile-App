package com.sevvalgonul.mobilvize

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.sevvalgonul.mobilvize.databinding.FragmentGamesBinding


class GamesFragment : Fragment() {
    private lateinit var binding : FragmentGamesBinding
    private lateinit var gameList : ArrayList<Game>

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
        gameList = arrayListOf<Game>()
        var game1 = Game(R.drawable.a,"OYUN 1", 10, "aciton")
        var game2 = Game(R.drawable.b,"OYUN 2", 20, "aciton")
        var game3 = Game(R.drawable.c,"OYUN 3", 30, "aciton")
        var game4 = Game(R.drawable.d,"OYUN 4", 40, "aciton")
        gameList.add(game1)
        gameList.add(game2)
        gameList.add(game3)
        gameList.add(game4)

        val layoutManager = LinearLayoutManager(context)
        binding.recyclerView.layoutManager = layoutManager
        //binding.recyclerView.setHasFixedSize(true)  // BU NEE
        val adapter = Rv_adapter(gameList)
        binding.recyclerView.adapter = adapter

    }


}
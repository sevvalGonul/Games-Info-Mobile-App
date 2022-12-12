package com.sevvalgonul.mobilvize

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sevvalgonul.mobilvize.databinding.FragmentDetailsBinding
import com.sevvalgonul.mobilvize.databinding.FragmentFavouritesBinding

class FavouritesFragment : Fragment() {
    private lateinit var binding: FragmentFavouritesBinding
    private lateinit var gameList : ArrayList<Game>  // bu favlist olmalı
    private lateinit var myAdapter : Rv_adapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFavouritesBinding.inflate(inflater,container,false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
         // favlist olmalı:
        gameList = arrayListOf<Game>()
        var game1 = Game(R.drawable.a,"OYUN 1", 10, "aciton")
        var game2 = Game(R.drawable.b,"BYUN 2", 20, "aciton")
        var game3 = Game(R.drawable.c,"CYUN 3", 30, "aciton")
        var game4 = Game(R.drawable.d,"KYUN 4", 40, "aciton")
        gameList.add(game1)
        gameList.add(game2)
        gameList.add(game3)
        gameList.add(game4)

        myAdapter = Rv_adapter(gameList)
        val recyclerView = binding.favRecyclerView
        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = myAdapter
        }


        if(!(gameList.isEmpty()))  // update needed?
            binding.noFavHas.visibility = View.INVISIBLE


        val itemSwipe = object: ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {  // this method will be called when user swipe an item
                showDialog(viewHolder)
            }

        }

        val swap = ItemTouchHelper(itemSwipe)
        swap.attachToRecyclerView(recyclerView)




        //game liste game itemlerini al

        /*
        for(item in gameList){

            if(item.sFavourite == true){
                    print( game card view)
            }

        }

        */


    }

    private fun showDialog(viewHolder: RecyclerView.ViewHolder) {
        val builder = AlertDialog.Builder(activity, R.style.MyAlertDialogStyle)
        builder.setTitle("Delete Item")
        builder.setMessage("Are you sure you want to delete item?")
        builder.setPositiveButton("YES") { dialog, which ->
            val position = viewHolder.adapterPosition
            gameList.removeAt(position)
            myAdapter.notifyItemRemoved(position)

        }
        builder.setNegativeButton("NO") { dialog, which ->
            val position = viewHolder.adapterPosition
            myAdapter.notifyItemChanged(position)

        }
        builder.setCancelable(false)  // prevent dialog from disappearing when user clicked somewhere else
        builder.show()
    }
}
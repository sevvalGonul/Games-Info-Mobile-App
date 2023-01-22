package com.sevvalgonul.mobilvize

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sevvalgonul.mobilvize.databinding.FragmentFavouritesBinding

class FavouritesFragment : Fragment() {
    private lateinit var gameList: List<ResultGame>
    private lateinit var binding: FragmentFavouritesBinding
    private lateinit var favList : ArrayList<Game>
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

        /*
        // Görünümleri test etmek için favori listesi statik olarak oluşturuldu. Finalde gerçeği yansıtacaktır.
        favList = arrayListOf<Game>()
        var game1 = Game(R.drawable.a,"Grand Theft Auto V", 96, "Action, shooter")
        var game2 = Game(R.drawable.b,"Portal 2", 95, "Action, puzzle")
        var game3 = Game(R.drawable.c,"The Witcher 3: Wild Hunt", 89, "Action, puzzle")
        var game4 = Game(R.drawable.d,"Left 4 Dead 2", 89, "Action, puzzle")
        favList.add(game1)
        favList.add(game2)
        favList.add(game3)
        favList.add(game4)

         */
        println("Favoriler açıldı")

        initRecyclerView(FavoriteModel.getFavoritedList())
        //fav game list = FavoriteModel.getFavoritedList()




    /**/
        val itemSwipe = object: ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                println("onMove içinde")
                return false
            }
           /* */

/* */
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {  // this method will be called when user swipe an item
                showDialog(viewHolder)
            }

        }

        val swap = ItemTouchHelper(itemSwipe)
        swap.attachToRecyclerView(binding.favRecyclerView)

    }

    private fun showDialog(viewHolder: RecyclerView.ViewHolder) {
        val builder = AlertDialog.Builder(activity, R.style.MyAlertDialogStyle)
        builder.setTitle("Delete Item")
        builder.setMessage("Are you sure you want to delete item?")
        /* */
        builder.setPositiveButton("YES") { dialog, which ->
            val position = viewHolder.adapterPosition
            FavoriteModel.deleteFavoritedListByIndex(position)
            println("position = " +position)
            initRecyclerView(FavoriteModel.getFavoritedList())
            //DELETE FROM FAV LİST YAPILDI AMA EFEKTİF DEĞİL
            //VE FAVOURİTED YANINDAKİ SAYI GÜNCELLENİYOR

        }

        builder.setNegativeButton("NO") { dialog, which ->
            val position = viewHolder.adapterPosition
            binding.favRecyclerView.adapter?.notifyItemChanged(position)
            println("no position")

        }
         /* */
        builder.setCancelable(false)  // prevent dialog from disappearing when user clicked somewhere else
        builder.show()
    }

/* */


    fun initRecyclerView(gameList : List<ResultGame>) {

        binding.favRecyclerView.layoutManager = LinearLayoutManager(binding.favRecyclerView.context)  // Context?
        binding.favRecyclerView.adapter = Rv_adapter(gameList, false)
        binding.secTitle.text = "Favourites (" + FavoriteModel.getFavoritedList().size + ")"

    }

}
package com.sevvalgonul.mobilvize


import android.graphics.Color
import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView

// Vizede kullanıldı. Final için GamesAdapter yazılacak ve gerekli göstermeler güncellenebilir.
class Rv_adapter(private var gameList : ArrayList<Game>, private var details : Boolean) :
    RecyclerView.Adapter<Rv_adapter.GameViewHolder>() {

    class GameViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    // initializing our values
        val image : ImageView = itemView.findViewById(R.id.game_png)
        val gameName : TextView = itemView.findViewById(R.id.game_name)
        val metacritic : TextView = itemView.findViewById(R.id.meta_critic)
        val genre : TextView = itemView.findViewById(R.id.game_detail)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(  // inflate our layout file
            R.layout.game_item,
            parent, false
        )
        return GameViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        val currentItem = gameList[position]
        holder.image.setImageResource(currentItem.image)
        holder.gameName.text = currentItem.name
        holder.metacritic.text = currentItem.rate.toString()
        holder.genre.text = currentItem.genre


        if(details) {  // Rv_adapter GamesFragment'ta kullanılıyorsa DetailsFragment'a navigate edecek ve tıklanan objeyi gönderecek
            holder.itemView.setOnClickListener {
                val navController = Navigation.findNavController(it)
                val bundle = Bundle()
                bundle.putParcelable("currentGame", currentItem)
                navController!!.navigate(R.id.action_gamesFragment_to_detailsFragment, bundle)

                holder.itemView.setBackgroundColor(Color.parseColor("#E0E0E0"))
            }

        }
    }

        override fun getItemCount(): Int {
            return gameList.size
        }

        fun setFilteredList(filteredList: ArrayList<Game>) {
            this.gameList = filteredList
            notifyDataSetChanged()
        }
    }


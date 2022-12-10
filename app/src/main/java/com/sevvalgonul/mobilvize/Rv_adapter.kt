package com.sevvalgonul.mobilvize

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class Rv_adapter(private var gameList : ArrayList<Game>) :
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

        holder.itemView.setOnClickListener{
            holder.itemView.setBackgroundColor(Color.parseColor("#E0E0E0"))

        }


    override fun getItemCount(): Int {
        return gameList.size
    }

    fun setFilteredList(filteredList : ArrayList<Game>) {
        this.gameList = filteredList
        notifyDataSetChanged()
    }
}

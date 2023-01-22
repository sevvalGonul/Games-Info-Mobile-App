package com.sevvalgonul.mobilvize


import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class Rv_adapter(private var gameList : List<ResultGame>, private var details : Boolean) :
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
        //holder.image.setImageResource(currentItem.image)
        holder.gameName.text = currentItem.name
        holder.metacritic.text = currentItem.metacritic.toString()
        val genreStr = currentItem.genres.map { it.name }
        holder.genre.text = genreStr.joinToString()
        Glide.with(holder.image.context).load(currentItem.background_image).into(holder.image)


        //if (details) {  // Rv_adapter GamesFragment'ta kullanılıyorsa DetailsFragment'a navigate edecek ve tıklanan objeyi gönderecek
            // Detay Ekrani GamesFragment'ten mi acildi, FavouritesFragment'ten mi Acildi Bilinmesi gerekiyor.
            holder.itemView.setOnClickListener {
                val navController = Navigation.findNavController(it)
                val bundle = Bundle()
                //bundle.putParcelable("currentGame", currentItem)
                bundle.putInt("gameId", currentItem.id)
                if (details) {  // Rv_adapter GamesFragment'ta kullanılıyorsa DetailsFragment'a navigate edecek ve tıklanan objeyi gönderecek
                    navController!!.navigate(R.id.action_gamesFragment_to_detailsFragment, bundle)
                } else {
                    navController!!.navigate(R.id.action_favouritesFragment_to_detailsFragment, bundle)
                }
                holder.itemView.setBackgroundColor(Color.parseColor("#E0E0E0"))
            }

       // }
    }

        override fun getItemCount(): Int {
            return gameList.size
        }

    fun setTempGameList(tempGameList: List<ResultGame>) {
        gameList = tempGameList
    }

    /*fun setFilteredList(filteredList: ArrayList<Game>) {
        this.gameList = filteredList
        notifyDataSetChanged()
    }*/
    }


package com.sevvalgonul.mobilvize

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

// When we use notifyDataSetChanged recycler view adapter updates all its item even the items that didn't change
// To solve that problem we use DiffUtil, enables us to update only items that different
class GamesAdapter(private var details : Boolean) : RecyclerView.Adapter<GamesAdapter.GameViewHolder>() {

    inner class GameViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // initializing our values
        val image : ImageView = itemView.findViewById(R.id.game_png)
        val gameName : TextView = itemView.findViewById(R.id.game_name)
        val metacritic : TextView = itemView.findViewById(R.id.meta_critic)
        val genre : TextView = itemView.findViewById(R.id.game_detail)
    }

    private val differCallback = object : DiffUtil.ItemCallback<ResultGame> () {
        override fun areItemsTheSame(oldItem: ResultGame, newItem: ResultGame): Boolean {
            return oldItem.uid == newItem.uid;
        }

        override fun areContentsTheSame(oldItem: ResultGame, newItem: ResultGame): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(  // inflate our layout file
            R.layout.game_item,
            parent, false
        )

        return GameViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        val game = differ.currentList[position]
        holder.itemView.apply {  // gereksiz???
            Glide.with(this).load(game.background_image).into(holder.image)
            holder.gameName.text = game.name
            holder.metacritic.text = game.metacritic.toString()

            if (details) {  // adapter GamesFragment'ta kullanılıyorsa DetailsFragment'a navigate edecek ve tıklanan objeyi gönderecek
                holder.itemView.setOnClickListener {
                    val navController = Navigation.findNavController(it)
                    val bundle = Bundle()
                    bundle.putParcelable("currentGame", game)
                    navController!!.navigate(R.id.action_gamesFragment_to_detailsFragment, bundle)

                    holder.itemView.setBackgroundColor(Color.parseColor("#E0E0E0"))
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}
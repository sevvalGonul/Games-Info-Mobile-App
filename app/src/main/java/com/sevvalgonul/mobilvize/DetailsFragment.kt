package com.sevvalgonul.mobilvize

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.sevvalgonul.mobilvize.databinding.FragmentDetailsBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DetailsFragment : Fragment() {
    private lateinit var binding : FragmentDetailsBinding
    private val args by navArgs<DetailsFragmentArgs>()
    private lateinit var details : DetailResponse


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //binding.imageView.setImageResource(args.currentGame.image)
        //binding.text.text = args.currentGame.name

        val apiService = GamesApiService.getInstance()
        var call = apiService.getGamesDetail(args.gameId)

        println("args.gameId=$args.gameId")
        call.enqueue(object: Callback<DetailResponse> {
            override fun onResponse(
                call: Call<DetailResponse>,
                response: Response<DetailResponse>
            ) {
                if(response.code() == 200) {
                    details = response.body()!!  // DetailResponse nullable da yapabilirsin?
                    //print(details)
                    Glide.with(binding.imageView.context).load(details.background_image).into(binding.imageView)  // load image url into imageView
                    binding.text.text = details.name  // Game name
                    binding.textView.text = details.description  // Game description
                    // Favori dosyasindan okunan deger ile kontrol edilecek.

                    if (FavoriteModel.isFavoritedGames(args.gameId)) {
                        binding.fav.setText("Favourited")
                    }

                }
            }

            override fun onFailure(call: Call<DetailResponse>, t: Throwable) {
                println("Detail frag API Call on Failure")
            }
        })

        binding.buttonReadMore.setOnClickListener{  //ON CLICK READ MORE BUTTON LOADS 4 MORE LINES EACH TIME
            binding.textView.maxLines+=4
        }

        binding.fav.setOnClickListener {   //ON CLICK TO FAVOURITE BUTTON, IT SETS THE TEXT TO FAVOURITED

            if (binding.fav.text.equals("Favourite")) {
                binding.fav.setText("Favourited")
/* Burada currentGame.isFavourite degis */
                println("OyunID= ${args.gameId}")
                // Dosyaya yazdirma FavoriteModel,e tasinacak.

                /*
                var myFile = FavoriteModel.getFavoritesFile()
                myFile.appendText(args.gameId.toString()+";")

                 */
                FavoriteModel.addFavoritedList(args.gameId)

                FavoriteModel.insertFavoriGame(getGameItem(args.gameId));
/*                if (args.currentGame.isFavourite == false){  //IF NOT ADDED TO FAVS BEFORE ADD TO FAV
                    args.currentGame.isFavourite = true
                }*/
            } else {
                binding.fav.setText("Favourite")
                //DELETE FROM FAVORİTED LİST
                FavoriteModel.deleteFavoritedList(args.gameId)
            }
        }

        binding.gamePgButton.setOnClickListener {  //THE BACK BUTTON ONCLICK: GO TO GAMES FRAGMENT
            val action = DetailsFragmentDirections.actionDetailsFragmentToGamesFragment()
            Navigation.findNavController(it).navigate(action)
        }

        binding.button.setOnClickListener{   //VISIT REDDIT REDIRECTS TO REDDIT WEB PAGE
            //val url = "https://www.reddit.com/r/GrandTheftAutoV/"
            val url = details.reddit_url
            if(url != null) {  // bu gereksiz mi?
                val i = Intent(Intent.ACTION_VIEW)
                i.data = Uri.parse(url)
                startActivity(i)
            }

        }
        binding.button2.setOnClickListener{  //VISIT WEBSITE REDIRECTS TO WEB PAGE
            //val url = "https://www.rockstargames.com/gta-v"
            val url = details.website
            if(url != null) {
                val i = Intent(Intent.ACTION_VIEW)
                i.data = Uri.parse(url)
                startActivity(i)
            }

        }
    }

    private fun getGameItem(gameId: Int): Game? {
/*
        val id: Int? = null,
        val name: String,
        val background_image: String,
        val metacritic: Int?,
        val genres : List<GenreNames>
*/
        var resultGame : ResultGame? = GameModel.getGameWitdID(gameId)
        var game = Game(resultGame?.id, resultGame?.name, resultGame?.background_image, resultGame?.metacritic )
        return game
    }
}
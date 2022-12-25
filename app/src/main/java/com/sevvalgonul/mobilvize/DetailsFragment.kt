package com.sevvalgonul.mobilvize

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.sevvalgonul.mobilvize.databinding.FragmentDetailsBinding
import android.net.Uri


class DetailsFragment : Fragment() {
    private lateinit var binding : FragmentDetailsBinding
    private val args by navArgs<DetailsFragmentArgs>()

    private lateinit var viewModel: GamesViewModel  // ??? DetailsFragment'ta buna ihtiyaç olacak mı


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
        viewModel = (activity as MainActivity).viewModel  // To access view model in main activity



        binding.imageView.setImageResource(args.currentGame.image)
        binding.text.text = args.currentGame.name

        binding.buttonReadMore.setOnClickListener{  //ON CLICK READ MORE BUTTON LOADS 4 MORE LINES EACH TIME
            binding.textView.maxLines+=4
        }

        binding.fav.setOnClickListener {   //ON CLICK TO FAVOURITE BUTTON, IT SETS THE TEXT TO FAVOURITED

            if (binding.fav.text.equals("Favourite")) {
                binding.fav.setText("Favourited")

                if (args.currentGame.isFavourite == false){  //IF NOT ADDED TO FAVS BEFORE ADD TO FAV
                    args.currentGame.isFavourite = true
                }
            } else {
                binding.fav.setText("Favourite")
            }
        }

        binding.gamePgButton.setOnClickListener{  //THE BACK BUTTON ONCLICK: GO TO GAMES FRAGMENT
            val action = DetailsFragmentDirections.actionDetailsFragmentToGamesFragment()
            Navigation.findNavController(it).navigate(action)
        }

        binding.button.setOnClickListener{   //VISIT REDDIT REDIRECTS TO REDDIT WEB PAGE
            val url = "https://www.reddit.com/r/GrandTheftAutoV/"
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url)
            startActivity(i)
        }
        binding.button2.setOnClickListener{  //VISIT WEBSITE REDIRECTS TO WEB PAGE
            val url = "https://www.rockstargames.com/gta-v"
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url)
            startActivity(i)
        }
    }
}
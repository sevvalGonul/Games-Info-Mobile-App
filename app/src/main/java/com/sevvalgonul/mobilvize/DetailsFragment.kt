package com.sevvalgonul.mobilvize

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.sevvalgonul.mobilvize.databinding.FragmentDetailsBinding


class DetailsFragment : Fragment() {
    private lateinit var binding : FragmentDetailsBinding
    private val args by navArgs<DetailsFragmentArgs>()


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
        binding.imageView.setImageResource(args.currentGame.image)
        binding.text.text = args.currentGame.name

        binding.buttonReadMore.setOnClickListener{
            binding.textView.maxLines+=4
        }

        binding.fav.setOnClickListener{


            if ( binding.fav.text.equals("Favourite") ){

            binding.fav.setText("Favourited")

        }
            else{

            binding.fav.setText("Favourite")


        }


        /*
        if ( Game item class . isFavourite == false){

            binding.fav.setText("Favourited")
            Game item class.isFavorite.set(true)


        }
        else{

            binding.fav.setText("Favourite")
            Game item class.isFavorite.set(false)

        }

         */



        }


        binding.gamePgButton.setOnClickListener{

            //stackback

            val action = DetailsFragmentDirections.actionDetailsFragmentToGamesFragment()

            Navigation.findNavController(it).navigate(action)



        }

    }


}
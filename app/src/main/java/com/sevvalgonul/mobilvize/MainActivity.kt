package com.sevvalgonul.mobilvize

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.sevvalgonul.mobilvize.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val navController = findNavController(R.id.fragmentContainerView)
        binding.bottomNavigationView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if(destination.id == R.id.detailsFragment) {
                binding.bottomNavigationView.visibility = View.GONE
            } else {
                binding.bottomNavigationView.visibility = View.VISIBLE
            }
        }
        println("main.onCreate")
        // Favori ID Listesini Dosyadan Okuyacak.
        FavoriteModel.getInstance(this.application)
        //FavoriteModel.getFavoriIdList()
    }

    override fun finish() {
        println("Yazilim finish...")
        super.finish()

    }
    override fun onDestroy() {
        println("Yazilim onDestroy...")
        super.onDestroy()
    }

    override fun onStop() {
        println("Yazilim onStop...")
        // Guncel Favori Listesi Dosyaya Yazdirilir.
        FavoriteModel.writeFavoritedIdListToFile()
        super.onStop()
        println("Yazilim super.onStop Sonrasi...")
    }


}
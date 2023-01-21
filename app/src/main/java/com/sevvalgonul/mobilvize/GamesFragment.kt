package com.sevvalgonul.mobilvize

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.sevvalgonul.mobilvize.databinding.FragmentGamesBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList


class GamesFragment : Fragment() {
    private lateinit var binding : FragmentGamesBinding
    //private lateinit var gameList : ArrayList<Game>
    private lateinit var adapter : Rv_adapter
    private var isLoading = false
    private var isLastPage = false
    private var tempGameList: ArrayList<ResultGame> = ArrayList()
    private var resultList: MutableList<ResultGame> = mutableListOf()
    private val PAGE_START = 1
    private val TOTAL_PAGES = 20
    private var currentPage = PAGE_START
    private val linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    private lateinit var apiService: GamesApiService

  override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentGamesBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.layoutManager = linearLayoutManager
        binding.recyclerView.setHasFixedSize(true)
        adapter = Rv_adapter(tempGameList,true)
        binding.recyclerView.adapter = adapter
        apiService = GamesApiService.getInstance()


        /*var call = apiService.getGames()
        call.enqueue(object: Callback<GamesResponse> {
            override fun onResponse(call: Call<GamesResponse>, response: Response<GamesResponse>) {
                if(response.code() == 200) {
                    var myResponse = response.body()
                    //print(myResponse)
                    //initRecyclerView(myResponse!!.results)
                }
            }
            override fun onFailure(call: Call<GamesResponse>, t: Throwable) {
                print("sıçış")
            }
        })*/

        addScrollListener()
        initListeners(adapter)
        loadFirstPage()



        /*gameList = arrayListOf<Game>()
        var game1 = Game(R.drawable.a,"Grand Theft Auto V", 96, "Action, shooter")
        var game2 = Game(R.drawable.b,"Portal 2", 95, "Action, puzzle")
        var game3 = Game(R.drawable.c,"The Witcher 3: Wild Hunt", 89, "Action, puzzle")
        var game4 = Game(R.drawable.d,"Left 4 Dead 2", 89, "Action, puzzle")
        gameList.add(game1)
        gameList.add(game2)
        gameList.add(game3)
        gameList.add(game4)

        val layoutManager = LinearLayoutManager(context)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.setHasFixedSize(true)
        adapter = Rv_adapter(gameList, true)
        binding.recyclerView.adapter = adapter


        binding.searchField.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText)
                return true
            }
        })

    }

    private fun filterList(query : String?) {
        if(query != null) {
            val filteredList = ArrayList<Game>()

            for(item in gameList) {
                if(item.name.lowercase(Locale.ROOT).contains(query.lowercase()) ) {
                    filteredList.add(item)
                }
            }

            if(filteredList.isEmpty()) {
                adapter.setFilteredList(filteredList)
                binding.noGameHas.setText("This game doesn't exist")
            }
            else {
                adapter.setFilteredList(filteredList)
            }
        }*/

    }

    /*fun initRecyclerView(gameList : List<ResultGame>) {

        binding.recyclerView.layoutManager = LinearLayoutManager(binding.recyclerView.context)  // Context?
        binding.recyclerView.adapter = Rv_adapter(gameList, true)

    }*/

    private fun loadNextPage() {
        apiService.getGames(page = currentPage).enqueue(object: Callback<GamesResponse>{
            override fun onResponse(call: Call<GamesResponse>, response: Response<GamesResponse>) {
                if (response.isSuccessful){
                    resultList.addAll(response.body()!!.results.toMutableList())
                    tempGameList.addAll(response.body()!!.results.toMutableList())
                    isLoading = false
                    adapter.notifyDataSetChanged()

                    if (currentPage == TOTAL_PAGES){
                        isLastPage = true
                    }
                }
            }

            override fun onFailure(call: Call<GamesResponse>, t: Throwable) { t.printStackTrace() }
        })
    }

    private fun addScrollListener(){
        binding.recyclerView.addOnScrollListener(object: PaginationScrollListener(linearLayoutManager) {
            override fun loadMoreItems() {
                isLoading = true
                currentPage += 1
                loadNextPage()}

            override fun isLastPage(): Boolean {
                return isLastPage
            }

            override fun isLoading(): Boolean {
                return isLoading
            }
        })
    }

    private fun loadFirstPage() {
        apiService.getGames().enqueue(object: Callback<GamesResponse>{
            override fun onResponse(call: Call<GamesResponse>, response: Response<GamesResponse>) {
                if (response.isSuccessful){
                    resultList.addAll(response.body()!!.results.toMutableList())
                    tempGameList.addAll(response.body()!!.results.toMutableList())
                    adapter.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<GamesResponse>, t: Throwable) { t.printStackTrace() }
        })
    }

    private fun initListeners(adapter: Rv_adapter) {
        binding.searchField.setOnQueryTextListener(object: android.widget.SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                val searchText = query!!.toLowerCase(Locale.getDefault())
                if (searchText.length >= 3){
                    apiService.search(searchText).enqueue(object: Callback<GamesResponse>{
                        override fun onResponse(call: Call<GamesResponse>, response: Response<GamesResponse>) {
                            if (response.isSuccessful){
                                binding.recyclerView.clearOnScrollListeners()
                                tempGameList.clear()
                                tempGameList.addAll(response.body()!!.results.toMutableList())
                                binding.recyclerView.adapter = adapter
                                adapter.notifyDataSetChanged()
                            }
                        }
                        override fun onFailure(call: Call<GamesResponse>, t: Throwable) {
                            t.printStackTrace() }
                    }
                    )
                    if (tempGameList.isEmpty()){

                    }
                }else{
                    tempGameList.clear()
                    tempGameList.addAll(resultList)
                    adapter.notifyDataSetChanged()
                }
                return false }

            override fun onQueryTextChange(newText: String?): Boolean {
                tempGameList.clear()
                val searchText = newText!!.toLowerCase(Locale.getDefault())
                binding.recyclerView.adapter = adapter
                if (searchText.length >= 3){
                    binding.recyclerView.clearOnScrollListeners()
                    resultList.forEach {
                        if (it.name.toLowerCase().contains(searchText)){
                            tempGameList.add(it)
                        }
                    }
                    if (tempGameList.isEmpty()){

                    }

                    adapter.notifyDataSetChanged()
                }else{
                    addScrollListener()
                    tempGameList.clear()
                    tempGameList.addAll(resultList)
                    adapter.notifyDataSetChanged()
                }
                return false
            }
        })
    }

}
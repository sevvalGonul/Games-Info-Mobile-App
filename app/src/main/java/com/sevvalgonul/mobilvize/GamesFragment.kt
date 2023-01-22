package com.sevvalgonul.mobilvize

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.sevvalgonul.mobilvize.databinding.FragmentGamesBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class GamesFragment : Fragment() {
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var apiService: GamesApiService
    private lateinit var binding : FragmentGamesBinding
    private lateinit var rvAdapter : Rv_adapter
    private val PAGE_START = 1
    private val TOTAL_PAGES = 20
    private var currentPage = PAGE_START
    //private var linearLayoutManager
    private var isLoading = false
    private var isLastPage = false

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
        println("gameFragment onViewCreated")
        apiService = GamesApiService.getInstance()

        initRecyclerView(GameModel.getTempGameList())


        /*
        var call = apiService.getGames()
        //aşağıda Liste halinde gamesler alınıyor
        call.enqueue(object: Callback<GamesResponse> {
            override fun onResponse(call: Call<GamesResponse>, response: Response<GamesResponse>) {
                if(response.code() == 200) {
                    var myResponse = response.body()
                    //print(myResponse)
                    FavoriteModel.setGameList(myResponse!!.results)
                    initRecyclerView(myResponse!!.results)
                }
            }
            override fun onFailure(call: Call<GamesResponse>, t: Throwable) {

            }
        })

*/

        addScrollListener()
        loadFirstPage()
        //initListeners(rvAdapter)



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

    fun initRecyclerView(gameList : List<ResultGame>) {
        //linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        linearLayoutManager = LinearLayoutManager(binding.recyclerView.context, LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.layoutManager = linearLayoutManager  // Context?
        binding.recyclerView.setHasFixedSize(true)
        rvAdapter  = Rv_adapter(gameList, true)
        binding.recyclerView.adapter = rvAdapter

    }

    private fun addScrollListener(){
        println("addScrollListener")
        //binding.recyclerView.addOnScrollListener(dene1)

        binding.recyclerView.addOnScrollListener(object: PaginationScrollListener(linearLayoutManager) {

            override fun loadMoreItems() {
                isLoading = true
                currentPage += 1
                println("addScrollListener.loadMoreItems currentPage=" + currentPage)
                loadNextPage()
            }

            override fun isLastPage(): Boolean {
                println("addScrollListener.isLastPage=" + isLastPage)
                return isLastPage
            }

            override fun isLoading(): Boolean {
                println("addScrollListener.isLoading=" + isLoading)
                return isLoading
            }
        })
    }

    private fun loadFirstPage() {
        apiService.getGames().enqueue(object: Callback<GamesResponse>{
            override fun onResponse(call: Call<GamesResponse>, response: Response<GamesResponse>) {
                if (response.isSuccessful){
                    //resultList.addAll(response.body()!!.results.toMutableList())
                    //tempGameList.addAll(response.body()!!.results.toMutableList())
                    //**GameModel.addGameList(response.body()!!.results)
                    GameModel.setTempGameList(response.body()!!.results)
                    //initRecyclerView(GameModel.getTempGameList())
                    rvAdapter.setTempGameList(GameModel.getTempGameList())
                    rvAdapter.notifyDataSetChanged()
                    println("loadFirstPage ")
                }
            }

            override fun onFailure(call: Call<GamesResponse>, t: Throwable) { t.printStackTrace() }
        })
    }

    private fun loadNextPage() {
        try {
            apiService.getGames(page = currentPage).enqueue(object : Callback<GamesResponse> {
                override fun onResponse(
                    call: Call<GamesResponse>,
                    response: Response<GamesResponse>
                ) {
                    println("response.isSuccessful=" + response.isSuccessful)
                    if (response.isSuccessful) {
                        //resultlist tüm verileri saklarrrr//altta result list add all vardı
                        //**GameModel.addGameList(response.body()!!.results)
                        GameModel.setTempGameList(response.body()!!.results)
                        isLoading = false
                        println("response.isSuccessful")
                        rvAdapter.setTempGameList(GameModel.getTempGameList())
                        rvAdapter.notifyDataSetChanged()

                        if (currentPage == TOTAL_PAGES) {
                            isLastPage = true
                        }
                    } else {
                        println("response.isSuccessful = false")
                    }
                }

                override fun onFailure(call: Call<GamesResponse>, t: Throwable) {
                    t.printStackTrace()
                }
            })
        } catch ()
    }



}
package com.sevvalgonul.mobilvize

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
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
    private val MIN_SEARCH_TEXT_LENGTH = 3
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

        initRecyclerView(GameModel.getGameList())


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
        ilkAcilistaCalistir()
        initSearchListeners(rvAdapter)

/*
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

    private fun initSearchListeners(rvAdapter: Rv_adapter) {
        binding.searchField.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                println("initSearchListeners.onQueryTextSubmit")
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                println("initSearchListeners.onQueryTextChange newTextlength=" + newText!!.length)
                if (newText!!.length > MIN_SEARCH_TEXT_LENGTH) {
                    val searchText = newText!!.lowercase()
                    apiService.search(searchText).enqueue(object : Callback<GamesResponse> {
                        override fun onResponse(
                            call: Call<GamesResponse>,
                            response: Response<GamesResponse>
                        ) {  // update the tempGameList in order to show search results in recyclerView
                            binding.recyclerView.clearOnScrollListeners()
                            /*
                            tempGameList.clear()
                            tempGameList.addAll(response.body()!!.results.toMutableList())*/
                            GameModel.setSearchList(response.body()!!.results)

                            rvAdapter.setGameList(GameModel.getSearchList())
                            rvAdapter.notifyDataSetChanged()
                        }

                        override fun onFailure(call: Call<GamesResponse>, t: Throwable) {
                            println("onQueryTextChange  onFailure")
                        }

                    })
                }

                if (newText!!.length == (MIN_SEARCH_TEXT_LENGTH) ) {

                    //3ten az olunca geri games list gelir

                    addScrollListener()
                    rvAdapter.setGameList(GameModel.getGameList())
                    rvAdapter.notifyDataSetChanged()

                }

                return true
            }
        })
    }

    private fun ilkAcilistaCalistir() {
        if (!SingleVar.isFirstRun()) {
            loadFirstPage()
            SingleVar.setFirstRun(true)
        }
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
                try {
                    loadNextPage()
                } catch (ex: Exception) {
                    println("loadMoreItems ex: Exception=")
                    isLoading = false
                }
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
        try {
        apiService.getGames().enqueue(object: Callback<GamesResponse>{
            override fun onResponse(call: Call<GamesResponse>, response: Response<GamesResponse>) {
                if (response.isSuccessful){
                    //resultList.addAll(response.body()!!.results.toMutableList())
                    //tempGameList.addAll(response.body()!!.results.toMutableList())
                    //**GameModel.addGameList(response.body()!!.results)
                    GameModel.addGameList(response.body()!!.results as ArrayList<ResultGame>)
                    //initRecyclerView(GameModel.getTempGameList())
                    rvAdapter.setGameList(GameModel.getGameList())
                    rvAdapter.notifyDataSetChanged()
                    println("loadFirstPage ")
                }
            }

            override fun onFailure(call: Call<GamesResponse>, t: Throwable) {
                println("loadFirstPage onFailure" )
//                isLoading = false
                t.printStackTrace()
            }
        })
        } catch (ex: Exception) {
            println("loadNextPage ex: Exception=" )
//            isLoading = false
        }
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
                        GameModel.addGameList(response.body()!!.results as ArrayList<ResultGame>)
                        isLoading = false
                        println("response.isSuccessful")
                        rvAdapter.setGameList(GameModel.getGameList())
                        rvAdapter.notifyDataSetChanged()

                        if (currentPage == TOTAL_PAGES) {
                            isLastPage = true
                        }
                    } else {
                        println("response.isSuccessful = false")
                    }
                }

                override fun onFailure(call: Call<GamesResponse>, t: Throwable) {
                    println("loadNextPage onFailure")
                    isLoading = false
                    t.printStackTrace()
                }
            })
        } catch (ex: Exception) {
            println("loadNextPage ex: Exception=" )
            isLoading = false
        }
    }



}
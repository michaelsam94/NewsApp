package com.example.newsapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.R
import com.example.newsapp.data.model.Article
import com.example.newsapp.databinding.FragmentNewsBinding
import com.example.newsapp.utils.Resource
import com.example.newsapp.utils.gone
import com.example.newsapp.utils.show
import com.example.newsapp.utils.showToast
import org.koin.androidx.viewmodel.ext.android.viewModel

class NewsFragment : Fragment(R.layout.fragment_news),NewsAdapter.Interaction {

    val viewModel: NewsViewModel by viewModel()
    
    private lateinit var binding: FragmentNewsBinding

    private val newsAdapter by lazy { NewsAdapter(this) }


    private var page = 1
    private var maxPage = 5

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewsBinding.inflate(layoutInflater,container,false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeToNewsLiveData()
    }

    private fun observeToNewsLiveData() {
        viewModel.getNews().observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Error -> {
                    binding.progressBar.gone()
                    binding.swipeRefresh.isRefreshing = false
                    it.msg?.let { msg -> showToast(msg) }
                }
                is Resource.Loading -> binding.progressBar.show()
                is Resource.Success -> {
                    if (it.data != null) {
                        binding.progressBar.gone()
                        binding.swipeRefresh.isRefreshing = false
                        newsAdapter.addArticlesToList(it.data) // add the call from api to list in memory to search
                    }
                }
            }
        }
    }

    private fun setupRecyclerView() {
        binding.swipeRefresh.apply {
            setOnRefreshListener {
                page= 1
                newsAdapter.clearArticles()
                viewModel.getHomeNews(page)
                observeToNewsLiveData()
            }
        }

        binding.newsRecycler.apply {
            adapter = newsAdapter
        }
        binding.newsRecycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val pastVisiblesItems: Int
                val visibleItemCount: Int
                val totalItemCount: Int
                var loading = true
                if (dy > 0) { //check for scroll down
                    visibleItemCount = binding.newsRecycler.layoutManager?.childCount ?: 0
                    totalItemCount = binding.newsRecycler.layoutManager?.itemCount ?: 0
                    pastVisiblesItems = (binding.newsRecycler.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()

                    if (loading) {
                        if (page < maxPage && (visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                            loading = false
                            page++
                            viewModel.getHomeNews(page)
                            loading = true
                        }
                    }
                }
            }
        })
    }


    override fun onItemSelected(position: Int, item: Article) {
        val action = NewsFragmentDirections.actionNewsFragmentToDetailsFragment(item)
        findNavController().navigate(action)
    }


}
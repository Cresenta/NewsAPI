package id.indocyber.newsapi.fragment.articles

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import dagger.hilt.android.AndroidEntryPoint
import id.indocyber.common.base_class.BaseFragment
import id.indocyber.common.extension.setGone
import id.indocyber.common.extension.setVisible
import id.indocyber.newsapi.R
import id.indocyber.newsapi.databinding.FragmentArticlesBinding
import id.indocyber.newsapi.view_model.ArticlesViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ArticlesFragment : BaseFragment<ArticlesViewModel, FragmentArticlesBinding>() {
    override val vm: ArticlesViewModel by viewModels()
    override val layoutResourceId = R.layout.fragment_articles
    private val adapter = ArticlesPagingAdapter()
    private val loadStateAdapter = ArticlesPagingStateAdapter(::retry)
    lateinit var selectedSourceIds: String

    override fun initBinding(binding: FragmentArticlesBinding) {
        binding.recycler.adapter = adapter.withLoadStateFooter(loadStateAdapter)
        selectedSourceIds = ArticlesFragmentArgs.fromBundle(arguments as Bundle).selectedSources

        adapter.addLoadStateListener {
            val list = adapter.snapshot()
            when (if (list.isEmpty()) it.refresh else it.append) {
                is LoadState.Error -> {
                    if (list.isEmpty()) {
                        binding.tvNoArticleInfo.setGone()
                        binding.btnRetry.setVisible()
                        binding.recycler.setGone()
                    } else {
                        binding.tvNoArticleInfo.setGone()
                        binding.btnRetry.setGone()
                        binding.recycler.setVisible()
                    }
                    Log.i("LoadStateObserver", "Ext LoadState Error")
                }
                is LoadState.NotLoading -> {
                    if (list.isEmpty()) {
                        binding.tvNoArticleInfo.text = "No article found"
                        binding.tvNoArticleInfo.setVisible()
                        binding.btnRetry.setGone()
                        binding.recycler.setGone()
                    } else {
                        binding.tvNoArticleInfo.setGone()
                        binding.btnRetry.setGone()
                        binding.recycler.setVisible()
                    }
                    Log.i("LoadStateObserver", "Ext LoadState NotLoading")
                }
                is LoadState.Loading -> {
                    if (list.isEmpty()) {
                        binding.tvNoArticleInfo.setGone()
                        binding.btnRetry.setGone()
                        binding.recycler.setGone()
                    } else {
                        binding.tvNoArticleInfo.setGone()
                        binding.btnRetry.setGone()
                        binding.recycler.setVisible()
                    }
                    Log.i("LoadStateObserver", "Ext LoadState Loading")
                }
            }
        }

        if (vm.pagingData.value == null) {
            vm.getArticles(selectedSourceIds)
        }
        binding.btnRetry.setOnClickListener {
            vm.getArticles(selectedSourceIds)
        }
        vm.pagingData.observe(this) {
            CoroutineScope(Dispatchers.Main).launch {
                adapter.submitData(it)
            }
        }
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.option_menu, menu)

        val searchMenuItem = menu.findItem(R.id.search)
        val searchView = searchMenuItem.actionView as SearchView
        searchView.maxWidth = R.style.max_width
        searchView.queryHint = "Search articles"
        if (vm.searchText.isNotEmpty()) {
            searchMenuItem.expandActionView()
            searchView.setQuery(vm.searchText, true)
            searchView.clearFocus()
        }
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            var timer: CountDownTimer? = null
            val waitingTime = 2000L
            override fun onQueryTextSubmit(query: String): Boolean {
                searchView.clearFocus()
                return true
            }
            override fun onQueryTextChange(newText: String): Boolean {
                vm.searchText = newText
                timer?.cancel()
                timer = object : CountDownTimer(waitingTime, 500) {
                    override fun onTick(millisUntilFinished: Long) {
                        Log.d("TIME", "seconds remaining: " + millisUntilFinished / 1000)
                    }

                    override fun onFinish() {
                        vm.getArticles(selectedSourceIds, vm.searchText)
                        Log.d("FINISHED", "DONE")
                    }
                }
                (timer as CountDownTimer).start()
                return false
            }
        })
    }

    private fun retry() = adapter.retry()
}
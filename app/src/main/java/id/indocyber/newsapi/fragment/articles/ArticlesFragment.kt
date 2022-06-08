package id.indocyber.newsapi.fragment.articles

import android.os.Bundle
import android.util.Log
import android.view.inputmethod.EditorInfo
import androidx.core.widget.addTextChangedListener
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

    override fun initBinding(binding: FragmentArticlesBinding) {
        binding.recycler.adapter = adapter.withLoadStateFooter(loadStateAdapter)
        binding.textInputSearch.addTextChangedListener {
            vm.searchText = it.toString()
        }

        val selectedSourceIds = ArticlesFragmentArgs.fromBundle(arguments as Bundle).selectedSources
        binding.textInputSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                vm.getArticles(
                    sources = selectedSourceIds,
                    q = vm.searchText
                )
            }
            false
        }

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

        vm.getArticles(selectedSourceIds)
        binding.btnRetry.setOnClickListener {
            vm.getArticles(selectedSourceIds)
        }
        vm.pagingData.observe(this) {
            CoroutineScope(Dispatchers.Main).launch {
                adapter.submitData(it)
            }
        }
    }

    private fun retry() = adapter.retry()
}
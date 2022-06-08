package id.indocyber.newsapi.fragment.articles

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import id.indocyber.common.extension.setGone
import id.indocyber.common.extension.setVisible
import id.indocyber.newsapi.databinding.ArticlesItemStateLayoutBinding

class ArticlesPagingStateAdapter(
    val retryCallback: () -> Unit
) : LoadStateAdapter<ArticlesPagingStateAdapter.ArticleStateViewHolder>() {

    inner class ArticleStateViewHolder(
        private val binding: ArticlesItemStateLayoutBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(loadState: LoadState) {
            binding.retryContent.setOnClickListener {
                retryCallback()
            }
            when (loadState) {
                is LoadState.Error -> {
                    binding.loadingContent.setGone()
                    binding.retryContent.setVisible()
                    Log.i("LoadStateObserver", "Adapter LoadState Error")
                }
                is LoadState.Loading -> {
                    binding.loadingContent.setVisible()
                    binding.retryContent.setGone()
                    Log.i("LoadStateObserver", "Adapter LoadState Loading")
                }
                is LoadState.NotLoading -> {
                    binding.loadingContent.setGone()
                    binding.retryContent.setGone()
                    Log.i("LoadStateObserver", "Adapter LoadState NotLoading")
                }
            }
        }
    }

    override fun onBindViewHolder(holder: ArticleStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): ArticleStateViewHolder {
        return ArticleStateViewHolder(
            ArticlesItemStateLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
}
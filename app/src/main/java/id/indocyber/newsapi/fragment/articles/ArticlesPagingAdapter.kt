package id.indocyber.newsapi.fragment.articles

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import id.indocyber.common.entity.everything.Article
import id.indocyber.common.extension.loadImage
import id.indocyber.newsapi.databinding.ArticlesItemLayoutBinding

class ArticlesPagingAdapter : PagingDataAdapter<Article, ArticlesPagingAdapter.ArticleViewHolder>(
    differ
) {
    inner class ArticleViewHolder(
        private val binding: ArticlesItemLayoutBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(article: Article?) {
            binding.tvTitle.text = article?.title
            binding.tvDescription.text = article?.description
            binding.imgArticle.loadImage(article?.urlToImage)
            binding.root.setOnClickListener {
                val toArticleWebViewFragment = ArticlesFragmentDirections.actionArticlesFragmentToArticleWebViewFragment(
                    url = article?.url.orEmpty()
                )
                it.findNavController().navigate(toArticleWebViewFragment)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        return ArticleViewHolder(
            ArticlesItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object{
        val differ = object: DiffUtil.ItemCallback<Article>() {
            override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem.url == newItem.url
            }

            override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem == newItem
            }
        }
    }
}
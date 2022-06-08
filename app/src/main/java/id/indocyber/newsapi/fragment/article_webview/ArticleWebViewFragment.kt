package id.indocyber.newsapi.fragment.article_webview

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import dagger.hilt.android.AndroidEntryPoint
import id.indocyber.newsapi.R
import id.indocyber.newsapi.databinding.FragmentArticleWebViewBinding

@AndroidEntryPoint
class ArticleWebViewFragment : Fragment() {
    private val layoutResourceId: Int = R.layout.fragment_article_web_view
    lateinit var binding: FragmentArticleWebViewBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, layoutResourceId, container, false)
        val articleUrl = ArticleWebViewFragmentArgs.fromBundle(arguments as Bundle).url
        binding.webArticle.loadUrl(articleUrl)
        return binding.root
    }
}
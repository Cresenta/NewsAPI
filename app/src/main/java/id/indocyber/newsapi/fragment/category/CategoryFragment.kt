package id.indocyber.newsapi.fragment.category

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import id.indocyber.newsapi.R
import id.indocyber.newsapi.databinding.FragmentCategoryBinding
import id.indocyber.newsapi.view_model.CategoryViewModel

@AndroidEntryPoint
class CategoryFragment : Fragment() {
    private val vm: CategoryViewModel by viewModels()
    private lateinit var binding: FragmentCategoryBinding
    private val layoutResourceId = R.layout.fragment_category
    private val adapter = CategoryAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, layoutResourceId, container, false)
        binding.lifecycleOwner = this
        binding.recycler.adapter = adapter
        vm.getCategories()
        vm.getCategoriesState.observe(viewLifecycleOwner) {
            adapter.submitCategoryList(it)
        }
        return binding.root
    }
}
package id.indocyber.newsapi.fragment.category

import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import id.indocyber.common.base_class.BaseFragment
import id.indocyber.newsapi.R
import id.indocyber.newsapi.databinding.FragmentCategoryBinding
import id.indocyber.newsapi.view_model.CategoryViewModel

@AndroidEntryPoint
class CategoryFragment : BaseFragment<CategoryViewModel, FragmentCategoryBinding>() {
    override val vm: CategoryViewModel by viewModels()
    override val layoutResourceId = R.layout.fragment_category
    private val adapter = CategoryAdapter()

    override fun initBinding(binding: FragmentCategoryBinding) {
        binding.recycler.adapter = adapter
        vm.getCategoriesState.observe(viewLifecycleOwner) {
            adapter.submitCategoryList(it)
        }
    }
}
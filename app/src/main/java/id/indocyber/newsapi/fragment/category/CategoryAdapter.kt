package id.indocyber.newsapi.fragment.category

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import id.indocyber.newsapi.databinding.CategoryItemLayoutBinding

class CategoryAdapter() : RecyclerView.Adapter<CategoryAdapter.CategoryItemViewHolder>() {

    inner class CategoryItemViewHolder(
        private val binding: CategoryItemLayoutBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(category: String) {
            binding.btnCategory.text = category
//            binding.btnCategory.setOnClickListener {
//                val intent = Intent(context, SourcesActivity::class.java)
//                intent.putExtra(
//                    SourcesActivity.Constants.EXTRA_SELECTED_CATEGORY,
//                    category
//                )
//                context.startActivity(intent)
//            }
        }
    }

    private val categories = AsyncListDiffer(this, differ)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryItemViewHolder {
        return CategoryItemViewHolder(
            CategoryItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CategoryItemViewHolder, position: Int) {
        holder.bind(categories.currentList[position])
    }

    override fun getItemCount(): Int = categories.currentList.size

    fun submitCategoryList(list: List<String>) {
        categories.submitList(list)
    }

    companion object{
        val differ = object: DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }
        }
    }
}
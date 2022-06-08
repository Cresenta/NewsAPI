package id.indocyber.newsapi.fragment.sources

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import id.indocyber.common.entity.sources.Source
import id.indocyber.newsapi.databinding.SourcesItemLayoutBinding

class SourcesAdapter(
    private val getSelectedSources: () -> MutableList<String>,
    private val setNextButtonVisibility: () -> Unit
) : RecyclerView.Adapter<SourcesAdapter.SourcesItemViewHolder>() {

    inner class SourcesItemViewHolder(
        private val binding: SourcesItemLayoutBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(source: Source) {
            val selectedSourcesIds = getSelectedSources()
            binding.tvName.text = source.name
            binding.tvDescription.text = source.description
            binding.root.setOnClickListener {
                when (source.id) {
                    in selectedSourcesIds -> {
                        selectedSourcesIds.remove(source.id)
                    }
                    else -> {
                        selectedSourcesIds.add(source.id)
                    }
                }
                setNextButtonVisibility()
                notifyItemChanged(absoluteAdapterPosition)
            }
            if (source.id in selectedSourcesIds) {
                binding.cardView.setCardBackgroundColor(Color.BLUE)
                binding.tvName.setTextColor(Color.WHITE)
                binding.tvDescription.setTextColor(Color.WHITE)
            } else {
                binding.cardView.setCardBackgroundColor(Color.TRANSPARENT)
                binding.tvName.setTextColor(Color.BLACK)
                binding.tvDescription.setTextColor(Color.BLACK)
            }
        }
    }

    private val sources = AsyncListDiffer(this, differ)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SourcesItemViewHolder {
        return SourcesItemViewHolder(
            SourcesItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: SourcesItemViewHolder, position: Int) {
        holder.bind(sources.currentList[position])
    }

    override fun getItemCount(): Int = sources.currentList.size

    fun submitSourceList(list: List<Source>) {
        sources.submitList(list)
    }

    companion object{
        val differ = object: DiffUtil.ItemCallback<Source>() {
            override fun areItemsTheSame(oldItem: Source, newItem: Source): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Source, newItem: Source): Boolean {
                return oldItem == newItem
            }
        }
    }
}
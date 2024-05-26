package com.example.ui_browser_setting

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ui_browser_setting.databinding.ItemLayoutBinding

class MyAdapter(
    private val items: MutableList<String>,
    private val context: Context,
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    private val expandedPositions = mutableSetOf<Int>()

    inner class ViewHolder(val binding: ItemLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.binding.textViewUrl.text = item

        val isExpanded = expandedPositions.contains(position)
        holder.binding.subItemLayout.visibility = if (isExpanded) View.VISIBLE else View.GONE
        holder.binding.buttonDown.text = if (isExpanded) "Up" else "Down"

        holder.binding.buttonDown.setOnClickListener {
            listener.onDownClick(position, isExpanded)
            if (isExpanded) {
                expandedPositions.remove(position)
            } else {
                expandedPositions.add(position)
            }
            notifyItemChanged(position)
        }

        holder.binding.buttonEdit.setOnClickListener {
            listener.onEditClick(position)
        }

        holder.binding.buttonDelete.setOnClickListener {
            listener.onDeleteClick(position)
        }

        holder.binding.buttonChromeCustomTab.setOnClickListener {
            listener.onChromeCustomTabClick(item)
        }

        holder.binding.buttonChrome.setOnClickListener {
            listener.onChromeClick(item)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}

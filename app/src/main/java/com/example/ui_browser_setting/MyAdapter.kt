package com.example.ui_browser_setting

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ui_browser_setting.databinding.ItemLayoutBinding

class MyAdapter(
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    private var items: MutableList<UrlData>? = null
    fun setItems(newItems: MutableList<UrlData>) {
        items = newItems
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ItemLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items?.get(position)
        holder.binding.textViewUrl.text = item?.url

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
            item?.url?.let { it1 -> listener.onChromeCustomTabClick(it1) }
        }

        holder.binding.buttonChrome.setOnClickListener {
            item?.url?.let { it1 -> listener.onChromeClick(it1) }
        }
    }

    private val expandedPositions = mutableSetOf<Int>()

    override fun getItemCount(): Int {
        return items?.size ?: 0
    }
}


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(private var items: MutableList<String>, private val itemClickListener: (String, Int) -> Unit) : RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(android.R.layout.simple_list_item_1, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text = items[position]
        holder.itemView.setOnClickListener {
            itemClickListener(items[position], position)
        }
    }

    override fun getItemCount(): Int = items.size

    fun updateList(newItems: List<String>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    fun updateItem(newItem: String, position: Int) {
        items.removeAt(position)
        items.add(0, newItem)
        notifyDataSetChanged() // Notify the adapter of the change
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(android.R.id.text1)
    }
}


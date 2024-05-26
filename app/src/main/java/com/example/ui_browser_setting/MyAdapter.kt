package com.example.ui_browser_setting

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.RecyclerView
import com.example.ui_browser_setting.databinding.ItemLayoutBinding

class MyAdapter(private val items: List<String>, private val context: Context) : RecyclerView.Adapter<MyAdapter.ViewHolder>() {

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
            if (isExpanded) {
                expandedPositions.remove(position)
            } else {
                expandedPositions.add(position)
            }
            notifyItemChanged(position)
        }

        holder.binding.buttonEdit.setOnClickListener {
            // Handle edit action
        }

        holder.binding.buttonDelete.setOnClickListener {
            // Handle delete action
        }

        holder.binding.buttonChromeCustomTab.setOnClickListener {
            val url = item
            val builder = CustomTabsIntent.Builder()
            val customTabsIntent = builder.build()
            customTabsIntent.launchUrl(context, Uri.parse(url))
        }

        holder.binding.buttonChrome.setOnClickListener {
            val url = item
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            context.startActivity(browserIntent)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}

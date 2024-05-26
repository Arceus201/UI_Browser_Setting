package com.example.ui_browser_setting

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(private val items: List<String>, private val context: Context) : RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    private val expandedPositions = mutableSetOf<Int>()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewUrl: TextView = itemView.findViewById(R.id.textViewUrl)
        val buttonDown: Button = itemView.findViewById(R.id.buttonDown)
        val subItemLayout: View = itemView.findViewById(R.id.subItemLayout)
        val buttonEdit: Button = itemView.findViewById(R.id.buttonEdit)
        val buttonDelete: Button = itemView.findViewById(R.id.buttonDelete)
        val buttonChromeCustomTab: Button = itemView.findViewById(R.id.buttonChromeCustomTab)
        val buttonChrome: Button = itemView.findViewById(R.id.buttonChrome)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textViewUrl.text = items[position]

        val isExpanded = expandedPositions.contains(position)
        holder.subItemLayout.visibility = if (isExpanded) View.VISIBLE else View.GONE
        holder.buttonDown.text = if (isExpanded) "Up" else "Down"

        holder.buttonDown.setOnClickListener {
            if (isExpanded) {
                expandedPositions.remove(position)
            } else {
                expandedPositions.add(position)
            }
            notifyItemChanged(position)
        }

        holder.buttonEdit.setOnClickListener {
            // Handle edit action
        }

        holder.buttonDelete.setOnClickListener {
            // Handle delete action
        }

        holder.buttonChromeCustomTab.setOnClickListener {
            val url = holder.textViewUrl.text.toString()
            val builder = CustomTabsIntent.Builder()
            val customTabsIntent = builder.build()
            customTabsIntent.launchUrl(context, Uri.parse(url))
        }

        holder.buttonChrome.setOnClickListener {
            val url = holder.textViewUrl.text.toString()
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            context.startActivity(browserIntent)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}

package com.example.ui_browser_setting

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ui_browser_setting.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), OnItemClickListener, EditUrlDialogFragment.EditUrlDialogListener {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private lateinit var adapter: MyAdapter
    private  var items: MutableList<UrlData>?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        items = if (savedInstanceState == null) {
            mutableListOf()
        } else {
            savedInstanceState.getParcelableArrayList("items")
        }


        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = MyAdapter(this)
        recyclerView.adapter = adapter

        binding.buttonAddUrl.setOnClickListener {
            addUrl()
        }

        // Sự kiện khi thay đổi trên SearchView
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filter(newText)
                return true
            }
        })
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList("items", items?.let { ArrayList(it) })
    }

    // Lọc danh sách URL dựa trên từ khóa từ SearchView
    private fun filter(text: String?) {
        val filteredItems = mutableListOf<UrlData>()
        if (text.isNullOrBlank()) {
            items?.let { filteredItems.addAll(it) }
        } else {
            val searchText = text.trim().lowercase()
            items?.forEach { url ->
                if (url.url?.lowercase()?.contains(searchText) == true) {
                    filteredItems.add(url)
                }
            }
        }
        adapter.setItems(filteredItems)
    }

    private fun addUrl() {
        val newUrl = binding.editTextUrl.text.toString().trim()
        if (newUrl.isNotEmpty()) {
            items?.add(UrlData(newUrl))
            items?.let { adapter.setItems(it) }
            adapter.notifyItemInserted((items?.size ?: 0) - 1)
            binding.editTextUrl.text.clear()
        }
    }

    override fun onEditClick(position: Int) {
        val url = items?.get(position)?.url ?: ""
        // Kiểm tra xem một DialogFragment với cùng tag đã được hiển thị hay chưa
        val existingFragment = supportFragmentManager.findFragmentByTag("EditUrlDialog")
        if (existingFragment == null) {
            val dialogFragment = EditUrlDialogFragment.newInstance(position, url)
            dialogFragment.show(supportFragmentManager, "EditUrlDialog")
        }
    }

    override fun onDeleteClick(position: Int) {
        val url = items?.get(position)?.url ?: ""
        AlertDialog.Builder(this)
            .setTitle("Bạn có chắc chắn muốn xóa?")
            .setMessage("URL: $url")
            .setPositiveButton("OK") { dialog, _ ->
                items?.removeAt(position)
                adapter.notifyItemRemoved(position)
                items?.let { adapter.notifyItemRangeChanged(position, it.size) }
                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
    }

    override fun onChromeCustomTabClick(url: String) {
        val builder = CustomTabsIntent.Builder()
        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(this, Uri.parse(url))
    }

    override fun onChromeClick(url: String) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(browserIntent)
    }

    override fun onDownClick(position: Int, isExpanded: Boolean) {
        // Handle down/up click if necessary
    }

    override fun onDialogPositiveClick(position: Int, newUrl: String) {
        items?.get(position)?.url = newUrl
        adapter.notifyItemChanged(position)
    }
}
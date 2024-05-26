package com.example.ui_browser_setting

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ui_browser_setting.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), OnItemClickListener, EditUrlDialogFragment.EditUrlDialogListener {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private lateinit var adapter: MyAdapter
    private val items = mutableListOf(
        "http://example.com/1",
        "http://example.com/2",
        "http://example.com/3"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = MyAdapter(items, this, this)
        recyclerView.adapter = adapter

        binding.buttonAddUrl.setOnClickListener {
            addUrl()
        }
    }

    private fun addUrl(){
        val newUrl = binding.editTextUrl.text.toString().trim()
        if (newUrl.isNotEmpty()) {
            items.add(newUrl)
            adapter.notifyItemInserted(items.size - 1)
            binding.editTextUrl.text.clear()
        }
    }

    override fun onEditClick(position: Int) {
        val url = items[position]

        // Kiểm tra xem một DialogFragment với cùng tag đã được hiển thị hay chưa
        val existingFragment = supportFragmentManager.findFragmentByTag("EditUrlDialog")
        if (existingFragment == null) {
            val dialogFragment = EditUrlDialogFragment.newInstance(position, url)
            dialogFragment.show(supportFragmentManager, "EditUrlDialog")
        }
    }


    override fun onDeleteClick(position: Int) {
        val url = items[position]
        AlertDialog.Builder(this)
            .setTitle("Bạn có chắc chắn muốn xóa?")
            .setMessage("URL: $url")
            .setPositiveButton("OK") { dialog, _ ->
                items.removeAt(position)
                adapter.notifyItemRemoved(position)
                adapter.notifyItemRangeChanged(position, items.size)
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
        items[position] = newUrl
        adapter.notifyItemChanged(position)
    }
}



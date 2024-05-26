package com.example.ui_browser_setting

interface OnItemClickListener {
    fun onEditClick(position: Int)
    fun onDeleteClick(position: Int)
    fun onChromeCustomTabClick(url: String)
    fun onChromeClick(url: String)
    fun onDownClick(position: Int, isExpanded: Boolean)
}

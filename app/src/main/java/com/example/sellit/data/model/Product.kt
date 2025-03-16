package com.example.sellit.data.model

// product model
data class Product(
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val price: Double = 0.0,
    val imagePath: String = "",
    val sellerId: String = "",
    val sellerName: String = "",
    val sellerEmail: String = "",
    val location: String = "",
    val timestamp: Long = System.currentTimeMillis()
)
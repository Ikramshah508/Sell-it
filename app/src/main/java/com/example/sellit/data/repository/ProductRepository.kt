package com.example.sellit.data.repository

import com.example.sellit.data.model.Product
import android.content.Context
import android.net.Uri
import com.example.sellit.utils.ImageUtils
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class ProductRepository(private val context: Context) {
    private val firestore = FirebaseFirestore.getInstance()
    private val productsCollection = firestore.collection("products")

    // post product from add product screen
    suspend fun saveProduct(product: Product) {
        try {
            productsCollection.document(product.id)
                .set(product)
                .await()
        } catch (e: Exception) {
            throw Exception("Failed to save product: ${e.message}")
        }
    }

    // get all products on home screen
    fun getAllProducts(): Flow<List<Product>> = flow {
        try {
            val snapshot = productsCollection
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .get()
                .await()

            val products = snapshot.documents.mapNotNull { it.toProduct() }
            emit(products)
        } catch (e: Exception) {
            emit(emptyList())
        }
    }

    // get product for particular user/seller
    fun getProductsBySeller(sellerId: String): Flow<List<Product>> = callbackFlow {
        val subscription = productsCollection
            .whereEqualTo("sellerId", sellerId)
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }

                if (snapshot != null) {
                    val products = snapshot.documents.mapNotNull { document ->
                        try {
                            Product(
                                id = document.getString("id") ?: "",
                                title = document.getString("title") ?: "",
                                description = document.getString("description") ?: "",
                                price = document.getDouble("price") ?: 0.0,
                                imagePath = document.getString("imagePath") ?: "",
                                sellerId = document.getString("sellerId") ?: "",
                                sellerName = document.getString("sellerName") ?: "",
                                location = document.getString("location") ?: "",
                                timestamp = document.getLong("timestamp")
                                    ?: System.currentTimeMillis()
                            )
                        } catch (e: Exception) {
                            null
                        }
                    }
                    trySend(products)
                }
            }

        awaitClose { subscription.remove() }
    }


    // get product by product id on product details screen
    suspend fun getProductById(productId: String): Product? {
        return try {
            val document = productsCollection.document(productId).get().await()
            document.toProduct()
        } catch (e: Exception) {
            null
        }
    }

    // reusable function to convert document data of firebase to product model
    private fun com.google.firebase.firestore.DocumentSnapshot.toProduct(): Product? {
        return try {
            Product(
                id = getString("id") ?: return null,
                title = getString("title") ?: return null,
                description = getString("description") ?: return null,
                price = getDouble("price") ?: return null,
                imagePath = getString("imagePath") ?: return null,
                sellerId = getString("sellerId") ?: return null,
                sellerName = getString("sellerName") ?: return null,
                location = getString("location") ?: return null,
                timestamp = getLong("timestamp") ?: System.currentTimeMillis()
            )
        } catch (e: Exception) {
            null
        }
    }
}

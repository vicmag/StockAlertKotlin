package com.store.domain.service

import com.store.domain.port.ProductRepository
import com.store.domain.port.NotificationService
import com.store.domain.exception.ProductNotFoundException
import com.store.domain.exception.InvalidAmountException
import com.store.domain.model.StockAlert
import com.store.domain.model.Product

class ProductService(
    private val productRepository: ProductRepository,
    private val notificationService: NotificationService? = null  
) {
    private val stockChangeSubscribers = mutableListOf<(Product, Int, Int) -> Unit>()

    fun decrementStock(productName: String, amount: Int) {

        val product = productRepository.findByName(productName)

        val oldStock = product.stock

        product.stock = product.stock - amount
        
        // 3. Guardar el producto actualizado
        productRepository.save(product)
        
        notifyStockChangeSubscribers(product, oldStock, product.stock)
    }

    fun subscribeToStockChanges(onStockChange: (Product, Int, Int) -> Unit) {
        stockChangeSubscribers.add(onStockChange)
    }

    private fun notifyStockChangeSubscribers(product: Product, oldStock: Int, newStock: Int) {
        stockChangeSubscribers.forEach { subscriber ->
            try {
                subscriber(product, oldStock, newStock)
            } catch (e: Exception) {
                // Manejar errores en suscriptores sin afectar el flujo principal
                println("Error en suscriptor: ${e.message}")
            }
        }
    }
}
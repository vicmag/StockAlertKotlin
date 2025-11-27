package com.store.domain.service

import com.store.domain.port.ProductRepository
import com.store.domain.port.NotificationService
import com.store.domain.exception.ProductNotFoundException
import com.store.domain.exception.InvalidAmountException
import com.store.domain.model.StockAlert

class ProductService(
    private val productRepository: ProductRepository,
    private val notificationService: NotificationService? = null  
) {
    fun decrementStock(productName: String, amount: Int) {
        // 1. Buscar producto por nombre
        val product = productRepository.findByName(productName)
        
        // 2. Decrementar el stock
        product.stock = product.stock - amount
        
        // 3. Guardar el producto actualizado
        productRepository.save(product)
        
        // 4. NUEVO: Verificar si se debe enviar alerta de stock bajo
        notificationService?.let { service ->
            if (product.stock <= product.minStockLevel) {
                val alert = StockAlert(
                    productName = product.name,
                    currentStock = product.stock,
                    minStockLevel = product.minStockLevel
                )
                service.sendLowStockAlert(alert)
            }
        }
    }
}
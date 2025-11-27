package com.store.domain.service

import com.store.domain.port.ProductRepository
import com.store.domain.port.NotificationService
import com.store.domain.exception.ProductNotFoundException
import com.store.domain.exception.InvalidAmountException

class ProductService(
    private val productRepository: ProductRepository
) {
    // Constructor secundario para backward compatibility
    constructor(
        repository: ProductRepository,
        notificationService: NotificationService?
    ) : this(repository) {
        throw UnsupportedOperationException("Notificación no implementada - Fase Roja")
    }
    
    fun decrementStock(productName: String, amount: Int) {
        val product = productRepository.findByName(productName)
        product.stock = product.stock - amount
        productRepository.save(product)
    }
}
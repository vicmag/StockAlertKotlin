package com.store.domain.service

import com.store.domain.model.Product
import com.store.domain.port.ProductRepository

class ProductService(
    private val productRepository: ProductRepository
) {
    
    fun increaseStock(productName: String, amount: Int): Boolean {
        throw UnsupportedOperationException("Método no implementado aún - Fase Roja")
    }
}
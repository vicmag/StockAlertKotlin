package com.store.domain.service

import com.store.domain.port.ProductRepository

class ProductService(
    private val productRepository: ProductRepository
) {
    fun increaseStock(productName: String, amount: Int) {
        // Implementación para fase roja - fallará las pruebas
        throw UnsupportedOperationException("Método no implementado aún - Fase Roja")
    }
}
package com.store.domain.service

import com.store.domain.port.ProductRepository
import com.store.domain.exception.ProductNotFoundException
import com.store.domain.exception.InvalidAmountException

class ProductService(
    private val productRepository: ProductRepository
) {
    fun decrementStock(productName: String, amount: Int) {
        // Implementación para fase roja - fallará las pruebas
        throw UnsupportedOperationException("Método decrementStock no implementado aún - Fase Roja")
    }
}
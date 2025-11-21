package com.store.domain.service

import com.store.domain.port.ProductRepository
import com.store.domain.exception.ProductNotFoundException

class ProductService(
    private val productRepository: ProductRepository
) {
    fun increaseStock(productName: String, amount: Int) {
        // FASE VERDE:
        // 1. Buscar producto
        val existingProduct = productRepository.findByName(productName)
                ?: throw ProductNotFoundException(productName)
        
        // 2. Incrementar stock - operación directa
        existingProduct.stock += amount  
        
        // 3. Guardar cambios - el test verifica este comportamiento
        productRepository.save(existingProduct)
        
    }
}
package com.store.domain.service

import com.store.domain.port.ProductRepository
import com.store.domain.exception.ProductNotFoundException
import com.store.domain.exception.InvalidAmountException

class ProductService(
    private val productRepository: ProductRepository
) {
    fun increaseStock(productName: String, amount: Int) {
        if (amount <= 0) {
            throw InvalidAmountException(amount)
        }

        val existingProduct = productRepository.findByName(productName)
                ?: throw ProductNotFoundException(productName)
        
        existingProduct.stock += amount  
        
        productRepository.save(existingProduct)
        
    }
}
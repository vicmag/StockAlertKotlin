package com.store.domain.service

import com.store.domain.port.ProductRepository

class ProductService(private val productRepository: ProductRepository){
    fun decrementStock(productName: String, decrement: Int){
        val product = productRepository.findByName(productName)
        product.stock = product.stock - decrement 
        productRepository.save(product)
    }
}
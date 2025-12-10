package com.store.domain.service

import com.store.domain.port.ProductRepository

class ProductService(
    private val productRepository: ProductRepository
){
    fun incrementStock(name: String, increment: Int) {
        //Fase Verde
        val product = productRepository.findByName(name)
        product.stock = product.stock + increment 
        productRepository.save(product)
    }
    
}
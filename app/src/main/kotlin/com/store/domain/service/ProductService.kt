package com.store.domain.service

import com.store.domain.port.ProductRepository

class ProductService(
    private val productRepository: ProductRepository
){
    fun incrementStock(name: String, increment: Int) {
        //No implementamos funcionalidad. Fase Roja
        throw UnsupportedOperationException("Fase Roja")
    }
    
}
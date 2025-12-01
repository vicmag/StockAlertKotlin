package com.store.domain.service

import com.store.domain.port.ProductRepository

class ProductService(private val productRepository: ProductRepository){
    fun decrementStock(productName: String, decrement: Int){
        //Implementación vacia. Fase Roja
        throw UnsupportedOperationException("Método no implementado")
    }
}
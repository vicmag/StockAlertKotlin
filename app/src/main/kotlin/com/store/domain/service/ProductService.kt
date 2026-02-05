package com.store.domain.service

import com.store.domain.port.ProductRepository

class ProductService(private val productRepository: ProductRepository){
    fun decrementStock(productName: String, decrement: Int){
        //1. "Buscar el producto en el repositorio por nombre")
        val product = productRepository.findByName(productName)

        //2. "Decrementar el stock del producto en la cantidad especificada"
        product.stock = product.stock - decrement

        //3. "Guardar el producto actualizado en el repositorio"
        productRepository.save(product)
    }

}
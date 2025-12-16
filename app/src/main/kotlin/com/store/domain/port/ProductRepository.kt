package com.store.domain.port

import com.store.domain.model.Product

interface ProductRepository {
    fun findByName(name: String): Product
    fun save(product: Product)
}
package com.store.domain.port

import com.store.domain.model.Product

/**
 * Contrato del repositorio - abstracción para DIP.
 * ✅ DIP: Dependemos de esta abstracción
 */
interface ProductRepository {
    fun findByName(name: String): Product?
    fun save(product: Product)
}
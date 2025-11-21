package com.store.domain.model

/**
 * Modelo simple - solo datos, sin lógica.
 * ✅ SRP: Solo representa un producto
 */
data class Product(
    val name: String,
    var stock: Int
)
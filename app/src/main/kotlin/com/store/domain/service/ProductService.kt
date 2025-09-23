package com.store.domain.service

import com.store.domain.port.ProductRepository

/**
 * Servicio de dominio - lógica de negocio.
 * 
 * ⚠️ FASE ROJA: La implementación debe FALLAR
 * Escribimos solo lo necesario para compilar
 */
class ProductService(
    private val productRepository: ProductRepository // ✅ Inyección por constructor
) {
    
    /**
     * ⚠️ Mínima implementación para compilación
     * Debe fallar la prueba de manera controlada
     */
    fun increaseStock(productName: String, amount: Int): Boolean {
        // ❌ IMPLEMENTACIÓN PENDIENTE - Fase Roja
        return false
    }
}
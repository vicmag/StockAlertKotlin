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
        // 1. Buscar producto (solución al fallo 1)
        val product = productRepository.findByName(productName)
        
        // 2. Si existe, actualizar y guardar (solución al fallo 2)
        if (product != null) {
            val updatedProduct = product.copy(stock = product.stock + amount)
            productRepository.save(updatedProduct)
            return true  // 3. Retornar éxito (solución al fallo 3)
        }
        
        return false
    }
}
package com.store.domain.service

import com.store.domain.port.ProductRepository
import com.store.domain.exception.ProductNotFoundException

class ProductService(
    private val productRepository: ProductRepository
) {
    fun increaseStock(productName: String, amount: Int) {
        // FASE VERDE: Implementación mínima para pasar el test
        // 1. Buscar producto - el test asegura que existe
        val existingProduct = productRepository.findByName(productName)
        
        // 2. Incrementar stock - operación directa
        existingProduct!!.stock += amount  
        
        // 3. Guardar cambios - el test verifica este comportamiento
        productRepository.save(existingProduct!!)
        
        // NOTA: No hay retorno - el éxito es implícito
        // NOTA: No hay manejo de errores - el test no lo requiere
        // NOTA: No hay validaciones - no existen en este escenario
    }
}
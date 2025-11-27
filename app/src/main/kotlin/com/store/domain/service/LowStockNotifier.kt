package com.store.domain.service

import com.store.domain.model.StockAlert
import com.store.domain.port.NotificationService
import com.store.domain.model.Product

class LowStockNotifier(
    private val notificationService: NotificationService
) {
    /**
     * Configura las notificaciones de stock bajo en un ProductService
     * @param productService El servicio al que se suscribirá
     */
    fun setupLowStockNotifications(productService: ProductService) {
        productService.subscribeToStockChanges { product: Product, oldStock: Int, newStock: Int ->
            // 🔄 REFACTOR: Lógica de notificación movida aquí
            if (isStockGoingBelowMinimum(oldStock, newStock, product.minStockLevel)) {
                println("🔔 DEBUG - ENVIANDO ALERTA!")
                val alert = StockAlert(
                    productName = product.name,
                    currentStock = newStock,
                    minStockLevel = product.minStockLevel
                )
                notificationService.sendLowStockAlert(alert)
            }
        }
    }
    
    /**
     * Determina si el stock acaba de pasar por debajo del nivel mínimo
     * Mejora: Solo notifica cuando CRUZA el límite, no cada vez que está bajo
     */
    private fun isStockGoingBelowMinimum(
        oldStock: Int, 
        newStock: Int, 
        minStockLevel: Int
    ): Boolean {
        return newStock <= minStockLevel && oldStock > minStockLevel
    }
}
// src/main/kotlin/com/store/domain/port/NotificationService.kt
package com.store.domain.port

import com.store.domain.model.StockAlert

interface NotificationService {
    fun sendLowStockAlert(alert: StockAlert)
}
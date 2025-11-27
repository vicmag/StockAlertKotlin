// src/main/kotlin/com/store/domain/model/StockAlert.kt
package com.store.domain.model

data class StockAlert(
    val productName: String,
    val currentStock: Int,
    val minStockLevel: Int
)
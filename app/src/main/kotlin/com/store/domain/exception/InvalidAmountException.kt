// src/main/kotlin/com/store/domain/exception/InvalidAmountException.kt
package com.store.domain.exception

class InvalidAmountException(amount: Int) : 
    IllegalArgumentException("La cantidad de incremento debe ser positiva: $amount")
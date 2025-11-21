package com.store.domain.exception

class ProductNotFoundException(productName: String) : 
    RuntimeException("Producto '$productName' no encontrado")
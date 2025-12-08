package com.store.domain.service

import io.kotest.core.spec.style.BehaviorSpec

class ProductServiceTest : BehaviorSpec({
    given("El producto 'Camiseta' existe en el repositorio con stock inicial de 10 unidades"){
       val productName = "Camiseta"
       val initialStock = 10
       val increment = 5
       val expectedStock = 15
       
       val product = Product(name = productName, stock = initialStock) 

       
    }
})

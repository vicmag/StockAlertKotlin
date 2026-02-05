package com.store.domain.service

import com.store.domain.model.Product
import com.store.domain.port.ProductRepository
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.mockk
import io.mockk.every
import io.mockk.verify

class ProducServiceTest : BehaviorSpec({
    given("El producto 'Camiseta' existe en el repositorio con stock inicial de 10 unidades"){
        //Arrange
        val productName = "Camiseta"
        val initialStock = 10
        val decrement = 5
        val finalStock = 5

        val product = Product(name = productName, stock = initialStock)

        val mockRepository = mockk<ProductRepository>()
        val productService = ProductService(mockRepository)

        beforeTest {
          every { mockRepository.findByName(productName) } returns product
          every { mockRepository.save(any()) } returns Unit
        }

        `when`("el sistema recibe una solicitud para decrementar el stock de 'Camiseta' en 5 unidades"){
            //Act
            productService.decrementStock(productName, decrement) 

            then("Buscar el producto en el repositorio por nombre"){
                verify{
                   mockRepository.findByName(productName) 
                }
            }
            then("Decrementar el stock del producto en la cantidad especificada (nuevo stock = 5)"){
                verify{
                    mockRepository.save(withArg {savedProduct: Product ->
                        savedProduct.stock shouldBe finalStock 
                    })
                }
            }
            then("Guardar el producto actualizado en el repositorio"){
                verify(exactly = 1){
                    mockRepository.save(any())
                }
            }
        }




    }

})
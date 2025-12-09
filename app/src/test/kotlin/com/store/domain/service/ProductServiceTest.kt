package com.store.domain.service

import io.kotest.core.spec.style.BehaviorSpec
import io.mokk.mockk

class ProductServiceTest : BehaviorSpec({
    given("El producto 'Camiseta' existe en el repositorio con stock inicial de 10 unidades"){
        //Arrange
        val productName = "Camiseta"
        val initialStock = 10
        val increment = 5
        val expectedStock = 15
       
        val product = Product(name = productName, stock = initialStock) 
    
        val mockRepository = mokk<ProductRepository>()
        val productService = ProductService(mockRepository)

        beforeTest {
            //Configuración del comportamiento (stubs)
            every { mockRepository.findByName(productName) } returns product
            every { mockRepository.save(any()) } returns Unit 
        }

    

        `when`("el sistema recibe una solicitud para incrementar el stock de 'Camiseta' en 5 unidades"){
            //Act
            productService.incrementStock(productName, increment)
        

            `then`("Buscar el producto en el repositorio por nombre"){
                verify {
                    mockRepository.findByName(productName)
                }
            }
            `then`("Incrementar el stock del producto en la cantidad especificada (nuevo stock = 15)"){
                mockRepository.save(withArg{ savedProduct: Product ->
                    savedProduct.stock shouldBe expectedStock
                })    
            }
            `then`("Guardar el producto actualizado en el repositorio"){
                verify(exactly = 1) {
                    mockRepository.save(any())
                }

            }
        }
    }
})

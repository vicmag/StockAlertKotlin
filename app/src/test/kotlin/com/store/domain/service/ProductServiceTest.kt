package com.store.domain.service

import com.store.domain.model.Product
import com.store.domain.port.ProductRepository
import com.store.domain.service.ProductService
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.mockk
import io.mockk.verify
import io.mockk.every


class ProductServiceTest : BehaviorSpec({
    given("el producto 'camiseta' con un stock de 10 unidades"){
        //Arrange 
        val productName = "camiseta"
        val initialStock = 10
        val decrement = 5
        val expectedStock = initialStock - decrement
        val existingProduct = Product(name = productName, stock = initialStock)

        val mockRepository = mockk<ProductRepository>()
        val productService = ProductService(mockRepository)

        beforeTest{
            //configuración de los mock (stubs)
            every { mockRepository.findByName(productName) } returns existingProduct
            every { mockRepository.save(any()) } returns Unit
        }

        `when`("el sistema recibe una solicitud para decrementar el stock de 'Camiseta' en 5 unidades"){
            //Act
            productService.decrementStock(productName, decrement)

            then("debe de buscar el producto en el repositorio por nombre"){
                verify{
                   mockRepository.findByName(productName) 
                }
            }

            then("debe de decrementar el stock del producto en la cantidad especificada (nuevo stock = 5)"){
                verify{
                    mockRepository.save(withArg { saveProduct: Product ->
                        saveProduct.stock shouldBe expectedStock
                    })
                }
            }

            then("debe de guardar el producto actualizado en el repositorio"){
                verify(exactly = 1){
                    mockRepository.save(any())
                }
            }

        }
    }
})
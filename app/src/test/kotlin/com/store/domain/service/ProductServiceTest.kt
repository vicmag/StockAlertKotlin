package com.store.domain.service

import com.store.domain.model.Product
import com.store.domain.port.ProductRepository
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify

class ProductServiceTest : BehaviorSpec({

    given("el producto 'Camiseta' existe en el repositorio con stock inicial de 10 unidades") {
        val productName = "Camiseta"
        val initialStock = 10
        val incrementAmount = 5
        val expectedStock = 15
        
        val mockRepository = mockk<ProductRepository>()
        val productService = ProductService(mockRepository)
        
        val existingProduct = Product(name = productName, stock = initialStock)

        beforeTest {
            // Configurar el repositorio para devolver el producto cuando se busque por nombre
            every { mockRepository.findByName(productName) } returns existingProduct
            every { mockRepository.save(any()) } returns true
        }

        `when`("el sistema recibe una solicitud para incrementar el stock de 'Camiseta' en 5 unidades") {
            val result = productService.increaseStock(productName, incrementAmount)

            then("debe buscar el producto en el repositorio por nombre") {
                verify { 
                    mockRepository.findByName(productName) 
                }
            }

            then("debe incrementar el stock del producto en la cantidad especificada (nuevo stock = 15)") {
                verify { 
                    mockRepository.save(withArg { savedProduct: Product ->
                        savedProduct.stock shouldBe expectedStock
                    }) 
                }
            }

            then("debe guardar el producto actualizado en el repositorio") {
                verify(exactly = 1) { 
                    mockRepository.save(any()) 
                }
            }

            then("debe retornar true indicando éxito") {
                result shouldBe true
            }
        }
    }
})
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
        val decrementAmount = 5
        val expectedStock = initialStock - decrementAmount  // 5 unidades
        
        val mockRepository = mockk<ProductRepository>()
        val productService = ProductService(mockRepository)
        
        val existingProduct = Product(name = productName, stock = initialStock)

        beforeTest {
            // Configurar el repositorio para devolver el producto cuando se busque por nombre
            every { mockRepository.findByName(productName) } returns existingProduct
            // save es void - retorna Unit
            every { mockRepository.save(any()) } returns Unit
        }

        `when`("el sistema recibe una solicitud para decrementar el stock de 'Camiseta' en 5 unidades") {
            // Act - El método es void, éxito implícito
            productService.decrementStock(productName, decrementAmount)

            then("debe buscar el producto en el repositorio por nombre") {
                verify { 
                    mockRepository.findByName(productName) 
                }
            }

            then("debe decrementar el stock del producto en la cantidad especificada (nuevo stock = 5)") {
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
        }
    }
})
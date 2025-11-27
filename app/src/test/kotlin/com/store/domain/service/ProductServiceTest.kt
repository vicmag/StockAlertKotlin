package com.store.domain.service

import com.store.domain.model.Product
import com.store.domain.model.StockAlert
import com.store.domain.port.ProductRepository
import com.store.domain.port.NotificationService
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

    given("el producto 'Camiseta Azul' tiene un nivel mínimo de stock de 10 unidades") {
        val productName = "Camiseta Azul"
        val initialStock = 15
        val minStockLevel = 10
        val decrementAmount = 10
        val expectedStock = initialStock - decrementAmount  // 5 unidades
        
        val mockRepository = mockk<ProductRepository>()
        val mockNotificationService = mockk<NotificationService>()  // ← NUEVA DEPENDENCIA
        val productService = ProductService(mockRepository, mockNotificationService)  // ← ERROR: Constructor no existe
        
        val existingProduct = Product(
            name = productName,
            stock = initialStock,
            minStockLevel = minStockLevel  // ← ERROR: Propiedad no existe
        )

        beforeTest {
            every { mockRepository.findByName(productName) } returns existingProduct
            every { mockRepository.save(any()) } returns Unit
            every { mockNotificationService.sendLowStockAlert(any()) } returns Unit  // ← ERROR: Método no existe
        }

        `when`("el stock actual de 'Camiseta Azul' se reduce a 5 unidades") {
            productService.decrementStock(productName, decrementAmount)

            then("el sistema debe enviar una alerta indicando que el stock está por debajo del nivel mínimo") {
                verify(exactly = 1) { 
                    mockNotificationService.sendLowStockAlert(
                        withArg { alert: StockAlert ->  // ← ERROR: Clase no existe
                            alert.productName shouldBe productName
                            alert.currentStock shouldBe expectedStock
                            alert.minStockLevel shouldBe minStockLevel
                        }
                    ) 
                }
            }

            then("debe actualizar el stock correctamente a 5 unidades") {
                verify { 
                    mockRepository.save(withArg { savedProduct: Product ->
                        savedProduct.stock shouldBe expectedStock
                    }) 
                }
            }
        }
    }
})
package com.store.domain.service

import com.store.domain.model.Product
import com.store.domain.port.ProductRepository
import com.store.domain.exception.ProductNotFoundException
import com.store.domain.exception.InvalidAmountException
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.kotest.assertions.throwables.shouldThrow
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify

class ProductServiceTest : BehaviorSpec({

    given("el producto 'Camiseta' existe en el repositorio con stock inicial de 10 unidades") {
        val productName = "Camiseta"
        val initialStock = 10
        val incrementAmount = 5
        val expectedStock = initialStock + incrementAmount
        
        val mockRepository = mockk<ProductRepository>()
        val productService = ProductService(mockRepository)
        
        val existingProduct = Product(name = productName, stock = initialStock)

        beforeTest {
            // Configurar el repositorio para devolver el producto cuando se busque por nombre
            every { mockRepository.findByName(productName) } returns existingProduct
            // save es void - retorna Unit (forma correcta para métodos sin retorno)
            every { mockRepository.save(any()) } returns Unit
        }

        `when`("el sistema recibe una solicitud para incrementar el stock de 'Camiseta' en 5 unidades") {
            // Act - El método es void, éxito implícito
            productService.increaseStock(productName, incrementAmount)

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
        }
    }

    given("el producto 'Zapato' NO existe en el repositorio") {
        val productName = "Zapato"
        val incrementAmount = 5
        
        val mockRepository = mockk<ProductRepository>()
        val productService = ProductService(mockRepository)

        beforeTest {
            // ❌ ESTO NO COMPILARÁ - findByName actualmente retorna Product, no Product?
            every { mockRepository.findByName(productName) } returns null
        }

        `when`("el sistema recibe una solicitud para incrementar el stock de 'Zapato'") {
            // ❌ ESTO NO COMPILARÁ - ProductNotFoundException no existe
            val exception = shouldThrow<ProductNotFoundException> {
                productService.increaseStock(productName, incrementAmount)
            }

            then("debe buscar el producto en el repositorio por nombre") {
                verify { 
                    mockRepository.findByName(productName) 
                }
            }

            then("debe lanzar ProductNotFoundException con mensaje descriptivo") {
                // ❌ ESTO NO COMPILARÁ - la excepción no existe
                exception.message shouldBe "Producto 'Zapato' no encontrado"
            }

            then("NO debe intentar guardar ningún producto en el repositorio") {
                verify(exactly = 0) { 
                    mockRepository.save(any()) 
                }
            }
        }
    }

    given("el producto 'Camiseta' existe y la cantidad de incremento es 0") {
        val productName = "Camiseta"
        val invalidAmount = 0
        
        val mockRepository = mockk<ProductRepository>()
        val productService = ProductService(mockRepository)
        
        val existingProduct = Product(name = productName, stock = 10)

        beforeTest {
            every { mockRepository.findByName(productName) } returns existingProduct
        }

        `when`("el sistema recibe una solicitud para incrementar el stock con cantidad 0") {
            then("debe lanzar InvalidAmountException") {
                shouldThrow<InvalidAmountException> {
                    productService.increaseStock(productName, invalidAmount)
                }
            }

            then("NO debe buscar el producto en el repositorio") {
                verify(exactly = 0) { 
                    mockRepository.findByName(any()) 
                }
            }

            then("NO debe guardar ningún producto") {
                verify(exactly = 0) { 
                    mockRepository.save(any()) 
                }
            }
        }
    }

    given("el producto 'Pantalón' existe y la cantidad de incremento es -5") {
        val productName = "Pantalón"
        val invalidAmount = -5
        
        val mockRepository = mockk<ProductRepository>()
        val productService = ProductService(mockRepository)
        
        val existingProduct = Product(name = productName, stock = 20)

        beforeTest {
            every { mockRepository.findByName(productName) } returns existingProduct
        }

        `when`("el sistema recibe una solicitud para incrementar el stock con cantidad -5") {
            then("debe lanzar InvalidAmountException") {
                shouldThrow<InvalidAmountException> {
                    productService.increaseStock(productName, invalidAmount)
                }
            }

            then("NO debe buscar el producto en el repositorio") {
                verify(exactly = 0) { 
                    mockRepository.findByName(any()) 
                }
            }

            then("NO debe guardar ningún producto") {
                verify(exactly = 0) { 
                    mockRepository.save(any()) 
                }
            }
        }
    }
})
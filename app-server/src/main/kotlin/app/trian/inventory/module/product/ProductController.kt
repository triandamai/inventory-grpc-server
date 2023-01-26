package app.trian.inventory.module.product

import app.trian.inventory.v1.getPagingRequest
import app.trian.inventory.v1.product.CreateProductRequest
import app.trian.inventory.v1.product.UpdateProductRequest
import app.trian.inventory.v1.product.deleteProductRequest
import kotlinx.coroutines.coroutineScope
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(
    value = ["/api/v1"]
)
class ProductController(
    private val productService: ProductService
) {
    @GetMapping(
        value = ["/products"],
        produces = ["application/json"]
    )
    suspend fun getListProduct(
        @RequestParam(name = "page") nextPage: Long? = 0
    ) = coroutineScope {
        productService.getListProduct(
            getPagingRequest {
                page = nextPage ?: 0
            }
        )
    }

    @PostMapping(
        value = ["/product"],
        produces = ["application/json"],
        consumes = ["application/json"]
    )
    suspend fun createNewProduct(@RequestBody request: CreateProductRequest) = coroutineScope {
        productService.createNewProduct(request)
    }

    @PutMapping(
        value = ["/product"],
        produces = ["application/json"],
        consumes = ["application/json"]
    )
    suspend fun updateCustomer(
        @RequestBody request: UpdateProductRequest
    ) = coroutineScope {
        productService.updateProduct(
            request
        )
    }

    @DeleteMapping(
        value = ["/customer/{productId}"],
        produces = ["application/json"]
    )
    suspend fun deleteCustomer(@PathVariable(name = "productId") resourceId: String) = coroutineScope {
        productService.deleteProduct(
            deleteProductRequest {
                productId = resourceId
            }
        )
    }
}
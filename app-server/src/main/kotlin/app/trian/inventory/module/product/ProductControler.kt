package app.trian.inventory.module.product

import app.trian.inventory.v1.getPagingRequest
import kotlinx.coroutines.coroutineScope
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class ProductControler (
    private val productService: ProductService
){
    @GetMapping(
        value = ["/api/v1/category"],
        produces = ["application/json"]
    )
    suspend fun getListCategory(
        @RequestParam(name = "page") nextPage:Long = 50
    ) =  coroutineScope {
        productService.getListProduct(
            getPagingRequest {
                page = nextPage
            }
        )
    }
}
package app.trian.inventory.module.category

import app.trian.inventory.v1.getPagingRequest
import kotlinx.coroutines.coroutineScope
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController


@RestController
class CategoryController (
    private val categoryService: CategoryService
){
    @GetMapping(
        value = ["/api/v1/category"],
        produces = ["application/json"]
    )
    suspend fun getListCategory(
        @RequestParam(name = "page") nextPage:Long = 50
    ) =  coroutineScope {
        categoryService.getListCategory(
            getPagingRequest {
                page = nextPage
            }
        )
    }

}
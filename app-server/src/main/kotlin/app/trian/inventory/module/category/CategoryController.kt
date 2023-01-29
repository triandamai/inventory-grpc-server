package app.trian.inventory.module.category

import app.trian.inventory.v1.category.CreateCategoryRequest
import app.trian.inventory.v1.category.UpdateCategoryRequest
import app.trian.inventory.v1.category.deleteCategoryRequest
import app.trian.inventory.v1.getPagingRequest
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
class CategoryController (
    private val categoryService: CategoryService
){
    @GetMapping(
        value = ["/categories"],
        produces = ["application/json"]
    )
    suspend fun getListCategorys(@RequestParam(name = "page") nextPage: Long = 0) = coroutineScope {
        categoryService.getListCategory(
            getPagingRequest {
                page = nextPage
            }
        )
    }
    

    @PostMapping(
        value = ["/category"],
        produces = ["application/json"]
    )
    suspend fun createNewCategory(@RequestBody request: CreateCategoryRequest) = coroutineScope {
        categoryService.createNewCategory(request)
    }

    @PutMapping(
        value = ["/category"],
        produces = ["application/json"]
    )
    suspend fun updateCategory(@RequestBody request: UpdateCategoryRequest)= coroutineScope {
        categoryService.updateCategory(request)
    }

    @DeleteMapping(
        value = ["/category/{categoryId}"],
        produces = ["application/json"]
    )
    suspend fun deleteCategory(@PathVariable(name = "categoryId") paramCategoryId:String) = coroutineScope {
        categoryService.deleteCategory(
            deleteCategoryRequest {
                categoryId = paramCategoryId
            }
        )
    
    }

}
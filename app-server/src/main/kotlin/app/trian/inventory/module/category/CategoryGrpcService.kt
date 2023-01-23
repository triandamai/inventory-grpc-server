package app.trian.inventory.module.category

import app.trian.inventory.v1.category.CategoryGrpcKt
import app.trian.inventory.v1.category.CategoryResponse
import app.trian.inventory.v1.category.CreateCategoryRequest
import app.trian.inventory.v1.category.categoryResponse
import net.devh.boot.grpc.server.service.GrpcService
import java.util.Date

@GrpcService
class CategoryGrpcService(
    private val categoryRepository: CategoryRepository
):CategoryGrpcKt.CategoryCoroutineImplBase() {

    override suspend fun createNewCategory(request: CreateCategoryRequest): CategoryResponse {
        val date = Date()
        val category = Category()

        val saveCategory = categoryRepository.save(
            category.copy(
                id = null,
                categoryName = request.categoryName,
                categoryDescription = request.categoryDescription,
                createdAt = date,
                updatedAt = date
            )
        )


        return categoryResponse {
            categoryId = saveCategory.id.orEmpty()
            categoryName = saveCategory.categoryName.orEmpty()
            categoryDescription = saveCategory.categoryDescription.orEmpty()
            createdAt = saveCategory.createdAt.toString()
            updatedAt = saveCategory.updatedAt.toString()
        }
    }
}
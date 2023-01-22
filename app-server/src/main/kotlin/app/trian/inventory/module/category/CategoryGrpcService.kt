package app.trian.inventory.module.category

import app.trian.inventory.v1.category.CategoryGrpcKt
import app.trian.inventory.v1.category.CategoryResponse
import app.trian.inventory.v1.category.CreateCategoryRequest
import app.trian.inventory.v1.category.categoryResponse
import net.devh.boot.grpc.server.service.GrpcService

@GrpcService
class CategoryGrpcService(
    private val categoryRepository: CategoryRepository
):CategoryGrpcKt.CategoryCoroutineImplBase() {
    override suspend fun createNewCategory(request: CreateCategoryRequest): CategoryResponse {
        return categoryResponse {  }
    }
}
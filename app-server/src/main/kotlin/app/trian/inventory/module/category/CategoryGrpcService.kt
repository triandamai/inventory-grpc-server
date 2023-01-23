package app.trian.inventory.module.category

import app.trian.inventory.module.error.DataNotFound
import app.trian.inventory.v1.category.*
import app.trian.inventory.v1.role.roleResponse
import net.devh.boot.grpc.server.service.GrpcService
import org.springframework.data.repository.findByIdOrNull
import java.util.Date
import javax.xml.crypto.Data

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

    override suspend fun updateCategory(request: UpdateCategoryRequest): CategoryResponse {
        val findCategory = categoryRepository.findByIdOrNull(request.categoryId.toString())?:
        throw DataNotFound("cannot find category ${request.categoryId}")

        val updatecategory = with(request){
            findCategory.copy(
                categoryName = findCategory.categoryName,
                categoryDescription = findCategory.categoryDescription,
                updatedAt = Date()
            )
        }

        val saveUpdateCategory = categoryRepository.save(updatecategory)

        return categoryResponse {
            categoryId = saveUpdateCategory.id.orEmpty()
            categoryName = saveUpdateCategory.categoryName.orEmpty()
            categoryDescription = saveUpdateCategory.categoryDescription.orEmpty()
            createdAt = saveUpdateCategory.createdAt.toString()
            updatedAt = saveUpdateCategory.updatedAt.toString()
        }
    }
}
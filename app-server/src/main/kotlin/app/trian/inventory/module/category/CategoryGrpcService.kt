package app.trian.inventory.module.category

import app.trian.inventory.module.error.DataNotFound
import app.trian.inventory.v1.GetPagingRequest
import app.trian.inventory.v1.category.*
import app.trian.inventory.v1.role.roleResponse
import com.google.protobuf.Field
import net.devh.boot.grpc.server.service.GrpcService
import org.springframework.data.domain.PageRequest
import org.springframework.data.repository.findByIdOrNull
import java.util.Date
import javax.xml.crypto.Data

@GrpcService
class CategoryGrpcService(
    private val categoryRepository: CategoryRepository
):CategoryGrpcKt.CategoryCoroutineImplBase() {


    override suspend fun getCategoryById(request: Field): CategoryResponse {
        val findWithId = categoryRepository.findByIdOrNull(request.toString())?:

        throw DataNotFound("category not found")

        return categoryResponse {
            categoryId =findWithId.id.orEmpty()
            categoryName = findWithId.categoryName.orEmpty()
            categoryDescription = findWithId.categoryDescription.orEmpty()
            createdAt = findWithId.createdAt.toString()
            updatedAt = findWithId.updatedAt.toString()
        }
    }

    override suspend fun getListCategory(request: GetPagingRequest): GetListCategoryResponse {
        val findCategory = categoryRepository.findAll(
            PageRequest.of(
              request.page.toInt(),
                50
            )
        )
        return getListCategoryResponse {
            totalItem = findCategory.totalElements
            totalPage = findCategory.totalPages.toLong()
            data += findCategory.content.map {
                categoryResponse {
                    categoryId = it.id.orEmpty()
                    categoryName = it.categoryName.orEmpty()
                    categoryDescription = it.categoryDescription.orEmpty()
                    createdAt = it.createdAt.toString()
                    updatedAt = it.updatedAt.toString()
                }
            }
            currentPage = findCategory.number.toLong()
        }
    }

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

    override suspend fun deleteCategory(request: DeleteCategoryRequest): CategoryResponse {
        val findCategory = categoryRepository.findByIdOrNull(request.categoryId.toString())?:
        throw DataNotFound("cannot find category ${request.categoryId}")

        findCategory.id.let { categoryRepository.deleteById(it.orEmpty()) }

        return categoryResponse {
            categoryId = findCategory.id.orEmpty()
            categoryName = findCategory.categoryName.orEmpty()
            categoryDescription = findCategory.categoryDescription.orEmpty()
            createdAt = findCategory.createdAt.toString()
            updatedAt = findCategory.updatedAt.toString()
        }
    }
}
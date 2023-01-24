package app.trian.inventory.module.category

import app.trian.inventory.module.error.DataNotFound
import app.trian.inventory.v1.GetPagingRequest
import app.trian.inventory.v1.category.*
import com.google.protobuf.Field
import org.springframework.data.domain.PageRequest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.util.*

@Service
class CategoryService (
    private val categoryRepository: CategoryRepository
){
     suspend fun getCategoryById(request: Field): CategoryResponse {
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

     suspend fun getListCategory(request: GetPagingRequest): GetListCategoryResponse {
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

     suspend fun createNewCategory(request: CreateCategoryRequest): CategoryResponse {
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

     suspend fun updateCategory(request: UpdateCategoryRequest): CategoryResponse {
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

     suspend fun deleteCategory(request: DeleteCategoryRequest): CategoryResponse {
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
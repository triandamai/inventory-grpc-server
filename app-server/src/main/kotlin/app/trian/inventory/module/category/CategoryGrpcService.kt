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
    private val categoryService: CategoryService
):CategoryGrpcKt.CategoryCoroutineImplBase() {
    override suspend fun getListCategory(request: GetPagingRequest): GetListCategoryResponse = categoryService.getListCategory(request)

    override suspend fun getCategoryById(request: Field): CategoryResponse = categoryService.getCategoryById(request)

    override suspend fun createNewCategory(request: CreateCategoryRequest): CategoryResponse = categoryService.createNewCategory(request)

    override suspend fun updateCategory(request: UpdateCategoryRequest): CategoryResponse = categoryService.updateCategory(request)

    override suspend fun deleteCategory(request: DeleteCategoryRequest): CategoryResponse = categoryService.deleteCategory(request)


}
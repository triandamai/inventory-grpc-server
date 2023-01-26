package app.trian.inventory.module.product

import app.trian.inventory.v1.GetPagingRequest
import app.trian.inventory.v1.product.*
import net.devh.boot.grpc.server.service.GrpcService

@GrpcService
class ProductGrpcService(
    private val productService: ProductService
) :ProductGrpcKt.ProductCoroutineImplBase(){

    override suspend fun getListProduct(request: GetPagingRequest): GetListProductResponse = productService.getListProduct(request)

    override suspend fun createNewProduct(request: CreateProductRequest): ProductResponse  = productService.createNewCategory(request)

    override suspend fun updateProduct(request: UpdateProductRequest): ProductResponse  = productService.updateCategory(request)

    override suspend fun deleteProduct(request: DeleteProductRequest): ProductResponse= productService.deleteProduct(request)
}
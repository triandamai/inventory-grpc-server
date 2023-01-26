package app.trian.inventory.module.product

import app.trian.inventory.v1.GetPagingRequest
import app.trian.inventory.v1.product.CreateProductRequest
import app.trian.inventory.v1.product.DeleteProductRequest
import app.trian.inventory.v1.product.GetListProductResponse
import app.trian.inventory.v1.product.ProductGrpcKt
import app.trian.inventory.v1.product.ProductResponse
import app.trian.inventory.v1.product.UpdateProductRequest
import net.devh.boot.grpc.server.service.GrpcService

@GrpcService
class ProductGrpcService(
    private val productService: ProductService
) :ProductGrpcKt.ProductCoroutineImplBase(){

    override suspend fun getListProduct(request: GetPagingRequest): GetListProductResponse = productService.getListProduct(request)

    override suspend fun createNewProduct(request: CreateProductRequest): ProductResponse  = productService.createNewProduct(request)

    override suspend fun updateProduct(request: UpdateProductRequest): ProductResponse  = productService.updateProduct(request)

    override suspend fun deleteProduct(request: DeleteProductRequest): ProductResponse= productService.deleteProduct(request)
}
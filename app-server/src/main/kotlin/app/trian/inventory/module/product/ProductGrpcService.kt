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
    private val productRepository: ProductRepository
) :ProductGrpcKt.ProductCoroutineImplBase(){
    override suspend fun getListProduct(request: GetPagingRequest): GetListProductResponse {
        return super.getListProduct(request)
    }

    override suspend fun createNewProduct(request: CreateProductRequest): ProductResponse {
        return super.createNewProduct(request)
    }

    override suspend fun updateProduct(request: UpdateProductRequest): ProductResponse {
        return super.updateProduct(request)
    }
    override suspend fun deleteProduct(request: DeleteProductRequest): ProductResponse {
        return super.deleteProduct(request)
    }
}
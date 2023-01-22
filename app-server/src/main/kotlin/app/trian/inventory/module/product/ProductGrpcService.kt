package app.trian.inventory.module.product

import app.trian.inventory.v1.product.ProductGrpcKt
import net.devh.boot.grpc.server.service.GrpcService

@GrpcService
class ProductGrpcService(
    private val productRepository: ProductRepository
) :ProductGrpcKt.ProductCoroutineImplBase(){
}
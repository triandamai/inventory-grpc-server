package app.trian.inventory.module.supplier

import app.trian.inventory.v1.supplier.SupplierGrpcKt
import net.devh.boot.grpc.server.service.GrpcService

@GrpcService
class SupplierGrpcService(
    private val supplierRepository: SupplierRepository
):SupplierGrpcKt.SupplierCoroutineImplBase() {
}
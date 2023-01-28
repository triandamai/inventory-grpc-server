package app.trian.inventory.module.supplier

import app.trian.inventory.module.error.DataExist
import app.trian.inventory.module.error.DataNotFound
import app.trian.inventory.v1.GetById
import app.trian.inventory.v1.GetPagingRequest
import app.trian.inventory.v1.supplier.CreateNewSupplierRequest
import app.trian.inventory.v1.supplier.DeleteSupplierRequest
import app.trian.inventory.v1.supplier.GetListSupplierResponse
import app.trian.inventory.v1.supplier.SupplierGrpcKt
import app.trian.inventory.v1.supplier.SupplierResponse
import app.trian.inventory.v1.supplier.UpdateSupplierRequest
import app.trian.inventory.v1.supplier.getListSupplierResponse
import app.trian.inventory.v1.supplier.supplierResponse
import net.devh.boot.grpc.server.service.GrpcService
import org.springframework.data.domain.PageRequest
import org.springframework.data.repository.findByIdOrNull
import java.util.Date

@GrpcService
class SupplierGrpcService(
    private val supplierService: SupplierService
) : SupplierGrpcKt.SupplierCoroutineImplBase() {
    override suspend fun getListSupplier(request: GetPagingRequest): GetListSupplierResponse =
        supplierService.getListSupplier(request)

    override suspend fun getSupplierById(request: GetById): SupplierResponse =
        supplierService.getSupplierById(request)
    override suspend fun createNewSupplier(request: CreateNewSupplierRequest): SupplierResponse =
        supplierService.createNewSupplier(request)


    override suspend fun updateSupplier(request: UpdateSupplierRequest): SupplierResponse =
        supplierService.updateSupplier(request)

    override suspend fun deleteSupplier(request: DeleteSupplierRequest): SupplierResponse =
        supplierService.deleteSupplier(request)
}
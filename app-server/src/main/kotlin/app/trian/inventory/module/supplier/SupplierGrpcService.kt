package app.trian.inventory.module.supplier

import app.trian.inventory.module.error.DataExist
import app.trian.inventory.module.error.DataNotFound
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
    private val supplierRepository: SupplierRepository
) : SupplierGrpcKt.SupplierCoroutineImplBase() {
    override suspend fun getListSupplier(request: GetPagingRequest): GetListSupplierResponse {
        val suppliers = supplierRepository.findAll(
            PageRequest.of(
                request.page.toInt(),
                50
            )
        )


        return getListSupplierResponse {
            totalItem = suppliers.totalElements
            totalPage = suppliers.totalPages.toLong()
            data += suppliers.map {
                supplierResponse { }
            }
            currentPage = suppliers.number.toLong()
        }
    }

    override suspend fun createNewSupplier(request: CreateNewSupplierRequest): SupplierResponse {
        val isSupplierExist = supplierRepository.findTopBySupplierEmail(request.supplierEmail)
        if (isSupplierExist != null) {
            throw DataExist("Email ${request.supplierEmail} sudah digunakan silahkan gunakan Email lain")
        }

        val date = Date()
        val payloadData = Supplier()

        val savedData = supplierRepository.save(
            payloadData.copy(
                id = null,
                supplierFullName = request.supplierFullName,
                supplierAddress = request.supplierAddress,
                supplierOrgName = request.supplierOrgName,
                supplierEmail = request.supplierEmail,
                supplierPhoneNumber = request.supplierPhoneNumber,
                createdAt = date,
                updatedAt = date
            )
        )
        return supplierResponse {
            supplierId = savedData.id.orEmpty()
            supplierAddress = savedData.supplierAddress.orEmpty()
            supplierOrgName = savedData.supplierOrgName.orEmpty()
            supplierFullName = savedData.supplierFullName.orEmpty()
            supplierPhoneNumber = savedData.supplierPhoneNumber.orEmpty()
            createdAt = savedData.createdAt.toString()
            updatedAt = savedData.updatedAt.toString()
        }
    }

    override suspend fun updateSupplier(request: UpdateSupplierRequest): SupplierResponse {
        val findSupplier = supplierRepository.findByIdOrNull(request.supplierId)
            ?: throw DataNotFound("Tidak dapat menemukan sata supplier, atau data sudah dihapus")

        val updatedSupplier = with(request) {
            findSupplier.copy(
                supplierFullName = if (supplierFullName.isNullOrEmpty()) findSupplier.supplierFullName else supplierFullName,
                supplierPhoneNumber = if (supplierPhoneNumber.isNullOrEmpty()) findSupplier.supplierPhoneNumber else supplierPhoneNumber,
                supplierEmail = if (supplierPhoneNumber.isNullOrEmpty()) findSupplier.supplierEmail else supplierEmail,
                supplierOrgName = if (supplierOrgName.isNullOrEmpty()) findSupplier.supplierOrgName else supplierOrgName,
                supplierAddress = if (supplierAddress.isNullOrEmpty()) findSupplier.supplierAddress else supplierAddress,
                updatedAt = Date()
            )
        }

        val savedData = supplierRepository.save(updatedSupplier)
        return supplierResponse {
            supplierId = savedData.id.orEmpty()
            supplierAddress = savedData.supplierAddress.orEmpty()
            supplierOrgName = savedData.supplierOrgName.orEmpty()
            supplierFullName = savedData.supplierFullName.orEmpty()
            supplierPhoneNumber = savedData.supplierPhoneNumber.orEmpty()
            createdAt = savedData.createdAt.toString()
            updatedAt = savedData.updatedAt.toString()
        }
    }

    override suspend fun deleteSupplier(request: DeleteSupplierRequest): SupplierResponse {
        val findSuplier = supplierRepository.findByIdOrNull(request.customerId)
            ?: throw DataNotFound("Tidak dapat menemukan sata supplier, atau data sudah dihapus")

        findSuplier.apply {
            supplierRepository.delete(this)
        }
        return supplierResponse {
            supplierId = findSuplier.id.orEmpty()
            supplierAddress = findSuplier.supplierAddress.orEmpty()
            supplierOrgName = findSuplier.supplierOrgName.orEmpty()
            supplierFullName = findSuplier.supplierFullName.orEmpty()
            supplierPhoneNumber = findSuplier.supplierPhoneNumber.orEmpty()
            createdAt = findSuplier.createdAt.toString()
            updatedAt = findSuplier.updatedAt.toString()
        }
    }
}
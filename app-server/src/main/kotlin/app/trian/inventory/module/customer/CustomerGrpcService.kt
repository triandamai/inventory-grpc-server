package app.trian.inventory.module.customer

import app.trian.inventory.module.error.DataExist
import app.trian.inventory.module.error.DataNotFound
import app.trian.inventory.v1.GetPagingRequest
import app.trian.inventory.v1.customer.CreateNewCustomerRequest
import app.trian.inventory.v1.customer.CustomerGrpcKt
import app.trian.inventory.v1.customer.CustomerResponse
import app.trian.inventory.v1.customer.DeleteCustomerRequest
import app.trian.inventory.v1.customer.GetListCustomerResponse
import app.trian.inventory.v1.customer.UpdateCustomerRequest
import app.trian.inventory.v1.customer.customerResponse
import app.trian.inventory.v1.customer.getListCustomerResponse
import net.devh.boot.grpc.server.service.GrpcService
import org.springframework.data.domain.PageRequest
import org.springframework.data.repository.findByIdOrNull
import java.util.*

@GrpcService
class CustomerGrpcService(
    private val customerService: CustomerService
) : CustomerGrpcKt.CustomerCoroutineImplBase() {
    override suspend fun getListCustomer(request: GetPagingRequest): GetListCustomerResponse =
        customerService.getListCustomer(request)

    override suspend fun createNewCustomer(request: CreateNewCustomerRequest): CustomerResponse =
        customerService.createNewCustomer(request)

    override suspend fun updateCustomer(request: UpdateCustomerRequest): CustomerResponse =
        customerService.updateCustomer(request)

    override suspend fun deleteCustomer(request: DeleteCustomerRequest): CustomerResponse =
        customerService.deleteCustomer(request)
}
package app.trian.inventory.module.customer

import app.trian.inventory.module.error.DataExist
import app.trian.inventory.module.error.DataNotFound
import app.trian.inventory.v1.GetPagingRequest
import app.trian.inventory.v1.customer.CreateNewCustomerRequest
import app.trian.inventory.v1.customer.CustomerResponse
import app.trian.inventory.v1.customer.DeleteCustomerRequest
import app.trian.inventory.v1.customer.GetListCustomerResponse
import app.trian.inventory.v1.customer.UpdateCustomerRequest
import app.trian.inventory.v1.customer.customerResponse
import app.trian.inventory.v1.customer.getListCustomerResponse
import org.springframework.data.domain.PageRequest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.util.*

@Service
class CustomerService(
    private val customerRepository: CustomerRepository
) {
    suspend fun getListCustomer(request: GetPagingRequest): GetListCustomerResponse {
        val customers = customerRepository.findAll(
            PageRequest.of(
                request.page.toInt(),
                50
            )
        )
        return getListCustomerResponse {
            totalItem = customers.totalElements
            totalPage = customers.totalPages.toLong()
            data += customers.content.map {
                customerResponse {
                    customerId = it.id.orEmpty()
                    customerEmail = it.customerEmail.orEmpty()
                    customerFullName = it.customerFullName.orEmpty()
                    customerPhoneNumber = it.customerPhoneNumber.orEmpty()
                    createdAt = it.createdAt.toString()
                    updatedAt = it.updatedAt.toString()
                }
            }
            currentPage = customers.number.toLong()
        }
    }

    suspend fun createNewCustomer(request: CreateNewCustomerRequest): CustomerResponse {

        val isCustomerExist = customerRepository.findTopByCustomerEmail(request.customerEmail)
        if (isCustomerExist != null) {
            throw DataExist("Email ${request.customerEmail} sudah terdaftar silahkan gunakan email lain")
        }

        val date = Date()
        val payloadData = Customer()

        val saveData = customerRepository.save(
            payloadData.copy(
                id = null,
                customerEmail = request.customerEmail,
                customerFullName = request.customerFullName,
                customerPhoneNumber = request.customerPhoneNumber,
                createdAt = date,
                updatedAt = date
            )
        )

        return customerResponse {
            customerId = saveData.id.orEmpty()
            customerEmail = saveData.customerEmail.orEmpty()
            customerFullName = saveData.customerFullName.orEmpty()
            customerPhoneNumber = saveData.customerPhoneNumber.orEmpty()
            createdAt = saveData.createdAt.toString()
            updatedAt = saveData.updatedAt.toString()
        }
    }

    suspend fun updateCustomer(request: UpdateCustomerRequest): CustomerResponse {
        val findCustomer = customerRepository.findByIdOrNull(request.customerId)
            ?: throw DataNotFound("Customer tidak ditemukan!")

        val updatedDataRequest = with(request) {
            findCustomer.copy(
                customerEmail = if (customerEmail.isNullOrEmpty()) findCustomer.customerEmail else customerEmail,
                customerFullName = if (customerFullName.isNullOrEmpty()) findCustomer.customerFullName else customerFullName,
                customerPhoneNumber = if (customerPhoneNumber.isNullOrEmpty()) findCustomer.customerPhoneNumber else customerPhoneNumber,
                updatedAt = Date()
            )
        }

        val saveData = customerRepository.save(updatedDataRequest)
        return customerResponse {
            customerId = saveData.id.orEmpty()
            customerEmail = saveData.customerEmail.orEmpty()
            customerFullName = saveData.customerFullName.orEmpty()
            customerPhoneNumber = saveData.customerPhoneNumber.orEmpty()
            createdAt = saveData.createdAt.toString()
            updatedAt = saveData.updatedAt.toString()
        }
    }

    suspend fun deleteCustomer(request: DeleteCustomerRequest): CustomerResponse {
        val findCustomer = customerRepository.findByIdOrNull(request.customerId)
            ?: throw DataNotFound("Customer tidak ditemukan!")

        findCustomer.apply {
            customerRepository.delete(this)
        }
        return customerResponse {
            customerId = findCustomer.id.orEmpty()
            customerEmail = findCustomer.customerEmail.orEmpty()
            customerFullName = findCustomer.customerFullName.orEmpty()
            customerPhoneNumber = findCustomer.customerPhoneNumber.orEmpty()
            createdAt = findCustomer.createdAt.toString()
            updatedAt = findCustomer.updatedAt.toString()
        }
    }
}
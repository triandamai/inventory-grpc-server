package app.trian.inventory.module.customer

import app.trian.inventory.v1.customer.CreateNewCustomerRequest
import app.trian.inventory.v1.customer.UpdateCustomerRequest
import app.trian.inventory.v1.customer.deleteCustomerRequest
import app.trian.inventory.v1.getById
import app.trian.inventory.v1.getPagingRequest
import kotlinx.coroutines.coroutineScope
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(
    value = ["/api/v1"]
)
class CustomerController(
    private val customerService: CustomerService
) {
    @GetMapping(
        value = ["/customers"],
        produces = ["application/json"]
    )
    suspend fun getListSuppliers(
        @RequestParam(name = "page") nextPage: Long? = 0
    ) = coroutineScope {
        customerService.getListCustomer(getPagingRequest {
            page = nextPage ?: 0
        })
    }

    @GetMapping(
        value = ["/customer/{customerId}"],
        produces = ["application/json"]
    )
    suspend fun getSupplierById(
        @PathVariable(name = "customerId") customerId: String=""
    ) = coroutineScope {
        customerService.getCustomerById(
            getById {
                resourceId = customerId
            }
        )
    }

    @PostMapping(
        value = ["/customer"],
        produces = ["application/json"],
        consumes = ["application/json"]
    )
    suspend fun createNewCustomer(@RequestBody request: CreateNewCustomerRequest) = coroutineScope {
        customerService.createNewCustomer(request)
    }

    @PutMapping(
        value = ["/customer"],
        produces = ["application/json"],
        consumes = ["application/json"]
    )
    suspend fun updateCustomer(
        @RequestBody request: UpdateCustomerRequest
    ) = coroutineScope {
        customerService.updateCustomer(
            request
        )
    }

    @DeleteMapping(
        value = ["/customer/{customerId}"],
        produces = ["application/json"]
    )
    suspend fun deleteCustomer(@PathVariable(name = "customerId") resourceId: String) = coroutineScope {
        customerService.deleteCustomer(
            deleteCustomerRequest {
                customerId = resourceId
            }
        )
    }
}
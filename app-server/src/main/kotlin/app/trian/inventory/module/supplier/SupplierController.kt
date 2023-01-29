package app.trian.inventory.module.supplier

import app.trian.inventory.v1.customer.CreateNewCustomerRequest
import app.trian.inventory.v1.customer.UpdateCustomerRequest
import app.trian.inventory.v1.customer.deleteCustomerRequest
import app.trian.inventory.v1.getById
import app.trian.inventory.v1.getPagingRequest
import app.trian.inventory.v1.supplier.CreateNewSupplierRequest
import app.trian.inventory.v1.supplier.UpdateSupplierRequest
import app.trian.inventory.v1.supplier.deleteSupplierRequest
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
class SupplierController(
    private val supplierService: SupplierService
) {
    @GetMapping(
        value = ["/suppliers"],
        produces = ["application/json"]
    )
    suspend fun getListSupplier(
        @RequestParam(name = "page") nextPage: Long? = 0
    ) = coroutineScope {
        supplierService.getListSupplier(
            getPagingRequest {
                page = nextPage ?: 0
            }
        )
    }

    @GetMapping(
        value = ["/supplier/{supplierId}"],
        produces = ["application/json"]
    )
    suspend fun getSupplierById(
        @PathVariable(name = "supplierId") supplierId:String =""
    ) = coroutineScope {
        supplierService.getSupplierById(
            getById {
                resourceId = supplierId
            }
        )
    }

    @PostMapping(
        value = ["/supplier"],
        produces = ["application/json"],
        consumes = ["application/json"]
    )
    suspend fun createNewSupplier(@RequestBody request: CreateNewSupplierRequest) = coroutineScope {
        supplierService.createNewSupplier(request)
    }

    @PutMapping(
        value = ["/supplier"],
        produces = ["application/json"],
        consumes = ["application/json"]
    )
    suspend fun updateCustomer(
        @RequestBody request: UpdateSupplierRequest
    ) = coroutineScope {
        supplierService.updateSupplier(
            request
        )
    }

    @DeleteMapping(
        value = ["/supplier/{supplierId}"],
        produces = ["application/json"]
    )
    suspend fun deleteCustomer(@PathVariable(name = "supplierId") resourceId: String) = coroutineScope {
        supplierService.deleteSupplier(
            deleteSupplierRequest {
                customerId = resourceId
            }
        )
    }
}
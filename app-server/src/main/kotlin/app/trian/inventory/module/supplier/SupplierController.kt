package app.trian.inventory.module.supplier

import org.springframework.web.bind.annotation.RestController

@RestController
class SupplierController(
    private val supplierService: SupplierService
) {
}
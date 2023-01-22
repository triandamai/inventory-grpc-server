package app.trian.inventory.module.supplier

import org.springframework.data.repository.PagingAndSortingRepository

interface SupplierRepository:PagingAndSortingRepository<Supplier,Int> {
}
package app.trian.inventory.module.supplier

import org.springframework.data.repository.PagingAndSortingRepository

interface SupplierRepository:PagingAndSortingRepository<Supplier,String> {
    fun findTopBySupplierEmail(userEmail:String):Supplier?
}
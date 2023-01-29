package app.trian.inventory.module.inbound


import app.trian.inventory.module.supplier.Supplier
import app.trian.inventory.module.user.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.PagingAndSortingRepository

interface InboundRepository:PagingAndSortingRepository<Inbound,String> {
    fun findAllByCashier(cashier :User, pageable: Pageable): Page<Inbound>

    fun  finAllBySupplier(supplier :Supplier, pageable: Pageable): Page<Inbound>
}
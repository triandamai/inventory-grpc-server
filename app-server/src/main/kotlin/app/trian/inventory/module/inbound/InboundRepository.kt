package app.trian.inventory.module.inbound


import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.PagingAndSortingRepository

interface InboundRepository:PagingAndSortingRepository<Inbound,String> {
    fun findAllByCashierId(cashierId :String, pageable: Pageable): Page<Inbound>

    fun  finAllBySupplierId(supplierId :String, pageable: Pageable): Page<Inbound>
}
package app.trian.inventory.module.outbound

import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.PagingAndSortingRepository


interface OutboundRepository:PagingAndSortingRepository<Outbound,String> {
    fun findAllByCashierId(cashierId :String, pageable:Pageable): Page<Outbound>

    fun  findAllByCustomerId(customerId :String, pageable: Pageable): Page<Outbound>

}
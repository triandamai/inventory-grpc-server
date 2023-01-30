package app.trian.inventory.module.outbound

import app.trian.inventory.module.customer.Customer
import app.trian.inventory.module.user.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.PagingAndSortingRepository


interface OutboundRepository:PagingAndSortingRepository<Outbound,String> {
    fun findAllByCashier(cashier :User, pageable:Pageable): Page<Outbound>

    fun  findAllByCustomer(customer :Customer, pageable: Pageable): Page<Outbound>

}
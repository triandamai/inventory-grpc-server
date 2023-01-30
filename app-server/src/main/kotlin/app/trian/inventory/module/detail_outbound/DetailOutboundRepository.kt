package app.trian.inventory.module.detail_outbound


import app.trian.inventory.module.outbound.Outbound
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.PagingAndSortingRepository

interface DetailOutboundRepository:PagingAndSortingRepository<DetailOutbound,String> {



    fun findAllByOutbound(outbound: Outbound, pageable: Pageable):Page<DetailOutbound>
}
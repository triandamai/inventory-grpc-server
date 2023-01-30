package app.trian.inventory.module.detail_inbound


import app.trian.inventory.module.inbound.Inbound
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.PagingAndSortingRepository

interface DetailInboundRepository:PagingAndSortingRepository<DetailInbound,String> {


    fun findAllByInbound(inbound: Inbound, pageable: Pageable): Page<DetailInbound>
}
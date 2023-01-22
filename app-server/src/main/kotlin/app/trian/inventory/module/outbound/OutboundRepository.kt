package app.trian.inventory.module.outbound

import org.springframework.data.repository.PagingAndSortingRepository

interface OutboundRepository:PagingAndSortingRepository<Outbound,Int> {
}
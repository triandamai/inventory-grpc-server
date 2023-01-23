package app.trian.inventory.module.inbound

import org.springframework.data.repository.PagingAndSortingRepository

interface InboundRepository:PagingAndSortingRepository<Inbound,String> {
}
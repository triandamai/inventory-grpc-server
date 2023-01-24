package app.trian.inventory.module.outbound

import org.springframework.stereotype.Service

@Service
class OutboundService(
    private val outboundRepository: OutboundRepository
) {

}
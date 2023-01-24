package app.trian.inventory.module.inbound

import org.springframework.stereotype.Service

@Service
class InboundService(
    private val inboundRepository: InboundRepository
) {
}
package app.trian.inventory.module.inbound

import org.springframework.web.bind.annotation.RestController

@RestController
class InboundController(
    private val inboundService: InboundService
) {
}
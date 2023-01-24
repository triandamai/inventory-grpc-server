package app.trian.inventory.module.outbound

import org.springframework.web.bind.annotation.RestController

@RestController
class OutboundController(
    private val outboundService: OutboundService
) {
}
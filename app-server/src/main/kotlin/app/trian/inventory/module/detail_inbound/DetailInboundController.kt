package app.trian.inventory.module.detail_inbound

import org.springframework.web.bind.annotation.RestController

@RestController
class DetailInboundController(
    private val detailInboundService: DetailInboundService
) {
}
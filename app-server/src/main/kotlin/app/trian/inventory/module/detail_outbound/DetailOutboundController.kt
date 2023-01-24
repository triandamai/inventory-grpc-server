package app.trian.inventory.module.detail_outbound

import org.springframework.web.bind.annotation.RestController

@RestController
class DetailOutboundController(
    private val detailOutboundService: DetailOutboundService
) {
}
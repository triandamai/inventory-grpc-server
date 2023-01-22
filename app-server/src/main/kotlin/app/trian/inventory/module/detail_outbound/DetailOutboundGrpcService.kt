package app.trian.inventory.module.detail_outbound

import app.trian.inventory.v1.detail_outbound.DetailOutboundGrpcKt
import net.devh.boot.grpc.server.service.GrpcService

@GrpcService
class DetailOutboundGrpcService(
    private val detailOutboundRepository: DetailOutboundRepository
):DetailOutboundGrpcKt.DetailOutboundCoroutineImplBase() {
}
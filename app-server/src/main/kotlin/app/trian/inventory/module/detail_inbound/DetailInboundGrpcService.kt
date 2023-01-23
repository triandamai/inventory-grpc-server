package app.trian.inventory.module.detail_inbound

import app.trian.inventory.v1.detail_inbound.DetailInboundGrpcKt
import net.devh.boot.grpc.server.service.GrpcService

@GrpcService
class DetailInboundGrpcService(
    private val detailInboundRepository: DetailInboundRepository
):DetailInboundGrpcKt.DetailInboundCoroutineImplBase() {
}
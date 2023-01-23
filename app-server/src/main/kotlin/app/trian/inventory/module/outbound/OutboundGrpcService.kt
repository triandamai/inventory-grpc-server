package app.trian.inventory.module.outbound

import app.trian.inventory.v1.outbound.OutboundGrpcKt
import net.devh.boot.grpc.server.service.GrpcService

@GrpcService
class OutboundGrpcService(
    private val outboundRepository: OutboundRepository
):OutboundGrpcKt.OutboundCoroutineImplBase() {
}
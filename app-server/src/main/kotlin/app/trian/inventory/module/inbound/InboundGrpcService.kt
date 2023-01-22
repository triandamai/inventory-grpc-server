package app.trian.inventory.module.inbound

import app.trian.inventory.v1.inbound.InboundGrpcKt
import net.devh.boot.grpc.server.service.GrpcService

@GrpcService
class InboundGrpcService(
    private val inboundRepository: InboundRepository
):InboundGrpcKt.InboundCoroutineImplBase(){
}
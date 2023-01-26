package app.trian.inventory.module.outbound

import app.trian.inventory.v1.GetById
import app.trian.inventory.v1.GetPagingRequest
import app.trian.inventory.v1.GetPagingRequestWithId
import app.trian.inventory.v1.UpdateStatusRequest
import app.trian.inventory.v1.inbound.getListDetailInboundResponse
import app.trian.inventory.v1.outbound.CreateNewOutboundRequest
import app.trian.inventory.v1.outbound.DeleteDetailOutboundRequest
import app.trian.inventory.v1.outbound.DeleteOutboundRequest
import app.trian.inventory.v1.outbound.DetailOutboundResponse
import app.trian.inventory.v1.outbound.GetListDetailOutboundResponse
import app.trian.inventory.v1.outbound.GetListOutboundResponse
import app.trian.inventory.v1.outbound.OutboundGrpcKt
import app.trian.inventory.v1.outbound.OutboundResponse
import app.trian.inventory.v1.outbound.UpdateOutboundRequest
import app.trian.inventory.v1.outbound.getListDetailOutboundResponse
import app.trian.inventory.v1.outbound.getListOutboundResponse
import app.trian.inventory.v1.outbound.outboundResponse
import net.devh.boot.grpc.server.service.GrpcService

@GrpcService
class OutboundGrpcService(
    private val outboundService: OutboundService
):OutboundGrpcKt.OutboundCoroutineImplBase() {
    override suspend fun getListOutbound(request: GetPagingRequest): GetListOutboundResponse =
        outboundService.getListOutbound(request)

    override suspend fun getListOutboundByCashier(request: GetPagingRequestWithId): GetListOutboundResponse =
        outboundService.getListOutboundByCashier(request)

    override suspend fun getListOutboundByCustomer(request: GetPagingRequestWithId): GetListOutboundResponse =
        outboundService.getListOutboundByCustomer(request)
    override suspend fun getDetailOutbound(request: GetById): OutboundResponse =
        outboundService.getDetailOutbound(request)

    override suspend fun getListDetailOutbound(request: GetPagingRequestWithId): GetListDetailOutboundResponse =
        outboundService.getListDetailOutbound(request)

    override suspend fun createNewOutbound(request: CreateNewOutboundRequest): OutboundResponse =
        outboundService.createNewOutbound(request)

    override suspend fun updateOutbound(request: UpdateOutboundRequest): OutboundResponse =
        outboundService.updateOutbound(request)

    override suspend fun updateStatusOutbound(request: UpdateStatusRequest): OutboundResponse =
        outboundService.updateStatusOutbound(request)

    override suspend fun deleteOutbound(request: DeleteOutboundRequest): OutboundResponse =
        outboundService.deleteOutbound(request)

    override suspend fun deleteDetailOutbound(request: DeleteDetailOutboundRequest): DetailOutboundResponse =
        outboundService.deleteDetailOutbound(request)
}
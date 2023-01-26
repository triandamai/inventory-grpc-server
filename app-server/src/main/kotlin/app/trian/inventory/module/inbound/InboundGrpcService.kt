package app.trian.inventory.module.inbound

import app.trian.inventory.v1.GetById
import app.trian.inventory.v1.GetPagingRequest
import app.trian.inventory.v1.GetPagingRequestWithId
import app.trian.inventory.v1.UpdateStatusRequest
import app.trian.inventory.v1.inbound.CreateNewInboundRequest
import app.trian.inventory.v1.inbound.DeleteDetailInboundRequest
import app.trian.inventory.v1.inbound.DeleteInboundRequest
import app.trian.inventory.v1.inbound.DetailInboundResponse
import app.trian.inventory.v1.inbound.GetListDetailInboundResponse
import app.trian.inventory.v1.inbound.GetListInboundResponse
import app.trian.inventory.v1.inbound.InboundGrpcKt
import app.trian.inventory.v1.inbound.InboundResponse
import app.trian.inventory.v1.inbound.UpdateInboundRequest
import app.trian.inventory.v1.inbound.detailInboundResponse
import app.trian.inventory.v1.inbound.getListDetailInboundResponse
import app.trian.inventory.v1.inbound.getListInboundResponse
import app.trian.inventory.v1.inbound.inboundResponse
import net.devh.boot.grpc.server.service.GrpcService

@GrpcService
class InboundGrpcService(
    private val inboundService: InboundService
) : InboundGrpcKt.InboundCoroutineImplBase() {
    override suspend fun getListInbound(request: GetPagingRequest): GetListInboundResponse =
        inboundService.getListInbound(request)

    override suspend fun getListInboundByCashier(request: GetPagingRequestWithId): GetListInboundResponse =
        inboundService.getListInboundByCashier(request)

    override suspend fun getListInboundBySupplier(request: GetPagingRequestWithId): GetListInboundResponse =
        inboundService.getListInboundBySupplier(request)

    override suspend fun getDetailInbound(request: GetById): InboundResponse =
        inboundService.getDetailInbound(request)

    override suspend fun getListDetailInbound(request: GetPagingRequestWithId): GetListDetailInboundResponse =
        inboundService.getListDetailInbound(request)

    override suspend fun createNewInbound(request: CreateNewInboundRequest): InboundResponse =
        inboundService.createNewInbound(request)
    override suspend fun updateInbound(request: UpdateInboundRequest): InboundResponse =
        inboundService.updateInbound(request)

    override suspend fun updateStatusInbound(request: UpdateStatusRequest): InboundResponse =
        inboundService.updateStatusInbound(request)

    override suspend fun deleteInbound(request: DeleteInboundRequest): InboundResponse =
        inboundService.deleteInbound(request)

    override suspend fun deleteDetailInbound(request: DeleteDetailInboundRequest): DetailInboundResponse =
        inboundService.deleteDetailInbound(request)


}
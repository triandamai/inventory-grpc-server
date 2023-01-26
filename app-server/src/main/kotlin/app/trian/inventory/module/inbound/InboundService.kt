package app.trian.inventory.module.inbound

import app.trian.inventory.module.detail_inbound.DetailInboundRepository
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
import app.trian.inventory.v1.inbound.InboundResponse
import app.trian.inventory.v1.inbound.UpdateInboundRequest
import app.trian.inventory.v1.inbound.detailInboundResponse
import app.trian.inventory.v1.inbound.getListDetailInboundResponse
import app.trian.inventory.v1.inbound.getListInboundResponse
import app.trian.inventory.v1.inbound.inboundResponse
import org.springframework.stereotype.Service

@Service
class InboundService(
    private val inboundRepository: InboundRepository,
    private val detailInboundRepository: DetailInboundRepository
) {
    /**
     * ambil semua list inbound
     * if(data == 0) throw DataNotFound
     * */
    suspend fun getListInbound(request: GetPagingRequest): GetListInboundResponse {
        return getListInboundResponse { }
    }

    /**
     * ambil data list inbound WHERE cashierId == resourceId
     * if(data == 0) throw DataNotFound
     * */
    suspend fun getListInboundByCashier(request: GetPagingRequestWithId): GetListInboundResponse {
        return getListInboundResponse { }
    }

    /**
     * ambil data list inbound WHERE supplierId == resourceId
     * if(data == 0) throw DataNotFound
     * */
    suspend fun getListInboundBySupplier(request: GetPagingRequestWithId): GetListInboundResponse {
        return getListInboundResponse { }
    }

    /**
     * ambil detail inbound WHERE inbound == resourceId
     * if(data == null) throw DataNotFound
     * */
    suspend fun getDetailInbound(request: GetById): InboundResponse {
        return inboundResponse { }
    }

    /**
     * ambil data list inbound WHERE inboundId == resourceId
     * if(data == 0) throw DataNotFound
     * */
    suspend fun getListDetailInbound(request: GetPagingRequestWithId): GetListDetailInboundResponse {
        return getListDetailInboundResponse { }
    }

    /**
     * save new Inbound (Transactional)
     * 1. find cashierId(User) if null throw NotFound
     * 2. find supplier if null throw NtFound
     * 3. save Inbound
     * 4. create List<DetailInBound>
     *     detail.forEach{ findProduct  }
     * 5. save DetailInbound
     * */
    suspend fun createNewInbound(request: CreateNewInboundRequest): InboundResponse {
        return inboundResponse { }
    }

    /**
     * 1. find inbound if null throw DataNotFound
     * 2. find supplier if null throw DataNotFound
     * 3. save inbound updated data
     * 4. detail.forEach{ set data }
     * 5. save detail inbound
     * */
    suspend fun updateInbound(request: UpdateInboundRequest): InboundResponse {
        return inboundResponse { }
    }

    /**
     * 1. find Inbound if null throw DataNotFound
     * 2. update status
     * 3. save data
     * */
    suspend fun updateStatusInbound(request: UpdateStatusRequest): InboundResponse {
        return inboundResponse { }
    }

    /**
     * 1. find Inbound if null throw DataNotFound
     * 2. delete data
     * */
    suspend fun deleteInbound(request: DeleteInboundRequest): InboundResponse {
        return inboundResponse { }
    }

    /**
     * 1. delete By Ids
     * */
    suspend fun deleteDetailInbound(request: DeleteDetailInboundRequest): DetailInboundResponse {
        return detailInboundResponse { }
    }
}
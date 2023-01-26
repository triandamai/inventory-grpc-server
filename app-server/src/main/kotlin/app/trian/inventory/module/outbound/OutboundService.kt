package app.trian.inventory.module.outbound

import app.trian.inventory.v1.GetById
import app.trian.inventory.v1.GetPagingRequest
import app.trian.inventory.v1.GetPagingRequestWithId
import app.trian.inventory.v1.UpdateStatusRequest
import app.trian.inventory.v1.outbound.CreateNewOutboundRequest
import app.trian.inventory.v1.outbound.DeleteDetailOutboundRequest
import app.trian.inventory.v1.outbound.DeleteOutboundRequest
import app.trian.inventory.v1.outbound.DetailOutboundResponse
import app.trian.inventory.v1.outbound.GetListDetailOutboundResponse
import app.trian.inventory.v1.outbound.GetListOutboundResponse
import app.trian.inventory.v1.outbound.OutboundResponse
import app.trian.inventory.v1.outbound.UpdateOutboundRequest
import app.trian.inventory.v1.outbound.detailOutboundResponse
import app.trian.inventory.v1.outbound.getListDetailOutboundResponse
import app.trian.inventory.v1.outbound.getListOutboundResponse
import app.trian.inventory.v1.outbound.outboundResponse
import org.springframework.stereotype.Service

@Service
class OutboundService(
    private val outboundRepository: OutboundRepository
) {

    /**
     * ambil semua list outbound
     * if(data == 0) throw DataNotFound
     * */
    suspend fun getListOutbound(request: GetPagingRequest): GetListOutboundResponse {

        return getListOutboundResponse { }
    }

    /**
     * ambil data list outbound WHERE cashierId == resourceId
     * if(data == 0) throw DataNotFound
     * */
    suspend fun getListOutboundByCashier(request: GetPagingRequestWithId): GetListOutboundResponse {
        return getListOutboundResponse { }
    }

    /**
     * ambil data list outbound WHERE supplierId == resourceId
     * if(data == 0) throw DataNotFound
     * */
    suspend fun getListOutboundByCustomer(request: GetPagingRequestWithId): GetListOutboundResponse {
        return getListOutboundResponse { }
    }

    /**
     * ambil detail outbound WHERE outbound == resourceId
     * if(data == null) throw DataNotFound
     * */
    suspend fun getDetailOutbound(request: GetById): OutboundResponse {
        return outboundResponse { }
    }

    /**
     * ambil data list outbound WHERE outboundId == resourceId
     * if(data == 0) throw DataNotFound
     * */
    suspend fun getListDetailOutbound(request: GetPagingRequestWithId): GetListDetailOutboundResponse {
        return getListDetailOutboundResponse { }
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
    suspend fun createNewOutbound(request: CreateNewOutboundRequest): OutboundResponse {
        return outboundResponse { }
    }

    /**
     * 1. find outbound if null throw DataNotFound
     * 2. find supplier if null throw DataNotFound
     * 3. save outbound updated data
     * 4. detail.forEach{ set data }
     * 5. save detail outbound
     * */
    suspend fun updateOutbound(request: UpdateOutboundRequest): OutboundResponse {
        return outboundResponse { }
    }

    /**
     * 1. find Inbound if null throw DataNotFound
     * 2. update status
     * 3. save data
     * */
    suspend fun updateStatusOutbound(request: UpdateStatusRequest): OutboundResponse {
        return outboundResponse { }
    }

    /**
     * 1. find Inbound if null throw DataNotFound
     * 2. delete data
     * */
    suspend fun deleteOutbound(request: DeleteOutboundRequest): OutboundResponse {
        return outboundResponse { }
    }

    /**
     * 1. delete By Ids
     * */
    suspend fun deleteDetailOutbound(request: DeleteDetailOutboundRequest): DetailOutboundResponse {
        return detailOutboundResponse { }
    }
}
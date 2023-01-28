package app.trian.inventory.module.outbound

import app.trian.inventory.module.error.DataNotFound
import app.trian.inventory.v1.GetById
import app.trian.inventory.v1.GetPagingRequest
import app.trian.inventory.v1.GetPagingRequestWithId
import app.trian.inventory.v1.UpdateStatusRequest
import app.trian.inventory.v1.customer.customerResponse
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
import app.trian.inventory.v1.user.userResponse
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
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
        val getListOutbound = outboundRepository.findAll(
            PageRequest.of(
                request.page.toInt(),
                50
            )
        )

        if (getListOutbound.isEmpty) throw DataNotFound("Data Tidak Ada")

        return getListOutboundResponse {
            currentPage = getListOutbound.number.toLong()

            totalItem = getListOutbound.totalElements

            totalPage = getListOutbound.totalPages.toLong()

            data += getListOutbound.content.map {
                outboundResponse {
                    outboundId = it.id.orEmpty()
                    statues = it.status.toLong()
                    cashier = with(it){
                        userResponse {
                            userId = cashier?.id.orEmpty()
                            userEmail = cashier?.userEmail.orEmpty()
                            userFullName = cashier?.userFullName.orEmpty()
                            authProvider = cashier?.authProvider.orEmpty()
                            createdAt = cashier?.createdAt.toString()
                            updatedAt = cashier?.updatedAt.toString()
                        }

                    }
                    customer = with(it){
                        customerResponse {
                            customerId = customer?.id.orEmpty()
                            customerEmail = customer?.customerEmail.orEmpty()
                            customerFullName = customer?.customerEmail.orEmpty()
                            customerPhoneNumber = customer?.customerPhoneNumber.orEmpty()
                            createdAt = customer?.createdAt.toString()
                            updatedAt = customer?.updatedAt.toString()
                        }
                    }
                    totalAmount = it.totalAmount.toLong()
                }
            }
        }
    }

    /**
     * ambil data list outbound WHERE cashierId == resourceId
     * if(data == 0) throw DataNotFound
     * */
    suspend fun getListOutboundByCashier(request: GetPagingRequestWithId): GetListOutboundResponse {
        val getOutboundByCashier = outboundRepository.findAllByCashierId(
            cashierId = request.resourceId,
            pageable = PageRequest.of(
                request.page.toInt(),
                50
            )
        )

        return getListOutboundResponse {
            totalItem = getOutboundByCashier.totalElements
            totalPage = getOutboundByCashier.totalPages.toLong()
            currentPage = getOutboundByCashier.number.toLong()

            data += getOutboundByCashier.content.map {
                outboundResponse {
                    outboundId = it.id.orEmpty()
                    totalAmount = it.totalAmount.toLong()
                    statues = it.status.toLong()
                    cashier = with(it){
                        userResponse {
                            id = cashier?.id.orEmpty()
                            userEmail = cashier?.userEmail.orEmpty()
                            userFullName = cashier?.userFullName.orEmpty()
                            authProvider = cashier?.authProvider.orEmpty()
                            createdAt = cashier?.createdAt.toString()
                            updatedAt = cashier?.updatedAt.toString()
                        }
                    }
                    customer = with(it){
                        customerResponse {
                            id = customer?.id.orEmpty()
                            customerFullName = customer?.customerFullName.orEmpty()
                            customerEmail = customer?.customerEmail.orEmpty()
                            customerPhoneNumber =  customer?.customerPhoneNumber.orEmpty()
                            createdAt = customer?.createdAt.toString()
                            updatedAt = customer?.updatedAt.toString()
                        }
                    }
                }
            }
        }
    }

    /**
     * ambil data list outbound WHERE supplierId == resourceId
     * if(data == 0) throw DataNotFound
     * */
    suspend fun getListOutboundByCustomer(request: GetPagingRequestWithId): GetListOutboundResponse {
        val getListOutboundByCustomer = outboundRepository.findAllByCustomerId(
            customerId = request.resourceId.orEmpty(),
            pageable = PageRequest.of(
                request.page.toInt(),
                50
            )
        )
        return getListOutboundResponse {
            currentPage = getListOutboundByCustomer.number.toLong()

            totalItem = getListOutboundByCustomer.totalElements

            totalPage = getListOutboundByCustomer.totalPages.toLong()

            data += getListOutboundByCustomer.content.map {
                outboundResponse {
                    outboundId = it.id.orEmpty()
                    statues = it.status.toLong()
                    totalAmount = it.totalAmount.toLong()
                    cashier = with(it){
                        userResponse {
                            id = cashier?.id
                            userFullName = cashier?.userFullName.orEmpty()
                            userEmail = cashier?.userEmail.orEmpty()
                            authProvider = cashier?.authProvider.orEmpty()
                            createdAt = cashier?.createdAt.toString()
                            updatedAt = cashier?.updatedAt.toString()
                        }
                    }
                    customer = with(it){
                        customerResponse {
                            customerId = customer?.id.orEmpty()
                            customerFullName = customer?.customerFullName.orEmpty()
                            customerEmail = customer?.customerEmail.orEmpty()
                            customerPhoneNumber = customer?.customerPhoneNumber.orEmpty()
                            createdAt = customer?.createdAt.toString()
                            updatedAt = customer?.updatedAt.toString()
                        }
                    }
                }
            }


        }
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
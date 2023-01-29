package app.trian.inventory.module.outbound

import app.trian.inventory.module.detail_outbound.DetailOutbound
import app.trian.inventory.module.detail_outbound.DetailOutboundRepository
import app.trian.inventory.module.error.DataNotFound
import app.trian.inventory.v1.GetById
import app.trian.inventory.v1.GetPagingRequest
import app.trian.inventory.v1.GetPagingRequestWithId
import app.trian.inventory.v1.UpdateStatusRequest
import app.trian.inventory.v1.category.categoryResponse
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
import app.trian.inventory.v1.product.productResponse
import app.trian.inventory.v1.user.userResponse
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import javax.transaction.Transactional
import javax.xml.crypto.Data

@Service
class OutboundService(
    private val outboundRepository: OutboundRepository,
    private val detailOutbound: DetailOutboundRepository
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
        if (getOutboundByCashier.isEmpty) throw DataNotFound("data is empty")
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
                    createdAt = it.createdAt.toString()
                    updatedAt = it.updatedAt.toString()
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
        if (getListOutboundByCustomer.isEmpty) throw DataNotFound("data is empty")
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
                    createdAt = it.createdAt.toString()
                    updatedAt = it.updatedAt.toString()
                }
            }


        }
    }

    /**
     * ambil detail outbound WHERE outbound == resourceId
     * if(data == null) throw DataNotFound
     * */
    suspend fun getDetailOutbound(request: GetById): OutboundResponse {
        val getDetailOutbound = detailOutbound.findByOutbound(request.resourceId)?:
        throw DataNotFound("DetailOutbound Not found")

        return outboundResponse {
            outboundId = getDetailOutbound.id.orEmpty()
            statues = getDetailOutbound.status.toLong()
            totalAmount = getDetailOutbound.totalAmount.toLong()
            cashier = with(getDetailOutbound){
                userResponse {
                    id = cashier?.id
                    userFullName = cashier?.userFullName.orEmpty()
                    userEmail = cashier?.userEmail.orEmpty()
                    authProvider = cashier?.authProvider.orEmpty()
                    createdAt = cashier?.createdAt.toString()
                    updatedAt = cashier?.updatedAt.toString()
                }
            }
            customer = with(getDetailOutbound){
                customerResponse {
                    customerId = customer?.id.orEmpty()
                    customerFullName = customer?.customerFullName.orEmpty()
                    customerEmail = customer?.customerEmail.orEmpty()
                    customerPhoneNumber = customer?.customerPhoneNumber.orEmpty()
                    createdAt = customer?.createdAt.toString()
                    updatedAt = customer?.updatedAt.toString()
                }
            }
            createdAt = getDetailOutbound.createdAt.toString()
            updatedAt = getDetailOutbound.updatedAt.toString()
        }
    }

    /**
     * ambil data list outbound WHERE outboundId == resourceId
     * if(data == 0) throw DataNotFound
     * */
    suspend fun getListDetailOutbound(request: GetPagingRequestWithId): GetListDetailOutboundResponse {
        val getListDetailId = detailOutbound.findAllByOutboundId(
            outboundId = request.resourceId,
            pageable = PageRequest.of(
                request.page.toInt(),
                50
            )
        )
        if (getListDetailId.isEmpty) throw DataNotFound("data is empty")
        return getListDetailOutboundResponse {
            totalItem = getListDetailId.totalElements
            totalPage = getListDetailId.totalPages.toLong()
            currentPage = getListDetailId.number.toLong()
            data += getListDetailId.content.map {
                detailOutboundResponse {
                    detailOutboundId = it.id.orEmpty()
                    //quantity = it.quantity//quantity gada bang adanya total amount
                    //price = it.price  //price juga gada bang
                    totalPrice = it.totalAmount.toLong() //totalprice juga g nemu bang
                    product = with(it){
                        productResponse {
                            productId = product?.id.orEmpty()
                            productName = product?.productName.orEmpty()
                            productUnit = product?.productUnit.orEmpty()
                            productOutboundPrice = product?.productOutboundPrice!!.toLong()
                            productStock = product?.productStock!!.toLong()
                            productDescription = product?.productDescription.orEmpty()
                            productImage = product?.productImage.orEmpty()
                            createdAt = product?.createdAt.toString()
                            updatedAt = product?.updatedAt.toString()
                            category = categoryResponse {
                                product?.category?.map {
                                    categoryId = it.id.orEmpty()
                                    categoryName = it.categoryName.orEmpty()
                                    categoryDescription = it.categoryDescription.orEmpty()
                                    createdAt = it.createdAt.toString()
                                    updatedAt = it.updatedAt.toString()
                                }
                            }
                        }
                    }
                    outbound =with(it) {
                        outboundResponse {
                            outboundId = outbound?.id.orEmpty()
                            statues = outbound?.status!!.toLong()
                            totalAmount =  outbound?.totalAmount!!.toLong()
                            createdAt = outbound?.createdAt.toString()
                            updatedAt = outbound?.updatedAt.toString()
                            cashier = userResponse {
                                    id = outbound?.cashier?.id
                                    userFullName = outbound?.cashier?.userFullName.orEmpty()
                                    userEmail = outbound?.cashier?.userEmail.orEmpty()
                                    authProvider = outbound?.cashier?.authProvider.orEmpty()
                                    createdAt = outbound?.cashier?.createdAt.toString()
                                    updatedAt = outbound?.cashier?.updatedAt.toString()
                                }
                            customer = customerResponse {
                                    customerId = outbound?.customer?.id.orEmpty()
                                    customerFullName = outbound?.customer?.customerFullName.orEmpty()
                                    customerEmail = outbound?.customer?.customerEmail.orEmpty()
                                    customerPhoneNumber = outbound?.customer?.customerPhoneNumber.orEmpty()
                                    createdAt = outbound?.customer?.createdAt.toString()
                                    updatedAt = outbound?.customer?.updatedAt.toString()
                                }
                        }
                    }
                }

            }

        }
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
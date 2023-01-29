package app.trian.inventory.module.outbound

import app.trian.inventory.module.customer.CustomerRepository
import app.trian.inventory.module.detail_outbound.DetailOutbound
import app.trian.inventory.module.detail_outbound.DetailOutboundRepository
import app.trian.inventory.module.error.DataNotFound
import app.trian.inventory.module.user.UserRepository
import app.trian.inventory.v1.GetById
import app.trian.inventory.v1.GetPagingRequest
import app.trian.inventory.v1.GetPagingRequestWithId
import app.trian.inventory.v1.UpdateStatusRequest
import app.trian.inventory.v1.category.categoryResponse
import app.trian.inventory.v1.customer.customerResponse
import app.trian.inventory.v1.outbound.*
import app.trian.inventory.v1.product.productResponse
import app.trian.inventory.v1.user.userResponse
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.util.*
import javax.transaction.Transactional
import javax.xml.crypto.Data

@Service
class OutboundService(
    private val outboundRepository: OutboundRepository,
    private val detailOutbound: DetailOutboundRepository,
    private val userRepository: UserRepository,
    private val customerRepository: CustomerRepository
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
                    statues = it.status?.toLong() ?: 0
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
                    totalAmount = it.totalAmount?.toLong() ?: 0
                }
            }
        }
    }

    /**
     * ambil data list outbound WHERE cashierId == resourceId
     * if(data == 0) throw DataNotFound
     * */
    suspend fun getListOutboundByCashier(request: GetPagingRequestWithId): GetListOutboundResponse {
       val getCashier = userRepository.findByIdOrNull(request.resourceId)?:
       throw DataNotFound("Cashier tidak ditemkan")

        val getOutboundByCashier = outboundRepository.findAllByCashier(
            cashier = getCashier,
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
                    totalAmount = it.totalAmount?.toLong() ?: 0
                    statues = it.status?.toLong() ?: 0
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
       val getCustomer = customerRepository.findByIdOrNull(request.resourceId)?:
       throw DataNotFound("cutomer tidak ditemukan")

        val getListOutboundByCustomer = outboundRepository.findAllByCustomer(
            customer = getCustomer,
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
                    statues = it.status?.toLong() ?: 0
                    totalAmount = it.totalAmount?.toLong() ?: 0
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
        val getDetailOutbound = outboundRepository.findByIdOrNull(request.resourceId)
            ?:throw DataNotFound("DetailOutbound Not found")

        return outboundResponse {
            outboundId = getDetailOutbound.id.orEmpty()
            statues = getDetailOutbound.status?.toLong() ?: 0
            totalAmount = getDetailOutbound.totalAmount?.toLong() ?: 0
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
        val getOutbound = outboundRepository.findByIdOrNull(request.resourceId)?:
        throw DataNotFound("outbound tidak ditemukan")

        val getListDetailId = detailOutbound.findAllByOutbound(
            outbound = getOutbound,
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
                    quantity = it.quantity.toLong()
                    price = it.price.toLong()
                    totalPrice = it.totalPrice.toLong()
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
                    outbound = with(it) {
                        outboundResponse {
                            outboundId = outbound?.id.orEmpty()
                            statues = outbound?.status?.toLong()?:0
                            totalAmount =  outbound?.totalAmount?.toLong()?:0
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
    @Transactional
    suspend fun createNewOutbound(request: CreateNewOutboundRequest): OutboundResponse {
        val findCashierId = userRepository.findByIdOrNull(request.cashierId)
            ?:throw DataNotFound("cashier not found")

        val findCustomer = customerRepository.findByIdOrNull(request.customerId)
            ?:throw DataNotFound("customer not found")
        val outbound = Outbound()

        val saveOutbound = outboundRepository.save(
            outbound.copy(
                id = null,
                status = request.status.toString(),
                totalAmount = request.totalAmount.toInt(),
                cashier = findCashierId,
                customer = findCustomer,
                createdAt = Date(),
                updatedAt = Date()
            )
        )
        return outboundResponse {
            outboundId = saveOutbound.id.orEmpty()
            statues = saveOutbound.status?.toLong()?:0
            totalAmount =  saveOutbound.totalAmount?.toLong()?:0
            createdAt = saveOutbound.createdAt.toString()
            updatedAt = saveOutbound.updatedAt.toString()
            cashier = userResponse {
                userId = saveOutbound.cashier?.id.orEmpty()
                userFullName = saveOutbound.cashier?.userFullName.orEmpty()
                userEmail = saveOutbound.cashier?.userEmail.orEmpty()
                authProvider = saveOutbound.cashier?.authProvider.orEmpty()
                createdAt = saveOutbound.cashier?.createdAt.toString()
                updatedAt = saveOutbound.cashier?.updatedAt.toString()
            }
            customer = customerResponse {
                customerId = saveOutbound.customer?.id.orEmpty()
                customerFullName = saveOutbound.customer?.customerFullName.orEmpty()
                customerEmail = saveOutbound.customer?.customerEmail.orEmpty()
                customerPhoneNumber = saveOutbound.customer?.customerPhoneNumber.orEmpty()
                createdAt = saveOutbound.customer?.createdAt.toString()
                updatedAt = saveOutbound.customer?.updatedAt.toString()
            }
        }
    }

    /**
     * 1. find outbound if null throw DataNotFound
     * 2. find supplier if null throw DataNotFound
     * 3. save outbound updated data
     * 4. detail.forEach{ set data }
     * 5. save detail outbound
     * */
    suspend fun updateOutbound(request: UpdateOutboundRequest): OutboundResponse {
        val findOutbound = outboundRepository.findByIdOrNull(request.outboundId)
            ?:throw DataNotFound("outbound tidak ditemukan")

        val findCustomer = customerRepository.findByIdOrNull(request.customerId)
            ?:throw DataNotFound("customer not found")

        val updateOutbound = with(request){
            findOutbound.copy(
                id = null,
                status = if (status.isNullOrEmpty()) findOutbound.status else status,
                totalAmount = totalAmount.toInt(),
                updatedAt = Date(),
                customer = findCustomer
            )
        }
        return outboundResponse {
            outboundId = updateOutbound.id.orEmpty()
            statues = updateOutbound.status?.toLong()?:0
            totalAmount =  updateOutbound.totalAmount?.toLong()?:0
            createdAt = updateOutbound.createdAt.toString()
            updatedAt = updateOutbound.updatedAt.toString()
            cashier = userResponse {
                userId = updateOutbound.cashier?.id.orEmpty()
                userFullName = updateOutbound.cashier?.userFullName.orEmpty()
                userEmail = updateOutbound.cashier?.userEmail.orEmpty()
                authProvider = updateOutbound.cashier?.authProvider.orEmpty()
                createdAt = updateOutbound.cashier?.createdAt.toString()
                updatedAt = updateOutbound.cashier?.updatedAt.toString()
            }
            customer = customerResponse {
                customerId = updateOutbound.customer?.id.orEmpty()
                customerFullName = updateOutbound.customer?.customerFullName.orEmpty()
                customerEmail = updateOutbound.customer?.customerEmail.orEmpty()
                customerPhoneNumber = updateOutbound.customer?.customerPhoneNumber.orEmpty()
                createdAt = updateOutbound.customer?.createdAt.toString()
                updatedAt = updateOutbound.customer?.updatedAt.toString()
            }
        }
    }

    /**
     * 1. find Inbound if null throw DataNotFound
     * 2. update status
     * 3. save data
     * */
    suspend fun updateStatusOutbound(request: UpdateStatusRequest): OutboundResponse {
        val findOutbound = outboundRepository.findByIdOrNull(request.transactionId)
            ?:throw DataNotFound("outbound tidak ditemukan")

        val saveStatus = with(request){
            findOutbound.copy(
                status = if (status.isNullOrEmpty()) findOutbound.status else status
            )
        }
        return outboundResponse {
            outboundId = saveStatus.id.orEmpty()
            statues = saveStatus.status?.toLong() ?: 0
            totalAmount =  saveStatus.totalAmount?.toLong() ?:0
            createdAt = saveStatus.createdAt.toString()
            updatedAt = saveStatus.updatedAt.toString()
            cashier = userResponse {
                userId = saveStatus.cashier?.id.orEmpty()
                userFullName = saveStatus.cashier?.userFullName.orEmpty()
                userEmail = saveStatus.cashier?.userEmail.orEmpty()
                authProvider = saveStatus.cashier?.authProvider.orEmpty()
                createdAt = saveStatus.cashier?.createdAt.toString()
                updatedAt = saveStatus.cashier?.updatedAt.toString()
            }
            customer = customerResponse {
                customerId = saveStatus.customer?.id.orEmpty()
                customerFullName = saveStatus.customer?.customerFullName.orEmpty()
                customerEmail = saveStatus.customer?.customerEmail.orEmpty()
                customerPhoneNumber = saveStatus.customer?.customerPhoneNumber.orEmpty()
                createdAt = saveStatus.customer?.createdAt.toString()
                updatedAt = saveStatus.customer?.updatedAt.toString()
            }
        }
    }

    /**
     * 1. find Inbound if null throw DataNotFound
     * 2. delete data
     * */
    suspend fun deleteOutbound(request: DeleteOutboundRequest): OutboundResponse {
        val findOutbound = outboundRepository.findByIdOrNull(request.outboundId)
            ?:throw DataNotFound("outbound tiidak ditemukan")
        
        findOutbound.id.let{
            outboundRepository.deleteById(it.orEmpty())
        }
        
        return outboundResponse {
            outboundId = findOutbound.id.orEmpty()
            statues = findOutbound.status?.toLong()?:0
            totalAmount =  findOutbound.totalAmount?.toLong()?:0
            createdAt = findOutbound.createdAt.toString()
            updatedAt = findOutbound.updatedAt.toString()
            cashier = userResponse {
                userId = findOutbound.cashier?.id.orEmpty()
                userFullName = findOutbound.cashier?.userFullName.orEmpty()
                userEmail = findOutbound.cashier?.userEmail.orEmpty()
                authProvider = findOutbound.cashier?.authProvider.orEmpty()
                createdAt = findOutbound.cashier?.createdAt.toString()
                updatedAt = findOutbound.cashier?.updatedAt.toString()
            }
            customer = customerResponse {
                customerId = findOutbound.customer?.id.orEmpty()
                customerFullName = findOutbound.customer?.customerFullName.orEmpty()
                customerEmail = findOutbound.customer?.customerEmail.orEmpty()
                customerPhoneNumber = findOutbound.customer?.customerPhoneNumber.orEmpty()
                createdAt = findOutbound.customer?.createdAt.toString()
                updatedAt = findOutbound.customer?.updatedAt.toString()
            }
        }
    }

    /**
     * 1. delete By Ids
     * */
    suspend fun deleteDetailOutbound(request: DeleteDetailOutboundRequest): DeleteDetailOutboundResponse {

        userRepository.deleteAllById(request.detailOutboundIdList)

        return deleteDetailOutboundResponse {
            success = true
            detaiOutboundId += request.detailOutboundIdList.map { it }
        }
    }
}
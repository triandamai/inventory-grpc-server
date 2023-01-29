package app.trian.inventory.module.inbound

import app.trian.inventory.module.detail_inbound.DetailInboundRepository
import app.trian.inventory.module.error.DataNotFound
import app.trian.inventory.v1.GetById
import app.trian.inventory.v1.GetPagingRequest
import app.trian.inventory.v1.GetPagingRequestWithId
import app.trian.inventory.v1.UpdateStatusRequest
import app.trian.inventory.v1.category.categoryResponse
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
import app.trian.inventory.v1.product.productResponse
import app.trian.inventory.v1.supplier.supplierResponse
import app.trian.inventory.v1.user.userResponse
import org.springframework.data.domain.PageRequest
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
        val getListInbound = inboundRepository.findAll(
            PageRequest.of(
                request.page.toInt(),
                50
            )
        )
        if (getListInbound.isEmpty) throw DataNotFound("Data Tidak Ada")

        return getListInboundResponse {
            totalItem = getListInbound.totalElements
            totalPage = getListInbound.totalPages.toLong()
            currentPage = getListInbound.number.toLong()
            data += getListInbound.content.map {
                inboundResponse {
                   inboundId = it.id.orEmpty()
                   totalAmount = it.totalAmount.toLong()
                   statue = it.status.toLong()
                   createdAt = it.createdAt.toString()
                   updatedAt = it.updatedAt.toString()
                   cashier = with(it) {
                       userResponse {
                           userId = cashier?.id.orEmpty()
                           userEmail = cashier?.userEmail.orEmpty()
                           userFullName = cashier?.userFullName.orEmpty()
                           authProvider = cashier?.authProvider.orEmpty()
                           createdAt = cashier?.createdAt.toString()
                           updatedAt = cashier?.updatedAt.toString()
                       }
                   }
                    supplier = with(it){
                        supplierResponse {
                            supplierId = supplier?.id.orEmpty()
                            supplierAddress = supplier?.supplierAddress.orEmpty()
                            supplierOrgName = supplier?.supplierOrgName.orEmpty()
                            supplierEmail = supplier?.supplierEmail.orEmpty()
                            supplierFullName = supplier?.supplierFullName.orEmpty()
                            supplierPhoneNumber = supplier?.supplierPhoneNumber.orEmpty()
                            createdAt = supplier?.createdAt.toString()
                            updatedAt = supplier?.updatedAt.toString()
                        }
                    }
                }

            }

        }
    }

    /**
     * ambil data list inbound WHERE cashierId == resourceId
     * if(data == 0) throw DataNotFound
     * */
    suspend fun getListInboundByCashier(request: GetPagingRequestWithId): GetListInboundResponse {
        val getlistByCashierId  = inboundRepository.findAllByCashierId(
            cashierId = request.resourceId.orEmpty(),
            pageable = PageRequest.of(
              request.page.toInt(),
                50
            )
        )
        if (getlistByCashierId.isEmpty) throw DataNotFound("data tidak ditemukan")
        return getListInboundResponse {
            totalItem = getlistByCashierId.totalElements
            totalPage = getlistByCashierId.totalPages.toLong()
            currentPage = getlistByCashierId.number.toLong()
            data += getlistByCashierId.content.map {
                inboundResponse {
                    inboundId = it.id.orEmpty()
                    totalAmount = it.totalAmount.toLong()
                    statue = it.status.toLong()
                    createdAt = it.createdAt.toString()
                    updatedAt = it.updatedAt.toString()
                    cashier = with(it) {
                        userResponse {
                            userId = cashier?.id.orEmpty()
                            userEmail = cashier?.userEmail.orEmpty()
                            userFullName = cashier?.userFullName.orEmpty()
                            authProvider = cashier?.authProvider.orEmpty()
                            createdAt = cashier?.createdAt.toString()
                            updatedAt = cashier?.updatedAt.toString()
                        }
                    }
                    supplier = with(it){
                        supplierResponse {
                            supplierId = supplier?.id.orEmpty()
                            supplierAddress = supplier?.supplierAddress.orEmpty()
                            supplierOrgName = supplier?.supplierOrgName.orEmpty()
                            supplierEmail = supplier?.supplierEmail.orEmpty()
                            supplierFullName = supplier?.supplierFullName.orEmpty()
                            supplierPhoneNumber = supplier?.supplierPhoneNumber.orEmpty()
                            createdAt = supplier?.createdAt.toString()
                            updatedAt = supplier?.updatedAt.toString()
                        }
                    }
                }

            }

        }
    }

    /**
     * ambil data list inbound WHERE supplierId == resourceId
     * if(data == 0) throw DataNotFound
     * */
    suspend fun getListInboundBySupplier(request: GetPagingRequestWithId): GetListInboundResponse {
        val getListBySupplierId  = inboundRepository.finAllBySupplierId(
            supplierId = request.resourceId.orEmpty(),
            pageable = PageRequest.of(
                request.page.toInt(),
                50
            )
        )
        if (getListBySupplierId.isEmpty) throw DataNotFound("inbound kosong")

        return getListInboundResponse {
            totalItem = getListBySupplierId.totalElements
            totalPage = getListBySupplierId.totalPages.toLong()
            currentPage = getListBySupplierId.number.toLong()
            data += getListBySupplierId.content.map {
                inboundResponse {
                    inboundId = it.id.orEmpty()
                    totalAmount = it.totalAmount.toLong()
                    statue = it.status.toLong()
                    createdAt = it.createdAt.toString()
                    updatedAt = it.updatedAt.toString()
                    cashier = with(it) {
                        userResponse {
                            userId = cashier?.id.orEmpty()
                            userEmail = cashier?.userEmail.orEmpty()
                            userFullName = cashier?.userFullName.orEmpty()
                            authProvider = cashier?.authProvider.orEmpty()
                            createdAt = cashier?.createdAt.toString()
                            updatedAt = cashier?.updatedAt.toString()
                        }
                    }
                    supplier = with(it){
                        supplierResponse {
                            supplierId = supplier?.id.orEmpty()
                            supplierAddress = supplier?.supplierAddress.orEmpty()
                            supplierOrgName = supplier?.supplierOrgName.orEmpty()
                            supplierEmail = supplier?.supplierEmail.orEmpty()
                            supplierFullName = supplier?.supplierFullName.orEmpty()
                            supplierPhoneNumber = supplier?.supplierPhoneNumber.orEmpty()
                            createdAt = supplier?.createdAt.toString()
                            updatedAt = supplier?.updatedAt.toString()
                        }
                    }
                }

            }

        }
    }

    /**
     * ambil detail inbound WHERE inbound == resourceId
     * if(data == null) throw DataNotFound
     * */
    suspend fun getDetailInbound(request: GetById): InboundResponse {
        var getDetailInbound = detailInboundRepository.findByInbound(request.resourceId)?:
        throw  DataNotFound("detail inbound not found")

        return inboundResponse {
            inboundId = getDetailInbound.id.orEmpty()
            totalAmount = getDetailInbound.totalAmount.toLong()
            statue = getDetailInbound.status.toLong()
            createdAt = getDetailInbound.createdAt.toString()
            updatedAt = getDetailInbound.updatedAt.toString()
            cashier = with(getDetailInbound) {
                userResponse {
                    userId = cashier?.id.orEmpty()
                    userEmail = cashier?.userEmail.orEmpty()
                    userFullName = cashier?.userFullName.orEmpty()
                    authProvider = cashier?.authProvider.orEmpty()
                    createdAt = cashier?.createdAt.toString()
                    updatedAt = cashier?.updatedAt.toString()
                }
            }
            supplier = with(getDetailInbound){
                supplierResponse {
                    supplierId = supplier?.id.orEmpty()
                    supplierAddress = supplier?.supplierAddress.orEmpty()
                    supplierOrgName = supplier?.supplierOrgName.orEmpty()
                    supplierEmail = supplier?.supplierEmail.orEmpty()
                    supplierFullName = supplier?.supplierFullName.orEmpty()
                    supplierPhoneNumber = supplier?.supplierPhoneNumber.orEmpty()
                    createdAt = supplier?.createdAt.toString()
                    updatedAt = supplier?.updatedAt.toString()
                }
            }
        }
    }

    /**
     * ambil data list inbound WHERE inboundId == resourceId
     * if(data == 0) throw DataNotFound
     * */
    suspend fun getListDetailInbound(request: GetPagingRequestWithId): GetListDetailInboundResponse {
        val getListDetail = detailInboundRepository.finAllByInboundId(
            inboundId = request.resourceId,
            pageable = PageRequest.of(
                request.page.toInt(),
                50
            )
        )

        if (getListDetail.isEmpty) throw DataNotFound("data is empty")

        return getListDetailInboundResponse {
            totalItem = getListDetail.totalElements
            totalPage = getListDetail.totalPages.toLong()
            currentPage = getListDetail.number.toLong()
            data += getListDetail.content.map {
                detailInboundResponse {
                    detailInboundId = it.id.orEmpty()
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
                    inbound = with(it){
                        inboundResponse {
                            inboundId = inbound?.id.orEmpty()
                            totalAmount = inbound?.totalAmount!!.toLong()
                            statue = inbound?.status!!.toLong()
                            createdAt = inbound?.createdAt.toString()
                            updatedAt = inbound?.updatedAt.toString()
                            cashier = userResponse {
                                    userId = inbound?.cashier?.id.orEmpty()
                                    userEmail = inbound?.cashier?.userEmail.orEmpty()
                                    userFullName = inbound?.cashier?.userFullName.orEmpty()
                                    authProvider = inbound?.cashier?.authProvider.orEmpty()
                                    createdAt = inbound?.cashier?.createdAt.toString()
                                    updatedAt = inbound?.cashier?.updatedAt.toString()
                                }
                            supplier = supplierResponse {
                                    supplierId = inbound?.supplier?.id.orEmpty()
                                    supplierAddress = inbound?.supplier?.supplierAddress.orEmpty()
                                    supplierOrgName = inbound?.supplier?.supplierOrgName.orEmpty()
                                    supplierEmail = inbound?.supplier?.supplierEmail.orEmpty()
                                    supplierFullName = inbound?.supplier?.supplierFullName.orEmpty()
                                    supplierPhoneNumber = inbound?.supplier?.supplierPhoneNumber.orEmpty()
                                    createdAt = inbound?.supplier?.createdAt.toString()
                                    updatedAt = inbound?.supplier?.updatedAt.toString()
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
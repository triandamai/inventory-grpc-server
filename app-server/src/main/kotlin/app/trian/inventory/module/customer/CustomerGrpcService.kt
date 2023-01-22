package app.trian.inventory.module.customer

import app.trian.inventory.v1.customer.CustomerGrpcKt
import net.devh.boot.grpc.server.service.GrpcService

@GrpcService
class CustomerGrpcService(
    private val customerRepository: CustomerRepository
) :CustomerGrpcKt.CustomerCoroutineImplBase(){
}
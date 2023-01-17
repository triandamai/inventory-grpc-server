package app.trian.grpclearn.module.roles

import app.trian.grpclearn.module.common.fromOffsetDateTime
import app.trian.grpclearn.module.error.DataNotFound
import app.trian.model.RolesServiceGrpc
import app.trian.model.ListRolesResponse
import app.trian.model.RolesResponse
import app.trian.model.CreateRolesRequest
import app.trian.model.UpdateRolesRequest
import app.trian.model.DeleteRoleRequest
import com.google.protobuf.Empty
import io.grpc.stub.StreamObserver
import net.devh.boot.grpc.server.service.GrpcService
import org.springframework.data.repository.findByIdOrNull
import java.time.OffsetDateTime
import java.util.Date

@GrpcService
class RolesService(
    private val rolesRepository: RolesRepository
) :RolesServiceGrpc.RolesServiceImplBase(){
    override fun getListRole(request: Empty, responseObserver: StreamObserver<ListRolesResponse>) {
        val roles = rolesRepository.findAll()

        responseObserver.onNext(
                ListRolesResponse.newBuilder()
                    .addAllRoles(
                        roles.map {
                            RolesResponse.newBuilder()
                                .setName(it.name)
                                .setDescription(it.description)
                                .setId(it.id?.toLong() ?: 0)
                                .setCreatedAt(it.createdAt)
                                .setUpdatedAt(it.updatedAt)
                                .build()
                        }
                    )
                    .build()

        )
        responseObserver.onCompleted()


    }

    override fun createRole(request: CreateRolesRequest, responseObserver: StreamObserver<RolesResponse>) {

        val dateTime = OffsetDateTime.now().fromOffsetDateTime()
        val role = Roles(
            id = null,
            name = request.name,
            description = request.description,
            createdAt = dateTime,
            updatedAt = dateTime
        )
        val savedData = rolesRepository.save(role)
        responseObserver.onNext(
            RolesResponse.newBuilder()
                .setName(savedData.name)
                .setDescription(savedData.description)
                .setId(savedData.id?.toLong() ?: 0)
                .setCreatedAt(savedData.createdAt)
                .setUpdatedAt(savedData.updatedAt)
                .build()
        )
        responseObserver.onCompleted()
    }

    override fun updateRole(request: UpdateRolesRequest, responseObserver: StreamObserver<RolesResponse>) {
        val findRole = rolesRepository.findByIdOrNull(request.id.toInt()) ?:
        throw DataNotFound("Role with id ${request.id} doesn't exist!")

        val savedData = rolesRepository.save(
            findRole.copy(
                name = request.name,
                description = request.description,
                updatedAt = OffsetDateTime.now().fromOffsetDateTime()
            )
        )

        responseObserver.onNext(
            RolesResponse.newBuilder()
                .setId(savedData.id?.toLong() ?: 0)
                .setName(savedData.name)
                .setDescription(savedData.description)
                .setCreatedAt(savedData.createdAt)
                .setUpdatedAt(savedData.updatedAt)
                .build()
        )
        responseObserver.onCompleted()
    }

    override fun deleteRole(request: DeleteRoleRequest, responseObserver: StreamObserver<RolesResponse>) {
        val findRole = rolesRepository.findByIdOrNull(request.id.toInt()) ?:
        throw DataNotFound("Role with id ${request.id} doesn't exist")

        rolesRepository.deleteById(request.id.toInt())

        responseObserver.onNext(
            RolesResponse.newBuilder()
                .setName(findRole.name)
                .setDescription(findRole.description)
                .setId(findRole.id?.toLong() ?: 0)
                .setCreatedAt(findRole.createdAt)
                .setUpdatedAt(findRole.updatedAt)
                .build()
        )
        responseObserver.onCompleted()

    }
}
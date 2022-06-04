package app.trian.grpclearn.module.ingredients

import app.trian.grpclearn.module.common.fromOffsetDateTime
import app.trian.grpclearn.module.error.DataNotFound
import app.trian.model.*
import com.google.protobuf.Empty
import io.grpc.stub.StreamObserver
import net.devh.boot.grpc.server.service.GrpcService
import org.springframework.data.repository.findByIdOrNull
import java.time.OffsetDateTime
import java.util.Date

@GrpcService
class IngredientService(
    private val ingredientsRepository: IngredientsRepository
):IngredientServiceGrpc.IngredientServiceImplBase(){
    override fun getListIngredient(request: Empty, responseObserver: StreamObserver<ListIngredientResponse>) {
        val ingredients = ingredientsRepository
            .findAll()

        responseObserver.onNext(
            ListIngredientResponse.newBuilder()
                .addAllIngredient(
                    ingredients.map {
                        IngredientResponse.newBuilder()
                            .setName(it.name)
                            .setDescription(it.description)
                            .setUnit(it.unit)
                            .setId(it.id?.toLong() ?: 0)
                            .setPicture(it.picture)
                            .setCreatedAt(it.createdAt)
                            .setUpdatedAt(it.updatedAt)
                            .build()
                    }
                )
                .build()
        )
        responseObserver.onCompleted()
    }

    override fun createIngredient(
        request: CreateIngredientRequest,
        responseObserver: StreamObserver<IngredientResponse>
    ) {
        val dateTime= OffsetDateTime.now().fromOffsetDateTime()
        val ingredients = Ingredients(
            id = null,
            name = request.name,
            description = request.description,
            unit = request.unit,
            picture = request.picture,
            createdAt = dateTime,
            updatedAt = dateTime
        )
        val savedData = ingredientsRepository.save(ingredients)

        responseObserver.onNext(
            IngredientResponse.newBuilder()
                .setName(savedData.name)
                .setDescription(savedData.description)
                .setPicture(savedData.picture)
                .setUnit(savedData.unit)
                .setId(savedData.id?.toLong() ?: 0)
                .setCreatedAt(savedData.createdAt)
                .setUpdatedAt(savedData.updatedAt)
                .build()
        )
        responseObserver.onCompleted()
    }

    override fun updateIngredient(
        request: UpdateIngredientRequest,
        responseObserver: StreamObserver<IngredientResponse>
    ) {
        val findIngredient = ingredientsRepository.findByIdOrNull(request.id.toInt()) ?:
        throw DataNotFound("Ingredients with id ${request.id} doesn't exist!")

        val savedData = ingredientsRepository.save(findIngredient.copy(
            description = request.description,
            name = request.name,
            unit = request.unit,
            picture = request.picture,
            updatedAt = OffsetDateTime.now().fromOffsetDateTime()
        ))
        responseObserver.onNext(
            IngredientResponse.newBuilder()
                .setName(savedData.name)
                .setDescription(savedData.description)
                .setUnit(savedData.unit)
                .setPicture(savedData.picture)
                .setId(savedData.id?.toLong() ?: 0)
                .setCreatedAt(savedData.createdAt)
                .setUpdatedAt(savedData.updatedAt)
                .build()
        )
        responseObserver.onCompleted()
    }

    override fun deleteIngredient(
        request: DeleteIngredientRequest,
        responseObserver: StreamObserver<IngredientResponse>
    ) {
        val findIngredient = ingredientsRepository.findByIdOrNull(request.id.toInt()) ?:
        throw DataNotFound("Ingredient with id ${request.id} doesn't exist")

        ingredientsRepository.deleteById(request.id.toInt())

        responseObserver.onNext(
            IngredientResponse.newBuilder()
                .setName(findIngredient.name)
                .setDescription(findIngredient.description)
                .setPicture(findIngredient.picture)
                .setUnit(findIngredient.unit)
                .setId(findIngredient.id?.toLong() ?: 0)
                .setCreatedAt(findIngredient.createdAt)
                .setUpdatedAt(findIngredient.updatedAt)
                .build()
        )
        responseObserver.onCompleted()
    }
}
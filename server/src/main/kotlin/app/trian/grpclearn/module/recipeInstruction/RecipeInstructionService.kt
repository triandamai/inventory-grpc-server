package app.trian.grpclearn.module.recipeInstruction

import app.trian.grpclearn.module.error.DataNotFound
import app.trian.grpclearn.module.recipe.RecipeRepository
import app.trian.grpclearn.module.recipeInstruction.RecipeInstruction
import app.trian.model.*
import io.grpc.stub.StreamObserver
import net.devh.boot.grpc.server.service.GrpcService
import org.springframework.data.repository.findByIdOrNull

@GrpcService
class RecipeInstructionService(
    private val recipeInstructionRepository: RecipeInstructionRepository,
    private val recipeRepository: RecipeRepository
) : RecipeInstructionServiceGrpc.RecipeInstructionServiceImplBase(){
    override fun getListInstructionByRecipe(
        request: RecipeInstructionByRecipeRequest,
        responseObserver: StreamObserver<ListRecipeInstructionResponse>
    ) {
        val findRecipe = recipeRepository.findByIdOrNull(request.id.toInt()) ?:
        throw DataNotFound("Cannot find recipe!")

        val finds = recipeInstructionRepository.findByRecipe(findRecipe)

        responseObserver.onNext(
            ListRecipeInstructionResponse.newBuilder()
                .addAllInstructions(
                    finds.map {
                        RecipeInstructionResponse.newBuilder()
                            .setId(it.id?.toLong() ?: 0)
                            .setDescription(it.description)
                            .setImage(it.image)
                            .build()
                    }
                )
                .build()
        )
        responseObserver.onCompleted()
    }

    override fun createInstruction(
        request: CreateRecipeInstructionRequest,
        responseObserver: StreamObserver<RecipeInstructionResponse>
    ) {
        val recipe = recipeRepository.findByIdOrNull(request.idRecipe.toInt()) ?:
        throw DataNotFound("Cannot find recipe!")

        val instruction = RecipeInstruction(
            id = null,
            description = request.description,
            image = request.image,
            recipe = recipe
        )
        val saved=recipeInstructionRepository.save(instruction)

        responseObserver.onNext(
            RecipeInstructionResponse.newBuilder()
                .setImage(saved.image)
                .setDescription(saved.description)
                .setId(saved.id?.toLong() ?: 0)
                .build()
        )
        responseObserver.onCompleted()
    }

    override fun updateInstruction(
        request: UpdateRecipeInstructionRequest,
        responseObserver: StreamObserver<RecipeInstructionResponse>
    ) {
       val findInstruction = recipeInstructionRepository.findByIdOrNull(request.id.toInt())?:
       throw DataNotFound("Cannot find Instruction")

       val saved = recipeInstructionRepository
           .save(
               findInstruction.copy(
                   description = request.description,
                   image = request.image,
               )
           )
        responseObserver.onNext(
            RecipeInstructionResponse.newBuilder()
                .setId(saved.id?.toLong() ?: 0)
                .setDescription(saved.description)
                .setImage(saved.image)
                .build()
        )
        responseObserver.onCompleted()
    }

    override fun deleteInstruction(
        request: DeleteRecipeInstructionRequest,
        responseObserver: StreamObserver<RecipeInstructionResponse>
    ) {
        val find = recipeInstructionRepository.findByIdOrNull(request.id.toInt()) ?:
        throw DataNotFound("Cannot find Instruction!")

        recipeInstructionRepository.deleteById(request.id.toInt())

        responseObserver.onNext(
            RecipeInstructionResponse.newBuilder()
                .setId(find.id?.toLong() ?: 0)
                .setDescription(find.description)
                .setImage(find.image)
                .build()
        )
        responseObserver.onCompleted()
    }
}
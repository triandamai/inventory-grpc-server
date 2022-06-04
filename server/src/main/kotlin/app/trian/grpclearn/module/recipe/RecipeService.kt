package app.trian.grpclearn.module.recipe

import app.trian.grpclearn.module.error.DataNotFound
import app.trian.grpclearn.module.user.UserRepository
import app.trian.model.*
import com.google.protobuf.Empty
import io.grpc.stub.StreamObserver
import net.devh.boot.grpc.server.service.GrpcService
import org.springframework.data.repository.findByIdOrNull

@GrpcService
class RecipeService(
    private val recipeRepository: RecipeRepository,
    private val userRepository: UserRepository
) : RecipeServiceGrpc.RecipeServiceImplBase() {
    override fun getListRecipe(request: Empty, responseObserver: StreamObserver<ListRecipe>) {
        val recipe = recipeRepository.findAll()

        responseObserver.onNext(
            ListRecipe.newBuilder()
                .addAllRecipe(
                    recipe.map {
                        RecipeResponse.newBuilder()
                            .setCover(it.cover)
                            .setDuration(it.duration)
                            .setTitle(it.title)
                            .setDescription(it.description)
                            .setStatus(it.status)
                            .build()
                    }
                )
                .build()
        )

    }

    override fun getListRecipeByUser(
        request: RecipeByUserRequest,
        responseObserver: StreamObserver<ListRecipe>
    ) {
        val user = userRepository.findByIdOrNull(request.userId.toInt()) ?: throw DataNotFound("Cannot find user!")

        val recipe = recipeRepository.findByUser(user)

        responseObserver.onNext(
            ListRecipe.newBuilder()
                .addAllRecipe(
                    recipe.map {
                        RecipeResponse.newBuilder()
                            .setStatus(it.status)
                            .setDescription(it.description)
                            .setTitle(it.title)
                            .setDuration(it.duration)
                            .setCover(it.cover)
                            .setId(it.id?.toLong() ?: 0)
                            .build()
                    }
                )
                .build()
        )
        responseObserver.onCompleted()
    }

    override fun createRecipe(
        request: CreateRecipeRequest,
        responseObserver: StreamObserver<RecipeResponse>
    ) {
        val findUser = userRepository.findByIdOrNull(request.userId.toInt()) ?: throw DataNotFound("Cannot find user!")

        val recipe = Recipe(
            id = null,
            title = request.title,
            description = request.description,
            cover = request.cover,
            duration = request.duration,
            status = request.status,
            user = findUser
        )
        val savedRecipe = recipeRepository.save(recipe)
        responseObserver.onNext(
            RecipeResponse.newBuilder()
                .setCover(savedRecipe.cover)
                .setDuration(savedRecipe.duration)
                .setDescription(savedRecipe.description)
                .setStatus(savedRecipe.status)
                .setTitle(savedRecipe.title)
                .build()
        )
        responseObserver.onCompleted()
    }

    override fun updateRecipe(
        request: UpdateRecipeRequest,
        responseObserver: StreamObserver<RecipeResponse>
    ) {
        val find = recipeRepository.findByIdOrNull(request.id.toInt()) ?:
        throw DataNotFound("Cannot find recipe!")

        val savedData = recipeRepository.save(find.copy(
            status = request.status,
            description = request.description,
            cover = request.cover,
            title = request.title

        ))
        responseObserver.onNext(
            RecipeResponse.newBuilder()
                .setTitle(savedData.title)
                .setStatus(savedData.status)
                .setDescription(savedData.description)
                .setDuration(savedData.duration)
                .setCover(savedData.cover)
                .setId(savedData.id?.toLong()?:0)
                .build()
        )
        responseObserver.onCompleted()
    }

    override fun deleteRecipe(
        request: DeleteRecipeRequest,
        responseObserver: StreamObserver<RecipeResponse>
    ) {
        val find = recipeRepository.findByIdOrNull(request.id.toInt()) ?:
        throw DataNotFound("Cannot find recipe!")
        recipeRepository.deleteById(request.id.toInt())
        responseObserver.onNext(
            RecipeResponse.newBuilder()
                .setTitle(find.title)
                .setStatus(find.status)
                .setDescription(find.description)
                .setDuration(find.duration)
                .setCover(find.cover)
                .setId(find.id?.toLong()?:0)
                .build()
        )
        responseObserver.onCompleted()
    }
}
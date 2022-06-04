package app.trian.grpclearn.module.recipe_ingredients

import app.trian.grpclearn.module.error.DataNotFound
import app.trian.grpclearn.module.ingredients.IngredientsRepository
import app.trian.grpclearn.module.recipe.RecipeRepository
import app.trian.model.*
import io.grpc.stub.StreamObserver
import net.devh.boot.grpc.server.service.GrpcService
import org.springframework.data.repository.findByIdOrNull

@GrpcService
class RecipeIngredientService(
    private val recipeIngredientsRepository: RecipeIngredientsRepository,
    private val recipeRepository: RecipeRepository,
    private val ingredientsRepository: IngredientsRepository
) : RecipeIngredientServiceGrpc.RecipeIngredientServiceImplBase() {
    override fun getListRecipeIngredientByRecipe(
        request: RecipeIngredientByRecipe,
        responseObserver: StreamObserver<ListRecipeIngredientResponse>
    ) {
        val findRecipe = recipeRepository.findByIdOrNull(request.idRecipe.toInt())?:
        throw DataNotFound("Cannot find recipe!")

        val finds = recipeIngredientsRepository.findByRecipe(findRecipe)

        responseObserver.onNext(
            ListRecipeIngredientResponse.newBuilder()
                .addAllIngredient(
                    finds.map {
                        RecipeIngredientResponse.newBuilder()
                            .setIngredient(
                                IngredientResponse.newBuilder()
                                    .setId(it.ingredients?.id?.toLong() ?: 0)
                                    .setUnit(it.ingredients?.unit)
                                    .setName(it.ingredients?.name)
                                    .setPicture(it.ingredients?.picture)
                                    .setDescription(it.ingredients?.description)
                                    .build()
                            )
                            .setId(it.id?.toLong() ?: 0)
                            .setQuantity(it.quantity)
                            .build()
                    }
                )
                .build()
        )
        responseObserver.onCompleted()
    }

    override fun createRecipeIngredient(
        request: CreateRecipeIngredientRequest,
        responseObserver: StreamObserver<RecipeIngredientResponse>
    ) {
        val findRecipe = recipeRepository.findByIdOrNull(request.idRecipe.toInt()) ?:
        throw DataNotFound("Cannot find recipe!")

        val findIngredient = ingredientsRepository.findByIdOrNull(request.idIngredient.toInt()) ?:
        throw DataNotFound("Cannot find Ingredient!")

        val ingredient = RecipeIngredients(
            id = null,
            quantity = request.quantity,
            recipe = findRecipe,
            ingredients = findIngredient
        )

        val savedData = recipeIngredientsRepository.save(ingredient)
        responseObserver.onNext(
            RecipeIngredientResponse.newBuilder()
                .setIngredient(
                    IngredientResponse.newBuilder()
                        .setId(savedData.ingredients?.id?.toLong() ?: 0)
                        .setUnit(savedData.ingredients?.unit)
                        .setName(savedData.ingredients?.name)
                        .setPicture(savedData.ingredients?.picture)
                        .setDescription(savedData.ingredients?.description)
                        .build()
                )
                .setId(savedData.id?.toLong() ?: 0)
                .setQuantity(savedData.quantity)
                .build()
        )
        responseObserver.onCompleted()

    }

    override fun updateRecipeIngredient(
        request: UpdateRecipeIngredientRequest,
        responseObserver: StreamObserver<RecipeIngredientResponse>
    ) {
        val find = recipeIngredientsRepository.findByIdOrNull(request.id.toInt())
            ?: throw DataNotFound("Cannot find ingredient!")



        val savedData = recipeIngredientsRepository.save(find.copy(
            quantity = request.quantity,

        ))
        responseObserver.onNext(
            RecipeIngredientResponse.newBuilder()
                .setIngredient(
                    IngredientResponse.newBuilder()
                        .setId(savedData.ingredients?.id?.toLong() ?: 0)
                        .setUnit(savedData.ingredients?.unit)
                        .setName(savedData.ingredients?.name)
                        .setPicture(savedData.ingredients?.picture)
                        .setDescription(savedData.ingredients?.description)
                        .build()
                )
                .setId(savedData.id?.toLong() ?: 0)
                .setQuantity(savedData.quantity)
                .build()
        )
        responseObserver.onCompleted()
    }

    override fun deleteRecipeIngredient(
        request: DeleteRecipeIngredientRequest,
        responseObserver: StreamObserver<RecipeIngredientResponse>
    ) {
        val find = recipeIngredientsRepository.findByIdOrNull(request.id.toInt())
            ?: throw DataNotFound("Cannot find ingredient!")

        recipeIngredientsRepository.deleteById(request.id.toInt())
        responseObserver.onNext(
            RecipeIngredientResponse.newBuilder()
                .setIngredient(
                    IngredientResponse.newBuilder()
                        .setId(find.ingredients?.id?.toLong() ?: 0)
                        .setUnit(find.ingredients?.unit)
                        .setName(find.ingredients?.name)
                        .setPicture(find.ingredients?.picture)
                        .setDescription(find.ingredients?.description)
                        .build()
                )
                .setId(find.id?.toLong() ?: 0)
                .setQuantity(find.quantity)
                .build()
        )
        responseObserver.onCompleted()

    }
}
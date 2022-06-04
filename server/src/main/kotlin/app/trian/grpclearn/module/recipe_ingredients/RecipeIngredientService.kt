package app.trian.grpclearn.module.recipe_ingredients

import app.trian.model.RecipeIngredientServiceGrpc
import net.devh.boot.grpc.server.service.GrpcService

@GrpcService
class RecipeIngredientService(
 private val recipeIngredientsRepository: RecipeIngredientsRepository
) :RecipeIngredientServiceGrpc.RecipeIngredientServiceImplBase(){
}
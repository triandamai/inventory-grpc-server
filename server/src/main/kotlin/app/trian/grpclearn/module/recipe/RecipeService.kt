package app.trian.grpclearn.module.recipe

import app.trian.model.RecipeServiceGrpc
import net.devh.boot.grpc.server.service.GrpcService

@GrpcService
class RecipeService(
    private val recipeRepository: RecipeRepository
) :RecipeServiceGrpc.RecipeServiceImplBase(){
}
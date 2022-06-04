package app.trian.grpclearn.module.recipeIntruction

import app.trian.model.RecipeInstructionServiceGrpc
import net.devh.boot.grpc.server.service.GrpcService

@GrpcService
class RecipeInstructionService(
    private val recipeInstructionRepository: RecipeInstructionRepository
) :RecipeInstructionServiceGrpc.RecipeInstructionServiceImplBase(){
}
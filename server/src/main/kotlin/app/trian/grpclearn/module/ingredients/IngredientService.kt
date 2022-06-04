package app.trian.grpclearn.module.ingredients

import app.trian.model.IngredientServiceGrpc
import net.devh.boot.grpc.server.service.GrpcService

@GrpcService
class IngredientService(
    private val ingredientsRepository: IngredientsRepository
):IngredientServiceGrpc.IngredientServiceImplBase(){

}
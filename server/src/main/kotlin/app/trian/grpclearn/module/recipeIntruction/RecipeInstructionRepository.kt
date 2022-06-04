package app.trian.grpclearn.module.recipeIntruction;

import org.springframework.data.jpa.repository.JpaRepository

interface RecipeInstructionRepository : JpaRepository<RecipeInstruction, Int> {
}
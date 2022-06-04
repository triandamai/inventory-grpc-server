package app.trian.grpclearn.module.recipeInstruction

import app.trian.grpclearn.module.recipe.Recipe
import javax.persistence.*

@Entity
data class RecipeInstruction(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id:Int?,
    var description:String,
    var image:String,
    @ManyToOne(
        fetch = FetchType.LAZY,
        cascade = [CascadeType.REMOVE]
    )
    var recipe: Recipe?,
    var createdAt:String,
    var updatedAt:String
)
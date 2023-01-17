package app.trian.grpclearn.module.recipe_ingredients

import app.trian.grpclearn.module.ingredients.Ingredients
import app.trian.grpclearn.module.recipe.Recipe
import org.hibernate.Hibernate
import javax.persistence.*

@Entity
data class RecipeIngredients(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id:Int?,
    var quantity:Long,
    @ManyToOne(
        fetch = FetchType.LAZY,
        cascade = [CascadeType.REMOVE]
    )
    var ingredients: Ingredients?,
    @ManyToOne(
        fetch = FetchType.LAZY,
        cascade = [CascadeType.REMOVE]
    )
    var recipe:Recipe?,
    var createdAt:String,
    var updatedAt:String
)
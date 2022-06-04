package app.trian.grpclearn.module.user

import app.trian.grpclearn.module.recipe.Recipe
import app.trian.grpclearn.module.roles.Roles
import org.springframework.context.annotation.Primary
import javax.persistence.*

@Entity
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id:Int?,
    var name:String,
    var username:String,
    @Column(
        unique = true
    )
    var email:String,
    var password:String,
    var auth_provider:String,
    @ManyToMany(
        mappedBy = "user",
        fetch = FetchType.LAZY,
        cascade = [CascadeType.REMOVE]
    )
    var roles:List<Roles> = listOf(),
    @OneToMany(
        mappedBy = "user"
    )
    var recipes:List<Recipe> = listOf(),
    var createdAt:String,
    var updatedAt:String
)

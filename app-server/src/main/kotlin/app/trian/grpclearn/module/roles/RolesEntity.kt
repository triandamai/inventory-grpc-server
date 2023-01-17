package app.trian.grpclearn.module.roles

import app.trian.grpclearn.module.user.User
import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

@Entity
data class Roles(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id:Int?,
    var name:String,
    var description:String,
    @JsonIgnore
    @ManyToMany(
        fetch = FetchType.LAZY,
        cascade = [CascadeType.REMOVE],
    )
    var user:List<User> = listOf(),
    var createdAt:String,
    var updatedAt:String

)
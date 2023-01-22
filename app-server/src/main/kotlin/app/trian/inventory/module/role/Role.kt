package app.trian.inventory.module.role


import app.trian.inventory.module.user.User
import com.fasterxml.jackson.annotation.JsonIgnore
import java.util.*
import javax.persistence.*

@Entity(
    name = "role_user"
)
data class Role(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id:Int?,
    var roleName:String,
    var roleDescription:String,
    @Temporal(TemporalType.TIMESTAMP)
    var createdAt: Date,
    @Temporal(TemporalType.TIMESTAMP)
    var updatedAt: Date
)
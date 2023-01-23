package app.trian.inventory.module.user

import app.trian.inventory.module.role.Role
import org.hibernate.annotations.GenericGenerator
import java.util.Date
import javax.persistence.*

@Entity
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "UUID")
    @GenericGenerator(
        name = "UUID",
        strategy = "org.hibernate.id.UUIDGenerator"
    )
    var id:String? = null,
    var userFullName:String,
    var userEmail:String,
    var userPassword:String,
    var authProvider:String,
    @ManyToMany(
        fetch = FetchType.LAZY,
        cascade = [CascadeType.REFRESH],
    )
    var roles:List<Role> = listOf(),
    @Temporal(TemporalType.TIMESTAMP)
    var createdAt:Date,
    @Temporal(TemporalType.TIMESTAMP)
    var updatedAt:Date
)
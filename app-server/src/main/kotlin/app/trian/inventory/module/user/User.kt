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
    var userFullName:String?=null,
    var userEmail:String?=null,
    var userPassword:String?=null,
    var authProvider:String?=null,
    @ManyToMany(
        fetch = FetchType.LAZY,
        cascade = [CascadeType.REFRESH],
    )
    var roles:List<Role> = listOf(),
    @Temporal(TemporalType.TIMESTAMP)
    var createdAt:Date?=null,
    @Temporal(TemporalType.TIMESTAMP)
    var updatedAt:Date?=null
)
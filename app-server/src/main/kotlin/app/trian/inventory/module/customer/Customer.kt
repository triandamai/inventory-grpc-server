package app.trian.inventory.module.customer

import org.hibernate.annotations.GenericGenerator
import java.util.*
import javax.persistence.*

@Entity
data class Customer(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "UUID")
    @GenericGenerator(
        name = "UUID",
        strategy = "org.hibernate.id.UUIDGenerator"
    )
    var id:String? = null,
    var customerAddress:String?=null,
    var customerFullName:String?=null,
    var customerEmail:String?=null,
    var customerPhoneNumber: String?=null,
    @Temporal(TemporalType.TIMESTAMP)
    var createdAt: Date?=null,
    @Temporal(TemporalType.TIMESTAMP)
    var updatedAt: Date?=null
)
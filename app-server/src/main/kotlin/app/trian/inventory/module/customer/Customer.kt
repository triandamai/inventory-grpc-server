package app.trian.inventory.module.customer

import java.util.*
import javax.persistence.*

@Entity
data class Customer(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id:Int?,
    var customerFullName:String,
    var customerEmail:String,
    var customerPhoneNumber: String,
    @Temporal(TemporalType.TIMESTAMP)
    var createdAt: Date,
    @Temporal(TemporalType.TIMESTAMP)
    var updatedAt: Date
)
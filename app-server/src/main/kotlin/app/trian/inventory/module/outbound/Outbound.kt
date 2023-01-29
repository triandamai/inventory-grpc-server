package app.trian.inventory.module.outbound

import app.trian.inventory.module.customer.Customer
import app.trian.inventory.module.supplier.Supplier
import app.trian.inventory.module.user.User
import org.hibernate.annotations.GenericGenerator
import java.util.*
import javax.persistence.*

@Entity
data class Outbound(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "UUID")
    @GenericGenerator(
        name = "UUID",
        strategy = "org.hibernate.id.UUIDGenerator"
    )
    var id:String? = null,
    var status:String? = null,
    var totalAmount:Int? = null,
    @ManyToOne(
        fetch = FetchType.LAZY,
        cascade = [CascadeType.REFRESH]
    )
    var cashier: User?=null,
    @ManyToOne(
        fetch = FetchType.LAZY,
        cascade = [CascadeType.REFRESH]
    )
    var customer: Customer?=null,
    @Temporal(TemporalType.TIMESTAMP)
    var createdAt: Date? = null,
    @Temporal(TemporalType.TIMESTAMP)
    var updatedAt: Date? = null
)
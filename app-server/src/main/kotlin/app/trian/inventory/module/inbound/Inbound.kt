package app.trian.inventory.module.inbound

import app.trian.inventory.module.supplier.Supplier
import app.trian.inventory.module.user.User
import java.util.*
import javax.persistence.*

@Entity
data class Inbound(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id:Int?,
    var status:String,
    var totalAmount:Int,
    @ManyToOne(
        fetch = FetchType.LAZY,
        cascade = [CascadeType.REFRESH]
    )
    var cashier: User?=null,
    @ManyToOne(
        fetch = FetchType.LAZY,
        cascade = [CascadeType.REFRESH]
    )
    var supplier: Supplier?=null,
    @Temporal(TemporalType.TIMESTAMP)
    var createdAt: Date,
    @Temporal(TemporalType.TIMESTAMP)
    var updatedAt: Date
)
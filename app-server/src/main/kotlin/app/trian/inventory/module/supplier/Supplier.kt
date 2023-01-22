package app.trian.inventory.module.supplier

import java.util.*
import javax.persistence.*

@Entity
data class Supplier(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id:Int?,
    var supplierFullName:String,
    var supplierEmail:String,
    var supplierPhoneNumber: String,
    var supplierOrgName:String,
    var supplierAddress:String,
    @Temporal(TemporalType.TIMESTAMP)
    var createdAt: Date,
    @Temporal(TemporalType.TIMESTAMP)
    var updatedAt: Date
)
package app.trian.inventory.module.supplier

import org.hibernate.annotations.GenericGenerator
import java.util.*
import javax.persistence.*

@Entity
data class Supplier(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "UUID")
    @GenericGenerator(
        name = "UUID",
        strategy = "org.hibernate.id.UUIDGenerator"
    )
    var id:String? = null,
    var supplierFullName:String?=null,
    var supplierEmail:String?=null,
    var supplierPhoneNumber: String?=null,
    var supplierOrgName:String?=null,
    var supplierAddress:String?=null,
    @Temporal(TemporalType.TIMESTAMP)
    var createdAt: Date?=null,
    @Temporal(TemporalType.TIMESTAMP)
    var updatedAt: Date?=null
)
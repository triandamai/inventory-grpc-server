package app.trian.inventory.module.product

import org.springframework.data.repository.PagingAndSortingRepository

interface ProductRepository:PagingAndSortingRepository<Product,Int> {
}
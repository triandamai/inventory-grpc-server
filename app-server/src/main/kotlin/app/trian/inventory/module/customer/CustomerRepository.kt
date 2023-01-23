package app.trian.inventory.module.customer

import org.springframework.data.repository.PagingAndSortingRepository

interface CustomerRepository:PagingAndSortingRepository<Customer,String> {
}
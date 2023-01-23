package app.trian.inventory.module.category

import org.springframework.data.repository.PagingAndSortingRepository

interface CategoryRepository:PagingAndSortingRepository<Category,String> {
}
package app.trian.inventory.module.product

import app.trian.inventory.module.error.DataNotFound
import app.trian.inventory.v1.GetPagingRequest
import app.trian.inventory.v1.category.CategoryResponse
import app.trian.inventory.v1.category.UpdateCategoryRequest
import app.trian.inventory.v1.category.categoryResponse
import app.trian.inventory.v1.product.CreateProductRequest
import app.trian.inventory.v1.product.DeleteProductRequest
import app.trian.inventory.v1.product.GetListProductResponse
import app.trian.inventory.v1.product.ProductResponse
import app.trian.inventory.v1.product.UpdateProductRequest
import app.trian.inventory.v1.product.getListProductResponse
import app.trian.inventory.v1.product.productResponse
import org.springframework.data.domain.PageRequest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.util.Date
@Service
class ProductService(
    private val productRepository: ProductRepository
) {
    suspend fun getListProduct(request :GetPagingRequest) :GetListProductResponse {
       val findProduct = productRepository.findAll(
           PageRequest.of(
               request.page.toInt(),
               50
           )
       )

        return  getListProductResponse {
            totalItem = findProduct.totalElements
            totalPage = findProduct.totalPages.toLong()
            data += findProduct.content.map {
                productResponse {
                    productId = it.id.orEmpty()
                    productName = it.productName.orEmpty()
                    productDescription = it.productDescription.orEmpty()
                    productImage = it.productImage.orEmpty()
                    productStock = it.productStock.toLong()
                    productOutboundPrice = it.productOutboundPrice.toLong()
                    productUnit = it.productUnit.orEmpty()
                    category = categoryResponse {
                            it.category.map {
                                categoryId = it.id.orEmpty()
                                categoryName = it.categoryName.orEmpty()
                                categoryDescription = it.categoryDescription.orEmpty()
                                createdAt = it.createdAt.toString()
                                updatedAt = it.updatedAt.toString()
                            }
                        }

                    createdAt = it.createdAt.toString()
                    updatedAt = it.updatedAt.toString()
                }
            }
            currentPage = findProduct.number.toLong()
        }
    }
    suspend fun createNewCategory(request:CreateProductRequest):ProductResponse {
        val date = Date()
        val product = Product()
        val saveProduct = productRepository.save(
            product.copy(
                id = null,
                productName = request.productName,
                productDescription = request.productDescription,
                productOutboundPrice = 12,
                productInboundPrice = 11,
                productStock = 10,
                productUnit = "unit",
                createdAt = date,
                updatedAt = date

            )
        )
        return productResponse {
            productId = saveProduct.id.orEmpty()
            productName = saveProduct.productName.orEmpty()
            productDescription = saveProduct.productDescription.orEmpty()
            productImage = saveProduct.productImage.orEmpty()
            productOutboundPrice = saveProduct.productOutboundPrice.toLong()
            productStock = saveProduct.productStock.toLong()
            productUnit = saveProduct.productUnit.orEmpty()
            createdAt = saveProduct.createdAt.toString()
            updatedAt = saveProduct.updatedAt.toString()
        }
    }
suspend fun updateProduct(request: UpdateProductRequest): ProductResponse {
    val findPoduct = productRepository.findByIdOrNull(request.productId.toString())?:
    throw DataNotFound("cannot find product ${request.productId}")

    val updateProduct = with(request){
        findPoduct.copy(
            productName = findPoduct.productName,
            productDescription = findPoduct.productDescription,
            productInboundPrice = findPoduct.productInboundPrice,
            productOutboundPrice = findPoduct.productOutboundPrice,
            productUnit = findPoduct.productUnit,
            productImage = findPoduct.productImage,
            productStock = findPoduct.productStock,
            updatedAt = Date()

        )
    }

    val saveUpdateProduct = productRepository.save(updateProduct)

    return productResponse {
        productName = saveUpdateProduct.productName.orEmpty()
        productDescription = saveUpdateProduct.productDescription.orEmpty()
        productOutboundPrice = saveUpdateProduct.productOutboundPrice.toLong()
        productUnit = saveUpdateProduct.productUnit.orEmpty()
        productImage = saveUpdateProduct.productImage.orEmpty()
        productStock = saveUpdateProduct.productStock.toLong()
        createdAt = saveUpdateProduct.createdAt.toString()
        updatedAt = saveUpdateProduct.updatedAt.toString()
    }
}

suspend fun deleteProduct(request: DeleteProductRequest):ProductResponse{
    val findProduct = productRepository.findByIdOrNull(request.productId)?:
    throw DataNotFound("product with ${request.productId} not found")

    findProduct.id.let { productRepository.deleteById(it.orEmpty()) }

    return productResponse {
        productName = findProduct.productName.orEmpty()
        productDescription = findProduct.productDescription.orEmpty()
        productOutboundPrice = findProduct.productOutboundPrice.toLong()
        productUnit = findProduct.productUnit.orEmpty()
        productImage = findProduct.productImage.orEmpty()
        productStock = findProduct.productStock.toLong()
        createdAt = findProduct.createdAt.toString()
        updatedAt = findProduct.updatedAt.toString()
    }
}

}
package app.trian.inventory.module.product

import app.trian.inventory.v1.GetPagingRequest
import app.trian.inventory.v1.product.CreateProductRequest
import app.trian.inventory.v1.product.DeleteProductRequest
import app.trian.inventory.v1.product.GetListProductResponse
import app.trian.inventory.v1.product.ProductResponse
import app.trian.inventory.v1.product.UpdateProductRequest
import app.trian.inventory.v1.product.getListProductResponse
import app.trian.inventory.v1.product.productResponse
import org.springframework.stereotype.Service

@Service
class ProductService(
    private val productRepository: ProductRepository
) {
     suspend fun getListProduct(request: GetPagingRequest): GetListProductResponse {
        return getListProductResponse {  }
    }

     suspend fun createNewProduct(request: CreateProductRequest): ProductResponse {
        return productResponse {  }
    }

     suspend fun updateProduct(request: UpdateProductRequest): ProductResponse {
        return productResponse {  }
    }
     suspend fun deleteProduct(request: DeleteProductRequest): ProductResponse {
        return productResponse {  }
    }
}
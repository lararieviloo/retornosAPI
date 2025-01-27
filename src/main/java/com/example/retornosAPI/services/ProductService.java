package com.example.retornosAPI.services;

import com.example.retornosAPI.models.Product;
import com.example.retornosAPI.models.ProductEntity;
import com.example.retornosAPI.repositories.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public Product createProduct(@Valid Product product) {
        ProductEntity entity = new ProductEntity(null, product.name(), product.price(), product.stockQuantity(), product.category());
        ProductEntity savedEntity = repository.save(entity);
        return new Product(savedEntity.getId(), savedEntity.getName(), savedEntity.getPrice(), savedEntity.getStockQuantity(), savedEntity.getCategory());
    }

    public Product getProductById(Long id) {
        ProductEntity entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        return new Product(entity.getId(), entity.getName(), entity.getPrice(), entity.getStockQuantity(), entity.getCategory());
    }

    public List<Product> getAllProducts() {
        return repository.findAll().stream()
                .map(entity -> new Product(entity.getId(), entity.getName(), entity.getPrice(), entity.getStockQuantity(), entity.getCategory()))
                .collect(Collectors.toList());
    }

    public void deleteProduct(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Product does not exist.");
        }
        repository.deleteById(id);
    }

    // Atualizar um produto existente
    public Product updateProduct(@Valid Long id, Product updatedProduct) {
        // Verificar se o produto existe
        ProductEntity existingEntity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product with ID " + id + " not found"));

        // Atualizar os dados do produto
        existingEntity.setName(updatedProduct.name());
        existingEntity.setPrice(updatedProduct.price());
        existingEntity.setStockQuantity(updatedProduct.stockQuantity());
        existingEntity.setCategory(updatedProduct.category());

        // Salvar as alterações no banco de dados
        ProductEntity savedEntity = repository.save(existingEntity);

        // Retornar o produto atualizado
        return new Product(savedEntity.getId(), savedEntity.getName(), savedEntity.getPrice(), savedEntity.getStockQuantity(), savedEntity.getCategory());
    }

    // Buscar produtos pelo nome
    public List<Product> getProductsByName(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("O nome do produto não pode ser vazio.");
        }

        List<ProductEntity> entities = repository.findByNameContainingIgnoreCase(name);
        if (entities.isEmpty()) {
            System.out.println("Nenhum produto encontrado com o nome: " + name);
        } else {
            System.out.println("Produtos encontrados com o nome '" + name + "': " + entities.size());
        }
        return entities.stream()
                .map(entity -> new Product(entity.getId(), entity.getName(), entity.getPrice(), entity.getStockQuantity(), entity.getCategory()))
                .collect(Collectors.toList());
    }
}
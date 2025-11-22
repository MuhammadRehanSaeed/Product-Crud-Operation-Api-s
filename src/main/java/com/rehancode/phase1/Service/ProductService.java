package com.rehancode.phase1.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.rehancode.phase1.DTO.ProductDTO;
import com.rehancode.phase1.Entity.Products;
import com.rehancode.phase1.Exceptions.ProductNotFoundException;
import com.rehancode.phase1.Repository.ProductsRepository;

@Service
public class ProductService {

    private final ProductsRepository repo;

    public ProductService(ProductsRepository repo) {
        this.repo = repo;
    }

    // Convert single entity → DTO
    private ProductDTO convertToDTO(Products product) {
        ProductDTO dto = new ProductDTO();
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setCode(product.getCode());
        return dto;
    }

    // Convert DTO → entity
    private Products convertToEntity(ProductDTO dto) {
        Products product = new Products();
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setCode(dto.getCode());
        return product;
    }

    // Create product
    public ProductDTO createProduct(ProductDTO dto) {
        Products saved = repo.save(convertToEntity(dto));
        return convertToDTO(saved);
    }

    // Get all
    // public List<ProductDTO> getProducts() {
    //     return repo.findAll()
    //                .stream()
    //                .map(this::convertToDTO)
    //                .toList();
    // }
    public Page<ProductDTO> getProducts(int page, int size) {
    Pageable pageable = PageRequest.of(page, size); // page = 0-based index
    Page<ProductDTO> productPage = repo.findAll(pageable)
                                       .map(this::convertToDTO);
    return productPage;
}

    // Get by ID
    public ProductDTO getProductById(int id) {
        Products product = repo.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product with ID " + id + " not found"));

        return convertToDTO(product);
    }

    // Update
    public ProductDTO updateProduct(int id, ProductDTO dto) {
        Products existing = repo.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));

        existing.setName(dto.getName());
        existing.setDescription(dto.getDescription());
        existing.setCode(dto.getCode()); 

        Products updated = repo.save(existing);
        return convertToDTO(updated);
    }

    // Delete
    public void deleteProduct(int id) {
        Products existing = repo.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product with ID " + id + " not found"));

        repo.delete(existing);
    }
}

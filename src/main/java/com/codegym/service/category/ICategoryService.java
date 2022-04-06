package com.codegym.service.category;

import com.codegym.model.Category;
import com.codegym.service.IGeneralService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ICategoryService extends IGeneralService<Category> {
    Page<Category> findAll(Pageable pageable);

    Optional<Category> findById(Long id);

    Category save(Category t);

    void removeById(Long id);

    Page<Category> findCategoryByNameContaining(String name, Pageable pageable);

}

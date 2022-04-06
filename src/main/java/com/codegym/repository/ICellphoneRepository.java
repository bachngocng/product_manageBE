package com.codegym.repository;

import com.codegym.model.Cellphone;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICellphoneRepository extends PagingAndSortingRepository<Cellphone,Long> {
    Page<Cellphone> findCellphoneByNameContaining(String name, Pageable pageable);
}

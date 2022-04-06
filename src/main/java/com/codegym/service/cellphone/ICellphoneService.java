package com.codegym.service.cellphone;

import com.codegym.model.Cellphone;
import com.codegym.service.IGeneralService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ICellphoneService extends IGeneralService<Cellphone> {
    Page<Cellphone> findAll(Pageable pageable);

    Optional<Cellphone> findById(Long id);

    Cellphone save(Cellphone cellphone);

    void removeById(Long id);

    Page<Cellphone> findCellphoneByNameContaining(String name, Pageable pageable);
}

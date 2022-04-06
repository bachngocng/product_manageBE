package com.codegym.service.cellphone;

import com.codegym.model.Cellphone;
import com.codegym.repository.ICellphoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CellphoneService implements ICellphoneService{
    @Autowired
    private ICellphoneRepository cellphoneRepository;

    @Override
    public Page<Cellphone> findAll(Pageable pageable) {
        return cellphoneRepository.findAll(pageable);
    }

    @Override
    public Iterable<Cellphone> findAll() {
        return cellphoneRepository.findAll();
    }

    @Override
    public Optional<Cellphone> findById(Long id) {
        return cellphoneRepository.findById(id);
    }


    @Override
    public Cellphone save(Cellphone cellphone) {
        return cellphoneRepository.save(cellphone);
    }

    @Override
    public void removeById(Long id) {
        cellphoneRepository.deleteById(id);
    }

    @Override
    public Page<Cellphone> findCellphoneByNameContaining(String name, Pageable pageable) {
        return cellphoneRepository.findCellphoneByNameContaining(name, pageable);
    }
}

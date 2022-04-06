package com.codegym.controller;

import com.codegym.model.Cellphone;
import com.codegym.model.CellphoneForm;
import com.codegym.service.category.ICategoryService;
import com.codegym.service.cellphone.ICellphoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/cellphones")
public class CellphoneRestController {
    @Autowired
    private ICellphoneService cellphoneService;

    @Autowired
    private ICategoryService categoryService;

    @Value("${file-upload}")
    private String uploadPath;

    @GetMapping
    public ResponseEntity<Page<Cellphone>> findAll(@RequestParam(name = "q")Optional<String> q, @PageableDefault Pageable pageable){
        Page<Cellphone> cellphones;
        if(!q.isPresent()){
            cellphones = cellphoneService.findAll(pageable);
        } else {
            cellphones = cellphoneService.findCellphoneByNameContaining(q.get(), pageable);
        }
        if(cellphones.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(cellphones, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Cellphone> findById(@PathVariable Long id) {
        Optional<Cellphone> cellphoneOptional = cellphoneService.findById(id);
        if (!cellphoneOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(cellphoneOptional.get(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Cellphone> save(@Valid @ModelAttribute CellphoneForm cellphoneForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        MultipartFile image = cellphoneForm.getImage();
        if(image.getSize()!=0){
            String fileName = image.getOriginalFilename();
            long currentTimeMillis = System.currentTimeMillis();
            fileName = currentTimeMillis + fileName;
            try {
                FileCopyUtils.copy(image.getBytes(),new File(uploadPath+ fileName));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Cellphone cellphone = new Cellphone(cellphoneForm.getId(),cellphoneForm.getName(), cellphoneForm.getDescription(),cellphoneForm.getPrice(),fileName, cellphoneForm.getCategory());
            cellphone.setCategory(cellphoneForm.getCategory());
            return new ResponseEntity<>(cellphoneService.save(cellphone), HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/{id}")
    public ResponseEntity<Cellphone> updateProduct(@PathVariable Long id, @ModelAttribute CellphoneForm cellphoneForm) {
        Optional<Cellphone> cellphoneOptional = cellphoneService.findById(id);
        if (!cellphoneOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        MultipartFile image = cellphoneForm.getImage();
        if(image.getSize()!=0){
            String fileName = image.getOriginalFilename();
            try {
                FileCopyUtils.copy(cellphoneForm.getImage().getBytes(),new File(uploadPath+ fileName));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Cellphone cellphone = new Cellphone(cellphoneForm.getId(),cellphoneForm.getName(), cellphoneForm.getDescription(),cellphoneForm.getPrice(),fileName, cellphoneForm.getCategory());
            cellphone.setCategory(cellphoneForm.getCategory());
            return new ResponseEntity<>(cellphoneService.save(cellphone), HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Cellphone> deleteProduct(@PathVariable Long id) {
        Optional<Cellphone> cellphoneOptional = cellphoneService.findById(id);
        if (!cellphoneOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        cellphoneService.removeById(id);
        return new ResponseEntity<>(cellphoneOptional.get(), HttpStatus.OK);
    }
}

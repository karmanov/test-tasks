package com.cakes.controller;

import com.cakes.domain.Cake;
import com.cakes.service.CakeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Provides REST API for CRUD operations under Cake object
 */
@RestController
public class CakeController {

    @Autowired
    private CakeService cakeService;


    /**
     * Get all cakes from db
     * @return
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(value = "/cake-application/cakes", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Cake> findAll() {
        return cakeService.findAll();
    }

    /**
     * Get cake by id from db
     * @param id
     * @return
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(value = "/cake-application/cakes/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Cake findById(@PathVariable("id") String id) {
        return cakeService.findById(id);
    }

    /**
     * Create new cake in db
     * @param cake
     * @return
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(value = "/cake-application/cakes", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Cake create(@Valid @RequestBody Cake cake) {
        return cakeService.create(cake);
    }

    /**
     * Update existing cake by given id
     * @param id
     * @param cake
     * @return
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping(value = "/cake-application/cakes/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Cake update(@PathVariable("id") String id, @Valid @RequestBody Cake cake) {
        return cakeService.update(id, cake);
    }

    /**
     * Delete cake with given id from db
     * @param id
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping(value = "/cake-application/cakes/{id}")
    public void delete(@PathVariable("id") String id) {
        cakeService.delete(id);
    }
}

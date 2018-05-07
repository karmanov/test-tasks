package com.cakes.service;

import com.cakes.domain.Cake;
import com.cakes.exception.CakeNotFoundException;
import com.cakes.repository.CakeRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Class contains cake related CRUD operations
 */
@Service
public class CakeService {

    @Autowired
    private CakeRepository cakeRepository;

    /**
     * Find cake by id
     *
     * @param id
     * @return
     */
    public Cake findById(String id) {
        return cakeRepository.findById(id).orElseThrow(CakeNotFoundException::new);
    }

    /**
     * Find all cakes
     *
     * @return
     */
    public List<Cake> findAll() {
        return cakeRepository.findAll();
    }

    /**
     * Delete existing cake
     *
     * @param id
     */

    public void delete(String id) {
        cakeRepository.deleteById(id);
    }

    /**
     * Create new cake
     *
     * @param cake
     * @return
     */
    public Cake create(Cake cake) {
        return cakeRepository.save(cake);
    }

    /**
     * Update existing cake
     *
     * @param id
     * @param cake
     * @return
     */
    public Cake update(String id, Cake cake) {
        Cake cakeToUpdate = findById(id);
        BeanUtils.copyProperties(cake, cakeToUpdate, "id");
        return cakeRepository.save(cakeToUpdate);
    }
}

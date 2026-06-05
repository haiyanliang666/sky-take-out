package com.sky.service;

import com.sky.dto.DishDTO;

public interface DishService {

    /**
     * Add Dish and corresponding flavor
     * @param dishDTO
     */
    public void saveWithFlavor(DishDTO dishDTO);
}

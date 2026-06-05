package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.vo.DishVO;

import java.util.List;

public interface DishService {

    /**
     * Add Dish and corresponding flavor
     * @param dishDTO
     */
    public void saveWithFlavor(DishDTO dishDTO);

    /**
     * Dish Page Query
     * @param dishPageQueryDTO
     * @return
     */
    PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO);

    /**
     * delete batch dishes
     * @param ids
     */
    void deleteBatch(List<Long> ids);

    /**
     * get dish and flavor by id
     * @param id
     * @return
     */
    DishVO getByIdWithFlavor(Long id);

    /**
     * update dish info
     * @param dishDTO
     */
    void updateWithFlavor(DishDTO dishDTO);
}

package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
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

    /**
     * Start Or Stop Dish
     * @param status
     * @param id
     */
    void startOrStop(Integer status, Long id);


    /**
     * Get dishes by categoryId
     * @param categoryId
     * @return
     */
    List<Dish> list(Long categoryId);

    /**
     * 条件查询菜品和口味
     * @param dish
     * @return
     */
    List<DishVO> listWithFlavor(Dish dish);
}

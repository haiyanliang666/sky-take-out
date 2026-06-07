package com.sky.service;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.vo.SetmealVO;

import java.util.List;

public interface SetmealService {

    /**
     * Add Setmeal
     * @param setmealDTO
     */
    void saveWithDish(SetmealDTO setmealDTO);

    /**
     * Setmeal Page Query
     * @param setmealPageQueryDTO
     * @return
     */
    PageResult pageQuery(SetmealPageQueryDTO setmealPageQueryDTO);

    /**
     * get setmeal by id
     * @param id
     * @return
     */
    SetmealVO getByIdWithDish(Long id);

    /**
     * Batch Delete Setmeal
     * @param ids
     */
    void deleteBatch(List<Long> ids);


    /**
     * Start or stop set meal
     * @param status
     * @param id
     */
    void startOrStop(Integer status, Long id);


    /**
     * Update Setmeal info
     * @param setmealDTO
     */
    void update(SetmealDTO setmealDTO);
}

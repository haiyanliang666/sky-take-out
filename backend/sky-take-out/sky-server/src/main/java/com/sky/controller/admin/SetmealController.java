package com.sky.controller.admin;


import com.sky.dto.DishPageQueryDTO;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.SetmealService;
import com.sky.vo.SetmealVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/setmeal")
@Api(tags = "Set Meal API")
@Slf4j

public class SetmealController {
    @Autowired
    private SetmealService setmealService;

    /**
     * Add Setmeal
     * @param setmealDTO
     * @return
     */
    @PostMapping
    @ApiOperation("Add Setmeal")
    public Result save(@RequestBody SetmealDTO setmealDTO){
        log.info("Add Setmeal:{}",setmealDTO);
        setmealService.saveWithDish(setmealDTO);
        return Result.success();
    }


    /**
     * Setmeal Page Query
     * @param setmealPageQueryDTO
     * @return
     */
    @GetMapping("/page")
    @ApiOperation("Setmeal Page Query")
    public Result<PageResult> page(SetmealPageQueryDTO setmealPageQueryDTO){
        log.info("Setmeal Page Query:{}",setmealPageQueryDTO);
        PageResult pageResult = setmealService.pageQuery(setmealPageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * get setmeal by id
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @ApiOperation("get setmeal by id")
    public Result<SetmealVO> getById(@PathVariable Long id){
        log.info("get setmeal by id:{}",id);
        SetmealVO setmealVO = setmealService.getByIdWithDish(id);
        return Result.success(setmealVO);
    }

    /**
     *
     * @param ids
     * @return
     */
    @DeleteMapping
    @ApiOperation("Batch Delete Setmeal")
    public Result delete(@RequestParam List<Long> ids){
        log.info("Batch Delete Setmeal;{}",ids);
        setmealService.deleteBatch(ids);
        return Result.success();
    }

    /**
     * Start or stop set meal
     * @param status
     * @param id
     * @return
     */
    @PostMapping("/status/{status}")
    @ApiOperation("Start or stop set meal")
    public Result<String> startOrStop(@PathVariable("status") Integer status, Long id){
        log.info("Start or stop by id: {}", id);
        setmealService.startOrStop(status, id);
        return Result.success();
    }

    /**
     * Update Setmeal info
     * @param setmealDTO
     * @return
     */
    @PutMapping
    @ApiOperation("Update Setmeal info")
    public Result update(@RequestBody SetmealDTO setmealDTO){
        log.info("Update Setmeal info:{}",setmealDTO);
        setmealService.update(setmealDTO);
        return Result.success();
    }
}

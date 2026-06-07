package com.sky.controller.admin;


import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Dish management
 */
@RestController
@RequestMapping("/admin/dish")
@Slf4j
@Api(tags = "Dish API")
public class DishController {

    @Autowired
    private DishService dishService;
    /**
     * Add Dish
     * @param dishDTO
     * @return
     */
    @PostMapping
    @ApiOperation("Add Dish")
    public Result save(@RequestBody DishDTO dishDTO){
        log.info("Add dish:{}",dishDTO);
        dishService.saveWithFlavor(dishDTO);
        return Result.success();
    }

    /**
     * Dish Page Query
     * @param dishPageQueryDTO
     * @return
     */
    @GetMapping("/page")
    @ApiOperation("Dish Page Query")
    public Result<PageResult> page(DishPageQueryDTO dishPageQueryDTO){
        log.info("Dish Page Query:{}",dishPageQueryDTO);
        PageResult pageResult = dishService.pageQuery(dishPageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * Batch delete dishes
     * @param ids
     * @return
     */
    @DeleteMapping
    @ApiOperation("Delete Batch Dishes")
    public Result delete(@RequestParam List<Long> ids) {
        log.info("batch delete dishes:{}",ids);
        dishService.deleteBatch(ids);
        return Result.success();
    }

    /**
     * get dish by id
     * @return
     */
    @GetMapping("/{id}")
    @ApiOperation("Get dish by id")
    public Result<DishVO> getById(@PathVariable Long id){
        log.info("Get dish by id:{}",id);
        DishVO dishVO = dishService.getByIdWithFlavor(id);
        return Result.success(dishVO);
    }
    /**
     * update dish info
     *
     * @param dishDTO
     * @return
     */
    @PutMapping
    @ApiOperation("update dish info")
    public Result update(@RequestBody DishDTO dishDTO) {
        log.info("update dish info：{}", dishDTO);
        dishService.updateWithFlavor(dishDTO);

        //将所有的菜品缓存数据清理掉，所有以dish_开头的key
        //cleanCache("dish_*");

        return Result.success();
    }

    /**
     * Start Or Stop Dish
     * @param status
     * @param id
     * @return
     */
    @PostMapping("/status/{status}")
    @ApiOperation("Start Or Stop Dish")
    public Result<String> startOrStop(@PathVariable("status") Integer status, Long id){
        log.info("Start Or Stop Dish:{}", id);
        dishService.startOrStop(status, id);
        return Result.success();
    }

    /**
     * Get dishes by categoryId
     * @param categoryId
     * @return
     */
    @GetMapping("/list")
    @ApiOperation("Get dishes by categoryId")
    public Result<List<Dish>> list(Long categoryId){
        log.info("Get dish by categoryId:{}",categoryId);
        List<Dish> list = dishService.list(categoryId);
        return Result.success(list);
    }
}



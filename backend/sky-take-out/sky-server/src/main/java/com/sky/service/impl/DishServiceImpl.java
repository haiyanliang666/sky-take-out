package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.entity.Setmeal;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealDishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.javassist.expr.NewArray;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
@Slf4j
public class DishServiceImpl implements DishService {
    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private DishFlavorMapper dishFlavorMapper;
    @Autowired
    private SetmealDishMapper setmealDishMapper;
    @Autowired
    private SetmealMapper setmealMapper;

//    public DishServiceImpl(DishMapper dishMapper, DishFlavorMapper dishFlavorMapper) {
//        this.dishMapper = dishMapper;
//        this.dishFlavorMapper = dishFlavorMapper;
//    }

    /**
     * Add dish
     * @param dishDTO
     * @return
     */
    @Transactional
    //@Override
    public void saveWithFlavor(DishDTO dishDTO){

        //add one row dish data
        Dish dish = new Dish();

        BeanUtils.copyProperties(dishDTO,dish);

        dishMapper.insert(dish);

        //add n rows flavor data

        Long dishId = dish.getId();
        List<DishFlavor>  flavors = dishDTO.getFlavors();
        if (flavors != null && flavors.size() > 0){
            flavors.forEach(dishFlavor -> {
                dishFlavor.setDishId(dishId);
            });
            dishFlavorMapper.insertBatch(flavors);
        }
    }

    /**
     * Dish page query
     * @param dishPageQueryDTO
     * @return
     */
    public PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO) {
        PageHelper.startPage(dishPageQueryDTO.getPage(), dishPageQueryDTO.getPageSize());
        Page<DishVO> page = dishMapper.pageQuery(dishPageQueryDTO);

        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * Delete batch dishes
     * @param ids
     */
    public void deleteBatch(List<Long> ids) {

        for (Long id : ids) {
            Dish dish = dishMapper.getById(id);
            if (dish.getStatus() == StatusConstant.ENABLE) {
                //del not allow since dish on sale
                throw new DeletionNotAllowedException(MessageConstant.DISH_ON_SALE);
            }
        }

        List<Long> setmealIds = setmealDishMapper.getSetmealIdsByDishIds(ids);
        if (setmealIds != null && setmealIds.size() > 0) {
            //dish relate to set meal, it can't be del
            throw new DeletionNotAllowedException(MessageConstant.DISH_BE_RELATED_BY_SETMEAL);
        }

        //del dish from dish tbl
//        for (Long id : ids) {
//            dishMapper.deleteById(id);
//            //del flavor relate to dish
//            dishFlavorMapper.deleteByDishId(id);
//        }
        //improved sql queries
        //sql: del from dish where id in {id, id, id .....}
        dishMapper.deleteByIds(ids);
        dishFlavorMapper.deleteByDishIds(ids);

    }

    /**
     * get dish and flavor by id
     * @param id
     * @return
     */
    public DishVO getByIdWithFlavor(Long id) {
        //get dish
        Dish dish = dishMapper.getById(id);

        //get flavor
        List<DishFlavor> dishFlavors = dishFlavorMapper.getByDishId(id);

        //encapsulate into dishVO
        DishVO dishVO = new DishVO();
        BeanUtils.copyProperties(dish, dishVO);
        dishVO.setFlavors(dishFlavors);

        return dishVO;
    }

    /**
     * update dish and flavor info
     *
     * @param dishDTO
     */
    public void updateWithFlavor(DishDTO dishDTO) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);

        //update dish info
        dishMapper.update(dish);

        //del previous flavor info by id
        dishFlavorMapper.deleteByDishId(dishDTO.getId());

        //insert updated flavor info
        List<DishFlavor> flavors = dishDTO.getFlavors();
        if (flavors != null && flavors.size() > 0) {
            flavors.forEach(dishFlavor -> {
                dishFlavor.setDishId(dishDTO.getId());
            });
            //insert n rows to dish_flavor
            dishFlavorMapper.insertBatch(flavors);
        }
    }

    /**
     * Start Or Stop Dish
     * @param status
     * @param id
     */
    public void startOrStop(Integer status, Long id) {
        Dish dish = Dish.builder().id(id).status(status).build();
        dishMapper.update(dish);

        if (status == StatusConstant.DISABLE){
            //if stop dish, also stop setmeal include dish
            List<Long> dishIds = new ArrayList<>();
            dishIds.add(id);

            //select setmeal_id from setmeal_dish where dish_id in (id,id,id...)
            List<Long> setmealIds = setmealDishMapper.getSetmealIdsByDishIds(dishIds);
            if (setmealIds != null && setmealIds.size() > 0){
                for (Long setmealId : setmealIds){
                    Setmeal setmeal = Setmeal.builder().id(setmealId).status(status).build();
                    setmealMapper.update(setmeal);
                }
            }
        }
    }

    /**
     * Get dishes by categoryId
     * @param categoryId
     * @return
     */
    public List<Dish> list(Long categoryId) {
        Dish dish = Dish.builder()
                .categoryId(categoryId)
                .status(StatusConstant.ENABLE)
                .build();
        return dishMapper.list(dish);
    }


}

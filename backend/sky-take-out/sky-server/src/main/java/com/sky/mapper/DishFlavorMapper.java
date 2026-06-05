package com.sky.mapper;

import com.sky.entity.DishFlavor;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DishFlavorMapper {
    /**
     * Batch insert flavors
     * @param flavors
     */
    void insertBatch(List<DishFlavor> flavors);

    /**
     * del dish flavors by dishId
     * @param dishId
     */

    @Delete("delete from dish_flavor where dish_id = #{dishId}")
    void deleteByDishId(Long dishId);

    /**
     * del dish flavors by dishIds
     * @param dishIds
     */
    void deleteByDishIds(List<Long> dishIds);

    /**
     * get flavors by dishid
     * @param dishId
     * @return
     */
    @Select("select * from dish_flavor where dish_id = #{dishId}")
    List<DishFlavor> getByDishId(Long dishId);
}

package com.sky.controller.user;

import com.sky.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController("userShopController")
@RequestMapping("/user/shop")
@Api(tags = "Shop API")
@Slf4j

public class ShopController {
    public static final String KEY = "SHOP_STATUS";
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * Set Shop status
     * @param status
     * @return
     */
    @PutMapping("/{status}")
    @ApiOperation("Set Shop status")
    public Result setStatus(@PathVariable Integer status){
        log.info("Set Shop status to：{}",status == 1 ? "营业中" : "打烊中");
        redisTemplate.opsForValue().set(KEY, String.valueOf(status));
        return Result.success();
    }

    /**
     * Get Shop status
     * @return
     */
    @GetMapping("/status")
    @ApiOperation("Get Shop status")
    public Result<Integer> getStatus(){
        Integer status = Integer.valueOf(redisTemplate.opsForValue().get(KEY));
        log.info("Get Shop status：{}",status == 1 ? "营业中" : "打烊中");
        return Result.success(status);
    }
}

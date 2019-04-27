package com.panda.item.service;


import com.panda.common.pojo.PageResult;
import com.panda.item.pojo.Brand;

public interface BrandService {
    PageResult<Brand> page(Integer page, Integer rows,String sotrBy,Boolean desc,String key);
}

package com.panda.item.service;


import com.panda.common.pojo.PageResult;
import com.panda.item.pojo.Brand;

import java.util.List;

public interface BrandService {
    PageResult<Brand> page(Integer page, Integer rows,String sotrBy,Boolean desc,String key);

    void addBrand(Brand brand, List<Long> cids);
}

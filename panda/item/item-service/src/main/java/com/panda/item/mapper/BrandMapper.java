package com.panda.item.mapper;

import com.panda.item.pojo.Brand;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

public interface BrandMapper extends Mapper<Brand> {
    //对于通用mapper没有解决的语句，需要自己写sql，两种方式：application.yml中的mybatis中去配和注解配置
    //注解不是很好用，但是单表都可以使用
    @Insert("insert into tb_category_brand (category_id,brand_id) values (#{cid},#{bid})")
    void insertCategoryBrand(@Param("cid") Long cid, @Param("bid") Long bid);
}

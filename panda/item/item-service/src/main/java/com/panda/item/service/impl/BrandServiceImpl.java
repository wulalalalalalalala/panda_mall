package com.panda.item.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.panda.common.pojo.PageResult;
import com.panda.item.mapper.BrandMapper;
import com.panda.item.pojo.Brand;
import com.panda.item.service.BrandService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;


@Service
public class  BrandServiceImpl implements BrandService {

    @Autowired
    private BrandMapper brandMapper;

    /**
     * 分页查询，以及动态排序
     *
     * @param page
     * @param rows
     * @param sotrBy
     * @param desc
     * @return
     */
    @Override
    public PageResult<Brand> page(Integer page, Integer rows,String sotrBy,Boolean desc,String key) {
        PageHelper.startPage(page,rows);

        //通用mapper使用动态sql,这里创建Brand对象来操作动态sql
        Example example = new Example(Brand.class);

        //判断sortBy不为空，根据其排序select XXX from tb_brand order by id desc
        if(StringUtils.isNotBlank(sotrBy)){   //" DESC":" ASC"前要有空格
            example.setOrderByClause(sotrBy+(desc?" DESC":" ASC"));//该方法相当于写好了sql语句中的order by
        }

        //关键字搜索查询
        if(StringUtils.isNotBlank(key)){
            //创建动态条件的封装工具
            Example.Criteria criteria = example.createCriteria();
            criteria.andLike("name","%"+key+"%");//where name like "%"+key+"%"
            criteria.andLike("letter","%"+key+"%");
        }
        //直接使用强转为Page就省得使用pageInfo再统计
        Page<Brand> brandPage = (Page<Brand>) brandMapper.selectByExample(example);
        //以前通用mapper中PageInfo自动统计行数再调用方法，现在直接强转
        //PageInfo<Brand> pageInfo = new PageInfo<>(select);

        return new PageResult<>(brandPage.getTotal(),new Long(brandPage.getPages()),brandPage);
    }
}

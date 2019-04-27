package com.panda.item.service.impl;

import com.panda.item.mapper.CategoryMapper;
import com.panda.item.pojo.Category;
import com.panda.item.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public List<Category> queryByparentId(Long pid) {
        //pid不是主键，所以需要根据查询参数查询
        Category category=new Category();
        category.setParentId(pid);
        return this.categoryMapper.select(category);
    }
}

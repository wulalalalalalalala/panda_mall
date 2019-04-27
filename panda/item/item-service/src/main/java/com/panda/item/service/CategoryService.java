package com.panda.item.service;


import com.panda.item.pojo.Category;
import java.util.List;

public interface CategoryService {
    List<Category> queryByparentId(Long pid);
}

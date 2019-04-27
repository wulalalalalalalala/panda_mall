package com.panda.item.controller;

import com.panda.item.pojo.Category;
import com.panda.item.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    /**
     * 查询分类
     *
     * @param pid
     * @return ResponseEntity<List<Category>> 返回状态码的同时返回数据
     *
     * 状态码：201新建 202接收成功但没有数据
     *       302转发重定向
     *       400请求参数异常 401未授权 403禁止访问 405请求方法错误（get post） 413文件上传大于限定大小
     */

    @GetMapping("list")
    public ResponseEntity<List<Category>> queryByparentId(@RequestParam("pid")Long pid){
        List<Category> categories=this.categoryService.queryByparentId(pid);
        if (categories != null) {
            //返回所有的数据并且返回状态码200
            return ResponseEntity.ok(categories);
        }
        //没有数据返回204
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}

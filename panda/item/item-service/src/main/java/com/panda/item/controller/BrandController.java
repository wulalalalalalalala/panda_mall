package com.panda.item.controller;

import com.panda.common.pojo.PageResult;
import com.panda.item.pojo.Brand;
import com.panda.item.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("brand")
public class BrandController {
    @Autowired
    private BrandService brandService;
    /**
     * 分页查询品牌，把查询到的所有数据封装到PageResult（总条数、总页数、当前页数据）
     *
     * @param page 查询哪一页
     * @param rows 当前页内容量
     * @return PageResult<Brand>
     */
    @GetMapping("page")
    public ResponseEntity<PageResult<Brand>> page(
            @RequestParam(value = "page",defaultValue = "1") Integer page,
            @RequestParam(value = "rows",defaultValue = "10") Integer rows,
            //required = false的意思是请求参数可能有，可能没有
            @RequestParam(value = "sortBy",required = false) String sortBy,
            @RequestParam(value = "desc",required = false) Boolean desc,
            @RequestParam(value = "key",required = false)String key) {

        PageResult<Brand> pageResult = this.brandService.page(page,rows,sortBy,desc,key);
        if (pageResult != null&&null!=pageResult.getItems()&&0!=pageResult.getItems().size()) {
            return ResponseEntity.ok(pageResult);
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * 保存品牌并同时获取分类的id
     *
     * @param brand
     * @param cids springmvc消息转换器可以直接使用将有特征的字符串转换成集合数组
     * @return
     */
    @PostMapping
    public ResponseEntity<Void> addBrand(Brand brand, @RequestParam("cids")List<Long> cids){
        this.brandService.addBrand(brand,cids);
        return ResponseEntity.status(HttpStatus.CREATED).build();//201
    }

}

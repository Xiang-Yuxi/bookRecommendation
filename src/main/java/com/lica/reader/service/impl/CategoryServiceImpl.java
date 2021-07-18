package com.lica.reader.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lica.reader.entity.Category;
import com.lica.reader.mapper.CategoryMapper;
import com.lica.reader.service.CategoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
@Service("categoryService")
@Transactional(propagation = Propagation.NOT_SUPPORTED,readOnly = true)
public class CategoryServiceImpl implements CategoryService {
    @Resource
    private CategoryMapper categoryMapper;


    //查询所有图书分类
    //返回图书分类List
    public List<Category> selectAll() {
        List<Category> list = categoryMapper.selectList(new QueryWrapper<Category>());
        return list;
    }
}


package com.lica.reader.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lica.reader.entity.Book;
import com.lica.reader.entity.Evaluation;
import com.lica.reader.entity.MemberReadState;
import com.lica.reader.mapper.BookMapper;
import com.lica.reader.mapper.EvaluationMapper;
import com.lica.reader.mapper.MemberReadStateMapper;
import com.lica.reader.service.BookService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service("bookService")
@Transactional(propagation = Propagation.NOT_SUPPORTED,readOnly = true)
public class BookServiceImpl implements BookService {
    @Resource
    private BookMapper bookMapper;
    @Resource
    private MemberReadStateMapper memberReadStateMapper;
    @Resource
    private EvaluationMapper evaluationMappere;
    /**
     *
     * @param page 页号
     * @param rows 每页记录数
     * @param categoryId 分类编号
     * @param order 排序方式
     * @return 分页对象
     */
    public IPage<Book> paging(Long categoryId , String order , Integer page , Integer rows){
        Page<Book> p = new Page<Book>(page,rows);
        QueryWrapper<Book> queryWrapper = new QueryWrapper<Book>();
        if(categoryId != null && categoryId != -1){
            queryWrapper.eq("category_id",categoryId);
        }
        if(order !=null){
            if(order.equals("quantity")){
                queryWrapper.orderByDesc("evaluation_quantity");
            }else if(order.equals("score")){
                queryWrapper.orderByDesc("evaluation_score");
            }
        }
        Page<Book> pageObject = bookMapper.selectPage(p, queryWrapper);
        return pageObject;
    }

    /**
     *根据图书编号查询图书对象
     * @param bookId 图书标号
     * @return 图书对象
     */
    public Book selectById(Long bookId) {
        Book book = bookMapper.selectById(bookId);
        return book;
    }

    @Transactional //开启声明式事务
    public void updateEvaluation() {
        bookMapper.updateEvaluation();
    }

    @Transactional
    public Book creatBook(Book book) {
        bookMapper.insert(book);
        return book;
    }

    @Transactional
    public Book updateBook(Book book) {
        bookMapper.updateById(book);
        return book;
    }

    @Transactional
    public void deleteBook(Long bookId) {
        bookMapper.deleteById(bookId);
        QueryWrapper<MemberReadState> mrsQueryWrapper = new QueryWrapper<MemberReadState>();
        mrsQueryWrapper.eq("book_id" , bookId);
        memberReadStateMapper.delete(mrsQueryWrapper);
        QueryWrapper<Evaluation> evaluationQueryWrapper = new QueryWrapper<Evaluation>();
        evaluationQueryWrapper.eq("book_id" , bookId);
        evaluationMappere.delete(evaluationQueryWrapper);
    }
}

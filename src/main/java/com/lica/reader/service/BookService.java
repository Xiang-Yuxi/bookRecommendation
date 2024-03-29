package com.lica.reader.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lica.reader.entity.Book;


public interface BookService {
    /**
     *
     * @param page 页号
     * @param rows 每页记录数
     * @param categoryId 分类编号
     * @param order 排序方式
     * @return 分页对象
     */
    public IPage<Book> paging(Long categoryId , String order , Integer page , Integer rows);

    /**
     *根据图书编号查询图书对象
     * @param bookId 图书标号
     * @return 图书对象
     */
    public Book selectById(Long bookId);

    /**
     * 更新图书评分、评价数量
     */
    public void updateEvaluation();

    /**
     * 创建对应的图书
     * @param book
     * @return
     */
    public Book creatBook(Book book);

    /**
     * 更新图书
     * @param book 新图书数据
     * @return 更新后的数据
     */
    public Book updateBook(Book book);


    /**
     * 删除图书及相关数据
     * @param bookId 图书编号
     */
    public void deleteBook(Long bookId);
}

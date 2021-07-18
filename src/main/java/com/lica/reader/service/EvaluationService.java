package com.lica.reader.service;

import com.baomidou.mybatisplus.core.metadata.IPage;

import com.lica.reader.entity.Evaluation;

import java.util.List;

public interface EvaluationService {
    /**
     * 按图书编号有效查询
     *
     * @param bookId 图书编号
     * @return 评论列表
     */
    public List<Evaluation> selectByBookId(Long bookId);

    /**
     * 分页查询短评
     *
     * @param page 页号
     * @param rows 每页记录数
     * @return
     */
    public IPage<Evaluation> paging(Integer page, Integer rows);

    /**
     * 禁用短评
     * @param evaluationId 禁用短评id
     * @param state 短评状态
     * @return
     */
    public Evaluation diable(Long evaluationId,String state);

    /**
     * 查询短评
     * @param evaluationId
     * @return
     */
    public Evaluation selectByEvaluationId(Long evaluationId);

}

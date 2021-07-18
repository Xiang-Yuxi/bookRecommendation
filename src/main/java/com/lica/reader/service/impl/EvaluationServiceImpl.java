package com.lica.reader.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lica.reader.entity.Book;
import com.lica.reader.entity.Evaluation;
import com.lica.reader.entity.Member;
import com.lica.reader.mapper.BookMapper;
import com.lica.reader.mapper.EvaluationMapper;
import com.lica.reader.mapper.MemberMapper;
import com.lica.reader.service.EvaluationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
@Service("evaluationService")
@Transactional(propagation = Propagation.NOT_SUPPORTED,readOnly = true)
public class EvaluationServiceImpl implements EvaluationService {
    @Resource
    private EvaluationMapper evaluationMapper;
    @Resource
    private MemberMapper memberMapper;
    @Resource
    private BookMapper bookMapper;
    /**
     * 按图书编号有效查询
     * @param bookId 图书编号
     * @return 评论列表
     */
    public List<Evaluation> selectByBookId(Long bookId) {
        Book book = bookMapper.selectById(bookId);
        QueryWrapper<Evaluation> queryWrapper = new QueryWrapper<Evaluation>();
        queryWrapper.eq("book_id",bookId);
        queryWrapper.eq("state","enable");
        queryWrapper.orderByDesc("create_time");
        List<Evaluation> evaluationList = evaluationMapper.selectList(queryWrapper);
        for(Evaluation eva : evaluationList){
            Member member = memberMapper.selectById(eva.getMemberId());
            eva.setMember(member);
            eva.setBook(book);
        }
        return evaluationList;
    }

    public IPage<Evaluation> paging(Integer page, Integer rows) {
        Page<Evaluation> p = new Page<Evaluation>(page,rows);
        QueryWrapper<Evaluation> queryWrapper = new QueryWrapper<Evaluation>();
        //queryWrapper.eq("state","enable");
        Page<Evaluation> pageObject = evaluationMapper.selectPage(p, queryWrapper);
        for(Evaluation eva : pageObject.getRecords()){
            Member member = memberMapper.selectById(eva.getMemberId());
            Book book = bookMapper.selectById(eva.getBookId());
            eva.setMember(member);
            eva.setBook(book);
        }
        return pageObject;
    }

    public Evaluation diable(Long evaluationId, String result) {
        Evaluation evaluation = evaluationMapper.selectById(evaluationId);
        if(evaluation.getState().equals("enable")){
            evaluation.setState("disable");
            evaluation.setDisableReason(result);
            evaluationMapper.updateById(evaluation);
        }

        return evaluation;
    }

    public Evaluation selectByEvaluationId(Long evaluationId) {
        Evaluation evaluation = evaluationMapper.selectById(evaluationId);
        return evaluation;
    }


}

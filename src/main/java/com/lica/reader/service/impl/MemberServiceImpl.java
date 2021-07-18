package com.lica.reader.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lica.reader.entity.Evaluation;
import com.lica.reader.entity.Member;
import com.lica.reader.entity.MemberReadState;
import com.lica.reader.mapper.EvaluationMapper;
import com.lica.reader.mapper.MemberMapper;
import com.lica.reader.mapper.MemberReadStateMapper;
import com.lica.reader.service.MemberService;
import com.lica.reader.service.exception.BussinessException;
import com.lica.reader.utils.MD5Utils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Service("memberService")
@Transactional
public class MemberServiceImpl implements MemberService {
    @Resource
    private MemberReadStateMapper memberReadStateMapper;

    @Resource
    private MemberMapper memberMapper;

    @Resource
    private EvaluationMapper evaluationMapper;

    /**
     * 会员注册，创建新会员
     *
     * @param username 用户名
     * @param password 密码
     * @param nickname 昵称
     * @return 新会员对象
     */
    public Member createMember(String username, String password, String nickname) {
        QueryWrapper<Member> queryWrapper = new QueryWrapper<Member>();
        queryWrapper.eq("username", username);
        List<Member> memberList = memberMapper.selectList(queryWrapper);
        //判断用户名是否存在
        if (memberList.size() > 0) {
            throw new BussinessException("M01", "用户名已存在");
        }
        Member member = new Member();
        member.setUsername(username);
        member.setNickname(nickname);
        int salt = new Random().nextInt(1000) + 1000;  //盐值
        String md5 = MD5Utils.md5Digest(password, salt);
        member.setPassword(md5);
        member.setSalt(salt);
        member.setCreateTime(new Date());
        memberMapper.insert(member);
        return member;
    }

    public Member checkLogin(String username, String password) {
        QueryWrapper<Member> queryWrapper = new QueryWrapper<Member>();
        queryWrapper.eq("username", username);
        Member member = memberMapper.selectOne(queryWrapper);
        //对用户是否存在进行校验
        if (member == null) {
            throw new BussinessException("M02", "用户不存在");
        }
        //对密码是否存在在进行校验
        String md5 = MD5Utils.md5Digest(password, member.getSalt());
        if (!md5.equals(member.getPassword())) {
            throw new BussinessException("M03", "输入密码有误");
        }
        return member;
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED , readOnly = true) //告诉service当前方法不需要事务
    public MemberReadState selectMemberReadState(Long memberId, Long bookId) {
        QueryWrapper<MemberReadState> queryWrapper = new QueryWrapper<MemberReadState>();
        queryWrapper.eq("book_id", bookId);
        queryWrapper.eq("member_id", memberId);
        MemberReadState memberReadState = memberReadStateMapper.selectOne(queryWrapper);
        return memberReadState;
    }

    public MemberReadState updateMemberReadState(Long memberId, Long bookId, Integer readState) {
        QueryWrapper<MemberReadState> queryWrapper = new QueryWrapper<MemberReadState>();
        queryWrapper.eq("book_id", bookId);
        queryWrapper.eq("member_id", memberId);
        MemberReadState memberReadState = memberReadStateMapper.selectOne(queryWrapper);
        //无则新增，有则更新
        if(memberReadState == null){
            memberReadState = new MemberReadState();
            memberReadState.setMemberId(memberId);
            memberReadState.setBookId(bookId);
            memberReadState.setReadState(readState);
            memberReadState.setCreateTime(new Date());
            memberReadStateMapper.insert(memberReadState);
        }else{
            memberReadState.setReadState(readState);
            memberReadStateMapper.updateById(memberReadState);
        }

        return null;
    }

    public Evaluation evaluate(Long memberId, long bookId, Integer score, String content) {
        Evaluation evaluation = new Evaluation();
        evaluation.setMemberId(memberId);
        evaluation.setBookId(bookId);
        evaluation.setScore(score);
        evaluation.setContent(content);
        evaluation.setCreateTime(new Date());
        evaluation.setState("enable");
        evaluation.setEnjoy(0);
        evaluationMapper.insert(evaluation);
        return evaluation;
    }

    public Evaluation enjoy(Long evaluationId) {
        Evaluation evaluation = evaluationMapper.selectById(evaluationId);
        evaluation.setEnjoy(evaluation.getEnjoy()+1);
        evaluationMapper.updateById(evaluation);
        return evaluation;
    }
}



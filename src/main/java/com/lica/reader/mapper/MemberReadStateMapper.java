package com.lica.reader.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lica.reader.entity.MemberReadState;

public interface MemberReadStateMapper extends BaseMapper<MemberReadState> {
    void selectOne(QueryWrapper<MemberReadStateMapper> queryWrapper);
}

package com.lica.reader.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lica.reader.entity.Test;

public interface TestMapper extends BaseMapper<Test> {  //传入泛型，说明对应的实体时test
    public void insertSample();

}

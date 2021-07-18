package com.lica.reader.service;

import com.lica.reader.mapper.TestMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class TestService {
    @Resource
    private TestMapper testMapper;
    @Transactional    //要么数据全部提交 要么数据全部回滚
    public void batchImport(){
        for(int i = 0;i < 5;i++){

            testMapper.insertSample();
        }
    }



}

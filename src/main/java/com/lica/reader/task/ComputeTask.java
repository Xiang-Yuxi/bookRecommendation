package com.lica.reader.task;

import com.lica.reader.service.BookService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 完成自动计算任务
 */
@Component
public class ComputeTask {
    @Resource
    private BookService bookService;
    //任务调度(秒、分、时、日、月、星期) 在每分钟0秒的时候自动执行一下定时任务
    @Scheduled(cron = "0 * * * * ?")
    public void updateEvaluation(){
        bookService.updateEvaluation();
        System.out.println("已更新所有图书评分");
    }

}

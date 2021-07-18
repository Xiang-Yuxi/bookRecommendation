package com.lica.reader.controller.management;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lica.reader.entity.Evaluation;
import com.lica.reader.service.EvaluationService;
import com.lica.reader.service.exception.BussinessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/management/evaluation")
public class MEvaluationController {
    @Resource
    private EvaluationService evaluationService;
    @GetMapping("/index.html")
    public ModelAndView showBook() {
        return new ModelAndView("/management/evaluation");
    }

    @GetMapping("/list")
    @ResponseBody
    public Map list(Integer page, Integer limit){
        if(page == null){
            page = 1;
        }

        if(limit == null){
            limit = 20;
        }

        IPage<Evaluation> pageObject = evaluationService.paging(page, limit);
        HashMap result = new HashMap();
        result.put("code","0");
        result.put("msg","success");
        result.put("data",pageObject.getRecords()); //当前页面数据
        result.put("count",pageObject.getTotal()); //未分页时记录总数
        return result;
    }

    @PostMapping("/disable")
    @ResponseBody
    public Map list(Long evaluationId , String state) {
        HashMap result = new HashMap();
        try {
            Evaluation rowEvaluation = evaluationService.selectByEvaluationId(evaluationId);
            evaluationService.diable(rowEvaluation.getEvaluationId() , rowEvaluation.getDisableReason());
            result.put("code","0");
            result.put("msg","success");
        }catch (BussinessException ex){
            ex.printStackTrace();
            result.put("code", ex.getCode());
            result.put("msg", ex.getMsg());
        }
        return result;
    }

}

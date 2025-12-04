package com.yuxh.music.controller;

import com.yuxh.music.dto.AjaxResult;
import com.yuxh.music.service.SearchService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Aizmr
 * @CreateTime: 2025-12-04
 * @Description:
 */
@RestController
@RequestMapping("/search")
public class SearchController extends BaseController {

    @Resource
    private SearchService searchService;

    @GetMapping("/list")
    public AjaxResult list(String name) {
        return success(searchService.list(name));
    }
    @GetMapping("/history")
    public AjaxResult history(String name) {
        return success(searchService.history(name));
    }

}

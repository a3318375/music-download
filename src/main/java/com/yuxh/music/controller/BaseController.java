package com.yuxh.music.controller;

import com.yuxh.music.dto.AjaxResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import java.beans.PropertyEditorSupport;
import java.util.Date;
import java.util.List;

/**
 * @Author: Aizmr
 * @CreateTime: 2025-12-04
 * @Description:
 */
public class BaseController {

    public AjaxResult success() {
        return AjaxResult.success();
    }

    public AjaxResult success(String message) {
        return AjaxResult.success(message);
    }

    public AjaxResult success(Object data) {
        return AjaxResult.success(data);
    }

    public AjaxResult error() {
        return AjaxResult.error();
    }

    public AjaxResult error(String message) {
        return AjaxResult.error(message);
    }
}


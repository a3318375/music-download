package com.yuxh.music.dto;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Objects;

/**
 * @Author: Aizmr
 * @CreateTime: 2025-12-04
 * @Description:
 */
public class AjaxResult extends HashMap<String, Object> {
    private static final long serialVersionUID = 1L;
    public static final String CODE_TAG = "code";
    public static final String MSG_TAG = "msg";
    public static final String DATA_TAG = "data";

    public AjaxResult() {
    }

    public AjaxResult(int code, String msg) {
        super.put("code", code);
        super.put("msg", msg);
    }

    public AjaxResult(int code, String msg, Object data) {
        super.put("code", code);
        super.put("msg", msg);
        if (data != null) {
            super.put("data", data);
        }

    }

    public static AjaxResult success() {
        return success("操作成功");
    }

    public static AjaxResult success(Object data) {
        return success("操作成功", data);
    }

    public static AjaxResult success(String msg) {
        return success(msg, (Object) null);
    }

    public static AjaxResult success(String msg, Object data) {
        return new AjaxResult(200, msg, data);
    }

    public static AjaxResult warn(String msg) {
        return warn(msg, (Object) null);
    }

    public static AjaxResult warn(String msg, Object data) {
        return new AjaxResult(601, msg, data);
    }

    public static AjaxResult error() {
        return error("操作失败");
    }

    public static AjaxResult error(String msg) {
        return error(msg, (Object) null);
    }

    public static AjaxResult error(String msg, Object data) {
        return new AjaxResult(500, msg, data);
    }

    public static AjaxResult error(int code, String msg) {
        return new AjaxResult(code, msg, (Object) null);
    }

    public boolean isSuccess() {
        return Objects.equals(200, this.get("code"));
    }

    public boolean isWarn() {
        return Objects.equals(601, this.get("code"));
    }

    public boolean isError() {
        return Objects.equals(500, this.get("code"));
    }

    public AjaxResult put(String key, Object value) {
        super.put(key, value);
        return this;
    }

    public static AjaxResult successPage(Object records, Long total, int size, int current, int pages) {
        AjaxResult re = new AjaxResult(200, "操作成功");
        re.put((String) "total", total);
        re.put((String) "size", size);
        re.put("data", records);
        re.put((String) "current", current);
        re.put((String) "pages", pages);
        return re;
    }
}


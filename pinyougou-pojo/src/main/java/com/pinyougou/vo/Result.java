package com.pinyougou.vo;

import java.io.Serializable;

public class Result implements Serializable {
    private Boolean success;
    private String massage;

    public Result(Boolean success, String massage) {
        this.success = success;
        this.massage = massage;
    }
    //成功方法
    public static Result ok(String massage){
        return new Result(true,massage);
    }
    //失败方法
    public static Result error(String massage){
        return new Result(false,massage);
    }
    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMassage() {
        return massage;
    }

    public void setMassage(String massage) {
        this.massage = massage;
    }
}

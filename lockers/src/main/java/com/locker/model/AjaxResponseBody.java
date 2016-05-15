package com.locker.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.locker.jsonview.Views;

/**
 * Created by randyr on 5/15/16.
 */
public class AjaxResponseBody {
    @JsonView(Views.Public.class)
    String message;

    @JsonView(Views.Public.class)
    String code;

    @JsonView(Views.Public.class)
    Iterable<LockerEntity> result;

    public void setMessage(String message) {this.message = message;}
    public String getMessage() {return message;}

    public void setCode(String code) {this.code = code;}
    public String getCode() {return code;}

    public void setResult(Iterable<LockerEntity> result) {this.result = result;}
    public Iterable<LockerEntity> getResult() {return result;}
}

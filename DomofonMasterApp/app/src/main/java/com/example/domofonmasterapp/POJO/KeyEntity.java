package com.example.domofonmasterapp.POJO;

import java.io.Serializable;

public class KeyEntity implements Serializable{
    private Long id_key;
    private Long code;
    //private boolean type; // true - iButton/64bit ; false - em-marine/32bit
    private String data_start;
    private String data_end;
    private Long porch_id;
    private Long intercom_id;

    public Long getIntercom_id() {
        return intercom_id;
    }

    public void setIntercom_id(Long intercom_id) {
        this.intercom_id = intercom_id;
    }

    public Long getPorch_id() {
        return porch_id;
    }

    public void setPorch_id(Long porch_id) {
        this.porch_id = porch_id;
    }

    public Long getId_key() {
        return id_key;
    }

    public void setId_key(Long id_key) {
        this.id_key = id_key;
    }

    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }

   /* public boolean isType() { return type; }

    public void setType(boolean type) {
        this.type = type;
    }*/

    public String getData_start() {
        return data_start;
    }

    public void setData_start(String data_start) {
        this.data_start = data_start;
    }

    public String getData_end() {
        return data_end;
    }

    public void setData_end(String data_end) {
        this.data_end = data_end;
    }
}

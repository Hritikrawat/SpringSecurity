package com.example.SpringSecurityImpl.entity;


import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
@Document(collection = "SpringSecurity")
public class Janta
{
    String jid;
    String jname;
    String jpass;
    List<String> mobile;

    public Janta(){

    }

    public Janta(String jid, String jname, String jpass, List<String> mobile) {
        this.jid = jid;
        this.jname = jname;
        this.jpass = jpass;
        this.mobile = mobile;
    }

    public String getJid() {
        return jid;
    }

    public void setJid(String jid) {
        this.jid = jid;
    }

    public String getJname() {
        return jname;
    }

    public void setJname(String jname) {
        this.jname = jname;
    }

    public String getJpass() {
        return jpass;
    }

    public void setJpass(String jpass) {
        this.jpass = jpass;
    }

    public List<String> getMobile() {
        return mobile;
    }

    public void setMobile(List<String> mobile) {
        this.mobile = mobile;
    }
}

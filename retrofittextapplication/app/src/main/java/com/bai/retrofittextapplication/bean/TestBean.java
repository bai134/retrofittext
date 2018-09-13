package com.bai.retrofittextapplication.bean;

import java.util.List;

public class TestBean extends BaseResponse {

    private data data;

    public TestBean.data getData() {
        return data;
    }

    public void setData(TestBean.data data) {
        this.data = data;
    }

    public class data{

    }

    private String welcomeurl;

    private List<String> greeturl;

    public List<String> getGreeturl() {
        return greeturl;
    }

    public void setGreeturl(List<String> greeturl) {
        this.greeturl = greeturl;
    }

    public String getWelcomeurl() {
        return welcomeurl;
    }

    public void setWelcomeurl(String welcomeurl) {
        this.welcomeurl = welcomeurl;
    }
}

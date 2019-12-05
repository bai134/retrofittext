package com.qcsoft.baselibrary.bean;

/**
 * Cerated by bailing
 * Create date : 2019/7/3 14:53
 * description :
 */
public class TokenBean extends BaseResponse {


    /**
     * data : {"token":"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1NzQ3NjUzNzEsImlhdCI6MTU3NDc2NDc3MX0.-4DVP9VxRRJCSHiUZZRBZuKTk240b9YpAz4JtBhTbyY"}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * token : eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1NzQ3NjUzNzEsImlhdCI6MTU3NDc2NDc3MX0.-4DVP9VxRRJCSHiUZZRBZuKTk240b9YpAz4JtBhTbyY
         */

        private String token;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}

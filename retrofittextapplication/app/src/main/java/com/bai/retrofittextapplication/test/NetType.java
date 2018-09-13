package com.bai.retrofittextapplication.test;

public enum NetType {

    NET_DISABLED(0),
    NET_WIFI(1),
    NET_MOBILE(2),
    NET_UNKNOWN(3);
    private int code;

    NetType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public NetType getNetType(int code){
        switch (code) {
            case 0 :
                return NET_DISABLED;
            case 1:
                return NET_WIFI;
            case 2:
                return NET_MOBILE;
            case 3:
                return NET_UNKNOWN;
        }
        return null;
    }

}

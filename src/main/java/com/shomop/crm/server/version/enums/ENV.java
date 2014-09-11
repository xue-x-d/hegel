package com.shomop.crm.server.version.enums;

public enum ENV {
    DEVELOP("develop"),PRODUCT("product"),JUNIT("junit");
    private String file;
    private ENV(String file){
        this.file = file;
    }
    public String getFile(){
        return this.file;
    }
}

package com.shomop.connect.server.version.enums;

public enum VERSION {
    TAOBAO("taobao"),DD("dd"),JD("jd");
    private String file;
    private VERSION(String file){
        this.file = file;
    }
    public String getFile(){
        return this.file;
    }
    
    public static VERSION getVersion(String name){
    	for(VERSION version : VERSION.values()){
    		if(version.getFile().equals(name)){
    			return version;
    		}
    	}
    	return TAOBAO;
    }
}

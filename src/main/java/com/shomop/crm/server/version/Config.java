package com.shomop.crm.server.version;

import java.io.File;

import com.shomop.crm.server.version.enums.ENV;
import com.shomop.crm.server.version.enums.VERSION;


/**
 * 各版本的一些差异性配置
 * @author shomop
 */
public class Config {
    public  VERSION version;
    public  ENV env;
    public Config(VERSION version, ENV env) {
        this.version = version;
        this.env = env;
    }
    /**
     * 默认linux系统为正式环境
     * @param version
     */
    public Config(VERSION version){
        this.version = version;
        //windows 下
        if("\\".equals(File.separator)){   
            this.env = ENV.DEVELOP;
        }else
        //linux下
        if("/".equals(File.separator)){   
            this.env = ENV.PRODUCT;
        }

    }
}


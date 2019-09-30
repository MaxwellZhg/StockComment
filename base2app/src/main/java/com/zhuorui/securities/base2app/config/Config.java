package com.zhuorui.securities.base2app.config;


import android.content.Context;

import com.github.fernandodev.androidproperties.lib.AssetsProperties;
import com.github.fernandodev.androidproperties.lib.Property;

/**
 * 默认配置信息
 * 其余配置额外参数信息可继承该类进行配置读取
 * 1.assets目录下需要配置properties文件
 * 2.run_mode配置文件名采用config_{runMode}.properties
 * 3.统一配置文件名config.properties
 */
public class Config extends AssetsProperties {

    @Property
    private String run_mode;/*运行环境*/

    /*自定义属性*/
    private RunConfig runConfig;
    private RunMode runMode;

    public Config(Context context) {
        super(context);
        /*依据配置读取运行环境*/
        runMode = RunMode.readRunModeByString(run_mode);
        runConfig = new RunConfig(context, "config_" + runMode.name());
    }

    public RunConfig getRunConfig() {
        return runConfig;
    }

    public boolean isDebug() {
        return runConfig.open_debug;
    }

    public int logLevel() {
        return runConfig.log_level;
    }

    public String domain() {
        // 主域名默认使用用户域名
        return runConfig.domain_api;
    }

    public String privateKey() {
        // 加密私钥
        return runConfig.private_key;
    }

    public int writeTimeout() {
        return runConfig.write_timeout;
    }

    public int readTimeout() {
        return runConfig.read_timeout;
    }

    public int connectTimeout() {
        return runConfig.connect_timeout;
    }

    public RunMode getRunMode() {
        return runMode;
    }
}
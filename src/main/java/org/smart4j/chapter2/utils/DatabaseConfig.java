package org.smart4j.chapter2.utils;

/**
 * Created by alvin on 2016/3/12.
 */
public enum DatabaseConfig {
    MYSQL("jdbc.driver","jdbc.url","jdbc.username","jdbc.password");

    private String driver;
    private String url;
    private String username;
    private String password;

    DatabaseConfig(String driver, String url, String username, String password) {
        this.driver = driver;
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public String getDriver() {
        return driver;
    }

    public String getUrl() {
        return url;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}

package com.view.vo;

import javafx.beans.property.SimpleStringProperty;

import java.lang.reflect.Field;

/**
 * @author yinfx
 * @time 15-1-5 下午6:06
 */
public class NCSmartClientVO {
    public static String NAME = "name";
    public static String JAVAHOME="javahome";
    public static String IP="ip";
    public static String PORT="port";

    private SimpleStringProperty name;
    private SimpleStringProperty javahome = new SimpleStringProperty("java");
    private SimpleStringProperty ip;
    private SimpleStringProperty port = new SimpleStringProperty("80");

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getJavahome() {
        return javahome.get();
    }

    public SimpleStringProperty javahomeProperty() {
        return javahome;
    }

    public void setJavahome(String javahome) {
        this.javahome.set(javahome);
    }

    public String getIp() {
        return ip.get();
    }

    public SimpleStringProperty ipProperty() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip.set(ip);
    }

    public String getPort() {
        return port.get();
    }

    public SimpleStringProperty portProperty() {
        return port;
    }

    public void setPort(String port) {
        this.port.set(port);
    }

    public NCSmartClientVO(SimpleStringProperty name, SimpleStringProperty javahome, SimpleStringProperty ip, SimpleStringProperty port) {
        this.name = name;
        this.javahome = javahome;
        this.ip = ip;
        this.port = port;
    }
    public NCSmartClientVO(String name, String javahome, String ip, String port) {
        this(new SimpleStringProperty(name),new SimpleStringProperty(javahome),new SimpleStringProperty(ip),new SimpleStringProperty(port));
    }

    public NCSmartClientVO(String name, String ip, String port) {
        this(new SimpleStringProperty(name),new SimpleStringProperty(null),new SimpleStringProperty(ip),new SimpleStringProperty(port));
    }

    @Override
    public String toString() {
        return "NCSmartClientVO{" +
                "name=" + name +
                ", javahome=" + javahome +
                ", ip=" + ip +
                ", port=" + port +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NCSmartClientVO that = (NCSmartClientVO) o;

        if (ip != null ? !ip.get().equals(that.ip.get()) : that.ip != null) return false;
        if (javahome != null ? !javahome.get().equals(that.javahome.get()) : that.javahome != null) return false;
        if (name != null ? !name.get().equals(that.name.get()) : that.name != null) return false;
        if (port != null ? !port.get().equals(that.port.get()) : that.port != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (javahome != null ? javahome.hashCode() : 0);
        result = 31 * result + (ip != null ? ip.hashCode() : 0);
        result = 31 * result + (port != null ? port.hashCode() : 0);
        return result;
    }
}

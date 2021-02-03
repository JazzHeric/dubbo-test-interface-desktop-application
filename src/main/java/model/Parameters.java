package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import tools.StringUtils;

import java.io.Serializable;

public class Parameters implements Serializable {
    private static final long serialVersionUID = -7207836539579721342L;

    private StringProperty paramType;

    private StringProperty paramValue;

    public String getParamType() {
        return typeProperty().get();
    }

    public void setParamType(String paramType) {
        if(StringUtils.isEmpty(paramType)) paramType = "";
        this.typeProperty().set(paramType);
    }

    public String getParamValue() {
        return valueProperty().get();
    }

    public void setParamValue(String paramValue) {
        if(StringUtils.isEmpty(paramValue)) paramValue = "";
        this.valueProperty().set(paramValue);
    }

    public StringProperty typeProperty(){
        if(paramType == null) paramType = new SimpleStringProperty(this, "paramType");
        return paramType;
    }

    public StringProperty valueProperty(){
        if(paramValue == null) paramValue = new SimpleStringProperty(this, "paramValue");
        return paramValue;
    }

    public Parameters(String paramType, String paramValue) {
        this.setParamType(paramType);
        this.setParamValue(paramValue);
    }

    public Parameters() {

    }

}

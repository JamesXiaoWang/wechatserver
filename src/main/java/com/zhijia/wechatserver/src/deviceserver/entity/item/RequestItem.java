package com.zhijia.wechatserver.src.deviceserver.entity.item;


import java.util.HashMap;
import java.util.List;

public class RequestItem {

    private String url;

    //body类型，form-data和其他
    private String bodyType;

    //分割线
    private String boundary;

    //文件保存地址
    private String filePath;

    //请求方法，post或get
    private String requestMethod;

    //请求头
    private HashMap<String,String> headerMap;

    //文本参数
    private HashMap<String,String> formFieldKeyValue;

    //文件参数
    private List<UploadFileItem> uploadFileItemList;

    //非formdata请求的body
    private String bodyText;

    public RequestItem() {
    }

    public RequestItem(String url,
                       String bodyType,
                       String boundary,
                       String filePath,
                       String requestMethod,
                       HashMap<String, String> headerMap,
                       HashMap<String, String> formFieldKeyValue,
                       List<UploadFileItem> uploadFileItemList,
                       String bodyText) {
        this.url = url;
        this.bodyType = bodyType;
        this.boundary = boundary;
        this.filePath = filePath;
        this.requestMethod = requestMethod;
        this.headerMap = headerMap;
        this.formFieldKeyValue = formFieldKeyValue;
        this.uploadFileItemList = uploadFileItemList;
        this.bodyText = bodyText;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getBodyType() {
        return bodyType;
    }

    public void setBodyType(String bodyType) {
        this.bodyType = bodyType;
    }


    public String getBoundary() {
        return boundary;
    }

    public void setBoundary(String boundary) {
        this.boundary = boundary;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }

    public HashMap<String, String> getHeaderMap() {
        return headerMap;
    }

    public void setHeaderMap(HashMap<String, String> headerMap) {
        this.headerMap = headerMap;
    }

    public HashMap<String, String> getFormFieldKeyValue() {
        return formFieldKeyValue;
    }

    public void setFormFieldKeyValue(HashMap<String, String> formFieldKeyValue) {
        this.formFieldKeyValue = formFieldKeyValue;
    }

    public List<UploadFileItem> getUploadFileItemList() {
        return uploadFileItemList;
    }

    public void setUploadFileItemList(List<UploadFileItem> uploadFileItemList) {
        this.uploadFileItemList = uploadFileItemList;
    }

    public String getBodyText() {
        return bodyText;
    }

    public void setBodyText(String bodyText) {
        this.bodyText = bodyText;
    }


}

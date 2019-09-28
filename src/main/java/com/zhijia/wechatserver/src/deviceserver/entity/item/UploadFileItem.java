package com.zhijia.wechatserver.src.deviceserver.entity.item;

import java.util.Arrays;

public class UploadFileItem {
    private String name;
    private String fileName;
    private String contentType;
    private byte[] fileBytes;

    public UploadFileItem(){};

    public UploadFileItem(String name, String fileName, String contentType, byte[] fileBytes) {
        this.name = name;
        this.fileName = fileName;
        this.contentType = contentType;
        this.fileBytes = fileBytes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public byte[] getFileBytes() {
        return fileBytes;
    }

    public void setFileBytes(byte[] fileBytes) {
        this.fileBytes = fileBytes;
    }

    @Override
    public String toString() {
        return "UploadFileItem{" +
                "name='" + name + '\'' +
                ", fileName='" + fileName + '\'' +
                ", contentType='" + contentType + '\'' +
                ", fileBytes=" + Arrays.toString(fileBytes) +
                '}';
    }
}

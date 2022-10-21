package com.laurencetuchin.employeesystemapi.dto;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class ErrorDTO {

    public String title; // shows briefly what error is
    public String detail;
    public int status;
    public String errorType;
    public String errorCode;

    public String timestamp = ZonedDateTime.now().format(DateTimeFormatter.ofPattern("yyy-MM-dd hh:mm:ss a z Z"));


    public ErrorDTO(String title, String detail, int status, String errorType, String errorCode) {
        this.title = title;
        this.detail = detail;
        this.status = status;
        this.errorType = errorType;
        this.errorCode = errorCode;
    }

    public static ErrorDTOBuilder builder(){
        return new ErrorDTOBuilder();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getErrorType() {
        return errorType;
    }

    public void setErrorType(String errorType) {
        this.errorType = errorType;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public static class ErrorDTOBuilder {
        public String title;
        public String detail;
        public int status;
        public String errorType;
        public String errorCode;

        public ErrorDTOBuilder withTitle(final String title) {
            this.title = title;
            return this;
        }

        public ErrorDTOBuilder withDetail(final String detail) {
            this.detail = detail;
            return this;
        }

        public ErrorDTOBuilder withStatus(final int status) {
            this.status = status;
            return this;
        }

        public ErrorDTOBuilder withErrorType(final String errorType){
            this.errorType = errorType;
            return this;
        }

        public ErrorDTOBuilder withErrorCode(final String errorCode){
            this.errorCode = errorCode;
            return this;
        }

        public ErrorDTO build() {
            return new ErrorDTO(title, detail, status, errorType, errorCode);
        }


    }

}

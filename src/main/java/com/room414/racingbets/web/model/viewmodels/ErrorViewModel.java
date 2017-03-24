package com.room414.racingbets.web.model.viewmodels;

import com.room414.racingbets.web.model.enums.ErrorCode;

import java.io.Serializable;

/**
 * @author Alexander Melashchenko
 * @version 1.0 24 Mar 2017
 */
public class ErrorViewModel implements Serializable {
    private static final long serialVersionUID = -9185930142812556682L;

    /**
     * Error code. Unique string identifying the type of error
     */
    private ErrorCode code;
    /**
     * Localized user readable message describing the error
     */
    private String message;
    /**
     * Identifies the type of object that the error is related to.
     * This is typically a resource type that is part of the response
     */
    private String objectName;
    /**
     * Identifies the property of the object that the error is related to.
     * Primarily for binding and validation errors, but sometimes used for
     * business logic errors too
     */
    private String propertyName;

    public ErrorCode getCode() {
        return code;
    }

    public void setCode(ErrorCode code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ErrorViewModel that = (ErrorViewModel) o;

        if (code != that.code) return false;
        if (message != null ? !message.equals(that.message) : that.message != null) return false;
        if (objectName != null ? !objectName.equals(that.objectName) : that.objectName != null) return false;
        return propertyName != null ? propertyName.equals(that.propertyName) : that.propertyName == null;
    }

    @Override
    public int hashCode() {
        int result = code != null ? code.hashCode() : 0;
        result = 31 * result + (message != null ? message.hashCode() : 0);
        result = 31 * result + (objectName != null ? objectName.hashCode() : 0);
        result = 31 * result + (propertyName != null ? propertyName.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ErrorViewModel{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", objectName='" + objectName + '\'' +
                ", propertyName='" + propertyName + '\'' +
                '}';
    }
}

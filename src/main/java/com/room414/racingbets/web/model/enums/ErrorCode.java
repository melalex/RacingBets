package com.room414.racingbets.web.model.enums;

/**
 * Enum that represents response error codes
 *
 * @author Alexander Melashchenko
 * @version 1.0 24 Mar 2017
 */
public enum ErrorCode {
    /**
     * Typically this is when the item id you passed in was not found.
     * For example, passing in a non-existent master tariff id.
     */
    OBJECT_NOT_FOUND("ObjectNotFound"),
    /**
     * A required object was not passed in on the request. Check the objectName
     * and propertyName for more information.
     */
    NOT_NULL("NotNull"),
    /**
     * An argument passed in did not meet validation requirements.
     * This may or may not be related to the request parameters passed in.
     */
    INVALID_ARGUMENT("InvalidArgument"),
    /**
     * The current state of the object in question prevents the requested
     * action from being performed.
     */
    INVALID_STATE("InvalidState"),
    /**
     * An argument was not of the expected type. This may or may not be
     * related to the request parameters passed in.
     */
    TYPE_MISMATCH("TypeMismatch"),
    /**
     * An ID (usually a masterTariffId) is required to process the request,
     * but none was supplied.
     */
    MISSING_ID("MissingId"),
    /**
     * The data sent in was not valid. You could see this error when
     * trying to overwrite a value that cannot be overwritten.
     */
    DATA_INTEGRITY_VIOLATION_EXCEPTION("DataIntegrityViolationException"),
    /**
     * The file format sent in was invalid.
     */
    INVALID_FILE_FORMAT("InvalidFileFormat"),
    /**
     * The supplied value is not valid.
     */
    INVALID_ERROR("InvalidError"),
    /**
     * There was an error saving the object.
     */
    OBJECT_CANNOT_BE_SAVED("ObjectCannotBeSaved"),
    /**
     * The supplied credentials do not have permission to access this resource.
     */
    INSUFFICIENT_PERMISSIONS("InsufficientPermissions"),
    /**
     * Unexpected exception
     */
    SYSTEM_ERROR("SystemError"),
    /**
     * You can only have one object with this ID. Usually seen when trying
     * to create a new object with a providerAccountId or providerProfileId that already exists.
     */
    UNIQUENESS_VIOLATION_ERROR("UniquenessViolationError"),
    /**
     * The date range is not valid. Usually caused by a fromDateTime being after a toDateTime.
     */
    INVALID_DATE_RANGE("InvalidDateRange"),
    /**
     * The date range is not valid. Usually caused by a fromDateTime being after a toDateTime.
     */
    INVALID_REQUEST_BODY("InvalidRequestBody");

    private String name;

    ErrorCode(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static ErrorCode getErrorCode(String name) {
        for(ErrorCode v : values()) {
            if (v.getName().equalsIgnoreCase(name)) {
                return v;
            }
        }
        throw new IllegalArgumentException("There is no ErrorCode named " + name);
    }

}

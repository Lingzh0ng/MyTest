package io.appium.uiautomator2.server;

public enum HttpStatusCode {

    OK(200, "OK"),
    CREATED(201, "Created"),
    ACCEPTED(202, "Accepted"),
    MULTIPLE_CHOICES(300, "Multiple Choices"),
    MOVED_PERMANENTLY(301, "Moved Permanently"),
    FOUND(302, "Found"),
    SEE_OTHER(303, "See Other (since HTTP/1.1)"),
    NOT_MODIFIED(304, "Not Modified"),
    USE_PROXY(305, "Use Proxy (since HTTP/1.1)"),
    SWITCH_PROXY(306, "Switch Proxy"),
    BAD_REQUEST(400, "Bad Request"),
    UNAUTHORIZED(401, "Unauthorized"),
    PAYMENT_REQUIRED(402, "Payment Required"),
    FORBIDDEN(403, "Forbidden"),
    NOT_FOUND(404, "Not Found"),
    METHOD_NOT_ALLOWED(405, "Method Not Allowed"),
    NOT_ACCEPTABLE(406, "Not Acceptable"),
    PROXY_AUTHENTICATION_REQUIRED(407, "Proxy Authentication Required"),
    REQUEST_TIMEOUT(408, "Request Timeout"),
    INTERNAL_SERVER_ERROR(500, "Internal Server Error"),
    NOT_IMPLEMENTED(501, "Not Implemented"),
    BAD_GATEWAY(502, "Bad Gateway"),
    SERVICE_UNAVAILABLE(503, "Service Unavailable");

    private int statusCode;
    private String desc;


    HttpStatusCode(int statusCode, String desc) {
        this.statusCode = statusCode;
        this.desc = desc;
    }

    /**
     * Gets the HTTP status code
     *
     * @return the status code number
     */
    public int getStatusCode() {
        return statusCode;
    }

    /**
     * Get the description
     *
     * @return the description of the status code
     */
    public String getDesc() {
        return desc;
    }

}

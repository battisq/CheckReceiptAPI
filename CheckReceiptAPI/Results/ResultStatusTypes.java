package CheckReceiptAPI.Results;

public enum ResultStatusTypes {

    //region Registration
    CONFLICT (409, "user exists"),
    INTERNAL_SERVER_ERROR(500, "invalid phone number"),
    BAD_REQUEST_REGISTRATION(400, "Invalid email address"),
    //endregion

    //region Restore password
    NO_CONTENT_RESTORE_PASSWORD(204, "phone number found"),
    NOT_FOUND(404, "the user was not found"),
    //endregion

    //region Checking exist check
    NO_CONTENT_CHECKING_EXIST_CHECK(204, "check found"),
    BAD_REQUEST_CHECKING_EXIST_CHECK(400, "Missing required property <date/sum>"),
    //endregion

    //region Other
    OK(200, ""),
    FORBIDDEN(403, "the user was not found or the specified password was not correct"),
    NOT_ACCEPTABLE(406, "check not found or <date / amount> is incorrect or does not match the " +
            "<date / amount> indicated in the check (or check exists, but an incorrect response from the server)"),
    ACCEPTED(202, "before calling this method, there was no check for the existence of a check " +
            "(CheckReceiptSDK.FNS.checkReceipt())");
    //endregion

    private int code;
    private String message;

    ResultStatusTypes(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public static ResultStatusTypes isType (int code) {

        ResultStatusTypes[] res = ResultStatusTypes.values();

        for (ResultStatusTypes ress: res) {
            if(ress.code == code)
                return ress;
        }

        throw new IllegalArgumentException("The code does not belong to a set of enum");
    }
}

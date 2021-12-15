package erfan.codes.bookshop.enums;

public enum Return_Status_Codes implements IReturn_Status_Codes {
    UNDEFINED(0, "undefined"),
    OK_VALID_FORM(100, "ok_validForm"),
    BARCODE_CHECK_ERROR(500, "barcode should be checked , sorry"),
    CHECK_BARCODE(400, "no data found with provided barcode"),
    INVALID_SUBSCRIBER_INFORMATION(400, "the inserted data is not complete or valid , please check"),
    LOGIN_CREDENTIAL_NOT_VALID(400, "the credentials provided for login are wrong"),
    SC_FORBIDDEN(403, "the user did not provide the sessionId to login into the system"),
    SESSION_NO_LONGER_VALID(403, "sessionId is no longer valid"),
    INVALID_BOOK_INFORMATION(400, "the book data is not valid , please check"),
    NO_BOOK_FOUND_FOR_SUBSCRIBER(200, "no book added by this subscriber"),
    NO_BOOK_FOUND(400, "no book found with this id"),
    INVALID_ADMIN_INFORMATION(400, "should enter admin information correctly!");

    private String messageKey;
    private int statusCode;

    private Return_Status_Codes(int statusCode, String messageKey) {
        this.statusCode = statusCode;
        this.messageKey = messageKey;
    }

    public static Return_Status_Codes get(int status) {
        if (status == 0) {
            return UNDEFINED;
        } else {
            Return_Status_Codes[] arr$ = values();
            Return_Status_Codes[] var2 = arr$;
            int var3 = arr$.length;

            for (int var4 = 0; var4 < var3; ++var4) {
                Return_Status_Codes val = var2[var4];
                if (val.statusCode == status) {
                    return val;
                }
            }

            return UNDEFINED;
        }
    }


    @Override
    public String getMessageKey() {
        return this.messageKey;
    }

    @Override
    public int getStatus() {
        return this.statusCode;
    }
}

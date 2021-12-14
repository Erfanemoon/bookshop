package erfan.codes.bookshop.general.common.global;

import erfan.codes.bookshop.general.General;

public enum SessionType {
    Undefined(""),
    Subscribers("subscribers"),
    ADMINS("admins");

    private String value;

    SessionType(String value) {
        this.value = value;
    }

    public static SessionType get(String value) {

        if (General.isEmpty(value)) {
            return Undefined;
        }

        SessionType[] values = values();
        for (SessionType val : values) {
            if (val.value.equals(value)) {
                return val;
            }
        }

        return Undefined;
    }

    public String getValue() {
        return value;
    }
}

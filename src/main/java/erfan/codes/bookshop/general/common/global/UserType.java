package erfan.codes.bookshop.general.common.global;

import erfan.codes.bookshop.general.General;

public enum UserType {

    Undefined(""),
    Subscribers("subscriber"),
    ADMIN("admin");

    private String value;

    UserType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }


    public static UserType get(String value) {
        if (General.isEmpty(value)) {
            return Undefined;
        }

        UserType[] values = values();
        for (UserType val : values) {
            if (val.value.equals(value)) {
                return val;
            }
        }

        return Undefined;
    }
}

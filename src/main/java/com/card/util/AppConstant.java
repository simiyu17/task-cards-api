package com.card.util;

public enum AppConstant {

    ADMIN_ROLE("ROLE_ADMIN"),
    MEMBER_ROLE("ROLE_MEMBER");

    private final String name;
    public static final String TASK_CARD_ID_PATH_PARAM = "card-id";
    public static final String DEFAULT_ADMIN_USER_EMAIL_ADDRESS = "admin.admin@yahoo.com";
    public static final String DEFAULT_ADMIN_USER_PASSWORD = "admin@123";
    public static final String DEFAULT_MEMBER_USER_EMAIL_ADDRESS = "member.member@yahoo.com";
    public static final String DEFAULT_MEMBER_USER_PASSWORD = "member@123";
    public static final String DEFAULT_TEST_USER_EMAIL_ADDRESS = "test.test@yahoo.com";
    public static final String DEFAULT_TEST_USER_PASSWORD = "test@123";
    public static final String LOGIN_SUCCESS_MESSAGE = "Login Successful";
    AppConstant(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

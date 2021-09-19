package ng.com.codetrik.notepad.user;

public enum Permission {
    READ("auth:read"),
    WRITE("auth:write"),
    UPDATE("auth:update"),
    DELETE("auth:delete"),
    SPECIAL("auth:special");

    private final String authority;

    Permission(String authority){
        this.authority = authority;
    }
}

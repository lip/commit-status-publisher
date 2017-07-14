package jetbrains.buildServer.commitPublisher.slack;

public enum SlackActionType {
    BUTTON("button"),
    SELECT("select");

    private String code;

    SlackActionType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

}

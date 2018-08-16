package Models.CONS;

public enum Errors {
    HIGHERSERVICE_ERROR("DB connection was successful, but cant find anything by Id or crud is not working properly");

    Errors(String Message) {
        message = Message;
    }

    private final String message;

    public String get() {
        return message;
    }
}

package Models.DTO;

import java.sql.Connection;

public class ConnectionDTO {
    public boolean success;
    public String message;
    public Connection connection;

    public ConnectionDTO(boolean Success, String Message, java.sql.Connection Connection) {
        success = Success;
        message = Message;
        connection = Connection;
    }
}

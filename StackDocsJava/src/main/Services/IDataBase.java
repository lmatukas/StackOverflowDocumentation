package Services;

import Models.DTO.ConnectionDTO;

public interface IDataBase {
    ConnectionDTO getConnection();
    ConnectionDTO closeConnection();
}

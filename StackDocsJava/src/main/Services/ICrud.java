package Services;

import Models.DBQueryModel;
import Models.DTO.DBqueryDTO;

public interface ICrud {
    DBqueryDTO create(Object object);
    <T> DBqueryDTO<T> read(DBQueryModel dbQuery, Class<T> dalType);
    DBqueryDTO update(Object dal, String primaryKey);
    DBqueryDTO delete(DBQueryModel deleteModel, Class dal);
}

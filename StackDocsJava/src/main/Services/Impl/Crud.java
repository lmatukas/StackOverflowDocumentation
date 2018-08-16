package Services.Impl;

import Models.DBQueryModel;
import Models.DTO.ConnectionDTO;
import Models.DTO.DBqueryDTO;
import Services.ICrud;
import Services.IDataBase;
import Services.QueryBuilder;
import com.google.inject.Inject;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Crud implements ICrud {

    private Statement statement;
    private IDataBase db;
    private DBqueryDTO dto;
    private ConnectionDTO connDTO;

    @Inject
    public Crud(IDataBase iDB){
        db = iDB;
    }

    @Override
    public DBqueryDTO create(Object object) {
        try {
            connDTO = db.getConnection();
            if (connDTO.success) {
                statement = connDTO.connection.createStatement();
                statement.executeUpdate(createInsertQuery(object));
                dto = new DBqueryDTO(true, "", null);
            } else {
                dto = new DBqueryDTO(false, connDTO.message, null);
            }
        } catch (Exception e) {
            dto =  new DBqueryDTO(false, e.getMessage(), null);
        } finally {
            db.closeConnection();
        }
        return dto;
    }

    @Override
    public <T> DBqueryDTO<T> read(DBQueryModel dbQuery, Class<T> dalType) {
        try {
            connDTO = db.getConnection();
            if (connDTO.success) {
                statement = connDTO.connection.createStatement();
                ResultSet rs  = statement
                        .executeQuery(new QueryBuilder(getClassNameWithoutDAL(dalType))
                        .buildQuery(dbQuery, "read")
                        .getQuery());
                List<T> rows = new ArrayList<>();
                while (rs.next()) {
                    T dal = dalType.newInstance();
                    loadResultSetIntoObject(rs, dal);
                    rows.add(dal);
                }
                dto = new DBqueryDTO<>(true, "", rows);
            } else {
                dto = new DBqueryDTO(false, connDTO.message, null);
            }
        } catch (Exception e) {
            dto = new DBqueryDTO(false, e.getMessage(), null);
        } finally {
             db.closeConnection();
        }
        return dto;
    }

    @Override
    public DBqueryDTO update(Object dal, String primaryKey) {
        try {
            connDTO = db.getConnection();
            if (connDTO.success) {
                PreparedStatement stmt = createUpdatePreparedStatement(dal,
                        getClassNameWithoutDAL(dal.getClass()), primaryKey);
                stmt.executeUpdate();
                dto = new DBqueryDTO(true, "", null);
            } else {
                dto = new DBqueryDTO(false, connDTO.message, null);
            }
        } catch (Exception e) {
            dto = new DBqueryDTO(false, e.getMessage(), null);
        } finally {
             db.closeConnection();
        }
        return dto;
    }

    @Override
    public DBqueryDTO delete(DBQueryModel deleteModel, Class dal) {
        try {
            connDTO = db.getConnection();
            if (connDTO.success) {
                statement = connDTO.connection.createStatement();
                statement.executeUpdate(new QueryBuilder(getClassNameWithoutDAL(dal))
                        .buildQuery(deleteModel, "delete")
                        .getQuery());
                dto = new DBqueryDTO(true, "", null);
            } else {
                dto = new DBqueryDTO(false, connDTO.message, null);
            }
        } catch (Exception e) {
            dto = new DBqueryDTO(false, e.getMessage(), null);
        } finally {
            db.closeConnection();
        }
        return dto;
    }

    private void loadResultSetIntoObject(ResultSet rst, Object object)
            throws IllegalArgumentException, IllegalAccessException, SQLException {
        Class<?> zclass = object.getClass();
        for(Field field : zclass.getDeclaredFields()) {
            String name = field.getName();
            field.setAccessible(true);
            Object value = rst.getObject(name);
            Class<?> type = field.getType();
            if (isPrimitive(type)) {
                Class<?> boxed = boxPrimitiveClass(type);
                if (type == boolean.class) {
                    value = (int) value == 1;
                } else {
                    value = boxed.cast(value);
                }
            }
            field.set(object, value);
        }
    }

    private String createInsertQuery(Object object)
            throws IllegalArgumentException, IllegalAccessException {
        Class<?> zclass = object.getClass();
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO ")
                .append(getClassNameWithoutDAL(zclass))
                .append(" VALUES (");
        Field[] fields = zclass.getDeclaredFields();
        for(int i = 0; i < fields.length; i++) {
            fields[i].setAccessible(true);
            if (fields[i].getName().equals("Id")) {
                sb.append("null");
            } else {
                sb.append(quoteIdentifier(fields[i].get(object).toString()));
            }
            if (i != fields.length - 1) {
                sb.append(",");
            } else {
                sb.append(")");
            }
        }
        return sb.toString();
    }

    private PreparedStatement createUpdatePreparedStatement(Object object,
                                                                  String tableName, String primaryKey) {
        PreparedStatement stmt;
        try {
            Class<?> zclass = object.getClass();
            String Sql = createUpdateQuery(zclass, tableName, primaryKey);
            stmt = connDTO.connection.prepareStatement(Sql);
            Field[] fields = zclass.getDeclaredFields();
            int pkSequence = fields.length;
            for(int i = 0; i < fields.length; i++) {
                Field field = fields[i];
                field.setAccessible(true);
                Object value = field.get(object);
                String name = field.getName();
                if(name.equals(primaryKey)) {
                    stmt.setObject(pkSequence, value);
                } else {
                    stmt.setObject(i, value);
                }
            }
        }
        catch(Exception e){
            throw new RuntimeException("Unable to create PreparedStatement: " + e.getMessage(), e);
        }
        return stmt;
    }

    private String createUpdateQuery(Class<?> zclass, String tableName, String primaryKey) {
        StringBuilder sets = new StringBuilder();
        String where = null;
        for (Field field : zclass.getDeclaredFields()) {
            String name = field.getName();
            String pair = quoteIdentifier(name) + " = ?";
            if (name.equals(primaryKey)) {
                where = " WHERE " + pair;
            } else {
                if (sets.length()>1) {
                    sets.append(", ");
                }
                sets.append(pair);
            }
        }
        if (where == null) {
            throw new IllegalArgumentException("Primary key not found in '" + zclass.getName() + "'");
        }
        return "UPDATE " + tableName + " SET " + sets.toString() + where;
    }

    private String quoteIdentifier(String value) {
        return "\"" + value + "\"";
    }

    private boolean isPrimitive(Class<?> type)
    {
        return (type == int.class || type == long.class ||
                type == double.class  || type == float.class
                || type == boolean.class || type == byte.class
                || type == char.class || type == short.class);
    }

    private Class<?> boxPrimitiveClass(Class<?> type)
    {
        if (type == int.class){return Integer.class;}
        else if (type == long.class){return Long.class;}
        else if (type == double.class){return Double.class;}
        else if (type == float.class){return Float.class;}
        else if (type == boolean.class){return Boolean.class;}
        else if (type == byte.class){return Byte.class;}
        else if (type == char.class){return Character.class;}
        else if (type == short.class){return Short.class;}
        else
        {
            throw new IllegalArgumentException("Class '" + type.getName() + "' is not a primitive");
        }
    }

    private String getClassNameWithoutDAL(Class c) {
        return c.getSimpleName().replace("DAL", "");
    }
}

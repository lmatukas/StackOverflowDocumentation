package Services;

import Models.DBQueryModel;

public class QueryBuilder {
    private StringBuilder sb = new StringBuilder();
    private String tableName;

    public QueryBuilder(String tn) {
        tableName = tn;
    }

    public QueryBuilder buildQuery(DBQueryModel queryModel, String readOrDelete) {
        if (tableName == null || tableName.isEmpty()) {
            throw new IllegalArgumentException("Missing table name!");
        }

        if (readOrDelete == null || readOrDelete.isEmpty()) {
            throw new IllegalArgumentException("Cannot build a query without specifying Read or Delete operation.");
        }

        sb.setLength(0);

        switch (readOrDelete.toUpperCase()) {
            case "READ":
                sb.append("SELECT * FROM ").append(tableName).append(" WHERE 1 = 1");
                break;
            case "DELETE":
                sb.append("DELETE FROM ").append(tableName).append(" WHERE 1 = 1");
                break;
            default:
                throw new IllegalArgumentException("Please specify either Read or Delete as third argument.");
        }

        if (queryModel.where != null && queryModel.whereValue != null) {
            return whereClause(queryModel);
        }

        return this;
    }

    public QueryBuilder whereClause(DBQueryModel queryModel) {
        String[] values = queryModel.whereValue;
        sb.append(" AND ").append(queryModel.where).append(" IN (");
        for (int i = 0; i < values.length; i++) {
            sb.append("'").append(values[i]).append("'");
            // We check if it's the last value if it is we omit the ','
            if (i != values.length - 1) {
                sb.append(",");
            }
        }
        sb.append(")");
        return this;
    }

    public QueryBuilder setTableName(String tn) {
        tableName = tn;
        return this;
    }

    public String getQuery(){
        return sb.toString();
    }

}

package jp.niro.jimcon.dbaccess;

import java.util.List;

/**
 * Created by niro on 2017/04/13.
 */
public class QueryBuilder {
    private StringBuilder builder;

    private QueryBuilder() {
        builder = new StringBuilder();
    }

    QueryBuilder(StringBuilder builder) {
        this.builder = builder;
    }

    public static QueryBuilder create() {
        return new QueryBuilder();
    }

    public QueryBuilder select(String columnName) {
        builder.append("SELECT ").append(columnName);
        return new QueryBuilder(builder);
    }

    public QueryBuilder select(String[] columnNames) {
        builder.append("SELECT ").append(setMultipleData(columnNames));
        return new QueryBuilder(builder);
    }

    public QueryBuilder select(ColumnNameList list) {
        builder.append("SELECT ").append(list.enumerate(", ","",""));
        return new QueryBuilder(builder);
    }

    public QueryBuilder insert(String tableName) {
        builder.append("INSERT INTO ").append(tableName);
        return new QueryBuilder(builder);
    }

    public QueryBuilder insert(String tableName, String[] columnNames) {
        builder
                .append("INSERT INTO ").append(tableName)
                .append(" ( ").append(setMultipleData(columnNames)).append(" )");
        return new QueryBuilder(builder);
    }

    public QueryBuilder insert(String tableName, DataPairList list) {
        builder
                .append("INSERT INTO ")
                .append(tableName)
                .append(" ( ").append(setColumnNameData(list.getDataPairList())).append(" )")
                .append(" VALUES ( ").append(setValueData(list.getDataPairList())).append(" )");;

        return new QueryBuilder(builder);
    }

    public QueryBuilder values(String data) {
        builder.append(" VALUES ( ").append(data).append(" )");
        return new QueryBuilder(builder);
    }

    public QueryBuilder values(String[] dataSet) {
        builder.append(" VALUES ( ").append(setMultipleData(dataSet)).append(" )");
        return new QueryBuilder(builder);
    }

    public QueryBuilder update(String tableName, DataPairList list) {
        builder.append(" UPDATE ").append(tableName)
                .append(" SET ");
        for (DataPair pair: list.getDataPairList()) {
            builder
                    .append(pair.getColumnName())
                    .append(" = ")
                    .append(pair.getValue())
                    .append(" , ");
        }
        builder.delete(builder.lastIndexOf(" , "), builder.length());
        return new QueryBuilder(builder);
    }

    public QueryBuilder update(String tableName, String columnName, String data) {
        builder.append(" UPDATE ").append(tableName)
                .append(" SET ").append(columnName).append(" = '").append(data).append("'");
        return new QueryBuilder(builder);
    }

    public QueryBuilder update(String tableName, String columnName, int data) {
        builder.append(" UPDATE ").append(tableName)
                .append(" SET ").append(columnName).append(" = ").append(data);
        return new QueryBuilder(builder);
    }

    public QueryBuilder update(String tableName, String columnName, long data) {
        builder.append(" UPDATE ").append(tableName)
                .append(" SET ").append(columnName).append(" = ").append(data);
        return new QueryBuilder(builder);
    }

    public QueryBuilder update(String tableName, String columnName, double data) {
        builder.append(" UPDATE ").append(tableName)
                .append(" SET ").append(columnName).append(" = ").append(data);
        return new QueryBuilder(builder);
    }

    public QueryBuilder addSet(String columnName, String data) {
        builder.append(" , ").append(columnName).append(" = '").append(data).append("'");
        return new QueryBuilder(builder);
    }

    public QueryBuilder addSet(String columnName, boolean data) {
        builder.append(" , ").append(columnName).append(" = ").append(String.valueOf(data));
        return new QueryBuilder(builder);
    }

    public QueryBuilder addSet(String columnName, int data) {
        builder.append(" , ").append(columnName).append(" = ").append(data);
        return new QueryBuilder(builder);
    }

    public QueryBuilder addSet(String columnName, long data) {
        builder.append(" , ").append(columnName).append(" = ").append(data);
        return new QueryBuilder(builder);
    }

    public QueryBuilder addSet(String columnName, double data) {
        builder.append(" , ").append(columnName).append(" = ").append(data);
        return new QueryBuilder(builder);
    }

    public QueryBuilder deleteFrom(String tableName) {
        builder.append("DELETE FROM ").append(tableName);
        return new QueryBuilder(builder);
    }

    public QueryBuilder from(String tableName) {
        builder.append(" FROM ").append(tableName);
        return new QueryBuilder(builder);
    }

    public QueryComparisonOperation where(String columnName) {
        builder.append(" WHERE ").append(columnName);
        return new QueryComparisonOperation(builder);
    }

    public QueryBuilder whereClause(String whereClause) {
        builder.append(" WHERE ").append(whereClause);
        return new QueryBuilder(builder);
    }

    public QueryBuilder whereAnd(DataPairList list) {
        builder.append(" WHERE ");
        for (DataPair pair: list.getDataPairList()) {
            builder
                    .append(pair.getColumnName())
                    .append(" = ")
                    .append(pair.getValue())
                    .append(" AND ");
        }
        builder.delete(builder.lastIndexOf(" AND "), builder.length());
        return new QueryBuilder(builder);
    }

    public QueryBuilder whereOr(DataPairList list) {
        builder.append(" WHERE ");
        for (DataPair pair: list.getDataPairList()) {
            builder
                    .append(pair.getColumnName())
                    .append(" = ")
                    .append(pair.getValue())
                    .append(" OR ");
        }
        builder.delete(builder.lastIndexOf(" OR "), builder.length());
        return new QueryBuilder(builder);
    }

    public QueryComparisonOperation and(String columnName) {
        builder.append(" AND ").append(columnName);
        return new QueryComparisonOperation(builder);
    }

    public QueryBuilder and(DataPairList list) {
        builder.append(" AND ( ");
        for (DataPair pair: list.getDataPairList()) {
            builder
                    .append(pair.getColumnName())
                    .append(" = ")
                    .append(pair.getValue())
                    .append(" OR ");
        }
        builder.delete(builder.lastIndexOf(" OR "), builder.length());
        builder.append(" )");
        return new QueryBuilder(builder);
    }

    public QueryComparisonOperation or(String columnName) {
        builder.append(" OR ").append(columnName);
        return new QueryComparisonOperation(builder);
    }

    public QueryBuilder or(DataPairList list) {
        builder.append(" OR ( ");
        for (DataPair pair: list.getDataPairList()) {
            builder
                    .append(pair.getColumnName())
                    .append(" = ")
                    .append(pair.getValue())
                    .append(" OR ");
        }
        builder.deleteCharAt(builder.lastIndexOf(" OR "));
        builder.append(" )");
        return new QueryBuilder(builder);
    }

    public QueryBuilder orderByASC(String columnName) {
        builder.append(" ORDER BY ").append(columnName).append(" ASC");
        return new QueryBuilder(builder);
    }

    public QueryBuilder orderByDESC(String columnName) {
        builder.append(" ORDER BY ").append(columnName).append(" DESC");
        return new QueryBuilder(builder);
    }

    public QueryBuilder groupBy(String columnName) {
        builder.append(" GROUP BY ").append(columnName);
        return new QueryBuilder(builder);
    }

    public QueryComparisonOperation havingCount(String columnName) {
        builder.append(" HAVING COUNT(").append(columnName).append(")");
        return new QueryComparisonOperation(builder);
    }

    public QueryBuilder append(String text) {
        builder.append(text);
        return new QueryBuilder(builder);
    }

    public String terminate() {
        return builder.append(";").toString();
    }

    @Override
    public String toString() {
        return builder.toString();
    }

    private String setMultipleData(String[] dataSet) {
        StringBuilder builder = new StringBuilder();
        for (String data : dataSet) {
            builder.append(data).append(", ");
        }
        builder.deleteCharAt(builder.lastIndexOf(", "));
        return builder.toString();
    }

    private String setColumnNameData(List<DataPair> list) {
        StringBuilder builder = new StringBuilder();
        for (DataPair data : list) {
            builder.append(data.getColumnName()).append(", ");
        }
        builder.deleteCharAt(builder.lastIndexOf(", "));
        return builder.toString();
    }

    private String setValueData(List<DataPair> list){
        StringBuilder builder = new StringBuilder();
        for (DataPair data : list) {
            builder.append(data.getValue()).append(", ");
        }
        builder.deleteCharAt(builder.lastIndexOf(", "));
        return builder.toString();
    }
}

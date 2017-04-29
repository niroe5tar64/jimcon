package jp.niro.jimcon.sql;

/**
 * Created by niro on 2017/04/13.
 */
public class QueryComparisonOperation {
    private StringBuilder builder;
    private static final String GREATER_THAN_OR_EQUAL_TO = " >= ";
    private static final String GREATER_THAN = " > ";
    private static final String LESS_THAN_OR_EQUAL_TO = " <= ";
    private static final String LESS_THAN = " < ";
    private static final String EQUAL_TO = " = ";
    private static final String LIKE = " Like ";


    QueryComparisonOperation(StringBuilder builder) {
        this.builder = builder;
    }

    private QueryBuilder set(String operator, String x) {
        return new QueryBuilder(builder.append(operator).append(x));
    }

    private QueryBuilder set(String operator, int x) {
        return new QueryBuilder(builder.append(operator).append(x));
    }

    private QueryBuilder set(String operator, double x) {
        return new QueryBuilder(builder.append(operator).append(x));
    }

    public QueryBuilder isGreaterThanOrEqualTo(int x) {
        return set(GREATER_THAN_OR_EQUAL_TO, x);
    }

    public QueryBuilder isGreaterThanOrEqualTo(double x) {
        return set(GREATER_THAN_OR_EQUAL_TO, x);
    }

    public QueryBuilder isGreaterThanOrEqualTo(String x) {
        return set(GREATER_THAN_OR_EQUAL_TO, x);
    }

    public QueryBuilder isGreaterThan(int x) {
        return set(GREATER_THAN, x);
    }

    public QueryBuilder isGreaterThan(double x) {
        return set(GREATER_THAN, x);
    }

    public QueryBuilder isGreaterThan(String x) {
        return set(GREATER_THAN, x);
    }

    public QueryBuilder isLessThanOrEqualTo(int x) {
        return set(LESS_THAN_OR_EQUAL_TO, x);
    }

    public QueryBuilder isLessThanOrEqualTo(double x) {
        return set(LESS_THAN_OR_EQUAL_TO, x);
    }

    public QueryBuilder isLessThanOrEqualTo(String x) {
        return set(LESS_THAN_OR_EQUAL_TO, x);
    }

    public QueryBuilder isLessThan(int x) {
        return set(LESS_THAN, x);
    }

    public QueryBuilder isLessThan(double x) {
        return set(LESS_THAN, x);
    }

    public QueryBuilder isLessThan(String x) {
        return set(LESS_THAN, x);
    }


    public QueryBuilder isEqualTo(int x) {
        return set(EQUAL_TO, x);
    }

    public QueryBuilder isEqualTo(double x) {
        return set(EQUAL_TO, x);
    }

    public QueryBuilder isEqualTo(String x) {
        return set(EQUAL_TO, x);
    }

    public QueryBuilder isTrue(){
        return set(EQUAL_TO, "true");
    }
    public QueryBuilder isFalse(){
        return set(EQUAL_TO, "false");
    }

    public QueryBuilder in(ValueList list) {
        return new QueryBuilder(builder.append(" IN ").append(list.enumerate(", ","(",")")));
    }

    public QueryBuilder between(int a, int b) {
        return new QueryBuilder(builder
                .append(" Between ").append(a).append(" And ").append(b));
    }

    public QueryBuilder between(double a, double b) {
        return new QueryBuilder(builder
                .append(" Between ").append(a).append(" And ").append(b));
    }

    public QueryBuilder between(String a, String b) {
        return new QueryBuilder(builder
                .append(" Between ").append(a).append(" And ").append(b));
    }

    public QueryBuilder isLike(String x) {
        return set(LIKE, x);
    }
}

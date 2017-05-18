package jp.niro.jimcon.flowlistview;

/**
 * Created by niro on 2017/05/17.
 */
public interface Cellable<E> {
    void updateItem(E item, boolean empty);
}

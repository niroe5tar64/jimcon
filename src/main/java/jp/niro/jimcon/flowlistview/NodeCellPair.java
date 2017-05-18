package jp.niro.jimcon.flowlistview;

import javafx.scene.Node;

/**
 * Created by niro on 2017/05/17.
 */
public class NodeCellPair<E> {

    private final Node node;

    private final Cellable<E> cell;

    public NodeCellPair(Node node, Cellable<E> cell) {
        this.node = node;
        this.cell = cell;
    }

    public Node getNode() {
        return node;
    }

    public Cellable<E> getCell() {
        return cell;
    }

}

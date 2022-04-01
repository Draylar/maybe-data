package draylar.maybedata.data;

public class MaybeLootTable {

    private Condition condition = null;

    public MaybeLootTable(Condition condition) {
        this.condition = condition;
    }

    public Condition getMaybeCondition() {
        return condition;
    }
}

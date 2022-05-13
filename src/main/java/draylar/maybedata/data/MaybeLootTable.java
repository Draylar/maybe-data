package draylar.maybedata.data;

public class MaybeLootTable {

    private final Condition condition;

    public MaybeLootTable(Condition condition) {
        this.condition = condition;
    }

    public Condition getMaybeCondition() {
        return condition;
    }
}

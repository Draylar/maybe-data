package draylar.maybedata.data;

import net.minecraft.recipe.Recipe;

public class ConditionalRecipe {

    private Condition condition;
    private Recipe<?> recipe;

    public Condition getCondition() {
        return condition;
    }

    public Recipe<?> getRecipe() {
        return recipe;
    }
}

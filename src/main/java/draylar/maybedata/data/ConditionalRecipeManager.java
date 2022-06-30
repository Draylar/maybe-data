package draylar.maybedata.data;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.gson.*;
import com.mojang.logging.LogUtils;
import draylar.maybedata.MaybeData;
import draylar.maybedata.mixin.RecipeManagerAccessor;
import net.fabricmc.fabric.api.resource.SimpleResourceReloadListener;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.recipe.RecipeType;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.profiler.Profiler;
import org.slf4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class ConditionalRecipeManager extends RecipeManager implements SimpleResourceReloadListener<Map<Identifier, JsonElement>> {

    private static final Identifier ID = new Identifier("maybedata", "conditional_recipes");
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    private static final Logger LOGGER = LogUtils.getLogger();

    public ConditionalRecipeManager() {
    }

    @Override
    public Identifier getFabricId() {
        return ID;
    }

    @Override
    public CompletableFuture<Map<Identifier, JsonElement>> load(ResourceManager manager, Profiler profiler, Executor executor) {
        return CompletableFuture.supplyAsync(() -> super.prepare(manager, profiler));
    }

    @Override
    public CompletableFuture<Void> apply(Map<Identifier, JsonElement> data, ResourceManager manager, Profiler profiler, Executor executor) {
        return CompletableFuture.runAsync(() -> apply(data, manager, profiler));
    }

    @Override
    public void apply(Map<Identifier, JsonElement> map, ResourceManager resourceManager, Profiler profiler) {
        Map<Identifier, JsonElement> valid = new HashMap<>();

        map.forEach((identifier, jsonElement) -> {
            if(jsonElement instanceof JsonObject obj) {
                Condition condition = GSON.fromJson(obj.get("condition"), Condition.class);
                if(condition.verify()) {
                    valid.put(new Identifier(identifier.getNamespace(), String.format("mayberecipe_%s", identifier.getPath())), obj.get("recipe"));
                }
            }
        });

        // collect original & new recipes
        Map<RecipeType<?>, Map<Identifier, Recipe<?>>> existing = ((RecipeManagerAccessor) MaybeData.getDataPackContents().getRecipeManager()).getRecipes();
        ImmutableMap<? extends RecipeType<?>, ImmutableMap<Identifier, Recipe<?>>> parsed = parse(valid);
        HashMap<RecipeType<?>, Map<Identifier, Recipe<?>>> combined = new HashMap<>();

        // add old recipes
        existing.forEach((recipeType, identifierRecipeMap) -> {
            if(!combined.containsKey(recipeType)) {
                combined.put(recipeType, new HashMap<>());
            }

            combined.get(recipeType).putAll(identifierRecipeMap);
        });

        // add new recipes
        parsed.forEach((recipeType, identifierRecipeMap) -> {
            if(!combined.containsKey(recipeType)) {
                combined.put(recipeType, new HashMap<>());
            }

            combined.get(recipeType).putAll(identifierRecipeMap);
        });

        // replace current recipe collection
        ((RecipeManagerAccessor) MaybeData.getDataPackContents().getRecipeManager()).setRecipes(combined);
    }

    public ImmutableMap<? extends RecipeType<?>, ImmutableMap<Identifier, Recipe<?>>> parse(Map<Identifier, JsonElement> map) {
        Map<RecipeType<?>, ImmutableMap.Builder<Identifier, Recipe<?>>> recipeMap = Maps.newHashMap();

        for (Map.Entry<Identifier, JsonElement> identifierJsonElementEntry : map.entrySet()) {
            Identifier identifier = identifierJsonElementEntry.getKey();

            try {
                Recipe<?> recipe = deserialize(identifier, JsonHelper.asObject(identifierJsonElementEntry.getValue(), "top element"));
                recipeMap.computeIfAbsent(recipe.getType(), (recipeType) -> ImmutableMap.builder()).put(identifier, recipe);
            } catch (IllegalArgumentException | JsonParseException var9) {
                LOGGER.error("Parsing error loading conditional recipe {}", identifier, var9);
            }
        }

        LOGGER.info("Loaded {} conditional recipes", recipeMap.size());
        return recipeMap.entrySet().stream().collect(ImmutableMap.toImmutableMap(Map.Entry::getKey, (entryx) -> (entryx.getValue()).build()));
    }
}

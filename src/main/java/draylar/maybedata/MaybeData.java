package draylar.maybedata;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.SimpleResourceReloadListener;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class MaybeData implements ModInitializer {

    @Override
    public void onInitialize() {
        ResourceManagerHelper.get(ResourceType.SERVER_DATA).registerReloadListener(new SimpleResourceReloadListener<>() {

            @Override
            public Identifier getFabricId() {
                return null;
            }

            @Override
            public CompletableFuture<Object> load(ResourceManager manager, Profiler profiler, Executor executor) {
                return null;
            }

            @Override
            public CompletableFuture<Void> apply(Object data, ResourceManager manager, Profiler profiler, Executor executor) {
                return null;
            }
        });
    }
}

package draylar.maybedata.mixin;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import draylar.maybedata.data.MaybeLootTable;
import net.minecraft.loot.LootManager;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LootManager.class)
public class LootManagerMixin {

    @Shadow @Final private static Gson GSON;

    @Inject(method = "method_20711", at = @At(value = "INVOKE", target = "Lcom/google/gson/Gson;fromJson(Lcom/google/gson/JsonElement;Ljava/lang/Class;)Ljava/lang/Object;"), cancellable = true)
    private static void deserializeCondition(ImmutableMap.Builder<Identifier, JsonElement> builder, Identifier id, JsonElement json, CallbackInfo ci) {
        MaybeLootTable table = GSON.fromJson(json, MaybeLootTable.class);
        if(table.getMaybeCondition() != null) {
            if(!table.getMaybeCondition().verify()) {
                ci.cancel();
            }
        }
    }
}

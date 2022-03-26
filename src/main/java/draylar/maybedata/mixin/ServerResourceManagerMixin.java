package draylar.maybedata.mixin;

import draylar.maybedata.data.ConditionalRecipeManager;
import net.minecraft.resource.ResourceReloader;
import net.minecraft.server.DataPackContents;
import net.minecraft.server.command.CommandManager;
import net.minecraft.util.registry.DynamicRegistryManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;

@Mixin(DataPackContents.class)
public class ServerResourceManagerMixin {

    @Unique private ConditionalRecipeManager maybedata_cRecipeManager;

    @Inject(method = "<init>", at = @At("RETURN"))
    private void initcRecipeManager(DynamicRegistryManager.Immutable dynamicRegistryManager, CommandManager.RegistrationEnvironment commandEnvironment, int functionPermissionLevel, CallbackInfo ci) {
        maybedata_cRecipeManager = new ConditionalRecipeManager((DataPackContents) (Object) this);
    }

    @Inject(method = "getContents", at = @At("RETURN"), cancellable = true)
    private void getContents(CallbackInfoReturnable<List<ResourceReloader>> cir) {
        ArrayList<ResourceReloader> list = new ArrayList<>(cir.getReturnValue());
        list.add(maybedata_cRecipeManager);
        cir.setReturnValue(list);
    }
}

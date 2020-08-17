package draylar.maybedata.mixin;

import draylar.maybedata.data.ConditionalRecipeManager;
import net.minecraft.resource.ReloadableResourceManager;
import net.minecraft.resource.ServerResourceManager;
import net.minecraft.server.command.CommandManager;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerResourceManager.class)
public class ServerResourceManagerMixin {

    @Shadow @Final private ReloadableResourceManager resourceManager;
    @Unique private ConditionalRecipeManager maybedata_cRecipeManager;

    @Inject(
            method = "<init>",
            at = @At("RETURN")
    )
    private void initcRecipeManager(CommandManager.RegistrationEnvironment registrationEnvironment, int i, CallbackInfo ci) {
        maybedata_cRecipeManager = new ConditionalRecipeManager((ServerResourceManager) (Object) this);
        this.resourceManager.registerListener(this.maybedata_cRecipeManager);
    }
}

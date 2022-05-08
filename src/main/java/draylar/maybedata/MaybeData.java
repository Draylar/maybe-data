package draylar.maybedata;

import draylar.maybedata.data.ConditionalRecipeManager;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.resource.ResourceType;
import net.minecraft.server.DataPackContents;

public class MaybeData implements ModInitializer {

    private static DataPackContents dataPackContents;

    @Override
    public void onInitialize() {
        ResourceManagerHelper.get(ResourceType.SERVER_DATA).registerReloadListener(new ConditionalRecipeManager());
    }

    public static void setDataPackContents(DataPackContents dataPackContents) {
        MaybeData.dataPackContents = dataPackContents;
    }

    public static DataPackContents getDataPackContents() {
        return dataPackContents;
    }
}

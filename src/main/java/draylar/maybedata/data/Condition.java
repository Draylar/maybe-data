package draylar.maybedata.data;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class Condition {

    private String modid = "";
    private String item = "";

    public Condition(String modid, String item) {
        this.modid = modid;
        this.item = item;
    }

    public boolean verify() {
        if(!modid.isEmpty()) {
            return FabricLoader.getInstance().isModLoaded(modid);
        }

        if(!item.isEmpty()) {
            return Registry.ITEM.getIds().contains(new Identifier(item));
        }

        return false;
    }
}

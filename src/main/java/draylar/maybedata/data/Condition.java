package draylar.maybedata.data;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

public class Condition {

    private final String modid;
    private final String item;
    private final String block;

    public Condition(String modid, String item, String block) {
        this.modid = modid;
        this.item = item;
        this.block = block;
    }

    public boolean verify() {
        if(!(modid == null || modid.isEmpty())) {
            return FabricLoader.getInstance().isModLoaded(modid);
        }

        if(!(item == null || item.isEmpty())) {
            return Registries.ITEM.getIds().contains(new Identifier(item));
        }

        if(!(block == null || block.isEmpty())) {
            return Registries.BLOCK.getIds().contains(new Identifier(block));
        }

        return false;
    }
}

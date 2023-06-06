package net.elijahandpaige.luminiscia;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;

public class Luminiscia implements ModInitializer {
    public static final String MOD_ID = "luminiscia";

    @Override
    public void onInitialize() {
        LuminisciaBlocks.registerAll();
        LuminisciaItems.registerAll();
        LuminisciaDimension.register();


    }
}

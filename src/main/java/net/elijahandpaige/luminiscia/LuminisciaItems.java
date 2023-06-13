package net.elijahandpaige.luminiscia;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class LuminisciaItems {
    public static final Item PYROFLUXITE_ORE = new BlockItem(LuminisciaBlocks.PYROFLUXITE_ORE, new FabricItemSettings());
    public static final Item SHIMMERWOOD_LOG = new BlockItem(LuminisciaBlocks.SHIMMERWOOD_LOG, new FabricItemSettings());
    public static final Item SHIMMERWOOD_PLANKS = new BlockItem(LuminisciaBlocks.SHIMMERWOOD_PLANKS, new FabricItemSettings());
    public static final Item PYROFLUXITE = new Item(new FabricItemSettings());

    public static ItemGroup LUMINISCIA_GROUP = FabricItemGroup.builder(
                    new Identifier(Luminiscia.MOD_ID, "luminiscia_group"))
            .displayName(Text.of("Luminiscia"))
            .icon(() -> new ItemStack(PYROFLUXITE_ORE))
            .build();

    public static void registerAll() {
        registerItem("pyrofluxite_ore", PYROFLUXITE_ORE);
        registerItem("pyrofluxite", PYROFLUXITE);
        registerItem("shimmerwood_log", SHIMMERWOOD_LOG);
        registerItem("shimmerwood_planks", SHIMMERWOOD_PLANKS);
    }
    private static Item registerItem(String name, Item item) {
        ItemGroupEvents.modifyEntriesEvent(LUMINISCIA_GROUP).register(content -> {
            content.add(item);
        });

        return Registry.register(
                Registries.ITEM,
                new Identifier(Luminiscia.MOD_ID, name),
                item
        );
    }
}

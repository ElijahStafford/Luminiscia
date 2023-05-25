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

public class LuminisciaBlocks {
    public static Block PYROFLUXITE_ORE = null;

    public static ItemGroup LUMINISCIA_GROUP = FabricItemGroup.builder(
            new Identifier(Luminiscia.MOD_ID, "luminiscia_group"))
            .displayName(Text.of("Luminiscia"))
            .icon(() -> new ItemStack(PYROFLUXITE_ORE))
            .build();

    public static void registerAll() {
        PYROFLUXITE_ORE = registerBlock(
                "pyrofluxite_ore",
                new Block(FabricBlockSettings.of(Material.STONE).requiresTool().strength(3.0f, 3.0f))
        );
    }

    private static Block registerBlock(String name, Block block) {
        var item = registerBlockItem(name, block);
        ItemGroupEvents.modifyEntriesEvent(LUMINISCIA_GROUP).register(content -> {
            content.add(item);
        });

        return Registry.register(
                Registries.BLOCK,
                new Identifier(Luminiscia.MOD_ID, name),
                block
        );
    }

    private static Item registerBlockItem(String name, Block block) {
        return Registry.register(
                Registries.ITEM,
                new Identifier(Luminiscia.MOD_ID, name),
                new BlockItem(block, new FabricItemSettings())
        );
    }
}

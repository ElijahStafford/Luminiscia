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
    public static final Block PYROFLUXITE_ORE = new Block(FabricBlockSettings.of(Material.STONE).requiresTool().strength(3.0f, 3.0f));

    public static void registerAll() {
        registerBlock("pyrofluxite_ore", PYROFLUXITE_ORE);
    }

    private static Block registerBlock(String name, Block block) {
        return Registry.register(
                Registries.BLOCK,
                new Identifier(Luminiscia.MOD_ID, name),
                block
        );
    }
}

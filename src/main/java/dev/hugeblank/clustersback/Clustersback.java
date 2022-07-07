// Clustersback - backport the iron/gold clusters feature to 1.16.5 in a legal, distributable manner
// Commissioned by zagxc (README for more details)
// Developed by hugeblank, June 2022
package dev.hugeblank.clustersback;

import dev.hugeblank.clustersback.resources.CBResourcePack;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.registry.Registry;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.*;

// I'm bringing clusters back. Them minecraft players don't know how to act.
// https://www.youtube.com/watch?v=3gOHvDP_vCs
public class Clustersback implements ModInitializer {
    public static final Path CONFIG_PATH = FabricLoader.getInstance().getConfigDir().resolve("clustersback");
    public static final Path CLIENT_PATH = CONFIG_PATH.resolve("minecraft_client.jar");

    public static final Item RAW_IRON;
    public static final Item RAW_GOLD;
    public static final Block RAW_IRON_BLOCK;
    public static final Item RAW_IRON_BLOCK_ITEM;
    public static final Block RAW_GOLD_BLOCK;
    public static final Item RAW_GOLD_BLOCK_ITEM;

    @Override
    public void onInitialize() {
        Registry.register(Registry.ITEM, "raw_iron", RAW_IRON);
        Registry.register(Registry.ITEM, "raw_gold", RAW_GOLD);
        Registry.register(Registry.ITEM, "raw_iron_block", RAW_IRON_BLOCK_ITEM);
        Registry.register(Registry.ITEM, "raw_gold_block", RAW_GOLD_BLOCK_ITEM);
        Registry.register(Registry.BLOCK, "raw_iron_block", RAW_IRON_BLOCK);
        Registry.register(Registry.BLOCK, "raw_gold_block", RAW_GOLD_BLOCK);

        FileSystem jar;
        if (!Files.exists(CLIENT_PATH)) MinecraftJarGetter.getVersion("1.18.2", CLIENT_PATH);
        try {
            // Setup/Critical files.
            jar = FileSystems.newFileSystem(CLIENT_PATH, ClassLoader.getSystemClassLoader());
            CBResourcePack.addResourceFile(jar.getPath("pack.png"));
            Path pack = Files.createTempFile("pack", "mcmeta");
            //noinspection ConstantConditions
            CBResourcePack.addResourceFile("pack.mcmeta",
                    write(pack, Clustersback.class.getResourceAsStream("/pack.mcmeta"))
            );
        } catch (IOException e) {
            throw new RuntimeException("Error loading critical resources for Clustersback", e);
        }

        // Textures
        CBResourcePack.addResourceFile(jar.getPath("assets/minecraft/textures/item/raw_iron.png"));
        CBResourcePack.addResourceFile(jar.getPath("assets/minecraft/textures/item/raw_gold.png"));
        CBResourcePack.addResourceFile(jar.getPath("assets/minecraft/textures/block/raw_iron_block.png"));
        CBResourcePack.addResourceFile(jar.getPath("assets/minecraft/textures/block/raw_gold_block.png"));

        // Models
        CBResourcePack.addResourceFile(jar.getPath("assets/minecraft/models/item/raw_iron.json"));
        CBResourcePack.addResourceFile(jar.getPath("assets/minecraft/models/item/raw_gold.json"));
        CBResourcePack.addResourceFile(jar.getPath("assets/minecraft/models/item/raw_iron_block.json"));
        CBResourcePack.addResourceFile(jar.getPath("assets/minecraft/models/item/raw_gold_block.json"));
        CBResourcePack.addResourceFile(jar.getPath("assets/minecraft/models/block/raw_iron_block.json"));
        CBResourcePack.addResourceFile(jar.getPath("assets/minecraft/models/block/raw_gold_block.json"));

        // Blockstates
        CBResourcePack.addResourceFile(jar.getPath("assets/minecraft/blockstates/raw_gold_block.json"));
        CBResourcePack.addResourceFile(jar.getPath("assets/minecraft/blockstates/raw_iron_block.json"));

        // Language (this one is kinda cringe because it overwrites *everything*. Schmee Schmoop.)
        // CBResourcePack.addResourceFile(jar.getPath("assets/minecraft/lang/en_us.json"));

        // Loot tables
        CBResourcePack.addResourceFile(jar.getPath("data/minecraft/loot_tables/blocks/gold_ore.json"));
        CBResourcePack.addResourceFile(jar.getPath("data/minecraft/loot_tables/blocks/iron_ore.json"));
        CBResourcePack.addResourceFile(jar.getPath("data/minecraft/loot_tables/blocks/raw_gold_block.json"));
        CBResourcePack.addResourceFile(jar.getPath("data/minecraft/loot_tables/blocks/raw_iron_block.json"));

        // Recipes
        CBResourcePack.addResourceFile(jar.getPath("data/minecraft/recipes/gold_ingot_from_smelting_raw_gold.json"));
        CBResourcePack.addResourceFile(jar.getPath("data/minecraft/recipes/iron_ingot_from_smelting_raw_iron.json"));
        CBResourcePack.addResourceFile(jar.getPath("data/minecraft/recipes/gold_ingot_from_blasting_raw_gold.json"));
        CBResourcePack.addResourceFile(jar.getPath("data/minecraft/recipes/iron_ingot_from_blasting_raw_iron.json"));
        CBResourcePack.addResourceFile(jar.getPath("data/minecraft/recipes/raw_iron.json"));
        CBResourcePack.addResourceFile(jar.getPath("data/minecraft/recipes/raw_gold.json"));
        CBResourcePack.addResourceFile(jar.getPath("data/minecraft/recipes/raw_iron_block.json"));
        CBResourcePack.addResourceFile(jar.getPath("data/minecraft/recipes/raw_gold_block.json"));

        // Recipe Advancements
        CBResourcePack.addResourceFile(jar.getPath("data/minecraft/advancements/recipes/building_blocks/raw_gold_block.json"));
        CBResourcePack.addResourceFile(jar.getPath("data/minecraft/advancements/recipes/building_blocks/raw_iron_block.json"));
        CBResourcePack.addResourceFile(jar.getPath("data/minecraft/advancements/recipes/misc/gold_ingot_from_smelting_raw_gold.json"));
        CBResourcePack.addResourceFile(jar.getPath("data/minecraft/advancements/recipes/misc/iron_ingot_from_smelting_raw_iron.json"));
        CBResourcePack.addResourceFile(jar.getPath("data/minecraft/advancements/recipes/misc/gold_ingot_from_blasting_raw_gold.json"));
        CBResourcePack.addResourceFile(jar.getPath("data/minecraft/advancements/recipes/misc/iron_ingot_from_blasting_raw_iron.json"));
        CBResourcePack.addResourceFile(jar.getPath("data/minecraft/advancements/recipes/misc/raw_gold.json"));
        CBResourcePack.addResourceFile(jar.getPath("data/minecraft/advancements/recipes/misc/raw_iron.json"));
    }

    static {
        RAW_IRON = new Item(new FabricItemSettings().group(ItemGroup.MATERIALS));
        RAW_GOLD = new Item(new FabricItemSettings().group(ItemGroup.MATERIALS));

        RAW_IRON_BLOCK = new Block(FabricBlockSettings.of(Material.METAL).strength(5.0F, 6.0F).breakByTool(FabricToolTags.PICKAXES).requiresTool());
        RAW_IRON_BLOCK_ITEM = new BlockItem(RAW_IRON_BLOCK, new FabricItemSettings().group(ItemGroup.BUILDING_BLOCKS));
        RAW_GOLD_BLOCK = new Block(FabricBlockSettings.of(Material.METAL).strength(5.0F, 6.0F));
        RAW_GOLD_BLOCK_ITEM = new BlockItem(RAW_GOLD_BLOCK, new FabricItemSettings().group(ItemGroup.BUILDING_BLOCKS));
    }

    private static Path write(Path p, InputStream istream) throws IOException {
        final byte[] BUF = new byte[8192];
        OutputStream ostream = Files.newOutputStream(p, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        int read;
        while ((read = istream.read(BUF)) != -1) {
            ostream.write(BUF, 0, read);
        }
        ostream.close(); istream.close();
        return p;
    }

    public static Path write(InputStream istream) throws IOException {
        Path p = Files.createTempFile(null, null);
        try { Files.delete(p); } catch (IOException ignored) {}
        return write(p, istream);
    }
}

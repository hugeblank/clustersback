package dev.hugeblank.clustersback.resources;

import net.minecraft.resource.ResourcePackProfile;
import net.minecraft.resource.ResourcePackProvider;
import net.minecraft.resource.ResourcePackSource;

import java.util.function.Consumer;

public class CBResourcePackProvider implements ResourcePackProvider {
    @Override
    public void register(Consumer<ResourcePackProfile> profileAdder, ResourcePackProfile.Factory factory) {
        CBResourcePack pack = CBResourcePack.create("Clustersback Generated");
        profileAdder.accept(ResourcePackProfile.of(
                "clustersback_generated",
                true,
                () -> pack, factory,
                ResourcePackProfile.InsertionPosition.TOP,
                ResourcePackSource.PACK_SOURCE_BUILTIN
        ));
    }
}

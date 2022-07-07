package dev.hugeblank.clustersback.mixin;

import net.minecraft.client.resource.SplashTextResourceSupplier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

// By request of commissioner
@Mixin(SplashTextResourceSupplier.class)
public class SplashesMixin {

    @Final
    @Shadow
    private List<String> splashTexts;

    @Inject(at = @At(value = "INVOKE", target = "Ljava/util/Random;nextInt(I)I" ), method = "get()Ljava/lang/String;")
    private void addSplashes(CallbackInfoReturnable<String> cir) {
        splashTexts.add("Trans rights!"); // injecting minecraft with a hefty dose of BASED
        splashTexts.add("Clustersback was commissioned by zagxc!"); // respecting commissioner
    }
}
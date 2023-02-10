package me.allinkdev.skinfix.mixin;

import me.allinkdev.skinfix.SkinFix;
import net.minecraft.client.render.WorldRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldRenderer.class)
public class WorldRendererMixin {
    @Inject(method = "<init>", at = @At("TAIL"))
    public void onInit(final CallbackInfo ci) {
        SkinFix.setWorldRenderer((WorldRenderer) (Object) this);
    }
}

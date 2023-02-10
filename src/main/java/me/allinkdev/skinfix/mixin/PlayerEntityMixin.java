package me.allinkdev.skinfix.mixin;

import me.allinkdev.skinfix.SkinFix;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {
    @Inject(method = "<init>", at = @At("TAIL"))
    public void onInit(final World par1, final CallbackInfo ci) {
        SkinFix.fixSkinAndCloak(thiz());
    }

    @Inject(method = "updateCapeUrl", at = @At("HEAD"), cancellable = true)
    public void onUpdateCapeUrl(final CallbackInfo ci) {
        ci.cancel();
    }

    private PlayerEntity thiz() {
        return ((PlayerEntity) (Object) (this));
    }
}

package me.allinkdev.skinfix.mixin;

import net.minecraft.client.network.OtherPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(OtherPlayerEntity.class)
public class OtherPlayerEntityMixin {
    @Redirect(method = "<init>", at = @At(value = "INVOKE", target = "Ljava/lang/String;length()I"))
    public int onLength(final String instance) {
        return -1;
    }
}

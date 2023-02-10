package me.allinkdev.skinfix.mixin;

import net.minecraft.entity.player.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ClientPlayerEntity.class)
public class ClientPlayerEntityMixin {
    @Redirect(method = "<init>", at = @At(value = "INVOKE", target = "Ljava/lang/String;length()I"))
    public int onLength(final String instance) {
        return -1;
    }
}

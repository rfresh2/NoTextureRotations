package com.ntr.mixin.client;

import com.ntr.NoTextureRotations;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockBehaviour.class)
public class MixinBlockBehavior {

    @Inject(method = "getSeed", at = @At("HEAD"), cancellable = true)
    public void disableSeedBasedOnPosition(final BlockState state, final BlockPos pos, final CallbackInfoReturnable<Long> cir) {
        var config = NoTextureRotations.config.getConfig();
        if (config.disableTextureRotations) {
            switch (config.mode) {
                case NO_ROTATIONS -> cir.setReturnValue(42L);
                case SECURE_RANDOM -> cir.setReturnValue(NoTextureRotations.secureRandom.nextLong());
                case RANDOM_OFFSET -> cir.setReturnValue(Mth.getSeed(pos) + NoTextureRotations.rotationsRandomOffset);
            }
        }
    }

}

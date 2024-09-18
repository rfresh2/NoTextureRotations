package com.ntr.mixin.client;

import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import com.ntr.NoTextureRotations;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockBehaviour.BlockStateBase.class)
public abstract class MixinBlockBehaviorBlockStateBase {
    // makes blocks like flowers not be offset based on their block position
    @Inject(method = "getOffset", at = @At("HEAD"), cancellable = true)
    public void disableOffsetBasedOnPos(final BlockGetter level, final BlockPos pos, final CallbackInfoReturnable<Vec3> cir,
                                        @Local(argsOnly = true) LocalRef<BlockPos> posRef) {
        var config = NoTextureRotations.config.getConfig();
        if (config.disableOffsets) {
            switch (config.mode) {
                case NO_ROTATIONS -> cir.setReturnValue(Vec3.ZERO);
                case SECURE_RANDOM -> posRef.set(BlockPos.of(NoTextureRotations.secureRandom.nextLong()));
                case RANDOM_OFFSET -> posRef.set(
                    new BlockPos(
                        posRef.get().getX() + NoTextureRotations.offsetsRandomOffsetX,
                        posRef.get().getY() + NoTextureRotations.offsetsRandomOffsetY,
                        posRef.get().getZ() + NoTextureRotations.offsetsRandomOffsetZ
                    )
                );
            }
        }
    }
}

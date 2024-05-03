package com.ntr.mixin.client;

import com.ntr.NoTextureRotations;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(BlockBehaviour.BlockStateBase.class)
public abstract class MixinBlockBehaviorBlockStateBase {
    @Final
    @Shadow
    private Optional<BlockBehaviour.OffsetFunction> offsetFunction;

    @Shadow protected abstract BlockState asState();

    // makes blocks like flowers not be offset based on their block position
    @Inject(method = "getOffset", at = @At("HEAD"), cancellable = true)
    public void disableOffsetBasedOnPos(final BlockGetter level, final BlockPos pos, final CallbackInfoReturnable<Vec3> cir) {
        var config = NoTextureRotations.config.get();
        if (config.enabled && config.disableOffsets) {
            switch (config.mode) {
                case NO_ROTATIONS -> cir.setReturnValue(Vec3.ZERO);
                case SECURE_RANDOM -> cir.setReturnValue(
                    offsetFunction
                        .map(function -> function.evaluate(asState(), level, BlockPos.of(NoTextureRotations.secureRandom.nextLong())))
                        .orElse(Vec3.ZERO));
            }
        }
    }
}

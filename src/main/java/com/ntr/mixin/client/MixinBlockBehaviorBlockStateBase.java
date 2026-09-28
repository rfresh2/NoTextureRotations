package com.ntr.mixin.client;

import com.ntr.NoTextureRotations;
import com.ntr.client.RenderOffsetContext;
import net.minecraft.core.BlockPos;
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
    public void disableOffsetBasedOnPos(
        final BlockPos pos, final CallbackInfoReturnable<Vec3> cir
    ) {
        var config = NoTextureRotations.config.getConfig();
        if (config.disableOffsets && RenderOffsetContext.isRendering()) {
            cir.setReturnValue(Vec3.ZERO);
        }
    }
}

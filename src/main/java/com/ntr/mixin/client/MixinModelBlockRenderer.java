package com.ntr.mixin.client;

import com.ntr.client.RenderOffsetContext;
import net.minecraft.client.renderer.block.BlockQuadOutput;
import net.minecraft.client.renderer.block.ModelBlockRenderer;
import net.minecraft.client.renderer.block.dispatch.BlockStateModel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ModelBlockRenderer.class)
public abstract class MixinModelBlockRenderer {

    @Inject(method = "tesselateBlock", at = @At("HEAD"))
    private void beginOffsetRendering(
        final BlockQuadOutput output,
        final float x,
        final float y,
        final float z,
        final net.minecraft.client.renderer.block.BlockAndTintGetter level,
        final BlockPos pos,
        final BlockState state,
        final BlockStateModel model,
        final long seed,
        final CallbackInfo ci
    ) {
        RenderOffsetContext.enter();
    }

    @Inject(method = "tesselateBlock", at = @At("RETURN"))
    private void endOffsetRendering(
        final BlockQuadOutput output,
        final float x,
        final float y,
        final float z,
        final net.minecraft.client.renderer.block.BlockAndTintGetter level,
        final BlockPos pos,
        final BlockState state,
        final BlockStateModel model,
        final long seed,
        final CallbackInfo ci
    ) {
        RenderOffsetContext.exit();
    }
}
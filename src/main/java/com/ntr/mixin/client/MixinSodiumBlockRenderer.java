package com.ntr.mixin.client;

import com.ntr.client.RenderOffsetContext;
import net.caffeinemc.mods.sodium.client.render.chunk.compile.pipeline.BlockRenderer;
import net.minecraft.client.renderer.block.dispatch.BlockStateModel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = BlockRenderer.class, remap = false)
public abstract class MixinSodiumBlockRenderer {

    @Inject(method = "renderModel", at = @At("HEAD"))
    private void beginOffsetRendering(
        final BlockStateModel model,
        final BlockState state,
        final BlockPos pos,
        final BlockPos origin,
        final CallbackInfo ci
    ) {
        RenderOffsetContext.enter();
    }

    @Inject(method = "renderModel", at = @At("RETURN"))
    private void endOffsetRendering(
        final BlockStateModel model,
        final BlockState state,
        final BlockPos pos,
        final BlockPos origin,
        final CallbackInfo ci
    ) {
        RenderOffsetContext.exit();
    }
}
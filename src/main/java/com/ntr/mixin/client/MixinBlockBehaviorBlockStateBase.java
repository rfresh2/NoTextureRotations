package com.ntr.mixin.client;

import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import com.ntr.NoTextureRotations;
import net.minecraft.client.Minecraft;
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
    public void disableOffsetBasedOnPos(
        final BlockGetter level, final BlockPos pos, final CallbackInfoReturnable<Vec3> cir,
        @Local(argsOnly = true) LocalRef<BlockPos> posRef
    ) {
        var config = NoTextureRotations.config.getConfig();
        if (!config.disableOffsets) return;
        var mc = Minecraft.getInstance();
        if (mc == null) return; // will occur at mc bootstrap as blockstate cache is populated

        // collisions and pushing happen in entity tick
        // allow these to occur with original offsets
        // otherwise our collisions are desync'd from the server
        if (NoTextureRotations.inClientLevelTick.get()) return;
        var spServer = mc.getSingleplayerServer();
        // allow singleplayer server to use original offsets for collisions
        if (spServer != null && spServer.isSameThread()) return;
        // any other caller is assumed to be from rendering

        switch (config.mode) {
            case NO_ROTATIONS -> cir.setReturnValue(Vec3.ZERO);
            case KEYED_RANDOM -> posRef.set(BlockPos.of(NoTextureRotations.keyedHash().hash(BlockPos.asLong(pos.getX() & 255, 0, pos.getZ() & 255))));
        }
    }
}

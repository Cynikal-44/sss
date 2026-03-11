package com.dropmod.mixin;

import com.dropmod.config.CustomDropsConfig;
import com.dropmod.config.DropHelper;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.SkeletonEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SkeletonEntity.class)
public class SkeletonEntityMixin {

    /**
     * Inject at the start of dropLoot to replace skeleton vanilla drops
     * with our configured drops.
     */
    @Inject(method = "dropLoot", at = @At("HEAD"), cancellable = true)
    private void customdrops_skeletonDrops(DamageSource source, boolean recentlyHit,
                                            CallbackInfo ci) {
        SkeletonEntity self = (SkeletonEntity) (Object) this;

        if (!(self.getWorld() instanceof ServerWorld serverWorld)) return;

        Vec3d pos = self.getPos();
        DropHelper.spawnDrops(serverWorld, pos, CustomDropsConfig.get().skeletonDrops);

        ci.cancel();
    }
}

package com.dropmod.mixin;

import com.dropmod.config.CustomDropsConfig;
import com.dropmod.config.DropHelper;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ZombieEntity.class)
public class ZombieEntityMixin {

    /**
     * Inject at the start of dropLoot so we can cancel it and replace
     * with our own drops, preventing vanilla loot from spawning.
     */
    @Inject(method = "dropLoot", at = @At("HEAD"), cancellable = true)
    private void customdrops_zombieDrops(DamageSource source, boolean recentlyHit,
                                         CallbackInfo ci) {
        ZombieEntity self = (ZombieEntity) (Object) this;

        // Only override on the server
        if (!(self.getWorld() instanceof ServerWorld serverWorld)) return;

        Vec3d pos = self.getPos();
        DropHelper.spawnDrops(serverWorld, pos, CustomDropsConfig.get().zombieDrops);

        // Cancel vanilla loot drop
        ci.cancel();
    }
}

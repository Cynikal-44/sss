package com.dropmod.config;

import net.minecraft.entity.ItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;

import java.util.List;
import java.util.Random;

public class DropHelper {

    private static final Random RANDOM = new Random();

    /**
     * Spawns all configured drops for a mob at the given position.
     * Called from the mixin after the entity dies.
     *
     * @param world   The server world
     * @param pos     Position to spawn items at
     * @param entries The list of drop entries from config
     */
    public static void spawnDrops(ServerWorld world, Vec3d pos, List<DropEntry> entries) {
        for (DropEntry entry : entries) {
            // Roll chance
            if (entry.chance < 1.0 && RANDOM.nextDouble() > entry.chance) {
                continue;
            }

            // Roll amount
            int min = Math.max(0, entry.minAmount);
            int max = Math.max(min, entry.maxAmount);
            int amount = min + (max > min ? RANDOM.nextInt(max - min + 1) : 0);

            if (amount <= 0) continue;

            // Resolve item from registry
            Identifier id = Identifier.tryParse(entry.item);
            if (id == null) {
                System.err.println("[CustomDrops] Invalid item identifier: " + entry.item);
                continue;
            }

            Item item = Registries.ITEM.get(id);
            if (item == null) {
                System.err.println("[CustomDrops] Unknown item: " + entry.item);
                continue;
            }

            // Spawn the item entity with a small random velocity (like vanilla drops)
            ItemStack stack = new ItemStack(item, amount);
            double vx = RANDOM.nextGaussian() * 0.05;
            double vy = 0.2 + RANDOM.nextGaussian() * 0.05;
            double vz = RANDOM.nextGaussian() * 0.05;

            ItemEntity itemEntity = new ItemEntity(world,
                    pos.x, pos.y + 0.5, pos.z,
                    stack, vx, vy, vz);
            world.spawnEntity(itemEntity);
        }
    }
}

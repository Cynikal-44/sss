package com.dropmod.config;

/**
 * Represents a single item drop with a configurable quantity range.
 * Example JSON:
 *   { "item": "minecraft:bone", "minAmount": 1, "maxAmount": 3, "chance": 1.0 }
 */
public class DropEntry {
    /** The item identifier, e.g. "minecraft:rotten_flesh" */
    public String item = "minecraft:rotten_flesh";

    /** Minimum number of this item to drop (inclusive). */
    public int minAmount = 1;

    /** Maximum number of this item to drop (inclusive). */
    public int maxAmount = 2;

    /**
     * Chance (0.0 – 1.0) that this drop occurs at all.
     * 1.0 = always, 0.5 = 50% chance, 0.0 = never.
     */
    public double chance = 1.0;

    public DropEntry() {}

    public DropEntry(String item, int minAmount, int maxAmount, double chance) {
        this.item = item;
        this.minAmount = minAmount;
        this.maxAmount = maxAmount;
        this.chance = chance;
    }
}

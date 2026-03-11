# Custom Drops — Fabric Mod for Minecraft 1.21.1

Fully customize what **Zombies** and **Skeletons** drop when killed, including item type, quantity range, and drop chance — all via a simple JSON config file.

---

## Configuration

The config file is created automatically at:
```
.minecraft/config/custom_drops.json
```

### Example Config
```json
{
  "zombieDrops": [
    { "item": "minecraft:rotten_flesh", "minAmount": 2, "maxAmount": 5, "chance": 1.0 },
    { "item": "minecraft:iron_ingot",   "minAmount": 1, "maxAmount": 1, "chance": 0.025 },
    { "item": "minecraft:carrot",       "minAmount": 1, "maxAmount": 1, "chance": 0.025 },
    { "item": "minecraft:potato",       "minAmount": 1, "maxAmount": 1, "chance": 0.025 }
  ],
  "skeletonDrops": [
    { "item": "minecraft:bone",  "minAmount": 0, "maxAmount": 2, "chance": 1.0 },
    { "item": "minecraft:arrow", "minAmount": 0, "maxAmount": 2, "chance": 1.0 }
  ]
}
```

### Fields
| Field | Description |
|---|---|
| `item` | The item ID to drop, e.g. `"minecraft:diamond"` |
| `minAmount` | Minimum number of items dropped |
| `maxAmount` | Maximum number of items dropped |
| `chance` | Drop chance — `1.0` = always, `0.5` = 50%, `0.0` = never |

### Tips
- You can add as many entries as you want per mob
- Setting `minAmount` and `maxAmount` to the same number gives a fixed drop amount
- Use `"chance": 0.0` to effectively disable a drop without removing the entry
- Any valid Minecraft item ID works, including modded items (e.g. `"modid:item_name"`)
- The mod **replaces** vanilla drops entirely — vanilla loot tables are ignored

### Example: Make zombies drop diamonds
```json
{
  "zombieDrops": [
    { "item": "minecraft:diamond", "minAmount": 1, "maxAmount": 3, "chance": 1.0 }
  ],
  "skeletonDrops": [
    { "item": "minecraft:bone",  "minAmount": 0, "maxAmount": 2, "chance": 1.0 },
    { "item": "minecraft:arrow", "minAmount": 0, "maxAmount": 2, "chance": 1.0 }
  ]
}
```

---

## Building from Source

Requirements: **JDK 21**, internet connection

```bash
cd drop-mod
./gradlew build
# Output: build/libs/custom-drops-1.0.0.jar
```

---

## Installation

1. Install Fabric Loader 0.16.5+ for Minecraft 1.21.1
2. Install Fabric API
3. Drop `custom-drops-1.0.0.jar` into `.minecraft/mods/`
4. Launch once to generate the config file
5. Edit `.minecraft/config/custom_drops.json` to your liking

---

## License
MIT

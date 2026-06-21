# 2BN-LowestLibrarians

2BN-LowestLibrarians is a Paper plugin that helps players spot minimum-price librarian enchanted book trades.

When a player opens an untraded librarian's merchant inventory, the plugin scans the villager's offers. If it finds a single-enchantment enchanted book at the enchantment's maximum level and at the lowest supported emerald price, the player receives a custom advancement toast and notification sounds.

## Features

- Detects cheapest max-level librarian enchanted book trades when a player opens the trade UI.
- Ignores non-librarian villagers and villagers that have already been traded with.
- Only notifies for books with exactly one stored enchantment.
- Uses the villager's displayed adjusted emerald price, so current in-game price modifiers are respected.
- Shows a custom toast through UltimateAdvancementAPI.
- Requires no commands or configuration.

## Requirements

- Paper API target: `1.21.11-R0.1-SNAPSHOT`
- Minecraft support: `1.21.11` and newer compatible Paper releases
- Java: `25`
- Runtime dependency: `UltimateAdvancementAPI`

The plugin declares `UltimateAdvancementAPI` as a required Bukkit dependency, so it must be installed on the server before this plugin can enable.

## Installation

1. Build the plugin with Maven:

   ```sh
   mvn clean package
   ```

2. Copy the shaded plugin jar into your server's `plugins` directory:

   ```sh
   target/2bn-lowestlibrarians-1.1.0.jar
   ```

3. Install `UltimateAdvancementAPI` on the same server.
4. Restart the server.

On startup, the server log should include:

```text
2BN-LowestLibrarians enabled.
```

## Price Rules

The plugin considers these emerald prices to be the lowest supported prices:

| Enchantment level | Standard book price |
| --- | ---: |
| I | 5 |
| II | 8 |
| III | 11 |
| IV | 14 |
| V | 17 |

Special treasure prices:

| Enchantment | Level | Price |
| --- | ---: | ---: |
| Mending | I | 10 |
| Curse of Vanishing | I | 10 |
| Curse of Binding | I | 10 |
| Frost Walker | II | 16 |

## Player Experience

Open a librarian's trade window. If the librarian has a qualifying offer, the player receives an enchanted-book toast named like:

```text
Cheapest Mending
Cheapest Efficiency 5
```

The notification also plays a vault shutter sound and two bell resonance sounds.

## Development

Build the default Minecraft 1.21 profile:

```sh
mvn clean package
```

Build with the alternate `minecraft-26.2` profile:

```sh
mvn clean package -Pminecraft-26.2
```

Project layout:

```text
src/main/java/dev/twobeardednomads/lowestlibrarians/
  LowestLibrariansPlugin.java
  listener/        Inventory open listener
  service/         Librarian filtering and recipe scanning orchestration
  scanning/        Merchant recipe emerald-price extraction
  evaluation/      Enchanted book validation
  pricing/         Lowest-price rules
  notification/    Toast and sound notifications
  formatting/      Enchantment display-name formatting
src/main/resources/plugin.yml
```

## Commands and Permissions

This plugin does not register any commands or permissions.

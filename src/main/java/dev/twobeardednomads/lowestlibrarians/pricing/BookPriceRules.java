package dev.twobeardednomads.lowestlibrarians.pricing;

import org.bukkit.enchantments.Enchantment;

import java.util.Objects;

public final class BookPriceRules {

  private static final int LEVEL_ONE_LOWEST_PRICE = 5;
  private static final int LEVEL_TWO_LOWEST_PRICE = 8;
  private static final int LEVEL_THREE_LOWEST_PRICE = 11;
  private static final int LEVEL_FOUR_LOWEST_PRICE = 14;
  private static final int LEVEL_FIVE_LOWEST_PRICE = 17;

  private static final int TREASURE_LEVEL_ONE_LOWEST_PRICE = 10;
  private static final int TREASURE_LEVEL_TWO_LOWEST_PRICE = 16;

  public int getLowestAllowedPrice(Enchantment enchantment, int level) {
    Objects.requireNonNull(enchantment, "enchantment must not be null");

    validateLevel(level);

    if (isTreasureBookWithSpecialPrice(enchantment, level)) {
      return getSpecialTreasurePrice(level);
    }

    return getStandardLowestPrice(level);
  }

  public boolean isLowestAllowedPrice(Enchantment enchantment, int level, int emeraldPrice) {
    Objects.requireNonNull(enchantment, "enchantment must not be null");

    validateLevel(level);

    if (emeraldPrice < 1) {
      return false;
    }

    return emeraldPrice == getLowestAllowedPrice(enchantment, level);
  }

  private boolean isTreasureBookWithSpecialPrice(Enchantment enchantment, int level) {
    if (isAlwaysTenEmeraldTreasure(enchantment, level)) {
      return true;
    }

    return isFrostWalkerLevelTwo(enchantment, level);
  }

  private boolean isAlwaysTenEmeraldTreasure(Enchantment enchantment, int level) {
    if (level != 1) {
      return false;
    }

    return enchantment.equals(Enchantment.MENDING)
        || enchantment.equals(Enchantment.VANISHING_CURSE)
        || enchantment.equals(Enchantment.BINDING_CURSE);
  }

  private boolean isFrostWalkerLevelTwo(Enchantment enchantment, int level) {
    return enchantment.equals(Enchantment.FROST_WALKER) && level == 2;
  }

  private int getSpecialTreasurePrice(int level) {
    return switch (level) {
      case 1 -> TREASURE_LEVEL_ONE_LOWEST_PRICE;
      case 2 -> TREASURE_LEVEL_TWO_LOWEST_PRICE;
      default -> throw new IllegalArgumentException("Unsupported treasure level: " + level);
    };
  }

  private int getStandardLowestPrice(int level) {
    return switch (level) {
      case 1 -> LEVEL_ONE_LOWEST_PRICE;
      case 2 -> LEVEL_TWO_LOWEST_PRICE;
      case 3 -> LEVEL_THREE_LOWEST_PRICE;
      case 4 -> LEVEL_FOUR_LOWEST_PRICE;
      case 5 -> LEVEL_FIVE_LOWEST_PRICE;
      default -> throw new IllegalArgumentException("Unsupported enchantment level: " + level);
    };
  }

  private void validateLevel(int level) {
    if (level < 1 || level > 5) {
      throw new IllegalArgumentException("Supported enchantment level must be between 1 and 5");
    }
  }
}
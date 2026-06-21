package dev.twobeardednomads.lowestlibrarians.domain;

import java.util.Objects;

public final class CheapestBookOffer {

  private final String enchantmentDisplayName;
  private final int enchantmentLevel;
  private final int emeraldPrice;

  public CheapestBookOffer(String enchantmentDisplayName, int enchantmentLevel, int emeraldPrice) {
    this.enchantmentDisplayName = validateEnchantmentDisplayName(enchantmentDisplayName);
    this.enchantmentLevel = validateEnchantmentLevel(enchantmentLevel);
    this.emeraldPrice = validateEmeraldPrice(emeraldPrice);
  }

  private static String validateEnchantmentDisplayName(String enchantmentDisplayName) {
    Objects.requireNonNull(enchantmentDisplayName, "enchantmentDisplayName must not be null");

    String trimmedName = enchantmentDisplayName.trim();
    if (trimmedName.isEmpty()) {
      throw new IllegalArgumentException("enchantmentDisplayName must not be blank");
    }

    return trimmedName;
  }

  private static int validateEnchantmentLevel(int enchantmentLevel) {
    if (enchantmentLevel < 1) {
      throw new IllegalArgumentException("enchantmentLevel must be at least 1");
    }

    return enchantmentLevel;
  }

  private static int validateEmeraldPrice(int emeraldPrice) {
    if (emeraldPrice < 1) {
      throw new IllegalArgumentException("emeraldPrice must be at least 1");
    }

    return emeraldPrice;
  }

  public String getEnchantmentDisplayName() {
    return enchantmentDisplayName;
  }

  public int getEnchantmentLevel() {
    return enchantmentLevel;
  }

  public int getEmeraldPrice() {
    return emeraldPrice;
  }

  public String createTitleText() {
    return "Cheapest " + enchantmentDisplayName + " " + enchantmentLevel;
  }
}
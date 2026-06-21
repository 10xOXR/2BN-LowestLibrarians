package dev.twobeardednomads.lowestlibrarians.evaluation;

import dev.twobeardednomads.lowestlibrarians.domain.CheapestBookOffer;
import dev.twobeardednomads.lowestlibrarians.formatting.EnchantmentDisplayFormatter;
import dev.twobeardednomads.lowestlibrarians.pricing.BookPriceRules;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public final class BookOfferEvaluator {

  private final BookPriceRules bookPriceRules;
  private final EnchantmentDisplayFormatter enchantmentDisplayFormatter;

  public BookOfferEvaluator(BookPriceRules bookPriceRules, EnchantmentDisplayFormatter enchantmentDisplayFormatter) {
    this.bookPriceRules = Objects.requireNonNull(bookPriceRules, "bookPriceRules must not be null");
    this.enchantmentDisplayFormatter = Objects.requireNonNull(
        enchantmentDisplayFormatter,
        "enchantmentDisplayFormatter must not be null"
    );
  }

  public Optional<CheapestBookOffer> evaluate(ItemStack resultItem, int emeraldPrice) {
    Objects.requireNonNull(resultItem, "resultItem must not be null");

    if (!isEnchantedBook(resultItem)) {
      return Optional.empty();
    }

    if (!(resultItem.getItemMeta() instanceof EnchantmentStorageMeta enchantmentStorageMeta)) {
      return Optional.empty();
    }

    return evaluateStoredEnchantments(enchantmentStorageMeta, emeraldPrice);
  }

  private boolean isEnchantedBook(ItemStack resultItem) {
    return resultItem.getType() == Material.ENCHANTED_BOOK;
  }

  private Optional<CheapestBookOffer> evaluateStoredEnchantments(
      EnchantmentStorageMeta enchantmentStorageMeta,
      int emeraldPrice
  ) {
    Map<Enchantment, Integer> storedEnchantments = enchantmentStorageMeta.getStoredEnchants();

    if (storedEnchantments.size() != 1) {
      return Optional.empty();
    }

    Map.Entry<Enchantment, Integer> storedEnchantment = storedEnchantments.entrySet().iterator().next();
    Enchantment enchantment = storedEnchantment.getKey();
    int offeredLevel = storedEnchantment.getValue();

    if (!isHighestLevel(enchantment, offeredLevel)) {
      return Optional.empty();
    }

    if (!bookPriceRules.isLowestAllowedPrice(enchantment, offeredLevel, emeraldPrice)) {
      return Optional.empty();
    }

    return Optional.of(createCheapestBookOffer(enchantment, offeredLevel, emeraldPrice));
  }

  private boolean isHighestLevel(Enchantment enchantment, int offeredLevel) {
    return offeredLevel == enchantment.getMaxLevel();
  }

  private CheapestBookOffer createCheapestBookOffer(Enchantment enchantment, int level, int emeraldPrice) {
    String enchantmentDisplayName = enchantmentDisplayFormatter.formatDisplayName(enchantment);
    return new CheapestBookOffer(enchantmentDisplayName, level, emeraldPrice);
  }
}
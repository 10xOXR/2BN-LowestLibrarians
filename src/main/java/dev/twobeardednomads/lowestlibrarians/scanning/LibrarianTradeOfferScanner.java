package dev.twobeardednomads.lowestlibrarians.scanning;

import dev.twobeardednomads.lowestlibrarians.domain.CheapestBookOffer;
import dev.twobeardednomads.lowestlibrarians.evaluation.BookOfferEvaluator;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantRecipe;

import java.util.Objects;
import java.util.Optional;

public final class LibrarianTradeOfferScanner {

  private final BookOfferEvaluator bookOfferEvaluator;

  public LibrarianTradeOfferScanner(BookOfferEvaluator bookOfferEvaluator) {
    this.bookOfferEvaluator = Objects.requireNonNull(bookOfferEvaluator, "bookOfferEvaluator must not be null");
  }

  public Optional<CheapestBookOffer> findCheapestBookOffer(MerchantRecipe merchantRecipe) {
    Objects.requireNonNull(merchantRecipe, "merchantRecipe must not be null");

    Optional<Integer> displayedEmeraldPrice = findDisplayedEmeraldPrice(merchantRecipe);
    if (displayedEmeraldPrice.isEmpty()) {
      return Optional.empty();
    }

    ItemStack resultItem = merchantRecipe.getResult();
    return bookOfferEvaluator.evaluate(resultItem, displayedEmeraldPrice.get());
  }

  private Optional<Integer> findDisplayedEmeraldPrice(MerchantRecipe merchantRecipe) {
    ItemStack adjustedFirstIngredient = merchantRecipe.getAdjustedIngredient1();

    if (!isEmeraldStack(adjustedFirstIngredient)) {
      return Optional.empty();
    }

    return Optional.of(adjustedFirstIngredient.getAmount());
  }

  private boolean isEmeraldStack(ItemStack itemStack) {
    if (itemStack == null) {
      return false;
    }

    return itemStack.getType() == Material.EMERALD && itemStack.getAmount() > 0;
  }
}
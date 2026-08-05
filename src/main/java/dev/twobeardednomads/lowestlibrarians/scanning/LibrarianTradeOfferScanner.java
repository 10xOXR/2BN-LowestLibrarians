package dev.twobeardednomads.lowestlibrarians.scanning;

import dev.twobeardednomads.lowestlibrarians.domain.CheapestBookOffer;
import dev.twobeardednomads.lowestlibrarians.evaluation.BookOfferEvaluator;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantRecipe;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public final class LibrarianTradeOfferScanner {

  private final BookOfferEvaluator bookOfferEvaluator;

  public LibrarianTradeOfferScanner(BookOfferEvaluator bookOfferEvaluator) {
    this.bookOfferEvaluator = Objects.requireNonNull(bookOfferEvaluator, "bookOfferEvaluator must not be null");
  }

  public Optional<CheapestBookOffer> findCheapestBookOffer(MerchantRecipe merchantRecipe) {
    Objects.requireNonNull(merchantRecipe, "merchantRecipe must not be null");

    Optional<Integer> originalEmeraldPrice = findOriginalEmeraldPrice(merchantRecipe);
    if (originalEmeraldPrice.isEmpty()) {
      return Optional.empty();
    }

    ItemStack resultItem = merchantRecipe.getResult();
    return bookOfferEvaluator.evaluate(resultItem, originalEmeraldPrice.get());
  }

  private Optional<Integer> findOriginalEmeraldPrice(MerchantRecipe merchantRecipe) {
    List<ItemStack> ingredients = merchantRecipe.getIngredients();
    if (ingredients.isEmpty()) {
      return Optional.empty();
    }

    ItemStack firstIngredient = ingredients.getFirst();
    if (!isEmeraldStack(firstIngredient)) {
      return Optional.empty();
    }

    return Optional.of(firstIngredient.getAmount());
  }

  private boolean isEmeraldStack(ItemStack itemStack) {
    if (itemStack == null) {
      return false;
    }

    return itemStack.getType() == Material.EMERALD && itemStack.getAmount() > 0;
  }
}

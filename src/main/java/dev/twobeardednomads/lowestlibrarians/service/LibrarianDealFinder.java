package dev.twobeardednomads.lowestlibrarians.service;

import dev.twobeardednomads.lowestlibrarians.domain.CheapestBookOffer;
import dev.twobeardednomads.lowestlibrarians.scanning.LibrarianTradeOfferScanner;
import org.bukkit.entity.AbstractVillager;
import org.bukkit.entity.Villager;
import org.bukkit.inventory.MerchantRecipe;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public final class LibrarianDealFinder {

  private final LibrarianTradeOfferScanner librarianTradeOfferScanner;

  public LibrarianDealFinder(LibrarianTradeOfferScanner librarianTradeOfferScanner) {
    this.librarianTradeOfferScanner = Objects.requireNonNull(
        librarianTradeOfferScanner,
        "librarianTradeOfferScanner must not be null"
    );
  }

  public Optional<CheapestBookOffer> findDeal(AbstractVillager villager) {
    Objects.requireNonNull(villager, "villager must not be null");

    if (!(villager instanceof Villager librarian)) {
      return Optional.empty();
    }

    if (!isLibrarian(librarian)) {
      return Optional.empty();
    }

    if (hasBeenTradedWith(librarian)) {
      return Optional.empty();
    }

    return findDealInRecipes(librarian.getRecipes());
  }

  private boolean isLibrarian(Villager villager) {
    return villager.getProfession() == Villager.Profession.LIBRARIAN;
  }

  private boolean hasBeenTradedWith(Villager villager) {
    return villager.getVillagerExperience() > 0;
  }

  private Optional<CheapestBookOffer> findDealInRecipes(List<MerchantRecipe> recipes) {
    for (MerchantRecipe recipe : recipes) {
      Optional<CheapestBookOffer> cheapestBookOffer = librarianTradeOfferScanner.findCheapestBookOffer(recipe);
      if (cheapestBookOffer.isPresent()) {
        return cheapestBookOffer;
      }
    }

    return Optional.empty();
  }
}
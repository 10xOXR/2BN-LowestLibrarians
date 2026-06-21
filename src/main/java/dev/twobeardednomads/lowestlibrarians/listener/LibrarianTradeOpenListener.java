package dev.twobeardednomads.lowestlibrarians.listener;

import dev.twobeardednomads.lowestlibrarians.LowestLibrariansPlugin;
import dev.twobeardednomads.lowestlibrarians.domain.CheapestBookOffer;
import dev.twobeardednomads.lowestlibrarians.notification.CheapestBookNotifier;
import dev.twobeardednomads.lowestlibrarians.service.LibrarianDealFinder;
import org.bukkit.entity.AbstractVillager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Merchant;
import org.bukkit.inventory.view.MerchantView;

import java.util.Objects;
import java.util.Optional;

public final class LibrarianTradeOpenListener implements Listener {

  private final LowestLibrariansPlugin plugin;
  private final LibrarianDealFinder librarianDealFinder;
  private final CheapestBookNotifier cheapestBookNotifier;

  public LibrarianTradeOpenListener(
      LowestLibrariansPlugin plugin,
      LibrarianDealFinder librarianDealFinder,
      CheapestBookNotifier cheapestBookNotifier
  ) {
    this.plugin = Objects.requireNonNull(plugin, "plugin must not be null");
    this.librarianDealFinder = Objects.requireNonNull(
        librarianDealFinder,
        "librarianDealFinder must not be null"
    );
    this.cheapestBookNotifier = Objects.requireNonNull(
        cheapestBookNotifier,
        "cheapestBookNotifier must not be null"
    );
  }

  @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
  public void onInventoryOpen(InventoryOpenEvent event) {
    if (!(event.getPlayer() instanceof Player player)) {
      return;
    }

    if (!(event.getView() instanceof MerchantView merchantView)) {
      return;
    }

    Optional<AbstractVillager> villager = findVillager(merchantView);
    if (villager.isEmpty()) {
      return;
    }

    Optional<CheapestBookOffer> cheapestBookOffer = librarianDealFinder.findDeal(villager.get());
    if (cheapestBookOffer.isEmpty()) {
      return;
    }

    plugin.getServer().getScheduler().runTask(plugin, () ->
        cheapestBookNotifier.notify(player, cheapestBookOffer.get())
    );
  }

  private Optional<AbstractVillager> findVillager(MerchantView merchantView) {
    Merchant merchant = merchantView.getMerchant();

    if (merchant instanceof AbstractVillager villager) {
      return Optional.of(villager);
    }

    return Optional.empty();
  }
}
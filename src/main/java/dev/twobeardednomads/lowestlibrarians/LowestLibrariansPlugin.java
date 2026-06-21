package dev.twobeardednomads.lowestlibrarians;

import dev.twobeardednomads.lowestlibrarians.evaluation.BookOfferEvaluator;
import dev.twobeardednomads.lowestlibrarians.formatting.EnchantmentDisplayFormatter;
import dev.twobeardednomads.lowestlibrarians.listener.LibrarianTradeOpenListener;
import dev.twobeardednomads.lowestlibrarians.notification.CheapestBookNotifier;
import dev.twobeardednomads.lowestlibrarians.notification.CheapestBookToastNotifier;
import dev.twobeardednomads.lowestlibrarians.pricing.BookPriceRules;
import dev.twobeardednomads.lowestlibrarians.scanning.LibrarianTradeOfferScanner;
import dev.twobeardednomads.lowestlibrarians.service.LibrarianDealFinder;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class LowestLibrariansPlugin extends JavaPlugin {

  @Override
  public void onEnable() {
    LibrarianTradeOpenListener librarianTradeOpenListener = createLibrarianTradeOpenListener();
    registerListeners(librarianTradeOpenListener);

    getLogger().info("2BN-LowestLibrarians enabled.");
  }

  @Override
  public void onDisable() {
    getLogger().info("2BN-LowestLibrarians disabled.");
  }

  private LibrarianTradeOpenListener createLibrarianTradeOpenListener() {
    BookPriceRules bookPriceRules = new BookPriceRules();
    EnchantmentDisplayFormatter enchantmentDisplayFormatter = new EnchantmentDisplayFormatter();
    BookOfferEvaluator bookOfferEvaluator = new BookOfferEvaluator(
        bookPriceRules,
        enchantmentDisplayFormatter
    );
    LibrarianTradeOfferScanner librarianTradeOfferScanner = new LibrarianTradeOfferScanner(bookOfferEvaluator);
    LibrarianDealFinder librarianDealFinder = new LibrarianDealFinder(librarianTradeOfferScanner);

    CheapestBookToastNotifier cheapestBookToastNotifier = new CheapestBookToastNotifier(this);
    CheapestBookNotifier cheapestBookNotifier = new CheapestBookNotifier(cheapestBookToastNotifier);

    return new LibrarianTradeOpenListener(this, librarianDealFinder, cheapestBookNotifier);
  }

  private void registerListeners(LibrarianTradeOpenListener librarianTradeOpenListener) {
    PluginManager pluginManager = getServer().getPluginManager();
    pluginManager.registerEvents(librarianTradeOpenListener, this);
  }
}
package dev.twobeardednomads.lowestlibrarians;

import dev.twobeardednomads.lowestlibrarians.evaluation.BookOfferEvaluator;
import dev.twobeardednomads.lowestlibrarians.formatting.EnchantmentDisplayFormatter;
import dev.twobeardednomads.lowestlibrarians.listener.LibrarianTradeOpenListener;
import dev.twobeardednomads.lowestlibrarians.notification.CheapestBookNotifier;
import dev.twobeardednomads.lowestlibrarians.notification.CheapestBookToastNotifier;
import dev.twobeardednomads.lowestlibrarians.pricing.BookPriceRules;
import dev.twobeardednomads.lowestlibrarians.scanning.LibrarianTradeOfferScanner;
import dev.twobeardednomads.lowestlibrarians.service.LibrarianDealFinder;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class LowestLibrariansPlugin extends JavaPlugin {
  private Audience audience;

  @Override
  public void onEnable() {
    this.audience = Bukkit.getServer().getConsoleSender();

    LibrarianTradeOpenListener librarianTradeOpenListener = createLibrarianTradeOpenListener();
    registerListeners(librarianTradeOpenListener);

    audience.sendMessage(header().append(Component.text("Loaded", NamedTextColor.WHITE)));
    audience.sendMessage(header().append(Component.text("Enabled", NamedTextColor.WHITE)));
  }

  @Override
  public void onDisable() {
    if (audience == null) {
      audience = Bukkit.getServer().getConsoleSender();
    }
    audience.sendMessage(header().append(Component.text("Disabled", NamedTextColor.WHITE)));
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

  private Component header() {
    return Component.text("[", NamedTextColor.YELLOW)
        .append(Component.text("2BN-LowestLibrarians", NamedTextColor.GOLD))
        .append(Component.text("] ", NamedTextColor.YELLOW));
  }
}

package dev.twobeardednomads.lowestlibrarians.notification;

import com.fren_gor.ultimateAdvancementAPI.UltimateAdvancementAPI;
import com.fren_gor.ultimateAdvancementAPI.advancement.display.AdvancementFrameType;
import dev.twobeardednomads.lowestlibrarians.domain.CheapestBookOffer;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.Objects;

public final class CheapestBookToastNotifier {

  private static final String TOAST_TITLE_PREFIX = "Cheapest ";
  private static final AdvancementFrameType TOAST_FRAME_TYPE = AdvancementFrameType.GOAL;

  private final UltimateAdvancementAPI ultimateAdvancementAPI;

  public CheapestBookToastNotifier(Plugin plugin) {
    Objects.requireNonNull(plugin, "plugin must not be null");
    this.ultimateAdvancementAPI = UltimateAdvancementAPI.getInstance(plugin);
  }

  public void notify(Player player, CheapestBookOffer cheapestBookOffer) {
    Objects.requireNonNull(player, "player must not be null");
    Objects.requireNonNull(cheapestBookOffer, "cheapestBookOffer must not be null");

    ultimateAdvancementAPI.displayCustomToast(
        player,
        createToastIcon(),
        createToastTitle(cheapestBookOffer),
        TOAST_FRAME_TYPE
    );
  }

  private ItemStack createToastIcon() {
    return new ItemStack(Material.ENCHANTED_BOOK);
  }

  private String createToastTitle(CheapestBookOffer cheapestBookOffer) {
    if (cheapestBookOffer.getEnchantmentLevel() == 1) {
      return TOAST_TITLE_PREFIX + cheapestBookOffer.getEnchantmentDisplayName();
    }
    return TOAST_TITLE_PREFIX
        + cheapestBookOffer.getEnchantmentDisplayName()
        + " "
        + cheapestBookOffer.getEnchantmentLevel();
  }
}
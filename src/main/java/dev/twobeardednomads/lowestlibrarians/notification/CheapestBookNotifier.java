package dev.twobeardednomads.lowestlibrarians.notification;

import dev.twobeardednomads.lowestlibrarians.domain.CheapestBookOffer;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;

import java.util.Objects;

public final class CheapestBookNotifier {

  private static final float SOUND_VOLUME = 1.0F;
  private static final float SOUND_PITCH = 1.0F;

  private final CheapestBookToastNotifier cheapestBookToastNotifier;

  public CheapestBookNotifier(CheapestBookToastNotifier cheapestBookToastNotifier) {
    this.cheapestBookToastNotifier = Objects.requireNonNull(
        cheapestBookToastNotifier,
        "cheapestBookToastNotifier must not be null"
    );
  }

  public void notify(Player player, CheapestBookOffer cheapestBookOffer) {
    Objects.requireNonNull(player, "player must not be null");
    Objects.requireNonNull(cheapestBookOffer, "cheapestBookOffer must not be null");

    cheapestBookToastNotifier.notify(player, cheapestBookOffer);
    playSound(player);
  }

  private void playSound(Player player) {
    player.playSound(
        player,
        Sound.BLOCK_VAULT_OPEN_SHUTTER,
        SoundCategory.MASTER,
        SOUND_VOLUME,
        SOUND_PITCH
    );
    player.playSound(
        player,
        Sound.BLOCK_BELL_RESONATE,
        SoundCategory.MASTER,
        SOUND_VOLUME,
        SOUND_PITCH
    );
    player.playSound(
        player,
        Sound.BLOCK_BELL_RESONATE,
        SoundCategory.MASTER,
        SOUND_VOLUME,
        SOUND_PITCH
    );
  }
}
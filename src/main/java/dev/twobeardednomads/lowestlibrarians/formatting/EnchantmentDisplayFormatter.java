package dev.twobeardednomads.lowestlibrarians.formatting;

import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;

import java.util.Locale;
import java.util.Objects;
import java.util.Set;

public final class EnchantmentDisplayFormatter {

  private static final Set<String> LOWERCASE_WORDS = Set.of("of");

  public String formatDisplayName(Enchantment enchantment) {
    Objects.requireNonNull(enchantment, "enchantment must not be null");

    NamespacedKey key = enchantment.getKey();
    String keyValue = key.getKey();

    return formatKeyValue(keyValue);
  }

  private String formatKeyValue(String keyValue) {
    String[] words = keyValue.split("_");
    StringBuilder displayName = new StringBuilder();

    for (int index = 0; index < words.length; index++) {
      if (index > 0) {
        displayName.append(' ');
      }

      displayName.append(formatWord(words[index], index));
    }

    return displayName.toString();
  }

  private String formatWord(String word, int wordIndex) {
    String lowerCaseWord = word.toLowerCase(Locale.ROOT);

    if (wordIndex > 0 && LOWERCASE_WORDS.contains(lowerCaseWord)) {
      return lowerCaseWord;
    }

    return capitalize(lowerCaseWord);
  }

  private String capitalize(String value) {
    if (value.isEmpty()) {
      return value;
    }

    char firstCharacter = Character.toUpperCase(value.charAt(0));

    if (value.length() == 1) {
      return String.valueOf(firstCharacter);
    }

    return firstCharacter + value.substring(1);
  }
}
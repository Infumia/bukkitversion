package tr.com.infumia.bukkitversion;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

/**
 * gets minecraft version from package version of the server.
 */
@Accessors(fluent = true)
public final class BukkitVersion {

  /**
   * the instance.
   */
  public static final BukkitVersion INSTANCE;

  /**
   * the major.
   */
  public static final int MAJOR;

  /**
   * the micro.
   */
  public static final int MICRO;

  /**
   * the minor.
   */
  public static final int MINOR;

  /**
   * pattern of the server text. the pattern looks like (major)_(minor)_R(micro).
   */
  @NotNull
  private static final Pattern PATTERN;

  /**
   * the matcher.
   */
  @Getter
  @NotNull
  private final Matcher matcher;

  /**
   * server version text.
   */
  @Getter
  @NotNull
  private final String version;

  static {
    INSTANCE = new BukkitVersion();
    PATTERN = Pattern.compile("v?(?<major>[0-9]+)[._](?<minor>[0-9]+)(?:[._]R(?<micro>[0-9]+))?(?<sub>.*)");
    MAJOR = BukkitVersion.INSTANCE.major();
    MICRO = BukkitVersion.INSTANCE.micro();
    MINOR = BukkitVersion.INSTANCE.minor();
  }

  /**
   * ctor.
   *
   * @param version the version.
   */
  public BukkitVersion(@NotNull final String version) {
    this.version = version;
    this.matcher = BukkitVersion.PATTERN.matcher(version);
  }

  /**
   * ctor.
   */
  public BukkitVersion() {
    this(Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3].substring(1));
  }

  /**
   * gets major part of the version.
   *
   * @return major part.
   */
  public int major() {
    return this.get("major");
  }

  /**
   * gets micro part of the version.
   *
   * @return micro part.
   */
  public int micro() {
    return this.get("micro");
  }

  /**
   * gets minor part of the version.
   *
   * @return minor part.
   */
  public int minor() {
    return this.get("minor");
  }

  /**
   * gets the part from the given key.
   *
   * @param key the key to get.
   *
   * @return the part of the given key.
   */
  private int get(@NotNull final String key) {
    return this.matcher.matches()
      ? Integer.parseInt(this.matcher.group(key))
      : 0;
  }
}

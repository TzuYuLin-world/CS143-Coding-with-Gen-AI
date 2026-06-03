package model;

/**
 * The smallest data unit: a single song.
 *
 * Design notes:
 * - playCount can only be incremented via play(); external code cannot set it directly.
 * - equals/hashCode use title + artist as the unique key.
 */
public class Song {

    private final String title;
    private final String artist;
    private int playCount;

    public Song(String title, String artist, int playCount) {
        if (title == null || title.isBlank())   throw new IllegalArgumentException("Title cannot be empty");
        if (artist == null || artist.isBlank()) throw new IllegalArgumentException("Artist cannot be empty");
        if (playCount < 0)                      throw new IllegalArgumentException("Play count cannot be negative");
        this.title     = title;
        this.artist    = artist;
        this.playCount = playCount;
    }

    /** Play once: print a message and increment playCount. */
    public void play() {
        playCount++;
        System.out.printf(">> Now playing: %s - %s  (total plays: %d)%n", title, artist, playCount);
    }

    // -- Getters ----------------------------------------------------------
    public String getTitle()   { return title; }
    public String getArtist()  { return artist; }
    public int    getPlayCount() { return playCount; }

    // -- equals / hashCode ------------------------------------------------
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Song other)) return false;
        return title.equalsIgnoreCase(other.title) &&
               artist.equalsIgnoreCase(other.artist);
    }

    @Override
    public int hashCode() {
        return 31 * title.toLowerCase().hashCode() + artist.toLowerCase().hashCode();
    }

    @Override
    public String toString() {
        return String.format("%-30s | %-20s | plays: %d", title, artist, playCount);
    }
}

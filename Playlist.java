package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/*
   A user-defined playlist.
 
   Design notes:
   - Stores Song references, not copies; playCount changes are reflected automatically.
    - Implements Playable; interchangeable with Artist and TopChart in PlayService.
    - The same song can belong to multiple playlists (many-to-many).
*/
public class Playlist implements Playable {

    private final String name;
    private final List<Song> songs;

    public Playlist(String name) {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Playlist name cannot be empty");
        this.name  = name;
        this.songs = new ArrayList<>();
    }

    /** Add a song; warns and returns false if already present. */
    public boolean addSong(Song song) {
        if (song == null) return false;
        if (songs.contains(song)) {
            System.out.println("WARNING: \"" + song.getTitle() + "\" is already in playlist \"" + name + "\"");
            return false;
        }
        songs.add(song);
        return true;
    }

    /** Remove a song object. */
    public boolean removeSong(Song song) {
        boolean removed = songs.remove(song);
        if (!removed) System.out.println("WARNING: \"" + song.getTitle() + "\" not found in playlist \"" + name + "\"");
        return removed;
    }

    /** Remove by title string (case-insensitive). */
    public boolean removeSongByTitle(String title) {
        return songs.removeIf(s -> s.getTitle().equalsIgnoreCase(title));
    }

    public boolean isEmpty() { return songs.isEmpty(); }

    // -- Playable ---------------------------------------------------------
    @Override
    public List<Song> getSongs() { return Collections.unmodifiableList(songs); }

    @Override
    public String getName() { return name; }

    @Override
    public String toString() {
        return String.format("[Playlist] %s (%d songs)", name, songs.size());
    }
}

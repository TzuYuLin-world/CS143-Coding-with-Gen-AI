package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Artist implements Playable {

    private final String name;
    private final List<Song> songs;

    public Artist(String name) {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Artist name cannot be empty!");
        this.name = name;
        this.songs = new ArrayList<>();
    }

    public void addSong(Song song) {
        if (song == null) return;
        if (!songs.contains(song)) {
            songs.add(song);
        }
    }

    public boolean removeSong(Song song) {
        return songs.remove(song);
    }

    public boolean hasSongs() {
        return !songs.isEmpty();
    }

    // ── Playable ──────────────────────────────────────────────────
    @Override
    public List<Song> getSongs() {
        return Collections.unmodifiableList(songs);
    }

    @Override
    public String getName() { return name; }

    @Override
    public String toString() {
        return String.format("Now playing:" + "[" + name + "]" + songs.size());
    }
}

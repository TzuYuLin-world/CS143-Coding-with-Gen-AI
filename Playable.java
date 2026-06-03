package model;

import java.util.List;

/**
 * Any source that can be "played" implements this interface.
 * PlayService depends only on Playable, regardless of whether
 * the source is a Playlist, Artist, or TopChart.
 */
public interface Playable {
    List<Song> getSongs();
    String getName();
}

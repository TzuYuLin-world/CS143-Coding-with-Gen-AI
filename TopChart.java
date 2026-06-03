package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A snapshot of the top-N songs ranked by play count.
 *
 * Design notes:
 * - cachedTop is a point-in-time snapshot; it does NOT update automatically.
 *   Playing songs will not change the chart until refresh() is called.
 * - topN is configurable (e.g. Top 5, Top 10).
 * - lastUpdated tells the user when the snapshot was taken.
 * - Implements Playable so PlayService can play the chart directly.
 */
public class TopChart implements Playable {

    private static final DateTimeFormatter FMT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final int topN;
    private List<Song> cachedTop;
    private LocalDateTime lastUpdated;

    public TopChart(int topN) {
        if (topN <= 0) throw new IllegalArgumentException("topN must be greater than 0");
        this.topN      = topN;
        this.cachedTop = Collections.emptyList();
        this.lastUpdated = null;
    }

    /**
     * Recalculate the chart from all songs in the library.
     * Sorts by playCount descending, then keeps the top N.
     */
    public void refresh(List<Song> allSongs) {
        cachedTop = allSongs.stream()
                .sorted(Comparator.comparingInt(Song::getPlayCount).reversed())
                .limit(topN)
                .collect(Collectors.toList());
        lastUpdated = LocalDateTime.now();
        System.out.println("Top chart updated at " + FMT.format(lastUpdated));
    }

    public boolean isStale()           { return lastUpdated == null; }
    public int     getTopN()           { return topN; }
    public String  getLastUpdatedString() {
        return lastUpdated == null ? "never" : FMT.format(lastUpdated);
    }

    // -- Playable ---------------------------------------------------------
    @Override
    public List<Song> getSongs() {
        if (isStale()) {
            System.out.println("WARNING: Top chart has never been refreshed. Please update it first.");
            return Collections.emptyList();
        }
        return Collections.unmodifiableList(cachedTop);
    }

    @Override
    public String getName() { return "Top " + topN + " Chart"; }

    @Override
    public String toString() {
        return String.format("[TopChart] Top %d  (last updated: %s)", topN, getLastUpdatedString());
    }
}

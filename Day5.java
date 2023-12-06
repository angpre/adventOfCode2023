import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class Day5 {
    public static void main(String[] args) throws IOException {
        System.out.println(part1());
        System.out.println(part2());
    }

    public static Pattern p = Pattern.compile("\\d+");

    public static long part1() throws IOException {
        BufferedReader f = Files.newBufferedReader(Paths.get("./inputs/input5"));
        String s;

        ArrayList<Long> seeds = new ArrayList<>();

        // Read seeds and save them
        Matcher m = p.matcher(f.readLine());

        while (m.find())
            seeds.add(Long.parseLong(m.group()));

        // Skip the first blank line
        s = f.readLine();

        // Process seeds
        while (s != null) {
            // Skip header
            f.readLine();

            // Array to hold map elements
            ArrayList<MapElem> map = new ArrayList<>();

            // Read map from file (until an empty line is found or the file ends)
            while ((s = f.readLine()) != null && s.length() >= 1)
                map.add(new MapElem(Arrays.stream(s.split(" ")).mapToLong(n -> Long.parseLong(n)).toArray()));

            // Update seeds
            for (int i = 0; i < seeds.size(); i++)
                for (MapElem e : map)
                    if (seeds.get(i) >= e.src && seeds.get(i) < e.src + e.range) {
                        seeds.set(i, seeds.get(i) + e.dst - e.src);
                        break;
                    }
        }

        // Return minimum
        return seeds.stream().min(Long::compare).get();
    }

    public static ArrayList<Range> split_at_boundaries(ArrayList<Range> ranges, ArrayList<MapElem> map) {
        ArrayList<Range> new_ranges = new ArrayList<>();

        // Compute map boundaries

        long map_max = 0;

        ArrayList<Long> boundaries = new ArrayList<>();

        for (MapElem e : map) {
            map_max = Math.max(map_max, e.src + e.dst);
            boundaries.add(e.src);
        }

        boundaries.add(map_max);

        boundaries.sort(Long::compare);

        boundaries.add(Long.MAX_VALUE);

        // Split ranges

        for (Range r : ranges) {
            long start = r.src;
            long range_left = r.range;

            for (int i = 0; i < boundaries.size(); i++) {
                long b = boundaries.get(i);
                if (start < b) {
                    long max_slot_range = b - start;
                    if (max_slot_range - range_left >= 0) {
                        new_ranges.add(new Range(start, range_left));
                        break;
                    } else {
                        new_ranges.add(new Range(start, max_slot_range));
                        start = b;
                        range_left -= max_slot_range;
                    }
                }
            }
        }

        return new_ranges;
    }

    public static long part2() throws IOException {
        BufferedReader f = Files.newBufferedReader(Paths.get("./inputs/input5"));
        String s;

        ArrayList<Range> ranges = new ArrayList<>();

        // Read ranges and save them
        Matcher m = p.matcher(f.readLine());

        while (m.find()) {
            long src = Long.parseLong(m.group());
            m.find();
            long range = Long.parseLong(m.group());

            ranges.add(new Range(src, range));
        }

        // Skip the first blank line
        s = f.readLine();

        // Process ranges
        while (s != null) {
            // Skip header
            f.readLine();

            // Array to hold map elements
            ArrayList<MapElem> map = new ArrayList<>();

            // Read map from file (until an empty line is found or the file ends)
            while ((s = f.readLine()) != null && s.length() >= 1)
                map.add(new MapElem(Arrays.stream(s.split(" ")).mapToLong(n -> Long.parseLong(n)).toArray()));

            // Update ranges
            ranges = split_at_boundaries(ranges, map);

            for (int i = 0; i < ranges.size(); i++) {
                Range r = ranges.get(i);
                for (MapElem e : map)
                    if (r.src >= e.src && r.src < e.src + e.range) {
                        ranges.set(i, new Range(r.src + e.dst - e.src, r.range));
                        break;
                    }
            }
        }

        // Return minimum
        return ranges.stream().map(e -> e.src).min(Long::compare).get();
    }
}

class MapElem {
    public final long dst;
    public final long src;
    public final long range;

    public MapElem(long[] arr) {
        dst = arr[0];
        src = arr[1];
        range = arr[2];
    }

    @Override
    public String toString() {
        return "{" + dst + "," + src + "," + range + "}";
    }
}

class Range {
    public final long src;
    public final long range;

    public Range(long src, long range) {
        this.src = src;
        this.range = range;
    }

    @Override
    public String toString() {
        return "(" + src + "," + range + ")";
    }
}
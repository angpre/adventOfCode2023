import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Pattern;
import java.util.ArrayList;
import java.util.regex.Matcher;

public class Day6 {
    public static void main(String[] args) throws IOException {
        System.out.println(part1());
        System.out.println(part2());
    }

    public static Pattern number = Pattern.compile("\\d+");

    private static long part1() throws IOException {
        BufferedReader f = Files.newBufferedReader(Paths.get("inputs/input6"));

        ArrayList<Integer> times = new ArrayList<>();
        ArrayList<Integer> distances = new ArrayList<>();

        Matcher m = number.matcher(f.readLine());

        while (m.find())
            times.add(Integer.parseInt(m.group()));

        m = number.matcher(f.readLine());

        while (m.find())
            distances.add(Integer.parseInt(m.group()));

        long res = 1;

        for (int i = 0; i < times.size(); i++)
            res *= ways_to_win(times.get(i), distances.get(i));

        return res;
    }

    private static long part2() throws IOException {
        BufferedReader f = Files.newBufferedReader(Paths.get("inputs/input6"));

        String time = "";
        String distance = "";

        Matcher m = number.matcher(f.readLine());

        while (m.find())
            time += m.group();

        m = number.matcher(f.readLine());

        while (m.find())
            distance += m.group();

        return ways_to_win(Long.parseLong(time), Long.parseLong(distance));

    }

    private static long ways_to_win(long time, long record) {
        /*
         * Let distance be d, speed s, holding time h, time t and record r, leftover
         * time l
         * s = h
         * d = s * l = s * (t-h) = h * (t-h) > r
         * h^2 - t*h + r < 0
         * 
         */

        double det = Math.sqrt(time * time - 4 * record);
        long s1 = (long) ((time + det) / 2);
        long s2 = (long) ((time - det) / 2);

        if (s1 * (time - s1) == record)
            s1--;

        return s1 - s2;
    }
}
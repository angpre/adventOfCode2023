import java.nio.file.Paths;
import java.io.IOException;
import java.nio.file.Files;
import java.util.regex.Pattern;
import java.io.BufferedReader;
import java.util.regex.Matcher;
import java.util.Arrays;

public class Day4 {
    private static Pattern p = Pattern.compile("\\d+");

    public static void main(String[] args) throws IOException {
        System.out.println(part1());
        System.out.println(part2());
    }

    private static int part1() throws IOException {
        return Files.lines(Paths.get("inputs/input4")).mapToInt(s -> 1 << winning_tickets(s) >> 1).sum();
    }

    private static int part2() throws IOException {
        int[] cards = new int[300];
        Arrays.fill(cards, 1);

        BufferedReader f = Files.newBufferedReader(Paths.get("./inputs/input4"));
        String s;

        int res = 0;
        int id = 1;

        while ((s = f.readLine()) != null) {
            int w = winning_tickets(s);

            for(int i=id+1; i<=id+w; i++)
                cards[i] += cards[id];

            res += cards[id++];
        }

        return res;
    }

    private static int winning_tickets(String s) {
        int[] winning = new int[10];
        int[] numbers = new int[25];

        Matcher m = p.matcher(s);
        m.find();

        for (int i = 0; i < 10; i++) {
            m.find();
            winning[i] = Integer.parseInt(m.group());
        }
        for (int i = 0; i < 25; i++) {
            m.find();
            numbers[i] = Integer.parseInt(m.group());
        }

        int result = 0;

        for (int x : winning)
            for (int y : numbers)
                if (x == y)
                    result++;

        return result;

    }
}
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * Day1
 */
public class Day1 {
    public static void main(String[] args) throws IOException {
        System.out.println(part1());
        System.out.println(part2());
    }

    private static int part1() throws IOException {
        return Files.lines(Paths.get("inputs/input1"))
                .mapToInt(s -> {
                    int[] ints = s.chars()
                            .filter(i -> i >= '0' && i <= '9')
                            .map(i -> i - '0')
                            .toArray();
                    return ints[0] * 10 + ints[ints.length - 1];
                }).sum();
    }

    private static int part2() throws IOException {
        String[] numbers = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "one", "two", "three", "four", "five", "six",
                "seven", "eight", "nine" };
        String[] numbers_rev = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "eno", "owt", "eerht", "ruof", "evif",
                "xis", "neves", "thgie", "enin" };

        int[] values = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 1, 2, 3, 4, 5, 6, 7, 8, 9 };

        Pattern p1 = Pattern.compile(String.join("|", numbers));
        Pattern p2 = Pattern.compile(String.join("|", numbers_rev));

        return Files.lines(Paths.get("inputs/input1"))
                .mapToInt(s -> {
                        int res = 0;
                        Matcher m1 = p1.matcher(s);
                        Matcher m2 = p2.matcher(new StringBuilder(s).reverse().toString());
                        m1.find();
                        m2.find();
                        res = str_to_val(m1.group(), numbers, values) * 10 +
                            str_to_val(m2.group(), numbers_rev, values);
                        return res;
                    })
                .sum();
    }

    private static int str_to_val(String str, String[] strs, int[] vals) {
        for (int i = 0; i < strs.length; i++)
            if (str.equals(strs[i]))
                return vals[i];
        return 0;
    }
}
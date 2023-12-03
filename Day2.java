import java.nio.file.Paths;
import java.io.IOException;
import java.nio.file.Files;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Day2 {
    public static void main(String[] args) throws IOException {
        System.out.println(part1());
        System.out.println(part2());
    }

    private static int part1() throws IOException {
        Pattern p = Pattern.compile("\\d+|[a-zA-z]+");

        return Files.lines(Paths.get("inputs/input2")).mapToInt(s -> {
            Matcher m = p.matcher(s);
            m.find();
            m.find();
            int id = Integer.parseInt(m.group());

            while (m.find()) {
                int n = Integer.parseInt(m.group());
                m.find();
                switch (m.group()) {
                    case "red":
                        if (n > 12)
                            return 0;
                    case "green":
                        if (n > 13)
                            return 0;
                    case "blue":
                        if (n > 14)
                            return 0;
                }
            }
            return id;
        }).sum();
    }

    private static int part2() throws IOException {
        Pattern p = Pattern.compile("\\d+|[a-zA-z]+");

        return Files.lines(Paths.get("inputs/input2")).mapToInt(s -> {
            Matcher m = p.matcher(s);
            m.find();
            m.find();

            int red, green, blue;
            red = green = blue = 0;

            while (m.find()) {
                int n = Integer.parseInt(m.group());
                m.find();
                switch (m.group()) {
                    case "red":
                        if (n > red)
                            red = n;
                            break;
                    case "green":
                        if (n > green)
                            green = n;
                            break;
                    case "blue":
                        if (n > blue)
                            blue = n;
                            break;
                }
            }
            
            return red*green*blue;
        }).sum();
    }
}

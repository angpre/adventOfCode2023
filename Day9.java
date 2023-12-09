import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

public class Day9 {
    public static void main(String[] args) throws IOException {
        System.out.println(part1());
        System.out.println(part2());
    }

    public static int part1() throws IOException {
        return Files.lines(Paths.get("inputs/input9")).mapToInt(s -> {
            ArrayList<Integer> vec = new ArrayList<>();
            Arrays.stream(s.split(" ")).mapToInt(Integer::parseInt).forEach(vec::add);

            int history = 0;

            while (!isZero(vec)) {
                history += vec.get(vec.size() - 1);
                ArrayList<Integer> new_vec = new ArrayList<>();

                for (int i = 0; i < vec.size() - 1; i++)
                    new_vec.add(vec.get(i + 1) - vec.get(i));

                vec = new_vec;
            }

            return history;
        }).sum();
    }

    public static int part2() throws IOException {
        return Files.lines(Paths.get("inputs/input9")).mapToInt(s -> {
            ArrayList<Integer> vec = new ArrayList<>();
            Arrays.stream(s.split(" ")).mapToInt(Integer::parseInt).forEach(vec::add);

            ArrayList<Integer> history = new ArrayList<>();

            while (!isZero(vec)) {
                history.add(vec.get(0));
                ArrayList<Integer> new_vec = new ArrayList<>();

                for (int i = 0; i < vec.size() - 1; i++)
                    new_vec.add(vec.get(i + 1) - vec.get(i));

                vec = new_vec;
            }

            int res = 0;

            for (int i = history.size() - 1; i >= 0; i--)
                res = history.get(i) - res;

            return res;
        }).sum();
    }

    public static boolean isZero(ArrayList<Integer> vec) {
        for (int i : vec)
            if (i != 0)
                return false;
        return true;
    }
}

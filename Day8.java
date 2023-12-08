import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.io.IOException;

public class Day8 {
    public static void main(String[] args) throws IOException {
        System.out.println(part1());
        System.out.println(part2());
    }

    public static int part1() throws IOException {
        BufferedReader f = Files.newBufferedReader(Paths.get("./inputs/input8"));

        // Read instructions
        String instructions = f.readLine();

        // Skip empty line
        f.readLine();

        // Read map
        HashMap<String, Next> map = new HashMap<>();
        String s;

        while ((s = f.readLine()) != null)
            map.put(s.substring(0, 3), new Next(s.substring(7, 10), s.substring(12, 15)));

        // Find the number of moves to get to "ZZZ"
        String curr = "AAA";
        int moves = 0;

        while (!curr.equals("ZZZ")) {
            Next dest = map.get(curr);
            char move = instructions.charAt(moves % instructions.length());
            curr = move == 'L' ? dest.l : dest.r;
            moves++;
        }

        return moves;
    }

    public static long part2() throws IOException {
        BufferedReader f = Files.newBufferedReader(Paths.get("./inputs/input8"));

        // Read instructions
        String instructions = f.readLine();

        // Skip empty line
        f.readLine();

        // Array to hold starting nodes
        ArrayList<String> current_nodes = new ArrayList<>();

        // Read map and keep track of all starting nodes
        HashMap<String, Next> map = new HashMap<>();
        String s;
        while ((s = f.readLine()) != null) {
            String from = s.substring(0, 3);
            if (from.charAt(2) == 'A')
                current_nodes.add(from);
            map.put(from, new Next(s.substring(7, 10), s.substring(12, 15)));
        }

        // Find the number of moves to get to all nodes ending with 'Z'
        long[] moves = new long[current_nodes.size()];

        for (int i = 0; i < current_nodes.size(); i++) {
            String curr = current_nodes.get(i);

            while (curr.charAt(2) != 'Z') {
                char move = instructions.charAt((int)(moves[i] % instructions.length()));
                Next dest = map.get(curr);
                curr = move == 'L' ? dest.l : dest.r;
                moves[i]++;
            }
        }

        return Arrays.stream(moves).reduce(1, Day8::lcm);
    }

    public static long lcm(long a, long b) {
        long prod = a*b;
        while(b!=0) {
            long temp = b;
            b = a % b;
            a = temp;
        }
        return prod/a;
    }
}

class Next {
    public final String l;
    public final String r;

    public Next(String l, String r) {
        this.l = l;
        this.r = r;
    }

    @Override
    public String toString() {
        return "(" + l + "," + r + ")";
    }
}
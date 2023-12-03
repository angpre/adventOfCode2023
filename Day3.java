import java.nio.file.Paths;
import java.io.IOException;
import java.nio.file.Files;
import java.io.BufferedReader;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.ArrayList;

public class Day3 {
    public static void main(String[] args) throws IOException {
        System.out.println(part1());
        System.out.println(part2());
    }

    public static int part1() throws IOException {
        ArrayList<Elem> numbers_last = null;
        ArrayList<Integer> symbols_last = null;

        Pattern num = Pattern.compile("\\d+");
        Pattern sym = Pattern.compile("[^\\d\\.]");

        BufferedReader f = Files.newBufferedReader(Paths.get("./inputs/input3"));
        String s;

        int sum = 0;

        while ((s = f.readLine()) != null) {
            Matcher m = num.matcher(s);

            ArrayList<Elem> numbers = new ArrayList<>();
            ArrayList<Integer> symbols = new ArrayList<>();

            while (m.find())
                numbers.add(new Elem(Integer.parseInt(m.group()), m.start() - 1, m.end()));

            m = sym.matcher(s);

            while (m.find())
                symbols.add(m.start());

            int res = valid_sum(numbers, symbols);
            res += valid_sum(numbers_last, symbols);
            res += valid_sum(numbers, symbols_last);

            numbers_last = numbers.size() != 0 ? numbers : null;
            symbols_last = symbols.size() != 0 ? symbols : null;

            sum += res;
        }
        ;

        return sum;
    }

    public static int part2() throws IOException {
        Pattern num = Pattern.compile("\\d+");
        Pattern sym = Pattern.compile("\\*");

        BufferedReader f = Files.newBufferedReader(Paths.get("./inputs/input3"));
        String s;

        int row = 0;

        ArrayList<Elem2> numbers = new ArrayList<>();
        ArrayList<Gear> gears = new ArrayList<>();

        while ((s = f.readLine()) != null) {
            Matcher m = num.matcher(s);

            while (m.find())
                numbers.add(new Elem2(Integer.parseInt(m.group()), m.start() - 1, m.end(), row));

            m = sym.matcher(s);

            while (m.find())
                gears.add(new Gear(m.start(), row));

            row++;
        }
        
        int sum = 0;

        for(Gear gear : gears) {
            int count = 0;
            int gear_rate = 1;

            for(int i = numbers.size() - 1; i >= 0; i--) {
                Elem2 n = numbers.get(i);
                
                if(n.row()>=gear.row()-1 && n.row()<=gear.row()+1 && gear.index() >= n.start() && gear.index()<=n.end()) {
                    count ++;
                    gear_rate *= n.val();
                }
            }

            if(count==2)
                sum += gear_rate;
        }

        return sum;
    }

    public static int valid_sum(ArrayList<Elem> xs, ArrayList<Integer> ys) {
        if (xs == null || ys == null)
            return 0;

        int sum = 0;

        for (int y : ys)
            for (int i = xs.size() - 1; i >= 0; i--)
                if (y >= xs.get(i).start() && y <= xs.get(i).end()) {
                    sum += xs.get(i).val();
                    xs.remove(i);
                }

        return sum;
    }
}

class Elem {
    private final int[] arr;

    public Elem(int val, int start, int end) {
        arr = new int[3];
        arr[0] = val;
        arr[1] = start;
        arr[2] = end;
    }

    public int val() {
        return arr[0];
    }

    public int start() {
        return arr[1];
    }

    public int end() {
        return arr[2];
    }
}

class Elem2 {
    private final int[] arr;

    public Elem2(int val, int start, int end, int row) {
        arr = new int[4];
        arr[0] = val;
        arr[1] = start;
        arr[2] = end;
        arr[3] = row;
    }

    public int val() {
        return arr[0];
    }

    public int start() {
        return arr[1];
    }

    public int end() {
        return arr[2];
    }

    public int row() {
        return arr[3];
    }
}

class Gear {
    final private int[] arr;

    public Gear(int index, int row) {
        arr = new int[2];
        arr[0] = index;
        arr[1] = row;
    }

    public int index() {
        return arr[0];
    }

    public int row() {
        return arr[1];
    }
}
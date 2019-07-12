import javafx.util.Pair;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
// comment and look over code, done but look over...

/**
 * main program to implement - interface wordData
 *
 * Author : Chris Miller
 */

public class WordDataImpl implements WordData {
    HashMap<String, HashMap<Integer, Integer>> outerMap = new HashMap<String, HashMap<Integer, Integer>>(); // the constructor for the main hashset

    public static final int UNRANKED = 0;

    public static final String NO_SUCH_WORD = "**ERROR**";

    /**
     * prints the name year and count for every word over every year
     */
    public void dumpData() {
        Collection<String> k = words();
        for (String l : k){
            String p = outerMap.get(l).toString();
            String[] parts = p.split(", ");
            for (int x = 0; x < parts.length; x++) {
                parts[x] = parts[x].replaceAll(",$", "");
                parts[x] = parts[x].replaceAll("=", " ");
                String[] parts2 = parts[x].split(" ");
                System.out.println("name = " + l + "\tyear = "+ parts2[0] + "\tcount = " + parts2[1]);
            }

        }
    }

    /**
     * takes a file name and creates a hashmap of key: name and a value: hashset key: year value: count
     *
     * @param filename
     * @throws FileNotFoundException
     */
    public WordDataImpl(String filename) throws FileNotFoundException {

        File file = new File(filename);
        ArrayList<String> list = new ArrayList<String>();

        try {
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine()) {
                String i = sc.nextLine();
                String[] parts = i.split(", ");
//                System.out.print(Arrays.toString(parts) + "\n");
                for (int x = 0; x < parts.length; x++) {
                    parts[x] = parts[x].replaceAll(",$", "");
                }

                String word = parts[0];
                int year = Integer.parseInt(parts[1]);
                int number = Integer.parseInt(parts[2]);
//                System.out.println(word + "\t" + year + "\t" + number);
                if (outerMap.containsKey(word) == true) {
                    outerMap.get(word).put(year, number);
                } else {
                    outerMap.put(word, new HashMap<Integer, Integer>());
                    outerMap.get(word).put(year, number);
                }
            }
            sc.close();
//            System.out.print(outerMap.get("airport"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    /**
     * returns a set of all the names in the main hashset
     * @return k
     */
    public Collection<String> words() {
        Set k = outerMap.keySet();
        return (k);
    }

    /**
     * returns the total number oo occurrences for every word over every year
     * @return count
     */
    public long totalWords() {
        long count = 0;
        for (String n : outerMap.keySet()) {
            for (int k : outerMap.get(n).values()) {
                count += k;
            }
        }
        return count;
    }

    /**
     * get the rank for a word over every year
     * @param word the word to be looked up
     * @return the rank of that word
     */
    public int getRankFor(String word) {
        if (outerMap.get(word) == null) { // if the word is not in the hashset : return 0;
            return 0;
        }
        ArrayList<String> words = new ArrayList<String>();
        words.addAll(outerMap.keySet()); // add all of the names in the hashmap to an arraylist of strings
//        System.out.print(words);
        final List<Pair<String, Long>> twords = new ArrayList<>(); // what will store and sort my data
        for (int i = 0; i < words.size(); i++) {
            Pair<String, Long> k = new Pair<String, Long>(words.get(i), getCountFor(words.get(i))); // for every word in the list words, add that word and the count of that word to the pair arraylist
            twords.add(k);
        }
        twords.sort(new Comparator<Pair<String, Long>>() { // sort the list based on occurance with a secondary search on the name
            @Override
            public int compare(Pair<String, Long> o1, Pair<String, Long> o2) {
                if (o1.getValue() > o2.getValue()) {
                    return -1;
                } else if (o1.getValue().equals(o2.getValue())) {
                int i = o1.getKey().compareTo(o2.getKey());
                return -i;
                } else {
                    return 1;//?
                }
            }
        });
//        System.out.println(twords);
        long compint = getCountFor(word); // if the count for the word is 0 then it is unranked so return 0;
        if (compint == UNRANKED) {
            return UNRANKED;
        } else {
        Pair<String, Long> comparePair = new Pair<String, Long>(word, compint); // construct a pair with the same name and count as the word you want the rank for and get the index of that pair.
        return (twords.indexOf(comparePair) + 1);
    }}


    public int getRankFor(String word, int year) {
        return this.getRankFor(word, year, year);
    }

    /**
     * get the rank for a word over a given set of years
     * @param word the word to be looked up
     * @param startYear the first year of the range of time desired
     * @param endYear the last year of the range of time desired
     * @return the int rank
     */
    public int getRankFor(String word, int startYear, int endYear) {
        int r = UNRANKED;
        if (outerMap.get(word) == null) { // if the word is not in the hashset : return 0;
            r = UNRANKED;
        } else {
            ArrayList<String> words = new ArrayList<String>();
            words.addAll(outerMap.keySet());// add all of the names in the hashmap to an arraylist of strings
//        System.out.print(words);
            final List<Pair<String, Long>> twords = new ArrayList<>();// what will store and sort my data
            for (int i = 0; i < words.size(); i++) {
                Pair<String, Long> k = new Pair<String, Long>(words.get(i), getCountFor(words.get(i), startYear, endYear));
                // for every word in the list words, add that word and the count of that word to the pair arraylist
                twords.add(k);
            }
            twords.sort(new Comparator<Pair<String, Long>>() {
                @Override
                public int compare(Pair<String, Long> o1, Pair<String, Long> o2) {
                    if (o1.getValue() > o2.getValue()) {
                        return -1;
                    } else if (o1.getValue().equals(o2.getValue())) {
                        int i = o1.getKey().compareTo(o2.getKey());
                        return -i;

                    } else {
                        return 1;
                    }
                }
            });
//        System.out.println(twords);
            long compint = getCountFor(word, startYear, endYear);
            if (compint == UNRANKED) {// if the count for the word is 0 then it is unranked so return 0;
                r = UNRANKED;
            } else {
                Pair<String, Long> comparePair = new Pair<String, Long>(word, compint); // construct a pair with the same name and count as the word you want the rank for and get the index of that pair.
                r = (twords.indexOf(comparePair) + 1);
            }
        }
        return r;
    }

    /**
     * get the count for this word over all years
     * @param word the word to be looked up
     * @return
     */
    public long getCountFor(String word) {
        long count = 0;
        HashMap<Integer, Integer> h = outerMap.get(word);
        for (int k : h.values()) {
            count += k;
        }
        return count;
    }

    public long getCountFor(String word, int year) {
        return this.getCountFor(word, year, year);
    }

    /**
     * get the count for the word over the given years
     * @param word the word to be looked up
     * @param startYear the first year of the range of time desired
     * @param endYear the last year of the range of time desired
     * @return long count
     */
    public long getCountFor(String word, int startYear, int endYear) {
        long count = 0;
        if (outerMap.get(word) == null) {
            return 0;
        } else if (startYear == endYear) {
            if (outerMap.get(word).get(startYear) == null) {
                return 0;
            }
            count = outerMap.get(word).get(startYear);
            return count;
        } else {
            for (int i = startYear; i <= endYear; i++) {
                if (outerMap.get(word).get(i) == null) {
                    count += 0;
                } else {
                    count += outerMap.get(word).get(i);
                }
            }
            return count;
        }
    }
}

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.*;

public class Main {

    public static void main(String[] args) {

        ArrayList <String> list = new ArrayList<>();

        File plik = new File("input.txt");
        try{
            Scanner input = new Scanner(new File("input.txt"));

            while(true){
                try{
                    String x = input.next();
                    list.add(input.next());
                }
                catch (Exception e){
                    e.printStackTrace();
                    break;
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

        String [] names = list.toArray(String[]::new);

        long startQ = System.currentTimeMillis();
        QuicksortString(names);
        long stopQ = System.currentTimeMillis();
        double qTime = (double) stopQ - (double) startQ;

        int ls = LongestString(list);
        list = AddingSpace(list, ls);

        long startC = System.currentTimeMillis();
        list = CountingSort(list,ls);
        long stopC = System.currentTimeMillis();
        double cTime = (double) stopC - (double) startC;

        System.out.println("Quick Sort Time: " + qTime);
        System.out.println("Counting Sort Time: " + cTime);


        try {
            FileWriter fileWriter = new FileWriter("results.txt");
            PrintWriter printWriter = new PrintWriter(fileWriter);
            for(String item : list) {
                printWriter.print(item);
                printWriter.print("\n");
            }
            printWriter.close();
        }
        catch (Exception e){

        }

    }
    public static ArrayList AddingSpace(ArrayList toSort, int longestString){
        for(int i=0; i<toSort.size(); i++){
            for(int j=toSort.get(i).toString().length()-1; j<longestString; j++){
                String a = toSort.get(i).toString() + " ";
                toSort.set(i,a);
            }
        }
        return toSort;
    }

    public static int LongestString (ArrayList list){
        int longestString = 0;

        for(int i=0; i<list.size(); i++){
            if(list.get(i).toString().length() > longestString){
                longestString = list.get(i).toString().length();
            }
        }
        return longestString;
    }

    public static ArrayList CountingSort(ArrayList toSort, int longestString){

        ArrayList <String> sortedList = (ArrayList<String>) toSort.clone();

        HashMap<Character, Integer> countingMap = new HashMap<>();

        for(int j=longestString-1; j>=0; j--) {

            countingMap.put((char)32,0);
            for (int i = 97; i <= 122; i++) {
                countingMap.put((char) i, 0);
            }

            for (int i = 0; i < toSort.size(); i++) {
                char a = toSort.get(i).toString().toLowerCase().charAt(j);
                countingMap.put(a, countingMap.get(a) + 1);
            }

            countingMap.put((char) 97, countingMap.get((char) (97)) + countingMap.get((char) 32));

            for (int i = 98; i <= 122; i++) {
                countingMap.put((char) i, (countingMap.get((char) (i - 1)) + countingMap.get((char) i)));
            }


            for (int i = toSort.size() - 1; i >= 0; i--) {
                char a = toSort.get(i).toString().toLowerCase().charAt(j);
                int value = countingMap.get(a);
                sortedList.set((countingMap.get(a) - 1), toSort.get(i).toString());
                countingMap.put(a, countingMap.get(a) - 1);
            }

            toSort = (ArrayList) sortedList.clone();

        }
        return sortedList;
    }

    public static int partition(String[] stringArray, int idx1, int idx2) {
        String pivotValue = stringArray[idx1];
        while (idx1 < idx2) {
            String value1;
            String value2;
            while ((value1 = stringArray[idx1]).compareTo( pivotValue ) < 0) {
                idx1 = idx1 + 1;
            }
            while ((value2 = stringArray[idx2]).compareTo( pivotValue ) > 0) {
                idx2 = idx2 - 1;
            }
            stringArray[idx1] = value2;
            stringArray[idx2] = value1;
        }
        return idx1;
    }
    public static void QuicksortString(String[] stringArray, int idx1, int idx2) {
        if (idx1 >= idx2) {
            // we are done
            return;
        }
        int pivotIndex = partition(stringArray, idx1, idx2);
        QuicksortString(stringArray, idx1, pivotIndex);
        QuicksortString(stringArray, pivotIndex+1, idx2);
    }
    public static void QuicksortString(String[] stringArray) {
        QuicksortString(stringArray, 0, stringArray.length - 1);
    }
}

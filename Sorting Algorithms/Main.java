import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;


/*

* Hocam kodumu deneyeceğnizi düşünürek test yaptığım değerleri değiştirdim. Çünkü 4 saatten fazla sürdü
* ve hızlı çalıştımanız için böyle yaptım. Paporumu HOW_MANY_SIZE = 8  ve HOW_MANY_SAMPLE = 32 durumu için yazdım
* Report klasörünün altında .txt ve .csv halini bulabilirsiniz. txt'yi program kendi oluşturdu
* Dataları csv ye ben yapıştırıp düzenledim grafik çizebilmek için.
*
* */


public class Main {
    private static final int HOW_MANY_SIZE = 1;                   //KAÇ FARKLI SİZEDA ARRAY VAR?
    private static final int START_SIZE = (int)Math.pow(2,6);   //EN DÜŞÜK ARRAY SİZE
    private static final int END_SIZE = START_SIZE * (int)Math.pow(2, HOW_MANY_SIZE - 1);       //EN YÜKSEK ARRAY
    private static final int HOW_MANY_SAMPLES = 16;             //DENEY TEKRAR SAYISI
    public static void main(String[] args) {
        ListObject[][] stabilityCheck = new ListObject[3][HOW_MANY_SAMPLES]; //DISTAN ICE ARRAYLERIN BASTAKI SORT DURUMU - KAC FARKLI AYNI DURUM VAR
        for (int sample = 0; sample < HOW_MANY_SAMPLES; sample++) {
            stabilityCheck[0][sample] = new ListObject(512, 0, true);
            stabilityCheck[1][sample] = new ListObject(512, 1, true);
            stabilityCheck[2][sample] = new ListObject(512, -1, true);
        }
        boolean[] stable = new boolean[5];
        for (ListObject[] listObjects : stabilityCheck) {
            for (ListObject listObject : listObjects) {
                Algorithms.gnomeSort(listObject.list);
            }
        }
        stable[0] = checkStabilityandReset(stabilityCheck);             //buradan itibaren stable check yaptım
        for (ListObject[] listObjects : stabilityCheck) {
            for (ListObject listObject : listObjects) {
                Algorithms.stooge(listObject.list);
            }
        }
        stable[1] = checkStabilityandReset(stabilityCheck);
        for (ListObject[] listObjects : stabilityCheck) {
            for (ListObject listObject : listObjects) {
                Algorithms.combSort(listObject.list);
            }
        }
        stable[2] = checkStabilityandReset(stabilityCheck);
        for (ListObject[] listObjects : stabilityCheck) {
            for (ListObject listObject : listObjects) {
                Algorithms.shakerSort(listObject.list);
            }
        }
        stable[3] = checkStabilityandReset(stabilityCheck);
        for (ListObject[] listObjects : stabilityCheck) {
            for (ListObject listObject : listObjects) {
                Algorithms.bitonic(listObject.list);
            }
        }
        stable[4] = checkStabilityandReset(stabilityCheck);
        for (boolean b : stable) {
            System.out.println(b);
        }
        ListObject[][][] arrays = new ListObject[HOW_MANY_SIZE][3][HOW_MANY_SAMPLES]; //DISTAN ICE ARRAYLERIN BOYUTU(TERSTEN KONULDU EN BUYUK EN BASTA) - ARRAYLERIN BASTAKI SORT DURUMU - KAC FARKLI AYNI DURUM VAR
        for (int howManyElements = END_SIZE, index = 0; howManyElements >= START_SIZE; howManyElements /= 2, index++) {
            for (int sample = 0; sample < HOW_MANY_SAMPLES; sample++) {
                arrays[index][0][sample] = new ListObject(howManyElements, 0, false);
                arrays[index][1][sample] = new ListObject(howManyElements, 1, false);
                arrays[index][2][sample] = new ListObject(howManyElements, -1, false);
            }
        }
        long[][][][] results = new long[5][HOW_MANY_SIZE][3][HOW_MANY_SAMPLES];     //DISTAN ICE SORTLARIN SAYISI - ARRAYLERIN BOYUTU - ARRAYLERIN BASTAKI SORT DURUMU - KAC FARKLI AYNI DURUM VAR
        for (int index = 0; index < arrays.length; index++) {                       //for loop for looping array size
            for(int order = 0; order < 3; order++){                                 //for loop for cases
                for (int sample = 0; sample < HOW_MANY_SAMPLES; sample++) {         //for loop for each element in an array
                    long startTime = System.nanoTime();                             //startTime getting
                    Algorithms.gnomeSort(arrays[index][order][sample].list);        //executing 1st algo
                    long endTime = System.nanoTime();                               //endTime getting
                    results[0][index][order][sample] = endTime - startTime;         //appending result array with execution time for this sorting
                    arrays[index][order][sample].reset();                           //resetting array
                }
            }
        }
        for (int index = 0; index < arrays.length; index++) {
            for(int order = 0; order < 3; order++){
                for (int sample = 0; sample < HOW_MANY_SAMPLES; sample++) {
                    long startTime = System.nanoTime();
                    Algorithms.stooge(arrays[index][order][sample].list);
                    long endTime = System.nanoTime();
                    results[1][index][order][sample] = endTime - startTime;
                    arrays[index][order][sample].reset();
                }
            }
        }
        for (int index = 0; index < arrays.length; index++) {
            for(int order = 0; order < 3; order++){
                for (int sample = 0; sample < HOW_MANY_SAMPLES; sample++) {
                    long startTime = System.nanoTime();
                    Algorithms.combSort(arrays[index][order][sample].list);
                    long endTime = System.nanoTime();
                    results[2][index][order][sample] = endTime - startTime;
                    arrays[index][order][sample].reset();
                }
            }
        }
        for (int index = 0; index < arrays.length; index++) {
            for(int order = 0; order < 3; order++){
                for (int sample = 0; sample < HOW_MANY_SAMPLES; sample++) {
                    long startTime = System.nanoTime();
                    Algorithms.shakerSort(arrays[index][order][sample].list);
                    long endTime = System.nanoTime();
                    results[3][index][order][sample] = endTime - startTime;
                    arrays[index][order][sample].reset();
                }
            }
        }
        for (int index = 0; index < arrays.length; index++) {
            for(int order = 0; order < 3; order++){
                for (int sample = 0; sample < HOW_MANY_SAMPLES; sample++) {
                    long startTime = System.nanoTime();
                    Algorithms.bitonic(arrays[index][order][sample].list);
                    long endTime = System.nanoTime();
                    results[4][index][order][sample] = endTime - startTime;
                    arrays[index][order][sample].reset();
                }
            }
        }
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
        LocalDateTime now = LocalDateTime.now();
        PrintStream resultStream;
        try {
            resultStream = new PrintStream(String.format("results_%s.txt", dtf.format(now)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }

        //Dosyaya düzgün bastırmak için burayı yazdım
        //DISTAN ICE SORTLARIN SAYISI - ARRAYLERIN BOYUTU - ARRAYLERIN BASTAKI SORT DURUMU - KAC FARKLI AYNI DURUM VAR
        resultStream.println("***GNOME SORT***");
        int endSize = END_SIZE;
        for (long[][] result : results[0]) {
            resultStream.println(endSize);
            endSize /= 2;
            for (long[] longs : result) {
                for (long l : longs) {
                    resultStream.print(l);
                    resultStream.print(",");
                }
                resultStream.print("AVERAGE: ");
                resultStream.println(Math.round(Arrays.stream(longs).average().getAsDouble()));
            }
            resultStream.println("\n");
        }
        resultStream.println("***END OF THE SORT***\n\n\n");
        resultStream.println("***STOOGE SORT***");
        endSize = END_SIZE;
        for (long[][] result : results[1]) {
            resultStream.println(endSize);
            endSize /= 2;
            for (long[] longs : result) {
                for (long l : longs) {
                    resultStream.print(l);
                    resultStream.print(",");
                }
                resultStream.print("AVERAGE: ");
                resultStream.println(Math.round(Arrays.stream(longs).average().getAsDouble()));
            }
            resultStream.println("\n");
        }
        resultStream.println("***END OF THE SORT***\n\n\n");
        resultStream.println("***COMB SORT***");
        endSize = END_SIZE;
        for (long[][] result : results[2]) {
            resultStream.println(endSize);
            endSize /= 2;
            for (long[] longs : result) {
                for (long l : longs) {
                    resultStream.print(l);
                    resultStream.print(",");
                }
                resultStream.print("AVERAGE: ");
                resultStream.println(Math.round(Arrays.stream(longs).average().getAsDouble()));
            }
            resultStream.println("\n");
        }
        resultStream.println("***END OF THE SORT***\n\n\n");
        resultStream.println("***SHAKER SORT***");
        endSize = END_SIZE;
        for (long[][] result : results[3]) {
            resultStream.println(endSize);
            endSize /= 2;
            for (long[] longs : result) {
                for (long l : longs) {
                    resultStream.print(l);
                    resultStream.print(",");
                }
                resultStream.print("AVERAGE: ");
                resultStream.println(Math.round(Arrays.stream(longs).average().getAsDouble()));
            }
            resultStream.println("\n");
        }
        resultStream.println("***END OF THE SORT***\n\n\n");
        resultStream.println("***BITONIC SORT***");
        endSize = END_SIZE;
        for (long[][] result : results[4]) {
            resultStream.println(endSize);
            endSize /= 2;
            for (long[] longs : result) {
                for (long l : longs) {
                    resultStream.print(l);
                    resultStream.print(",");
                }
                resultStream.print("AVERAGE: ");
                resultStream.println(Math.round(Arrays.stream(longs).average().getAsDouble()));
            }
            resultStream.println("\n");
        }
        resultStream.println("***END OF THE SORT***\n\n\n");
        resultStream.flush();
        resultStream.close();

    }
    private static boolean checkStabilityandReset(ListObject[][] stabilityCheck){
        boolean stable = true;
        for (ListObject[] listObjects : stabilityCheck) {
            for (ListObject listObject : listObjects) {
                if(stable){
                    stable = listObject.checkStability();
                }
                listObject.reset();
            }
        }
        return stable;
    }
}
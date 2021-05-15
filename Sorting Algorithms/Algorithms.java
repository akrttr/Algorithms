public class Algorithms {
    public static void gnomeSort(ListElement[] list) {

        int pos = 0;
        while (pos < list.length) {

            if (pos == 0 || list[pos].getValue() >= list[pos - 1].getValue()) {
                pos = pos + 1;
            } else {
                ListElement tmp;
                tmp = list[pos];
                list[pos] = list[pos - 1];
                list[pos - 1] = tmp;
                pos = pos - 1;
            }
        }
    }

    public static void stoogesort(ListElement[] list, int leftMost, int rightMost) {
        if (list[leftMost].getValue() > list[rightMost].getValue()) {
            ListElement tmp = list[leftMost];
            list[leftMost] = list[rightMost];
            list[rightMost] = tmp;
        }

        if ((rightMost - leftMost + 1) > 2) {

            int tmp = (rightMost - leftMost + 1) / 3;
            stoogesort(list, leftMost, rightMost - tmp);
            stoogesort(list, leftMost + tmp, rightMost);
            stoogesort(list, leftMost, rightMost - tmp);
        }
    }

    public static void stooge(ListElement[] list){
        stoogesort(list, 0, list.length-1 );
    }

    public static void combSort(ListElement[] list) {

        int gap = list.length;
        double shrink = 1.3;
        boolean sorted = false;

        while (!sorted) {

            gap = (int) (gap / shrink);
            if (gap <= 1) {

                gap = 1;
                sorted = true;
            }

            int i = 0;

            while (i + gap < list.length) {

                if (list[i].getValue() > list[i + gap].getValue()) {
                    ListElement tmp = list[i];
                    list[i] = list[i + gap];
                    list[i + gap] = tmp;
                    sorted = false;

                }
                i = i + 1;
            }
        }
    }

    public static void shakerSort(ListElement[] list) {
        boolean sorted = true;
        int start = 0;
        int end = list.length;

        while (sorted) {
            sorted = false;

            for (int i = 0; i < list.length - 1; i++) {

                if (list[i].getValue() > list[i + 1].getValue()) {
                    ListElement tmp = list[i];
                    list[i] = list[i + 1];
                    list[i + 1] = tmp;
                    sorted = true;

                }

            }
            if (sorted == false) {
                break;
            }


            sorted = false;

            end = end - 1;

            for (int i = end - 1; i >= 0; i--) {
                if (list[i].getValue() > list[i + 1].getValue()) {
                    ListElement tmp = list[i];
                    list[i] = list[i + 1];
                    list[i + 1] = tmp;
                    sorted = true;

                }
            }
            start = start + 1;

        }
    }

    public static void compAndSwap(ListElement[] a, int i, int j, int dir) {
        if ((a[i].getValue() > a[j].getValue() && dir == 1) ||
                (a[i].getValue() < a[j].getValue() && dir == 0)) {
            // Swapping elements
            ListElement temp = a[i];
            a[i] = a[j];
            a[j] = temp;
        }
    }

    public static void bitonicMerge(ListElement[] a, int low, int cnt, int dir) {
        if (cnt > 1) {
            int k = cnt / 2;
            for (int i = low; i < low + k; i++)
                compAndSwap(a, i, i + k, dir);
            bitonicMerge(a, low, k, dir);
            bitonicMerge(a, low + k, k, dir);
        }
    }

    public static void bitonicSort(ListElement[] a, int low, int cnt, int dir) {
        if (cnt > 1) {
            int k = cnt / 2;

            // sort in ascending order since dir here is 1
            bitonicSort(a, low, k, 1);

            // sort in descending order since dir here is 0
            bitonicSort(a, low + k, k, 0);

            // Will merge wole sequence in ascending order
            // since dir=1.
            bitonicMerge(a, low, cnt, dir);
        }
    }

    public static void bitonic(ListElement[] a){
        bitonicSort(a, 0, a.length , 1);
    }


}

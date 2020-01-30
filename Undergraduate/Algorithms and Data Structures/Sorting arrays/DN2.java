import java.util.Scanner;

public class DN2 {

    public static int[] zaporedje;
    public static int premikiHeap;
    public static int primerjaveHeap;
    public static int premikiMerge = 0;
    public static int primerjaveMerge = 0;
    public static boolean mergeTrace = false, quickTrace = false;
    public static int premikiQuick = 0;
    public static int primerjaveQuick = 0;
    public static int premikiBucket = 0;
    public static int primerjaveBucket = 0;
    public static int premikiRadix = 0;
    public static int primerjaveRadix = 0;

    public static void main(String[] args) {
        //Ustvarjanje zaporedja
        Scanner sc = new Scanner(System.in);
        String[] nastavitve = sc.nextLine().split("\\s+");
        String[] zaporedjeS = sc.nextLine().split("\\s+");
        int[] sortedArray;
        zaporedje = new int[zaporedjeS.length];
        for (int i = 0; i < zaporedjeS.length; i++)
            zaporedje[i] = Integer.parseInt(zaporedjeS[i]);
        switch(nastavitve[1]) {
            case "insert":
                if (nastavitve[0].equals("count")){
                    sortedArray = insertSort(zaporedje, nastavitve[2], "count", false);
                    System.out.print(" | ");
                    insertSort(sortedArray, nastavitve[2], "count", false);
                    System.out.print(" | ");
                    if (nastavitve[2].equals("up")) insertSort(sortedArray, "down", "count", false);
                    else insertSort(zaporedje, "up", "count", false);

                }
                else selectSort(zaporedje, nastavitve[2], nastavitve[0]);
                break;
            case "select":
                if (nastavitve[0].equals("count")){
                    sortedArray = selectSort(zaporedje, nastavitve[2], "count");
                    System.out.print(" | ");
                    selectSort(sortedArray, nastavitve[2], "count");
                    System.out.print(" | ");
                    if (nastavitve[2].equals("up")) selectSort(sortedArray, "down", "count");
                    else selectSort(zaporedje, "up", "count");
                }
                else selectSort(zaporedje, nastavitve[2], nastavitve[0]);
                break;
            case "bubble":
                if (nastavitve[0].equals("trace")) bubbleSort(zaporedje, nastavitve[2], "trace");
                else if (nastavitve[0].equals("count")) {
                    sortedArray = bubbleSort(zaporedje, nastavitve[2], "count");
                    System.out.print(" | ");
                    bubbleSort(sortedArray, nastavitve[2], "count");
                    System.out.print(" | ");
                    if (nastavitve[2].equals("up")) bubbleSort(sortedArray, "down", "count");
                    else bubbleSort(zaporedje, "up", "count");
                }
                break;
            case "heap":
                primerjaveHeap = 0; premikiHeap = 0;
                if (nastavitve[0].equals("trace")) heapSort(zaporedje, nastavitve[2], nastavitve[0]);
                else if (nastavitve[0].equals("count")) {
                    sortedArray = heapSort(zaporedje, nastavitve[2], "count");
                    System.out.print(" | ");
                    heapSort(sortedArray, nastavitve[2], "count");
                    System.out.print(" | ");
                    if (nastavitve[2].equals("up")) heapSort(sortedArray, "down", "count");
                    else heapSort(zaporedje, "up", "count");
                }
                break;
            case "merge":
                if (nastavitve[0].equals("trace")) {
                    mergeTrace = true;
                    if (nastavitve[2].equals("up")) mergeSortUp(zaporedje, 0, zaporedje.length - 1);
                    else if (nastavitve[2].equals("down")) mergeSortDown(zaporedje, 0, zaporedje.length - 1);
                }
                else if (nastavitve[0].equals("count")) {
                    if (nastavitve[2].equals("up")) {
                        sortedArray = mergeSortUp(zaporedje, 0, zaporedje.length -1 );
                        System.out.print(premikiMerge + " " + primerjaveMerge + " | ");
                        primerjaveMerge = 0; premikiMerge = 0;
                        int[] sortedArray2 =  mergeSortUp(sortedArray, 0, sortedArray.length - 1);
                        System.out.print(premikiMerge + " " + primerjaveMerge + " | ");
                        primerjaveMerge = 0; premikiMerge = 0;
                        mergeSortDown(sortedArray2, 0, sortedArray.length - 1);
                        System.out.println(premikiMerge + " " + primerjaveMerge);
                    }
                    else if (nastavitve[2].equals("down")) {
                        sortedArray = mergeSortDown(zaporedje, 0, zaporedje.length -1 );
                        System.out.print(premikiMerge + " " + primerjaveMerge + " | ");
                        primerjaveMerge = 0; premikiMerge = 0;
                        int[] sortedArray2 =  mergeSortDown(sortedArray, 0, sortedArray.length - 1);
                        System.out.print(premikiMerge + " " + primerjaveMerge + " | ");
                        primerjaveMerge = 0; premikiMerge = 0;
                        mergeSortUp(sortedArray2, 0, sortedArray.length - 1);
                        System.out.println(premikiMerge + " " + primerjaveMerge);
                    }
                }
                break;
            case "bucket":
                if (nastavitve[0].equals("trace")) bucketSort(zaporedje, nastavitve[2], nastavitve[0]);
                else if (nastavitve[0].equals("count")) {
                    sortedArray = bucketSort(zaporedje, nastavitve[2], "count");
                    //printArray(sortedArray, -1);
                    System.out.print(" | ");
                    bucketSort(sortedArray, nastavitve[2], "count");
                    System.out.print(" | ");
                    if (nastavitve[2].equals("up")) bucketSort(sortedArray, "down", "count");
                    else bucketSort(sortedArray, "up", "count");
                }
                break;
            case "quick":
                if (nastavitve[0].equals("trace")) {
                    quickTrace = true;
                    printArray(zaporedje, -1);
                    quickSort(zaporedje, 0, zaporedje.length - 1, nastavitve[2]);
                    printArray(zaporedje, -1);
                }
                else if (nastavitve[0].equals("count")) {
                    quickSort(zaporedje, 0, zaporedje.length - 1, nastavitve[2]);
                    System.out.print(premikiQuick + " " + primerjaveQuick + " | ");
                    premikiQuick = 0; primerjaveQuick = 0;
                    quickSort(zaporedje, 0, zaporedje.length - 1, nastavitve[2]);
                    System.out.print(premikiQuick + " " + primerjaveQuick + " | ");
                    premikiQuick = 0; primerjaveQuick = 0;
                    if (nastavitve[2].equals("up")) quickSort(zaporedje, 0, zaporedje.length - 1, "down");
                    else quickSort(zaporedje, 0, zaporedje.length - 1, "up");
                    System.out.print(premikiQuick + " " + primerjaveQuick);
                }
                break;
            case "radix":
                if (nastavitve[0].equals("trace")) radixSort(zaporedje, nastavitve[2], "trace");
                else if (nastavitve[0].equals("count")) {
                    radixSort(zaporedje, nastavitve[2], "count");
                    System.out.print(premikiRadix + " " + primerjaveRadix + " | ");
                    premikiRadix = 0; primerjaveRadix = 0;
                    radixSort(zaporedje, nastavitve[2], "count");
                    System.out.print(premikiRadix + " " + primerjaveRadix + " | ");
                    premikiRadix = 0; primerjaveRadix = 0;
                    if (nastavitve[2].equals("up")) radixSort(zaporedje, "down", "count");
                    else radixSort(zaporedje, "up", "count");
                    System.out.print(premikiRadix + " " + premikiRadix);
                }
                break;
        }
    }

    public static int[] insertSort(int[] array, String smer, String prvi, boolean bucket) {
        boolean traceBool = false, countBool = false;
        int premiki = 0, primerjave = 0;
        if (prvi.equals("trace")) traceBool = true;
        if(prvi.equals("count")) countBool = true;
        if (traceBool && !bucket) printArray(array, -1);
        for (int i = 1; i < array.length; i++) {
            int trenutni = array[i];
            premikiBucket++;
            premiki++;
            int j = i - 1;
            if (smer.equals("up")) {
                while (j >= 0) {
                    primerjave++;
                    primerjaveBucket++;
                    if (trenutni < array[j]) {
                        array[j + 1] = array[j];
                        premikiBucket++;
                        premiki++;
                    } else break;
                    j = j - 1;
                }
            }
            else if (smer.equals("down")) {
                while (j >= 0) {
                    primerjave++;
                    primerjaveBucket++;
                    if (trenutni > array[j]){
                        array[j + 1] = array[j];
                        premikiBucket++;
                        premiki++;
                    }
                    else break;
                    j = j - 1;
                }
            }
            array[j + 1] = trenutni;
            premikiBucket++;
            premiki++;
            if (traceBool) printArray(array, i + 1);
        }
        if (countBool && !bucket) System.out.print(premiki + " " + primerjave);
        return array;
    }

    public static int[] selectSort(int[] array, String smer, String prvi) {
        boolean traceBool = false, countBool = false;
        int premiki = 0, primerjave = 0;
        if (prvi.equals("trace")) traceBool = true;
        if(prvi.equals("count")) countBool = true;

        if (traceBool) printArray(array, -1);
        for (int i = 0; i < array.length - 1; i++) {
            int iskaniIndex = i;
            for (int j = i + 1; j < array.length; j++) {
                primerjave++;
                if (smer.equals("up")) {
                    if (array[j] < array[iskaniIndex])
                        iskaniIndex = j;
                }
                else if (smer.equals("down")) {
                    if (array[j] > array[iskaniIndex])
                        iskaniIndex = j;
                }
            }
            int temp = array[iskaniIndex];
            array[iskaniIndex] = array[i];
            array[i] = temp;
            premiki += 3;
            if (traceBool) printArray(array, i + 1);
        }
        if (countBool) System.out.print(premiki + " " + primerjave);
        return array;
    }

    public static int[] bubbleSort(int[] array, String smer, String prvi) {
        boolean traceBool = false, countBool = false;
        if (prvi.equals("trace")) traceBool = true;
        if(prvi.equals("count")) countBool = true;

        if (traceBool) printArray(array, -1);
        int temp, lastSwapped = 0, i = 0, primerjave = 0, premiki = 0;
        boolean swapped;
        while (i < array.length - 1) {
            swapped = false;
            for (int j = array.length - 1; j > i; j--) {
                if (smer.equals("up")) {
                    primerjave++;
                    if (array[j] < array[j - 1]) {
                        temp = array[j];
                        array[j] = array[j - 1];
                        array[j - 1] = temp;
                        premiki += 3;

                        lastSwapped = j;
                        swapped = true;
                    }
                }
                else if (smer.equals("down")) {
                    primerjave++;
                    if (array[j] > array[j - 1]) {
                        temp = array[j];
                        array[j] = array[j - 1];
                        array[j - 1] = temp;
                        premiki += 3;

                        lastSwapped = j;
                        swapped = true;
                    }
                }
            }
            if (!swapped && i < array.length - 2) {
                if (traceBool) printArray(array, array.length - 1);
                else if (countBool) System.out.print(premiki + " " + primerjave);
                return array; }
            if (i + 1 != lastSwapped && i < array.length - 2) {
                i = lastSwapped - 1;
                if (traceBool) printArray(array, i + 1);
            }
            else if (i < array.length - 1 && traceBool) printArray(array, i + 1);
            i++;
        }
        if (countBool) System.out.print(premiki + " " + primerjave);
        return array;
    }

    public static int[] heapSort(int[] array, String smer, String prvi) {
        premikiHeap = 0;
        primerjaveHeap = 0;
        boolean traceBool = false, countBool = false, swapped;
        if (prvi.equals("trace")) traceBool = true;
        if(prvi.equals("count")) countBool = true;

        if (traceBool) printArray(array, -1);
        for (int i = (array.length / 2) - 1; i >= 0; i--){
            if (smer.equals("up")) kopicaMin(array, array.length, i);
            else if (smer.equals("down")) kopicaMax(array, array.length, i);
        }
        if (traceBool) printArray(array, array.length);
        for (int i = array.length - 1; i > 0; i--) {
            int temp = array[0];
            array[0] = array[i];
            array[i] = temp;
            premikiHeap += 3;

            if (smer.equals("up")) kopicaMin(array, i, 0);
            else if (smer.equals("down")) kopicaMax(array, i, 0);
            if (i != 0 && traceBool) printArray(array, i);
        }
        if (countBool) System.out.print(premikiHeap + " " + primerjaveHeap);
        return array;
    }

    public static void kopicaMin(int[] array, int size, int node) {
        int extreme = node;
        int desni = 2 * node + 2;
        int levi = 2 * node + 1;
        if (levi < size) {
            primerjaveHeap++;
            if (array[levi] > array[extreme]) extreme = levi;
        }
        if (desni < size) {
            primerjaveHeap++;
            if (array[desni] > array[extreme]) extreme = desni;
        }
        if (extreme != node) {
            int temp = array[node];
            array[node] = array[extreme];
            array[extreme] = temp;
            premikiHeap += 3;

            kopicaMin(array, size, extreme);
        }
    }

    public static void kopicaMax(int[] array, int size, int node) {
        int extreme = node;
        int desni = 2 * node + 2;
        int levi = 2 * node + 1;
        if (levi < size) {
            primerjaveHeap++;
            if (array[levi] < array[extreme])
                extreme = levi;
        }
        if (desni < size) {
            primerjaveHeap++;
            if (array[desni] < array[extreme])
                extreme = desni;
        }
        if (extreme != node) {
            int temp = array[node];
            array[node] = array[extreme];
            array[extreme] = temp;
            premikiHeap += 3;

            kopicaMax(array, size, extreme);
        }
    }

    public static void mergeUp(int[] array, int leva, int sredina, int desna) {
        int size1 = sredina - leva + 1;
        int size2 = desna - sredina;
        int[] arrayLevo = new int[size1];
        int[] arrayDesno = new int[size2];
        for (int i = 0; i < size1; i++) {
            arrayLevo[i] = array[leva + i];
            premikiMerge++;
        }
        for (int i = 0; i < size2; i++) {
            arrayDesno[i] = array[sredina + 1 + i];
            premikiMerge++;
        }
        int index1 = 0, index2 = 0, indexMerged = leva;
        while (index1 < size1 && index2 < size2) {
            primerjaveMerge++;
            if (arrayLevo[index1] <= arrayDesno[index2]) {
                premikiMerge++;
                array[indexMerged] = arrayLevo[index1];
                index1++;
            }
            else {
                premikiMerge++;
                array[indexMerged] = arrayDesno[index2];
                index2++;
            }
            indexMerged++;
        }
        while (index1 < size1) {
            premikiMerge++;
            array[indexMerged] = arrayLevo[index1];
            index1++;
            indexMerged++;
        }
        while (index2 < size2) {
            premikiMerge++;
            array[indexMerged] = arrayDesno[index2];
            index2++;
            indexMerged++;
        }
        if (mergeTrace) printMergeArray(array, leva, desna);
    }

    public static int[] mergeSortUp(int[] array, int leva, int desna) {
        if (leva == 0 && desna == array.length - 1 && mergeTrace) printArray(array,   -1);
        if (leva < desna) {
            int sredina = (leva + desna) / 2;
            for (int i = leva; i <= sredina; i++) {
                if (mergeTrace) System.out.print(array[i] + " ");
            }
            if (mergeTrace) System.out.print("| ");
            for (int i = sredina + 1; i <= desna; i++) {
                if (mergeTrace) System.out.print(array[i] + " ");
            }
            if (mergeTrace) System.out.println("");
            mergeSortUp(array, leva, sredina);
            mergeSortUp(array, sredina + 1, desna);

            mergeUp(array, leva, sredina, desna);
        }
        return array;
    }

    public static void mergeDown(int[] array, int leva, int sredina, int desna) {
        int size1 = sredina - leva + 1;
        int size2 = desna - sredina;
        int[] arrayLevo = new int[size1];
        int[] arrayDesno = new int[size2];
        for (int i = 0; i < size1; i++) {
            arrayLevo[i] = array[leva + i];
            premikiMerge++;
        }
        for (int i = 0; i < size2; i++) {
            arrayDesno[i] = array[sredina + 1 + i];
            premikiMerge++;
        }
        int index1 = 0, index2 = 0, indexMerged = leva;
        while (index1 < size1 && index2 < size2) {
            primerjaveMerge++;
            if (arrayLevo[index1] >= arrayDesno[index2]) {
                premikiMerge++;
                array[indexMerged] = arrayLevo[index1];
                index1++;
            }
            else {
                premikiMerge++;
                array[indexMerged] = arrayDesno[index2];
                index2++;
            }
            indexMerged++;
        }
        while (index1 < size1) {
            premikiMerge++;
            array[indexMerged] = arrayLevo[index1];
            index1++;
            indexMerged++;
        }
        while (index2 < size2) {
            premikiMerge++;
            array[indexMerged] = arrayDesno[index2];
            index2++;
            indexMerged++;
        }
        if (mergeTrace) printMergeArray(array, leva, desna);
    }

    public static int[] mergeSortDown(int[] array, int leva, int desna) {
        if (leva == 0 && desna == array.length - 1 && mergeTrace) printArray(array, -1);
        if (leva < desna) {
            int sredina = (leva + desna) / 2;

            for (int i = leva; i <= sredina; i++) {
                if (mergeTrace) System.out.print(array[i] + " ");
            }
            if (mergeTrace) System.out.print("| ");
            for (int i = sredina + 1; i <= desna; i++) {
                if (mergeTrace) System.out.print(array[i] + " ");
            }
            if (mergeTrace) System.out.println("");
            mergeSortDown(array, leva, sredina);
            mergeSortDown(array, sredina + 1, desna);

            mergeDown(array, leva, sredina, desna);
        }
        return array;
    }

    public static int[] bucketSort(int[] array, String smer, String prvi) {
        boolean traceBool = false, countBool = false;
        if (prvi.equals("trace")) traceBool = true;
        if(prvi.equals("count")) countBool = true;

        premikiBucket = 0; primerjaveBucket = 0;
        if (traceBool) printArray(array, -1);
        int stKosev = array.length / 2, min = array[0], max = array[0], counterFinal = 0;
        int[] c = new int[stKosev], arrayFinal = new int[array.length];
        int[][] kosi = new int[stKosev][0];
        //Najdemo min in max v arrayu
        for (int i = 1; i < array.length; i++) {
            primerjaveBucket++;
            if (array[i] < min) {
                min = array[i];
                continue;
            }
            else if (array[i] > max)
                max = array[i];
            primerjaveBucket++;
        }
        double v = (max - min + 1) / (double)stKosev, l;
        //V tabelo damo koliko elementov bo v posameznem košu
        for (int i = 0; i < array.length; i++) {
            primerjaveBucket++;
            l = Math.floor((array[i] - min) / v);
            c[(int)l]++;
        }
        //Za vsak koš naredimo svoj array in dodamo elemente
        for (int i = 0; i < array.length; i++) {
            primerjaveBucket++;
            l = Math.floor((array[i] - min) / v);
            premikiBucket++;
            kosi[(int)l] = arrayAdd(kosi[(int)l], array[i]);
        }
        if (smer.equals("up")) {
            for (int i = 0; i < kosi.length; i++) {
                for (int j = 0; j < kosi[i].length; j++) {
                    if (traceBool) System.out.print(kosi[i][j] + " ");
                    arrayFinal[counterFinal] = kosi[i][j];
                    premikiBucket++;
                    counterFinal++;
                }
                if (i < kosi.length - 1 && traceBool) System.out.print("| ");
            }
            if (traceBool) System.out.println("");
            if (traceBool) insertSort(arrayFinal, "up", "trace", true);
            else if (countBool) arrayFinal = insertSort(arrayFinal, "up", "count", true);
        }
        else if (smer.equals("down")) {
            for (int i = kosi.length - 1; i >= 0; i--) {
                for (int j = 0; j < kosi[i].length; j++) {
                    if (traceBool) System.out.print(kosi[i][j] + " ");
                    arrayFinal[counterFinal] = kosi[i][j];
                    premikiBucket++;
                    counterFinal++;
                }
                if (i > 0 && traceBool) System.out.print("| ");
            }
            if (traceBool) System.out.println("");
            if (traceBool) insertSort(arrayFinal, "down", "trace", true);
            else if (countBool) arrayFinal =  insertSort(arrayFinal, "down", "count", true);
        }
        if (countBool) System.out.print(premikiBucket + " " + primerjaveBucket);
        return arrayFinal;
    }

    public static int[] arrayAdd(int[] array, int number) {
        int[] arrayF = new int[array.length + 1];
        for (int i = 0; i < array.length; i++) {
            arrayF[i] = array[i];
        }
        arrayF[arrayF.length - 1] = number;
        return arrayF;
    }

    public static void quickSort(int[] array, int prvi, int zadnji, String smer) {
        int p = 0;
        if (prvi < zadnji) {
            if (smer.equals("up")) p = pivotUp(array, prvi, zadnji);
            else if (smer.equals("down")) p = pivotDown(array, prvi, zadnji);

            quickSort(array, prvi, p - 1, smer);
            quickSort(array, p + 1, zadnji, smer);
        }
    }

    public static int pivotUp(int[] array, int prvi, int zadnji) {
        int i = prvi, j = zadnji + 1, pivot = array[prvi], temp;
        premikiQuick++;
        while (true) {
            primerjaveQuick++;
            while (array[++i] < pivot) {
                if (i == zadnji) break;
                primerjaveQuick++;
            }
            primerjaveQuick++;
            while (pivot < array[--j]) {
                if (j == prvi) break;
                primerjaveQuick++;
            }
            if (i >= j) break;
            premikiQuick += 3;
            temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }
        premikiQuick += 3;
        temp = array[prvi];
        array[prvi] = array[j];
        array[j] = temp;
        if (quickTrace) printQuickArray(array, prvi, zadnji, j);
        return j;
    }

    public static int pivotDown(int[] array, int prvi, int zadnji) {
        int i = prvi, j = zadnji + 1, pivot = array[prvi], temp;
        premikiQuick++;
        while (true) {
            primerjaveQuick++;
            while (array[++i] > pivot) {
                if (i == zadnji) break;
                primerjaveQuick++;
            }
            primerjaveQuick++;
            while (pivot > array[--j]) {
                if (j == prvi) break;
                primerjaveQuick++;
            }
            if (i >= j) break;
            premikiQuick += 3;
            temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }
        premikiQuick += 3;
        temp = array[prvi];
        array[prvi] = array[j];
        array[j] = temp;
        if (quickTrace) printQuickArray(array, prvi, zadnji, j);
        return j;
    }

    public static int[] radixHelp(int[] array, int eksponent, String smer, boolean trace) {
        int vsota = 0;
        int[] t = new int[array.length];
        int[][] c = new int[2][10];
        for (int i = 0; i < array.length; i++) {
            primerjaveRadix++;
            c[0][(array[i]/eksponent) % 10]++;
        }
        if (smer.equals("up")) {
            for (int i = 0; i < c[0].length; i++) {
                vsota += c[0][i];
                premikiRadix += c[0][i];
                c[1][i] = vsota;
            }
        }
        else if (smer.equals("down")) {
            for (int i = c[0].length - 1; i >= 0; i--) {
                vsota += c[0][i];
                premikiRadix += c[0][i];
                c[1][i] = vsota;
            }
        }
        for (int i = array.length - 1; i >= 0; i--) {
            premikiRadix++;
            primerjaveRadix++;
            t[c[1][(array[i] / eksponent) % 10] - 1] = array[i];
            c[1][(array[i] / eksponent) % 10]--;
        }
        if (trace) printArray(t, -1);
        return t;
    }

    public static void radixSort(int[] array, String smer, String prvi) {
        boolean traceBool = false;
        if (prvi.equals("trace")) traceBool = true;

        if (traceBool) printArray(array, -1);
        //Najdi max
        int max = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] > max) {
                max = array[i];
            }
        }

        for (int eksponent = 1; max/eksponent > 0 ; eksponent *= 10) {
            array = radixHelp(array, eksponent, smer, traceBool);
        }
    }

    public static void printArray(int[] array, int index) {
        StringBuffer sb = new StringBuffer();
        sb.append(array[0]);
        for (int i = 1; i < array.length; i++) {
            if (i == index) sb.append(" |");
            sb.append(" " + array[i]);
        }
        if (index == array.length) sb.append(" |");
        System.out.println(sb);
    }

    public static void printMergeArray(int[] array, int start, int end) {
        StringBuffer sb = new StringBuffer();
        for (int i = start; i <= end; i++) {
            sb.append(array[i] + " ");
        }
        System.out.println(sb);
    }

    public static void printQuickArray(int[] array, int start, int end, int pivot) {
        StringBuffer sb = new StringBuffer();
        for (int i = start; i <= end; i++) {
            if (i != pivot) sb.append(array[i] + " ");
            else sb.append("| " + array[i] + " | ");
        }
        if (sb.length() > 0) System.out.println(sb);
    }

}

public class Main {

    public static void main(String[] args) {
        // Create a RANDOM array of 10000
        AlphaNumeric[] randomArray = ArrayMaker.makeArray(ArrayMaker.ArrayOrder.RANDOM, 10000);
        
        // Test the InsertionCount sort
        InsertionCount<AlphaNumeric> insertionCountSorter = new InsertionCount<>(AlphaNumeric.numberGetter);
        AlphaNumeric[] randomArray1 = randomArray.clone();
        long insertionCountTime = insertionCountSorter.timer(randomArray1);
        System.out.println("InsertionCount Sort took " + insertionCountTime + " nanoseconds, and " + 
            insertionCountSorter.getCount() + " operations.");

        // Test the Insertion sort
        Insertion<AlphaNumeric> insertionSorter = new Insertion<>(AlphaNumeric.orderNumeric);
        AlphaNumeric[] randomArray2 = randomArray.clone();
        long insertionTime = insertionSorter.timer(randomArray2);
        System.out.println("Insertion Sort took " + insertionTime + " nanoseconds, and " + 
            insertionSorter.getCount() + " operations.");

        // Test the Counting sort
        Counting<AlphaNumeric> countingSorter = new Counting<>(AlphaNumeric.numberGetter);
        AlphaNumeric[] randomArray3 = randomArray.clone();
        long countingTime = countingSorter.timer(randomArray3);
        System.out.println("Counting Sort took " + countingTime + " nanoseconds, and " + 
            countingSorter.getCount() + " operations.");

        // Compare the sorting times
        System.out.println("//// Sorting Time Comparisons ////");
        System.out.println("InsertionCount vs. Insertion: " + (insertionCountTime - insertionTime) + " nanoseconds");
        System.out.println("InsertionCount vs. Counting: " + (insertionCountTime - countingTime) + " nanoseconds");
        System.out.println("Insertion vs. Counting: " + (insertionTime - countingTime) + " nanoseconds");
    }


    public static void test2(String[] args) {
        // Create a RANDOM, SORTED, and REVERSED array of 50000
        AlphaNumeric[] randomArray = ArrayMaker.makeArray(ArrayMaker.ArrayOrder.RANDOM, 50000);
        AlphaNumeric[] sortedArray = ArrayMaker.makeArray(ArrayMaker.ArrayOrder.SORTED, 50000);
        AlphaNumeric[] reversedArray = ArrayMaker.makeArray(ArrayMaker.ArrayOrder.REVERSED, 50000);
        System.out.println("SIZE " +  "COUNT");
        // Create an array of insertion and merge sorters
        Sorter[] sorters = new Sorter[6];
        sorters[0] = new Insertion<AlphaNumeric>(AlphaNumeric.orderAlpha);
        sorters[1] = new Insertion<AlphaNumeric>(AlphaNumeric.orderAlpha);
        sorters[2] = new Insertion<AlphaNumeric>(AlphaNumeric.orderAlpha);
        sorters[3] = new Merge<AlphaNumeric>(AlphaNumeric.orderAlpha);
        sorters[4] = new Merge<AlphaNumeric>(AlphaNumeric.orderAlpha);
        sorters[5] = new Merge<AlphaNumeric>(AlphaNumeric.orderAlpha);
        System.out.println("0 , " +  "0");
        // Sort arrays of increasing size from n = 1000, 2000, 3000, …, 50000
        for (int i = 1000; i <= 50000; i += 1000) {
            // Create a subarray of size [i] containing elements [0] to [i] of the
            // appropriate source array
            AlphaNumeric[] subArray = new AlphaNumeric[i];

            // Loop for Insertion and Merge Sort
            for (int j = 0; j < 6; j++) {
                AlphaNumeric[] sourceArray;

                // Select source array based on whether it's Insertion or Merge Sort
                if (j < 3) {
                    // For Insertion Sort
                    if (sorters[j] == sorters[0]) {
                        sourceArray = randomArray;
                    } else if (sorters[j] == sorters[1]) {
                        sourceArray = sortedArray;
                    } else {
                        sourceArray = reversedArray;
                    }
                } else {
                    // For Merge Sort
                    if (sorters[j] == sorters[3]) {
                        sourceArray = randomArray;
                    } else if (sorters[j] == sorters[4]) {
                        sourceArray = sortedArray;
                    } else {
                        sourceArray = reversedArray;
                    }
                }

                // Copy the source array into the subarray
                for (int k = 0; k < i; k++) {
                    subArray[k] = sourceArray[k];
                }

                sorters[j].sort(subArray);

                // Print the count
                long count;
                String prefix = "REVERSED"; // Default to "3." for reversedArray
                if (sourceArray == randomArray) {
                    prefix = "RANDOM";
                } else if (sourceArray == sortedArray) {
                    prefix = "SORTED";
                }
                String whichSort;
                if (j < 3) {
                    count = ((Insertion<AlphaNumeric>) sorters[j]).getCount();
                    whichSort = "Insertion";
                } else {
                    count = ((Merge<AlphaNumeric>) sorters[j]).getCount();
                    whichSort = "Merge";
                }
                System.out.println(whichSort + " , " + prefix + " , " + i + " , " + count);

            }
        }
    }

    public static void test(String[] args) {

        AlphaNumeric[] array;

        System.out.println("\n___________________ SORTING WITH INSERTION (alpha)");

        // Make an array in which the numbers are increasing, but alpha is in decreasing
        // sorted order
        array = ArrayMaker.makeArray(ArrayMaker.ArrayOrder.SORTED, 10);
        print(array);

        // Create the Sorter and sort the above array
        Insertion<AlphaNumeric> insertionAlpha = new Insertion<>(AlphaNumeric.orderAlpha);
        insertionAlpha.sort(array);
        System.out.println("Operations count " + insertionAlpha.getCount());
        print(array);

        System.out.println("\n___________________ SORTING WITH INSERTION (number)");

        // Use the array that was just sorted (but is now in reverse order relative to
        // this comparator)
        print(array);

        Insertion<AlphaNumeric> insertionNumber = new Insertion<>(AlphaNumeric.orderNumeric);
        insertionNumber.sort(array);
        System.out.println("Operations count " + insertionNumber.getCount());
        print(array);

        System.out.println("\n___________________ SORTING WITH MERGE (alpha)");

        // Make an array in which the numbers are increasing, but alpha is in decreasing
        // sorted order
        array = ArrayMaker.makeArray(ArrayMaker.ArrayOrder.SORTED, 10);
        print(array);

        // Create the Sorter and sort the above array
        Merge<AlphaNumeric> mergeAlpha = new Merge<>(AlphaNumeric.orderAlpha);
        mergeAlpha.sort(array);
        System.out.println("Operations count " + mergeAlpha.getCount());
        print(array);

        System.out.println("\n___________________ SORTING WITH COUNTING");

        array = ArrayMaker.makeArray(ArrayMaker.ArrayOrder.REVERSED, 10);
        print(array);

        Counting<AlphaNumeric> countingNumber = new Counting<>(AlphaNumeric.numberGetter);
        countingNumber.sort(array);
        System.out.println("Operations count " + countingNumber.getCount());
        print(array);

        System.out.println("\n___________________ SORTING WITH RADIX");

        array = ArrayMaker.makeArray(ArrayMaker.ArrayOrder.REVERSED, 10);
        print(array);

        Radix<AlphaNumeric> radixNumber = new Radix<>(AlphaNumeric.numberGetter);
        radixNumber.sort(array);
        System.out.println("Operations count " + radixNumber.getCount());
        print(array);

    } // end main

    public static void print(Object[] array) {
        for (Object el : array) {
            System.out.print(el + " ");
        }
        System.out.println();
    }
}

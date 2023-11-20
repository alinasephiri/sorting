import java.util.function.Function;

/** Radix sort on Integers only. */
public class Radix<T> implements Sorter<T> {

  /** Extracts the key from the objects in the array to sort */
  Function<T,Integer> keyGetter = null;

  /** Counter of compare operations */
  int count = 0;

  /**
   * Constructor for Sorting with Radix
   * 
   * @param order Comparator to establish ordering of array elements.
   */
  public Radix(Function<T,Integer> getter) {
    keyGetter = getter;
  }

  /**
   * Create a lambda function that uses the getDigit function to map a position to
   * a digit.
   * 
   * @param pos position of digit to extract from Simple.numeric
   * @return lambda function maps Simple.numeric to the digit at pos
   */
  private Function<T,Integer> makeDigitGetter(int digit) {
    double divisor = Math.pow(10.0,digit);    
    return (object)-> (int)(keyGetter.apply(object)/divisor)%10;
  }

  /**
   * Sorts specified array using Radix Sort. Inplace sorter.
   * 
   * @param array Array to be sorted.
   */
  public void sort(T[] array) {

    // Find the maximum value to determine number of digits
    Integer maxValue = findMax(array);

    // Calculate the maximum number of digits in any of the array values
    int digitCount = (int) Math.log10(maxValue) + 1;

    // For each digit, create key getter and sorter. Call the sorter
    for (int digit=0; digit<=digitCount; digit++) {
      // Create a getter for the digit to be sorted on
      Function<T,Integer> getter = makeDigitGetter(digit);
      // Create a Counting sorter to sort on that digit. Supplying the max value.
      Counting<T> digitSorter = new Counting<>(getter,9);
      // Sort on that digit
      digitSorter.sort(array);
      // Update the iteration counter for sorting the one digit
      count += digitSorter.getCount();
    }
  } // end sort(T[])

  /**
   * Sorts specified array, placing results in outArray. Not inplace sorter.
   * 
   * @param inArray  Values to be sorted. inArray remains untouched in process.
   * @param outArray Contains sorted values of inArray upon completion.
   */
  public void sort(T[] inArray, T[] outArray) {

    // Radix sort is an in-place sorter.
    // To preserve original array values, copy the inArray to the outArray.
    // Then sort inplace on the outArray, leaving inArray untouched.
    for (int i = 0; i < inArray.length; i++) {
      outArray[i] = inArray[i];
    }

    sort(outArray);
  } // end sort(T[],T[])
  
  // Helper function to find the max to know how many digits there are
  private Integer findMax(T[] array) {
    Integer max = keyGetter.apply(array[0]);
    for (T element : array) {
      Integer valueOf = keyGetter.apply(element);
      if (valueOf > max) {
        max = valueOf;
      }
    }
    return max;
  }

  // GETTER
  public long getCount() {
    return count;
  }

} // end class Radix

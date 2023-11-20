import java.util.function.Function;

/** Counting Sort assumes key of type Integer */
public class Counting<T> implements Sorter<T> {

  /** Extracts the key from an object in the array */
  Function<T,Integer> keyGetter = null;

  /** Max Value in the array to be sorted */
  Integer maxValue = null;

  /** Counter of loop iterations */
  long count = 0;

  /** Default empty constructor. */
  public Counting() {}

  /** Constructor for Counting
   * 
   * @param order Comparator to establish ordering of array elements.
   */
  public Counting(Function<T,Integer> getter) {
    keyGetter = getter;
  }

    /** Constructor for Counting with known max value
   * 
   * @param order Comparator to establish ordering of array elements.
   */
  public Counting(Function<T,Integer> getter, Integer maximum) {
    keyGetter = getter;
    this.maxValue = maximum;
  }

  /** Sorts specified array using Counting Sort. Inplace version of the sorter.
   * 
   * @param array Array to be sorted.
   */
  public void sort(T[] array) {


    // Counting is not an in-place sorting algorithm.
    // Therefore, need to sort, then copy the new sorted array back into the original

    @SuppressWarnings("unchecked")
    T[] sorted = (T[]) new Object[array.length];

    sort(array,sorted);
    for (int i=0; i<array.length; i++) {
      array[i] = sorted[i];
    }
  } // end sort(T[])

  /**  Sorts specified array, placing results in outArray. Not inplace sorter.
   * @param inArray Values to be sorted. inArray remains untouched in process.
   * @param outArray Contains sorted values of inArray upon completion.
   */
  public void sort(T[] inArray, T[] outArray) {
    if (maxValue==null) {
      maxValue = findMax(inArray);
    }

    // Reset count back to 0
    count = 0;

    // _________________________________________________________________
    // TODO ___________ COMPLETE COUNTING SORT BELOW ___________________
    // ________________   IMPORTANT TO COMMENT YOUR CODE _______________
    // _____ BEWARE OF THE CONVERSION FROM 1-based to 0-based indexing _

    // let B[] be an array with length inArray.length
    @SuppressWarnings("unchecked")
    T[] B = (T[]) new Object[inArray.length];
    
    // let C[0...max] be a new array
    Integer[] C = new Integer[maxValue + 1];

    // Assign each value to 0 instead of null
    for(int i=0; i < maxValue + 1; i++){
      C[i] = 0;
    }
    // increment a value it is found inArray
    for(int j=0; j < inArray.length; j++){
        C[keyGetter.apply(inArray[j])] = C[keyGetter.apply(inArray[j])] + 1;
        count++;
    }
    //C[i] now contains the number of elements equal to i. add up the list of values
    for(int i=1; i < maxValue + 1; i++){
      C[i] = C[i] + C[i-1];
    }
    // C[i] now contains the number of elements less than
    //or equal to i

    // add the values in B[] and decrement the number in C[]
    for(int j=inArray.length-1; j > -1; j--){
        B[C[keyGetter.apply(inArray[j])]-1] = inArray[j];
        C[keyGetter.apply(inArray[j])] = C[keyGetter.apply(inArray[j])] - 1;
        count++;
    }
    // Copy B array to outArray
    for(int i=0; i < inArray.length; i++){
        outArray[i] = B[i];
    }    
    

    // ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^    

  } // end sort(T[], T[])

  private Integer findMax(T[] array) {
    Integer max = keyGetter.apply(array[0]);
    for (T element : array) {
      Integer valueOf = keyGetter.apply(element);
      if (valueOf > max) {
        max = valueOf;
      }
    }
    return max;
  } // end findMax()

  public void setKeyGetter(Function<T,Integer> getter) {
    keyGetter = getter;
  }

  public long getCount() {
    return count;
  }  
  /**
   * records the time it takes to sort an array using the sorter
   * @param array the array being sorted
   * @return timeNS the sorting time in nanoseconds
   */
  public long timer(T[] array){
    // start time in nanoseconds 
    long startNS;

    // end time in nanoseconds 
    long endNS;

    startNS=System.nanoTime();
    this.sort(array);
    endNS=System.nanoTime();

    // total time in milliseconds         
    long timeNS = (endNS - startNS) / 1000000;

    return(timeNS);

  }
} // end class Countin

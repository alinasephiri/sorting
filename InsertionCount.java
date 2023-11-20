import java.util.function.Function;

/** InsertionCount sort - an in-place sorting algorithm */
public class InsertionCount<T> implements Sorter<T> {

  /** Extracts the key from an object in the array */
  Function<T,Integer> keyGetter = null;

  /** Counter of compare operations */
  long count = 0;

  /** Constructor for InsertionCount Sort to set comparator
   * @param getter to extract the Integer out of the element
   */
  public InsertionCount(Function<T,Integer> getter) {
    keyGetter = getter;
  }

/** Sorts specified array using Insertion Sort. Inplace sorter.
   * @param array Array to be sorted.
   */
  public void sort(T[] array) {
    
    // Reset count back to 0
    count = 0;

    // TODO ___________ COMPLETE INSERTIONCOUNT SORT BELOW __________________

    int n = array.length;
    for (int i = 0; i < n; i++) {
      // Initialize a count for elements less than the current element
      int lessCount = 0;
      for (int j = i + 1; j < n; j++) {
        count++; // Increment comparison counter
        if (keyGetter.apply(array[j]) < keyGetter.apply(array[i])) {
          // If an element is less than the current element, increment the lessCount
          lessCount++;
        }
      }
      if (lessCount == 0) {
        // If there are no elements less than the current element, move to the next element
        i++;
      } 
      else {
        // Swap elements
        T temp = array[lessCount + i];
        array[lessCount + i] = array[i];
        array[i] = temp;
      }
    }
 
 
 
  } // end sort(T[])

  /**  Sorts specified array, placing results in outArray. Not in place sorter.
   * @param inArray Values to be sorted. inArray remains untouched in process.
   * @param outArray Contains sorted values of inArray upon completion.
   */
  public void sort(T[] inArray, T[] outArray) {

    for (int i=0; i<inArray.length; i++) {
      outArray[i] = inArray[i];
    }

    sort(outArray);
  } // end sort(T[],T[])

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
}

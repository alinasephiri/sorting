import java.util.Comparator;

/** Merge Sort divide-and-conquer recursive algorithm */
public class Merge<T> implements Sorter<T> {

  /** Establishes ordering of type T */
  private Comparator<T> orderBy;

  /** Counter of compare operations */
  int count = 0;


  /** Constructor for Merge Sort to set comparator
   * 
   * @param order Comparator to establish ordering of array elements.
   */
  public Merge(Comparator<T> order) {
    orderBy = order;
  }

  /** Sorts specified array using Merge Sort
   * 
   * @param array Array to be sorted.
   */
  public void sort(T[] array) {
    // Reset count back to 0
    count = 0;

    // TODO: Call the mergeSort method to start the sorting process
    mergeSort(array, 0, array.length - 1);
  }
  /**
   * 
   * @param array array being sorted
   * @param p index of first value
   * @param r index of last value
   */
  private void mergeSort(T[] array, int p, int r) {
    if (p < r) {
      // Calculate the middle index
      int q = (p + r) / 2;

      // Recursively sort the left and right halves
      mergeSort(array, p, q);
      mergeSort(array, q + 1, r);

      // Merge the sorted halves
      merge(array, p, q, r);
    }
  }
  /**
   * 
   * @param array array being sorted
   * @param p index of first value
   * @param q index of middle value
   * @param r index of last value
   */
  private void merge(T[] array, int p, int q, int r) {
    // Calculate the sizes of the two subarrays
    int n1 = q - p + 1;
    int n2 = r - q;

    // Create temporary arrays to hold the left and right subarrays
    @SuppressWarnings("unchecked")
    T[] left = (T[]) new Object[n1];

    @SuppressWarnings("unchecked")
    T[] right = (T[]) new Object[n2];

    // Copy data to the temporary arrays
    for (int i = 0; i < n1; i++) {
      left[i] = array[p + i];
    }

    for (int j = 0; j < n2; j++) {
      right[j] = array[q + j + 1];
    }

    int i = 0, j = 0;

    // Merge the left and right subarrays back into the original array
    for (int k = p; k <= r; k++) {
      if (i < n1 && j < n2) {
        if (orderBy.compare(left[i], right[j]) <= 0) {
          array[k] = left[i];
          i++;
        } else {
          array[k] = right[j];
          j++;
        }
      } else if (i < n1) {
        array[k] = left[i];
        i++;
      } else {
        array[k] = right[j];
        j++;
      }
      count++; // Increment the comparison count
    }
  }


  /**  Sorts specified array, placing results in outArray. Not inplace sorter.
   * @param inArray Values to be sorted. inArray remains untouched in process.
   * @param outArray Contains sorted values of inArray upon completion.
   */
  public void sort(T[] inArray, T[] outArray) {

    // Merge sort is designed to modify the original array.
    // To preserve original array values, copy the inArray to the outArray. 
    // Then sort inplace on the outArray, leaving inArray untouched.
    for (int i=0; i<inArray.length; i++) {
      outArray[i] = inArray[i];
    }

    sort(outArray);
  } // end sort(T[],T[])

  public void setComparator(Comparator<T> order) {
    orderBy = order;
  }

  public long getCount() {
    return count;
  }
} // end class Merge

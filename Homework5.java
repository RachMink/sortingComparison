/**@name: Rachel Minkowitz
 * @since: 07/06/2021
 * @version: 1.0
 * @Description: CISC 3130 Homework 5, Proff. Lowenthal 
 * This program compares the efficiency of different searches using the various sets 
 * of input data
 * yay we made it!!!
 */
import java.io.File;
import java.io.PrintStream;
import java.util.Scanner;

public class Homework5 {
    public static void main(String[] args) throws Exception {

        Scanner sc = new Scanner(new File("input.txt"));
        PrintStream ps = new PrintStream(new File("output.txt"));

        int[] arr = new int[100];
        int[] bubble = new int[100];
        int[] quick = new int[100];
        int[] heap = new int[100];
        int amount;
        String type;

        while (sc.hasNext()) {
            amount = sc.nextInt();
            type = sc.next();

            ps.println("Orginal list of " + amount + " numbers in " + type + " order");
            for (int i = 0; i < amount; i++) {
                arr[i] = sc.nextInt();
                ps.print(arr[i] + " ");
                bubble[i] = arr[i];
                quick[i] = arr[i];
                heap[i] = arr[i];
            }
            ps.println("\n");

            int[] bubbleAns = bubbleSort(bubble, amount);
            ps.println("Comparisons in Bubble Sort: " + bubbleAns[0]);
            ps.println("Swaps in Bubble Sort: " + bubbleAns[1]);
            ps.println("Array after Bubble sort: ");
            for (int i = 0; i < amount; ++i) {
                ps.print(bubble[i] + " ");
            }
            ps.println("\n");

            int[] quickAns = new int[2];
            quickAns = quickSort(quick, 0, amount - 1, quickAns);
            ps.println("Comparisons in Quick Sort: " + quickAns[0]);
            ps.println("Swaps in Quick Sort: " + quickAns[1]);
            ps.println("Array after Quick sort: ");
            for (int i = 0; i < amount; i++) {
                ps.print(quick[i] + " ");
            }
            ps.println("\n");

            int[] heapAns = heapSort(heap, amount);
            ps.println("Comparisons in Heap Sort: " + heapAns[0]);
            ps.println("Swaps in Heap Sort: " + heapAns[1]);
            ps.println("Array after Heap sort: ");
            for (int i = 0; i < amount; i++) {
                ps.print(heap[i] + " ");
            }
            ps.println("\n");

            compare(ps, "comparison", bubbleAns[0], quickAns[0], heapAns[0]);
            compare(ps, "swap", bubbleAns[1], quickAns[1], heapAns[1]);
            ps.println();
        }

    }

    // *******BUBBLE SORT******************************************************

    public static int[] bubbleSort(int[] bubble, int amount) {
        int bubbleComparison = 0;
        int bubbleSwaps = 0;
        int n = amount;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n - 1; j++) {
                bubbleComparison++;
                if (bubble[j] > bubble[j + 1]) {

                    // swap arr[j+1] and arr[j]
                    int temp = bubble[j];
                    bubble[j] = bubble[j + 1];
                    bubble[j + 1] = temp;
                    bubbleSwaps++;
                }
            }
        }

        int[] bubbleAns = new int[2];
        bubbleAns[0] = bubbleComparison;
        bubbleAns[1] = bubbleSwaps;
        return bubbleAns;
    }

    // *******QUICK SORT***************************************************

    public static int[] quickSort(int[] quick, int low, int high, int[] quickAns) {
        int pivot;

        if (low < high) {
            // partition the array around pivot=>partitioning index and return pivot
            pivot = partition(quick, low, high, quickAns);
            // sort each partition recursively
            quickSort(quick, low, pivot - 1, quickAns);
            quickSort(quick, pivot + 1, high, quickAns);
        }

        return quickAns;
    }

    public static int partition(int intArray[], int low, int high, int[] ans) {
        // selects last element as pivot, pivot using which array is partitioned.
        int pivot = intArray[high];
        int i = (low - 1); // smaller element index
        for (int j = low; j <= high - 1; j++) {

            ans[0]++;// adds one to the comparison slot

            // check if current element is less than or equal to pi
            if (intArray[j] <= pivot) {
                i++;
                swap(intArray, i, j, ans);

            }

        }

        // swap intArray[i+1] and intArray[high] (or pi)
        swap(intArray, i + 1, high, ans);
        return i + 1;
    }

    // A utility function to swap two elements
    public static void swap(int[] arr, int i, int j, int[] ans) {
        ans[1]++;
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    // *****HEAP SORT************************************************

    public static int[] heapSort(int arr[], int amount) {
        int n = amount;

        int[] heapAns = new int[2];

        // Build heap (rearrange array)

        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(arr, n, i, heapAns);
        }

        // One by one extract an element from heap

        for (int i = n - 1; i >= 0; i--) {
            // Move current root to end
            int temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;
            heapAns[1]++;
            // call max heapify on the reduced heap
            heapify(arr, i, 0, heapAns);
        }
        return heapAns;
    }

    // To heapify a subtree rooted with node i which is
    // an index in arr[]. n is size of heap

    public static void heapify(int arr[], int n, int i, int[] heapAns) {
        int largest = i; // Initialize largest as root
        int l = 2 * i + 1; // left = 2*i + 1
        int r = 2 * i + 2; // right = 2*i + 2

        // If left child is larger than root
        if (l < n && arr[l] > arr[largest]) {
            largest = l;
        }

        // If right child is larger than largest so far
        if (r < n && arr[r] > arr[largest]) {
            largest = r;
        }

        heapAns[0]++;

        // If largest is not root
        if (largest != i) {
            int swap = arr[i];
            arr[i] = arr[largest];
            arr[largest] = swap;
            heapAns[1]++;

            // Recursively heapify the affected sub-tree
            heapify(arr, n, largest, heapAns);
        }
    }

    public static void compare(PrintStream ps, String type, int bubble, int quick, int heap) {

        if (bubble > quick && bubble > heap) {
            if (quick > heap) {
                ps.println("Bubble had the most " + type + "s then quick, then heap");
            } else if (quick < heap) {
                ps.println("Bubble had the most " + type + "s then heap, then quick");
            } else if (quick == heap) {
                ps.println("Bubble had the most " + type + "s then quick and heap had equal " + type + "s");
            }
        }

        else if (quick > bubble && quick > heap) {
            if (bubble > heap) {
                ps.println("Quick had the most " + type + "s then bubble, then heap");
            } else if (heap > bubble) {
                ps.println("Quick had the most " + type + "s then heap, then bubble");
            } else if (bubble == heap) {
                ps.println("Quick had the most " + type + "s then bubble and heap had equal " + type + "s");
            }
        } else {
            if (bubble > quick) {
                ps.println("Heap had the most " + type + "s then bubble, then quick");
            } else if (bubble < quick) {
                ps.println("Heap had the most " + type + "s then quick, then bubble");
            } else if (quick == bubble) {
                ps.println("Heap had the most " + type + "s then quick and bubble had equal " + type + "s");
            }

        }
    }
}

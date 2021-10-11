package sort;

import buildings.Space;

public class Sort {
    public static void quickSort(Space[] array, int low, int high) {
        if (array.length == 0)
            return;
        if (low >= high)
            return;
        int middle = low + (high - low) / 2;
        double support = array[middle].getArea();
        int i = low, j = high;
        while (i <= j) {
            while (array[i].getArea() > support) {
                i++;
            }
            while (array[j].getArea() < support) {
                j--;
            }
            if (i <= j) {
                double temp = array[i].getArea();
                array[i].setArea(array[j].getArea());
                array[j].setArea(temp);
                i++;
                j--;
            }
        }
        if (low < j)
            quickSort(array, low, j);
        if (high > i)
            quickSort(array, i, high);
    }
}

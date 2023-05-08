package se.akh.gup.wireless.car;

public class APICaller {

    //    public int[] array = {4,14,8,12,2,10,8,6,14,4};
    public int[] array = {6,4,14,6,8,12,10,6,11,5,12};

    public int getNumberOfBars(){
        return array.length;
    }

    public int getBarHeight(int index){
        return array[index];
    }
}

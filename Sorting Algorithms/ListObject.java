import java.util.Arrays;
import java.util.Collections;

public class ListObject {
    public final ListElement[] list;
    public ListObject(int howManyElements, int status, boolean stabilityCheck){
        Integer[] temp = new Integer[howManyElements];
        java.util.Random random = new java.util.Random();
        if(stabilityCheck){
            for (int i = 0; i < howManyElements; i++) {
                temp[i] = random.nextInt(64);
            }
        }else{
            for (int i = 0; i < howManyElements; i++) {
                temp[i] = random.nextInt();         //average case
            }
        }
        switch(status){
            case 1:
                Arrays.sort(temp);          //best case
                break;
            case -1:
                Arrays.sort(temp, Collections.reverseOrder());  //worst case
                break;
        }
        list = new ListElement[howManyElements];
        for (int i = 0; i < howManyElements;) {
            list[i] = new ListElement(temp[i], ++i);
        }
    }
    public boolean checkStability(){
        ListElement pre = list[0];
        ListElement cur = null;
        for (int i = 1; i < list.length; i++) {
            cur = list[i];
            if (pre.getValue() == cur.getValue())                   //stability check
                if (pre.getOrder() > cur.getOrder())
                    return false;
            pre = cur;
        }
        return true;
    }

    public void reset(){
        Arrays.sort(list);
    }

}



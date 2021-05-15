public class ListElement implements Comparable<ListElement>{
    private final int value;
    private final int order;
    public ListElement(int value, int order){
        this.value = value;
        this.order = order;
    }
    public int getValue() {
        return value;
    }
    public int getOrder() {
        return order;
    }

    @Override
    public int compareTo(ListElement o) {
        return this.order - o.order;
    }
}

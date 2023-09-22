/**
 * Main
 */
public class Main{

    public static<T extends Comparable <T>> void main(String[] args) {
        AVLTree<T> tree = new AVLTree<T>();
        for(int i =0; i<6; i++){
            Integer f = i;
            tree.put((T)f);
        }
        Integer put = -1;
        tree.put((T)put);
        Integer delete = 1;
        tree.delete((T)delete);
    }
}
/**
 * Main
 */
public class Main{

    public static<T extends Comparable <T>> void main(String[] args) {
        AVLTree<T> tree = new AVLTree<T>();
        for(int i =0; i<10; i++){
            Integer f = i;
            tree.put((T)f);
        }
        
    }
}
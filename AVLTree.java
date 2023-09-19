
import java.util.LinkedList;



public class AVLTree<T extends Comparable <T>>{
    private AVLNode<T> root;
    private LinkedList<Integer> rotationDirection = new LinkedList<Integer>();
    private LinkedList<AVLNode<T>> rod = new LinkedList<AVLNode<T>>();
    
    public AVLNode<T> getRoot (){
        return this.root;
    }

    public void setRoot(AVLNode<T> root){
        this.root = root;
    }

    public void put(T key){
        AVLNode<T> searchNode = this.root;
        AVLNode<T> newNode = new AVLNode<T>(key);
        if(searchNode ==null){
            this.root = newNode;
        }else{
            positionToPut(newNode,searchNode);
        }
         if(rod.size()>=3){
            balancing(rotationDirection.pollFirst(), rotationDirection.pollFirst(), rod.pollFirst(), rod.pollFirst(), rod.pollFirst());
        }
    }

    private void positionToPut (AVLNode<T> newNode, AVLNode<T> searchNode) {
        if(searchNode == null){
            rod.addLast(newNode);
            return;
        }
        if(searchNode.getInfo().compareTo(newNode.getInfo()) > 0){
            searchNode.setFatBal(searchNode.getFatBal()-1);
            if(searchNode.getFatBal() >= 2 || searchNode.getFatBal() <= -2 || !rotationDirection.isEmpty()){
                rod.addLast(searchNode);
                rotationDirection.addLast(0);
            }
            positionToPut(newNode, searchNode.getLeft());
            if(searchNode.getLeft() == null){
                searchNode.setLeft(newNode);
             }
            
        }else{
            searchNode.setFatBal(searchNode.getFatBal()+1);
            if(searchNode.getFatBal() >= 2 || searchNode.getFatBal() <= -2|| !rotationDirection.isEmpty()){
                rotationDirection.addLast(1);
                rod.addLast(searchNode);
            }
             positionToPut(newNode, searchNode.getRight());
             if(searchNode.getRight() == null){
                 searchNode.setRight(newNode);
             }
        }
    }

    private void balancing(Integer value1, Integer value2, AVLNode<T> a, AVLNode<T> b, AVLNode<T>c){
            a.setFatBal(0);
            b.setFatBal(0);
            c.setFatBal(0);
            AVLNode<T> rightLeaf = a.getRight();
            AVLNode<T> leftLeaf = a.getLeft();
            T aValue = a.getInfo();
        //rotacao simples direita
        if(value1 == 0 && value2 == 0){
            AVLNode<T> t1 = c;
            AVLNode<T> t2 = b.getRight();
            AVLNode<T> t3 = a.getRight();
            AVLNode<T>leftTree = new AVLNode<T>(t1.getInfo()); 
            AVLNode<T>rightTree = new AVLNode<T>(aValue);
            a.setInfo(b.getInfo());
            rightLeaf.setInfo(aValue);
            a.setLeft(t1);
            rightLeaf.setLeft(t2);
            rightLeaf.setLeft(t3);
        }
        //rotacao dupla esquerda direita
        else if(value1 == 0 && value2 ==1){
            AVLNode<T> t1 = b.getLeft();
            AVLNode<T> t2 = c.getLeft();
            AVLNode<T> t3 = c.getRight();
            AVLNode<T> t4 = a.getRight();
            a.setInfo(c.getInfo());
            rightLeaf.setInfo(aValue);
            leftLeaf.setLeft(t1);
            leftLeaf.setRight(t2);
            rightLeaf.setLeft(t3);
            rightLeaf.setRight(t4);
        }
        //rotacao dupla direita esquerda
        else if(value1 == 1 && value2 == 0){
            AVLNode<T> t1 = a.getLeft();
            AVLNode<T> t2 = c.getLeft();
            AVLNode<T> t3 = c.getRight();
            AVLNode<T> t4 = b.getRight();
            a.setInfo(c.getInfo());
            leftLeaf.setInfo(aValue);
            leftLeaf.setLeft(t1);
            leftLeaf.setRight(t2);
            rightLeaf.setLeft(t3);
            rightLeaf.setRight(t4);
        }
        //rotacao simples esquerda
        else if(value1 == 1 && value2 ==1){
            AVLNode<T> t1 = a.getLeft();
            AVLNode<T> t2 = b.getLeft();
            AVLNode<T> t3 = c;
            a.setInfo(b.getInfo());
            leftLeaf.setInfo(aValue);
            leftLeaf.setLeft(t1);
            leftLeaf.setRight(t2);
            rightLeaf = t3;

        }
        rod = null;
        rotationDirection = null;
    }

    private void recalculateFatBal(T valueSearched){

    }

    public LinkedList<T> passeioEmOrdem(){
        AVLNode<T> searchNode = this.root;
        LinkedList<T> results = new LinkedList<T>();
        results = passeioEmOrdemOrganizar(searchNode, results);
        return results;
    }

    private LinkedList<T> passeioEmOrdemOrganizar(AVLNode<T> searchNode, LinkedList<T> results){
        if(searchNode.getLeft() != null){
            passeioEmOrdemOrganizar(searchNode.getLeft(), results);
        }
         results.addLast(searchNode.getInfo());
         if(searchNode.getRight() != null){
             passeioEmOrdemOrganizar(searchNode.getRight(), results);
         }
         return results;
    }

    public LinkedList<T> passeioPorNivel(){
        LinkedList<AVLNode<T>> searchQueue = new LinkedList<AVLNode<T>>();
        LinkedList<T> resultQueue = new LinkedList<T>();
        searchQueue.addLast(this.root);
        while(!searchQueue.isEmpty()){
            AVLNode<T> node = searchQueue.getFirst();
            resultQueue.addLast(node.getInfo());
            if(node.getLeft() != null){
                searchQueue.addLast(node.getLeft());
            }
             if(node.getRight() != null){
                searchQueue.addLast(node.getRight());
            }
        }
        return resultQueue;

    }

}

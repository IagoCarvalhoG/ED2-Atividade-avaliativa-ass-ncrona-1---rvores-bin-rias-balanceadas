
import java.util.LinkedList;



public class AVLTree<T extends Comparable <T>>{
    private AVLNode<T> root;
    private boolean rotationInQueue;
    private LinkedList<AVLNode<T>> nodes;

    public AVLTree(){
        this.rotationInQueue = false;
        this.nodes = new LinkedList<AVLNode<T>>();
    }
    
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
            this.root = positionToPut(newNode,searchNode);
        }
    }

    private AVLNode<T> positionToPut (AVLNode<T> newNode, AVLNode<T> searchNode) {
        if(searchNode == null){
            return newNode;
        }
        if(searchNode.getInfo().compareTo(newNode.getInfo()) > 0){
            searchNode.setFatBal(searchNode.getFatBal()-1);

            if(searchNode.getFatBal() <= -2 && !this.rotationInQueue){
                rotationInQueue = true;
                positionToPut(newNode, searchNode.getLeft());
                searchNode = rightRotation(searchNode);
            }
            
            else{
                 positionToPut(newNode, searchNode.getLeft());
            if(searchNode.getLeft() == null){
                searchNode.setLeft(newNode);
             }
            }

        }else{
            searchNode.setFatBal(searchNode.getFatBal()+1);
            if(searchNode.getFatBal() >= 2 && !this.rotationInQueue){
                rotationInQueue = true;
                positionToPut(newNode, searchNode.getRight());
                searchNode = leftRotation(searchNode);
            }
            
            else{
            positionToPut(newNode, searchNode.getRight());
             if(searchNode.getRight() == null){
                 searchNode.setRight(newNode);
             }
            }
        }
        return searchNode;
    }

    public void delete(T key){
        searchNodeToDelete(key, this.root);
        
        AVLNode<T> nodeToDelete = nodes.pollFirst();
        AVLNode<T> nodeToReplace = ne
        if(nodeToDelete.getLeft() == null && nodeToDelete.getRight() == null){
            nodeToDelete = null;
        }
        else if(nodeToDelete.getLeft() != null){
            nodeToReplace = nodeToDelete.getLeft();
            while(nodeToReplace.getRight() != null){
            nodeToReplace = nodeToReplace.getRight();
        }
        }else{
            nodeToReplace = nodeToDelete.getRight();
        }
        nodeToDelete.setInfo(nodeToReplace.getInfo());
    }

    private void searchNodeToDelete(T key, AVLNode<T> searchNode){
         if(searchNode.getInfo() == key){
            nodes.addFirst(searchNode);
            return;
        }
          if(searchNode.getInfo().compareTo(key) > 0){
            nodes.addFirst(searchNode);
            searchNodeToDelete(key, searchNode.getLeft());
            return;
          }
          
          else{
             nodes.addFirst(searchNode);
            searchNodeToDelete(key, searchNode.getRight());
            return;
          }
    }

    private AVLNode<T> getHigherFromLeft(N){

    }
    private AVLNode<T> rightRotation(AVLNode<T>a){
        AVLNode<T> b = a.getLeft();
        AVLNode<T> c;
        if(b.getFatBal() == -1 || (b.getFatBal() == -2 && a.getFatBal()==-2)){
            a.setLeft(b.getRight());
            b.setRight(a);
            if(b.getFatBal() == -2){
                a.setFatBal(+1);
            }else{
                a.setFatBal(0);
            }
            b.setFatBal(0);
            a=b;
        }else{
            c = b.getRight();
            b.setRight(c.getLeft());
            a.setLeft(c.getRight());
            c.setLeft(b);
            c.setRight(a);
            if(c.getFatBal() == 0){
                b.setFatBal(0);
                a.setFatBal(0);
                c.setFatBal(0);
            }else if(b.getFatBal() == 2 && c.getFatBal()==1){
                a.setFatBal(0);
                b.setFatBal(0);
                c.setFatBal(1);
            }else{
                if(c.getFatBal() == -1){
                b.setFatBal(b.getFatBal() -1);
                a.setFatBal(1);
                c.setFatBal(0);
            }else{
                a.setFatBal(0);
                b.setFatBal(b.getFatBal() -2);
                c.setFatBal(0);
            }  
            }
            a=c;
        }
        this.rotationInQueue = false;
        return a;
    }

    private AVLNode<T> leftRotation(AVLNode<T>a){
        AVLNode<T> b = a.getRight();
        AVLNode<T> c;
        if(b.getFatBal() == 1 || (b.getFatBal() == 2 && a.getFatBal()==2)){
            a.setRight(b.getLeft());
            b.setLeft(a);
            if(b.getFatBal() == 2){
                a.setFatBal(-1);
            }else{
                a.setFatBal(0);
            }
            b.setFatBal(0);
            a=b;
        }else{
            c = b.getLeft();
            b.setLeft(c.getRight());
            a.setRight(c.getLeft());
            c.setRight(b);
            c.setLeft(a);
            if(c.getFatBal() == 0){
                b.setFatBal(0);
                a.setFatBal(0);
                c.setFatBal(0);
            }else if(b.getFatBal() == -2 && c.getFatBal()==-1){
                a.setFatBal(0);
                b.setFatBal(0);
                c.setFatBal(-1);
            }else{
                if(c.getFatBal() == 1){
                b.setFatBal(b.getFatBal() +1);
                a.setFatBal(-1);
                c.setFatBal(0);
            }else{
                a.setFatBal(0);
                b.setFatBal(b.getFatBal() +2);
                c.setFatBal(0);
            }  
            }
            a=c;
        }
        this.rotationInQueue = false;
        return a;
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

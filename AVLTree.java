
import java.util.LinkedList;



public class AVLTree<T extends Comparable <T>>{
    private AVLNode<T> root;
    private boolean rotationInQueue;
    private AVLNode<T> subs;
    private boolean balancingNeeded;

    public AVLTree(){
        this.rotationInQueue = false;
        this.subs = new AVLNode<T>(null);
        this.balancingNeeded = true;
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
            this.rotationInQueue = false;
        }
    }

    private AVLNode<T> positionToPut (AVLNode<T> newNode, AVLNode<T> searchNode) {
        if(searchNode == null){
            return newNode;
        }
        if(searchNode.getInfo().compareTo(newNode.getInfo()) > 0){
            searchNode.setFatBal(searchNode.getFatBal()-1);
            searchNode.setLeft(positionToPut(newNode, searchNode.getLeft()));
            //if(searchNode.getLeft() == null){
             //   searchNode.setLeft(newNode);
             //}
             if(this.rotationInQueue){
                searchNode.setFatBal(searchNode.getFatBal()+1);
            }
            if(searchNode.getFatBal() <= -2 ){
                rotationInQueue = true;
                searchNode = rightRotation(searchNode);
            }
        }else{
            searchNode.setFatBal(searchNode.getFatBal()+1);
            searchNode.setRight(positionToPut(newNode, searchNode.getRight())) ;
            // if(searchNode.getRight() == null){
            //     searchNode.setRight(newNode);
           // }
           if(this.rotationInQueue){
                searchNode.setFatBal(searchNode.getFatBal()-1);
            }
            if(searchNode.getFatBal() >= 2){
                this.rotationInQueue = true;
                searchNode = leftRotation(searchNode);
            }
        }
        return searchNode;
    }
    public void delete(T key){
        AVLNode<T> searchNode = this.root;
        
        if(searchNode ==null){
            return;
        }else{
            this.root = delete(key,searchNode);
            this.rotationInQueue = false;
        }
    }

    private AVLNode<T> delete(T key, AVLNode<T> searchNode){
          if(searchNode.getInfo().compareTo(key) == 0){
            if(searchNode.getLeft() == null && searchNode.getRight() == null){
                searchNode = null;
            }else if(searchNode.getLeft() == null){
                searchNode = searchNode.getRight();
            }else{
                this.subs.setRight(searchNode.getRight());
                searchNode = getHigherFromLeft(searchNode.getLeft());
                this.subs.setLeft(searchNode);
                searchNode = this.subs;
            }
            return searchNode;
        }
        if(searchNode.getInfo().compareTo(key) > 0){
            searchNode.setLeft(delete(key, searchNode.getLeft()));

            if(this.balancingNeeded){
                if(searchNode.getFatBal() == 0 ){
                    searchNode.setFatBal(searchNode.getFatBal()+1);
                    this.balancingNeeded = false;
                }else if(searchNode.getFatBal() ==1){
                    searchNode = leftRotation(searchNode);
                    
                }else{
                    searchNode.setFatBal(0);  
        }
            }
        }else{
            searchNode.setRight(delete(key, searchNode.getRight())) ;

           if(this.balancingNeeded){
            if(searchNode.getFatBal() == 0){
                searchNode.setFatBal(searchNode.getFatBal()-1);
                this.balancingNeeded = false;
            }else if(searchNode.getFatBal() ==1){
                searchNode.setFatBal(0);
            }else{
             searchNode = rightRotation(searchNode);
             }
            }
        }
        return searchNode;
    }

     private AVLNode<T> getHigherFromLeft(AVLNode<T> searchNode){
        if(searchNode == null){
            return searchNode;
        }
        getHigherFromLeft(searchNode.getRight());
        if(this.subs.getInfo() == null){
            this.subs.setInfo(searchNode.getInfo());
            searchNode = searchNode.getLeft();
           
        }else{
        if(searchNode.getFatBal() == 0 && balancingNeeded){
            searchNode.setFatBal(searchNode.getFatBal()-1);
            this.balancingNeeded = false;
        }else if(searchNode.getFatBal() ==1 && balancingNeeded){
            searchNode.setFatBal(0);
        }else if(balancingNeeded){
             searchNode = rightRotation(searchNode);
        }
    }
     return searchNode;
    }
    private AVLNode<T> rightRotation(AVLNode<T>a){
        AVLNode<T> b = a.getLeft();
        AVLNode<T> c;
        if(b.getFatBal() == -1){
            a.setLeft(b.getRight());
            b.setRight(a);
            /*if(b.getFatBal() == -2){
                a.setFatBal(+1);
            }else{
                a.setFatBal(0);
            }*/
            a.setFatBal(0);
            b.setFatBal(0);
            a=b;
        }else{
            c = b.getRight();
            b.setRight(c.getLeft());
            a.setLeft(c.getRight());
            c.setLeft(b);
            c.setRight(a);
            /*if(c.getFatBal() == 0){
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
            }*/
            if (c.getFatBal() == -1) {
                a.setFatBal(1);
                } else {
                a.setFatBal(0);
                }
                if (c.getFatBal() == 1) {
                b.setFatBal(-1);
                } else {
                b.setFatBal(0);
                }
                c.setFatBal(0);
            a=c;
        }
        return a;
    }

    private AVLNode<T> leftRotation(AVLNode<T>a){
        AVLNode<T> b = a.getRight();
        AVLNode<T> c;
        if(b.getFatBal() == 1){
            a.setRight(b.getLeft());
            b.setLeft(a);
          /*  if(b.getFatBal() == 2){
                a.setFatBal(-1);
            }else{
                a.setFatBal(0);
            }*/ 
            a.setFatBal(0);
            b.setFatBal(0);
            a=b;
        }else{
            c = b.getLeft();
            b.setLeft(c.getRight());
            a.setRight(c.getLeft());
            c.setRight(b);
            c.setLeft(a);
           /*  if(c.getFatBal() == 0){
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
            }*/
            if (c.getFatBal() == 1) {
                a.setFatBal(-1);
                } else {
                a.setFatBal(0);
                }
                if (c.getFatBal() == -1) {
                b.setFatBal(1);
                } else {
                b.setFatBal(0);
                }
            c.setFatBal(0);
            a=c;
        }
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

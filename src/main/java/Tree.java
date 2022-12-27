
public class Tree<T extends Comparable> {
    private Tree() {}
    public static int size = 0;

    static class Empty<T extends Comparable> extends Tree<T> {
        @Override
        public String toString() {
            return "Empty{}";
        }
    }

    static class Leaf<T extends Comparable> extends Tree<T> {
        public T value;

        public Leaf(T value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return "Leaf{" +
                    "value=" + value +
                    '}';
        }
    }

    static class Branch<T extends Comparable> extends Tree<T> {
        public Tree<T> left;
        public Tree<T> right;

        public Branch(Tree<T> left, Tree<T> right) {
            this.left = left;
            this.right = right;
        }

        @Override
        public String toString() {
            return "Branch{" +
                    "left=" + left +
                    ", right=" + right +
                    '}';
        }
    }

    public static <T extends Comparable> Tree<T> emptyTree() { return new Empty<T>(); }

    public int size() {
        if ( this instanceof Leaf ) {
            return 1;
        }
        if ( this instanceof Branch b ) {
            return b.left.size() + b.right.size();
        }
        return 0;
    }
    public <T> boolean contains( T i) {
        if (this instanceof Leaf leaf) { //является листом
            if (leaf.value == i) {
                return true; //вернуть обновленный бранч
            }
        }
        if ( this instanceof Branch b ) { //является бранчем
            if ( b.left.contains(i)) { //если размер левого меньше или равно размеру правого
                return true;
            }
            if ( b.right.contains(i)) { //если размер левого меньше или равно размеру правого
                return true;
            }
        }
        return false;
    }

    public Tree<T> insert( T t ) {
        if ( this instanceof Leaf v) { //является листом
            boolean tIsLessThanThis = t.compareTo( v.value ) < 0;//сравниваю значение новое и прежнее
            Tree<T> leftBranch = tIsLessThanThis ? new Leaf<>( t ) : v;//новое
            Tree<T> rightBranch = tIsLessThanThis ? v : new Leaf<>( t );//прежнее
            return new Branch<>( leftBranch, rightBranch ); //вернуть обновленный бранч
        }
        if ( this instanceof Branch b ) { //является бранчем
            if ( b.left.size() <= b.right.size() ) { //если размер левого меньше или равно размеру правого
                return new Branch<>( b.left.insert( t ), b.right ); //вернуть новый бранч и вставить слева
            }
            return new Branch<>( b.left, b.right.insert( t ) ); //иначе новый бранч и вставить справа
        }
        return new Leaf<>( t ); //по умолчанию - новый лист
    }
    public Tree<T> delete( T t ) {
        if ( this instanceof Leaf leaf) {
            if(leaf.contains(t)) {
                return null;
            } else {
                return leaf;
            }
        }
        if ( this instanceof Branch b ) {
            if(b.left.contains(t)) {
                return new Branch<>(b.left.delete(t), b.right);
            } else {
                return new Branch<>(b.left, b.right.delete(t));
            }
        }
       return new Leaf<>( t );
    }
    public void inOrder(Branch branch) { //цетрированный обход дерева - рекурсивный метод
        if ( branch != null) {
            inOrder((Branch) branch.left); //сделать дейтсвие с левым потомком
            size++;
            inOrder((Branch) branch.right); //действие с правым потомком
        }
    }

    @Override
    public String toString() {
        return "Tree{}";
    }

    public static void main(String[] args) {
        Tree leaf1 = new Leaf(2);
        Tree leaf2 = new Leaf(3);
        Tree leaf5 = new Leaf(5);
        Tree branch = new Branch(leaf1, leaf5);
        branch = branch.insert(4);
        branch = branch.insert(3);
        branch = branch.insert(6);
        branch = branch.insert(7);
        System.out.println(branch);
        System.out.println(branch.contains(8));
        System.out.println(branch.contains(7));
        System.out.println(leaf2.delete(8));
        System.out.println(branch.delete(5));
        branch.delete(2);
        System.out.println(branch);
        //branch.inOrder((Branch) branch);
        //System.out.println(size);

    }
}

// t5=Branch{
//      left=Leaf{value=2},
//      right=Leaf{value=5}
// }

// t4=Branch{
//      left=Branch{
//              left=Leaf{value=2},
//              right=Leaf{value=4}
//      },
//      right=Leaf{value=5}
// }

// t3=Branch{
//      left=Branch{
//          left=Leaf{value=2},
//          right=Leaf{value=4}
//      },
//      right=Branch{
//          left=Leaf{value=3},
//          right=Leaf{value=5}
//      }
// }

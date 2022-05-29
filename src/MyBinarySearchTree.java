import java.util.Stack;

public class MyBinarySearchTree<Type extends Comparable<Type>> {

    class Node {
        public Type data;
        public Node left;
        public Node right;
        public int height = 0;

        public Node(Type data) {
            this.data = data;
        }

        public int balanceFactor() {
            return height(left) - height(right);
        }

        @Override
        public String toString() {
            return data.toString() + ":H" + height + ":B" + balanceFactor();
        }
    }

    private Node root;
    private int size;
    public long comparisons;
    private boolean isBalanced = true;
    public Integer rotations = 0;

    public MyBinarySearchTree() {
        root = null;
        size = 0;
        comparisons = 0;
    }

    public MyBinarySearchTree(boolean balance){
        this.isBalanced = balance;
    }

    public void add(Type data) {
        root = add(data, root);
    }
    private Node add(Type data, Node root) {
        if (root == null) {
            size++;
            root = new Node(data);
            return root;
        } else if (data.compareTo(root.data) < 0) {
            root.left = add(data, root.left);
        } else if (data.compareTo(root.data) > 0) {
            root.right = add(data, root.right);
        } else {
            return root;
        }
        if (isBalanced) {
            root.height = 1 + Math.max(height(root.left), height(root.right));
            int balance = root.balanceFactor();
            if (balance > 1 && data.compareTo(root.left.data) < 0) {
                return rightRotate(root);
            }else if (balance < -1 && data.compareTo(root.right.data) > 0) {
                return leftRotate(root);
            }else if (balance > 1 && data.compareTo(root.left.data) > 0) {
                root.left = leftRotate(root.left);
                return rightRotate(root);
            }else if (balance < -1 && data.compareTo(root.right.data) < 0) {
                root.right = rightRotate(root.right);
                return leftRotate(root);
            }
        } else {
            root.height = 1 + Math.max(height(root.left), height(root.right));
        }
        return root;
    }



    public void remove(Type data) {
        root = remove(root, data);
    }
    private Node remove(Node root, Type data) {
        if (root == null) {
            return null;
        }
        if (data.compareTo(root.data) < 0) {
            root.left = remove(root.left, data);
        } else if (data.compareTo(root.data) > 0) {
            root.right = remove(root.right, data);
        } else {
            if (root.left == null) {
                return root.right;
            } else if (root.right == null) {
                return root.left;
            }
            root.data = findMin(root.right).data;
            root.right = remove(root.right, root.data);
        }
        if (isBalanced) {
            root.height = 1 + Math.max(height(root.left), height(root.right));
            int balance = root.balanceFactor();
            if (balance > 1 && root.left.balanceFactor() >= 0) {
                return rightRotate(root);

            } else if (balance < -1 && root.right.balanceFactor() <= 0) {
                return leftRotate(root);

            } else if (balance > 1 && root.left.balanceFactor() < 0) {
                root.left = leftRotate(root.left);
                return rightRotate(root);

            } else if (balance < -1 && root.right.balanceFactor() > 0) {
                root.right = rightRotate(root.right);
                return leftRotate(root);
            }

        } else {
            root.height = 1 + Math.max(height(root.left), height(root.right));
        }
        return root;
    }
    private Node leftRotate(Node root) {
        Node temp = root.right;
        root.right = temp.left;
        temp.left = root;
        updateHeight(root);
        updateHeight(temp);
        rotations++;
        return temp;
    }

    private Node rightRotate(Node root) {
        Node temp = root.left;
        root.left = temp.right;
        temp.right = root;
        updateHeight(root);
        updateHeight(temp);
        rotations++;
        return temp;
    }
    private Node findMin(Node right) {
        if (right == null) {
            return null;
        }
        if (right.left == null) {
            return right;
        }
        return findMin(right.left);
    }
    private void updateHeight(Node root) {
        if (root == null) {
            return;
        }
        int leftHeight = root.left == null ? -1 : root.left.height;
        int rightHeight = root.right == null ? -1 : root.right.height;
        root.height = 1 + Math.max(leftHeight, rightHeight);
    }
    private Node rebalance(Node node) {
        if (node == null) {
            return null;
        }
        int leftHeight = node.left == null ? 0 : node.left.height;
        int rightHeight = node.right == null ? 0 : node.right.height;
        node.height = Math.max(leftHeight, rightHeight) + 1;

        if (isBalanced) {
            int balanceFactor = node.balanceFactor();
            if (balanceFactor > 1) {
                if (node.left.balanceFactor() < 0) {
                    node.left = leftRotate(node.left);
                }
                rotations++;
                return rightRotate(node);
            } else if (balanceFactor < -1) {
                if (node.right.balanceFactor() > 0) {
                    node.right = rightRotate(node.right);
                }
                rotations++;
                return leftRotate(node);
            }
        }
        return node;
    }

    public Type find(Type data) {
        return find(root, data);
    }

    private Type find(Node root, Type data) {
        comparisons++;
        if (root == null) {
            return null;
        }
        if (data.compareTo(root.data) < 0) {
            return find(root.left, data);
        } else if (data.compareTo(root.data) > 0) {
            return find(root.right, data);
        } else {
            return root.data;
        }
    }

    public int getSize() {
        return size;
    }

    public int height() {
        return height(root);
    }

    private int height(Node root) {
        if (root == null) {
            return -1;
        }
        return root.height;
    }

    public boolean isEmpty() {
        return root == null;
    }

    @Override
    public String toString() {
        if(root == null)
            return "[]";
        StringBuilder sb = new StringBuilder("[");
        Stack<Node> stack = new Stack<>();
        Node current = root;
        while(!stack.isEmpty() || current != null) {
            while(current != null) {
                stack.push(current);
                current = current.left;
            }
            current = stack.pop();
            sb.append(current);
            current = current.right;
            if(!stack.isEmpty() || current != null)
                sb.append(", ");
        }

        return sb.append("]").toString();
    }
}


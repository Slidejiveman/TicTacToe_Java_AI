package TreeFiles;

public abstract class AbstractTree<E> implements TreeInterface<E> {
    public boolean isInternal(Position<E> p) {return numChildren(p) > 0;}
    public boolean isExternal(Position<E> p) {return numChildren(p) == 0;}
    public boolean isRoot(Position<E> p) {return p == root();}
    public boolean isEmpty() {return size() == 0;}
    
    /**
     * Returns the number of levels separating Position p from the root node/position
     * @param p, a node to find the depth of
     * @return an integer representing a nodes depth
     */
    public int depth(Position<E> p) {
    	if(isRoot(p)) {
    		return 0;
    	} else {
    		return 1 + depth(parent(p));
    	}
    }
    
    /**
     * Returns the height of the entire tree
     * @param p, a node
     * @return an integer representing the tree's height
     */
    public int height(Position<E> p) {
    	int height = 0;
    	for(Position<E> c : children(p)) {
    		height = Math.max(height, 1 + height(c));
    	}
    	return height;
    }
}

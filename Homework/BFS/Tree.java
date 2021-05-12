import java.util.List;
import java.util.Optional;
import java.util.Queue;
import java.util.ArrayDeque;
import java.util.ArrayList;


public class Tree<T>{
	private T value;
	private List<Tree<T>> children;
	private List<Tree<T>> getChildren;
	
	private Tree(T value) {
		this.value = value;
		this.children = new ArrayList<>();
	}
	
	public static <T> Tree<T> of (T value) {
		return new Tree<>(value);
	}
	
	public Tree<T> addChild(T value) {
		Tree<T> newChild = new Tree<>(value);
		children.add(newChild);
		return newChild;
	}






public static <T> Optional<Tree<T>> search(T value, Tree<T> root) {
	Queue<Tree<T>> queue = new ArrayDeque<>();
	queue.add(root);
	
	while(!queue.isEmpty()) {
		Tree<T> currentNode = queue.remove();
		if (( currentNode).getValue().equals(value)) {
			return Optional.of(currentNode);
		} else {
			queue.addAll(currentNode.getChildren);
		}
	}
	return Optional.empty();
		
	}

private Object getValue() {
	
	return this.value;
}
	
	

}
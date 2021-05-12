package dfs;



public class DFS {
	static void traversal (Node node) {
		if (node == null) {
			return;
		}
		
		System.out.println(node.data);
		traversal(node.left);
		traversal(node.right);
	}
}
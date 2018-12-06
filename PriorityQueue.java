

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.NoSuchElementException;

public class PriorityQueue<E> {
	/**
	 * A comparator to compare entries in the priority queue.
	 */
	private Comparator<E> comp;
	/**
	 * An ArrayList which stores entries in the priority queue.
	 */
	private ArrayList<E> heap = new ArrayList<E>();
	/**
	 * A HashMap which stores the position in the priority queue of each element.
	 */
	private HashMap<E,Integer> map = new HashMap<E,Integer>();
	
	public PriorityQueue(Comparator<E> comp) {
		this.comp = comp;
	}
		
	/**
	 * Removes the element with the smallest priority in the queue. 
	 * The complexity of removal is the same as siftDown which is O(log n)
	 */
	public void removeSmallest() {
		assert invariant() : showHeap();
		if (size() == 0) {
			throw new NoSuchElementException();
		}
		heap.set(0, heap.get(heap.size()-1));
		heap.remove(heap.size()-1);
		
		//preserves the invariant
		if (heap.size() > 0) siftDown(0);
		assert invariant() : showHeap();
	}
	
	/**
	 * Returns the element with the smallest priority in the queue.
	 * Time complexity is O(1).
	 * @return - The element with the smallest priority in the queue.
	 */
	public E getSmallest() {
		
		//check if there are any elements in the queue
		if(heap.size() == 0) {
			throw new NoSuchElementException();
		}
		//return first element in queue
		return heap.get(0);
	}
	
	/**
	 * Adds a new element to the priority queue.
	 * Complexity is the same as siftUp which is O(log n).
	 * @param x - The element to add to the priority queue.
	 */
	public void add(E x) {
		assert invariant() : showHeap();
			heap.add(x);
			map.put(x,heap.size()-1);
			// preserves the invariant
			siftUp((heap.size()-1));
		assert invariant() : showHeap();
	}
	
	/**
	 * Replaces an element in the priority queue with a new element
	 * Since map.get() is O(1) the complexity of replacing an element is the same as
	 * siftUp which is O(log n).
	 * @param oldE - The element to be replaced.
	 * @param newE - The element to replace the old one.
	 */
	public void update(E oldE, E newE) {
		assert invariant() : showHeap();
		int index = map.get(oldE);
		if(index != -1) {
			heap.set(index, heap.get(0));
			siftUp(index);
			heap.set(0, newE);
			siftDown(0);
		}
		assert invariant() : showHeap();
	}
	
	public int size() {
		return heap.size();
	}
	
	private void siftUp(int index) {
		E value = heap.get(index);
		map.remove(value,index);
		while(parent(index) >= 0) {
			int parent = parent(index);
			E parentValue = heap.get(parent);
			if(comp.compare(value, parentValue) < 0) {
				
				heap.set(index, parentValue);
				map.remove(parentValue,parent);
				map.put(parentValue,index);
				index = parent;
			} else break;
			
			if(index == 0) {
				break;
			}
		}
		heap.set(index,value);
		map.put(value,index);
	}
	
	private void siftDown(int index) {
		E value = heap.get(index);
		map.remove(value,index);
		while(leftChild(index) < heap.size()) {
			int left = leftChild(index);
			int right = rightChild(index);
			int child = left;
			E childValue = heap.get(left);
			
			if(right < size()) {
				E rightValue = heap.get(right);
				if(comp.compare(childValue,rightValue)>0) {
					child = right;
					childValue = rightValue;
				}
			}
			
			if(comp.compare(value, childValue) > 0 ) {
				heap.set(index, childValue);
				map.remove(childValue,child);
				map.put(childValue,index);
				index = child;
			} else break;
		}
		
		heap.set(index, value);
		map.put(value,index);
	}

	private int leftChild(int index) {
		return 2*index + 1;
	}
	
	private int rightChild(int index) {
		return 2*index+2;
	}
	
	private int parent(int index) {
		return (index-1)/2;
	}
	
	private boolean invariant(){
		boolean preserved = true;
		int parent = 0;
		//Preserved becomes false if the invariant is broken at some node. At most has to
		//go through every node in the tree.
		while(preserved) {
			if(heap.size() == 0 || parent == heap.size()) break;
			E parentValue = heap.get(parent);
			//Check if the map has the right index stored for this value.
			if(parent != (map.get(parentValue))) {
				preserved = false;
				break;
			};
			if(leftChild(parent)<heap.size()) {
				E leftValue = heap.get(leftChild(parent));
				if(rightChild(parent)<heap.size()) {
					E rightValue = heap.get(rightChild(parent));
					preserved = comp.compare(parentValue,leftValue)<=0 &&
								comp.compare(parentValue,rightValue)<=0;
				}else {
					preserved = comp.compare(parentValue,leftValue)<=0;
				}
			}		
			parent++;
		}
		return preserved;
	}

	//Prints out each parent and its children.
	private String showHeap(){
		int parent = 0;
		int left = leftChild(parent);
		int right = rightChild(parent);
		StringBuilder sb = new StringBuilder();
		sb.append("\n");
		while(left < heap.size()) {
			sb.append(heap.get(parent).toString() + " parent of " 
						+ heap.get(left).toString());
			if(right < heap.size()) {
				sb.append(" and " + heap.get(right).toString());
			}
			sb.append("\n");
			parent++;
			left = leftChild(parent);
			right = rightChild(parent);
		}
		return sb.toString();
	}
}
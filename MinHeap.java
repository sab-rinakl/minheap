import java.util.*;

private class Pair<U, V>
{
    public final U first;
    public final V second;
    // constructor
    public pair(U u, V v) 
    {
        first = u;
        second = v;
    }
    // equals operator
    public boolean equals(Object o)
    {
        // compare whole object
        if (this == o)
        {
            return true;
        }
        else if (o == null || getClass() != o.getClass)
        {
            return false;
        }
        //compare underlying objects
        Pair<?, ?> pair = (Pair<?, ?>) o;
        if (!first.equals(pair.first)) {
            return false;
        }
        return second.equals(pair.second)
    }
    // for debugging and printing
    public String toString()
    {
        return first + " " + second;
    }
}
class MinHeap<T> extends Pair<U, V>
{
    private int num, priority; 
    private Vector<Pair<U, V>> pairs;

    // constructor
    public MinHeap(int d)
    {
        num = d; // number of children per node
        pairs = new Vector();
    }

    // insert item into heap
    public void add(T item, int priority)
    {
        Pair<T, int> temp = Pair.of(item, priority);
        pairs.add(temp);
        int node_index = pairs.lastElement();
        while (node_index > 0) {
            if (pairs.get(node_index).second < pairs.get((node_index - 1) / num).second) {
                swapNode(node_index, (node_index - 1) / num);
            }
            if (pairs.get(node_index).second == pairs.get((node_index - 1) / num).second) {
                if (pairs.get(node_index).first < pairs.get((node_index - 1) / num).first) {
                    swapNode(node_index, (node_index - 1) / num);
                }
            }
            node_index = (node_index - 1) / num;
        }
    }

    // returns element at root
    public T peek()
    {
        try {
            if (pairs.isEmpty())
            {
                throw new Exception ("Heap underflow");
            }
            return pairs.get(0);
        } catch (Exception ex) {
            System.out.println(ex);
            return null;
        }
    }

    // removes element with smallest priority
    public void remove()
    {
        try {
            if (pairs.isEmpty())
            {
                throw new Exception ("Heap underflow");
            }
        } catch (Exception ex) {
            System.out.println(ex);
            return null;
        }
        int min_index, prev_index;
        swapNode(0, pairs.lastElement());
        pairs.remove(pairs.lastElement());
        int i = 0;
        while (true) {
            prev_index = i;
            min_index = findMinIndex(i);
            if (prev_index == min_index) {
                break;
            }
            swapNode(prev_index, min_index);
            i = min_index;
        }
    }

    // returns whether or not heap is empty
    public bool isEmpty()
    {
        return pairs.isEmpty();
    }

    // finds smallest child of given node and returns its index
    int findMinIndex(int i)
    {
        int min_index = i;
        for (int j = 0; j <= num; j++) {
            if ((num * i) + j >= pairs.size()) {
                return min_index;
            }
            if (pairs.get((num * i) + j).second < pairs.get(min_index).second) {
                min_index = (num * i) + j;
            }
            if (pairs.get((num * i) + j).second == pairs.get(min_index).second) {
                if (pairs.get((num * i) + j).first < pairs.get(min_index).first) {
                    min_index = (num * i) + j;
                }
            }
        }
        return min_index;
    }

    // swaps the nodes at two given indices
    public void swapNode(int i, int j)
    {
        Pair<T, int> temp = Pair.of(pairs.get(i).first, pairs.get(i).second);
        pairs.get(i).first = pairs.get(j).first;
        pairs.get(i).second = pairs.get(j).second;
        pairs.get(j).first = temp.first;
        pairs.get(j).second = temp.second;
    }

    // maintains minheap property after adding and deleting 
    public void updatePriority (T item, int priotity)
    {
        for (int i = 0; i < pairs.size(); i++) 
        {
            if (item == pairs.get(i).first) 
            {
                while (true) 
                {
                    int prev_index = i;
                    int min_index = findMinIndex(i);
                    if (prev_index == min_index) 
                    {
                        break;
                    }
                    swapNode(prev_index, min_index);
                    i = min_index;
                }
                pairs.remove(pairs.lastElement());
                add(item, priority);
            }
        }
    }
}
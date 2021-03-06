import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.Map;
import java.util.HashMap;

import static java.util.Arrays.sort;

/**
 * Created by zhouboli on 16/5/16.
 */

class ClassicSortingAlgorithms
{
    void bubbleSort(int[] a) { //O(n^2) stable
        int len = a.length;
        for (int i = 0; i < len; i ++) {
            for (int j = 0; j < len-i-1; j ++) {
                if (a[j] < a[j+1]) {
                    //swap
                    int tmp = a[j];
                    a[j] = a[j+1];
                    a[j+1] = tmp;
                }
            }
        }
    }

    void selectSort(int[] a) { //O(n^2) unstable
        int min = 0;
        for (int i = 0; i < a.length; i ++) {
            for (int j = i; j < a.length; j ++) {
               if (a[j] < a[min]) {
                   min = j;
               }
            }
            //swap if necessary
            if (min != i) {
                int tmp = a[min];
                a[min] = a[i];
                a[i] = tmp;
            }
        }
    }

    void insertSort(int[] a) {
        for (int i = 1; i < a.length; i ++) {
            int j = i-1;
            int cur = a[i];
            while (j >= 0 && a[j] > cur) {
                a[j+1] = a[j];
                j --;
            }
            //since j -- as above
            a[j+1] = cur;
        }
    }

    void quickSort(int[] a, int start, int end) {
        int i = start, j = end;
        int pivot = a[(start+end)/2]; //choose a pivot in array
        while (i <= j) { //iterate the code below until they meet
            while (a[j] >= pivot) j --; //find the one smaller than pivot otherwise move left
            while (a[i] <= pivot) i ++; //find the one greater than pivot otherwise move right
            if (i < j) { //exchange them and move on
                int tmp = a[i];
                a[i] = a[j];
                a[j] = tmp;
                i ++;
                j --;
            }
        }
        if (i < end) quickSort(a, i, end);
        if (j > start) quickSort(a, start, j);
    }

    void quickSort2(int[] a, int start, int end) {
        int index = partition(a, start, end);
        if (start < index - 1) quickSort2(a, start, index-1);
        if (index < end) quickSort2(a, partition(a, start, end), end);

    }

    private int partition(int[] a, int start, int end) {
        //can be other
        int pivot = a[(start+end)/2]; //choose a pivot in array
        while (start < end) { //iterate the code below until they meet
            while (a[end] >= pivot) end --; //find the one smaller than pivot otherwise move left
            while (a[start] <= pivot) start ++; //find the one greater than pivot otherwise move right
            if (start < end) { //exchange them and move on
                int tmp = a[start];
                a[start] = a[end];
                a[end] = tmp;
                start ++;
                end --;
            }
        }
        return start;
    }

    int median(int[] a) {
        Arrays.sort(a);
        return a.length%2 == 0 ? (a[a.length/2]+a[(a.length-1)/2])/2 : a[a.length/2];
    }

    //to be tested
    int quickMedian(int[] a) {
        int mid = 0;
        while (mid != a.length/2) {
            if (mid > a.length/2) {
                mid = partition(a, 0, mid);
            }
            if (mid < a.length/2) {
                mid = partition(a, mid, a.length-1);
            }
        }
        return a[mid];
    }
}

class TrieNode
{
    // Initialize your data structure here.
    boolean isWord;
    TrieNode[] children;

    public TrieNode()
    {
        children = new TrieNode[26];
    }
}

class Trie
{
    private TrieNode root;

    public Trie()
    {
        root = new TrieNode();
    }

    // Inserts a word into the trie.
    public void insert(String word)
    {
        TrieNode cur = root;
        //按word的单词顺序遍历到底然后标记为一个单词
        for (int i = 0; i < word.length(); i ++)
        {
            int index = word.charAt(i) - 'a';
            if (cur.children[index] == null)
            {
                cur.children[index] = new TrieNode();
                cur.children[index].isWord = false;
            }
            cur = cur.children[index];
        }
        cur.isWord = true;
    }

    // Returns if the word is in the trie.
    public boolean search(String word)
    {
        return false;
    }

    // Returns if there is any word in the trie
    // that starts with the given prefix.
    public boolean startsWith(String prefix)
    {
        return false;
    }
}

class TreeNode
{
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode(int x) { val = x; }
}

class ListNode
{
    int val;
    ListNode next;

    public ListNode(int x)
    {
        val = x;
        next = null;
    }

    public String toString()
    {
        return String.valueOf(this.val);
    }
}

/**
 * LRU Cache (Hard)
 */

class LRUNode
{
    int key, val;
    LRUNode next, pre;
    public LRUNode (int key, int val)
    {
        this.key = key;
        this.val = val;
    }
}

class LRUCache
{
    int capacity;
    LRUNode head, tail;
    HashMap<Integer, LRUNode> map;
    public LRUCache(int capacity)
    {
        map = new HashMap<Integer, LRUNode>();
        this.capacity = capacity;
        head = new LRUNode(0, 0);
        tail = new LRUNode(0, 0);
        head.next = tail;
        tail.pre = head;
        tail.next = null;
        head.pre = null;
    }

    private void addFirst(LRUNode node)
    {
        node.next = head.next;
        node.next.pre = node;
        head.next = node;
        node.pre = head;
    }

    private void delete(LRUNode node)
    {
        node.pre.next = node.next;
        node.next.pre = node.pre;
    }

    public int get(int key)
    {
        if (map.get(key) != null)
        {
            LRUNode node = map.get(key);
            delete(node);
            addFirst(node);
            return node.val;
        }
        return -1;
    }

    public void set(int key, int value)
    {
        if (map.get(key) != null)
        {
            LRUNode node = map.get(key);
            node.val = value;
            delete(node);
            addFirst(node);
        }
        else
        {
            if (map.size() == capacity)
            {
                map.remove(tail.pre.key);
                delete(tail.pre);
            }
            LRUNode node = new LRUNode(key, value);
            addFirst(node);
            map.put(key, node);
        }
    }
}

public class Leet
{
    /**
     * Add Two Numbers (cannot use operator + or -)
     */
    int getSum(int a, int b) {
        if (a == 0) return b;
        if (b == 0) return a;
        while (b != 0) {
            a = a & b;
            int carry = a ^ b;
            b = carry << 1;
        }
        return a;
    }

    /**
     * Intersection of Two Arrays
     */
    public int[] intersection(int[] nums1, int[] nums2)
    {
        int[] small, big;
        if (nums1.length >= nums2.length)
        {
            small = nums2;
            big = nums1;
        }
        else
        {
            small = nums1;
            big = nums2;
        }
        Set<Integer> s = new HashSet<>(),
                     r = new HashSet<>();
        for (int x : big) s.add(x);
        for (int x : small)
        {
            if (s.contains(x)) r.add(x);
        }
        Integer[] ra = new Integer[r.size()];
        int[] result = new int[r.size()];
        r.toArray(ra);
        for (int i = 0; i < ra.length; i ++) result[i] = ra[i];
        return result;
    } //ugly honestly

    /**
     * Find the Duplicate Number
     */


    /**
     * Longest Consecutive Sequence
     */
    public int longestConsecutive(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        Set<Integer> set = new HashSet<>();
        for (int num : nums) set.add(num);
        int max = 1;
        for (int num : nums) {
            if (set.remove(num)) {
                int val = num;
                int count = 1;
                while (set.remove(val-1)) val --;
                count += num - val;

                val = num;
                while (set.remove(val + 1)) val ++;
                count += val - num;
                max = Math.max(max, count);
            }
        }
        return max;
    }

    /**
     * Possible Phone Password Combinations
     */
    //4-bit password: 10E4 = 10000
    //9-number pad gesture password: A(9, 4) + A(9, 5) + ... + A(9, 9) = 985,824 (4 points least)

    /**
     * Find Peak Element
     */
    public int findPeakElement(int[] nums) {
        int low = 0, high = nums.length;
        while (low < high) {
            int mid = (low+high)/2;
            if (nums[mid] > nums[mid+1]) high = mid;
            else low = mid+1;
        }
        return low;
    }

    /**
     * Lowest Common Ancestor of a Binary Tree
     */
    public TreeNode lowestCommonAncestorInBinaryTree(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null) return null;
        if (root == p || root == q) return root;
        TreeNode left = lowestCommonAncestor(root.left, p, q);
        TreeNode right = lowestCommonAncestor(root.right, p, q);
        return left != null && right != null ? root : left != null ? left : right;
    }

    /**
     * Binary Tree Paths
     */
    public List<String> binaryTreePaths(TreeNode root) {
        List<String> list = new ArrayList<String>();
        if (root == null) return list;
        binaryTreePath(root, String.valueOf(root.val), list);
        return list;
    }

    private void binaryTreePath(TreeNode node, String path, List<String> list) {
        if (node.left == null && node.right == null) list.add(path);
        if (node.left != null) binaryTreePath(node.left, new String(path + "->" + node.left.val).toString(), list);
        if (node.right != null) binaryTreePath(node.right, new String(path + "->" + node.right.val).toString(), list);
    }

    /**
     * Swap Nodes in Pairs
     */
    public ListNode swapPairs(ListNode head) {
        if (head == null || head.next == null) return head;
        ListNode second = head.next;
        ListNode third = second.next;
        second.next = head;
        head.next = swapPairs(third);
        return second;
    }

    /**
     * Binary Tree Level Order Traversal II (bottom-up level)
     */
    public static List<List<Integer>> levelOrderBottom(TreeNode root)
    {
        List<List<Integer>> tree = new ArrayList<List<Integer>>();
        if (root == null) return tree;
        addNode(tree, root, 0);
        for(int i = 0; i < tree.size()-1; i ++)
        {
            int last = tree.size()-1;
            List<Integer> tmp = tree.get(last);
            tree.remove(last);
            tree.add(i, tmp);
        }
        //addNodeHead(tree, root, 0);
        return tree;
    }

//	private static void addNodeHead(List<List<Integer>> tree, TreeNode node, int level)
//	{
//		List<Integer> list;
//		if (tree.size() < level+1)
//		{
//			list = new ArrayList<Integer>();
//			tree.add(0, list);
//		}
//		else list = tree.get(0);
//		list.add(node.val);
//		level ++;
//		if (node.left != null) addNodeHead(tree, node.left, level);
//		if (node.right != null) addNodeHead(tree, node.right, level);
//	}

    /**
     * Binary Tree Level Order Traversal
     */
    public static List<List<Integer>> levelOrder(TreeNode root)
    {
        List<List<Integer>> tree = new ArrayList<List<Integer>>();
        if (root == null) return tree;
        addNode(tree, root, 0);
        return tree;
    }

    private static void addNode(List<List<Integer>> tree, TreeNode node, int level)
    {
        List<Integer> list; //prepared for current level
        if (tree.size() < level+1) //tree first comes to this level, create a new list for this level
        {
            list = new ArrayList<Integer>();
            tree.add(list); //add the new list to tree
        }
        else list = tree.get(level); //list already created, get it
        list.add(node.val); //add the current node value to this list
        level ++; //move to the next level
        if (node.left != null) addNode(tree, node.left, level); //left not null then move to node's left
        if (node.right != null) addNode(tree, node.right, level); //same
    }

    /**
     * Binary Tree Inorder Traversal
     */
    //Recursive
    public List<Integer> inorderTraversal(TreeNode root)
    {
        List<Integer> list = new ArrayList<Integer>();
        if (root == null) return list;
        inorderVisit(root, list);
        return list;
    }
    private void inorderVisit(TreeNode node, List<Integer> list)
    {
        if (node == null) return;
        inorderVisit(node.left, list);
        list.add(node.val);
        inorderVisit(node.right, list);
    }
    //Iterative
    public List<Integer> inorderTraversalIteratively(TreeNode root) {
        List<Integer> list = new ArrayList<Integer>();
        if (root == null) return list;
        Stack<TreeNode> stack = new Stack<TreeNode>();
        while (root != null || !stack.isEmpty()) {
            while (root != null) {
                stack.push(root);
                root = root.left;
            }
            root = stack.pop();
            list.add(root.val);
            root = root.right;
        }
        return list;
    }

    /**
     * Binary Tree Postorder Traversal
     */
    //Recursive
    public List<Integer> postorderTraversal(TreeNode root)
    {
        List<Integer> list = new ArrayList<Integer>();
        if (root == null) return list;
        postorderVisit(root, list);
        return list;
    }
    private void postorderVisit(TreeNode node, List<Integer> list)
    {
        if (node == null) return;
        postorderVisit(node.left, list);
        postorderVisit(node.right, list);
        list.add(node.val);
    }
    //Iterative
    public List<Integer> postorderTraversalIteratively(TreeNode root) {
        List<Integer> list = new ArrayList<Integer>();
        if (root == null) return list;
        Stack<TreeNode> stack = new Stack<TreeNode>();
        while (root != null || !stack.isEmpty()) {
            while (root != null) {
                stack.push(root);
                list.add(0, root.val);
                root = root.right;
            }
            root = stack.pop();
            root = root.left;
        }
        return list;
    }

    /**
     * Binary Tree Preorder Traversal
     */
    //Recursive
    public List<Integer> preorderTraversal(TreeNode root)
    {
        List<Integer> list = new ArrayList<Integer>();
        if (root == null) return list;
        preorderVisit(root, list);
        return list;
    }
    private void preorderVisit(TreeNode node, List<Integer> list)
    {
        if (node == null) return;
        list.add(node.val);
        preorderVisit(node.left, list);
        preorderVisit(node.right, list);
    }
    //Iteratively
    public List<Integer> preorderTraversalIteratively(TreeNode root) {
        List<Integer> list = new ArrayList<Integer>();
        Stack<TreeNode> stack = new Stack<TreeNode>();
        while (!stack.isEmpty() || root != null) {
            while (root != null) {
                stack.push(root);
                list.add(root.val);
                root = root.left;
            }
            root = stack.pop();
            root = root.right;
        }
        return list;
    }

    /**
     * Path Sum II
     */
    public List<List<Integer>> pathSum(TreeNode root, int sum) {
        List<List<Integer>> list = new ArrayList<List<Integer>>();
        if (root == null) return list;
        dfs(root, new ArrayList<Integer>(), list, sum);
        return list;
    }

    private void dfs(TreeNode root, List<Integer> path, List<List<Integer>> list, int sum) {
        if (root == null) return;
        path.add(root.val);
        if (root.left == null && root.right == null && sum-root.val == 0) {
            list.add(path); //2. 直到符合sum的path出现则添加进结果list (其他方法是不复制每一个path,当符合sum的path出现则拷贝此path进结果list)
            return;
        }
        dfs(root.left, new ArrayList<Integer>(path), list, sum-root.val); //1. 每次调用都复制一个path
        dfs(root.right, new ArrayList<Integer>(path), list, sum-root.val);
    }

    /**
     * Path Sum
     */
    //递得一手好归
    public static boolean hasPathSum(TreeNode root, int sum)
    {
        if (root == null) return false;
        if (root.val == sum && root.left == null && root.right == null) return true;
        return hasPathSum(root.left, sum-root.val) || hasPathSum(root.right, sum-root.val);
    }

    /**
     * Pascal's Triangle
     */
    public static List<List<Integer>> generate(int numRows)
    {
        List<List<Integer>> list = new ArrayList<List<Integer>>();
        if (numRows == 0) return list;
        for (int row = 0; row < numRows; row ++)
        {
            List<Integer> cur = new ArrayList<Integer>();
            cur.add(1);
            for (int index = 1; index < row; index ++)
            {
                if (row > 1)
                {
                    List<Integer> pre = list.get(row-1);
                    cur.add(pre.get(index-1) + pre.get(index));
                }
            }
            if (row > 0) cur.add(1);
            list.add(cur);
        }
        return list;
    }

    /**
     * Ugly Number
     */
    public static boolean isUgly(int num)
    {
        if (num <= 0) return false;
        if (num == 1) return true;
        while (num % 2 == 0) num /= 2;
        while (num % 3 == 0) num /= 3;
        while (num % 5 == 0) num /= 5;
        return num == 1 ? true : false;
    }

    /**
     * Isomorphic Strings
     */
    public static boolean isIsomorphic(String s, String t)
    {
        if (s == null && t == null) return true;
        if (s.length() != t.length()) return false;
        int i = 0;
        HashMap<Character,Character> map=new HashMap<Character,Character>();
        while(i < s.length())
        {
            if(map.containsKey(s.charAt(i)))
            {
                if(map.get(s.charAt(i)) != t.charAt(i)) return false;
            }
            else if(map.containsValue(t.charAt(i))) return false;
            else map.put(s.charAt(i),t.charAt(i));
            i++;
        }
        return true;
    }

    /**
     * Implement Queue using Stacks
     */
    class MyQueue
    {
        Stack<Integer> tt, th, cur;
        MyQueue()
        {
            tt = new Stack<Integer>();
            th = new Stack<Integer>();
            cur = tt;
        }
        // Push element x to the back of queue.
        public void push(int x)
        {
            if (cur == tt) cur.push(x);
            else if (cur == th)
            {
                while (cur.empty() == false) tt.push(cur.pop());
                tt.push(x);
                cur = tt;
            }
        }

        // Removes the element from in front of queue.
        public void pop()
        {
            if (cur == tt)
            {
                while (tt.size() > 1) th.push(tt.pop());
                tt.pop();
                cur = th;
            }
            else if (cur == th) th.pop();
        }

        // Get the front element.
        public int peek()
        {
            if (cur == tt)
            {
                while (cur.empty() == false) th.push(cur.pop());
                cur = th;
            }
            return th.peek();
        }

        // Return whether the queue is empty.
        public boolean empty()
        {
            return tt.empty() && th.empty();
        }
    }

    /**
     * Implement Stack using Queues
     */
    class MyStack
    {
        LinkedBlockingQueue<Integer> p, q, cur, bak;
        Integer top;
        MyStack()
        {
            p = new LinkedBlockingQueue<Integer>();
            q = new LinkedBlockingQueue<Integer>();
            cur = p;
            bak = q;
        }
        // Push element x onto stack.
        public void push(int x) {
            try {
                cur = p.isEmpty() && q.isEmpty() ? p : p.isEmpty() ? q : p;
                bak = p == cur ? q : p;
                top = x;
                cur.put(x);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Removes the element on top of the stack.
        public void pop() {
            cur = p.isEmpty() && q.isEmpty() ? p : p.isEmpty() ? q : p;
            bak = p == cur ? q : p;
            while(cur.size() > 1)
            {
                Integer x = cur.remove();
                try {
                    bak.put(x);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (cur.size() == 1) top = x;
            }
            cur.remove();
            LinkedBlockingQueue<Integer> tmp = bak;
            bak = cur;
            cur = tmp;
        }

        // Get the top element.
        public int top() {
            return top;
        }

        // Return whether the stack is empty.
        public boolean empty() {
            return cur.isEmpty();
        }
    }

    /**
     * Two Sum
     */
    public static int[] twoSum(int[] nums, int target)
    {
        if (nums == null) return null;
        HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
        int base = nums[0];
        for(int i = 0; i < nums.length; i ++)
        {
            base = nums[i];
            int check = target - base;
            if (map.containsKey(check))
            {
                int[] result = {i, map.get(check)};
                return result;
            }
            map.put(nums[i], i);
        }
        int[] result = {};
        return result;
    }

    /**
     * ZigZag Conversion
     */
    public static String convert(String s, int numRows)
    {
        List<StringBuffer> zz = new ArrayList<StringBuffer>();
        if (s == null) return null;
        if (s.length() <= numRows || numRows == 1) return s;
        for (int i = 0; i < numRows; i ++) zz.add(new StringBuffer());
        int col = 0, row = 0;
        for (int i = 0; i < s.length(); i ++)
        {
            String letter = String.valueOf(s.charAt(i));
            StringBuffer buf = zz.get(row);
            buf.append(letter);
            if (row == numRows-1 || col%(numRows-1) != 0) { row --; col ++; }
            else if (col%(numRows-1) == 0 && row < numRows-1 && row >= 0) row ++;
        }
        StringBuffer result = new StringBuffer();
        for (int i = 0; i < numRows; i ++)
        {
            result.append(zz.get(i));
        }
        return result.toString();
    }

    /**
     * Palindrome Linked List
     */
    public boolean isPalindrome(ListNode head)
    {
        if (head == null) return true;
        ListNode node = head;
        List<Integer> list = new ArrayList<Integer>();
        while (node != null)
        {
            list.add(node.val);
            node = node.next;
        }
        int i = 0, j = list.size()-1;
        while (i <= j)
        {
            if (list.get(i).intValue() != list.get(j).intValue()) return false;
            i ++;
            j --;
        }
        return true;
    }

    /**
     * Symmetric Tree (Do not complicate it)
     */
    public static boolean isSymmetric(TreeNode root)
    {
        if (root == null) return true;
        return isSymmetric(root.left, root.right);
    }

    private static boolean isSymmetric(TreeNode x, TreeNode y)
    {
        if (x == null && y == null) return true;
        if (x == null || y == null) return false;
        return x.val == y.val && isSymmetric(x.left, y.right) && isSymmetric(x.right, y.left);
    }

    /**
     * Letter Combinations of a Phone Number (M)
     */
    public static List<String> letterCombinations(String digits)
    {
        List<String> comb = new ArrayList<String>();
        if (digits == null || digits.length() == 0) return comb;
        char[][] dict = {
                {},
                {},
                {'a', 'b', 'c'},
                {'d', 'e', 'f'},
                {'g', 'h', 'i'},
                {'j', 'k', 'l'},
                {'m', 'n', 'o'},
                {'p', 'q', 'r', 's'},
                {'t', 'u', 'v'},
                {'w', 'x', 'y', 'z'},
                {},
                {}
        };
        combineBFS(digits, comb, dict, "", 0);
        return comb;
    }

    private static void combineBFS(String digits, List<String> comb, char[][] dict, String str, int index)
    {
        if (index > digits.length()-1) return;
        int digit = Integer.parseInt(String.valueOf(digits.charAt(index)));
        char[] letters = dict[digit];
        for (int i = 0; i < letters.length; i ++)
        {
            String next = str+letters[i];
            if (next.length() == digits.length()) comb.add(next);
            combineBFS(digits, comb, dict, next, index+1);
        }
    }

    /**
     * Word Pattern
     */
    public static boolean wordPattern(String pattern, String str)
    {
        String[] ss = str.split(" ");
        if (pattern.length() != ss.length) return false;
        HashMap<String, String> map = new HashMap<String, String>();
        for (int i = 0; i < pattern.length(); i ++)
        {
            String key = String.valueOf(pattern.charAt(i));
            String value = ss[i];
            if (i == 1 && value.equals(ss[0]) && !key.equals(String.valueOf(pattern.charAt(0)))) return false;
            if (map.containsKey(key))
            {
                if (map.get(key).equals(value)) continue;
                else return false;
            }
            else map.put(key, value);
        }
        return true;
    }

    /**
     * Power of Four
     */
    public static boolean isPowerOfFour(int num) {
        return num > 0 && ((num - 1) & num) == 0 && (num & 0x55555555) != 0;
    }

    /**
     * Power of Three
     */
    public static boolean isPowerOfThree(int n)
    {
        return n > 0 && (int) Math.pow(3, (int) (Math.log(Integer.MAX_VALUE) / Math.log(3.0))) % n == 0;
    }

    /**
     * Power of Two
     */
    public static boolean isPowerOfTwo(int n)
    {
        return n > 0 && (int) Math.pow(2, (int) (Math.log(Integer.MAX_VALUE) / Math.log(2.0))) % n == 0;
    }

    /**
     * Linked List Cycle (One Shot)
     */
    public boolean hasCycle(ListNode head)
    {
        if (head == null || head.next == null) return false;
        if (head.next == head) return true;
        if (head.next.next == null) return false;
        if (head.next.next == head || head.next.next == head.next) return true;
        ListNode x1, x2;
        x1 = head;
        x2 = head.next.next;
        while (x1 != null && x2 != null && x2.next != null && x2.next.next != null)
        {
            if (x1 == x2) return true;
            x1 = x1.next;
            x2 = x2.next.next;
        }
        return false;
    }

    /**
     * Climbing Stairs
     */
    public static int climbStairs(int n)
    {
        if (n == 0) return 0;
        else if (n == 1 || n == 2) return n == 2 ? 2 : 1;
        else
        {
            int cur = 3;
            int sum = 0;
            int pre = 2;
            int pnd = 1;
            while (cur <= n)
            {
                sum = pre + pnd;
                pnd = pre;
                pre = sum;
                cur ++;
            }
            return sum;
        }
    }

    /**
     * Lowest Common Ancestor of a Binary Search Tree
     */
    public static TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q)
    {
        return root.val > p.val && root.val > q.val ? lowestCommonAncestor(root.left, p, q) : root.val < p.val && root.val < q.val ? lowestCommonAncestor(root.right, p, q) : root;
    }

    /**
     * Reverse String
     */
    public static String reverseString(String s)
    {
        if (s == null || s.length() <= 0) return s;
        char[] chs = s.toCharArray();
        int left = 0;
        int right = s.length() - 1;
        while (left < right && left != right)
        {
            char tmp = chs[left];
            chs[left] = chs[right];
            chs[right] = tmp;
            left ++;
            right --;
        }
        return String.valueOf(chs);
    }

    /**
     * Same Tree
     */
    public static boolean isSameTree(TreeNode p, TreeNode q)
    {
        if (p != null && q != null)
        {
            if (p.val == q.val) return isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
            else return false;
        }
        else
        {
            if (p == null && q == null) return true;
            else return false;
        }
    }

    /**
     * Move Zeroes
     */
    public static void moveZeroes(int[] nums)
    {
        int zero = 0, l = 0, r = nums.length;
        while (l < r)
        {
            if (nums[l] != 0)
            {
                int tmp = nums[zero];
                nums[zero] = nums[l];
                nums[l] = tmp;
                zero ++;
            }
            l++;
        }
    }
	/*思想：设置两个指针，一个指向第一个零，另一个指向零后方第一个非零数，交换后右移遍历一遍*/

    /**
     * Add Digits
     */
    public static int addDigits(int n)
    {
        if (n < 10) return (int) n;
        else
        {
            int result = 0;
            double num = n;
            while (num / 10 > 0)
            {
                result += num % 10;
                num /= 10;
            }
            while (result >= 10)
            {
                num = result;
                result = 0;
                while (num / 10 > 0)
                {
                    result += num % 10;
                    num /= 10;
                }
            }
            return result;
        }
    }

    /**
     * Nim game
     */
    public static Boolean canWin(int n)
    {
        return n % 4 != 0;
    }

    /**
     * Reverse LinkedList
     */
    public static ListNode reverseList(ListNode head)
    {
        if(head == null || head.next == null) return head;

        ListNode pre, cur, next;
        pre = null;
        cur = head;
        next = cur.next;
        while(next != null) {
            cur.next = pre;
            pre = cur;
            cur = next;
            next = next.next;
        }
        cur.next = pre;

        return cur;
    }

    /**
     * Remove LinkedList Node
     */
    public static ListNode removeElements(ListNode head, int val)
    {
        if(null == head) return null;
        ListNode node = new ListNode(-1);
        node.next = head;
        ListNode pre = node;
        ListNode cur = pre.next;
        while(cur != null)
        {
            if(val == cur.val) pre.next = cur.next;
            else pre = pre.next;
            cur = cur.next;
        }

        return node.next;
    }

    /**
     * Hamming Weight
     */
    public static int hammingWeight(int n)
    {
        int count = 0;
        for(int i = 0; i < 32; i ++)
        {
            if(1 == (n & 1)) count ++;
            n >>>= 1;
        }
        return count;
    }

    /**
     * Happy number
     * Example: 19 is a happy number
     *	1^2 + 9^2 = 82
     *	8^2 + 2^2 = 68
     *	6^2 + 8^2 = 100
     *	1^2 + 0^2 + 02 = 1
     */
    public static boolean isHappy(int n)
    {
        int sum = 0;
        HashSet<Integer> set = new HashSet<Integer>();
        while(!set.contains(n))
        {
            set.add(n);
            sum = 0;
            while(n>0)
            {
                sum += (n%10)*(n%10);
                n /= 10;
            }
            n = sum;
            if(1 == sum) return true;
        }
        return false;
    }

    public static int bitSum(int n)
    {
        int sum = 0;
        int x = n;
        while(x>0)
        {
            sum += (x%10)*(x%10);
            x /= 10;
        }
        return sum;
    }

    /**
     * Majority Element II (Better change to Majority Vote Algorithm in the future)
     */
    public List<Integer> majorityElmentII(int[] nums) {
        List<Integer> list = new ArrayList<>();
        int length = nums.length;
        if (nums == null || length == 0) return list;
        if (length == 1) {
            list.add(nums[0]);
            return list;
        }
        if (length == 2) {
            list.add(nums[0]);
            if (nums[0] != nums[1]) list.add(nums[1]);
            return list;
        }
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < length; i ++) {
            if (map.containsKey(nums[i])) map.put(nums[i], map.get(nums[i])+1);
            else map.put(nums[i], 1);
        }
        Iterator iter = map.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry)iter.next();
            if ((Integer)entry.getValue() > length/3) list.add((Integer) entry.getKey());
        }
        return list;
    }

    /**
     * Majority Element
     */
    public static int majorityElement(int[] nums)
    {
        sort(nums);
        return nums[nums.length/2];
    }

    /**
     * Anagrams
     * Given an array of strings, return all groups of strings that are anagrams.
     * Note: All inputs will be in lower-case.
     */
    public static List<String> anagrams(String[] strs)
    {
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        List<String> list = new ArrayList<String>();
        for(int i = 0; i < strs.length; i ++)
        {
            char[] tmp = strs[i].toCharArray();
            sort(tmp);
            if(map.containsKey(new String(tmp)))
            {
                if(!list.contains(strs[map.get(new String(tmp))])) list.add(strs[map.get(new String(tmp))]);
                list.add(strs[i]);
            } else map.put(new String(tmp), i);
        }
        return list;
    }

    /**
     * Remove Element
     */
    public static int removeElement(int[] nums, int val)
    {
        int i = 0, j = 0;
        while(i < nums.length)
        {
            if(val != nums[i])
            {
                nums[j] = nums[i];
                i ++; j ++; continue;
            }
            i ++;
        }
        return j;
    }

    /**
     * Height-balanced Binary Tree
     * Given a binary tree, determine if it is height-balanced.
     * A height-balanced binary tree is defined as a binary tree in which the depth of the two subtrees of every node never differ by more than 1.
     */
    public static boolean isBalanced(TreeNode root)
    {
        if (root == null) return true;
        if (Math.abs(heightOf(root.left) - heightOf(root.right)) > 1) return false;
        return isBalanced(root.left) && isBalanced(root.right);
    }

    //Returns height of a TreeNode
    public static int heightOf(TreeNode node)
    {
        if(node == null) return 0;
        return Math.max(heightOf(node.left), heightOf(node.right)) + 1;
    }

    /**
     * Contains Duplicates
     */
    public static boolean containsDuplicate(int[] nums)
    {
        HashSet<Integer> set = new HashSet<Integer>();
        for(int i = 0; i < nums.length; i ++)
        {
            if(set.contains(nums[i])) return true;
            set.add(nums[i]);
        }
        return false;
    }

    /**
     * Contains Duplicates in k
     * Given an array of integers and an integer k,
     * find out whether there there are two distinct indices i and j in the array
     * such that nums[i] = nums[j] and the difference between i and j is at most k.
     */
    public static boolean containsNearbyDuplicate(int[] nums, int k)
    {
        HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
        for(int i = 0; i < nums.length; i ++)
        {
            if(map.containsKey(nums[i]) && (Math.abs(map.get(nums[i]) - i) <= k)) return true;
            map.put(nums[i], i);
        }
        return false;
    }

    /**
     * Reverse Integer
     */
    public static int reverse(int x)
    {
        int ax = Math.abs(x);
        int result = 0;
        while(ax > 0)
        {
            if(result * 10 / 10 != result) return 0;
            result = result * 10 + ax % 10;
            ax /= 10;
        }
        return x >= 0 ? result : -result;
    }

    /**
     * Reverse Bits
     */
    public static int reverseBits(int n)
    {
        int rb = 0;
        for(int i = 0; i < 32; i ++)
        {
            int tmp = n & 1;
            rb |= tmp << (31 - i);
            n >>>= 1;
        }
        return rb;
    }

    /**
     * Merge Two Sorted Lists
     * Merge two sorted linked lists and return it as a new list.
     * The new list should be made by splicing together the nodes of the first two lists.
     */
    public static ListNode mergeTwoLists(ListNode l1, ListNode l2)
    {
        if(null == l1 || null == l2) return (null == l1) ? l2: l1;
        ListNode head = new ListNode(-1);
        ListNode node = head;
        while(null != l1 && null != l2)
        {
            if(l1.val <= l2.val)
            {
                node.next = l1;
                l1 = l1.next;
            }
            else
            {
                node.next = l2;
                l2 = l2.next;
            }
            node = node.next;
        }
        if(null != l1) node.next = l1;
        if(null != l2) node.next = l2;
        return head.next;
    }

    //Not finished YET!
    /**
     * String to Integer
     * Hint: Carefully consider all possible input cases.
     * If you want a challenge, please do not see below and ask yourself what are the possible input cases.
     * Notes: It is intended for this problem to be specified vaguely (ie, no given input specs).
     * You are responsible to gather all the input requirements up front.
     */
    public static int myAtoi(String str)
    {
        int index = 0;
        int sign = 1;
        char ch;
        int digit;
        int total = 0;
        //1. Empty string
        if(0 == str.length()) return 0;
        //2. Remove spaces
        while(str.charAt(index) == ' ' && index < str.length())
            index ++;
        //3. Handle signs
        if(str.charAt(index) == '+' || str.charAt(index) == '-')
        {
            if(str.charAt(index) == '+')
                sign = 1;
            else
                sign = -1;
            index ++;
        }
        //4. Convert numbers and avoid overflow
        while(index < str.length())
        {
            ch = str.charAt(index);
            if(ch < '0' || ch > '9')
                break;
            digit = ch - '0';
            //Check if total will be overflow after 10 times and add digit
            if(Integer.MAX_VALUE/10 < total || Integer.MAX_VALUE/10 == total && Integer.MAX_VALUE%10 < digit)
                return sign == 1 ? Integer.MAX_VALUE: Integer.MIN_VALUE;
            total = 10*total + digit;
            index ++;
        }
        return total * sign;
    }

    /**
     * Invert a binary tree
     * Google: 90% of our engineers use the software you wrote (Homebrew), but you can't invert a binary tree on a whiteboard so fuck off.
     */
    public static TreeNode invertTree(TreeNode root)
    {
        if(root == null) return null;
        TreeNode tmp = root.left;
        root.left = root.right;
        root.right = tmp;
        invertTree(root.left);
        invertTree(root.right);
        return root;
    }

    /**
     * Rotate Array
     * Rotate an array of n elements to the right by k steps.
     * For example, with n = 7 and k = 3, the array [1,2,3,4,5,6,7] is rotated to [5,6,7,1,2,3,4].
     */
    public static void rotate(int[] nums, int k)
    {
        if(k > nums.length) k %= nums.length;
        reverse(nums, 0, nums.length - k - 1);
        reverse(nums, nums.length - k, nums.length - 1);
        reverse(nums, 0, nums.length - 1);
    }

    public static void reverse(int[] nums, int low, int high) {
        while(low < high) {
            int tmp = nums[low];
            nums[low] = nums[high];
            nums[high] = tmp;
            low ++;
            high --;
        }
    }

    /**
     * Merge Sorted Array
     * You may assume that nums1 has enough space (size that is greater or equal to m + n) to hold additional elements from nums2.
     * The number of elements initialized in nums1 and nums2 are m and n respectively.
     */
    public static void merge(int[] nums1, int m, int[] nums2, int n) {
        int i = m - 1, j = n - 1, cur = m + n - 1;
        while(i >= 0 && j >= 0 && cur >= 0) {
            if(nums1[i] > nums2[j]) {
                nums1[cur] = nums1[i];
                i --;
            } else {
                nums1[cur] = nums2[j];
                j --;
            }
            cur --;
        }
        if(i >= 0) {
            while(i >= 0 && cur >= 0) {
                nums1[cur] = nums1[i];
                i --; cur --;
            }
        }
        if(j >= 0) {
            while(j >= 0 && cur >= 0) {
                nums1[cur] = nums2[j];
                j --; cur --;
            }
        }
    }

    /**
     * Maximum Subarray
     */
    public static int maxSubArray(int[] nums) {
        if (nums.length == 1) return nums[0];
        int sum = 0, tmp = 0;
        int max = nums[0];
        for(int i = 0; i < nums.length; i ++) {
            max = max > nums[i] ? max: nums[i];
            tmp += nums[i];
            if(tmp > 0) {
                sum = tmp > sum ? tmp: sum;
            }
            tmp = tmp < 0 ? 0: tmp;
        }
        return sum == 0 ? max: sum;
    }

    /**
     * Maximum Depth of Binary Tree
     */
    public static int maxDepth(TreeNode root) {
        if(null == root) return 0;
        return 1 + Math.max(maxDepth(root.left), maxDepth(root.right));
    }

    /**
     * Minimum Depth of Binary Tree
     */
    public static int minDepth(TreeNode root) {
        if(null == root) return 0;
        return 1 + Math.min(minDepth(root.left), minDepth(root.right));
    }

    /**
     * Remove Duplicates from Sorted Array
     */
    public static int removeDuplicates(int[] nums) {
        if(nums.length <= 1) return nums.length;
        int count = 0;
        for(int i = 1; i < nums.length; i ++) {
            if(nums[i] != nums[count]) {
                count ++;
                nums[count] = nums[i];
            }
        }
        return count + 1;
    }

    /**
     * Find the peak number
     */
    public static int findPeak(int[] nums) {
        if(0 == nums.length) return -1;
        if(1 == nums.length) return 0;
        if(2 == nums.length) return nums[0] > nums[1] ? 0: 1;
        int mid, pre, next;
        int low = 0, high = nums.length - 1;
        if(low < high) {
            while(low <= high) {
                mid = (low+high)/2;
                pre = mid-1; next = mid+1;
                if(nums[mid] > nums[pre] && nums[mid] > nums[next])
                    return mid;
                if(nums[mid] > nums[next] && nums[mid] < nums[pre])
                    high = mid-1;
                if(nums[mid] < nums[next] && nums[mid] > nums[pre])
                    low = mid+1;
            }
        }
        return -1;
    }

    /**
     * Reverse Words in a String
     */
    public static String reverseWords(String s) {
        Pattern pattern = Pattern.compile("[^\\s]+");
        Matcher matcher = pattern.matcher(s);
        Stack<String> stack = new Stack<String>();
        while(matcher.find()) {
            stack.push(matcher.group());
        }
        StringBuilder sb = new StringBuilder();
        while(!stack.empty()) {
            sb.append(stack.pop() + " ");
        }
        String res = sb.toString();
        res = res.trim();
        return res;
    }

    /**
     * Intersection of Two Linked Lists
     */
    public static ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        if(null == headA || null == headB) return null;
        int lenA = 0, lenB = 0;
        ListNode tmpA = headA.next;
        while(tmpA != null) {
            lenA ++;
            tmpA = tmpA.next;
        }
        ListNode tmpB = headB.next;
        while(tmpB != null) {
            lenB ++;
            tmpB = tmpB.next;
        }
        if(lenA > lenB) {
            int diff = lenA - lenB;
            while(diff != 0) {
                headA = headA.next;
                diff --;
            }
        }
        if(lenB > lenA) {
            int diff = lenB - lenA;
            while(diff != 0) {
                headB = headB.next;
                diff --;
            }
        }
        while(headA != null && headB != null) {
            if(headA.val == headB.val) return headA;
            headA = headA.next;
            headB = headB.next;
        }
        return null;
    }

    /**
     * Square root
     */
    public static float mySqrt(float num, float low, float high) {
        float mid;
        if(low < high) {
            while(low < high) {
                mid = (low+high)/2;
                if(Math.abs(Math.pow(mid, 2) - num) <= 0.05) return mid;
                if(Math.pow(mid, 2) - num > 0.05) high = mid;
                if(num - Math.pow(mid, 2) > 0.05) low = mid;
            }
        }
        return -1;
    }

    /**
     * Remove Nth Node From End of List
     * Given a linked list, remove the nth node from the end of list and return its head.
     */
    public static ListNode removeNthFromEnd(ListNode head, int n) {
        if(null == head) return null;
        if(null == head.next) return 1 == n ? null: head;
        int len = 1;
        ListNode tmp = head.next;
        while(tmp != null) {
            len ++;
            tmp = tmp.next;
        }
        int count = len - n - 1;
        if(count + 1 <= 0) return head.next;
        tmp = head;
        while(count > 0) {
            count --;
            tmp = tmp.next;
        }
        tmp.next = tmp.next.next;
        return head;
    }

    /**
     * Dungeon Game
     */
    public static int calculateMinimumHP(int[][] dungeon) {
        return 0;
    }

    /**
     * Length of Last Word
     */
    public static int lengthOfLastWord(String s) {
        String ts = s.trim();
        int len = ts.length();
        if(0 == len) return 0;
        int count = 0;
        while(len - 1 - count >= 0 && ts.charAt(len - 1 - count) != ' ') {
            count ++;
        }
        return count;
    }

    /**
     * Remove Duplicates from Sorted List
     */
    public static ListNode deleteDuplicates(ListNode head) {
        if(null == head || null == head.next) return head;
        ListNode cur = head;
        while(cur != null && cur.next != null) {
            if(cur.val == cur.next.val) {
                cur.next = cur.next.next;
            } else {
                cur = cur.next;
            }
        }
        return head;
    }

    /**
     * BinSearch for looped array
     */
    public static int binSearchLoop(int[] a, int key) {
        int mid; int low = 0; int high = a.length - 1;
        if(low <= high) {
            while(low <= high) {
                mid = (low + high)/2;
                if(a[mid] == key) return mid;
                if(a[mid] > a[low]) {
                    if(a[mid] > key && key > a[low]) high = mid - 1;
                    if(a[mid] < key) low = mid + 1;
                }
                if(a[mid] < a[low]) {
                    if(a[mid] < key && key < a[high]) low = mid + 1;
                    if(a[mid] > key) high = mid - 1;
                }
            }
        }
        return -1;
    }

    /**
     * Palindrome Number
     */
    public static boolean isPalindrome(int x)
    {
        if(x == 0) return true;
        int result = 0, num = x;
        while(x > 0) {
            result = 10*result+x%10;
            x /= 10;
        }
        return num == result;
    }

    /**
     * Valid Anagram
     */
    public static boolean isAnagram(String s, String t)
    {
        char[] cs = s.toCharArray();
        char[] ct = t.toCharArray();
        sort(cs);
        sort(ct);
        return Arrays.equals(cs, ct);
    }

	/*HERE IT IS*******************************************/

    public static void main(String[] args) throws Exception
    {

    }

	/*HERE IT IS*******************************************/
}
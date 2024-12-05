/******************************************************************
 *
 *   Abdullah I Khan Section 002
 *
 *   This java file contains the problem solutions of canFinish and
 *   numGroups methods.
 *
 ********************************************************************/

import java.util.*;

class ProblemSolutions {

    /**
     * Method canFinish
     * <p>
     * You are building a course curriculum along with required intermediate
     * exams certifications that must be taken by programmers in order to obtain
     * a new certification called 'master programmer'. In doing so, you are placing
     * prerequisites on intermediate exam certifications that must be taken first.
     * You are allowing the flexibility of taking the exams in any order as long as
     * any exam prerequisites are satisfied.
     * <p>
     * Unfortunately, in the past, your predecessors have accidentally published
     * curriculums and exam schedules that were not possible to complete due to cycles
     * in prerequisites. You want to avoid this embarrassment by making sure you define
     * a curriculum and exam schedule that can be completed.
     * <p>
     * You goal is to ensure that any student pursuing the certificate of 'master
     * programmer', can complete 'n' certification exams, each being specific to a
     * topic. Some exams have prerequisites of needing to take and pass earlier
     * certificate exams. You do not want to force any order of taking the exams, but
     * you want to make sure that at least one order is possible.
     * <p>
     * This method will save your embarrassment by returning true or false if
     * there is at least one order that can taken of exams.
     * <p>
     * You wrote this method, and in doing so, you represent these 'n' exams as
     * nodes in a graph, numbered from 0 to n-1. And you represent the prerequisite
     * between taking exams as directed edges between two nodes which represent
     * those two exams.
     * <p>
     * Your method expects a 2-dimensional array of exam prerequisites, where
     * prerequisites[i] = [ai, bi] indicating that you must take exam 'bi' first
     * if you want to take exam 'ai'. For example, the pair [0, 1], indicates that
     * to take exam certification '0', you have to first have the certification for
     * exam '1'.
     * <p>
     * The method will return true if you can finish all certification exams.
     * Otherwise, return false (e.g., meaning it is a cyclic or cycle graph).
     * <p>
     * Example 1:
     * Input: numExams = 2, prerequisites = [[1,0]]
     * Output: true
     * Explanation: There are a total of 2 exams to take.
     * To take exam 1 you should have completed the
     * certification of exam 0. So, it is possible (no
     * cyclic or cycle graph of prereqs).
     * <p>
     * <p>
     * Example 2:
     * Input: numExams = 2, prerequisites = [[1,0],[0,1]]
     * Output: false
     * Explanation: There are a total of 2 exams to take.
     * To take exam 1 you should have completed the
     * certification of exam 0, and to take exams 0 you
     * should also have completed the certification of exam
     * 1. So, it is impossible (it is a cycle graph).
     *
     * @param numExams      - number of exams, which will produce a graph of n nodes
     * @param prerequisites - 2-dim array of directed edges.
     * @return boolean          - True if all exams can be taken, else false.
     */

    public boolean canFinish(int numExams, int[][] prerequisites) {
        int[] prerequisitesCount = new int[numExams]; // track how many pre-reqs each exam has
        ArrayList<Integer>[] adj = new ArrayList[numExams];
        // initialize adj list
        for (int a = 0; a < numExams; a++) {
            adj[a] = new ArrayList<>();
        }
        for (int[] preReq : prerequisites) { // fill adjlist with pre req
            int exam = preReq[0]; // exam needs prereq
            int prereq = preReq[1]; // exam = preq
            adj[prereq].add(exam); // add exam to dependent exams list
            prerequisitesCount[exam]++; // increment prereq count
        }
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < numExams; i++) { // start by adding exams that dont need prereq
            if (prerequisitesCount[i] == 0) {
                queue.offer(i);
            }
        }
        int examsPassed = 0; //go through each exam in queue
        while (!queue.isEmpty()) {
            int exam = queue.poll();
            examsPassed++; // exam finished, increment count of finished exams
            for (int dExams : adj[exam]) {
                prerequisitesCount[dExams]--; // prereq done
                if (prerequisitesCount[dExams] == 0) {
                    queue.offer(dExams); // exam has no more prereq, add to queue

                }
            }
        }
    return examsPassed == numExams; // if all exams done return true otherwise false
    }

    /**
     * Method getAdjList
     *
     * Building an Adjacency List for the directed graph based on number of nodes
     * and passed in directed edges.
     *
     * @param numNodes      - number of nodes in graph (labeled 0 through n-1) for n nodes
     * @param edges         - 2-dim array of directed edges
     * @return ArrayList<Integer>[]  - An adjacency list representing the provided graph.
     */

    private ArrayList<Integer>[] getAdjList(int numNodes, int[][] edges) {

        ArrayList<Integer>[] adj 
                    = new ArrayList[numNodes];      // Create an array of ArrayList ADT

        for (int node = 0; node < numNodes; node++){
            adj[node] = new ArrayList<Integer>();   // Allocate empty ArrayList per node
        }
        for (int[] edge : edges){
            adj[edge[0]].add(edge[1]);              // Add connected node edge [1] for node [0]
        }
        return adj;
    }

    /*
     * Assignment Graphing - Number of groups.
     *
     * There are n people. Some of them are connected
     * as friends forming a group. If person 'a' is
     * connected to person 'b', and person 'b' is
     * connected to person 'c', they form a connected
     * group.
     *
     * Not all groups are interconnected, meaning there
     * can be 1 or more groups depending on how people
     * are connected.
     *
     * This example can be viewed as a graph problem,
     * where people are represented as nodes, and
     * edges between them represent people being
     * connected. In this problem, we are representing
     * this graph externally as an non-directed
     * Adjacency Matrix. And the graph itself may not
     * be fully connected, it can have 1 or more
     * non-connected compoents (subgraphs).
     *
     * Example 1:
     *   Input :
         AdjMatrix = [[0,1,0], [1,0,0], [0,0,0]]
     *   Output: 2
     *   Explanation: The Adjacency Matrix defines an
     *   undirected graph of 3 nodes (indexed 0 to 2).
     *   Where nodes 0 and 1 aee connected, and node 2
     *   is NOT connected. This forms two groups of
     *   nodes.
     *
     * Example 2:
     *   Input : AdjMatrix = [ [0,0,0], [0,0,0], [0,0,0]]
     *   Output: 3
     *   Explanation: The Adjacency Matrix defines an
     *   undirected graph of 3 nodes (indexed 0 to 2).
     *   There are no connected nodes, hence forming
     *   three groups.
     *
     * Example 3:
     *   Input : AdjMatrix = [[0,1,0], [1,0,0], [0,1,0]]
     *   Output: 1
     *   Explanation, The adjacency Matrix defined an
     *   undirected graph of 3 nodes (index 0 to 2).
     *   All three nodes are connected by at least one
     *   edge. So they form on large group.
     */

    public int numGroups(int[][] adjMatrix) {
        int numNodes = adjMatrix.length; // get number of nodes
        Map<Integer, List<Integer>> graph = new HashMap(); // create hashmap of graph
        boolean[] checked = new boolean[numNodes]; // track if node has been checked
        int numGroups = 0; // intialize groups of node to 0
        for (int a = 0; a < numNodes; a++) { // build graph from adjmatrix
            for (int b = 0; b < numNodes; b++) {
                if (adjMatrix[a][b] == 1 && a != b) { // check if a and b have similarity
                    graph.putIfAbsent(a, new ArrayList<>()); // add a if not already to graph
                    graph.putIfAbsent(b, new ArrayList<>()); // add b if not already to graph
                    graph.get(a).add(b); // add a as a neighbor node of b
                    graph.get(b).add(a); // add b as a neighbor node of a
                }
            }
        }
        for (int node = 0; node < numNodes; node++) { // loop through each node
            if (!checked[node]) { // if person has not checked to the node, new group found
                numGroups++;
                bfs(node, graph, checked); // check bfs
            }
        }
        return numGroups; // return total # of node groups
    }
    //helper method for bfs
    private void bfs(int firstNode, Map<Integer, List<Integer>> graph, boolean[] checked) {
        Queue<Integer> q = new LinkedList<>();
        q.offer(firstNode); // start bfs from first node
        checked[firstNode] = true; // mark as checked
        while (!q.isEmpty()) { //  iterate through all adj nodes, mark node as checked and add to queue
            int node = q.poll();
            for (int adjNodes : graph.getOrDefault(node, new ArrayList<>())) {
                if (!checked[adjNodes]) {
                    checked[adjNodes] = true;
                    q.offer(adjNodes);
                }
            }
        }
    }
}

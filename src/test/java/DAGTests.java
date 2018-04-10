import org.junit.Test;

/**
 * Created by karthik on 10/04/18.
 */
public class DAGTests {

    @Test
    public void testLevelOrderForDAG() {
        DAG<String, Integer> dag = new DAG<>();

        DAG.Vertex<String, Integer> karthik = new DAG.Vertex<>("Karthik", 1);
        DAG.Vertex<String, Integer> nivetha = new DAG.Vertex<>("Nivetha", 2);
        DAG.Vertex<String, Integer> prem = new DAG.Vertex<>("Prem", 3);
        DAG.Vertex<String, Integer> vivek = new DAG.Vertex<>("Vivek", 4);
        DAG.Vertex<String, Integer> sabrina = new DAG.Vertex<>("Sabrina", 5);
        DAG.Vertex<String, Integer> gautam = new DAG.Vertex<>("Gautam", 6);
        DAG.Vertex<String, Integer> somex = new DAG.Vertex<>("Some-X", 7);

        dag.addEdge(karthik, nivetha);
        dag.addEdge(karthik, prem);
        dag.addEdge(karthik, vivek);

        dag.addEdge(nivetha, karthik);
        dag.addEdge(nivetha, sabrina);

        dag.addEdge(prem, karthik);
        dag.addEdge(prem, nivetha);
        dag.addEdge(prem, somex);

        dag.addEdge(vivek, karthik);
        dag.addEdge(vivek, nivetha);

        dag.addEdge(sabrina, gautam);

        dag.addEdge(gautam, somex);


        /*dag.processLevelOrder(karthik, vertex -> System.out.print(vertex + "  "),
                level -> System.out.println("\nEnd of level :: " + level + "\n"));*/

        dag.processDepthFirst(karthik, vertex -> System.out.println(vertex));
    }
}

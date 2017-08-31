import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

/**
 * Created by Yixing Zhang on 5/24/17.
 */
public abstract class VectorModel implements IModel {
  protected DocScores docScores;
  protected Retriever retriever;
  protected int queryNumber;
  protected int totalDocNum;
  protected double avgLen;
  protected int limit = 100000;

  public VectorModel() {
    docScores = new DocScores();
    retriever = new RetrieverES();
    avgLen = retriever.getAvgLen();
    totalDocNum = retriever.getTotalDocNum();
  }

  @Override
  public void setQueryNumber(int number) {
    this.queryNumber = number;
    docScores.clear();
  }

  public void querySentence(String sentence) {
    System.out.println("Query number: " + queryNumber);
    Scanner sc = new Scanner(sentence);
    while (sc.hasNext()) {
      query(sc.next());
    }
    sc.close();
  }

  @Override
  public String getReport() {
    TreeMap<Double, String> treeMap = docScores.outputTreeMap();
    StringBuilder sb = new StringBuilder();
    int count = 1;
    for (Map.Entry<Double, String> entry : treeMap.entrySet()) {
      if (count > limit) {
        break;
      }
      String str = queryNumber + " Q0 " + entry.getValue() + " " +
              count + " " + entry.getKey() * -1 + " Exp\n";
      sb.append(str);
      count++;
    }
    return sb.toString();
  }

  public void closeRetriever() {
    retriever.close();
  }

}

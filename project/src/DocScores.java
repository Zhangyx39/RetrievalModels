import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;

/**
 * Created by Yixing Zhang on 5/22/17.
 */
public class DocScores {
  private HashMap<String, DocInfo> data;

  public DocScores() {
    this.data = new HashMap<>();
  }

  public Set<String> idSet() {
    return data.keySet();
  }

  public void putScore(String docno, double score) {
    if (!data.containsKey(docno)) {
      DocInfo doc = new DocInfo(docno);
      doc.setScore(score);
      data.put(docno, doc);
    }
    else {
      data.get(docno).addScore(score);
    }
  }

  public void putLen(String docno, int len) {
    if (!data.containsKey(docno)) {
      DocInfo doc = new DocInfo(docno);
      doc.setDocLen(len);
      data.put(docno, doc);
    }
    else {
      data.get(docno).setDocLen(len);
    }
  }

  public int getLen(String docno) {
    if (data.containsKey(docno)) {
      return data.get(docno).getDocLen();
    }
    else {
      return 0;
    }
  }

  public Set<String> getIDs() {
    return data.keySet();
  }

  @Override
  public String toString() {
    return data.toString();
  }

  public TreeMap<Double, String> outputTreeMap() {
    TreeMap<Double, String> treeMap = new TreeMap<>();
    for (String s : data.keySet()) {
      treeMap.put(data.get(s).getScore() * -1, s);
    }
    return treeMap;
  }

  public void clear() {
    data.clear();
  }

  public static void main(String[] args) {
    String str = "123 123456\n246 1234\n";
    Scanner sc = new Scanner(str);
    sc.next();
    System.out.println(sc.nextLine());
  }
}

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;

/**
 * Created by Yixing Zhang on 5/25/17.
 */
public abstract class LanguageModel implements IModel {
  protected DocScores docScores;
  protected Retriever retriever;
  protected int queryNumber;
  protected int totalDocNum;
  protected double avgLen;
  protected int vocSize;
  protected int totalDocLen;
  protected int limit = 100000;

  public LanguageModel() {
    docScores = new DocScores();
    retriever = new RetrieverES();
    avgLen = retriever.getAvgLen();
    totalDocNum = retriever.getTotalDocNum();
    vocSize = retriever.getVocSize();
    totalDocLen = 0;
  }

  @Override
  public void setQueryNumber(int number) {
    this.queryNumber = number;
    docScores.clear();
  }

  public void initDoc(String sentence) {
    JsonArray arr = retriever.getAllDoc(sentence);
    for (JsonElement e : arr) {
      String id = e.getAsJsonObject().get("_id").getAsString();
      int len = e.getAsJsonObject().getAsJsonObject("_source")
              .get("doclen").getAsInt();
      totalDocLen += len;
      docScores.putScore(id, 0);
      docScores.putLen(id, len);
    }
  }


  public void querySentence(String sentence) {
    System.out.println("Query number: " + queryNumber);
    initDoc(sentence);
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

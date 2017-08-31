import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import java.util.HashMap;

/**
 * Created by Yixing Zhang on 5/25/17.
 */
public class OkapiBM25 extends LanguageModel {
  double k1 = 1.2;
  double k2 = 0.5;
  double b = 0.75;

  public OkapiBM25() {
    super();
    System.out.println("Okapi-BM25");
  }

  public void query(String term) {
    JsonArray arr = retriever.getTermInfos(term);
    int df = retriever.getDF(term);
    System.out.println(term + ": " + arr.size());
    HashMap<String, Integer> tfs = new HashMap<>();
    for (JsonElement jsonElement : arr) {
      int tf = jsonElement.getAsJsonObject()
              .getAsJsonObject("fields")
              .get("tf")
              .getAsInt();
      String id = jsonElement.getAsJsonObject().get("_id").getAsString();
      tfs.put(id, tf);
    }

    for (String id : docScores.getIDs()) {
      int tf = 0;
      if (tfs.containsKey(id)) {
        tf = tfs.get(id);
      }
      int len = docScores.getLen(id);
      double score = okapiBM25(totalDocNum, df, tf, len, avgLen);
      docScores.putScore(id, score);
    }
  }

  public double okapiBM25(int tdn, int df, int tf, int len, double avgLen) {
    return termOne(tdn, df) * termTwo(tf, len, avgLen) * termThree(tf);
  }

  public double termOne(int tdn, int df) {
    return Math.log((tdn + 0.5) / (df + 0.5));
  }

  public double termTwo(int tf, int len, double avgLen) {
    return (tf + k1 * tf) / (tf + k1 * ((1 - b) + b * len / avgLen));
  }

  public double termThree(int tf) {
    return (tf + k2 * tf) / (tf + k2);
  }



  public static void main(String[] args) {
    OkapiBM25 bm25 = new OkapiBM25();
    bm25.initDoc("alleg measur corrupt govern jurisdict worldwid");
    bm25.query("alleg");
    bm25.closeRetriever();
  }
}

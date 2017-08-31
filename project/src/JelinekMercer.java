import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import java.util.HashMap;

/**
 * Created by Yixing Zhang on 5/26/17.
 */
public class JelinekMercer extends LanguageModel {
  private double lambda = 0.5;

  public JelinekMercer() {
    super();
    System.out.println("Jelinek-Mercer Smoothing");
  }

  public void query(String term) {
    JsonArray arr = retriever.getTermInfos(term);
    System.out.println(term + ": " + arr.size());
    int ttf = retriever.getTTF(term);
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
      double score = Math.log(jm(tf, len, ttf, totalDocLen));
      docScores.putScore(id, score);
    }
  }

  public double jm(double tf, double len, double ttf, double tdLen) {
    return lambda * tf / len + (1 - lambda) * ttf / tdLen;
  }

  public static void main(String[] args) {
    JelinekMercer jm = new JelinekMercer();
    jm.initDoc("alleg measur corrupt govern jurisdict worldwid");
    jm.query("alleg");
    jm.closeRetriever();
  }
}

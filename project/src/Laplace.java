import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import java.util.HashMap;

/**
 * Created by Yixing Zhang on 5/26/17.
 */
public class Laplace extends LanguageModel {
  public Laplace() {
    super();
    System.out.println("Laplace Smoothing");
  }

  public void query(String term) {
    JsonArray arr = retriever.getTermInfos(term);
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
      double score = Math.log(laplace(tf, len, vocSize));
      //System.out.println(id + ": " + score);
      docScores.putScore(id, score);
    }
  }

  public double laplace(double tf, double len, double vocSize) {
    return (tf + 1) / (len + vocSize);
  }

  public static void main(String[] args) {
    Laplace laplace = new Laplace();
    laplace.initDoc("alleg measur corrupt govern jurisdict worldwid");
    laplace.query("alleg");
    laplace.closeRetriever();

  }
}

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

/**
 * Created by Yixing Zhang on 5/24/17.
 */
public class TFDIF extends VectorModel {

  public TFDIF() {
    super();
    System.out.println("TF-DIF");
  }

  public void query(String term) {
    JsonArray arr = retriever.getTermInfos(term);
    System.out.println(term + ": " + arr.size());
    int df = retriever.getDF(term);
    for (JsonElement jsonElement : arr) {
      int tf = jsonElement.getAsJsonObject()
              .getAsJsonObject("fields")
              .get("tf")
              .getAsInt();

      String id = jsonElement.getAsJsonObject().get("_id").getAsString();
      int len = retriever.getDocLen(id);
      double score = tf_dif(df, tf, len);
      docScores.putScore(id, score);
    }
  }

  public double okapi_tf(int tf, int len) {
    return tf / (tf + 0.5 + 1.5 * len / avgLen);
  }

  public double tf_dif(int df, int tf, int len) {
    return okapi_tf(tf, len) * Math.log(totalDocNum / df);
  }

  public static void main(String[] args) {
    TFDIF tfdif = new TFDIF();
    tfdif.setQueryNumber(0);
    tfdif.querySentence("controversi standard perform determin salari level incent pai contrast determin pai sole basi senior longev job");
    //System.out.println(tfdif.getReport());
    tfdif.closeRetriever();
  }
}

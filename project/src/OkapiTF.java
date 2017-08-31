import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

/**
 * Created by Yixing Zhang on 5/22/17.
 */
public class OkapiTF extends VectorModel {

  public OkapiTF() {
    super();
    System.out.println("Okapi-TF");
  }

  public void query(String term) {
    JsonArray arr = retriever.getTermInfos(term);
    System.out.println(term + ": " + arr.size());
    for (JsonElement jsonElement : arr) {
      int tf = jsonElement.getAsJsonObject()
              .getAsJsonObject("fields")
              .get("tf")
              .getAsInt();
      String id = jsonElement.getAsJsonObject().get("_id").getAsString();
      int len = retriever.getDocLen(id);
      double score = okapi_tf(tf, len);
      docScores.putScore(id, score);
    }
  }

  public double okapi_tf(int tf, int len) {
    return tf / (tf + 0.5 + 1.5 * len / avgLen);
  }


  public static void main(String[] args) {
    OkapiTF okapiTF = new OkapiTF();
    okapiTF.query("algorithm");
//    okapiTF.setQueryNumber(85);
    okapiTF.querySentence("militari coup etat countri");
//    System.out.println(okapiTF.getReport());
    okapiTF.closeRetriever();
  }
}

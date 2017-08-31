import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;


/**
 * Created by Yixing Zhang on 5/22/17.
 */
public class TermVector {
  private String docno;
  private JsonObject termVector;
  private double len;
  private double avgLen;

  public TermVector(String docno, JsonObject termVector) {
    this.docno = docno;
    this.termVector = termVector;
  }

  public int getTF(String term) {
    JsonObject object =  termVector.getAsJsonObject("term_vectors")
            .getAsJsonObject("text")
            .getAsJsonObject("terms")
            .getAsJsonObject(term);
    if (object == null) {
      return 0;
    }
    else {
      return object.get("term_freq").getAsInt();
    }
  }

  public double getTermScore(String term) {
    int tf = getTF(term);
    double score = tf / (tf + 0.5 + 1.5 * len / avgLen);
    return score;
  }


  public static void main(String[] args) throws IOException {
    Scanner sc = new Scanner(new File("test.json"));
    StringBuilder sb = new StringBuilder();
    while (sc.hasNextLine()) {
      sb.append(sc.nextLine());
    }
    String json = sb.toString();
    //System.out.println(json);

    JsonParser jsonParser = new JsonParser();
    JsonObject jsonObject = jsonParser.parse(json).getAsJsonObject();

    TermVector tv = new TermVector("123", jsonObject);
    //System.out.println(tv.getTermInfos("associ"));

    System.out.println(jsonObject.getAsJsonObject("term_vectors")
            .getAsJsonObject("text")
            .getAsJsonObject("terms"));
  }
}

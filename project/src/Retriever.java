import com.google.gson.JsonArray;

/**
 * Created by Yixing Zhang on 6/9/17.
 */
public interface Retriever {

  JsonArray getTermInfos(String term);

  void close();

  int getDF(String term);

  int getTTF(String term);

  int getDocLen(String doc);

  int getVocSize();

  double getAvgLen();

  int getTotalDocNum();

  JsonArray getAllDoc(String sentence);
}

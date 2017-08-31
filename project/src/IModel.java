/**
 * Created by Yixing Zhang on 5/24/17.
 */
public interface IModel {

  void setQueryNumber(int number);

  void querySentence(String sentence);

  void query(String str);

  void closeRetriever();

  String getReport();
}

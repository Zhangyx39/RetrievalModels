/**
 * Created by Yixing Zhang on 5/26/17.
 */
public class DocInfo {
  private String docno;
  private double score;
  private int docLen;

  public DocInfo(String docno) {
    this.docno = docno;
    score = 0;
    docLen = 0;
  }

  public void addScore(double score) {
    this.score += score;
  }

  public double getScore() {
    return score;
  }

  public int getDocLen() {
    return docLen;
  }

  public void setScore(double score) {
    this.score = score;
  }

  public void setDocLen(int docLen) {
    this.docLen = docLen;
  }
}

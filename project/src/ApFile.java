import com.google.gson.annotations.SerializedName;

/**
 * Created by Yixing Zhang on 5/21/17.
 */
public class ApFile {
  @SerializedName("docno")
  private String docno;
  @SerializedName("doclen")
  private int doclen;
  @SerializedName("text")
  private String text;

  public ApFile(String docno, int doclen, String text) {
    this.docno = docno;
    this.doclen = doclen;
    this.text = text;
  }

  public String getDocno() {
    return docno;
  }

  public String getText() {
    return text;
  }

  public void setDocno(String docno) {
    this.docno = docno;
  }

  public void setText(String text) {
    this.text = text;
  }
}

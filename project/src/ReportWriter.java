import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * Created by Yixing Zhang on 5/23/17.
 */
public class ReportWriter {
  IModel model;
  private String input;
  private String output;

  public ReportWriter(IModel model, String input, String output) {
    this.model = model;
    this.input = input;
    this.output = output;
  }

  public void close() {
    model.closeRetriever();
  }

  public String query(int num, String query) {
    model.setQueryNumber(num);
    model.querySentence(query);
    String str = model.getReport();
    return str;
  }

  public void writeReport() {
    StringBuilder sb = new StringBuilder();
    try {
      Scanner sc = new Scanner(new File(input));
      while (sc.hasNextLine()) {
        int num = sc.nextInt();
        String query = sc.nextLine();
        sb.append(query(num, query));
        FileUtils.writeStringToFile(new File(output),
                sb.toString(),
                "UTF-8");
      }
      sc.close();
      model.closeRetriever();
    }
    catch (IOException e) {
      System.out.println(e.getMessage());
    }
  }

  public static void main(String[] args) {
//    ReportWriter reportWriter = new ReportWriter(new TFDIF(),
//            "query_modified.txt",
//            "ft_dif_output.txt");
//
//    ReportWriter reportWriter = new ReportWriter(new OkapiTF(),
//            "query_modified.txt",
//            "okapi_test.txt");
//    reportWriter.writeReport();


    ReportWriter reportWriter = new ReportWriter(new TFDIF(),
            "query/query_stemmed.txt",
            "/Users/tommy/Dropbox/CS6200_Yixing_Zhang/homework6" +
                    "/features/tfidf.txt");
    reportWriter.writeReport();
    reportWriter.close();
  }
}

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * Created by Yixing Zhang on 5/22/17.
 */
public class ModifyQuery {
  public static void main(String[] args) throws IOException {
    Scanner sc = new Scanner(new File("/Users/tommy/CS/CS6200/AP_DATA/query_desc.51-100.short.txt"));
    StringBuilder sb = new StringBuilder();
    while (sc.hasNextLine()) {
      String str = sc.nextLine();
      System.out.println(str);
      str = str.replaceAll("[^a-zA-Z 0-9]", "").toLowerCase();
      System.out.println(str);
      sb.append(str);
      sb.append("\n");
      FileUtils.writeStringToFile(new File("query_modified.txt"),sb.toString(), "UTF-8");
    }
  }
}

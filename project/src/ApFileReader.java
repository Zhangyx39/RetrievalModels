import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;
import java.io.IOException;

/**
 * Created by Yixing Zhang on 5/21/17.
 */
public class ApFileReader {


  public static void main(String[] args) throws IOException {

    String filePath = "/Users/tommy/CS/CS6200/AP_DATA/ap89_collection";
    File file = new File(filePath);
    String[] filelist = file.list();
    for (int i = 0; i < filelist.length; i++) {
      System.out.println(filelist[i]);
      if (filelist[i].substring(0, 2).equals("ap")) {
        String fileName = filePath + "/" + filelist[i];
        //System.out.println(fileName);
        Document doc = Jsoup.parse(new File(fileName), "UTF-8");
        Gson gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();

        for (Element e : doc.getElementsByTag("doc")) {
          String docno = e.getElementsByTag("docno").text();
          String text = e.getElementsByTag("text").text();
          int doclen = text.split(" ").length;
          ApFile apFile = new ApFile(docno, doclen,text);
          String jsonStr = gson.toJson(apFile);
          FileUtils.writeStringToFile(new File
                          ("/Users/tommy/CS/CS6200/json2/" + docno + ".json"), jsonStr,
                  "UTF-8");
        }
      }
    }
  }
}

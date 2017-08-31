import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.entity.ContentType;
import org.apache.http.nio.entity.NStringEntity;
import org.elasticsearch.client.RestClient;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Yixing Zhang on 5/21/17.
 */
public class Parser {

  public static void main(String[] args) throws IOException, InterruptedException {
    RestClient restClient = RestClient.builder(
            new HttpHost("localhost", 9200, "http")).build();


    String filePath = "/Users/tommy/CS/CS6200/json2";
    Scanner sc = new Scanner(new File("fileList.txt"));
    List<String> jsons = new ArrayList<>();
    List<String> docnos = new ArrayList<>();
    while (sc.hasNextLine()) {
      String docno = sc.nextLine();
      docnos.add(docno);
      String fileName = filePath + "/" + docno + ".json";
      File file = new File(fileName);
      Scanner scanner = new Scanner(file);
      String str = new String();
      while (scanner.hasNextLine()) {
        str += scanner.nextLine();
      }
      jsons.add(str);
      scanner.close();
    }

    for (int i = 0; i < docnos.size(); i++) {
      System.out.println(docnos.get(i));
      HttpEntity httpEntity = new NStringEntity(jsons.get(i),
              ContentType.APPLICATION_JSON);
      restClient.performRequest("PUT",
              "/ap_dataset/document/" + docnos.get(i),
              Collections.emptyMap(),
              httpEntity);
    }

//    int numRequests = filelist.length;
//    final CountDownLatch latch = new CountDownLatch(numRequests);
//
//
//
//
//    for (int i = 0; i < numRequests; i++) {
//
//      String fileName = filePath + "/" + filelist[i];
//
//      Reader reader = new InputStreamReader(new FileInputStream(fileName),
//              "UTF-8");
//      int c = 0;
//      StringBuilder sb = new StringBuilder();
//      while((c = reader.read())!=-1) {
//        sb.append((char)c);
//      }
//      reader.close();
//      String json = sb.toString();
//      HttpEntity httpEntity = new NStringEntity(json, ContentType.APPLICATION_JSON);
//
//
//
//      restClient.performRequestAsync(
//              "PUT",
//              "/ap_dataset/document/" + filelist[i].substring(0,
//                      filelist[i].length() - 5),
//              Collections.<String, String>emptyMap(),
//              //assume that the documents are stored in an entities array
//              httpEntity,
//              new ResponseListener() {
//                @Override
//                public void onSuccess(Response response) {
//                  latch.countDown();
//                }
//
//                @Override
//                public void onFailure(Exception exception) {
//                  latch.countDown();
//                }
//              }
//      );
//    }
//
////wait for all requests to be completed
    //latch.await();
    restClient.close();
  }
}

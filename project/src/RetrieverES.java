import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.entity.ContentType;
import org.apache.http.nio.entity.NStringEntity;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;

import java.io.IOException;
import java.util.Collections;
import java.util.Scanner;

/**
 * Created by Yixing Zhang on 5/22/17.
 */
public class RetrieverES implements Retriever {
  RestClient restClient;
  JsonParser jsonParser;

  public RetrieverES() {
    this.restClient = RestClient.builder(
            new HttpHost("localhost", 9200, "http"))
            .build();
    jsonParser = new JsonParser();
  }

  public int getVocSize() {
    try {
      String body = "{\n" +
              "  \"size\": 0, \n" +
              "  \"aggs\": {\n" +
              "    \"vocabSize\": {\n" +
              "      \"cardinality\": {\n" +
              "        \"field\": \"text\"\n" +
              "      }\n" +
              "    }\n" +
              "  }\n" +
              "}";
      HttpEntity httpEntity = new NStringEntity(body,
              ContentType.APPLICATION_JSON);
      Response response = restClient.performRequest("GET",
              "ap_dataset/document/_search",
              Collections.emptyMap(),
              httpEntity);
      Scanner sc = new Scanner(response.getEntity().getContent());
      String json = sc.nextLine();
      sc.close();
      JsonObject jsonObject = jsonParser.parse(json).getAsJsonObject();
      return jsonObject
              .getAsJsonObject("aggregations")
              .getAsJsonObject("vocabSize")
              .get("value")
              .getAsInt();
    }
    catch (IOException e) {
      System.out.println(e.getMessage());
    }
    return 0;
  }

  public JsonArray getAllDoc(String sentence) {
    try {
      String body = "{\n" +
              "  \"_source\": \"doclen\", \n" +
              "  \"query\": {\n" +
              "    \"match\": {\n" +
              "      \"text\": \"" + sentence + "\"\n" +
              "    }\n" +
              "  }\n" +
              "}";
      HttpEntity httpEntity = new NStringEntity(body,
              ContentType.APPLICATION_JSON);
      Response response = restClient.performRequest("GET",
              "/ap_dataset/document/_search?size=100000",
              Collections.emptyMap(),
              httpEntity);
      Scanner sc = new Scanner(response.getEntity().getContent());
      String json = sc.nextLine();
      sc.close();
      JsonObject jsonObject = jsonParser.parse(json).getAsJsonObject();
      return jsonObject.getAsJsonObject("hits").getAsJsonArray("hits");
    }
    catch (IOException e) {
      System.out.println(e.getMessage());
    }
    return null;
  }

  public int getDF(String term) {
    try {
      String body = "{\n" +
              "  \"query\": {\n" +
              "    \"term\": {\n" +
              "      \"text\": {\n" +
              "        \"value\": \"" + term + "\"\n" +
              "      }\n" +
              "    }\n" +
              "  },\n" +
              "  \"script_fields\": {\n" +
              "    \"ttf\": {\n" +
              "      \"script\": {\n" +
              "        \"lang\":   \"groovy\",\n" +
              "        \"inline\": \"_index['text'][/" + term + "/].ttf()\"\n" +
              "      }\n" +
              "    }\n" +
              "  }\n" +
              "}";
      HttpEntity httpEntity = new NStringEntity(body,
              ContentType.APPLICATION_JSON);
      Response response = restClient.performRequest("GET",
              "/ap_dataset/document/_search?size=1",
              Collections.emptyMap(),
              httpEntity);
      Scanner sc = new Scanner(response.getEntity().getContent());
      String json = sc.nextLine();
      sc.close();
      JsonObject jsonObject = jsonParser.parse(json).getAsJsonObject();
      return jsonObject.getAsJsonObject("hits").get("total").getAsInt();
    }
    catch (IOException e) {
      System.out.println(e.getMessage());
    }
    return 0;
  }

  public int getTTF(String term) {
    try {
      String body = "{\n" +
              "  \"query\": {\n" +
              "    \"term\": {\n" +
              "      \"text\": {\n" +
              "        \"value\": \"" + term + "\"\n" +
              "      }\n" +
              "    }\n" +
              "  },\n" +
              "  \"script_fields\": {\n" +
              "    \"ttf\": {\n" +
              "      \"script\": {\n" +
              "        \"lang\":   \"groovy\",\n" +
              "        \"inline\": \"_index['text'][/" + term + "/].ttf()" +
              "\"\n" +
              "      }\n" +
              "    }\n" +
              "  }\n" +
              "}";
      HttpEntity httpEntity = new NStringEntity(body,
              ContentType.APPLICATION_JSON);
      Response response = restClient.performRequest("GET",
              "/ap_dataset/document/_search?size=1",
              Collections.emptyMap(),
              httpEntity);
      Scanner sc = new Scanner(response.getEntity().getContent());
      String json = sc.nextLine();
      sc.close();
      JsonObject jsonObject = jsonParser.parse(json).getAsJsonObject();

      if (jsonObject
              .getAsJsonObject("hits")
              .getAsJsonArray("hits")
              .size() == 0) {
        return 0;
      }

      return jsonObject
              .getAsJsonObject("hits")
              .getAsJsonArray("hits")
              .get(0)
              .getAsJsonObject()
              .getAsJsonObject("fields")
              .getAsJsonArray("ttf")
              .get(0)
              .getAsInt();
    }
    catch (IOException e) {
      System.out.println(e.getMessage());
    }
    return 0;
  }

  public JsonArray getTermInfos(String term) {
    try {
      String body = "{\n" +
              "  \"query\": {\n" +
              "    \"term\": {\n" +
              "      \"text\": {\n" +
              "        \"value\": \"" + term + "\"\n" +
              "      }\n" +
              "    }\n" +
              "  },\n" +
              "  \"script_fields\": {\n" +
              "    \"tf\": {\n" +
              "      \"script\": {\n" +
              "        \"lang\":   \"groovy\",\n" +
              "        \"inline\": \"_index['text'][/" + term + "/].tf()\"\n" +
              "      }\n" +
              "    }\n" +
              "  }\n" +
              "}";
      HttpEntity httpEntity = new NStringEntity(body,
              ContentType.APPLICATION_JSON);
      Response response = restClient.performRequest("GET",
              "/ap_dataset/document/_search?size=100000",
              Collections.emptyMap(),
              httpEntity);
      Scanner sc = new Scanner(response.getEntity().getContent());
      String json = sc.nextLine();
      sc.close();
      JsonObject jsonObject = jsonParser.parse(json).getAsJsonObject();
      return jsonObject.getAsJsonObject("hits").getAsJsonArray("hits");
    }
    catch (IOException e) {
      System.out.println(e.getMessage());
    }
    return null;
  }


  public double getAvgLen() {
    try {
      String body = "{\n" +
              "  \"size\": 0, \n" +
              "  \"aggs\": {\n" +
              "    \"ave_doc_len\": {\n" +
              "      \"avg\": {\n" +
              "        \"field\": \"doclen\"\n" +
              "      }\n" +
              "    }\n" +
              "  }\n" +
              "}";
      HttpEntity httpEntity = new NStringEntity(body,
              ContentType.APPLICATION_JSON);
      Response response = restClient.performRequest("GET",
              "/ap_dataset/document/_search",
              Collections.emptyMap(),
              httpEntity);
      Scanner sc = new Scanner(response.getEntity().getContent());
      String json = sc.nextLine();
      sc.close();
      JsonObject jsonObject = jsonParser.parse(json).getAsJsonObject();
      return jsonObject
              .getAsJsonObject("aggregations")
              .getAsJsonObject("ave_doc_len")
              .get("value")
              .getAsDouble();
    }
    catch (IOException e) {
      System.out.println(e.getMessage());
    }
    return 0;
  }

  public double getAvgLen2() {
    try {
      Response response = restClient.performRequest("GET",
              "/ap_dataset/document/AP890101-0001/_termvectors",
              Collections.emptyMap());
      Scanner sc = new Scanner(response.getEntity().getContent());
      String json = sc.nextLine();
      sc.close();
      JsonObject jsonObject = jsonParser.parse(json).getAsJsonObject();
      JsonObject stats = jsonObject
              .getAsJsonObject("term_vectors")
              .getAsJsonObject("text")
              .getAsJsonObject("field_statistics");
      double avg = stats.get("sum_ttf").getAsDouble()
              / stats.get("doc_count").getAsDouble();
      return avg;
    }
    catch (IOException e) {
      System.out.println(e.getMessage());
    }
    return 0;
  }

  public int getDocLen(String docno) {
    try {
      Response response = restClient.performRequest("GET",
              "/ap_dataset/document/" + docno + "/_source",
              Collections.emptyMap());
      Scanner sc = new Scanner(response.getEntity().getContent());
      while (sc.hasNext()) {
        if (sc.next().equals("\"doclen\":")) {
          String len = sc.next();
          len = len.replace(",", "");
          return Integer.parseInt(len);
        }
      }
    }
    catch (IOException e) {
      System.out.println(e.getMessage());
    }
    return 0;
  }

  public int getTotalDocNum() {
    try {
      Response response = restClient.performRequest("GET",
              "/ap_dataset/document/_search",
              Collections.emptyMap());
      Scanner sc = new Scanner(response.getEntity().getContent());
      String json = sc.nextLine();
      sc.close();
      JsonObject jsonObject = jsonParser.parse(json).getAsJsonObject();
      return jsonObject
              .getAsJsonObject("hits")
              .get("total").getAsInt();
    }
    catch (IOException e) {
      System.out.println(e.getMessage());
    }
    return 0;
  }



  public void close() {
    try {
      restClient.close();
    }
    catch (IOException e) {
      System.out.println(e.getMessage());
    }
  }

  public static void main(String[] args) {
    Retriever retriever = new RetrieverES();
    //System.out.println(retriever.getDocLen("AP890222-0257"));
    //System.out.println(retriever.getAvgLen2());
//    //System.out.println(retriever.getTotalDocNum());
//    System.out.println(retriever.getTermInfos("algorithm")
//            .get(0).getAsJsonObject().getAsJsonObject("fields").get("tf").getAsInt());
//    //System.out.println(retriever.getDF("apple"));
//    System.out.println(retriever.getTTF("appl"));
//    String sen = "allegations measures corrupt governmental jurisdiction " +
//            "worldwide";
//    System.out.println(retriever.getAllDoc(sen).get(0).getAsJsonObject().get
//            ("_id"));
    System.out.println(retriever.getTermInfos("employe"));
    retriever.close();
  }
}

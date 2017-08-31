# RetrievalModels
Implemented five retrieval models using elasticsearch and compared their performances.

### Dataset
This project use trec_ap89 corpus that can be found from [TREC](http://trec.nist.gov/data.html).

### Document Indexing
  * Use **Jsoup** to read the HTML like formated files from the dataset.
  * Parse them to **json** format by [google-gson](https://github.com/google/gson).
  * Use HTTP client to upload json files to a local [ElasticSearch](https://www.elastic.co/products/elasticsearch) index.

### Query Execution
Implement document ranking by retrieving information such as `TF` and `DF` from the local ElasticSearch index in five retrieval models:
  * Vector space models: `Okapi-TF` and `TF-IDF`.
  * Language models: `Okapi BM25`, Unigram LM with `Laplace` smoothing and Unigram LM with `Jelinek-Mercer` smoothing.
  
### Results
  * For each query, generate top 1000 documents by the ranking models and same them as result files.
  * Run evaluation in command line for each result file and get the following results.
  ```
  trec_eval [-q] qrel_file results_file
  ```


|Model          |Average Precision|At 10 docs|At 30 docs|
| ------------- | --------------- |----------|----------|
| Okapi TF      | 0.1828          | 0.4000   | 0.2693   |
| TF-IDF        | 0.2547          | 0.3720   | 0.3253   |
| Okapi BM25    | 0.2631          | 0.4160   | 0.3307   |
| Laplace       | 0.1749          | 0.3560   | 0.2720   |
| Jelinek-Mercer| 0.1984          | 0.3120   | 0.2627   |

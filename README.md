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
  * For each [query](query/query_origin.txt), generate top 1000 documents by the ranking models and same them as [result files](result_stemmed).
  * Run evaluation in command line for each result file using [trec_eval](trec_eval.txt) and [qrel_file](qrels.adhoc.51-100.AP89.txt) to get the following results.
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


### License

This projext is under the [MIT license](LICENSE).

```
Copyright (c) 2017 Yixing Zhang

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```

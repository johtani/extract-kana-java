# フリガナ抽出アプリ

引数として与えられたテキスト（もしくは改行区切りのファイル）のフリガナをいくつかの形態素解析器を使って出力します。

## 概要

テキストを入力に与えると以下の形態素解析器を利用して読み仮名を取得して出力するアプリです。
現在対応している形態素解析器は以下の通りです。

* Kuromoji IPADic Neologd([Elasticsearch's Analyzer for Kuromoji with Neologd](https://github.com/codelibs/elasticsearch-analysis-kuromoji-ipadic-neologd)で利用されているlucene-analyzers-kuromoji-ipadic-neologdを利用)
* [Sudachi](https://github.com/WorksApplications/Sudachi)

## 事前準備

* ビルドにはJava、Gradleが必要
* リポジトリをローカルにクローン
* Sudachiのcore辞書を[ここ](http://sudachi.s3-website-ap-northeast-1.amazonaws.com/sudachidict/)からダウンロード
  * ダウンロードしたzipファイルより、`system_core.dic`をプロジェクト直下にコピー

## ビルド

Gradleを利用してビルドします。

```shell
cd extract-kana-java
./gradlew build
```

## 使用方法

#### テキスト入力

```shell
cd extract-kana-java
./furigana.sh 東京タワー
```

出力
```csv
東京タワー,kuromoji_neologd,トウキョウタワー,sudachi,トウキョウ タワー
```

#### ファイル入力

```shell
./furigana.sh -m=FILE ファイル名
```

#### ヘルプ出力

```shell
./furigana.sh -h
```

## TODO(以下、未実装)

* NDJSONフォーマットによる出力
* ファイル出力オプション

## ライセンス

Apache License 2.0
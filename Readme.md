# AWS Lambda Template

Java で AWS Lambda を作成、デプロイするためのプロジェクトテンプレートです。


# 使い方

## プロジェクト名の変更

1. ディレクトリ名を変更する
2. settings.gradle の rootProject.name を修正し、プロジェクト名を変更する

## 新しい関数プロジェクトを追加

1. hello サブプロジェクトのコピーを作る
2. settings.gradle の includes に新しく作ったプロジェクトを追加する

## Lambdaの関数コードを作る

Java でガリガリ作る。

## デプロイの準備

1. AWSにアクセスするためのアクセストークンを作る
2. 必要に応じて、サブプロジェクト内のbuild.gradle を修正する

## デプロイ、アップデート

1. gradlew migrateFunction で AWS に 関数（モジュール）が登録される。デフォルトで関数名は、{rootProject名}-{subProject名}になる
2. 既に関数が登録されていた場合、Javaのクラスファイルがアップデートされる

## アンデプロイ

gradlew deleteFunction で AWS から関数（モジュール）が削除される


# 注意事項

* Logger は、debug と error しか使えないです。

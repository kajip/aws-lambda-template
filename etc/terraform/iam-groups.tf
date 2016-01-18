/*
 * Groups
 */

// 開発者
resource "aws_iam_group" "developers" {
    name = "SekisanDevelopers"
}

// 開発リーダー
resource "aws_iam_group" "leaders" {
    name = "SekisanLeaders"
}

// マネージャ
resource "aws_iam_group" "managers" {
    name = "SekisanManagers"
}

// オールマイティ。障害時など一時的に全権限を付与するために使う
resource "aws_iam_group" "president" {
    name = "SekisanPresident"
}

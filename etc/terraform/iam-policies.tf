/*
 * Policies
 */

// ReadOnly
resource "aws_iam_policy" "read_only" {
    name = "SekisanReadOnly"
    description = "特定サービスのReadOnlyアクセス"
    policy = "${file("policies/ReadOnly.json")}"
}

// LambdaInvoke
resource "aws_iam_policy" "lambda_invoke" {
    name = "SekisanLambdaInvoke"
    description = "登録済みのLambdaの実行権限"
    policy = "${file("policies/LambdaInvoke.json")}"
}

// DeveloperIAM
resource "aws_iam_policy" "developer_iam" {
    name = "SekisanDeveloperIAM"
    description = "自分のパスワード変更、トークン操作"
    policy = "${file("policies/DeveloperIAM.json")}"
}

// AdministratorIAM
resource "aws_iam_policy" "administrator_iam" {
    name = "SekisanAdministratorIAM"
    description = "ユーザ、グループ操作権限。ただし、ロールとポリシー操作を除く"
    policy = "${file("policies/AdministratorIAM.json")}"
}

// Billing
resource "aws_iam_policy" "billing" {
    name = "SekisanBilling"
    description = "課金情報アクセス"
    policy = "${file("policies/Billing.json")}"
}

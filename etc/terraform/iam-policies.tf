/* Policies */
 
// ReadOnly
resource "aws_iam_policy" "read_only" {
    name = "SekisanReadOnly"
    description = "特定サービスのReadOnlyアクセス"
    policy = "${file("policies/ReadOnly.json")}"
}
resource "aws_iam_policy_attachment" "read_only" {
    name = "ReadOnlyAttachment"
    groups = [
        "${aws_iam_group.developers.name}",
        "${aws_iam_group.leaders.name}",
        "${aws_iam_group.managers.name}"
    ]
    policy_arn = "${aws_iam_policy.read_only.arn}"
}


// LambdaInvoke
resource "aws_iam_policy" "lambda_invoke" {
    name = "SekisanLambdaInvoke"
    description = "登録済みのLambdaの実行権限"
    policy = "${file("policies/LambdaInvoke.json")}"
}
resource "aws_iam_policy_attachment" "lambda_invoke" {
    name = "LambdaInvokeAttachment"
    groups = [
        "${aws_iam_group.developers.name}",
        "${aws_iam_group.leaders.name}"
    ]
    policy_arn = "${aws_iam_policy.lambda_invoke.arn}"
}


// DeveloperIAM
resource "aws_iam_policy" "developer_iam" {
    name = "SekisanDeveloperIAM"
    description = "自分のパスワード変更、トークン操作"
    policy = "${file("policies/DeveloperIAM.json")}"
}
resource "aws_iam_policy_attachment" "developer_iam" {
    name = "DeveloperIAMAttachment"
    groups = [
        "${aws_iam_group.developers.name}"
    ]
    policy_arn = "${aws_iam_policy.developer_iam.arn}"
}


// AdministratorIAM
resource "aws_iam_policy" "administrator_iam" {
    name = "SekisanAdministratorIAM"
    description = "ユーザ、グループ操作権限。ただし、ロールとポリシー操作を除く"
    policy = "${file("policies/AdministratorIAM.json")}"
}
resource "aws_iam_policy_attachment" "administrator_iam" {
    name = "AdministratorIAMAttachment"
    groups = [
        "${aws_iam_group.leaders.name}",
        "${aws_iam_group.managers.name}"
    ]
    policy_arn = "${aws_iam_policy.administrator_iam.arn}"
}


// Billing
resource "aws_iam_policy" "billing" {
    name = "SekisanBilling"
    description = "課金情報アクセス"
    policy = "${file("policies/Billing.json")}"
}
resource "aws_iam_policy_attachment" "billing" {
    name = "BillingAttachment"
    groups = [
        "${aws_iam_group.leaders.name}",
        "${aws_iam_group.managers.name}"
    ]
    policy_arn = "${aws_iam_policy.billing.arn}"
}


// デフォルトで用意された AdministratorAccess を付与
resource "aws_iam_policy_attachment" "administrator_access" {
    name = "AdministratorAccessAttachment"
    groups = ["${aws_iam_group.president.name}"]
    policy_arn = "arn:aws:iam::aws:policy/AdministratorAccess"
}

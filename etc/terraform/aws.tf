# AWS Provider Settings

provider "aws" {
    region = "ap-northeast-1"
}

# System Variables

## システム名
variable "system_name" {}
## 環境
variable "vpc_environment" {
    default = "Development"
}

#! /bin/bash
# Description：本地测试结果打包并自动推送到在线服务

cd /Users/mayer/Project/IdeaProjects/Milestone/anturo-auto/anturo-center-order-e2e-bdd-automation/target
rm -rf ./builds
mkdir -p ./builds/qa/automation/apis/anturo-center-order-e2e-bdd-automation/target/site
if [ -d "./site/serenity" ]
then
    cp -r ./site/serenity ./builds/qa/automation/apis/anturo-center-order-e2e-bdd-automation/target/site
else
    echo "serenity文件夹当前不存在！"
    exit 1
fi
tar -czvf bdd-automation-report-order.tar.gz ./builds/qa/automation/apis/anturo-center-order-e2e-bdd-automation/target/site/serenity
curl --location --request POST 'http://10.6.0.116:8070/qa/restful/upload' \
--header 'accept: application/json' \
--header 'Authorization: Basic cWE6MTIzNDU2' \
--form 'filename=@"/Users/mayer/Project/IdeaProjects/Milestone/anturo-auto/anturo-center-order-e2e-bdd-automation/target/bdd-automation-report-order.tar.gz"' \
--form 'path="/automation/hammler-gitlab-ci/order-center/bdd"'
echo $?
echo -e "\n【Success】推送自动化测试结果成功！"
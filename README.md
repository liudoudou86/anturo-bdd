### 简介

> **测试工程：** BDD自动化测试

> **测试类型：** BDD场景自动化测试

***

### 特点

* **开发语言**：*Java8*
* **开发工具**：*IntelliJ IDEA*
* **开发框架**：[Hammler-BDD](http://idp-gitlab.anturo.com/qa/automation/frameworks/hammler-bdd)
* **测试相关**：
    * 测试用例：*Gherkin*
    * 测试框架：*Cucumber*、*Serenity*
    * 测试工具：*RestAssured*
    * 测试断言：*Hamcrest、AssertJ*
    * 测试报告：*Serenity*、*Allure*
    * 虚拟服务：*WireMock*、*HoverFly*
    * 测试单元：*JUnit*、*TestNG*
    * 代码质量：*Jacoco*、*SonarQube*
    * 埋点监控：*Prometheus*、*Grafana*
* **作业流程**：*Maven插件*
* **执行方式**：*Maven命令*
* **持续集成**：*Jenkins*、*GitLab CI*

***

### 基于Maven插件的自动化测试作业流

* **Maven三方插件**
    * *build-helper-maven-plugin*：测试资源目录收集
    * *maven-surefile-plugin*：测试代码单元测试
    * *jacoco-maven-plugin*：测试代码覆盖率度量
    * *sonar-maven-plugin*：测试代码质量扫描
    * *maven-failsafe-plugin*：E2E/BDD自动化测试执行
    * *allure-maven*：自动化测试报告
    * *serenity-maven-plugin*：BDD自动化聚合及报告

* **Maven作业触发**

```shell
mvn clean post-integration-test -P e2e-bdd 开启BDD自动化测试（默认）
mvn clean post-integration-test -P e2e-api 开启API自动化测试
```

* **Profile环境切换**

💡通过更新ApplicationConfig.yaml配置文件中如下：

```yaml
bdd:
  profile:
    active: uat
```

* **DevOps**：请按如下样例更新被测项目的.gitlab-ci.yml

  ```yaml
  stages:
    - e2e-bdd-regression
    ...
  e2e-bdd-regression-test:
    stage: e2e-bdd-regression
    when: on_success
    script:
      - curl -X POST -F token=自动化项目token -F ref=master -F variables[COMMIT_PROJECT_NAME]=${CI_PROJECT_NAME} -F variables[COMMIT_REF]=${CI_COMMIT_REF_NAME} -F variables[COMMIT_SHA]=${CI_COMMIT_SHA} -F variables[COMMIT_TITLE]=${CI_COMMIT_TITLE} -F variables[COMMIT_USER]=${GITLAB_USER_EMAIL} -F variables[COMMIT_TIME]=${CI_COMMIT_TIMESTAMP} http://idp-gitlab.anturo.com/api/v4/projects/{自动化项目ID}/trigger/pipeline
      - echo "E2e-Bdd-Regression-Test triggered by ${CI_PROJECT_NAME}-${CI_PROJECT_ID}-${CI_COMMIT_REF_NAME}-${CI_COMMIT_SHA}"
    only:
      refs:
        - 触发分支名称
    allow_failure: true
    cache: {}
  ```

***

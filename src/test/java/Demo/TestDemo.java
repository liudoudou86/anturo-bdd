package Demo;

import org.testng.annotations.Test;
// 官方要求静态导入
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

/**
 * @author Tesla Liu
 * @date 2022/10/13 09:18
 * 描述 测试用例样例
 */

public class TestDemo {

    /**
     * Groovy风格写法，推荐使用这种方法
     * 测试响应码
     */
    @Test
    public void testStatusCode() {
        given()
                .get("https://jsonplaceholder.typicode.com/posts/3")
                .then()
                .statusCode(200)
                .log().status();
    }

    /**
     * 断言body结果
     */
    @Test
    public void testBody() {
        given()
                .get("https://jsonplaceholder.typicode.com/posts/3")
                .then()
                .body("id",equalTo(3),"userId",equalTo(1))
                .log().body();
    }

    /**
     * 带param的请求
     * */
    @Test
    public void testParam() {
        given()
                .param("userId", 2)
                .when()
                .get("https://jsonplaceholder.typicode.com/posts")
                .then()
                .statusCode(200)
                .log().status();
    }

    /**
     * 携带多个param的请求
     * */
    @Test
    public void testWithParam() {
        // 构造一个多参数的map对象
        Map<String, String> parameter = new HashMap<>();
        parameter.put("userId", "2");
        parameter.put("id", "14");

        given()
                .params(parameter)
                .when()
                .log().all()
                .get("https://jsonplaceholder.typicode.com/posts")
                .then()
                .statusCode(200)
                .log().status();
    }
}

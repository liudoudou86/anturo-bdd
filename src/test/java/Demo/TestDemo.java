package Demo;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.Test;
// 官方要求静态导入
import static io.restassured.RestAssured.*;

/**
 * @author Tesla Liu
 * @date 2022/10/13 09:18
 * 描述 测试用例样例
 */

public class TestDemo {

    /**
     * Groovy风格写法，推荐使用这种方法
     */
    @Test
    public void testStatusCode() {
        given().
                get("https://www.baidu.com").
                then().
                statusCode(200);
    }

    /**
     * Java原本风格写法
     */
    @Test
    public void testStatusCodeJavaStyle() {
        //1. 创建一个RestAssured对象
        RestAssured ra = new RestAssured();
        //2. 创建一个请求对象
        RequestSpecification rs = ra.given();
        //3. 发送请求，拿到响应对象
        Response res = rs.get("https://www.baidu.com");
        //4. 判断响应状态码是不是200
        assert res.getStatusCode() == 200;
    }
}

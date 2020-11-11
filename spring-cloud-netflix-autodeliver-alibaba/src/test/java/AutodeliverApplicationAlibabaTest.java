import com.study.netflix.AutodeliverApplicationAlibaba;
import com.study.netflix.client.ResumeFeignClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AutodeliverApplicationAlibaba.class)
public class AutodeliverApplicationAlibabaTest {

    @Autowired
    private ResumeFeignClient resumeFeignClient;

    @Autowired
    private RestTemplate restTemplate;

    @Test
    public void testFeignClient() {
        Integer resumeOpenState =
                resumeFeignClient.findResumeOpenState(1545132l);
        System.out.println("=======>>>resumeOpenState： " + resumeOpenState);
    }

    @Test
    public void testRestTemplete() {
        Integer forObject = restTemplate.getForObject("http://netflix-service-resume/resume/openstate/111", Integer.class);
        System.out.println("通过nacos获取实例任何请求得到的简历状态" + forObject);
    }
}

import com.study.netflix.AutodeliverApplication;
import com.study.netflix.client.ResumeFeignClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AutodeliverApplication.class)
public class AutodeliverApplicationTest {

    @Autowired
    private ResumeFeignClient resumeFeignClient;

    @Test
    public void testFeignClient() {
        Integer resumeOpenState =
                resumeFeignClient.findResumeOpenState(1545132l);
        System.out.println("=======>>>resumeOpenState： " + resumeOpenState);
    }
}

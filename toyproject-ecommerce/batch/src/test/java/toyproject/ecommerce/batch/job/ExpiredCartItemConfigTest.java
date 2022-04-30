package toyproject.ecommerce.batch.job;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import toyproject.ecommerce.core.repository.CartItemRepository;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("core")
@TestPropertySource(properties = {"job.name=expiredCartItemJob"})
public class ExpiredCartItemConfigTest {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private CartItemRepository cartProductRepository;

    @Test
    public void test() throws Exception {
        JobExecution jobExecution = jobLauncherTestUtils.launchJob();

        assertThat(BatchStatus.COMPLETED).isEqualTo(jobExecution.getStatus());
        assertThat(0).isEqualTo(cartProductRepository.findByModifiedDateBefore(LocalDateTime.now().minusDays(1)).size());
    }
}
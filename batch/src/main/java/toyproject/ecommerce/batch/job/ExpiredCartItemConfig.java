package toyproject.ecommerce.batch.job;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import toyproject.ecommerce.core.domain.CartItem;
import toyproject.ecommerce.core.repository.CartItemRepository;

import javax.persistence.EntityManagerFactory;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Configuration
public class ExpiredCartItemConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory entityManagerFactory;
    private final CartItemRepository cartItemRepository;
    private final JobLauncher jobLauncher;

    private final static int CHUNK_SIZE = 15;

    @Scheduled(cron = "${expired.cart.item.batch.cron}")
    public void run() throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis())
                .toJobParameters();

        jobLauncher.run(expiredCartItemJob(), jobParameters);
    }

    @Bean
    public Job expiredCartItemJob() {
        return jobBuilderFactory.get("expiredCartItemJob")
                .incrementer(new RunIdIncrementer())
                .start(expiredCartItemJobStep())
                .build();
    }

    @Bean
    public Step expiredCartItemJobStep() {
        return stepBuilderFactory.get("expiredCartItemStep")
                .<CartItem, CartItem> chunk(CHUNK_SIZE)
                .reader(expiredCartItemReader())
                .writer(expiredCartItemWriter())
                .build();
    }

    @Bean(destroyMethod = "")
    @StepScope
    public JpaPagingItemReader<CartItem> expiredCartItemReader() {
        JpaPagingItemReader<CartItem> jpaPagingItemReader = new JpaPagingItemReader<CartItem>() {
            @Override
            public int getPage() {
                return 0;
            }
        };

        jpaPagingItemReader.setQueryString("SELECT ci FROM CartItem ci WHERE ci.modifiedDate < :modifiedDate ORDER BY ci.id");

        Map<String, Object> map = new HashMap<>();
        LocalDateTime now = LocalDateTime.now();
        map.put("modifiedDate", now.minusHours(1));

        jpaPagingItemReader.setEntityManagerFactory(entityManagerFactory);
        jpaPagingItemReader.setParameterValues(map);
        jpaPagingItemReader.setPageSize(CHUNK_SIZE);

        return jpaPagingItemReader;
    }

    private ItemWriter<CartItem> expiredCartItemWriter() {
        return cartItemRepository::deleteAll;
    }

}

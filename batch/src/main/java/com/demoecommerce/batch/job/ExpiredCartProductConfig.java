package com.demoecommerce.batch.job;

import com.demoecommerce.batch.config.SimpleIncrementer;
import com.demoecommerce.domain.entity.CartProduct;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import javax.persistence.EntityManagerFactory;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Configuration
public class ExpiredCartProductConfig {

    private final JobBuilderFactory jobBuilderFactory;

    private final StepBuilderFactory stepBuilderFactory;

    private final SimpleIncrementer simpleIncrementer;

    private final EntityManagerFactory entityManagerFactory;

    private final JobLauncher jobLauncher;

    private final static int CHUNK_SIZE = 15;

    @Scheduled(cron = "${expired.cart.product.batch.cron}")
    public void run() throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("time",System.currentTimeMillis())
                .toJobParameters();

        jobLauncher.run(expiredCartProductJob(), jobParameters);
    }

    @Bean
    public Job expiredCartProductJob() {
        return jobBuilderFactory.get("expiredCartProductJob")
                .incrementer(simpleIncrementer)
                .start(expiredCartProductJobStep())
                .build();
    }

    @Bean
    public Step expiredCartProductJobStep() {
        return stepBuilderFactory.get("expiredCartProductStep")
                .<CartProduct, CartProduct> chunk(10)
                .reader(expiredCartProductReader())
                .writer(expiredCartProductWriter())
                .build();
    }

    @Bean(destroyMethod = "")
    @StepScope
    public JpaPagingItemReader<CartProduct> expiredCartProductReader() {
        JpaPagingItemReader<CartProduct> jpaPagingItemReader = new JpaPagingItemReader<CartProduct>() {
            @Override
            public int getPage() {
                return 0;
            }
        };

        jpaPagingItemReader.setQueryString("SELECT cp FROM CartProduct cp WHERE cp.updatedDate < :updatedDate ORDER BY cp.cartProductId");

        Map<String, Object> map = new HashMap<>();
        LocalDateTime now = LocalDateTime.now();
        map.put("updatedDate", now.minusDays(1));

        jpaPagingItemReader.setEntityManagerFactory(entityManagerFactory);
        jpaPagingItemReader.setParameterValues(map);
        jpaPagingItemReader.setPageSize(CHUNK_SIZE);

        return jpaPagingItemReader;
    }

    private JpaItemWriter<CartProduct> expiredCartProductWriter() {
        JpaItemWriter<CartProduct> jpaItemWriter = new JpaItemWriter<>();
        jpaItemWriter.setEntityManagerFactory(entityManagerFactory);
        return jpaItemWriter;
    }

}

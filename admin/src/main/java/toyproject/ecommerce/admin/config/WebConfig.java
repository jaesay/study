package toyproject.ecommerce.admin.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import toyproject.ecommerce.core.domain.entity.Member;
import toyproject.ecommerce.core.domain.enums.Role;
import toyproject.ecommerce.core.repository.MemberRepository;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${cloud.aws.credentials.accessKey}")
    private String accessKey;

    @Value("${cloud.aws.credentials.secretKey}")
    private String secretKey;

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public BasicAWSCredentials awsCredentials() {
        return new BasicAWSCredentials(accessKey, secretKey);
    }

    @Bean
    public AmazonS3 amazonS3Client() {
        return AmazonS3ClientBuilder.standard()
                .withRegion(Regions.AP_NORTHEAST_2)
                .withCredentials(new AWSStaticCredentialsProvider(this.awsCredentials()))
                .build();
    }

    @Bean
    public ApplicationRunner applicationRunner() {
        return new ApplicationRunner() {
            @Autowired MemberRepository memberRepository;
            @Autowired PasswordEncoder passwordEncoder;

            @Override
            public void run(ApplicationArguments args) throws Exception {
                String email = "admin@test.com";

                Member member1 = memberRepository.findByEmail(email)
                        .orElse(Member.builder()
                                .email(email)
                                .password(passwordEncoder.encode("1111"))
                                .name("admin")
                                .role(Role.ADMIN)
                                .build());

                if (member1.getId() == null) {
                    memberRepository.save(member1);
                }
            }
        };
    }
}

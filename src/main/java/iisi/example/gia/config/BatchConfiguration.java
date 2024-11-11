package iisi.example.gia.config;

import iisi.example.gia.entity.EmpDO;
import iisi.example.gia.task.EmpItemProcessor;
import iisi.example.gia.task.EmpItemWriter;
import iisi.example.gia.vo.EmpVO;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {


    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

    @Bean
    public FlatFileItemReader<EmpVO> empReader() {
        return new FlatFileItemReaderBuilder<EmpVO>()
                .name("personItemReader")
                .resource(new ClassPathResource("sample-data.csv"))
                .delimited()
                .names("ename", "job", "hiredate", "sal", "comm", "deptno")
                .targetType(EmpVO.class)
                .build();
    }

    @Bean
    public EmpItemProcessor empItemProcessor() {
        return new EmpItemProcessor();
    }

    @Bean
    public EmpItemWriter empItemWriter() {
        return new EmpItemWriter();
    }

    @Bean
    public Step importEmpJobStep1(JobRepository jobRepository, PlatformTransactionManager transactionManager,
                                  FlatFileItemReader<EmpVO> empReader, EmpItemProcessor empItemProcessor, EmpItemWriter empItemWriter) {
        return new StepBuilder("importEmpJobStep1", jobRepository)
                .<EmpVO, EmpDO>chunk(3, transactionManager)
                .reader(empReader)
                .processor(empItemProcessor)
                .writer(empItemWriter)
                .build();
    }


    @Bean
    public Job importUserJob(JobRepository jobRepository, Step importEmpJobStep1) {
        return new JobBuilder("importEmpJob", jobRepository)
                .start(importEmpJobStep1)
                .build();
    }


}

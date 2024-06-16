package batchprocessing;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
public class BatchConfiguration {
	private static final Logger log = LoggerFactory.getLogger(BatchConfiguration.class);

	@Bean
	public DataSource dataSource() {
		HikariConfig hikariConfig = new HikariConfig();
		String jdbcProto = "jdbc:oracle:thin:@(DESCRIPTION=(CONNECT_DATA=(SERVICE_NAME=ORCLPDB1))(ADDRESS=(PROTOCOL=tcp)(HOST=192.168.86.41)(PORT=1521)))";
		hikariConfig.setJdbcUrl(jdbcProto);
		hikariConfig.setUsername("hr");
		hikariConfig.setPassword("f0rever");
		hikariConfig.setDriverClassName("oracle.jdbc.OracleDriver");
		return new HikariDataSource(hikariConfig);
	}
	@Bean
	public JdbcCursorItemReader<Employee> reader(DataSource dataSource) {
		log.info("Reader entered.");
		JdbcCursorItemReader<Employee> reader = new JdbcCursorItemReader<>();
		reader.setDataSource(dataSource);
		reader.setSql("SELECT EMPLOYEE_ID, FIRST_NAME, LAST_NAME, EMAIL, PHONE_NUMBER, HIRE_DATE, JOB_ID, SALARY, COMMISSION_PCT, MANAGER_ID, DEPARTMENT_ID FROM EMPLOYEES");
		reader.setRowMapper(new EmployeeRowMapper());
		return reader;
	}

	@Bean
	public ItemProcessor<Employee, Employee> processor() {
		log.info("Processor entered.");
		return item -> {
			// Process item (if needed)
			long id = item.getEmployeeId();
			log.info("processor: {}",id);
			return item;
		};
	}

	@Bean
	public ItemWriter<Employee> writer() {
		log.info("Writer entered.");
		return items -> {
			for (Employee item : items) {
				// Write item to the destination
				//long id = item.getEmployeeId();
				//System.out.println(id);
				log.info("{} {}", item.getFirstName(), item.getLastName());
			}
		};
	}



	// tag::readerwriterprocessor[]
/*	@Bean
	public FlatFileItemReader<Person> reader() {
		return new FlatFileItemReaderBuilder<Person>()
			.name("personItemReader")
			.resource(new ClassPathResource("sample-data.csv"))
			.delimited()
			.names("firstName", "lastName")
			.targetType(Person.class)
			.build();
	}*/

/*	@Bean
	public PersonItemProcessor processor() {
		return new PersonItemProcessor();
	}

	@Bean
	public JdbcBatchItemWriter<Person> writer(DataSource dataSource) {
		return new JdbcBatchItemWriterBuilder<Person>()
			.sql("INSERT INTO people (first_name, last_name) VALUES (:firstName, :lastName)")
			.dataSource(dataSource)
			.beanMapped()
			.build();
	}*/
	// end::readerwriterprocessor[]

	// tag::jobstep[]
	@Bean
	public Job importUserJob(JobRepository jobRepository,Step step1, JobCompletionNotificationListener listener) {
		return new JobBuilder("importUserJob", jobRepository)
			.listener(listener)
			.start(step1)
			.build();
	}

	@Bean
	public Step step1(JobRepository jobRepository, DataSourceTransactionManager transactionManager,
					  JdbcCursorItemReader<Employee> reader, ItemProcessor<Employee, Employee> processor, ItemWriter<Employee> writer) {
		return new StepBuilder("step1", jobRepository)
			.<Employee, Employee> chunk(10, transactionManager)
			.reader(reader)
			.processor(processor)
			.writer(writer)
			.allowStartIfComplete(true)
				.build();
	}
	// end::jobstep[]
}

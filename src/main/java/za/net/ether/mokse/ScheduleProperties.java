// package za.net.ether.mokse;

// import java.util.ArrayList;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
// import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
// import org.springframework.boot.context.properties.ConfigurationProperties;
// import org.springframework.boot.context.properties.EnableConfigurationProperties;
// import org.springframework.context.annotation.Bean;

// @ConfigurationProperties(prefix = "schedule")
// public class ScheduleProperties {
//     private int code;
//     private int seed;
//     private int count;
//     private int inc;
//     private int X = 0;
//     private ArrayList<ArrayList<TimeStage>> schedule;

//     @Configuration
//     @ConditionalOnClass(Schedule.class)
//     @EnableConfigurationProperties(ScheduleProperties.class)
//     public class ScheduleAutoConfiguration {

//         @Autowired
//         private ScheduleProperties scheduleProperties;

//         @Bean
//         @ConditionalOnMissingBean
//         public ScheduleConfig scheduleConfig() {

//         }

//         @Bean
//         @ConditionalOnMissingBean
//         public Schedule schedule(ScheduleConfig scheduleConfig) {
//             return new Schedule(scheduleConfig);
//         }
//     }

// }

package com.locker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.locker.dao")
public class LockersApplication {

    private ApplicationContext ctx;

    public static void main(String[] args) {
        //ctx kan gebruikt worden om bijvoorbeeld alle beans weer te geven voor debuggen.
        ApplicationContext ctx =  SpringApplication.run(LockersApplication.class, args);
    }
}


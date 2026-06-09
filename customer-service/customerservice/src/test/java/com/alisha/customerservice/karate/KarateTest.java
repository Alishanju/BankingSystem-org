package com.alisha.customerservice.karate;

import com.intuit.karate.junit5.Karate;

public class KarateTest {

    @Karate.Test
    Karate testAll() {
        return Karate.run(
                "classpath:karate/login.feature",
                "classpath:karate/customer.feature");
    }
}
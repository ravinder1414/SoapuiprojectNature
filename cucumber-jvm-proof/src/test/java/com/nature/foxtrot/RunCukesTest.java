package com.nature.foxtrot;

import org.junit.runner.RunWith;

import cucumber.junit.Cucumber;


@RunWith(Cucumber.class)
@Cucumber.Options(format ={"pretty", "html:target/cucumber","json:target/cucumber.json"})

public class RunCukesTest {
}

//package com.nature.foxtrot;
//
//import org.junit.runner.RunWith;
//import cucumber.junit.Cucumber;
//
//@RunWith(Cucumber.class)
//@Cucumber.Options(format={"junit:target/cuke-report/report.xml"})
//public class RunCukesTest {
//}
package com.nature.cukes;

import org.junit.runner.RunWith;
import cucumber.junit.Cucumber;

@RunWith(Cucumber.class)
@Cucumber.Options(format={"junit:target/cuke-report/report.xml"})
public class RunCukesTest {
}
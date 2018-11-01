package com.airwellex.demo;

import com.airwellex.demo.utils.RestfulClientFactory;
import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

/**
 * Created by houjiagang on 2018/10/30.
 */
@RunWith(DataProviderRunner.class)
//@ContextConfiguration(value = {"/test-datasource.xml"})
//@TestExecutionListeners(TransactionDbUnitTestExecutionListener.class)
public class BaseTestCase {

    protected static final String CONTENT_TYPE = MediaType.APPLICATION_JSON + "; charset=UTF-8";

    protected final WebTarget target(final String path) {
        return RestfulClientFactory.getClient().target("http://preview.airwallex.com:30001/").path(path);

    }


    @Test
    public void test() {

    }


}

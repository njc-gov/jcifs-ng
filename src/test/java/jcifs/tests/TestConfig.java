/**
 * © 2016 AgNO3 Gmbh & Co. KG
 * All right reserved.
 * 
 * Created: 18.01.2016 by mbechler
 */
package jcifs.tests;


import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.junit.Assume;


/**
 * @author mbechler
 *
 */
public class TestConfig {

    private static final Logger log = Logger.getLogger(TestConfig.class);
    private static Properties PROPERTIES = new Properties();


    static {
        String propFile = System.getProperty("jcifs.test.properties");
        if ( propFile != null ) {
            try ( FileInputStream fis = new FileInputStream(propFile) ) {
                PROPERTIES.load(fis);
            }
            catch ( IOException e ) {
                log.error("Failed to load test properties " + propFile, e);
            }
        }
        PROPERTIES.putAll(System.getProperties());
    }


    public static Properties getProperties () {

        return PROPERTIES;
    }


    /**
     * @return
     */
    public static String getTestServer () {
        String testServer = (String) getProperties().get("test.server");
        Assume.assumeNotNull(testServer);
        return testServer;
    }


    public static String getTestUserDomain () {
        String testDomain = (String) getProperties().get("test.user.domain");
        Assume.assumeNotNull(testDomain);
        return testDomain;
    }


    public static String getTestUserPassword () {
        String testPassword = (String) getProperties().get("test.user.password");
        Assume.assumeNotNull(testPassword);
        return testPassword;
    }


    public static String getTestUser () {
        String testUser = (String) getProperties().get("test.user.name");
        Assume.assumeNotNull(testUser);
        return testUser;
    }

}
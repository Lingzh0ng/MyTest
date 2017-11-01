package com.wearapay.mylocation;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
  @Test public void addition_isCorrect() throws Exception {
    assertEquals(4, 2 + 2);
    String date = "2017-06-13T07:19:02.311Z";
    date = date.replace("Z", " UTC");
    SimpleDateFormat format = new SimpleDateFormat("yyy-MM-dd'T'HH:mm:ss.SSS Z");
    Date d = format.parse(date);
    System.out.println(new SimpleDateFormat("yyy-MM-dd HH:mm:ss").format(d));
  }
}
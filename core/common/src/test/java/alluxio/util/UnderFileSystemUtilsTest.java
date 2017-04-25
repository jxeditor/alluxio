/*
 * The Alluxio Open Foundation licenses this work under the Apache License, version 2.0
 * (the "License"). You may not use this work except in compliance with the License, which is
 * available at www.apache.org/licenses/LICENSE-2.0
 *
 * This software is distributed on an "AS IS" basis, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied, as more fully set forth in the License.
 *
 * See the NOTICE file distributed with this work for information regarding copyright ownership.
 */

package alluxio.util;

import alluxio.ConfigurationRule;
import alluxio.PropertyKey;

import com.google.common.collect.Maps;
import org.junit.Assert;
import org.junit.Test;

import java.io.Closeable;
import java.util.Map;

public final class UnderFileSystemUtilsTest {

  /**
   * Tests the {@link UnderFileSystemUtils#isObjectStorage(String)} method.
   */
  @Test
  public void isUfsObjectStorage() throws Exception {
    Assert.assertEquals(true, UnderFileSystemUtils.isObjectStorage("s3://bucket/"));
    Assert.assertEquals(true, UnderFileSystemUtils.isObjectStorage("s3n://bucket"));
    Assert.assertEquals(true, UnderFileSystemUtils.isObjectStorage("s3a://bucket/"));
    Assert.assertEquals(true, UnderFileSystemUtils.isObjectStorage("gs://bucket/"));
    Assert.assertEquals(true, UnderFileSystemUtils.isObjectStorage("swift://bucket/"));
    Assert.assertEquals(true, UnderFileSystemUtils.isObjectStorage("oss://bucket/"));
    Assert.assertEquals(false, UnderFileSystemUtils.isObjectStorage("hdfs://dir/"));
    Assert.assertEquals(false, UnderFileSystemUtils.isObjectStorage("/dir/"));
    Assert.assertEquals(false, UnderFileSystemUtils.isObjectStorage("/"));
    Assert.assertEquals(false, UnderFileSystemUtils.isObjectStorage(""));
  }

  @Test
  public void getValue() throws Exception {
    try (Closeable c = new ConfigurationRule(PropertyKey.S3A_ACCESS_KEY, "bar").toResource()) {
      Map<String, String> conf = Maps.newHashMap();
      Assert.assertEquals("bar", UnderFileSystemUtils.getValue(PropertyKey.S3A_ACCESS_KEY, conf));
      conf.put(PropertyKey.S3A_ACCESS_KEY.toString(), "foo");
      Assert.assertEquals("foo", UnderFileSystemUtils.getValue(PropertyKey.S3A_ACCESS_KEY, conf));
    }
  }

  @Test
  public void contains() throws Exception {
    Map<String, String> conf = Maps.newHashMap();
    Assert.assertFalse(UnderFileSystemUtils.containsKey(PropertyKey.S3A_ACCESS_KEY, conf));
    conf.put(PropertyKey.S3A_ACCESS_KEY.toString(), "foo");
    Assert.assertTrue(UnderFileSystemUtils.containsKey(PropertyKey.S3A_ACCESS_KEY, conf));
  }
}

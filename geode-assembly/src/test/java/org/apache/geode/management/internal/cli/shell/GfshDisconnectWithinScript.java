/*
 * Licensed to the Apache Software Foundation (ASF) under one or more contributor license
 * agreements. See the NOTICE file distributed with this work for additional information regarding
 * copyright ownership. The ASF licenses this file to You under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the License. You may obtain a
 * copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package org.apache.geode.management.internal.cli.shell;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import org.apache.geode.test.junit.categories.AcceptanceTest;
import org.apache.geode.test.junit.rules.gfsh.GfshRule;

@Category(AcceptanceTest.class)
public class GfshDisconnectWithinScript {

  @Rule
  public GfshRule gfsh = new GfshRule();


  @Before
  public void setup() {
    gfsh.execute("start locator");
  }

  @Test
  public void disconnectInScriptDoesNotRaiseNPE() {
    gfsh.execute("connect", "disconnect", "echo \"Disconnect command resolved without issue.\"");
  }
}

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

package org.apache.geode.internal.protocol;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.geode.annotations.Experimental;
import org.apache.geode.cache.IncompatibleVersionException;
import org.apache.geode.distributed.internal.InternalLocator;
import org.apache.geode.internal.cache.tier.sockets.ClientProtocolProcessor;
import org.apache.geode.internal.cache.tier.sockets.MessageExecutionContext;
import org.apache.geode.internal.protocol.protobuf.ProtobufStreamProcessor;
import org.apache.geode.internal.protocol.protobuf.statistics.ProtobufClientStatistics;

@Experimental
public final class ProtobufLocatorPipeline implements ClientProtocolProcessor {
  private final ProtobufClientStatistics statistics;
  private final InternalLocator locator;
  private final ProtobufStreamProcessor streamProcessor;

  ProtobufLocatorPipeline(ProtobufStreamProcessor protobufStreamProcessor,
      ProtobufClientStatistics statistics, InternalLocator locator) {
    this.streamProcessor = protobufStreamProcessor;
    this.statistics = statistics;
    this.locator = locator;
    this.statistics.clientConnected();
  }

  @Override
  public void processMessage(InputStream inputStream, OutputStream outputStream)
      throws IOException, IncompatibleVersionException {
    streamProcessor.receiveMessage(inputStream, outputStream,
        new MessageExecutionContext(locator, statistics));
  }

  @Override
  public void close() {
    this.statistics.clientDisconnected();
  }
}

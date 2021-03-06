/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.trino.plugin.http;

import com.google.inject.Injector;
import io.airlift.bootstrap.Bootstrap;
import io.airlift.json.JsonModule;
import io.trino.plugin.base.TypeDeserializerModule;
import io.trino.spi.connector.ConnectorContext;

import java.util.Map;

import static java.util.Objects.requireNonNull;

public class ConnectorFactory
        implements io.trino.spi.connector.ConnectorFactory
{
    @Override
    public String getName()
    {
        return "example-http";
    }

    @Override
    public io.trino.spi.connector.Connector create(String catalogName, Map<String, String> requiredConfig, ConnectorContext context)
    {
        requireNonNull(requiredConfig, "requiredConfig is null");

        // A plugin is not required to use Guice; it is just very convenient
        Bootstrap app = new Bootstrap(
                new JsonModule(),
                new TypeDeserializerModule(context.getTypeManager()),
                new Module());

        Injector injector = app
                .doNotInitializeLogging()
                .setRequiredConfigurationProperties(requiredConfig)
                .initialize();

        return injector.getInstance(Connector.class);
    }
}

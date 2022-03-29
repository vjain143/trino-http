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

import com.google.common.collect.ImmutableList;
import io.trino.spi.connector.ConnectorRecordSetProvider;
import io.trino.spi.connector.ConnectorSession;
import io.trino.spi.connector.ConnectorSplit;
import io.trino.spi.connector.ConnectorTableHandle;
import io.trino.spi.connector.ConnectorTransactionHandle;

import java.util.List;

public class RecordSetProvider
        implements ConnectorRecordSetProvider
{
    @Override
    public io.trino.spi.connector.RecordSet getRecordSet(ConnectorTransactionHandle transaction, ConnectorSession session, ConnectorSplit split, ConnectorTableHandle table, List<? extends io.trino.spi.connector.ColumnHandle> columns)
    {
        Split exampleSplit = (Split) split;

        ImmutableList.Builder<ColumnHandle> handles = ImmutableList.builder();
        for (io.trino.spi.connector.ColumnHandle handle : columns) {
            handles.add((ColumnHandle) handle);
        }

        return new RecordSet(exampleSplit, handles.build());
    }
}

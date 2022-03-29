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
import io.trino.spi.connector.ColumnMetadata;
import org.testng.annotations.Test;

import java.net.URI;

import static io.trino.plugin.http.MetadataUtil.TABLE_CODEC;
import static io.trino.spi.type.BigintType.BIGINT;
import static io.trino.spi.type.VarcharType.createUnboundedVarcharType;
import static org.testng.Assert.assertEquals;

public class TestTable
{
    private final Table exampleTable = new Table("tableName",
            ImmutableList.of(new Column("a", createUnboundedVarcharType()), new Column("b", BIGINT)),
            ImmutableList.of(URI.create("file://table-1.json"), URI.create("file://table-2.json")));

    @Test
    public void testColumnMetadata()
    {
        assertEquals(exampleTable.getColumnsMetadata(), ImmutableList.of(
                new ColumnMetadata("a", createUnboundedVarcharType()),
                new ColumnMetadata("b", BIGINT)));
    }

    @Test
    public void testRoundTrip()
    {
        String json = TABLE_CODEC.toJson(exampleTable);
        Table exampleTableCopy = TABLE_CODEC.fromJson(json);

        assertEquals(exampleTableCopy.getName(), exampleTable.getName());
        assertEquals(exampleTableCopy.getColumns(), exampleTable.getColumns());
        assertEquals(exampleTableCopy.getSources(), exampleTable.getSources());
    }
}
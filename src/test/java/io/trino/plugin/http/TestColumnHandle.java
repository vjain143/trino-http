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

import io.airlift.testing.EquivalenceTester;
import org.testng.annotations.Test;

import static io.trino.plugin.http.MetadataUtil.COLUMN_CODEC;
import static io.trino.spi.type.BigintType.BIGINT;
import static io.trino.spi.type.VarcharType.createUnboundedVarcharType;
import static org.testng.Assert.assertEquals;

public class TestColumnHandle
{
    private final ColumnHandle columnHandle = new ColumnHandle("columnName", createUnboundedVarcharType(), 0);

    @Test
    public void testJsonRoundTrip()
    {
        String json = COLUMN_CODEC.toJson(columnHandle);
        ColumnHandle copy = COLUMN_CODEC.fromJson(json);
        assertEquals(copy, columnHandle);
    }

    @Test
    public void testEquivalence()
    {
        EquivalenceTester.equivalenceTester()
                .addEquivalentGroup(
                        new ColumnHandle("columnName", createUnboundedVarcharType(), 0),
                        new ColumnHandle("columnName", BIGINT, 0),
                        new ColumnHandle("columnName", createUnboundedVarcharType(), 1))
                .addEquivalentGroup(
                        new ColumnHandle("columnNameX", createUnboundedVarcharType(), 0),
                        new ColumnHandle("columnNameX", BIGINT, 0),
                        new ColumnHandle("columnNameX", createUnboundedVarcharType(), 1))
                .check();
    }
}

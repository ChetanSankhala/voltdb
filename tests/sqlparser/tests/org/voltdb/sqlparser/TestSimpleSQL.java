/* This file is part of VoltDB.
 * Copyright (C) 2008-2017 VoltDB Inc.
 *
 * This file contains original code and/or modifications of original code.
 * Any modifications made by VoltDB Inc. are licensed under the following
 * terms and conditions:
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS BE LIABLE FOR ANY CLAIM, DAMAGES OR
 * OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
 * ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 */
/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with this
 * work for additional information regarding copyright ownership. The ASF
 * licenses this file to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 *//* This file is part of VoltDB.
 * Copyright (C) 2008-2017 VoltDB Inc.
 *
 * This file contains original code and/or modifications of original code.
 * Any modifications made by VoltDB Inc. are licensed under the following
 * terms and conditions:
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS BE LIABLE FOR ANY CLAIM, DAMAGES OR
 * OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
 * ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 */
/*
 * This file has been created by elves.  If you make changes to it,
 * the elves will become annoyed, will overwrite your changes with
 * whatever odd notions they have of what should
 * be here, and ignore your plaintive bleatings.  So, don't edit this file,
 * Unless you want your work to disappear.
 */
package org.voltdb.sqlparser;

import org.junit.Test;

import org.hsqldb_voltpatches.VoltXMLElement;
import org.hsqldb_voltpatches.HSQLInterface;
import org.voltdb.sqlparser.syntax.SQLKind;

import org.voltdb.sqlparser.assertions.semantics.VoltXMLElementAssert.IDTable;
import static org.voltdb.sqlparser.assertions.semantics.VoltXMLElementAssert.*;
import org.voltdb.planner.ParameterizationInfo;

public class TestSimpleSQL {
    HSQLInterface m_HSQLInterface = null;
    String        m_schema = null;
    public TestSimpleSQL() {
        m_HSQLInterface = HSQLInterface.loadHsqldb(ParameterizationInfo.getParamStateManager());
        String m_schema = "create table alpha ( id integer, beta integer );create table gamma ( id integer not null, zooba integer );create table fargle ( id integer not null, dooba integer )";
        try {
            m_HSQLInterface.processDDLStatementsUsingVoltSQLParser(m_schema, null);
        } catch (Exception ex) {
            System.err.printf("Error parsing ddl: %s\n", ex.getMessage());
        }
    }

    //
    // SQL: create table alpha ( id integer not null, beta integer)
    //
    //
    // Pattern XML:
    //.ELEMENT: databaseschema
    //...name = databaseschema
    //.[
    //.....ELEMENT: table
    //....|..name = ALPHA
    //.....[
    //....|....ELEMENT: columns
    //....|....|.name = columns
    //....|....[
    //....|....|...ELEMENT: column
    //....|....|.....index = 0
    //....|....|.....name = ID
    //....|....|.....nullable = false
    //....|....|.....size = 10
    //....|....|.....valuetype = INTEGER
    //....|....|...ELEMENT: column
    //....|....|.....index = 1
    //....|....|.....name = BETA
    //....|....|.....nullable = true
    //....|....|.....size = 10
    //....|....|.....valuetype = INTEGER
    //....|....ELEMENT: indexes
    //....|....|.name = indexes
    //....|....[
    //....|....|...ELEMENT: index
    //....|....|.....assumeunique = false
    //....|....|.....columns =
    //....|....|.....ishashindex = false
    //....|....|.....name = SYS_IDX_10002
    //....|....|.....unique = true
    //....|....ELEMENT: constraints
    //....|....|.name = constraints
    //....|....[
    //....|....|...ELEMENT: constraint
    //....|....|.....assumeunique = false
    //....|....|.....constrainttype = NOT_NULL
    //....|....|.....name = SYS_CT_10001
    //....|....|.....nameisauto = true
    //....|....|.....rowslimit = 2147483647
    //
    //
    //
    @SuppressWarnings("unchecked")
    @Test
    public void testCreateTable() throws Exception {
        String ddl    = "create table alpha ( id integer not null, beta integer)";
        IDTable idTable = new IDTable();
        HSQLInterface hif = HSQLInterface.loadHsqldb(ParameterizationInfo.getParamStateManager());
        hif.processDDLStatementsUsingVoltSQLParser(ddl, null);
        VoltXMLElement element = hif.getVoltCatalogXML(null);
        assertThat(element)
            .hasName(1, "databaseschema")
            .hasAllOf(
                withAttribute(2, "name", "databaseschema"),
                withChildNamed(3, "table",
                    withAttribute(4, "name", "ALPHA"),
                    withChildNamed(5, "columns",
                        withAttribute(6, "name", "columns"),
                        withChildNamed(7, "column",
                                       "name", "ID",
                            withAttribute(8, "index", "0"),
                            withAttribute(9, "name", "ID"),
                            withAttribute(10, "nullable", "false"),
                            withAttribute(11, "size", "10"),
                            withAttribute(12, "valuetype", "INTEGER")),
                        withChildNamed(13, "column",
                                       "name", "BETA",
                            withAttribute(14, "index", "1"),
                            withAttribute(15, "name", "BETA"),
                            withAttribute(16, "nullable", "true"),
                            withAttribute(17, "size", "10"),
                            withAttribute(18, "valuetype", "INTEGER"))),
                    withChildNamed(19, "indexes",
                        withAttribute(20, "name", "indexes"),
                        withChildNamed(21, "index",
                                       "name", "SYS_IDX_10002",
                            withAttribute(22, "assumeunique", "false"),
                            withAttribute(23, "columns", ""),
                            withAttribute(24, "ishashindex", "false"),
                            withAttribute(25, "name", "SYS_IDX_10002"),
                            withAttribute(26, "unique", "true"))),
                    withChildNamed(27, "constraints",
                        withAttribute(28, "name", "constraints"))));
    }

    //
    // SQL: create table alpha ( id integer not null, beta Decimal)
    //
    //
    // Pattern XML:
    //.ELEMENT: databaseschema
    //...name = databaseschema
    //.[
    //.....ELEMENT: table
    //....|..name = ALPHA
    //.....[
    //....|....ELEMENT: columns
    //....|....|.name = columns
    //....|....[
    //....|....|...ELEMENT: column
    //....|....|.....index = 0
    //....|....|.....name = ID
    //....|....|.....nullable = false
    //....|....|.....size = 10
    //....|....|.....valuetype = INTEGER
    //....|....|...ELEMENT: column
    //....|....|.....index = 1
    //....|....|.....name = BETA
    //....|....|.....nullable = true
    //....|....|.....size = 100
    //....|....|.....valuetype = DECIMAL
    //....|....ELEMENT: indexes
    //....|....|.name = indexes
    //....|....[
    //....|....|...ELEMENT: index
    //....|....|.....assumeunique = false
    //....|....|.....columns =
    //....|....|.....ishashindex = false
    //....|....|.....name = SYS_IDX_10002
    //....|....|.....unique = true
    //....|....ELEMENT: constraints
    //....|....|.name = constraints
    //....|....[
    //....|....|...ELEMENT: constraint
    //....|....|.....assumeunique = false
    //....|....|.....constrainttype = NOT_NULL
    //....|....|.....name = SYS_CT_10001
    //....|....|.....nameisauto = true
    //....|....|.....rowslimit = 2147483647
    //
    //
    //
    @SuppressWarnings("unchecked")
    @Test
    public void testCreateTableWithDecimal() throws Exception {
        String ddl    = "create table alpha ( id integer not null, beta Decimal)";
        IDTable idTable = new IDTable();
        HSQLInterface hif = HSQLInterface.loadHsqldb(ParameterizationInfo.getParamStateManager());
        hif.processDDLStatementsUsingVoltSQLParser(ddl, null);
        VoltXMLElement element = hif.getVoltCatalogXML(null);
        assertThat(element)
            .hasName(1, "databaseschema")
            .hasAllOf(
                withAttribute(2, "name", "databaseschema"),
                withChildNamed(3, "table",
                    withAttribute(4, "name", "ALPHA"),
                    withChildNamed(5, "columns",
                        withAttribute(6, "name", "columns"),
                        withChildNamed(7, "column",
                                       "name", "ID",
                            withAttribute(8, "index", "0"),
                            withAttribute(9, "name", "ID"),
                            withAttribute(10, "nullable", "false"),
                            withAttribute(11, "size", "10"),
                            withAttribute(12, "valuetype", "INTEGER")),
                        withChildNamed(13, "column",
                                       "name", "BETA",
                            withAttribute(14, "index", "1"),
                            withAttribute(15, "name", "BETA"),
                            withAttribute(16, "nullable", "true"),
                            withAttribute(17, "size", "100"),
                            withAttribute(18, "valuetype", "DECIMAL"))),
                    withChildNamed(19, "indexes",
                        withAttribute(20, "name", "indexes"),
                        withChildNamed(21, "index",
                                       "name", "SYS_IDX_10002",
                            withAttribute(22, "assumeunique", "false"),
                            withAttribute(23, "columns", ""),
                            withAttribute(24, "ishashindex", "false"),
                            withAttribute(25, "name", "SYS_IDX_10002"),
                            withAttribute(26, "unique", "true"))),
                    withChildNamed(27, "constraints",
                        withAttribute(28, "name", "constraints"))));
    }

    // Pattern XML:
    //    //.ELEMENT: insert
    //    //...table = ALPHA
    //    //.[
    //    //.....ELEMENT: columns
    //    //.....[
    //    //....|....ELEMENT: column
    //    //....|....|.name = ID
    //    //....|....[
    //    //....|....|...ELEMENT: value
    //    //....|....|.....id = 1
    //    //....|....|.....value = 1
    //    //....|....|.....valuetype = INTEGER
    //    //....|....ELEMENT: column
    //    //....|....|.name = BETA
    //    //....|....[
    //    //....|....|...ELEMENT: value
    //    //....|....|.....id = 2
    //    //....|....|.....value = 1
    //    //....|....|.....valuetype = INTEGER
    //    //.....ELEMENT: parameters
    //    //
    //    //
    //
    //
    @SuppressWarnings("unchecked")
    @Test
    public void testInsert1() throws Exception {
        String sql    = "insert into alpha values (1, 1)";
        IDTable idTable = new IDTable();
        VoltXMLElement element = m_HSQLInterface.getVoltXMLFromSQLUsingVoltSQLParser(sql, null, SQLKind.DML);
        assertThat(element)
            .hasName(1, "insert")
            .hasAllOf(
                withAttribute(2, "table", "ALPHA"),
                withChildNamed(3, "columns",
                    withChildNamed(4, "column",
                        withAttribute(5, "name", "ID"),
                        withChildNamed(6, "value",
                            withIdAttribute(7, idTable),
                            withAttribute(8, "value", "1"),
                            withAttribute(9, "valuetype", "INTEGER"))),
                    withChildNamed(10, "column",
                        withAttribute(11, "name", "BETA"),
                        withChildNamed(12, "value",
                            withIdAttribute(13, idTable),
                            withAttribute(14, "value", "1"),
                            withAttribute(15, "valuetype", "INTEGER")))),
                withChildNamed(16, "parameters"));
    }

    // Pattern XML:
    //    //.ELEMENT: insert
    //    //...table = ALPHA
    //    //.[
    //    //.....ELEMENT: columns
    //    //.....[
    //    //....|....ELEMENT: column
    //    //....|....|.name = BETA
    //    //....|....[
    //    //....|....|...ELEMENT: value
    //    //....|....|.....id = 1
    //    //....|....|.....value = 100
    //    //....|....|.....valuetype = INTEGER
    //    //....|....ELEMENT: column
    //    //....|....|.name = ID
    //    //....|....[
    //    //....|....|...ELEMENT: value
    //    //....|....|.....id = 2
    //    //....|....|.....value = 101
    //    //....|....|.....valuetype = INTEGER
    //    //.....ELEMENT: parameters
    //    //
    //    //
    //
    //
    @SuppressWarnings("unchecked")
    @Test
    public void testInsert2() throws Exception {
        String sql    = "insert into alpha (beta, id) values (100, 101)";
        IDTable idTable = new IDTable();
        VoltXMLElement element = m_HSQLInterface.getVoltXMLFromSQLUsingVoltSQLParser(sql, null, SQLKind.DML);
        assertThat(element)
            .hasName(1, "insert")
            .hasAllOf(
                withAttribute(2, "table", "ALPHA"),
                withChildNamed(3, "columns",
                    withChildNamed(4, "column",
                        withAttribute(5, "name", "BETA"),
                        withChildNamed(6, "value",
                            withIdAttribute(7, idTable),
                            withAttribute(8, "value", "100"),
                            withAttribute(9, "valuetype", "INTEGER"))),
                    withChildNamed(10, "column",
                        withAttribute(11, "name", "ID"),
                        withChildNamed(12, "value",
                            withIdAttribute(13, idTable),
                            withAttribute(14, "value", "101"),
                            withAttribute(15, "valuetype", "INTEGER")))),
                withChildNamed(16, "parameters"));
    }

    // Pattern XML:
    //    //.ELEMENT: select
    //    //.[
    //    //.....ELEMENT: columns
    //    //.....[
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = ID
    //    //....|....|.column = ID
    //    //....|....|.id = 1
    //    //....|....|.index = 0
    //    //....|....|.table = ALPHA
    //    //.....ELEMENT: parameters
    //    //.....ELEMENT: tablescans
    //    //.....[
    //    //....|....ELEMENT: tablescan
    //    //....|....|.jointype = inner
    //    //....|....|.table = ALPHA
    //    //
    //    //
    //
    //
    @SuppressWarnings("unchecked")
    @Test
    public void testSelectId() throws Exception {
        String sql    = "select id from alpha";
        IDTable idTable = new IDTable();
        VoltXMLElement element = m_HSQLInterface.getVoltXMLFromSQLUsingVoltSQLParser(sql, null, SQLKind.DQL);
        assertThat(element)
            .hasName(1, "select")
            .hasAllOf(
                withChildNamed(2, "columns",
                    withChildNamed(3, "columnref",
                                   "alias", "ID",
                                   "column", "ID",
                                   "table", "ALPHA",
                        withAttribute(4, "alias", "ID"),
                        withAttribute(5, "column", "ID"),
                        withIdAttribute(6, idTable),
                        withAttribute(7, "index", "0"),
                        withAttribute(8, "table", "ALPHA"))),
                withChildNamed(9, "parameters"),
                withChildNamed(10, "tablescans",
                    withChildNamed(11, "tablescan",
                        withAttribute(12, "jointype", "inner"),
                        withAttribute(13, "table", "ALPHA"))));
    }

    // Pattern XML:
    //    //.ELEMENT: select
    //    //.[
    //    //.....ELEMENT: columns
    //    //.....[
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = BETA
    //    //....|....|.column = BETA
    //    //....|....|.id = 1
    //    //....|....|.index = 1
    //    //....|....|.table = ALPHA
    //    //.....ELEMENT: parameters
    //    //.....ELEMENT: tablescans
    //    //.....[
    //    //....|....ELEMENT: tablescan
    //    //....|....|.jointype = inner
    //    //....|....|.table = ALPHA
    //    //
    //    //
    //
    //
    @SuppressWarnings("unchecked")
    @Test
    public void testSelectBeta() throws Exception {
        String sql    = "select beta from alpha";
        IDTable idTable = new IDTable();
        VoltXMLElement element = m_HSQLInterface.getVoltXMLFromSQLUsingVoltSQLParser(sql, null, SQLKind.DQL);
        assertThat(element)
            .hasName(1, "select")
            .hasAllOf(
                withChildNamed(2, "columns",
                    withChildNamed(3, "columnref",
                                   "alias", "BETA",
                                   "column", "BETA",
                                   "table", "ALPHA",
                        withAttribute(4, "alias", "BETA"),
                        withAttribute(5, "column", "BETA"),
                        withIdAttribute(6, idTable),
                        withAttribute(7, "index", "1"),
                        withAttribute(8, "table", "ALPHA"))),
                withChildNamed(9, "parameters"),
                withChildNamed(10, "tablescans",
                    withChildNamed(11, "tablescan",
                        withAttribute(12, "jointype", "inner"),
                        withAttribute(13, "table", "ALPHA"))));
    }

    // Pattern XML:
    //    //.ELEMENT: select
    //    //.[
    //    //.....ELEMENT: columns
    //    //.....[
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = DOOBA
    //    //....|....|.column = DOOBA
    //    //....|....|.id = 1
    //    //....|....|.index = 1
    //    //....|....|.table = FARGLE
    //    //.....ELEMENT: parameters
    //    //.....ELEMENT: tablescans
    //    //.....[
    //    //....|....ELEMENT: tablescan
    //    //....|....|.jointype = inner
    //    //....|....|.table = FARGLE
    //    //
    //    //
    //
    //
    @SuppressWarnings("unchecked")
    @Test
    public void testSelectFromFargle() throws Exception {
        String sql    = "select dooba from fargle";
        IDTable idTable = new IDTable();
        VoltXMLElement element = m_HSQLInterface.getVoltXMLFromSQLUsingVoltSQLParser(sql, null, SQLKind.DQL);
        assertThat(element)
            .hasName(1, "select")
            .hasAllOf(
                withChildNamed(2, "columns",
                    withChildNamed(3, "columnref",
                                   "alias", "DOOBA",
                                   "column", "DOOBA",
                                   "table", "FARGLE",
                        withAttribute(4, "alias", "DOOBA"),
                        withAttribute(5, "column", "DOOBA"),
                        withIdAttribute(6, idTable),
                        withAttribute(7, "index", "1"),
                        withAttribute(8, "table", "FARGLE"))),
                withChildNamed(9, "parameters"),
                withChildNamed(10, "tablescans",
                    withChildNamed(11, "tablescan",
                        withAttribute(12, "jointype", "inner"),
                        withAttribute(13, "table", "FARGLE"))));
    }

    // Pattern XML:
    //    //.ELEMENT: select
    //    //.[
    //    //.....ELEMENT: columns
    //    //.....[
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = ID
    //    //....|....|.column = ID
    //    //....|....|.id = 1
    //    //....|....|.index = 0
    //    //....|....|.table = GAMMA
    //    //.....ELEMENT: parameters
    //    //.....ELEMENT: tablescans
    //    //.....[
    //    //....|....ELEMENT: tablescan
    //    //....|....|.jointype = inner
    //    //....|....|.table = GAMMA
    //    //
    //    //
    //
    //
    @SuppressWarnings("unchecked")
    @Test
    public void testSelectFromGamma() throws Exception {
        String sql    = "select id from gamma";
        IDTable idTable = new IDTable();
        VoltXMLElement element = m_HSQLInterface.getVoltXMLFromSQLUsingVoltSQLParser(sql, null, SQLKind.DQL);
        assertThat(element)
            .hasName(1, "select")
            .hasAllOf(
                withChildNamed(2, "columns",
                    withChildNamed(3, "columnref",
                                   "alias", "ID",
                                   "column", "ID",
                                   "table", "GAMMA",
                        withAttribute(4, "alias", "ID"),
                        withAttribute(5, "column", "ID"),
                        withIdAttribute(6, idTable),
                        withAttribute(7, "index", "0"),
                        withAttribute(8, "table", "GAMMA"))),
                withChildNamed(9, "parameters"),
                withChildNamed(10, "tablescans",
                    withChildNamed(11, "tablescan",
                        withAttribute(12, "jointype", "inner"),
                        withAttribute(13, "table", "GAMMA"))));
    }

    // Pattern XML:
    //    //.ELEMENT: select
    //    //.[
    //    //.....ELEMENT: columns
    //    //.....[
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = ID
    //    //....|....|.column = ID
    //    //....|....|.id = 1
    //    //....|....|.index = 0
    //    //....|....|.table = GAMMA
    //    //.....ELEMENT: parameters
    //    //.....ELEMENT: tablescans
    //    //.....[
    //    //....|....ELEMENT: tablescan
    //    //....|....|.jointype = inner
    //    //....|....|.table = GAMMA
    //    //
    //    //
    //
    //
    @SuppressWarnings("unchecked")
    @Test
    public void testCaseSelect() throws Exception {
        String sql    = "select ID from GAMMA";
        IDTable idTable = new IDTable();
        VoltXMLElement element = m_HSQLInterface.getVoltXMLFromSQLUsingVoltSQLParser(sql, null, SQLKind.DQL);
        assertThat(element)
            .hasName(1, "select")
            .hasAllOf(
                withChildNamed(2, "columns",
                    withChildNamed(3, "columnref",
                                   "alias", "ID",
                                   "column", "ID",
                                   "table", "GAMMA",
                        withAttribute(4, "alias", "ID"),
                        withAttribute(5, "column", "ID"),
                        withIdAttribute(6, idTable),
                        withAttribute(7, "index", "0"),
                        withAttribute(8, "table", "GAMMA"))),
                withChildNamed(9, "parameters"),
                withChildNamed(10, "tablescans",
                    withChildNamed(11, "tablescan",
                        withAttribute(12, "jointype", "inner"),
                        withAttribute(13, "table", "GAMMA"))));
    }
}

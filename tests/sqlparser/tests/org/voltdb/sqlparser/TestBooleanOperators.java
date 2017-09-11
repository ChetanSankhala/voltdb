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

public class TestBooleanOperators {
    HSQLInterface m_HSQLInterface = null;
    String        m_schema = null;
    public TestBooleanOperators() {
        m_HSQLInterface = HSQLInterface.loadHsqldb(ParameterizationInfo.getParamStateManager());
        String m_schema = "create table alpha ( id integer, beta integer );create table gamma ( id integer not null, zooba integer );create table fargle ( id integer not null, dooba integer )";
        try {
            m_HSQLInterface.processDDLStatementsUsingVoltSQLParser(m_schema, null);
        } catch (Exception ex) {
            System.err.printf("Error parsing ddl: %s\n", ex.getMessage());
        }
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
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = BETA
    //    //....|....|.column = BETA
    //    //....|....|.id = 2
    //    //....|....|.index = 1
    //    //....|....|.table = ALPHA
    //    //.....ELEMENT: parameters
    //    //.....ELEMENT: tablescans
    //    //.....[
    //    //....|....ELEMENT: tablescan
    //    //....|....|.jointype = inner
    //    //....|....|.table = ALPHA
    //    //....|....[
    //    //....|....|...ELEMENT: joincond
    //    //....|....|...[
    //    //....|....|....|..ELEMENT: operation
    //    //....|....|....|....id = 7
    //    //....|....|....|....optype = and
    //    //....|....|....|..[
    //    //....|....|....|....|.ELEMENT: operation
    //    //....|....|....|....|...id = 4
    //    //....|....|....|....|...optype = equal
    //    //....|....|....|....|.[
    //    //....|....|....|....|.....ELEMENT: columnref
    //    //....|....|....|....|....|..alias = ID
    //    //....|....|....|....|....|..column = ID
    //    //....|....|....|....|....|..id = 1
    //    //....|....|....|....|....|..index = 0
    //    //....|....|....|....|....|..table = ALPHA
    //    //....|....|....|....|.....ELEMENT: value
    //    //....|....|....|....|....|..id = 3
    //    //....|....|....|....|....|..value = 0
    //    //....|....|....|....|....|..valuetype = INTEGER
    //    //....|....|....|....|.ELEMENT: operation
    //    //....|....|....|....|...id = 6
    //    //....|....|....|....|...optype = equal
    //    //....|....|....|....|.[
    //    //....|....|....|....|.....ELEMENT: columnref
    //    //....|....|....|....|....|..alias = ID
    //    //....|....|....|....|....|..column = ID
    //    //....|....|....|....|....|..id = 1
    //    //....|....|....|....|....|..index = 0
    //    //....|....|....|....|....|..table = ALPHA
    //    //....|....|....|....|.....ELEMENT: value
    //    //....|....|....|....|....|..id = 5
    //    //....|....|....|....|....|..value = 1
    //    //....|....|....|....|....|..valuetype = INTEGER
    //    //
    //    //
    //
    //
    @SuppressWarnings("unchecked")
    @Test
    public void TestAnd() throws Exception {
        String sql    = "select * from alpha where id = 0 and id = 1";
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
                        withAttribute(8, "table", "ALPHA")),
                    withChildNamed(9, "columnref",
                                   "alias", "BETA",
                                   "column", "BETA",
                                   "table", "ALPHA",
                        withAttribute(10, "alias", "BETA"),
                        withAttribute(11, "column", "BETA"),
                        withIdAttribute(12, idTable),
                        withAttribute(13, "index", "1"),
                        withAttribute(14, "table", "ALPHA"))),
                withChildNamed(15, "parameters"),
                withChildNamed(16, "tablescans",
                    withChildNamed(17, "tablescan",
                        withAttribute(18, "jointype", "inner"),
                        withAttribute(19, "table", "ALPHA"),
                        withChildNamed(20, "joincond",
                            withChildNamed(21, "operation",
                                           "optype", "and",
                                withIdAttribute(22, idTable),
                                withAttribute(23, "optype", "and"),
                                withChildNamed(24, "operation",
                                               "optype", "equal",
                                    withIdAttribute(25, idTable),
                                    withAttribute(26, "optype", "equal"),
                                    withChildNamed(27, "columnref",
                                                   "alias", "ID",
                                                   "column", "ID",
                                                   "table", "ALPHA",
                                        withAttribute(28, "alias", "ID"),
                                        withAttribute(29, "column", "ID"),
                                        withIdAttribute(30, idTable),
                                        withAttribute(31, "index", "0"),
                                        withAttribute(32, "table", "ALPHA")),
                                    withChildNamed(33, "value",
                                        withIdAttribute(34, idTable),
                                        withAttribute(35, "value", "0"),
                                        withAttribute(36, "valuetype", "INTEGER"))),
                                withChildNamed(37, "operation",
                                               "optype", "equal",
                                    withIdAttribute(38, idTable),
                                    withAttribute(39, "optype", "equal"),
                                    withChildNamed(40, "columnref",
                                                   "alias", "ID",
                                                   "column", "ID",
                                                   "table", "ALPHA",
                                        withAttribute(41, "alias", "ID"),
                                        withAttribute(42, "column", "ID"),
                                        withIdAttribute(43, idTable),
                                        withAttribute(44, "index", "0"),
                                        withAttribute(45, "table", "ALPHA")),
                                    withChildNamed(46, "value",
                                        withIdAttribute(47, idTable),
                                        withAttribute(48, "value", "1"),
                                        withAttribute(49, "valuetype", "INTEGER"))))))));
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
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = BETA
    //    //....|....|.column = BETA
    //    //....|....|.id = 2
    //    //....|....|.index = 1
    //    //....|....|.table = ALPHA
    //    //.....ELEMENT: parameters
    //    //.....ELEMENT: tablescans
    //    //.....[
    //    //....|....ELEMENT: tablescan
    //    //....|....|.jointype = inner
    //    //....|....|.table = ALPHA
    //    //....|....[
    //    //....|....|...ELEMENT: joincond
    //    //....|....|...[
    //    //....|....|....|..ELEMENT: operation
    //    //....|....|....|....id = 7
    //    //....|....|....|....optype = or
    //    //....|....|....|..[
    //    //....|....|....|....|.ELEMENT: operation
    //    //....|....|....|....|...id = 4
    //    //....|....|....|....|...optype = notequal
    //    //....|....|....|....|.[
    //    //....|....|....|....|.....ELEMENT: columnref
    //    //....|....|....|....|....|..alias = ID
    //    //....|....|....|....|....|..column = ID
    //    //....|....|....|....|....|..id = 1
    //    //....|....|....|....|....|..index = 0
    //    //....|....|....|....|....|..table = ALPHA
    //    //....|....|....|....|.....ELEMENT: value
    //    //....|....|....|....|....|..id = 3
    //    //....|....|....|....|....|..value = 0
    //    //....|....|....|....|....|..valuetype = INTEGER
    //    //....|....|....|....|.ELEMENT: operation
    //    //....|....|....|....|...id = 6
    //    //....|....|....|....|...optype = equal
    //    //....|....|....|....|.[
    //    //....|....|....|....|.....ELEMENT: columnref
    //    //....|....|....|....|....|..alias = ID
    //    //....|....|....|....|....|..column = ID
    //    //....|....|....|....|....|..id = 1
    //    //....|....|....|....|....|..index = 0
    //    //....|....|....|....|....|..table = ALPHA
    //    //....|....|....|....|.....ELEMENT: value
    //    //....|....|....|....|....|..id = 5
    //    //....|....|....|....|....|..value = 1
    //    //....|....|....|....|....|..valuetype = INTEGER
    //    //
    //    //
    //
    //
    @SuppressWarnings("unchecked")
    @Test
    public void TestOr() throws Exception {
        String sql    = "select * from alpha where id != 0 or id = 1";
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
                        withAttribute(8, "table", "ALPHA")),
                    withChildNamed(9, "columnref",
                                   "alias", "BETA",
                                   "column", "BETA",
                                   "table", "ALPHA",
                        withAttribute(10, "alias", "BETA"),
                        withAttribute(11, "column", "BETA"),
                        withIdAttribute(12, idTable),
                        withAttribute(13, "index", "1"),
                        withAttribute(14, "table", "ALPHA"))),
                withChildNamed(15, "parameters"),
                withChildNamed(16, "tablescans",
                    withChildNamed(17, "tablescan",
                        withAttribute(18, "jointype", "inner"),
                        withAttribute(19, "table", "ALPHA"),
                        withChildNamed(20, "joincond",
                            withChildNamed(21, "operation",
                                           "optype", "or",
                                withIdAttribute(22, idTable),
                                withAttribute(23, "optype", "or"),
                                withChildNamed(24, "operation",
                                               "optype", "notequal",
                                    withIdAttribute(25, idTable),
                                    withAttribute(26, "optype", "notequal"),
                                    withChildNamed(27, "columnref",
                                                   "alias", "ID",
                                                   "column", "ID",
                                                   "table", "ALPHA",
                                        withAttribute(28, "alias", "ID"),
                                        withAttribute(29, "column", "ID"),
                                        withIdAttribute(30, idTable),
                                        withAttribute(31, "index", "0"),
                                        withAttribute(32, "table", "ALPHA")),
                                    withChildNamed(33, "value",
                                        withIdAttribute(34, idTable),
                                        withAttribute(35, "value", "0"),
                                        withAttribute(36, "valuetype", "INTEGER"))),
                                withChildNamed(37, "operation",
                                               "optype", "equal",
                                    withIdAttribute(38, idTable),
                                    withAttribute(39, "optype", "equal"),
                                    withChildNamed(40, "columnref",
                                                   "alias", "ID",
                                                   "column", "ID",
                                                   "table", "ALPHA",
                                        withAttribute(41, "alias", "ID"),
                                        withAttribute(42, "column", "ID"),
                                        withIdAttribute(43, idTable),
                                        withAttribute(44, "index", "0"),
                                        withAttribute(45, "table", "ALPHA")),
                                    withChildNamed(46, "value",
                                        withIdAttribute(47, idTable),
                                        withAttribute(48, "value", "1"),
                                        withAttribute(49, "valuetype", "INTEGER"))))))));
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
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = BETA
    //    //....|....|.column = BETA
    //    //....|....|.id = 2
    //    //....|....|.index = 1
    //    //....|....|.table = ALPHA
    //    //.....ELEMENT: parameters
    //    //.....ELEMENT: tablescans
    //    //.....[
    //    //....|....ELEMENT: tablescan
    //    //....|....|.jointype = inner
    //    //....|....|.table = ALPHA
    //    //....|....[
    //    //....|....|...ELEMENT: joincond
    //    //....|....|...[
    //    //....|....|....|..ELEMENT: operation
    //    //....|....|....|....id = 5
    //    //....|....|....|....optype = not
    //    //....|....|....|..[
    //    //....|....|....|....|.ELEMENT: operation
    //    //....|....|....|....|...id = 4
    //    //....|....|....|....|...optype = notequal
    //    //....|....|....|....|.[
    //    //....|....|....|....|.....ELEMENT: columnref
    //    //....|....|....|....|....|..alias = ID
    //    //....|....|....|....|....|..column = ID
    //    //....|....|....|....|....|..id = 1
    //    //....|....|....|....|....|..index = 0
    //    //....|....|....|....|....|..table = ALPHA
    //    //....|....|....|....|.....ELEMENT: value
    //    //....|....|....|....|....|..id = 3
    //    //....|....|....|....|....|..value = 0
    //    //....|....|....|....|....|..valuetype = INTEGER
    //    //
    //    //
    //
    //
    @SuppressWarnings("unchecked")
    @Test
    public void TestNot() throws Exception {
        String sql    = "select * from alpha where not id != 0";
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
                        withAttribute(8, "table", "ALPHA")),
                    withChildNamed(9, "columnref",
                                   "alias", "BETA",
                                   "column", "BETA",
                                   "table", "ALPHA",
                        withAttribute(10, "alias", "BETA"),
                        withAttribute(11, "column", "BETA"),
                        withIdAttribute(12, idTable),
                        withAttribute(13, "index", "1"),
                        withAttribute(14, "table", "ALPHA"))),
                withChildNamed(15, "parameters"),
                withChildNamed(16, "tablescans",
                    withChildNamed(17, "tablescan",
                        withAttribute(18, "jointype", "inner"),
                        withAttribute(19, "table", "ALPHA"),
                        withChildNamed(20, "joincond",
                            withChildNamed(21, "operation",
                                           "optype", "not",
                                withIdAttribute(22, idTable),
                                withAttribute(23, "optype", "not"),
                                withChildNamed(24, "operation",
                                               "optype", "notequal",
                                    withIdAttribute(25, idTable),
                                    withAttribute(26, "optype", "notequal"),
                                    withChildNamed(27, "columnref",
                                                   "alias", "ID",
                                                   "column", "ID",
                                                   "table", "ALPHA",
                                        withAttribute(28, "alias", "ID"),
                                        withAttribute(29, "column", "ID"),
                                        withIdAttribute(30, idTable),
                                        withAttribute(31, "index", "0"),
                                        withAttribute(32, "table", "ALPHA")),
                                    withChildNamed(33, "value",
                                        withIdAttribute(34, idTable),
                                        withAttribute(35, "value", "0"),
                                        withAttribute(36, "valuetype", "INTEGER"))))))));
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
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = BETA
    //    //....|....|.column = BETA
    //    //....|....|.id = 2
    //    //....|....|.index = 1
    //    //....|....|.table = ALPHA
    //    //.....ELEMENT: parameters
    //    //.....ELEMENT: tablescans
    //    //.....[
    //    //....|....ELEMENT: tablescan
    //    //....|....|.jointype = inner
    //    //....|....|.table = ALPHA
    //    //....|....[
    //    //....|....|...ELEMENT: joincond
    //    //....|....|...[
    //    //....|....|....|..ELEMENT: operation
    //    //....|....|....|....id = 8
    //    //....|....|....|....optype = and
    //    //....|....|....|..[
    //    //....|....|....|....|.ELEMENT: operation
    //    //....|....|....|....|...id = 5
    //    //....|....|....|....|...optype = not
    //    //....|....|....|....|.[
    //    //....|....|....|....|.....ELEMENT: operation
    //    //....|....|....|....|....|..id = 4
    //    //....|....|....|....|....|..optype = notequal
    //    //....|....|....|....|.....[
    //    //....|....|....|....|....|....ELEMENT: columnref
    //    //....|....|....|....|....|....|.alias = ID
    //    //....|....|....|....|....|....|.column = ID
    //    //....|....|....|....|....|....|.id = 1
    //    //....|....|....|....|....|....|.index = 0
    //    //....|....|....|....|....|....|.table = ALPHA
    //    //....|....|....|....|....|....ELEMENT: value
    //    //....|....|....|....|....|....|.id = 3
    //    //....|....|....|....|....|....|.value = 0
    //    //....|....|....|....|....|....|.valuetype = INTEGER
    //    //....|....|....|....|.ELEMENT: operation
    //    //....|....|....|....|...id = 7
    //    //....|....|....|....|...optype = notequal
    //    //....|....|....|....|.[
    //    //....|....|....|....|.....ELEMENT: columnref
    //    //....|....|....|....|....|..alias = BETA
    //    //....|....|....|....|....|..column = BETA
    //    //....|....|....|....|....|..id = 2
    //    //....|....|....|....|....|..index = 1
    //    //....|....|....|....|....|..table = ALPHA
    //    //....|....|....|....|.....ELEMENT: value
    //    //....|....|....|....|....|..id = 6
    //    //....|....|....|....|....|..value = 0
    //    //....|....|....|....|....|..valuetype = INTEGER
    //    //
    //    //
    //
    //
    @SuppressWarnings("unchecked")
    @Test
    public void TestPrecedenceNotAndL() throws Exception {
        String sql    = "select * from alpha where not id != 0 and beta != 0";
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
                        withAttribute(8, "table", "ALPHA")),
                    withChildNamed(9, "columnref",
                                   "alias", "BETA",
                                   "column", "BETA",
                                   "table", "ALPHA",
                        withAttribute(10, "alias", "BETA"),
                        withAttribute(11, "column", "BETA"),
                        withIdAttribute(12, idTable),
                        withAttribute(13, "index", "1"),
                        withAttribute(14, "table", "ALPHA"))),
                withChildNamed(15, "parameters"),
                withChildNamed(16, "tablescans",
                    withChildNamed(17, "tablescan",
                        withAttribute(18, "jointype", "inner"),
                        withAttribute(19, "table", "ALPHA"),
                        withChildNamed(20, "joincond",
                            withChildNamed(21, "operation",
                                           "optype", "and",
                                withIdAttribute(22, idTable),
                                withAttribute(23, "optype", "and"),
                                withChildNamed(24, "operation",
                                               "optype", "not",
                                    withIdAttribute(25, idTable),
                                    withAttribute(26, "optype", "not"),
                                    withChildNamed(27, "operation",
                                                   "optype", "notequal",
                                        withIdAttribute(28, idTable),
                                        withAttribute(29, "optype", "notequal"),
                                        withChildNamed(30, "columnref",
                                                       "alias", "ID",
                                                       "column", "ID",
                                                       "table", "ALPHA",
                                            withAttribute(31, "alias", "ID"),
                                            withAttribute(32, "column", "ID"),
                                            withIdAttribute(33, idTable),
                                            withAttribute(34, "index", "0"),
                                            withAttribute(35, "table", "ALPHA")),
                                        withChildNamed(36, "value",
                                            withIdAttribute(37, idTable),
                                            withAttribute(38, "value", "0"),
                                            withAttribute(39, "valuetype", "INTEGER")))),
                                withChildNamed(40, "operation",
                                               "optype", "notequal",
                                    withIdAttribute(41, idTable),
                                    withAttribute(42, "optype", "notequal"),
                                    withChildNamed(43, "columnref",
                                                   "alias", "BETA",
                                                   "column", "BETA",
                                                   "table", "ALPHA",
                                        withAttribute(44, "alias", "BETA"),
                                        withAttribute(45, "column", "BETA"),
                                        withIdAttribute(46, idTable),
                                        withAttribute(47, "index", "1"),
                                        withAttribute(48, "table", "ALPHA")),
                                    withChildNamed(49, "value",
                                        withIdAttribute(50, idTable),
                                        withAttribute(51, "value", "0"),
                                        withAttribute(52, "valuetype", "INTEGER"))))))));
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
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = BETA
    //    //....|....|.column = BETA
    //    //....|....|.id = 2
    //    //....|....|.index = 1
    //    //....|....|.table = ALPHA
    //    //.....ELEMENT: parameters
    //    //.....ELEMENT: tablescans
    //    //.....[
    //    //....|....ELEMENT: tablescan
    //    //....|....|.jointype = inner
    //    //....|....|.table = ALPHA
    //    //....|....[
    //    //....|....|...ELEMENT: joincond
    //    //....|....|...[
    //    //....|....|....|..ELEMENT: operation
    //    //....|....|....|....id = 8
    //    //....|....|....|....optype = or
    //    //....|....|....|..[
    //    //....|....|....|....|.ELEMENT: operation
    //    //....|....|....|....|...id = 5
    //    //....|....|....|....|...optype = not
    //    //....|....|....|....|.[
    //    //....|....|....|....|.....ELEMENT: operation
    //    //....|....|....|....|....|..id = 4
    //    //....|....|....|....|....|..optype = notequal
    //    //....|....|....|....|.....[
    //    //....|....|....|....|....|....ELEMENT: columnref
    //    //....|....|....|....|....|....|.alias = ID
    //    //....|....|....|....|....|....|.column = ID
    //    //....|....|....|....|....|....|.id = 1
    //    //....|....|....|....|....|....|.index = 0
    //    //....|....|....|....|....|....|.table = ALPHA
    //    //....|....|....|....|....|....ELEMENT: value
    //    //....|....|....|....|....|....|.id = 3
    //    //....|....|....|....|....|....|.value = 0
    //    //....|....|....|....|....|....|.valuetype = INTEGER
    //    //....|....|....|....|.ELEMENT: operation
    //    //....|....|....|....|...id = 7
    //    //....|....|....|....|...optype = notequal
    //    //....|....|....|....|.[
    //    //....|....|....|....|.....ELEMENT: columnref
    //    //....|....|....|....|....|..alias = BETA
    //    //....|....|....|....|....|..column = BETA
    //    //....|....|....|....|....|..id = 2
    //    //....|....|....|....|....|..index = 1
    //    //....|....|....|....|....|..table = ALPHA
    //    //....|....|....|....|.....ELEMENT: value
    //    //....|....|....|....|....|..id = 6
    //    //....|....|....|....|....|..value = 0
    //    //....|....|....|....|....|..valuetype = INTEGER
    //    //
    //    //
    //
    //
    @SuppressWarnings("unchecked")
    @Test
    public void TestPrecedenceNotOrL() throws Exception {
        String sql    = "select * from alpha where not id != 0 or beta != 0";
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
                        withAttribute(8, "table", "ALPHA")),
                    withChildNamed(9, "columnref",
                                   "alias", "BETA",
                                   "column", "BETA",
                                   "table", "ALPHA",
                        withAttribute(10, "alias", "BETA"),
                        withAttribute(11, "column", "BETA"),
                        withIdAttribute(12, idTable),
                        withAttribute(13, "index", "1"),
                        withAttribute(14, "table", "ALPHA"))),
                withChildNamed(15, "parameters"),
                withChildNamed(16, "tablescans",
                    withChildNamed(17, "tablescan",
                        withAttribute(18, "jointype", "inner"),
                        withAttribute(19, "table", "ALPHA"),
                        withChildNamed(20, "joincond",
                            withChildNamed(21, "operation",
                                           "optype", "or",
                                withIdAttribute(22, idTable),
                                withAttribute(23, "optype", "or"),
                                withChildNamed(24, "operation",
                                               "optype", "not",
                                    withIdAttribute(25, idTable),
                                    withAttribute(26, "optype", "not"),
                                    withChildNamed(27, "operation",
                                                   "optype", "notequal",
                                        withIdAttribute(28, idTable),
                                        withAttribute(29, "optype", "notequal"),
                                        withChildNamed(30, "columnref",
                                                       "alias", "ID",
                                                       "column", "ID",
                                                       "table", "ALPHA",
                                            withAttribute(31, "alias", "ID"),
                                            withAttribute(32, "column", "ID"),
                                            withIdAttribute(33, idTable),
                                            withAttribute(34, "index", "0"),
                                            withAttribute(35, "table", "ALPHA")),
                                        withChildNamed(36, "value",
                                            withIdAttribute(37, idTable),
                                            withAttribute(38, "value", "0"),
                                            withAttribute(39, "valuetype", "INTEGER")))),
                                withChildNamed(40, "operation",
                                               "optype", "notequal",
                                    withIdAttribute(41, idTable),
                                    withAttribute(42, "optype", "notequal"),
                                    withChildNamed(43, "columnref",
                                                   "alias", "BETA",
                                                   "column", "BETA",
                                                   "table", "ALPHA",
                                        withAttribute(44, "alias", "BETA"),
                                        withAttribute(45, "column", "BETA"),
                                        withIdAttribute(46, idTable),
                                        withAttribute(47, "index", "1"),
                                        withAttribute(48, "table", "ALPHA")),
                                    withChildNamed(49, "value",
                                        withIdAttribute(50, idTable),
                                        withAttribute(51, "value", "0"),
                                        withAttribute(52, "valuetype", "INTEGER"))))))));
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
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = BETA
    //    //....|....|.column = BETA
    //    //....|....|.id = 2
    //    //....|....|.index = 1
    //    //....|....|.table = ALPHA
    //    //.....ELEMENT: parameters
    //    //.....ELEMENT: tablescans
    //    //.....[
    //    //....|....ELEMENT: tablescan
    //    //....|....|.jointype = inner
    //    //....|....|.table = ALPHA
    //    //....|....[
    //    //....|....|...ELEMENT: joincond
    //    //....|....|...[
    //    //....|....|....|..ELEMENT: operation
    //    //....|....|....|....id = 8
    //    //....|....|....|....optype = and
    //    //....|....|....|..[
    //    //....|....|....|....|.ELEMENT: operation
    //    //....|....|....|....|...id = 4
    //    //....|....|....|....|...optype = notequal
    //    //....|....|....|....|.[
    //    //....|....|....|....|.....ELEMENT: columnref
    //    //....|....|....|....|....|..alias = ID
    //    //....|....|....|....|....|..column = ID
    //    //....|....|....|....|....|..id = 1
    //    //....|....|....|....|....|..index = 0
    //    //....|....|....|....|....|..table = ALPHA
    //    //....|....|....|....|.....ELEMENT: value
    //    //....|....|....|....|....|..id = 3
    //    //....|....|....|....|....|..value = 0
    //    //....|....|....|....|....|..valuetype = INTEGER
    //    //....|....|....|....|.ELEMENT: operation
    //    //....|....|....|....|...id = 7
    //    //....|....|....|....|...optype = not
    //    //....|....|....|....|.[
    //    //....|....|....|....|.....ELEMENT: operation
    //    //....|....|....|....|....|..id = 6
    //    //....|....|....|....|....|..optype = notequal
    //    //....|....|....|....|.....[
    //    //....|....|....|....|....|....ELEMENT: columnref
    //    //....|....|....|....|....|....|.alias = BETA
    //    //....|....|....|....|....|....|.column = BETA
    //    //....|....|....|....|....|....|.id = 2
    //    //....|....|....|....|....|....|.index = 1
    //    //....|....|....|....|....|....|.table = ALPHA
    //    //....|....|....|....|....|....ELEMENT: value
    //    //....|....|....|....|....|....|.id = 5
    //    //....|....|....|....|....|....|.value = 0
    //    //....|....|....|....|....|....|.valuetype = INTEGER
    //    //
    //    //
    //
    //
    @SuppressWarnings("unchecked")
    @Test
    public void TestPrecedenceNotAndR() throws Exception {
        String sql    = "select * from alpha where id != 0 and not beta != 0";
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
                        withAttribute(8, "table", "ALPHA")),
                    withChildNamed(9, "columnref",
                                   "alias", "BETA",
                                   "column", "BETA",
                                   "table", "ALPHA",
                        withAttribute(10, "alias", "BETA"),
                        withAttribute(11, "column", "BETA"),
                        withIdAttribute(12, idTable),
                        withAttribute(13, "index", "1"),
                        withAttribute(14, "table", "ALPHA"))),
                withChildNamed(15, "parameters"),
                withChildNamed(16, "tablescans",
                    withChildNamed(17, "tablescan",
                        withAttribute(18, "jointype", "inner"),
                        withAttribute(19, "table", "ALPHA"),
                        withChildNamed(20, "joincond",
                            withChildNamed(21, "operation",
                                           "optype", "and",
                                withIdAttribute(22, idTable),
                                withAttribute(23, "optype", "and"),
                                withChildNamed(24, "operation",
                                               "optype", "notequal",
                                    withIdAttribute(25, idTable),
                                    withAttribute(26, "optype", "notequal"),
                                    withChildNamed(27, "columnref",
                                                   "alias", "ID",
                                                   "column", "ID",
                                                   "table", "ALPHA",
                                        withAttribute(28, "alias", "ID"),
                                        withAttribute(29, "column", "ID"),
                                        withIdAttribute(30, idTable),
                                        withAttribute(31, "index", "0"),
                                        withAttribute(32, "table", "ALPHA")),
                                    withChildNamed(33, "value",
                                        withIdAttribute(34, idTable),
                                        withAttribute(35, "value", "0"),
                                        withAttribute(36, "valuetype", "INTEGER"))),
                                withChildNamed(37, "operation",
                                               "optype", "not",
                                    withIdAttribute(38, idTable),
                                    withAttribute(39, "optype", "not"),
                                    withChildNamed(40, "operation",
                                                   "optype", "notequal",
                                        withIdAttribute(41, idTable),
                                        withAttribute(42, "optype", "notequal"),
                                        withChildNamed(43, "columnref",
                                                       "alias", "BETA",
                                                       "column", "BETA",
                                                       "table", "ALPHA",
                                            withAttribute(44, "alias", "BETA"),
                                            withAttribute(45, "column", "BETA"),
                                            withIdAttribute(46, idTable),
                                            withAttribute(47, "index", "1"),
                                            withAttribute(48, "table", "ALPHA")),
                                        withChildNamed(49, "value",
                                            withIdAttribute(50, idTable),
                                            withAttribute(51, "value", "0"),
                                            withAttribute(52, "valuetype", "INTEGER")))))))));
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
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = BETA
    //    //....|....|.column = BETA
    //    //....|....|.id = 2
    //    //....|....|.index = 1
    //    //....|....|.table = ALPHA
    //    //.....ELEMENT: parameters
    //    //.....ELEMENT: tablescans
    //    //.....[
    //    //....|....ELEMENT: tablescan
    //    //....|....|.jointype = inner
    //    //....|....|.table = ALPHA
    //    //....|....[
    //    //....|....|...ELEMENT: joincond
    //    //....|....|...[
    //    //....|....|....|..ELEMENT: operation
    //    //....|....|....|....id = 8
    //    //....|....|....|....optype = or
    //    //....|....|....|..[
    //    //....|....|....|....|.ELEMENT: operation
    //    //....|....|....|....|...id = 4
    //    //....|....|....|....|...optype = notequal
    //    //....|....|....|....|.[
    //    //....|....|....|....|.....ELEMENT: columnref
    //    //....|....|....|....|....|..alias = ID
    //    //....|....|....|....|....|..column = ID
    //    //....|....|....|....|....|..id = 1
    //    //....|....|....|....|....|..index = 0
    //    //....|....|....|....|....|..table = ALPHA
    //    //....|....|....|....|.....ELEMENT: value
    //    //....|....|....|....|....|..id = 3
    //    //....|....|....|....|....|..value = 0
    //    //....|....|....|....|....|..valuetype = INTEGER
    //    //....|....|....|....|.ELEMENT: operation
    //    //....|....|....|....|...id = 7
    //    //....|....|....|....|...optype = not
    //    //....|....|....|....|.[
    //    //....|....|....|....|.....ELEMENT: operation
    //    //....|....|....|....|....|..id = 6
    //    //....|....|....|....|....|..optype = notequal
    //    //....|....|....|....|.....[
    //    //....|....|....|....|....|....ELEMENT: columnref
    //    //....|....|....|....|....|....|.alias = BETA
    //    //....|....|....|....|....|....|.column = BETA
    //    //....|....|....|....|....|....|.id = 2
    //    //....|....|....|....|....|....|.index = 1
    //    //....|....|....|....|....|....|.table = ALPHA
    //    //....|....|....|....|....|....ELEMENT: value
    //    //....|....|....|....|....|....|.id = 5
    //    //....|....|....|....|....|....|.value = 0
    //    //....|....|....|....|....|....|.valuetype = INTEGER
    //    //
    //    //
    //
    //
    @SuppressWarnings("unchecked")
    @Test
    public void TestPrecedenceNotOrR() throws Exception {
        String sql    = "select * from alpha where id != 0 or not beta != 0";
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
                        withAttribute(8, "table", "ALPHA")),
                    withChildNamed(9, "columnref",
                                   "alias", "BETA",
                                   "column", "BETA",
                                   "table", "ALPHA",
                        withAttribute(10, "alias", "BETA"),
                        withAttribute(11, "column", "BETA"),
                        withIdAttribute(12, idTable),
                        withAttribute(13, "index", "1"),
                        withAttribute(14, "table", "ALPHA"))),
                withChildNamed(15, "parameters"),
                withChildNamed(16, "tablescans",
                    withChildNamed(17, "tablescan",
                        withAttribute(18, "jointype", "inner"),
                        withAttribute(19, "table", "ALPHA"),
                        withChildNamed(20, "joincond",
                            withChildNamed(21, "operation",
                                           "optype", "or",
                                withIdAttribute(22, idTable),
                                withAttribute(23, "optype", "or"),
                                withChildNamed(24, "operation",
                                               "optype", "notequal",
                                    withIdAttribute(25, idTable),
                                    withAttribute(26, "optype", "notequal"),
                                    withChildNamed(27, "columnref",
                                                   "alias", "ID",
                                                   "column", "ID",
                                                   "table", "ALPHA",
                                        withAttribute(28, "alias", "ID"),
                                        withAttribute(29, "column", "ID"),
                                        withIdAttribute(30, idTable),
                                        withAttribute(31, "index", "0"),
                                        withAttribute(32, "table", "ALPHA")),
                                    withChildNamed(33, "value",
                                        withIdAttribute(34, idTable),
                                        withAttribute(35, "value", "0"),
                                        withAttribute(36, "valuetype", "INTEGER"))),
                                withChildNamed(37, "operation",
                                               "optype", "not",
                                    withIdAttribute(38, idTable),
                                    withAttribute(39, "optype", "not"),
                                    withChildNamed(40, "operation",
                                                   "optype", "notequal",
                                        withIdAttribute(41, idTable),
                                        withAttribute(42, "optype", "notequal"),
                                        withChildNamed(43, "columnref",
                                                       "alias", "BETA",
                                                       "column", "BETA",
                                                       "table", "ALPHA",
                                            withAttribute(44, "alias", "BETA"),
                                            withAttribute(45, "column", "BETA"),
                                            withIdAttribute(46, idTable),
                                            withAttribute(47, "index", "1"),
                                            withAttribute(48, "table", "ALPHA")),
                                        withChildNamed(49, "value",
                                            withIdAttribute(50, idTable),
                                            withAttribute(51, "value", "0"),
                                            withAttribute(52, "valuetype", "INTEGER")))))))));
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
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = BETA
    //    //....|....|.column = BETA
    //    //....|....|.id = 2
    //    //....|....|.index = 1
    //    //....|....|.table = ALPHA
    //    //.....ELEMENT: parameters
    //    //.....ELEMENT: tablescans
    //    //.....[
    //    //....|....ELEMENT: tablescan
    //    //....|....|.jointype = inner
    //    //....|....|.table = ALPHA
    //    //....|....[
    //    //....|....|...ELEMENT: joincond
    //    //....|....|...[
    //    //....|....|....|..ELEMENT: operation
    //    //....|....|....|....id = 10
    //    //....|....|....|....optype = or
    //    //....|....|....|..[
    //    //....|....|....|....|.ELEMENT: operation
    //    //....|....|....|....|...id = 7
    //    //....|....|....|....|...optype = or
    //    //....|....|....|....|.[
    //    //....|....|....|....|.....ELEMENT: operation
    //    //....|....|....|....|....|..id = 4
    //    //....|....|....|....|....|..optype = notequal
    //    //....|....|....|....|.....[
    //    //....|....|....|....|....|....ELEMENT: columnref
    //    //....|....|....|....|....|....|.alias = ID
    //    //....|....|....|....|....|....|.column = ID
    //    //....|....|....|....|....|....|.id = 1
    //    //....|....|....|....|....|....|.index = 0
    //    //....|....|....|....|....|....|.table = ALPHA
    //    //....|....|....|....|....|....ELEMENT: value
    //    //....|....|....|....|....|....|.id = 3
    //    //....|....|....|....|....|....|.value = 0
    //    //....|....|....|....|....|....|.valuetype = INTEGER
    //    //....|....|....|....|.....ELEMENT: operation
    //    //....|....|....|....|....|..id = 6
    //    //....|....|....|....|....|..optype = notequal
    //    //....|....|....|....|.....[
    //    //....|....|....|....|....|....ELEMENT: columnref
    //    //....|....|....|....|....|....|.alias = BETA
    //    //....|....|....|....|....|....|.column = BETA
    //    //....|....|....|....|....|....|.id = 2
    //    //....|....|....|....|....|....|.index = 1
    //    //....|....|....|....|....|....|.table = ALPHA
    //    //....|....|....|....|....|....ELEMENT: value
    //    //....|....|....|....|....|....|.id = 5
    //    //....|....|....|....|....|....|.value = 0
    //    //....|....|....|....|....|....|.valuetype = INTEGER
    //    //....|....|....|....|.ELEMENT: operation
    //    //....|....|....|....|...id = 9
    //    //....|....|....|....|...optype = notequal
    //    //....|....|....|....|.[
    //    //....|....|....|....|.....ELEMENT: columnref
    //    //....|....|....|....|....|..alias = BETA
    //    //....|....|....|....|....|..column = BETA
    //    //....|....|....|....|....|..id = 2
    //    //....|....|....|....|....|..index = 1
    //    //....|....|....|....|....|..table = ALPHA
    //    //....|....|....|....|.....ELEMENT: value
    //    //....|....|....|....|....|..id = 8
    //    //....|....|....|....|....|..value = 1
    //    //....|....|....|....|....|..valuetype = INTEGER
    //    //
    //    //
    //
    //
    @SuppressWarnings("unchecked")
    @Test
    public void TestPrecedenceOrOr() throws Exception {
        String sql    = "select * from alpha where id != 0 or beta != 0 or beta !=  1";
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
                        withAttribute(8, "table", "ALPHA")),
                    withChildNamed(9, "columnref",
                                   "alias", "BETA",
                                   "column", "BETA",
                                   "table", "ALPHA",
                        withAttribute(10, "alias", "BETA"),
                        withAttribute(11, "column", "BETA"),
                        withIdAttribute(12, idTable),
                        withAttribute(13, "index", "1"),
                        withAttribute(14, "table", "ALPHA"))),
                withChildNamed(15, "parameters"),
                withChildNamed(16, "tablescans",
                    withChildNamed(17, "tablescan",
                        withAttribute(18, "jointype", "inner"),
                        withAttribute(19, "table", "ALPHA"),
                        withChildNamed(20, "joincond",
                            withChildNamed(21, "operation",
                                           "optype", "or",
                                withIdAttribute(22, idTable),
                                withAttribute(23, "optype", "or"),
                                withChildNamed(24, "operation",
                                               "optype", "or",
                                    withIdAttribute(25, idTable),
                                    withAttribute(26, "optype", "or"),
                                    withChildNamed(27, "operation",
                                                   "optype", "notequal",
                                        withIdAttribute(28, idTable),
                                        withAttribute(29, "optype", "notequal"),
                                        withChildNamed(30, "columnref",
                                                       "alias", "ID",
                                                       "column", "ID",
                                                       "table", "ALPHA",
                                            withAttribute(31, "alias", "ID"),
                                            withAttribute(32, "column", "ID"),
                                            withIdAttribute(33, idTable),
                                            withAttribute(34, "index", "0"),
                                            withAttribute(35, "table", "ALPHA")),
                                        withChildNamed(36, "value",
                                            withIdAttribute(37, idTable),
                                            withAttribute(38, "value", "0"),
                                            withAttribute(39, "valuetype", "INTEGER"))),
                                    withChildNamed(40, "operation",
                                                   "optype", "notequal",
                                        withIdAttribute(41, idTable),
                                        withAttribute(42, "optype", "notequal"),
                                        withChildNamed(43, "columnref",
                                                       "alias", "BETA",
                                                       "column", "BETA",
                                                       "table", "ALPHA",
                                            withAttribute(44, "alias", "BETA"),
                                            withAttribute(45, "column", "BETA"),
                                            withIdAttribute(46, idTable),
                                            withAttribute(47, "index", "1"),
                                            withAttribute(48, "table", "ALPHA")),
                                        withChildNamed(49, "value",
                                            withIdAttribute(50, idTable),
                                            withAttribute(51, "value", "0"),
                                            withAttribute(52, "valuetype", "INTEGER")))),
                                withChildNamed(53, "operation",
                                               "optype", "notequal",
                                    withIdAttribute(54, idTable),
                                    withAttribute(55, "optype", "notequal"),
                                    withChildNamed(56, "columnref",
                                                   "alias", "BETA",
                                                   "column", "BETA",
                                                   "table", "ALPHA",
                                        withAttribute(57, "alias", "BETA"),
                                        withAttribute(58, "column", "BETA"),
                                        withIdAttribute(59, idTable),
                                        withAttribute(60, "index", "1"),
                                        withAttribute(61, "table", "ALPHA")),
                                    withChildNamed(62, "value",
                                        withIdAttribute(63, idTable),
                                        withAttribute(64, "value", "1"),
                                        withAttribute(65, "valuetype", "INTEGER"))))))));
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
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = BETA
    //    //....|....|.column = BETA
    //    //....|....|.id = 2
    //    //....|....|.index = 1
    //    //....|....|.table = ALPHA
    //    //.....ELEMENT: parameters
    //    //.....ELEMENT: tablescans
    //    //.....[
    //    //....|....ELEMENT: tablescan
    //    //....|....|.jointype = inner
    //    //....|....|.table = ALPHA
    //    //....|....[
    //    //....|....|...ELEMENT: joincond
    //    //....|....|...[
    //    //....|....|....|..ELEMENT: operation
    //    //....|....|....|....id = 10
    //    //....|....|....|....optype = or
    //    //....|....|....|..[
    //    //....|....|....|....|.ELEMENT: operation
    //    //....|....|....|....|...id = 4
    //    //....|....|....|....|...optype = notequal
    //    //....|....|....|....|.[
    //    //....|....|....|....|.....ELEMENT: columnref
    //    //....|....|....|....|....|..alias = ID
    //    //....|....|....|....|....|..column = ID
    //    //....|....|....|....|....|..id = 1
    //    //....|....|....|....|....|..index = 0
    //    //....|....|....|....|....|..table = ALPHA
    //    //....|....|....|....|.....ELEMENT: value
    //    //....|....|....|....|....|..id = 3
    //    //....|....|....|....|....|..value = 0
    //    //....|....|....|....|....|..valuetype = INTEGER
    //    //....|....|....|....|.ELEMENT: operation
    //    //....|....|....|....|...id = 9
    //    //....|....|....|....|...optype = and
    //    //....|....|....|....|.[
    //    //....|....|....|....|.....ELEMENT: operation
    //    //....|....|....|....|....|..id = 6
    //    //....|....|....|....|....|..optype = notequal
    //    //....|....|....|....|.....[
    //    //....|....|....|....|....|....ELEMENT: columnref
    //    //....|....|....|....|....|....|.alias = BETA
    //    //....|....|....|....|....|....|.column = BETA
    //    //....|....|....|....|....|....|.id = 2
    //    //....|....|....|....|....|....|.index = 1
    //    //....|....|....|....|....|....|.table = ALPHA
    //    //....|....|....|....|....|....ELEMENT: value
    //    //....|....|....|....|....|....|.id = 5
    //    //....|....|....|....|....|....|.value = 0
    //    //....|....|....|....|....|....|.valuetype = INTEGER
    //    //....|....|....|....|.....ELEMENT: operation
    //    //....|....|....|....|....|..id = 8
    //    //....|....|....|....|....|..optype = notequal
    //    //....|....|....|....|.....[
    //    //....|....|....|....|....|....ELEMENT: columnref
    //    //....|....|....|....|....|....|.alias = BETA
    //    //....|....|....|....|....|....|.column = BETA
    //    //....|....|....|....|....|....|.id = 2
    //    //....|....|....|....|....|....|.index = 1
    //    //....|....|....|....|....|....|.table = ALPHA
    //    //....|....|....|....|....|....ELEMENT: value
    //    //....|....|....|....|....|....|.id = 7
    //    //....|....|....|....|....|....|.value = 1
    //    //....|....|....|....|....|....|.valuetype = INTEGER
    //    //
    //    //
    //
    //
    @SuppressWarnings("unchecked")
    @Test
    public void TestPrecedenceOrAnd() throws Exception {
        String sql    = "select * from alpha where id != 0 or beta != 0 and beta != 1";
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
                        withAttribute(8, "table", "ALPHA")),
                    withChildNamed(9, "columnref",
                                   "alias", "BETA",
                                   "column", "BETA",
                                   "table", "ALPHA",
                        withAttribute(10, "alias", "BETA"),
                        withAttribute(11, "column", "BETA"),
                        withIdAttribute(12, idTable),
                        withAttribute(13, "index", "1"),
                        withAttribute(14, "table", "ALPHA"))),
                withChildNamed(15, "parameters"),
                withChildNamed(16, "tablescans",
                    withChildNamed(17, "tablescan",
                        withAttribute(18, "jointype", "inner"),
                        withAttribute(19, "table", "ALPHA"),
                        withChildNamed(20, "joincond",
                            withChildNamed(21, "operation",
                                           "optype", "or",
                                withIdAttribute(22, idTable),
                                withAttribute(23, "optype", "or"),
                                withChildNamed(24, "operation",
                                               "optype", "notequal",
                                    withIdAttribute(25, idTable),
                                    withAttribute(26, "optype", "notequal"),
                                    withChildNamed(27, "columnref",
                                                   "alias", "ID",
                                                   "column", "ID",
                                                   "table", "ALPHA",
                                        withAttribute(28, "alias", "ID"),
                                        withAttribute(29, "column", "ID"),
                                        withIdAttribute(30, idTable),
                                        withAttribute(31, "index", "0"),
                                        withAttribute(32, "table", "ALPHA")),
                                    withChildNamed(33, "value",
                                        withIdAttribute(34, idTable),
                                        withAttribute(35, "value", "0"),
                                        withAttribute(36, "valuetype", "INTEGER"))),
                                withChildNamed(37, "operation",
                                               "optype", "and",
                                    withIdAttribute(38, idTable),
                                    withAttribute(39, "optype", "and"),
                                    withChildNamed(40, "operation",
                                                   "optype", "notequal",
                                        withIdAttribute(41, idTable),
                                        withAttribute(42, "optype", "notequal"),
                                        withChildNamed(43, "columnref",
                                                       "alias", "BETA",
                                                       "column", "BETA",
                                                       "table", "ALPHA",
                                            withAttribute(44, "alias", "BETA"),
                                            withAttribute(45, "column", "BETA"),
                                            withIdAttribute(46, idTable),
                                            withAttribute(47, "index", "1"),
                                            withAttribute(48, "table", "ALPHA")),
                                        withChildNamed(49, "value",
                                            withIdAttribute(50, idTable),
                                            withAttribute(51, "value", "0"),
                                            withAttribute(52, "valuetype", "INTEGER"))),
                                    withChildNamed(53, "operation",
                                                   "optype", "notequal",
                                        withIdAttribute(54, idTable),
                                        withAttribute(55, "optype", "notequal"),
                                        withChildNamed(56, "columnref",
                                                       "alias", "BETA",
                                                       "column", "BETA",
                                                       "table", "ALPHA",
                                            withAttribute(57, "alias", "BETA"),
                                            withAttribute(58, "column", "BETA"),
                                            withIdAttribute(59, idTable),
                                            withAttribute(60, "index", "1"),
                                            withAttribute(61, "table", "ALPHA")),
                                        withChildNamed(62, "value",
                                            withIdAttribute(63, idTable),
                                            withAttribute(64, "value", "1"),
                                            withAttribute(65, "valuetype", "INTEGER")))))))));
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
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = BETA
    //    //....|....|.column = BETA
    //    //....|....|.id = 2
    //    //....|....|.index = 1
    //    //....|....|.table = ALPHA
    //    //.....ELEMENT: parameters
    //    //.....ELEMENT: tablescans
    //    //.....[
    //    //....|....ELEMENT: tablescan
    //    //....|....|.jointype = inner
    //    //....|....|.table = ALPHA
    //    //....|....[
    //    //....|....|...ELEMENT: joincond
    //    //....|....|...[
    //    //....|....|....|..ELEMENT: operation
    //    //....|....|....|....id = 10
    //    //....|....|....|....optype = and
    //    //....|....|....|..[
    //    //....|....|....|....|.ELEMENT: operation
    //    //....|....|....|....|...id = 9
    //    //....|....|....|....|...optype = and
    //    //....|....|....|....|.[
    //    //....|....|....|....|.....ELEMENT: operation
    //    //....|....|....|....|....|..id = 4
    //    //....|....|....|....|....|..optype = notequal
    //    //....|....|....|....|.....[
    //    //....|....|....|....|....|....ELEMENT: columnref
    //    //....|....|....|....|....|....|.alias = ID
    //    //....|....|....|....|....|....|.column = ID
    //    //....|....|....|....|....|....|.id = 1
    //    //....|....|....|....|....|....|.index = 0
    //    //....|....|....|....|....|....|.table = ALPHA
    //    //....|....|....|....|....|....ELEMENT: value
    //    //....|....|....|....|....|....|.id = 3
    //    //....|....|....|....|....|....|.value = 0
    //    //....|....|....|....|....|....|.valuetype = INTEGER
    //    //....|....|....|....|.....ELEMENT: operation
    //    //....|....|....|....|....|..id = 6
    //    //....|....|....|....|....|..optype = notequal
    //    //....|....|....|....|.....[
    //    //....|....|....|....|....|....ELEMENT: columnref
    //    //....|....|....|....|....|....|.alias = BETA
    //    //....|....|....|....|....|....|.column = BETA
    //    //....|....|....|....|....|....|.id = 2
    //    //....|....|....|....|....|....|.index = 1
    //    //....|....|....|....|....|....|.table = ALPHA
    //    //....|....|....|....|....|....ELEMENT: value
    //    //....|....|....|....|....|....|.id = 5
    //    //....|....|....|....|....|....|.value = 0
    //    //....|....|....|....|....|....|.valuetype = INTEGER
    //    //....|....|....|....|.ELEMENT: operation
    //    //....|....|....|....|...id = 8
    //    //....|....|....|....|...optype = notequal
    //    //....|....|....|....|.[
    //    //....|....|....|....|.....ELEMENT: columnref
    //    //....|....|....|....|....|..alias = BETA
    //    //....|....|....|....|....|..column = BETA
    //    //....|....|....|....|....|..id = 2
    //    //....|....|....|....|....|..index = 1
    //    //....|....|....|....|....|..table = ALPHA
    //    //....|....|....|....|.....ELEMENT: value
    //    //....|....|....|....|....|..id = 7
    //    //....|....|....|....|....|..value = 1
    //    //....|....|....|....|....|..valuetype = INTEGER
    //    //
    //    //
    //
    //
    @SuppressWarnings("unchecked")
    @Test
    public void TestPrecedenceAndAnd() throws Exception {
        String sql    = "select * from alpha where id != 0 and beta != 0 and beta != 1";
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
                        withAttribute(8, "table", "ALPHA")),
                    withChildNamed(9, "columnref",
                                   "alias", "BETA",
                                   "column", "BETA",
                                   "table", "ALPHA",
                        withAttribute(10, "alias", "BETA"),
                        withAttribute(11, "column", "BETA"),
                        withIdAttribute(12, idTable),
                        withAttribute(13, "index", "1"),
                        withAttribute(14, "table", "ALPHA"))),
                withChildNamed(15, "parameters"),
                withChildNamed(16, "tablescans",
                    withChildNamed(17, "tablescan",
                        withAttribute(18, "jointype", "inner"),
                        withAttribute(19, "table", "ALPHA"),
                        withChildNamed(20, "joincond",
                            withChildNamed(21, "operation",
                                           "optype", "and",
                                withIdAttribute(22, idTable),
                                withAttribute(23, "optype", "and"),
                                withChildNamed(24, "operation",
                                               "optype", "and",
                                    withIdAttribute(25, idTable),
                                    withAttribute(26, "optype", "and"),
                                    withChildNamed(27, "operation",
                                                   "optype", "notequal",
                                        withIdAttribute(28, idTable),
                                        withAttribute(29, "optype", "notequal"),
                                        withChildNamed(30, "columnref",
                                                       "alias", "ID",
                                                       "column", "ID",
                                                       "table", "ALPHA",
                                            withAttribute(31, "alias", "ID"),
                                            withAttribute(32, "column", "ID"),
                                            withIdAttribute(33, idTable),
                                            withAttribute(34, "index", "0"),
                                            withAttribute(35, "table", "ALPHA")),
                                        withChildNamed(36, "value",
                                            withIdAttribute(37, idTable),
                                            withAttribute(38, "value", "0"),
                                            withAttribute(39, "valuetype", "INTEGER"))),
                                    withChildNamed(40, "operation",
                                                   "optype", "notequal",
                                        withIdAttribute(41, idTable),
                                        withAttribute(42, "optype", "notequal"),
                                        withChildNamed(43, "columnref",
                                                       "alias", "BETA",
                                                       "column", "BETA",
                                                       "table", "ALPHA",
                                            withAttribute(44, "alias", "BETA"),
                                            withAttribute(45, "column", "BETA"),
                                            withIdAttribute(46, idTable),
                                            withAttribute(47, "index", "1"),
                                            withAttribute(48, "table", "ALPHA")),
                                        withChildNamed(49, "value",
                                            withIdAttribute(50, idTable),
                                            withAttribute(51, "value", "0"),
                                            withAttribute(52, "valuetype", "INTEGER")))),
                                withChildNamed(53, "operation",
                                               "optype", "notequal",
                                    withIdAttribute(54, idTable),
                                    withAttribute(55, "optype", "notequal"),
                                    withChildNamed(56, "columnref",
                                                   "alias", "BETA",
                                                   "column", "BETA",
                                                   "table", "ALPHA",
                                        withAttribute(57, "alias", "BETA"),
                                        withAttribute(58, "column", "BETA"),
                                        withIdAttribute(59, idTable),
                                        withAttribute(60, "index", "1"),
                                        withAttribute(61, "table", "ALPHA")),
                                    withChildNamed(62, "value",
                                        withIdAttribute(63, idTable),
                                        withAttribute(64, "value", "1"),
                                        withAttribute(65, "valuetype", "INTEGER"))))))));
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
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = BETA
    //    //....|....|.column = BETA
    //    //....|....|.id = 2
    //    //....|....|.index = 1
    //    //....|....|.table = ALPHA
    //    //.....ELEMENT: parameters
    //    //.....ELEMENT: tablescans
    //    //.....[
    //    //....|....ELEMENT: tablescan
    //    //....|....|.jointype = inner
    //    //....|....|.table = ALPHA
    //    //....|....[
    //    //....|....|...ELEMENT: joincond
    //    //....|....|...[
    //    //....|....|....|..ELEMENT: operation
    //    //....|....|....|....id = 10
    //    //....|....|....|....optype = or
    //    //....|....|....|..[
    //    //....|....|....|....|.ELEMENT: operation
    //    //....|....|....|....|...id = 7
    //    //....|....|....|....|...optype = and
    //    //....|....|....|....|.[
    //    //....|....|....|....|.....ELEMENT: operation
    //    //....|....|....|....|....|..id = 4
    //    //....|....|....|....|....|..optype = notequal
    //    //....|....|....|....|.....[
    //    //....|....|....|....|....|....ELEMENT: columnref
    //    //....|....|....|....|....|....|.alias = ID
    //    //....|....|....|....|....|....|.column = ID
    //    //....|....|....|....|....|....|.id = 1
    //    //....|....|....|....|....|....|.index = 0
    //    //....|....|....|....|....|....|.table = ALPHA
    //    //....|....|....|....|....|....ELEMENT: value
    //    //....|....|....|....|....|....|.id = 3
    //    //....|....|....|....|....|....|.value = 0
    //    //....|....|....|....|....|....|.valuetype = INTEGER
    //    //....|....|....|....|.....ELEMENT: operation
    //    //....|....|....|....|....|..id = 6
    //    //....|....|....|....|....|..optype = notequal
    //    //....|....|....|....|.....[
    //    //....|....|....|....|....|....ELEMENT: columnref
    //    //....|....|....|....|....|....|.alias = BETA
    //    //....|....|....|....|....|....|.column = BETA
    //    //....|....|....|....|....|....|.id = 2
    //    //....|....|....|....|....|....|.index = 1
    //    //....|....|....|....|....|....|.table = ALPHA
    //    //....|....|....|....|....|....ELEMENT: value
    //    //....|....|....|....|....|....|.id = 5
    //    //....|....|....|....|....|....|.value = 0
    //    //....|....|....|....|....|....|.valuetype = INTEGER
    //    //....|....|....|....|.ELEMENT: operation
    //    //....|....|....|....|...id = 9
    //    //....|....|....|....|...optype = notequal
    //    //....|....|....|....|.[
    //    //....|....|....|....|.....ELEMENT: columnref
    //    //....|....|....|....|....|..alias = BETA
    //    //....|....|....|....|....|..column = BETA
    //    //....|....|....|....|....|..id = 2
    //    //....|....|....|....|....|..index = 1
    //    //....|....|....|....|....|..table = ALPHA
    //    //....|....|....|....|.....ELEMENT: value
    //    //....|....|....|....|....|..id = 8
    //    //....|....|....|....|....|..value = 1
    //    //....|....|....|....|....|..valuetype = INTEGER
    //    //
    //    //
    //
    //
    @SuppressWarnings("unchecked")
    @Test
    public void TestPrecedenceAndOr() throws Exception {
        String sql    = "select * from alpha where id != 0 and beta != 0 or beta != 1";
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
                        withAttribute(8, "table", "ALPHA")),
                    withChildNamed(9, "columnref",
                                   "alias", "BETA",
                                   "column", "BETA",
                                   "table", "ALPHA",
                        withAttribute(10, "alias", "BETA"),
                        withAttribute(11, "column", "BETA"),
                        withIdAttribute(12, idTable),
                        withAttribute(13, "index", "1"),
                        withAttribute(14, "table", "ALPHA"))),
                withChildNamed(15, "parameters"),
                withChildNamed(16, "tablescans",
                    withChildNamed(17, "tablescan",
                        withAttribute(18, "jointype", "inner"),
                        withAttribute(19, "table", "ALPHA"),
                        withChildNamed(20, "joincond",
                            withChildNamed(21, "operation",
                                           "optype", "or",
                                withIdAttribute(22, idTable),
                                withAttribute(23, "optype", "or"),
                                withChildNamed(24, "operation",
                                               "optype", "and",
                                    withIdAttribute(25, idTable),
                                    withAttribute(26, "optype", "and"),
                                    withChildNamed(27, "operation",
                                                   "optype", "notequal",
                                        withIdAttribute(28, idTable),
                                        withAttribute(29, "optype", "notequal"),
                                        withChildNamed(30, "columnref",
                                                       "alias", "ID",
                                                       "column", "ID",
                                                       "table", "ALPHA",
                                            withAttribute(31, "alias", "ID"),
                                            withAttribute(32, "column", "ID"),
                                            withIdAttribute(33, idTable),
                                            withAttribute(34, "index", "0"),
                                            withAttribute(35, "table", "ALPHA")),
                                        withChildNamed(36, "value",
                                            withIdAttribute(37, idTable),
                                            withAttribute(38, "value", "0"),
                                            withAttribute(39, "valuetype", "INTEGER"))),
                                    withChildNamed(40, "operation",
                                                   "optype", "notequal",
                                        withIdAttribute(41, idTable),
                                        withAttribute(42, "optype", "notequal"),
                                        withChildNamed(43, "columnref",
                                                       "alias", "BETA",
                                                       "column", "BETA",
                                                       "table", "ALPHA",
                                            withAttribute(44, "alias", "BETA"),
                                            withAttribute(45, "column", "BETA"),
                                            withIdAttribute(46, idTable),
                                            withAttribute(47, "index", "1"),
                                            withAttribute(48, "table", "ALPHA")),
                                        withChildNamed(49, "value",
                                            withIdAttribute(50, idTable),
                                            withAttribute(51, "value", "0"),
                                            withAttribute(52, "valuetype", "INTEGER")))),
                                withChildNamed(53, "operation",
                                               "optype", "notequal",
                                    withIdAttribute(54, idTable),
                                    withAttribute(55, "optype", "notequal"),
                                    withChildNamed(56, "columnref",
                                                   "alias", "BETA",
                                                   "column", "BETA",
                                                   "table", "ALPHA",
                                        withAttribute(57, "alias", "BETA"),
                                        withAttribute(58, "column", "BETA"),
                                        withIdAttribute(59, idTable),
                                        withAttribute(60, "index", "1"),
                                        withAttribute(61, "table", "ALPHA")),
                                    withChildNamed(62, "value",
                                        withIdAttribute(63, idTable),
                                        withAttribute(64, "value", "1"),
                                        withAttribute(65, "valuetype", "INTEGER"))))))));
    }
}

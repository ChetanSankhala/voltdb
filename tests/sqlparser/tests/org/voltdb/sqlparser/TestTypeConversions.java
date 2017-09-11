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

public class TestTypeConversions {
    HSQLInterface m_HSQLInterface = null;
    String        m_schema = null;
    public TestTypeConversions() {
        m_HSQLInterface = HSQLInterface.loadHsqldb(ParameterizationInfo.getParamStateManager());
        String m_schema = "create table alltypes (ftinyint tinyint, fsmallint smallint, finteger integer, fbigint bigint, ffloat float, fdecimal decimal, ftimestamp timestamp)";
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
    //    //....|....|.alias = FTINYINT
    //    //....|....|.column = FTINYINT
    //    //....|....|.id = 1
    //    //....|....|.index = 0
    //    //....|....|.table = ALLTYPES
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = FSMALLINT
    //    //....|....|.column = FSMALLINT
    //    //....|....|.id = 2
    //    //....|....|.index = 1
    //    //....|....|.table = ALLTYPES
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = FINTEGER
    //    //....|....|.column = FINTEGER
    //    //....|....|.id = 3
    //    //....|....|.index = 2
    //    //....|....|.table = ALLTYPES
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = FBIGINT
    //    //....|....|.column = FBIGINT
    //    //....|....|.id = 4
    //    //....|....|.index = 3
    //    //....|....|.table = ALLTYPES
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = FFLOAT
    //    //....|....|.column = FFLOAT
    //    //....|....|.id = 5
    //    //....|....|.index = 4
    //    //....|....|.table = ALLTYPES
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = FDECIMAL
    //    //....|....|.column = FDECIMAL
    //    //....|....|.id = 6
    //    //....|....|.index = 5
    //    //....|....|.table = ALLTYPES
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = FTIMESTAMP
    //    //....|....|.column = FTIMESTAMP
    //    //....|....|.id = 7
    //    //....|....|.index = 6
    //    //....|....|.table = ALLTYPES
    //    //.....ELEMENT: parameters
    //    //.....ELEMENT: tablescans
    //    //.....[
    //    //....|....ELEMENT: tablescan
    //    //....|....|.jointype = inner
    //    //....|....|.table = ALLTYPES
    //    //....|....[
    //    //....|....|...ELEMENT: joincond
    //    //....|....|...[
    //    //....|....|....|..ELEMENT: operation
    //    //....|....|....|....id = 10
    //    //....|....|....|....optype = equal
    //    //....|....|....|..[
    //    //....|....|....|....|.ELEMENT: operation
    //    //....|....|....|....|...id = 8
    //    //....|....|....|....|...optype = add
    //    //....|....|....|....|.[
    //    //....|....|....|....|.....ELEMENT: columnref
    //    //....|....|....|....|....|..alias = FSMALLINT
    //    //....|....|....|....|....|..column = FSMALLINT
    //    //....|....|....|....|....|..id = 2
    //    //....|....|....|....|....|..index = 1
    //    //....|....|....|....|....|..table = ALLTYPES
    //    //....|....|....|....|.....ELEMENT: columnref
    //    //....|....|....|....|....|..alias = FTINYINT
    //    //....|....|....|....|....|..column = FTINYINT
    //    //....|....|....|....|....|..id = 1
    //    //....|....|....|....|....|..index = 0
    //    //....|....|....|....|....|..table = ALLTYPES
    //    //....|....|....|....|.ELEMENT: value
    //    //....|....|....|....|...id = 9
    //    //....|....|....|....|...value = 0
    //    //....|....|....|....|...valuetype = INTEGER
    //    //
    //    //
    //
    //
    @SuppressWarnings("unchecked")
    @Test
    public void TestTISI() throws Exception {
        String sql    = "select * from alltypes where fsmallint + ftinyint  = 0";
        IDTable idTable = new IDTable();
        VoltXMLElement element = m_HSQLInterface.getVoltXMLFromSQLUsingVoltSQLParser(sql, null, SQLKind.DQL);
        assertThat(element)
            .hasName(1, "select")
            .hasAllOf(
                withChildNamed(2, "columns",
                    withChildNamed(3, "columnref",
                                   "alias", "FTINYINT",
                                   "column", "FTINYINT",
                                   "table", "ALLTYPES",
                        withAttribute(4, "alias", "FTINYINT"),
                        withAttribute(5, "column", "FTINYINT"),
                        withIdAttribute(6, idTable),
                        withAttribute(7, "index", "0"),
                        withAttribute(8, "table", "ALLTYPES")),
                    withChildNamed(9, "columnref",
                                   "alias", "FSMALLINT",
                                   "column", "FSMALLINT",
                                   "table", "ALLTYPES",
                        withAttribute(10, "alias", "FSMALLINT"),
                        withAttribute(11, "column", "FSMALLINT"),
                        withIdAttribute(12, idTable),
                        withAttribute(13, "index", "1"),
                        withAttribute(14, "table", "ALLTYPES")),
                    withChildNamed(15, "columnref",
                                   "alias", "FINTEGER",
                                   "column", "FINTEGER",
                                   "table", "ALLTYPES",
                        withAttribute(16, "alias", "FINTEGER"),
                        withAttribute(17, "column", "FINTEGER"),
                        withIdAttribute(18, idTable),
                        withAttribute(19, "index", "2"),
                        withAttribute(20, "table", "ALLTYPES")),
                    withChildNamed(21, "columnref",
                                   "alias", "FBIGINT",
                                   "column", "FBIGINT",
                                   "table", "ALLTYPES",
                        withAttribute(22, "alias", "FBIGINT"),
                        withAttribute(23, "column", "FBIGINT"),
                        withIdAttribute(24, idTable),
                        withAttribute(25, "index", "3"),
                        withAttribute(26, "table", "ALLTYPES")),
                    withChildNamed(27, "columnref",
                                   "alias", "FFLOAT",
                                   "column", "FFLOAT",
                                   "table", "ALLTYPES",
                        withAttribute(28, "alias", "FFLOAT"),
                        withAttribute(29, "column", "FFLOAT"),
                        withIdAttribute(30, idTable),
                        withAttribute(31, "index", "4"),
                        withAttribute(32, "table", "ALLTYPES")),
                    withChildNamed(33, "columnref",
                                   "alias", "FDECIMAL",
                                   "column", "FDECIMAL",
                                   "table", "ALLTYPES",
                        withAttribute(34, "alias", "FDECIMAL"),
                        withAttribute(35, "column", "FDECIMAL"),
                        withIdAttribute(36, idTable),
                        withAttribute(37, "index", "5"),
                        withAttribute(38, "table", "ALLTYPES")),
                    withChildNamed(39, "columnref",
                                   "alias", "FTIMESTAMP",
                                   "column", "FTIMESTAMP",
                                   "table", "ALLTYPES",
                        withAttribute(40, "alias", "FTIMESTAMP"),
                        withAttribute(41, "column", "FTIMESTAMP"),
                        withIdAttribute(42, idTable),
                        withAttribute(43, "index", "6"),
                        withAttribute(44, "table", "ALLTYPES"))),
                withChildNamed(45, "parameters"),
                withChildNamed(46, "tablescans",
                    withChildNamed(47, "tablescan",
                        withAttribute(48, "jointype", "inner"),
                        withAttribute(49, "table", "ALLTYPES"),
                        withChildNamed(50, "joincond",
                            withChildNamed(51, "operation",
                                           "optype", "equal",
                                withIdAttribute(52, idTable),
                                withAttribute(53, "optype", "equal"),
                                withChildNamed(54, "operation",
                                               "optype", "add",
                                    withIdAttribute(55, idTable),
                                    withAttribute(56, "optype", "add"),
                                    withChildNamed(57, "columnref",
                                                   "alias", "FSMALLINT",
                                                   "column", "FSMALLINT",
                                                   "table", "ALLTYPES",
                                        withAttribute(58, "alias", "FSMALLINT"),
                                        withAttribute(59, "column", "FSMALLINT"),
                                        withIdAttribute(60, idTable),
                                        withAttribute(61, "index", "1"),
                                        withAttribute(62, "table", "ALLTYPES")),
                                    withChildNamed(63, "columnref",
                                                   "alias", "FTINYINT",
                                                   "column", "FTINYINT",
                                                   "table", "ALLTYPES",
                                        withAttribute(64, "alias", "FTINYINT"),
                                        withAttribute(65, "column", "FTINYINT"),
                                        withIdAttribute(66, idTable),
                                        withAttribute(67, "index", "0"),
                                        withAttribute(68, "table", "ALLTYPES"))),
                                withChildNamed(69, "value",
                                    withIdAttribute(70, idTable),
                                    withAttribute(71, "value", "0"),
                                    withAttribute(72, "valuetype", "INTEGER")))))));
    }

    // Pattern XML:
    //    //.ELEMENT: select
    //    //.[
    //    //.....ELEMENT: columns
    //    //.....[
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = FTINYINT
    //    //....|....|.column = FTINYINT
    //    //....|....|.id = 1
    //    //....|....|.index = 0
    //    //....|....|.table = ALLTYPES
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = FSMALLINT
    //    //....|....|.column = FSMALLINT
    //    //....|....|.id = 2
    //    //....|....|.index = 1
    //    //....|....|.table = ALLTYPES
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = FINTEGER
    //    //....|....|.column = FINTEGER
    //    //....|....|.id = 3
    //    //....|....|.index = 2
    //    //....|....|.table = ALLTYPES
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = FBIGINT
    //    //....|....|.column = FBIGINT
    //    //....|....|.id = 4
    //    //....|....|.index = 3
    //    //....|....|.table = ALLTYPES
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = FFLOAT
    //    //....|....|.column = FFLOAT
    //    //....|....|.id = 5
    //    //....|....|.index = 4
    //    //....|....|.table = ALLTYPES
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = FDECIMAL
    //    //....|....|.column = FDECIMAL
    //    //....|....|.id = 6
    //    //....|....|.index = 5
    //    //....|....|.table = ALLTYPES
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = FTIMESTAMP
    //    //....|....|.column = FTIMESTAMP
    //    //....|....|.id = 7
    //    //....|....|.index = 6
    //    //....|....|.table = ALLTYPES
    //    //.....ELEMENT: parameters
    //    //.....ELEMENT: tablescans
    //    //.....[
    //    //....|....ELEMENT: tablescan
    //    //....|....|.jointype = inner
    //    //....|....|.table = ALLTYPES
    //    //....|....[
    //    //....|....|...ELEMENT: joincond
    //    //....|....|...[
    //    //....|....|....|..ELEMENT: operation
    //    //....|....|....|....id = 10
    //    //....|....|....|....optype = equal
    //    //....|....|....|..[
    //    //....|....|....|....|.ELEMENT: operation
    //    //....|....|....|....|...id = 8
    //    //....|....|....|....|...optype = add
    //    //....|....|....|....|.[
    //    //....|....|....|....|.....ELEMENT: columnref
    //    //....|....|....|....|....|..alias = FTINYINT
    //    //....|....|....|....|....|..column = FTINYINT
    //    //....|....|....|....|....|..id = 1
    //    //....|....|....|....|....|..index = 0
    //    //....|....|....|....|....|..table = ALLTYPES
    //    //....|....|....|....|.....ELEMENT: columnref
    //    //....|....|....|....|....|..alias = FSMALLINT
    //    //....|....|....|....|....|..column = FSMALLINT
    //    //....|....|....|....|....|..id = 2
    //    //....|....|....|....|....|..index = 1
    //    //....|....|....|....|....|..table = ALLTYPES
    //    //....|....|....|....|.ELEMENT: value
    //    //....|....|....|....|...id = 9
    //    //....|....|....|....|...value = 0
    //    //....|....|....|....|...valuetype = INTEGER
    //    //
    //    //
    //
    //
    @SuppressWarnings("unchecked")
    @Test
    public void TestSITI() throws Exception {
        String sql    = "select * from alltypes where ftinyint  + fsmallint = 0";
        IDTable idTable = new IDTable();
        VoltXMLElement element = m_HSQLInterface.getVoltXMLFromSQLUsingVoltSQLParser(sql, null, SQLKind.DQL);
        assertThat(element)
            .hasName(1, "select")
            .hasAllOf(
                withChildNamed(2, "columns",
                    withChildNamed(3, "columnref",
                                   "alias", "FTINYINT",
                                   "column", "FTINYINT",
                                   "table", "ALLTYPES",
                        withAttribute(4, "alias", "FTINYINT"),
                        withAttribute(5, "column", "FTINYINT"),
                        withIdAttribute(6, idTable),
                        withAttribute(7, "index", "0"),
                        withAttribute(8, "table", "ALLTYPES")),
                    withChildNamed(9, "columnref",
                                   "alias", "FSMALLINT",
                                   "column", "FSMALLINT",
                                   "table", "ALLTYPES",
                        withAttribute(10, "alias", "FSMALLINT"),
                        withAttribute(11, "column", "FSMALLINT"),
                        withIdAttribute(12, idTable),
                        withAttribute(13, "index", "1"),
                        withAttribute(14, "table", "ALLTYPES")),
                    withChildNamed(15, "columnref",
                                   "alias", "FINTEGER",
                                   "column", "FINTEGER",
                                   "table", "ALLTYPES",
                        withAttribute(16, "alias", "FINTEGER"),
                        withAttribute(17, "column", "FINTEGER"),
                        withIdAttribute(18, idTable),
                        withAttribute(19, "index", "2"),
                        withAttribute(20, "table", "ALLTYPES")),
                    withChildNamed(21, "columnref",
                                   "alias", "FBIGINT",
                                   "column", "FBIGINT",
                                   "table", "ALLTYPES",
                        withAttribute(22, "alias", "FBIGINT"),
                        withAttribute(23, "column", "FBIGINT"),
                        withIdAttribute(24, idTable),
                        withAttribute(25, "index", "3"),
                        withAttribute(26, "table", "ALLTYPES")),
                    withChildNamed(27, "columnref",
                                   "alias", "FFLOAT",
                                   "column", "FFLOAT",
                                   "table", "ALLTYPES",
                        withAttribute(28, "alias", "FFLOAT"),
                        withAttribute(29, "column", "FFLOAT"),
                        withIdAttribute(30, idTable),
                        withAttribute(31, "index", "4"),
                        withAttribute(32, "table", "ALLTYPES")),
                    withChildNamed(33, "columnref",
                                   "alias", "FDECIMAL",
                                   "column", "FDECIMAL",
                                   "table", "ALLTYPES",
                        withAttribute(34, "alias", "FDECIMAL"),
                        withAttribute(35, "column", "FDECIMAL"),
                        withIdAttribute(36, idTable),
                        withAttribute(37, "index", "5"),
                        withAttribute(38, "table", "ALLTYPES")),
                    withChildNamed(39, "columnref",
                                   "alias", "FTIMESTAMP",
                                   "column", "FTIMESTAMP",
                                   "table", "ALLTYPES",
                        withAttribute(40, "alias", "FTIMESTAMP"),
                        withAttribute(41, "column", "FTIMESTAMP"),
                        withIdAttribute(42, idTable),
                        withAttribute(43, "index", "6"),
                        withAttribute(44, "table", "ALLTYPES"))),
                withChildNamed(45, "parameters"),
                withChildNamed(46, "tablescans",
                    withChildNamed(47, "tablescan",
                        withAttribute(48, "jointype", "inner"),
                        withAttribute(49, "table", "ALLTYPES"),
                        withChildNamed(50, "joincond",
                            withChildNamed(51, "operation",
                                           "optype", "equal",
                                withIdAttribute(52, idTable),
                                withAttribute(53, "optype", "equal"),
                                withChildNamed(54, "operation",
                                               "optype", "add",
                                    withIdAttribute(55, idTable),
                                    withAttribute(56, "optype", "add"),
                                    withChildNamed(57, "columnref",
                                                   "alias", "FTINYINT",
                                                   "column", "FTINYINT",
                                                   "table", "ALLTYPES",
                                        withAttribute(58, "alias", "FTINYINT"),
                                        withAttribute(59, "column", "FTINYINT"),
                                        withIdAttribute(60, idTable),
                                        withAttribute(61, "index", "0"),
                                        withAttribute(62, "table", "ALLTYPES")),
                                    withChildNamed(63, "columnref",
                                                   "alias", "FSMALLINT",
                                                   "column", "FSMALLINT",
                                                   "table", "ALLTYPES",
                                        withAttribute(64, "alias", "FSMALLINT"),
                                        withAttribute(65, "column", "FSMALLINT"),
                                        withIdAttribute(66, idTable),
                                        withAttribute(67, "index", "1"),
                                        withAttribute(68, "table", "ALLTYPES"))),
                                withChildNamed(69, "value",
                                    withIdAttribute(70, idTable),
                                    withAttribute(71, "value", "0"),
                                    withAttribute(72, "valuetype", "INTEGER")))))));
    }

    // Pattern XML:
    //    //.ELEMENT: select
    //    //.[
    //    //.....ELEMENT: columns
    //    //.....[
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = FTINYINT
    //    //....|....|.column = FTINYINT
    //    //....|....|.id = 1
    //    //....|....|.index = 0
    //    //....|....|.table = ALLTYPES
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = FSMALLINT
    //    //....|....|.column = FSMALLINT
    //    //....|....|.id = 2
    //    //....|....|.index = 1
    //    //....|....|.table = ALLTYPES
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = FINTEGER
    //    //....|....|.column = FINTEGER
    //    //....|....|.id = 3
    //    //....|....|.index = 2
    //    //....|....|.table = ALLTYPES
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = FBIGINT
    //    //....|....|.column = FBIGINT
    //    //....|....|.id = 4
    //    //....|....|.index = 3
    //    //....|....|.table = ALLTYPES
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = FFLOAT
    //    //....|....|.column = FFLOAT
    //    //....|....|.id = 5
    //    //....|....|.index = 4
    //    //....|....|.table = ALLTYPES
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = FDECIMAL
    //    //....|....|.column = FDECIMAL
    //    //....|....|.id = 6
    //    //....|....|.index = 5
    //    //....|....|.table = ALLTYPES
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = FTIMESTAMP
    //    //....|....|.column = FTIMESTAMP
    //    //....|....|.id = 7
    //    //....|....|.index = 6
    //    //....|....|.table = ALLTYPES
    //    //.....ELEMENT: parameters
    //    //.....ELEMENT: tablescans
    //    //.....[
    //    //....|....ELEMENT: tablescan
    //    //....|....|.jointype = inner
    //    //....|....|.table = ALLTYPES
    //    //....|....[
    //    //....|....|...ELEMENT: joincond
    //    //....|....|...[
    //    //....|....|....|..ELEMENT: operation
    //    //....|....|....|....id = 10
    //    //....|....|....|....optype = equal
    //    //....|....|....|..[
    //    //....|....|....|....|.ELEMENT: operation
    //    //....|....|....|....|...id = 8
    //    //....|....|....|....|...optype = add
    //    //....|....|....|....|.[
    //    //....|....|....|....|.....ELEMENT: columnref
    //    //....|....|....|....|....|..alias = FTINYINT
    //    //....|....|....|....|....|..column = FTINYINT
    //    //....|....|....|....|....|..id = 1
    //    //....|....|....|....|....|..index = 0
    //    //....|....|....|....|....|..table = ALLTYPES
    //    //....|....|....|....|.....ELEMENT: columnref
    //    //....|....|....|....|....|..alias = FINTEGER
    //    //....|....|....|....|....|..column = FINTEGER
    //    //....|....|....|....|....|..id = 3
    //    //....|....|....|....|....|..index = 2
    //    //....|....|....|....|....|..table = ALLTYPES
    //    //....|....|....|....|.ELEMENT: value
    //    //....|....|....|....|...id = 9
    //    //....|....|....|....|...value = 0
    //    //....|....|....|....|...valuetype = INTEGER
    //    //
    //    //
    //
    //
    @SuppressWarnings("unchecked")
    @Test
    public void TestTII() throws Exception {
        String sql    = "select * from alltypes where ftinyint  + finteger  = 0";
        IDTable idTable = new IDTable();
        VoltXMLElement element = m_HSQLInterface.getVoltXMLFromSQLUsingVoltSQLParser(sql, null, SQLKind.DQL);
        assertThat(element)
            .hasName(1, "select")
            .hasAllOf(
                withChildNamed(2, "columns",
                    withChildNamed(3, "columnref",
                                   "alias", "FTINYINT",
                                   "column", "FTINYINT",
                                   "table", "ALLTYPES",
                        withAttribute(4, "alias", "FTINYINT"),
                        withAttribute(5, "column", "FTINYINT"),
                        withIdAttribute(6, idTable),
                        withAttribute(7, "index", "0"),
                        withAttribute(8, "table", "ALLTYPES")),
                    withChildNamed(9, "columnref",
                                   "alias", "FSMALLINT",
                                   "column", "FSMALLINT",
                                   "table", "ALLTYPES",
                        withAttribute(10, "alias", "FSMALLINT"),
                        withAttribute(11, "column", "FSMALLINT"),
                        withIdAttribute(12, idTable),
                        withAttribute(13, "index", "1"),
                        withAttribute(14, "table", "ALLTYPES")),
                    withChildNamed(15, "columnref",
                                   "alias", "FINTEGER",
                                   "column", "FINTEGER",
                                   "table", "ALLTYPES",
                        withAttribute(16, "alias", "FINTEGER"),
                        withAttribute(17, "column", "FINTEGER"),
                        withIdAttribute(18, idTable),
                        withAttribute(19, "index", "2"),
                        withAttribute(20, "table", "ALLTYPES")),
                    withChildNamed(21, "columnref",
                                   "alias", "FBIGINT",
                                   "column", "FBIGINT",
                                   "table", "ALLTYPES",
                        withAttribute(22, "alias", "FBIGINT"),
                        withAttribute(23, "column", "FBIGINT"),
                        withIdAttribute(24, idTable),
                        withAttribute(25, "index", "3"),
                        withAttribute(26, "table", "ALLTYPES")),
                    withChildNamed(27, "columnref",
                                   "alias", "FFLOAT",
                                   "column", "FFLOAT",
                                   "table", "ALLTYPES",
                        withAttribute(28, "alias", "FFLOAT"),
                        withAttribute(29, "column", "FFLOAT"),
                        withIdAttribute(30, idTable),
                        withAttribute(31, "index", "4"),
                        withAttribute(32, "table", "ALLTYPES")),
                    withChildNamed(33, "columnref",
                                   "alias", "FDECIMAL",
                                   "column", "FDECIMAL",
                                   "table", "ALLTYPES",
                        withAttribute(34, "alias", "FDECIMAL"),
                        withAttribute(35, "column", "FDECIMAL"),
                        withIdAttribute(36, idTable),
                        withAttribute(37, "index", "5"),
                        withAttribute(38, "table", "ALLTYPES")),
                    withChildNamed(39, "columnref",
                                   "alias", "FTIMESTAMP",
                                   "column", "FTIMESTAMP",
                                   "table", "ALLTYPES",
                        withAttribute(40, "alias", "FTIMESTAMP"),
                        withAttribute(41, "column", "FTIMESTAMP"),
                        withIdAttribute(42, idTable),
                        withAttribute(43, "index", "6"),
                        withAttribute(44, "table", "ALLTYPES"))),
                withChildNamed(45, "parameters"),
                withChildNamed(46, "tablescans",
                    withChildNamed(47, "tablescan",
                        withAttribute(48, "jointype", "inner"),
                        withAttribute(49, "table", "ALLTYPES"),
                        withChildNamed(50, "joincond",
                            withChildNamed(51, "operation",
                                           "optype", "equal",
                                withIdAttribute(52, idTable),
                                withAttribute(53, "optype", "equal"),
                                withChildNamed(54, "operation",
                                               "optype", "add",
                                    withIdAttribute(55, idTable),
                                    withAttribute(56, "optype", "add"),
                                    withChildNamed(57, "columnref",
                                                   "alias", "FTINYINT",
                                                   "column", "FTINYINT",
                                                   "table", "ALLTYPES",
                                        withAttribute(58, "alias", "FTINYINT"),
                                        withAttribute(59, "column", "FTINYINT"),
                                        withIdAttribute(60, idTable),
                                        withAttribute(61, "index", "0"),
                                        withAttribute(62, "table", "ALLTYPES")),
                                    withChildNamed(63, "columnref",
                                                   "alias", "FINTEGER",
                                                   "column", "FINTEGER",
                                                   "table", "ALLTYPES",
                                        withAttribute(64, "alias", "FINTEGER"),
                                        withAttribute(65, "column", "FINTEGER"),
                                        withIdAttribute(66, idTable),
                                        withAttribute(67, "index", "2"),
                                        withAttribute(68, "table", "ALLTYPES"))),
                                withChildNamed(69, "value",
                                    withIdAttribute(70, idTable),
                                    withAttribute(71, "value", "0"),
                                    withAttribute(72, "valuetype", "INTEGER")))))));
    }

    // Pattern XML:
    //    //.ELEMENT: select
    //    //.[
    //    //.....ELEMENT: columns
    //    //.....[
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = FTINYINT
    //    //....|....|.column = FTINYINT
    //    //....|....|.id = 1
    //    //....|....|.index = 0
    //    //....|....|.table = ALLTYPES
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = FSMALLINT
    //    //....|....|.column = FSMALLINT
    //    //....|....|.id = 2
    //    //....|....|.index = 1
    //    //....|....|.table = ALLTYPES
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = FINTEGER
    //    //....|....|.column = FINTEGER
    //    //....|....|.id = 3
    //    //....|....|.index = 2
    //    //....|....|.table = ALLTYPES
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = FBIGINT
    //    //....|....|.column = FBIGINT
    //    //....|....|.id = 4
    //    //....|....|.index = 3
    //    //....|....|.table = ALLTYPES
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = FFLOAT
    //    //....|....|.column = FFLOAT
    //    //....|....|.id = 5
    //    //....|....|.index = 4
    //    //....|....|.table = ALLTYPES
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = FDECIMAL
    //    //....|....|.column = FDECIMAL
    //    //....|....|.id = 6
    //    //....|....|.index = 5
    //    //....|....|.table = ALLTYPES
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = FTIMESTAMP
    //    //....|....|.column = FTIMESTAMP
    //    //....|....|.id = 7
    //    //....|....|.index = 6
    //    //....|....|.table = ALLTYPES
    //    //.....ELEMENT: parameters
    //    //.....ELEMENT: tablescans
    //    //.....[
    //    //....|....ELEMENT: tablescan
    //    //....|....|.jointype = inner
    //    //....|....|.table = ALLTYPES
    //    //....|....[
    //    //....|....|...ELEMENT: joincond
    //    //....|....|...[
    //    //....|....|....|..ELEMENT: operation
    //    //....|....|....|....id = 10
    //    //....|....|....|....optype = equal
    //    //....|....|....|..[
    //    //....|....|....|....|.ELEMENT: operation
    //    //....|....|....|....|...id = 8
    //    //....|....|....|....|...optype = add
    //    //....|....|....|....|.[
    //    //....|....|....|....|.....ELEMENT: columnref
    //    //....|....|....|....|....|..alias = FINTEGER
    //    //....|....|....|....|....|..column = FINTEGER
    //    //....|....|....|....|....|..id = 3
    //    //....|....|....|....|....|..index = 2
    //    //....|....|....|....|....|..table = ALLTYPES
    //    //....|....|....|....|.....ELEMENT: columnref
    //    //....|....|....|....|....|..alias = FTINYINT
    //    //....|....|....|....|....|..column = FTINYINT
    //    //....|....|....|....|....|..id = 1
    //    //....|....|....|....|....|..index = 0
    //    //....|....|....|....|....|..table = ALLTYPES
    //    //....|....|....|....|.ELEMENT: value
    //    //....|....|....|....|...id = 9
    //    //....|....|....|....|...value = 0
    //    //....|....|....|....|...valuetype = INTEGER
    //    //
    //    //
    //
    //
    @SuppressWarnings("unchecked")
    @Test
    public void TestITI() throws Exception {
        String sql    = "select * from alltypes where finteger  + ftinyint  = 0";
        IDTable idTable = new IDTable();
        VoltXMLElement element = m_HSQLInterface.getVoltXMLFromSQLUsingVoltSQLParser(sql, null, SQLKind.DQL);
        assertThat(element)
            .hasName(1, "select")
            .hasAllOf(
                withChildNamed(2, "columns",
                    withChildNamed(3, "columnref",
                                   "alias", "FTINYINT",
                                   "column", "FTINYINT",
                                   "table", "ALLTYPES",
                        withAttribute(4, "alias", "FTINYINT"),
                        withAttribute(5, "column", "FTINYINT"),
                        withIdAttribute(6, idTable),
                        withAttribute(7, "index", "0"),
                        withAttribute(8, "table", "ALLTYPES")),
                    withChildNamed(9, "columnref",
                                   "alias", "FSMALLINT",
                                   "column", "FSMALLINT",
                                   "table", "ALLTYPES",
                        withAttribute(10, "alias", "FSMALLINT"),
                        withAttribute(11, "column", "FSMALLINT"),
                        withIdAttribute(12, idTable),
                        withAttribute(13, "index", "1"),
                        withAttribute(14, "table", "ALLTYPES")),
                    withChildNamed(15, "columnref",
                                   "alias", "FINTEGER",
                                   "column", "FINTEGER",
                                   "table", "ALLTYPES",
                        withAttribute(16, "alias", "FINTEGER"),
                        withAttribute(17, "column", "FINTEGER"),
                        withIdAttribute(18, idTable),
                        withAttribute(19, "index", "2"),
                        withAttribute(20, "table", "ALLTYPES")),
                    withChildNamed(21, "columnref",
                                   "alias", "FBIGINT",
                                   "column", "FBIGINT",
                                   "table", "ALLTYPES",
                        withAttribute(22, "alias", "FBIGINT"),
                        withAttribute(23, "column", "FBIGINT"),
                        withIdAttribute(24, idTable),
                        withAttribute(25, "index", "3"),
                        withAttribute(26, "table", "ALLTYPES")),
                    withChildNamed(27, "columnref",
                                   "alias", "FFLOAT",
                                   "column", "FFLOAT",
                                   "table", "ALLTYPES",
                        withAttribute(28, "alias", "FFLOAT"),
                        withAttribute(29, "column", "FFLOAT"),
                        withIdAttribute(30, idTable),
                        withAttribute(31, "index", "4"),
                        withAttribute(32, "table", "ALLTYPES")),
                    withChildNamed(33, "columnref",
                                   "alias", "FDECIMAL",
                                   "column", "FDECIMAL",
                                   "table", "ALLTYPES",
                        withAttribute(34, "alias", "FDECIMAL"),
                        withAttribute(35, "column", "FDECIMAL"),
                        withIdAttribute(36, idTable),
                        withAttribute(37, "index", "5"),
                        withAttribute(38, "table", "ALLTYPES")),
                    withChildNamed(39, "columnref",
                                   "alias", "FTIMESTAMP",
                                   "column", "FTIMESTAMP",
                                   "table", "ALLTYPES",
                        withAttribute(40, "alias", "FTIMESTAMP"),
                        withAttribute(41, "column", "FTIMESTAMP"),
                        withIdAttribute(42, idTable),
                        withAttribute(43, "index", "6"),
                        withAttribute(44, "table", "ALLTYPES"))),
                withChildNamed(45, "parameters"),
                withChildNamed(46, "tablescans",
                    withChildNamed(47, "tablescan",
                        withAttribute(48, "jointype", "inner"),
                        withAttribute(49, "table", "ALLTYPES"),
                        withChildNamed(50, "joincond",
                            withChildNamed(51, "operation",
                                           "optype", "equal",
                                withIdAttribute(52, idTable),
                                withAttribute(53, "optype", "equal"),
                                withChildNamed(54, "operation",
                                               "optype", "add",
                                    withIdAttribute(55, idTable),
                                    withAttribute(56, "optype", "add"),
                                    withChildNamed(57, "columnref",
                                                   "alias", "FINTEGER",
                                                   "column", "FINTEGER",
                                                   "table", "ALLTYPES",
                                        withAttribute(58, "alias", "FINTEGER"),
                                        withAttribute(59, "column", "FINTEGER"),
                                        withIdAttribute(60, idTable),
                                        withAttribute(61, "index", "2"),
                                        withAttribute(62, "table", "ALLTYPES")),
                                    withChildNamed(63, "columnref",
                                                   "alias", "FTINYINT",
                                                   "column", "FTINYINT",
                                                   "table", "ALLTYPES",
                                        withAttribute(64, "alias", "FTINYINT"),
                                        withAttribute(65, "column", "FTINYINT"),
                                        withIdAttribute(66, idTable),
                                        withAttribute(67, "index", "0"),
                                        withAttribute(68, "table", "ALLTYPES"))),
                                withChildNamed(69, "value",
                                    withIdAttribute(70, idTable),
                                    withAttribute(71, "value", "0"),
                                    withAttribute(72, "valuetype", "INTEGER")))))));
    }

    // Pattern XML:
    //    //.ELEMENT: select
    //    //.[
    //    //.....ELEMENT: columns
    //    //.....[
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = FTINYINT
    //    //....|....|.column = FTINYINT
    //    //....|....|.id = 1
    //    //....|....|.index = 0
    //    //....|....|.table = ALLTYPES
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = FSMALLINT
    //    //....|....|.column = FSMALLINT
    //    //....|....|.id = 2
    //    //....|....|.index = 1
    //    //....|....|.table = ALLTYPES
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = FINTEGER
    //    //....|....|.column = FINTEGER
    //    //....|....|.id = 3
    //    //....|....|.index = 2
    //    //....|....|.table = ALLTYPES
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = FBIGINT
    //    //....|....|.column = FBIGINT
    //    //....|....|.id = 4
    //    //....|....|.index = 3
    //    //....|....|.table = ALLTYPES
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = FFLOAT
    //    //....|....|.column = FFLOAT
    //    //....|....|.id = 5
    //    //....|....|.index = 4
    //    //....|....|.table = ALLTYPES
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = FDECIMAL
    //    //....|....|.column = FDECIMAL
    //    //....|....|.id = 6
    //    //....|....|.index = 5
    //    //....|....|.table = ALLTYPES
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = FTIMESTAMP
    //    //....|....|.column = FTIMESTAMP
    //    //....|....|.id = 7
    //    //....|....|.index = 6
    //    //....|....|.table = ALLTYPES
    //    //.....ELEMENT: parameters
    //    //.....ELEMENT: tablescans
    //    //.....[
    //    //....|....ELEMENT: tablescan
    //    //....|....|.jointype = inner
    //    //....|....|.table = ALLTYPES
    //    //....|....[
    //    //....|....|...ELEMENT: joincond
    //    //....|....|...[
    //    //....|....|....|..ELEMENT: operation
    //    //....|....|....|....id = 10
    //    //....|....|....|....optype = equal
    //    //....|....|....|..[
    //    //....|....|....|....|.ELEMENT: operation
    //    //....|....|....|....|...id = 8
    //    //....|....|....|....|...optype = add
    //    //....|....|....|....|.[
    //    //....|....|....|....|.....ELEMENT: columnref
    //    //....|....|....|....|....|..alias = FBIGINT
    //    //....|....|....|....|....|..column = FBIGINT
    //    //....|....|....|....|....|..id = 4
    //    //....|....|....|....|....|..index = 3
    //    //....|....|....|....|....|..table = ALLTYPES
    //    //....|....|....|....|.....ELEMENT: columnref
    //    //....|....|....|....|....|..alias = FTINYINT
    //    //....|....|....|....|....|..column = FTINYINT
    //    //....|....|....|....|....|..id = 1
    //    //....|....|....|....|....|..index = 0
    //    //....|....|....|....|....|..table = ALLTYPES
    //    //....|....|....|....|.ELEMENT: value
    //    //....|....|....|....|...id = 9
    //    //....|....|....|....|...value = 0
    //    //....|....|....|....|...valuetype = INTEGER
    //    //
    //    //
    //
    //
    @SuppressWarnings("unchecked")
    @Test
    public void TestTIBI() throws Exception {
        String sql    = "select * from alltypes where fbigint   + ftinyint  = 0";
        IDTable idTable = new IDTable();
        VoltXMLElement element = m_HSQLInterface.getVoltXMLFromSQLUsingVoltSQLParser(sql, null, SQLKind.DQL);
        assertThat(element)
            .hasName(1, "select")
            .hasAllOf(
                withChildNamed(2, "columns",
                    withChildNamed(3, "columnref",
                                   "alias", "FTINYINT",
                                   "column", "FTINYINT",
                                   "table", "ALLTYPES",
                        withAttribute(4, "alias", "FTINYINT"),
                        withAttribute(5, "column", "FTINYINT"),
                        withIdAttribute(6, idTable),
                        withAttribute(7, "index", "0"),
                        withAttribute(8, "table", "ALLTYPES")),
                    withChildNamed(9, "columnref",
                                   "alias", "FSMALLINT",
                                   "column", "FSMALLINT",
                                   "table", "ALLTYPES",
                        withAttribute(10, "alias", "FSMALLINT"),
                        withAttribute(11, "column", "FSMALLINT"),
                        withIdAttribute(12, idTable),
                        withAttribute(13, "index", "1"),
                        withAttribute(14, "table", "ALLTYPES")),
                    withChildNamed(15, "columnref",
                                   "alias", "FINTEGER",
                                   "column", "FINTEGER",
                                   "table", "ALLTYPES",
                        withAttribute(16, "alias", "FINTEGER"),
                        withAttribute(17, "column", "FINTEGER"),
                        withIdAttribute(18, idTable),
                        withAttribute(19, "index", "2"),
                        withAttribute(20, "table", "ALLTYPES")),
                    withChildNamed(21, "columnref",
                                   "alias", "FBIGINT",
                                   "column", "FBIGINT",
                                   "table", "ALLTYPES",
                        withAttribute(22, "alias", "FBIGINT"),
                        withAttribute(23, "column", "FBIGINT"),
                        withIdAttribute(24, idTable),
                        withAttribute(25, "index", "3"),
                        withAttribute(26, "table", "ALLTYPES")),
                    withChildNamed(27, "columnref",
                                   "alias", "FFLOAT",
                                   "column", "FFLOAT",
                                   "table", "ALLTYPES",
                        withAttribute(28, "alias", "FFLOAT"),
                        withAttribute(29, "column", "FFLOAT"),
                        withIdAttribute(30, idTable),
                        withAttribute(31, "index", "4"),
                        withAttribute(32, "table", "ALLTYPES")),
                    withChildNamed(33, "columnref",
                                   "alias", "FDECIMAL",
                                   "column", "FDECIMAL",
                                   "table", "ALLTYPES",
                        withAttribute(34, "alias", "FDECIMAL"),
                        withAttribute(35, "column", "FDECIMAL"),
                        withIdAttribute(36, idTable),
                        withAttribute(37, "index", "5"),
                        withAttribute(38, "table", "ALLTYPES")),
                    withChildNamed(39, "columnref",
                                   "alias", "FTIMESTAMP",
                                   "column", "FTIMESTAMP",
                                   "table", "ALLTYPES",
                        withAttribute(40, "alias", "FTIMESTAMP"),
                        withAttribute(41, "column", "FTIMESTAMP"),
                        withIdAttribute(42, idTable),
                        withAttribute(43, "index", "6"),
                        withAttribute(44, "table", "ALLTYPES"))),
                withChildNamed(45, "parameters"),
                withChildNamed(46, "tablescans",
                    withChildNamed(47, "tablescan",
                        withAttribute(48, "jointype", "inner"),
                        withAttribute(49, "table", "ALLTYPES"),
                        withChildNamed(50, "joincond",
                            withChildNamed(51, "operation",
                                           "optype", "equal",
                                withIdAttribute(52, idTable),
                                withAttribute(53, "optype", "equal"),
                                withChildNamed(54, "operation",
                                               "optype", "add",
                                    withIdAttribute(55, idTable),
                                    withAttribute(56, "optype", "add"),
                                    withChildNamed(57, "columnref",
                                                   "alias", "FBIGINT",
                                                   "column", "FBIGINT",
                                                   "table", "ALLTYPES",
                                        withAttribute(58, "alias", "FBIGINT"),
                                        withAttribute(59, "column", "FBIGINT"),
                                        withIdAttribute(60, idTable),
                                        withAttribute(61, "index", "3"),
                                        withAttribute(62, "table", "ALLTYPES")),
                                    withChildNamed(63, "columnref",
                                                   "alias", "FTINYINT",
                                                   "column", "FTINYINT",
                                                   "table", "ALLTYPES",
                                        withAttribute(64, "alias", "FTINYINT"),
                                        withAttribute(65, "column", "FTINYINT"),
                                        withIdAttribute(66, idTable),
                                        withAttribute(67, "index", "0"),
                                        withAttribute(68, "table", "ALLTYPES"))),
                                withChildNamed(69, "value",
                                    withIdAttribute(70, idTable),
                                    withAttribute(71, "value", "0"),
                                    withAttribute(72, "valuetype", "INTEGER")))))));
    }

    // Pattern XML:
    //    //.ELEMENT: select
    //    //.[
    //    //.....ELEMENT: columns
    //    //.....[
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = FTINYINT
    //    //....|....|.column = FTINYINT
    //    //....|....|.id = 1
    //    //....|....|.index = 0
    //    //....|....|.table = ALLTYPES
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = FSMALLINT
    //    //....|....|.column = FSMALLINT
    //    //....|....|.id = 2
    //    //....|....|.index = 1
    //    //....|....|.table = ALLTYPES
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = FINTEGER
    //    //....|....|.column = FINTEGER
    //    //....|....|.id = 3
    //    //....|....|.index = 2
    //    //....|....|.table = ALLTYPES
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = FBIGINT
    //    //....|....|.column = FBIGINT
    //    //....|....|.id = 4
    //    //....|....|.index = 3
    //    //....|....|.table = ALLTYPES
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = FFLOAT
    //    //....|....|.column = FFLOAT
    //    //....|....|.id = 5
    //    //....|....|.index = 4
    //    //....|....|.table = ALLTYPES
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = FDECIMAL
    //    //....|....|.column = FDECIMAL
    //    //....|....|.id = 6
    //    //....|....|.index = 5
    //    //....|....|.table = ALLTYPES
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = FTIMESTAMP
    //    //....|....|.column = FTIMESTAMP
    //    //....|....|.id = 7
    //    //....|....|.index = 6
    //    //....|....|.table = ALLTYPES
    //    //.....ELEMENT: parameters
    //    //.....ELEMENT: tablescans
    //    //.....[
    //    //....|....ELEMENT: tablescan
    //    //....|....|.jointype = inner
    //    //....|....|.table = ALLTYPES
    //    //....|....[
    //    //....|....|...ELEMENT: joincond
    //    //....|....|...[
    //    //....|....|....|..ELEMENT: operation
    //    //....|....|....|....id = 10
    //    //....|....|....|....optype = equal
    //    //....|....|....|..[
    //    //....|....|....|....|.ELEMENT: operation
    //    //....|....|....|....|...id = 8
    //    //....|....|....|....|...optype = add
    //    //....|....|....|....|.[
    //    //....|....|....|....|.....ELEMENT: columnref
    //    //....|....|....|....|....|..alias = FTINYINT
    //    //....|....|....|....|....|..column = FTINYINT
    //    //....|....|....|....|....|..id = 1
    //    //....|....|....|....|....|..index = 0
    //    //....|....|....|....|....|..table = ALLTYPES
    //    //....|....|....|....|.....ELEMENT: columnref
    //    //....|....|....|....|....|..alias = FBIGINT
    //    //....|....|....|....|....|..column = FBIGINT
    //    //....|....|....|....|....|..id = 4
    //    //....|....|....|....|....|..index = 3
    //    //....|....|....|....|....|..table = ALLTYPES
    //    //....|....|....|....|.ELEMENT: value
    //    //....|....|....|....|...id = 9
    //    //....|....|....|....|...value = 0
    //    //....|....|....|....|...valuetype = INTEGER
    //    //
    //    //
    //
    //
    @SuppressWarnings("unchecked")
    @Test
    public void TestBITI() throws Exception {
        String sql    = "select * from alltypes where ftinyint  + fbigint   = 0";
        IDTable idTable = new IDTable();
        VoltXMLElement element = m_HSQLInterface.getVoltXMLFromSQLUsingVoltSQLParser(sql, null, SQLKind.DQL);
        assertThat(element)
            .hasName(1, "select")
            .hasAllOf(
                withChildNamed(2, "columns",
                    withChildNamed(3, "columnref",
                                   "alias", "FTINYINT",
                                   "column", "FTINYINT",
                                   "table", "ALLTYPES",
                        withAttribute(4, "alias", "FTINYINT"),
                        withAttribute(5, "column", "FTINYINT"),
                        withIdAttribute(6, idTable),
                        withAttribute(7, "index", "0"),
                        withAttribute(8, "table", "ALLTYPES")),
                    withChildNamed(9, "columnref",
                                   "alias", "FSMALLINT",
                                   "column", "FSMALLINT",
                                   "table", "ALLTYPES",
                        withAttribute(10, "alias", "FSMALLINT"),
                        withAttribute(11, "column", "FSMALLINT"),
                        withIdAttribute(12, idTable),
                        withAttribute(13, "index", "1"),
                        withAttribute(14, "table", "ALLTYPES")),
                    withChildNamed(15, "columnref",
                                   "alias", "FINTEGER",
                                   "column", "FINTEGER",
                                   "table", "ALLTYPES",
                        withAttribute(16, "alias", "FINTEGER"),
                        withAttribute(17, "column", "FINTEGER"),
                        withIdAttribute(18, idTable),
                        withAttribute(19, "index", "2"),
                        withAttribute(20, "table", "ALLTYPES")),
                    withChildNamed(21, "columnref",
                                   "alias", "FBIGINT",
                                   "column", "FBIGINT",
                                   "table", "ALLTYPES",
                        withAttribute(22, "alias", "FBIGINT"),
                        withAttribute(23, "column", "FBIGINT"),
                        withIdAttribute(24, idTable),
                        withAttribute(25, "index", "3"),
                        withAttribute(26, "table", "ALLTYPES")),
                    withChildNamed(27, "columnref",
                                   "alias", "FFLOAT",
                                   "column", "FFLOAT",
                                   "table", "ALLTYPES",
                        withAttribute(28, "alias", "FFLOAT"),
                        withAttribute(29, "column", "FFLOAT"),
                        withIdAttribute(30, idTable),
                        withAttribute(31, "index", "4"),
                        withAttribute(32, "table", "ALLTYPES")),
                    withChildNamed(33, "columnref",
                                   "alias", "FDECIMAL",
                                   "column", "FDECIMAL",
                                   "table", "ALLTYPES",
                        withAttribute(34, "alias", "FDECIMAL"),
                        withAttribute(35, "column", "FDECIMAL"),
                        withIdAttribute(36, idTable),
                        withAttribute(37, "index", "5"),
                        withAttribute(38, "table", "ALLTYPES")),
                    withChildNamed(39, "columnref",
                                   "alias", "FTIMESTAMP",
                                   "column", "FTIMESTAMP",
                                   "table", "ALLTYPES",
                        withAttribute(40, "alias", "FTIMESTAMP"),
                        withAttribute(41, "column", "FTIMESTAMP"),
                        withIdAttribute(42, idTable),
                        withAttribute(43, "index", "6"),
                        withAttribute(44, "table", "ALLTYPES"))),
                withChildNamed(45, "parameters"),
                withChildNamed(46, "tablescans",
                    withChildNamed(47, "tablescan",
                        withAttribute(48, "jointype", "inner"),
                        withAttribute(49, "table", "ALLTYPES"),
                        withChildNamed(50, "joincond",
                            withChildNamed(51, "operation",
                                           "optype", "equal",
                                withIdAttribute(52, idTable),
                                withAttribute(53, "optype", "equal"),
                                withChildNamed(54, "operation",
                                               "optype", "add",
                                    withIdAttribute(55, idTable),
                                    withAttribute(56, "optype", "add"),
                                    withChildNamed(57, "columnref",
                                                   "alias", "FTINYINT",
                                                   "column", "FTINYINT",
                                                   "table", "ALLTYPES",
                                        withAttribute(58, "alias", "FTINYINT"),
                                        withAttribute(59, "column", "FTINYINT"),
                                        withIdAttribute(60, idTable),
                                        withAttribute(61, "index", "0"),
                                        withAttribute(62, "table", "ALLTYPES")),
                                    withChildNamed(63, "columnref",
                                                   "alias", "FBIGINT",
                                                   "column", "FBIGINT",
                                                   "table", "ALLTYPES",
                                        withAttribute(64, "alias", "FBIGINT"),
                                        withAttribute(65, "column", "FBIGINT"),
                                        withIdAttribute(66, idTable),
                                        withAttribute(67, "index", "3"),
                                        withAttribute(68, "table", "ALLTYPES"))),
                                withChildNamed(69, "value",
                                    withIdAttribute(70, idTable),
                                    withAttribute(71, "value", "0"),
                                    withAttribute(72, "valuetype", "INTEGER")))))));
    }

    // Pattern XML:
    //    //.ELEMENT: select
    //    //.[
    //    //.....ELEMENT: columns
    //    //.....[
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = FTINYINT
    //    //....|....|.column = FTINYINT
    //    //....|....|.id = 1
    //    //....|....|.index = 0
    //    //....|....|.table = ALLTYPES
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = FSMALLINT
    //    //....|....|.column = FSMALLINT
    //    //....|....|.id = 2
    //    //....|....|.index = 1
    //    //....|....|.table = ALLTYPES
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = FINTEGER
    //    //....|....|.column = FINTEGER
    //    //....|....|.id = 3
    //    //....|....|.index = 2
    //    //....|....|.table = ALLTYPES
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = FBIGINT
    //    //....|....|.column = FBIGINT
    //    //....|....|.id = 4
    //    //....|....|.index = 3
    //    //....|....|.table = ALLTYPES
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = FFLOAT
    //    //....|....|.column = FFLOAT
    //    //....|....|.id = 5
    //    //....|....|.index = 4
    //    //....|....|.table = ALLTYPES
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = FDECIMAL
    //    //....|....|.column = FDECIMAL
    //    //....|....|.id = 6
    //    //....|....|.index = 5
    //    //....|....|.table = ALLTYPES
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = FTIMESTAMP
    //    //....|....|.column = FTIMESTAMP
    //    //....|....|.id = 7
    //    //....|....|.index = 6
    //    //....|....|.table = ALLTYPES
    //    //.....ELEMENT: parameters
    //    //.....ELEMENT: tablescans
    //    //.....[
    //    //....|....ELEMENT: tablescan
    //    //....|....|.jointype = inner
    //    //....|....|.table = ALLTYPES
    //    //....|....[
    //    //....|....|...ELEMENT: joincond
    //    //....|....|...[
    //    //....|....|....|..ELEMENT: operation
    //    //....|....|....|....id = 10
    //    //....|....|....|....optype = equal
    //    //....|....|....|..[
    //    //....|....|....|....|.ELEMENT: operation
    //    //....|....|....|....|...id = 8
    //    //....|....|....|....|...optype = add
    //    //....|....|....|....|.[
    //    //....|....|....|....|.....ELEMENT: columnref
    //    //....|....|....|....|....|..alias = FFLOAT
    //    //....|....|....|....|....|..column = FFLOAT
    //    //....|....|....|....|....|..id = 5
    //    //....|....|....|....|....|..index = 4
    //    //....|....|....|....|....|..table = ALLTYPES
    //    //....|....|....|....|.....ELEMENT: columnref
    //    //....|....|....|....|....|..alias = FTINYINT
    //    //....|....|....|....|....|..column = FTINYINT
    //    //....|....|....|....|....|..id = 1
    //    //....|....|....|....|....|..index = 0
    //    //....|....|....|....|....|..table = ALLTYPES
    //    //....|....|....|....|.ELEMENT: value
    //    //....|....|....|....|...id = 9
    //    //....|....|....|....|...value = 0
    //    //....|....|....|....|...valuetype = INTEGER
    //    //
    //    //
    //
    //
    @SuppressWarnings("unchecked")
    @Test
    public void TestTIF() throws Exception {
        String sql    = "select * from alltypes where ffloat    + ftinyint  = 0";
        IDTable idTable = new IDTable();
        VoltXMLElement element = m_HSQLInterface.getVoltXMLFromSQLUsingVoltSQLParser(sql, null, SQLKind.DQL);
        assertThat(element)
            .hasName(1, "select")
            .hasAllOf(
                withChildNamed(2, "columns",
                    withChildNamed(3, "columnref",
                                   "alias", "FTINYINT",
                                   "column", "FTINYINT",
                                   "table", "ALLTYPES",
                        withAttribute(4, "alias", "FTINYINT"),
                        withAttribute(5, "column", "FTINYINT"),
                        withIdAttribute(6, idTable),
                        withAttribute(7, "index", "0"),
                        withAttribute(8, "table", "ALLTYPES")),
                    withChildNamed(9, "columnref",
                                   "alias", "FSMALLINT",
                                   "column", "FSMALLINT",
                                   "table", "ALLTYPES",
                        withAttribute(10, "alias", "FSMALLINT"),
                        withAttribute(11, "column", "FSMALLINT"),
                        withIdAttribute(12, idTable),
                        withAttribute(13, "index", "1"),
                        withAttribute(14, "table", "ALLTYPES")),
                    withChildNamed(15, "columnref",
                                   "alias", "FINTEGER",
                                   "column", "FINTEGER",
                                   "table", "ALLTYPES",
                        withAttribute(16, "alias", "FINTEGER"),
                        withAttribute(17, "column", "FINTEGER"),
                        withIdAttribute(18, idTable),
                        withAttribute(19, "index", "2"),
                        withAttribute(20, "table", "ALLTYPES")),
                    withChildNamed(21, "columnref",
                                   "alias", "FBIGINT",
                                   "column", "FBIGINT",
                                   "table", "ALLTYPES",
                        withAttribute(22, "alias", "FBIGINT"),
                        withAttribute(23, "column", "FBIGINT"),
                        withIdAttribute(24, idTable),
                        withAttribute(25, "index", "3"),
                        withAttribute(26, "table", "ALLTYPES")),
                    withChildNamed(27, "columnref",
                                   "alias", "FFLOAT",
                                   "column", "FFLOAT",
                                   "table", "ALLTYPES",
                        withAttribute(28, "alias", "FFLOAT"),
                        withAttribute(29, "column", "FFLOAT"),
                        withIdAttribute(30, idTable),
                        withAttribute(31, "index", "4"),
                        withAttribute(32, "table", "ALLTYPES")),
                    withChildNamed(33, "columnref",
                                   "alias", "FDECIMAL",
                                   "column", "FDECIMAL",
                                   "table", "ALLTYPES",
                        withAttribute(34, "alias", "FDECIMAL"),
                        withAttribute(35, "column", "FDECIMAL"),
                        withIdAttribute(36, idTable),
                        withAttribute(37, "index", "5"),
                        withAttribute(38, "table", "ALLTYPES")),
                    withChildNamed(39, "columnref",
                                   "alias", "FTIMESTAMP",
                                   "column", "FTIMESTAMP",
                                   "table", "ALLTYPES",
                        withAttribute(40, "alias", "FTIMESTAMP"),
                        withAttribute(41, "column", "FTIMESTAMP"),
                        withIdAttribute(42, idTable),
                        withAttribute(43, "index", "6"),
                        withAttribute(44, "table", "ALLTYPES"))),
                withChildNamed(45, "parameters"),
                withChildNamed(46, "tablescans",
                    withChildNamed(47, "tablescan",
                        withAttribute(48, "jointype", "inner"),
                        withAttribute(49, "table", "ALLTYPES"),
                        withChildNamed(50, "joincond",
                            withChildNamed(51, "operation",
                                           "optype", "equal",
                                withIdAttribute(52, idTable),
                                withAttribute(53, "optype", "equal"),
                                withChildNamed(54, "operation",
                                               "optype", "add",
                                    withIdAttribute(55, idTable),
                                    withAttribute(56, "optype", "add"),
                                    withChildNamed(57, "columnref",
                                                   "alias", "FFLOAT",
                                                   "column", "FFLOAT",
                                                   "table", "ALLTYPES",
                                        withAttribute(58, "alias", "FFLOAT"),
                                        withAttribute(59, "column", "FFLOAT"),
                                        withIdAttribute(60, idTable),
                                        withAttribute(61, "index", "4"),
                                        withAttribute(62, "table", "ALLTYPES")),
                                    withChildNamed(63, "columnref",
                                                   "alias", "FTINYINT",
                                                   "column", "FTINYINT",
                                                   "table", "ALLTYPES",
                                        withAttribute(64, "alias", "FTINYINT"),
                                        withAttribute(65, "column", "FTINYINT"),
                                        withIdAttribute(66, idTable),
                                        withAttribute(67, "index", "0"),
                                        withAttribute(68, "table", "ALLTYPES"))),
                                withChildNamed(69, "value",
                                    withIdAttribute(70, idTable),
                                    withAttribute(71, "value", "0"),
                                    withAttribute(72, "valuetype", "INTEGER")))))));
    }

    // Pattern XML:
    //    //.ELEMENT: select
    //    //.[
    //    //.....ELEMENT: columns
    //    //.....[
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = FTINYINT
    //    //....|....|.column = FTINYINT
    //    //....|....|.id = 1
    //    //....|....|.index = 0
    //    //....|....|.table = ALLTYPES
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = FSMALLINT
    //    //....|....|.column = FSMALLINT
    //    //....|....|.id = 2
    //    //....|....|.index = 1
    //    //....|....|.table = ALLTYPES
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = FINTEGER
    //    //....|....|.column = FINTEGER
    //    //....|....|.id = 3
    //    //....|....|.index = 2
    //    //....|....|.table = ALLTYPES
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = FBIGINT
    //    //....|....|.column = FBIGINT
    //    //....|....|.id = 4
    //    //....|....|.index = 3
    //    //....|....|.table = ALLTYPES
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = FFLOAT
    //    //....|....|.column = FFLOAT
    //    //....|....|.id = 5
    //    //....|....|.index = 4
    //    //....|....|.table = ALLTYPES
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = FDECIMAL
    //    //....|....|.column = FDECIMAL
    //    //....|....|.id = 6
    //    //....|....|.index = 5
    //    //....|....|.table = ALLTYPES
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = FTIMESTAMP
    //    //....|....|.column = FTIMESTAMP
    //    //....|....|.id = 7
    //    //....|....|.index = 6
    //    //....|....|.table = ALLTYPES
    //    //.....ELEMENT: parameters
    //    //.....ELEMENT: tablescans
    //    //.....[
    //    //....|....ELEMENT: tablescan
    //    //....|....|.jointype = inner
    //    //....|....|.table = ALLTYPES
    //    //....|....[
    //    //....|....|...ELEMENT: joincond
    //    //....|....|...[
    //    //....|....|....|..ELEMENT: operation
    //    //....|....|....|....id = 10
    //    //....|....|....|....optype = equal
    //    //....|....|....|..[
    //    //....|....|....|....|.ELEMENT: operation
    //    //....|....|....|....|...id = 8
    //    //....|....|....|....|...optype = add
    //    //....|....|....|....|.[
    //    //....|....|....|....|.....ELEMENT: columnref
    //    //....|....|....|....|....|..alias = FTINYINT
    //    //....|....|....|....|....|..column = FTINYINT
    //    //....|....|....|....|....|..id = 1
    //    //....|....|....|....|....|..index = 0
    //    //....|....|....|....|....|..table = ALLTYPES
    //    //....|....|....|....|.....ELEMENT: columnref
    //    //....|....|....|....|....|..alias = FFLOAT
    //    //....|....|....|....|....|..column = FFLOAT
    //    //....|....|....|....|....|..id = 5
    //    //....|....|....|....|....|..index = 4
    //    //....|....|....|....|....|..table = ALLTYPES
    //    //....|....|....|....|.ELEMENT: value
    //    //....|....|....|....|...id = 9
    //    //....|....|....|....|...value = 0
    //    //....|....|....|....|...valuetype = INTEGER
    //    //
    //    //
    //
    //
    @SuppressWarnings("unchecked")
    @Test
    public void TestFTI() throws Exception {
        String sql    = "select * from alltypes where ftinyint  + ffloat    = 0";
        IDTable idTable = new IDTable();
        VoltXMLElement element = m_HSQLInterface.getVoltXMLFromSQLUsingVoltSQLParser(sql, null, SQLKind.DQL);
        assertThat(element)
            .hasName(1, "select")
            .hasAllOf(
                withChildNamed(2, "columns",
                    withChildNamed(3, "columnref",
                                   "alias", "FTINYINT",
                                   "column", "FTINYINT",
                                   "table", "ALLTYPES",
                        withAttribute(4, "alias", "FTINYINT"),
                        withAttribute(5, "column", "FTINYINT"),
                        withIdAttribute(6, idTable),
                        withAttribute(7, "index", "0"),
                        withAttribute(8, "table", "ALLTYPES")),
                    withChildNamed(9, "columnref",
                                   "alias", "FSMALLINT",
                                   "column", "FSMALLINT",
                                   "table", "ALLTYPES",
                        withAttribute(10, "alias", "FSMALLINT"),
                        withAttribute(11, "column", "FSMALLINT"),
                        withIdAttribute(12, idTable),
                        withAttribute(13, "index", "1"),
                        withAttribute(14, "table", "ALLTYPES")),
                    withChildNamed(15, "columnref",
                                   "alias", "FINTEGER",
                                   "column", "FINTEGER",
                                   "table", "ALLTYPES",
                        withAttribute(16, "alias", "FINTEGER"),
                        withAttribute(17, "column", "FINTEGER"),
                        withIdAttribute(18, idTable),
                        withAttribute(19, "index", "2"),
                        withAttribute(20, "table", "ALLTYPES")),
                    withChildNamed(21, "columnref",
                                   "alias", "FBIGINT",
                                   "column", "FBIGINT",
                                   "table", "ALLTYPES",
                        withAttribute(22, "alias", "FBIGINT"),
                        withAttribute(23, "column", "FBIGINT"),
                        withIdAttribute(24, idTable),
                        withAttribute(25, "index", "3"),
                        withAttribute(26, "table", "ALLTYPES")),
                    withChildNamed(27, "columnref",
                                   "alias", "FFLOAT",
                                   "column", "FFLOAT",
                                   "table", "ALLTYPES",
                        withAttribute(28, "alias", "FFLOAT"),
                        withAttribute(29, "column", "FFLOAT"),
                        withIdAttribute(30, idTable),
                        withAttribute(31, "index", "4"),
                        withAttribute(32, "table", "ALLTYPES")),
                    withChildNamed(33, "columnref",
                                   "alias", "FDECIMAL",
                                   "column", "FDECIMAL",
                                   "table", "ALLTYPES",
                        withAttribute(34, "alias", "FDECIMAL"),
                        withAttribute(35, "column", "FDECIMAL"),
                        withIdAttribute(36, idTable),
                        withAttribute(37, "index", "5"),
                        withAttribute(38, "table", "ALLTYPES")),
                    withChildNamed(39, "columnref",
                                   "alias", "FTIMESTAMP",
                                   "column", "FTIMESTAMP",
                                   "table", "ALLTYPES",
                        withAttribute(40, "alias", "FTIMESTAMP"),
                        withAttribute(41, "column", "FTIMESTAMP"),
                        withIdAttribute(42, idTable),
                        withAttribute(43, "index", "6"),
                        withAttribute(44, "table", "ALLTYPES"))),
                withChildNamed(45, "parameters"),
                withChildNamed(46, "tablescans",
                    withChildNamed(47, "tablescan",
                        withAttribute(48, "jointype", "inner"),
                        withAttribute(49, "table", "ALLTYPES"),
                        withChildNamed(50, "joincond",
                            withChildNamed(51, "operation",
                                           "optype", "equal",
                                withIdAttribute(52, idTable),
                                withAttribute(53, "optype", "equal"),
                                withChildNamed(54, "operation",
                                               "optype", "add",
                                    withIdAttribute(55, idTable),
                                    withAttribute(56, "optype", "add"),
                                    withChildNamed(57, "columnref",
                                                   "alias", "FTINYINT",
                                                   "column", "FTINYINT",
                                                   "table", "ALLTYPES",
                                        withAttribute(58, "alias", "FTINYINT"),
                                        withAttribute(59, "column", "FTINYINT"),
                                        withIdAttribute(60, idTable),
                                        withAttribute(61, "index", "0"),
                                        withAttribute(62, "table", "ALLTYPES")),
                                    withChildNamed(63, "columnref",
                                                   "alias", "FFLOAT",
                                                   "column", "FFLOAT",
                                                   "table", "ALLTYPES",
                                        withAttribute(64, "alias", "FFLOAT"),
                                        withAttribute(65, "column", "FFLOAT"),
                                        withIdAttribute(66, idTable),
                                        withAttribute(67, "index", "4"),
                                        withAttribute(68, "table", "ALLTYPES"))),
                                withChildNamed(69, "value",
                                    withIdAttribute(70, idTable),
                                    withAttribute(71, "value", "0"),
                                    withAttribute(72, "valuetype", "INTEGER")))))));
    }

    // Pattern XML:
    //    //.ELEMENT: select
    //    //.[
    //    //.....ELEMENT: columns
    //    //.....[
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = FTINYINT
    //    //....|....|.column = FTINYINT
    //    //....|....|.id = 1
    //    //....|....|.index = 0
    //    //....|....|.table = ALLTYPES
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = FSMALLINT
    //    //....|....|.column = FSMALLINT
    //    //....|....|.id = 2
    //    //....|....|.index = 1
    //    //....|....|.table = ALLTYPES
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = FINTEGER
    //    //....|....|.column = FINTEGER
    //    //....|....|.id = 3
    //    //....|....|.index = 2
    //    //....|....|.table = ALLTYPES
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = FBIGINT
    //    //....|....|.column = FBIGINT
    //    //....|....|.id = 4
    //    //....|....|.index = 3
    //    //....|....|.table = ALLTYPES
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = FFLOAT
    //    //....|....|.column = FFLOAT
    //    //....|....|.id = 5
    //    //....|....|.index = 4
    //    //....|....|.table = ALLTYPES
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = FDECIMAL
    //    //....|....|.column = FDECIMAL
    //    //....|....|.id = 6
    //    //....|....|.index = 5
    //    //....|....|.table = ALLTYPES
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = FTIMESTAMP
    //    //....|....|.column = FTIMESTAMP
    //    //....|....|.id = 7
    //    //....|....|.index = 6
    //    //....|....|.table = ALLTYPES
    //    //.....ELEMENT: parameters
    //    //.....ELEMENT: tablescans
    //    //.....[
    //    //....|....ELEMENT: tablescan
    //    //....|....|.jointype = inner
    //    //....|....|.table = ALLTYPES
    //    //....|....[
    //    //....|....|...ELEMENT: joincond
    //    //....|....|...[
    //    //....|....|....|..ELEMENT: operation
    //    //....|....|....|....id = 10
    //    //....|....|....|....optype = equal
    //    //....|....|....|..[
    //    //....|....|....|....|.ELEMENT: operation
    //    //....|....|....|....|...id = 8
    //    //....|....|....|....|...optype = add
    //    //....|....|....|....|.[
    //    //....|....|....|....|.....ELEMENT: columnref
    //    //....|....|....|....|....|..alias = FDECIMAL
    //    //....|....|....|....|....|..column = FDECIMAL
    //    //....|....|....|....|....|..id = 6
    //    //....|....|....|....|....|..index = 5
    //    //....|....|....|....|....|..table = ALLTYPES
    //    //....|....|....|....|.....ELEMENT: columnref
    //    //....|....|....|....|....|..alias = FTINYINT
    //    //....|....|....|....|....|..column = FTINYINT
    //    //....|....|....|....|....|..id = 1
    //    //....|....|....|....|....|..index = 0
    //    //....|....|....|....|....|..table = ALLTYPES
    //    //....|....|....|....|.ELEMENT: value
    //    //....|....|....|....|...id = 9
    //    //....|....|....|....|...value = 0
    //    //....|....|....|....|...valuetype = INTEGER
    //    //
    //    //
    //
    //
    @SuppressWarnings("unchecked")
    @Test
    public void TestTID() throws Exception {
        String sql    = "select * from alltypes where fdecimal  + ftinyint  = 0";
        IDTable idTable = new IDTable();
        VoltXMLElement element = m_HSQLInterface.getVoltXMLFromSQLUsingVoltSQLParser(sql, null, SQLKind.DQL);
        assertThat(element)
            .hasName(1, "select")
            .hasAllOf(
                withChildNamed(2, "columns",
                    withChildNamed(3, "columnref",
                                   "alias", "FTINYINT",
                                   "column", "FTINYINT",
                                   "table", "ALLTYPES",
                        withAttribute(4, "alias", "FTINYINT"),
                        withAttribute(5, "column", "FTINYINT"),
                        withIdAttribute(6, idTable),
                        withAttribute(7, "index", "0"),
                        withAttribute(8, "table", "ALLTYPES")),
                    withChildNamed(9, "columnref",
                                   "alias", "FSMALLINT",
                                   "column", "FSMALLINT",
                                   "table", "ALLTYPES",
                        withAttribute(10, "alias", "FSMALLINT"),
                        withAttribute(11, "column", "FSMALLINT"),
                        withIdAttribute(12, idTable),
                        withAttribute(13, "index", "1"),
                        withAttribute(14, "table", "ALLTYPES")),
                    withChildNamed(15, "columnref",
                                   "alias", "FINTEGER",
                                   "column", "FINTEGER",
                                   "table", "ALLTYPES",
                        withAttribute(16, "alias", "FINTEGER"),
                        withAttribute(17, "column", "FINTEGER"),
                        withIdAttribute(18, idTable),
                        withAttribute(19, "index", "2"),
                        withAttribute(20, "table", "ALLTYPES")),
                    withChildNamed(21, "columnref",
                                   "alias", "FBIGINT",
                                   "column", "FBIGINT",
                                   "table", "ALLTYPES",
                        withAttribute(22, "alias", "FBIGINT"),
                        withAttribute(23, "column", "FBIGINT"),
                        withIdAttribute(24, idTable),
                        withAttribute(25, "index", "3"),
                        withAttribute(26, "table", "ALLTYPES")),
                    withChildNamed(27, "columnref",
                                   "alias", "FFLOAT",
                                   "column", "FFLOAT",
                                   "table", "ALLTYPES",
                        withAttribute(28, "alias", "FFLOAT"),
                        withAttribute(29, "column", "FFLOAT"),
                        withIdAttribute(30, idTable),
                        withAttribute(31, "index", "4"),
                        withAttribute(32, "table", "ALLTYPES")),
                    withChildNamed(33, "columnref",
                                   "alias", "FDECIMAL",
                                   "column", "FDECIMAL",
                                   "table", "ALLTYPES",
                        withAttribute(34, "alias", "FDECIMAL"),
                        withAttribute(35, "column", "FDECIMAL"),
                        withIdAttribute(36, idTable),
                        withAttribute(37, "index", "5"),
                        withAttribute(38, "table", "ALLTYPES")),
                    withChildNamed(39, "columnref",
                                   "alias", "FTIMESTAMP",
                                   "column", "FTIMESTAMP",
                                   "table", "ALLTYPES",
                        withAttribute(40, "alias", "FTIMESTAMP"),
                        withAttribute(41, "column", "FTIMESTAMP"),
                        withIdAttribute(42, idTable),
                        withAttribute(43, "index", "6"),
                        withAttribute(44, "table", "ALLTYPES"))),
                withChildNamed(45, "parameters"),
                withChildNamed(46, "tablescans",
                    withChildNamed(47, "tablescan",
                        withAttribute(48, "jointype", "inner"),
                        withAttribute(49, "table", "ALLTYPES"),
                        withChildNamed(50, "joincond",
                            withChildNamed(51, "operation",
                                           "optype", "equal",
                                withIdAttribute(52, idTable),
                                withAttribute(53, "optype", "equal"),
                                withChildNamed(54, "operation",
                                               "optype", "add",
                                    withIdAttribute(55, idTable),
                                    withAttribute(56, "optype", "add"),
                                    withChildNamed(57, "columnref",
                                                   "alias", "FDECIMAL",
                                                   "column", "FDECIMAL",
                                                   "table", "ALLTYPES",
                                        withAttribute(58, "alias", "FDECIMAL"),
                                        withAttribute(59, "column", "FDECIMAL"),
                                        withIdAttribute(60, idTable),
                                        withAttribute(61, "index", "5"),
                                        withAttribute(62, "table", "ALLTYPES")),
                                    withChildNamed(63, "columnref",
                                                   "alias", "FTINYINT",
                                                   "column", "FTINYINT",
                                                   "table", "ALLTYPES",
                                        withAttribute(64, "alias", "FTINYINT"),
                                        withAttribute(65, "column", "FTINYINT"),
                                        withIdAttribute(66, idTable),
                                        withAttribute(67, "index", "0"),
                                        withAttribute(68, "table", "ALLTYPES"))),
                                withChildNamed(69, "value",
                                    withIdAttribute(70, idTable),
                                    withAttribute(71, "value", "0"),
                                    withAttribute(72, "valuetype", "INTEGER")))))));
    }

    // Pattern XML:
    //    //.ELEMENT: select
    //    //.[
    //    //.....ELEMENT: columns
    //    //.....[
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = FTINYINT
    //    //....|....|.column = FTINYINT
    //    //....|....|.id = 1
    //    //....|....|.index = 0
    //    //....|....|.table = ALLTYPES
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = FSMALLINT
    //    //....|....|.column = FSMALLINT
    //    //....|....|.id = 2
    //    //....|....|.index = 1
    //    //....|....|.table = ALLTYPES
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = FINTEGER
    //    //....|....|.column = FINTEGER
    //    //....|....|.id = 3
    //    //....|....|.index = 2
    //    //....|....|.table = ALLTYPES
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = FBIGINT
    //    //....|....|.column = FBIGINT
    //    //....|....|.id = 4
    //    //....|....|.index = 3
    //    //....|....|.table = ALLTYPES
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = FFLOAT
    //    //....|....|.column = FFLOAT
    //    //....|....|.id = 5
    //    //....|....|.index = 4
    //    //....|....|.table = ALLTYPES
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = FDECIMAL
    //    //....|....|.column = FDECIMAL
    //    //....|....|.id = 6
    //    //....|....|.index = 5
    //    //....|....|.table = ALLTYPES
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = FTIMESTAMP
    //    //....|....|.column = FTIMESTAMP
    //    //....|....|.id = 7
    //    //....|....|.index = 6
    //    //....|....|.table = ALLTYPES
    //    //.....ELEMENT: parameters
    //    //.....ELEMENT: tablescans
    //    //.....[
    //    //....|....ELEMENT: tablescan
    //    //....|....|.jointype = inner
    //    //....|....|.table = ALLTYPES
    //    //....|....[
    //    //....|....|...ELEMENT: joincond
    //    //....|....|...[
    //    //....|....|....|..ELEMENT: operation
    //    //....|....|....|....id = 10
    //    //....|....|....|....optype = equal
    //    //....|....|....|..[
    //    //....|....|....|....|.ELEMENT: operation
    //    //....|....|....|....|...id = 8
    //    //....|....|....|....|...optype = add
    //    //....|....|....|....|.[
    //    //....|....|....|....|.....ELEMENT: columnref
    //    //....|....|....|....|....|..alias = FTINYINT
    //    //....|....|....|....|....|..column = FTINYINT
    //    //....|....|....|....|....|..id = 1
    //    //....|....|....|....|....|..index = 0
    //    //....|....|....|....|....|..table = ALLTYPES
    //    //....|....|....|....|.....ELEMENT: columnref
    //    //....|....|....|....|....|..alias = FDECIMAL
    //    //....|....|....|....|....|..column = FDECIMAL
    //    //....|....|....|....|....|..id = 6
    //    //....|....|....|....|....|..index = 5
    //    //....|....|....|....|....|..table = ALLTYPES
    //    //....|....|....|....|.ELEMENT: value
    //    //....|....|....|....|...id = 9
    //    //....|....|....|....|...value = 0
    //    //....|....|....|....|...valuetype = INTEGER
    //    //
    //    //
    //
    //
    @SuppressWarnings("unchecked")
    @Test
    public void TestDTI() throws Exception {
        String sql    = "select * from alltypes where ftinyint  + fdecimal  = 0";
        IDTable idTable = new IDTable();
        VoltXMLElement element = m_HSQLInterface.getVoltXMLFromSQLUsingVoltSQLParser(sql, null, SQLKind.DQL);
        assertThat(element)
            .hasName(1, "select")
            .hasAllOf(
                withChildNamed(2, "columns",
                    withChildNamed(3, "columnref",
                                   "alias", "FTINYINT",
                                   "column", "FTINYINT",
                                   "table", "ALLTYPES",
                        withAttribute(4, "alias", "FTINYINT"),
                        withAttribute(5, "column", "FTINYINT"),
                        withIdAttribute(6, idTable),
                        withAttribute(7, "index", "0"),
                        withAttribute(8, "table", "ALLTYPES")),
                    withChildNamed(9, "columnref",
                                   "alias", "FSMALLINT",
                                   "column", "FSMALLINT",
                                   "table", "ALLTYPES",
                        withAttribute(10, "alias", "FSMALLINT"),
                        withAttribute(11, "column", "FSMALLINT"),
                        withIdAttribute(12, idTable),
                        withAttribute(13, "index", "1"),
                        withAttribute(14, "table", "ALLTYPES")),
                    withChildNamed(15, "columnref",
                                   "alias", "FINTEGER",
                                   "column", "FINTEGER",
                                   "table", "ALLTYPES",
                        withAttribute(16, "alias", "FINTEGER"),
                        withAttribute(17, "column", "FINTEGER"),
                        withIdAttribute(18, idTable),
                        withAttribute(19, "index", "2"),
                        withAttribute(20, "table", "ALLTYPES")),
                    withChildNamed(21, "columnref",
                                   "alias", "FBIGINT",
                                   "column", "FBIGINT",
                                   "table", "ALLTYPES",
                        withAttribute(22, "alias", "FBIGINT"),
                        withAttribute(23, "column", "FBIGINT"),
                        withIdAttribute(24, idTable),
                        withAttribute(25, "index", "3"),
                        withAttribute(26, "table", "ALLTYPES")),
                    withChildNamed(27, "columnref",
                                   "alias", "FFLOAT",
                                   "column", "FFLOAT",
                                   "table", "ALLTYPES",
                        withAttribute(28, "alias", "FFLOAT"),
                        withAttribute(29, "column", "FFLOAT"),
                        withIdAttribute(30, idTable),
                        withAttribute(31, "index", "4"),
                        withAttribute(32, "table", "ALLTYPES")),
                    withChildNamed(33, "columnref",
                                   "alias", "FDECIMAL",
                                   "column", "FDECIMAL",
                                   "table", "ALLTYPES",
                        withAttribute(34, "alias", "FDECIMAL"),
                        withAttribute(35, "column", "FDECIMAL"),
                        withIdAttribute(36, idTable),
                        withAttribute(37, "index", "5"),
                        withAttribute(38, "table", "ALLTYPES")),
                    withChildNamed(39, "columnref",
                                   "alias", "FTIMESTAMP",
                                   "column", "FTIMESTAMP",
                                   "table", "ALLTYPES",
                        withAttribute(40, "alias", "FTIMESTAMP"),
                        withAttribute(41, "column", "FTIMESTAMP"),
                        withIdAttribute(42, idTable),
                        withAttribute(43, "index", "6"),
                        withAttribute(44, "table", "ALLTYPES"))),
                withChildNamed(45, "parameters"),
                withChildNamed(46, "tablescans",
                    withChildNamed(47, "tablescan",
                        withAttribute(48, "jointype", "inner"),
                        withAttribute(49, "table", "ALLTYPES"),
                        withChildNamed(50, "joincond",
                            withChildNamed(51, "operation",
                                           "optype", "equal",
                                withIdAttribute(52, idTable),
                                withAttribute(53, "optype", "equal"),
                                withChildNamed(54, "operation",
                                               "optype", "add",
                                    withIdAttribute(55, idTable),
                                    withAttribute(56, "optype", "add"),
                                    withChildNamed(57, "columnref",
                                                   "alias", "FTINYINT",
                                                   "column", "FTINYINT",
                                                   "table", "ALLTYPES",
                                        withAttribute(58, "alias", "FTINYINT"),
                                        withAttribute(59, "column", "FTINYINT"),
                                        withIdAttribute(60, idTable),
                                        withAttribute(61, "index", "0"),
                                        withAttribute(62, "table", "ALLTYPES")),
                                    withChildNamed(63, "columnref",
                                                   "alias", "FDECIMAL",
                                                   "column", "FDECIMAL",
                                                   "table", "ALLTYPES",
                                        withAttribute(64, "alias", "FDECIMAL"),
                                        withAttribute(65, "column", "FDECIMAL"),
                                        withIdAttribute(66, idTable),
                                        withAttribute(67, "index", "5"),
                                        withAttribute(68, "table", "ALLTYPES"))),
                                withChildNamed(69, "value",
                                    withIdAttribute(70, idTable),
                                    withAttribute(71, "value", "0"),
                                    withAttribute(72, "valuetype", "INTEGER")))))));
    }

    // Pattern XML:
    //    //.ELEMENT: select
    //    //.[
    //    //.....ELEMENT: columns
    //    //.....[
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = FTINYINT
    //    //....|....|.column = FTINYINT
    //    //....|....|.id = 1
    //    //....|....|.index = 0
    //    //....|....|.table = ALLTYPES
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = FSMALLINT
    //    //....|....|.column = FSMALLINT
    //    //....|....|.id = 2
    //    //....|....|.index = 1
    //    //....|....|.table = ALLTYPES
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = FINTEGER
    //    //....|....|.column = FINTEGER
    //    //....|....|.id = 3
    //    //....|....|.index = 2
    //    //....|....|.table = ALLTYPES
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = FBIGINT
    //    //....|....|.column = FBIGINT
    //    //....|....|.id = 4
    //    //....|....|.index = 3
    //    //....|....|.table = ALLTYPES
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = FFLOAT
    //    //....|....|.column = FFLOAT
    //    //....|....|.id = 5
    //    //....|....|.index = 4
    //    //....|....|.table = ALLTYPES
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = FDECIMAL
    //    //....|....|.column = FDECIMAL
    //    //....|....|.id = 6
    //    //....|....|.index = 5
    //    //....|....|.table = ALLTYPES
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = FTIMESTAMP
    //    //....|....|.column = FTIMESTAMP
    //    //....|....|.id = 7
    //    //....|....|.index = 6
    //    //....|....|.table = ALLTYPES
    //    //.....ELEMENT: parameters
    //    //.....ELEMENT: tablescans
    //    //.....[
    //    //....|....ELEMENT: tablescan
    //    //....|....|.jointype = inner
    //    //....|....|.table = ALLTYPES
    //    //....|....[
    //    //....|....|...ELEMENT: joincond
    //    //....|....|...[
    //    //....|....|....|..ELEMENT: operation
    //    //....|....|....|....id = 10
    //    //....|....|....|....optype = equal
    //    //....|....|....|..[
    //    //....|....|....|....|.ELEMENT: operation
    //    //....|....|....|....|...id = 8
    //    //....|....|....|....|...optype = add
    //    //....|....|....|....|.[
    //    //....|....|....|....|.....ELEMENT: columnref
    //    //....|....|....|....|....|..alias = FSMALLINT
    //    //....|....|....|....|....|..column = FSMALLINT
    //    //....|....|....|....|....|..id = 2
    //    //....|....|....|....|....|..index = 1
    //    //....|....|....|....|....|..table = ALLTYPES
    //    //....|....|....|....|.....ELEMENT: columnref
    //    //....|....|....|....|....|..alias = FINTEGER
    //    //....|....|....|....|....|..column = FINTEGER
    //    //....|....|....|....|....|..id = 3
    //    //....|....|....|....|....|..index = 2
    //    //....|....|....|....|....|..table = ALLTYPES
    //    //....|....|....|....|.ELEMENT: value
    //    //....|....|....|....|...id = 9
    //    //....|....|....|....|...value = 0
    //    //....|....|....|....|...valuetype = INTEGER
    //    //
    //    //
    //
    //
    @SuppressWarnings("unchecked")
    @Test
    public void TestSII() throws Exception {
        String sql    = "select * from alltypes where fsmallint + finteger  = 0";
        IDTable idTable = new IDTable();
        VoltXMLElement element = m_HSQLInterface.getVoltXMLFromSQLUsingVoltSQLParser(sql, null, SQLKind.DQL);
        assertThat(element)
            .hasName(1, "select")
            .hasAllOf(
                withChildNamed(2, "columns",
                    withChildNamed(3, "columnref",
                                   "alias", "FTINYINT",
                                   "column", "FTINYINT",
                                   "table", "ALLTYPES",
                        withAttribute(4, "alias", "FTINYINT"),
                        withAttribute(5, "column", "FTINYINT"),
                        withIdAttribute(6, idTable),
                        withAttribute(7, "index", "0"),
                        withAttribute(8, "table", "ALLTYPES")),
                    withChildNamed(9, "columnref",
                                   "alias", "FSMALLINT",
                                   "column", "FSMALLINT",
                                   "table", "ALLTYPES",
                        withAttribute(10, "alias", "FSMALLINT"),
                        withAttribute(11, "column", "FSMALLINT"),
                        withIdAttribute(12, idTable),
                        withAttribute(13, "index", "1"),
                        withAttribute(14, "table", "ALLTYPES")),
                    withChildNamed(15, "columnref",
                                   "alias", "FINTEGER",
                                   "column", "FINTEGER",
                                   "table", "ALLTYPES",
                        withAttribute(16, "alias", "FINTEGER"),
                        withAttribute(17, "column", "FINTEGER"),
                        withIdAttribute(18, idTable),
                        withAttribute(19, "index", "2"),
                        withAttribute(20, "table", "ALLTYPES")),
                    withChildNamed(21, "columnref",
                                   "alias", "FBIGINT",
                                   "column", "FBIGINT",
                                   "table", "ALLTYPES",
                        withAttribute(22, "alias", "FBIGINT"),
                        withAttribute(23, "column", "FBIGINT"),
                        withIdAttribute(24, idTable),
                        withAttribute(25, "index", "3"),
                        withAttribute(26, "table", "ALLTYPES")),
                    withChildNamed(27, "columnref",
                                   "alias", "FFLOAT",
                                   "column", "FFLOAT",
                                   "table", "ALLTYPES",
                        withAttribute(28, "alias", "FFLOAT"),
                        withAttribute(29, "column", "FFLOAT"),
                        withIdAttribute(30, idTable),
                        withAttribute(31, "index", "4"),
                        withAttribute(32, "table", "ALLTYPES")),
                    withChildNamed(33, "columnref",
                                   "alias", "FDECIMAL",
                                   "column", "FDECIMAL",
                                   "table", "ALLTYPES",
                        withAttribute(34, "alias", "FDECIMAL"),
                        withAttribute(35, "column", "FDECIMAL"),
                        withIdAttribute(36, idTable),
                        withAttribute(37, "index", "5"),
                        withAttribute(38, "table", "ALLTYPES")),
                    withChildNamed(39, "columnref",
                                   "alias", "FTIMESTAMP",
                                   "column", "FTIMESTAMP",
                                   "table", "ALLTYPES",
                        withAttribute(40, "alias", "FTIMESTAMP"),
                        withAttribute(41, "column", "FTIMESTAMP"),
                        withIdAttribute(42, idTable),
                        withAttribute(43, "index", "6"),
                        withAttribute(44, "table", "ALLTYPES"))),
                withChildNamed(45, "parameters"),
                withChildNamed(46, "tablescans",
                    withChildNamed(47, "tablescan",
                        withAttribute(48, "jointype", "inner"),
                        withAttribute(49, "table", "ALLTYPES"),
                        withChildNamed(50, "joincond",
                            withChildNamed(51, "operation",
                                           "optype", "equal",
                                withIdAttribute(52, idTable),
                                withAttribute(53, "optype", "equal"),
                                withChildNamed(54, "operation",
                                               "optype", "add",
                                    withIdAttribute(55, idTable),
                                    withAttribute(56, "optype", "add"),
                                    withChildNamed(57, "columnref",
                                                   "alias", "FSMALLINT",
                                                   "column", "FSMALLINT",
                                                   "table", "ALLTYPES",
                                        withAttribute(58, "alias", "FSMALLINT"),
                                        withAttribute(59, "column", "FSMALLINT"),
                                        withIdAttribute(60, idTable),
                                        withAttribute(61, "index", "1"),
                                        withAttribute(62, "table", "ALLTYPES")),
                                    withChildNamed(63, "columnref",
                                                   "alias", "FINTEGER",
                                                   "column", "FINTEGER",
                                                   "table", "ALLTYPES",
                                        withAttribute(64, "alias", "FINTEGER"),
                                        withAttribute(65, "column", "FINTEGER"),
                                        withIdAttribute(66, idTable),
                                        withAttribute(67, "index", "2"),
                                        withAttribute(68, "table", "ALLTYPES"))),
                                withChildNamed(69, "value",
                                    withIdAttribute(70, idTable),
                                    withAttribute(71, "value", "0"),
                                    withAttribute(72, "valuetype", "INTEGER")))))));
    }

    // Pattern XML:
    //    //.ELEMENT: select
    //    //.[
    //    //.....ELEMENT: columns
    //    //.....[
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = FTINYINT
    //    //....|....|.column = FTINYINT
    //    //....|....|.id = 1
    //    //....|....|.index = 0
    //    //....|....|.table = ALLTYPES
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = FSMALLINT
    //    //....|....|.column = FSMALLINT
    //    //....|....|.id = 2
    //    //....|....|.index = 1
    //    //....|....|.table = ALLTYPES
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = FINTEGER
    //    //....|....|.column = FINTEGER
    //    //....|....|.id = 3
    //    //....|....|.index = 2
    //    //....|....|.table = ALLTYPES
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = FBIGINT
    //    //....|....|.column = FBIGINT
    //    //....|....|.id = 4
    //    //....|....|.index = 3
    //    //....|....|.table = ALLTYPES
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = FFLOAT
    //    //....|....|.column = FFLOAT
    //    //....|....|.id = 5
    //    //....|....|.index = 4
    //    //....|....|.table = ALLTYPES
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = FDECIMAL
    //    //....|....|.column = FDECIMAL
    //    //....|....|.id = 6
    //    //....|....|.index = 5
    //    //....|....|.table = ALLTYPES
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = FTIMESTAMP
    //    //....|....|.column = FTIMESTAMP
    //    //....|....|.id = 7
    //    //....|....|.index = 6
    //    //....|....|.table = ALLTYPES
    //    //.....ELEMENT: parameters
    //    //.....ELEMENT: tablescans
    //    //.....[
    //    //....|....ELEMENT: tablescan
    //    //....|....|.jointype = inner
    //    //....|....|.table = ALLTYPES
    //    //....|....[
    //    //....|....|...ELEMENT: joincond
    //    //....|....|...[
    //    //....|....|....|..ELEMENT: operation
    //    //....|....|....|....id = 10
    //    //....|....|....|....optype = equal
    //    //....|....|....|..[
    //    //....|....|....|....|.ELEMENT: operation
    //    //....|....|....|....|...id = 8
    //    //....|....|....|....|...optype = add
    //    //....|....|....|....|.[
    //    //....|....|....|....|.....ELEMENT: columnref
    //    //....|....|....|....|....|..alias = FINTEGER
    //    //....|....|....|....|....|..column = FINTEGER
    //    //....|....|....|....|....|..id = 3
    //    //....|....|....|....|....|..index = 2
    //    //....|....|....|....|....|..table = ALLTYPES
    //    //....|....|....|....|.....ELEMENT: columnref
    //    //....|....|....|....|....|..alias = FSMALLINT
    //    //....|....|....|....|....|..column = FSMALLINT
    //    //....|....|....|....|....|..id = 2
    //    //....|....|....|....|....|..index = 1
    //    //....|....|....|....|....|..table = ALLTYPES
    //    //....|....|....|....|.ELEMENT: value
    //    //....|....|....|....|...id = 9
    //    //....|....|....|....|...value = 0
    //    //....|....|....|....|...valuetype = INTEGER
    //    //
    //    //
    //
    //
    @SuppressWarnings("unchecked")
    @Test
    public void TestISI() throws Exception {
        String sql    = "select * from alltypes where finteger  + fsmallint = 0";
        IDTable idTable = new IDTable();
        VoltXMLElement element = m_HSQLInterface.getVoltXMLFromSQLUsingVoltSQLParser(sql, null, SQLKind.DQL);
        assertThat(element)
            .hasName(1, "select")
            .hasAllOf(
                withChildNamed(2, "columns",
                    withChildNamed(3, "columnref",
                                   "alias", "FTINYINT",
                                   "column", "FTINYINT",
                                   "table", "ALLTYPES",
                        withAttribute(4, "alias", "FTINYINT"),
                        withAttribute(5, "column", "FTINYINT"),
                        withIdAttribute(6, idTable),
                        withAttribute(7, "index", "0"),
                        withAttribute(8, "table", "ALLTYPES")),
                    withChildNamed(9, "columnref",
                                   "alias", "FSMALLINT",
                                   "column", "FSMALLINT",
                                   "table", "ALLTYPES",
                        withAttribute(10, "alias", "FSMALLINT"),
                        withAttribute(11, "column", "FSMALLINT"),
                        withIdAttribute(12, idTable),
                        withAttribute(13, "index", "1"),
                        withAttribute(14, "table", "ALLTYPES")),
                    withChildNamed(15, "columnref",
                                   "alias", "FINTEGER",
                                   "column", "FINTEGER",
                                   "table", "ALLTYPES",
                        withAttribute(16, "alias", "FINTEGER"),
                        withAttribute(17, "column", "FINTEGER"),
                        withIdAttribute(18, idTable),
                        withAttribute(19, "index", "2"),
                        withAttribute(20, "table", "ALLTYPES")),
                    withChildNamed(21, "columnref",
                                   "alias", "FBIGINT",
                                   "column", "FBIGINT",
                                   "table", "ALLTYPES",
                        withAttribute(22, "alias", "FBIGINT"),
                        withAttribute(23, "column", "FBIGINT"),
                        withIdAttribute(24, idTable),
                        withAttribute(25, "index", "3"),
                        withAttribute(26, "table", "ALLTYPES")),
                    withChildNamed(27, "columnref",
                                   "alias", "FFLOAT",
                                   "column", "FFLOAT",
                                   "table", "ALLTYPES",
                        withAttribute(28, "alias", "FFLOAT"),
                        withAttribute(29, "column", "FFLOAT"),
                        withIdAttribute(30, idTable),
                        withAttribute(31, "index", "4"),
                        withAttribute(32, "table", "ALLTYPES")),
                    withChildNamed(33, "columnref",
                                   "alias", "FDECIMAL",
                                   "column", "FDECIMAL",
                                   "table", "ALLTYPES",
                        withAttribute(34, "alias", "FDECIMAL"),
                        withAttribute(35, "column", "FDECIMAL"),
                        withIdAttribute(36, idTable),
                        withAttribute(37, "index", "5"),
                        withAttribute(38, "table", "ALLTYPES")),
                    withChildNamed(39, "columnref",
                                   "alias", "FTIMESTAMP",
                                   "column", "FTIMESTAMP",
                                   "table", "ALLTYPES",
                        withAttribute(40, "alias", "FTIMESTAMP"),
                        withAttribute(41, "column", "FTIMESTAMP"),
                        withIdAttribute(42, idTable),
                        withAttribute(43, "index", "6"),
                        withAttribute(44, "table", "ALLTYPES"))),
                withChildNamed(45, "parameters"),
                withChildNamed(46, "tablescans",
                    withChildNamed(47, "tablescan",
                        withAttribute(48, "jointype", "inner"),
                        withAttribute(49, "table", "ALLTYPES"),
                        withChildNamed(50, "joincond",
                            withChildNamed(51, "operation",
                                           "optype", "equal",
                                withIdAttribute(52, idTable),
                                withAttribute(53, "optype", "equal"),
                                withChildNamed(54, "operation",
                                               "optype", "add",
                                    withIdAttribute(55, idTable),
                                    withAttribute(56, "optype", "add"),
                                    withChildNamed(57, "columnref",
                                                   "alias", "FINTEGER",
                                                   "column", "FINTEGER",
                                                   "table", "ALLTYPES",
                                        withAttribute(58, "alias", "FINTEGER"),
                                        withAttribute(59, "column", "FINTEGER"),
                                        withIdAttribute(60, idTable),
                                        withAttribute(61, "index", "2"),
                                        withAttribute(62, "table", "ALLTYPES")),
                                    withChildNamed(63, "columnref",
                                                   "alias", "FSMALLINT",
                                                   "column", "FSMALLINT",
                                                   "table", "ALLTYPES",
                                        withAttribute(64, "alias", "FSMALLINT"),
                                        withAttribute(65, "column", "FSMALLINT"),
                                        withIdAttribute(66, idTable),
                                        withAttribute(67, "index", "1"),
                                        withAttribute(68, "table", "ALLTYPES"))),
                                withChildNamed(69, "value",
                                    withIdAttribute(70, idTable),
                                    withAttribute(71, "value", "0"),
                                    withAttribute(72, "valuetype", "INTEGER")))))));
    }

    // Pattern XML:
    //    //.ELEMENT: select
    //    //.[
    //    //.....ELEMENT: columns
    //    //.....[
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = FTINYINT
    //    //....|....|.column = FTINYINT
    //    //....|....|.id = 1
    //    //....|....|.index = 0
    //    //....|....|.table = ALLTYPES
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = FSMALLINT
    //    //....|....|.column = FSMALLINT
    //    //....|....|.id = 2
    //    //....|....|.index = 1
    //    //....|....|.table = ALLTYPES
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = FINTEGER
    //    //....|....|.column = FINTEGER
    //    //....|....|.id = 3
    //    //....|....|.index = 2
    //    //....|....|.table = ALLTYPES
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = FBIGINT
    //    //....|....|.column = FBIGINT
    //    //....|....|.id = 4
    //    //....|....|.index = 3
    //    //....|....|.table = ALLTYPES
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = FFLOAT
    //    //....|....|.column = FFLOAT
    //    //....|....|.id = 5
    //    //....|....|.index = 4
    //    //....|....|.table = ALLTYPES
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = FDECIMAL
    //    //....|....|.column = FDECIMAL
    //    //....|....|.id = 6
    //    //....|....|.index = 5
    //    //....|....|.table = ALLTYPES
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = FTIMESTAMP
    //    //....|....|.column = FTIMESTAMP
    //    //....|....|.id = 7
    //    //....|....|.index = 6
    //    //....|....|.table = ALLTYPES
    //    //.....ELEMENT: parameters
    //    //.....ELEMENT: tablescans
    //    //.....[
    //    //....|....ELEMENT: tablescan
    //    //....|....|.jointype = inner
    //    //....|....|.table = ALLTYPES
    //    //....|....[
    //    //....|....|...ELEMENT: joincond
    //    //....|....|...[
    //    //....|....|....|..ELEMENT: operation
    //    //....|....|....|....id = 10
    //    //....|....|....|....optype = equal
    //    //....|....|....|..[
    //    //....|....|....|....|.ELEMENT: operation
    //    //....|....|....|....|...id = 8
    //    //....|....|....|....|...optype = add
    //    //....|....|....|....|.[
    //    //....|....|....|....|.....ELEMENT: columnref
    //    //....|....|....|....|....|..alias = FBIGINT
    //    //....|....|....|....|....|..column = FBIGINT
    //    //....|....|....|....|....|..id = 4
    //    //....|....|....|....|....|..index = 3
    //    //....|....|....|....|....|..table = ALLTYPES
    //    //....|....|....|....|.....ELEMENT: columnref
    //    //....|....|....|....|....|..alias = FSMALLINT
    //    //....|....|....|....|....|..column = FSMALLINT
    //    //....|....|....|....|....|..id = 2
    //    //....|....|....|....|....|..index = 1
    //    //....|....|....|....|....|..table = ALLTYPES
    //    //....|....|....|....|.ELEMENT: value
    //    //....|....|....|....|...id = 9
    //    //....|....|....|....|...value = 0
    //    //....|....|....|....|...valuetype = INTEGER
    //    //
    //    //
    //
    //
    @SuppressWarnings("unchecked")
    @Test
    public void TestSIBI() throws Exception {
        String sql    = "select * from alltypes where fbigint   + fsmallint = 0";
        IDTable idTable = new IDTable();
        VoltXMLElement element = m_HSQLInterface.getVoltXMLFromSQLUsingVoltSQLParser(sql, null, SQLKind.DQL);
        assertThat(element)
            .hasName(1, "select")
            .hasAllOf(
                withChildNamed(2, "columns",
                    withChildNamed(3, "columnref",
                                   "alias", "FTINYINT",
                                   "column", "FTINYINT",
                                   "table", "ALLTYPES",
                        withAttribute(4, "alias", "FTINYINT"),
                        withAttribute(5, "column", "FTINYINT"),
                        withIdAttribute(6, idTable),
                        withAttribute(7, "index", "0"),
                        withAttribute(8, "table", "ALLTYPES")),
                    withChildNamed(9, "columnref",
                                   "alias", "FSMALLINT",
                                   "column", "FSMALLINT",
                                   "table", "ALLTYPES",
                        withAttribute(10, "alias", "FSMALLINT"),
                        withAttribute(11, "column", "FSMALLINT"),
                        withIdAttribute(12, idTable),
                        withAttribute(13, "index", "1"),
                        withAttribute(14, "table", "ALLTYPES")),
                    withChildNamed(15, "columnref",
                                   "alias", "FINTEGER",
                                   "column", "FINTEGER",
                                   "table", "ALLTYPES",
                        withAttribute(16, "alias", "FINTEGER"),
                        withAttribute(17, "column", "FINTEGER"),
                        withIdAttribute(18, idTable),
                        withAttribute(19, "index", "2"),
                        withAttribute(20, "table", "ALLTYPES")),
                    withChildNamed(21, "columnref",
                                   "alias", "FBIGINT",
                                   "column", "FBIGINT",
                                   "table", "ALLTYPES",
                        withAttribute(22, "alias", "FBIGINT"),
                        withAttribute(23, "column", "FBIGINT"),
                        withIdAttribute(24, idTable),
                        withAttribute(25, "index", "3"),
                        withAttribute(26, "table", "ALLTYPES")),
                    withChildNamed(27, "columnref",
                                   "alias", "FFLOAT",
                                   "column", "FFLOAT",
                                   "table", "ALLTYPES",
                        withAttribute(28, "alias", "FFLOAT"),
                        withAttribute(29, "column", "FFLOAT"),
                        withIdAttribute(30, idTable),
                        withAttribute(31, "index", "4"),
                        withAttribute(32, "table", "ALLTYPES")),
                    withChildNamed(33, "columnref",
                                   "alias", "FDECIMAL",
                                   "column", "FDECIMAL",
                                   "table", "ALLTYPES",
                        withAttribute(34, "alias", "FDECIMAL"),
                        withAttribute(35, "column", "FDECIMAL"),
                        withIdAttribute(36, idTable),
                        withAttribute(37, "index", "5"),
                        withAttribute(38, "table", "ALLTYPES")),
                    withChildNamed(39, "columnref",
                                   "alias", "FTIMESTAMP",
                                   "column", "FTIMESTAMP",
                                   "table", "ALLTYPES",
                        withAttribute(40, "alias", "FTIMESTAMP"),
                        withAttribute(41, "column", "FTIMESTAMP"),
                        withIdAttribute(42, idTable),
                        withAttribute(43, "index", "6"),
                        withAttribute(44, "table", "ALLTYPES"))),
                withChildNamed(45, "parameters"),
                withChildNamed(46, "tablescans",
                    withChildNamed(47, "tablescan",
                        withAttribute(48, "jointype", "inner"),
                        withAttribute(49, "table", "ALLTYPES"),
                        withChildNamed(50, "joincond",
                            withChildNamed(51, "operation",
                                           "optype", "equal",
                                withIdAttribute(52, idTable),
                                withAttribute(53, "optype", "equal"),
                                withChildNamed(54, "operation",
                                               "optype", "add",
                                    withIdAttribute(55, idTable),
                                    withAttribute(56, "optype", "add"),
                                    withChildNamed(57, "columnref",
                                                   "alias", "FBIGINT",
                                                   "column", "FBIGINT",
                                                   "table", "ALLTYPES",
                                        withAttribute(58, "alias", "FBIGINT"),
                                        withAttribute(59, "column", "FBIGINT"),
                                        withIdAttribute(60, idTable),
                                        withAttribute(61, "index", "3"),
                                        withAttribute(62, "table", "ALLTYPES")),
                                    withChildNamed(63, "columnref",
                                                   "alias", "FSMALLINT",
                                                   "column", "FSMALLINT",
                                                   "table", "ALLTYPES",
                                        withAttribute(64, "alias", "FSMALLINT"),
                                        withAttribute(65, "column", "FSMALLINT"),
                                        withIdAttribute(66, idTable),
                                        withAttribute(67, "index", "1"),
                                        withAttribute(68, "table", "ALLTYPES"))),
                                withChildNamed(69, "value",
                                    withIdAttribute(70, idTable),
                                    withAttribute(71, "value", "0"),
                                    withAttribute(72, "valuetype", "INTEGER")))))));
    }

    // Pattern XML:
    //    //.ELEMENT: select
    //    //.[
    //    //.....ELEMENT: columns
    //    //.....[
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = FTINYINT
    //    //....|....|.column = FTINYINT
    //    //....|....|.id = 1
    //    //....|....|.index = 0
    //    //....|....|.table = ALLTYPES
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = FSMALLINT
    //    //....|....|.column = FSMALLINT
    //    //....|....|.id = 2
    //    //....|....|.index = 1
    //    //....|....|.table = ALLTYPES
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = FINTEGER
    //    //....|....|.column = FINTEGER
    //    //....|....|.id = 3
    //    //....|....|.index = 2
    //    //....|....|.table = ALLTYPES
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = FBIGINT
    //    //....|....|.column = FBIGINT
    //    //....|....|.id = 4
    //    //....|....|.index = 3
    //    //....|....|.table = ALLTYPES
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = FFLOAT
    //    //....|....|.column = FFLOAT
    //    //....|....|.id = 5
    //    //....|....|.index = 4
    //    //....|....|.table = ALLTYPES
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = FDECIMAL
    //    //....|....|.column = FDECIMAL
    //    //....|....|.id = 6
    //    //....|....|.index = 5
    //    //....|....|.table = ALLTYPES
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = FTIMESTAMP
    //    //....|....|.column = FTIMESTAMP
    //    //....|....|.id = 7
    //    //....|....|.index = 6
    //    //....|....|.table = ALLTYPES
    //    //.....ELEMENT: parameters
    //    //.....ELEMENT: tablescans
    //    //.....[
    //    //....|....ELEMENT: tablescan
    //    //....|....|.jointype = inner
    //    //....|....|.table = ALLTYPES
    //    //....|....[
    //    //....|....|...ELEMENT: joincond
    //    //....|....|...[
    //    //....|....|....|..ELEMENT: operation
    //    //....|....|....|....id = 10
    //    //....|....|....|....optype = equal
    //    //....|....|....|..[
    //    //....|....|....|....|.ELEMENT: operation
    //    //....|....|....|....|...id = 8
    //    //....|....|....|....|...optype = add
    //    //....|....|....|....|.[
    //    //....|....|....|....|.....ELEMENT: columnref
    //    //....|....|....|....|....|..alias = FSMALLINT
    //    //....|....|....|....|....|..column = FSMALLINT
    //    //....|....|....|....|....|..id = 2
    //    //....|....|....|....|....|..index = 1
    //    //....|....|....|....|....|..table = ALLTYPES
    //    //....|....|....|....|.....ELEMENT: columnref
    //    //....|....|....|....|....|..alias = FBIGINT
    //    //....|....|....|....|....|..column = FBIGINT
    //    //....|....|....|....|....|..id = 4
    //    //....|....|....|....|....|..index = 3
    //    //....|....|....|....|....|..table = ALLTYPES
    //    //....|....|....|....|.ELEMENT: value
    //    //....|....|....|....|...id = 9
    //    //....|....|....|....|...value = 0
    //    //....|....|....|....|...valuetype = INTEGER
    //    //
    //    //
    //
    //
    @SuppressWarnings("unchecked")
    @Test
    public void TestBISI() throws Exception {
        String sql    = "select * from alltypes where fsmallint + fbigint   = 0";
        IDTable idTable = new IDTable();
        VoltXMLElement element = m_HSQLInterface.getVoltXMLFromSQLUsingVoltSQLParser(sql, null, SQLKind.DQL);
        assertThat(element)
            .hasName(1, "select")
            .hasAllOf(
                withChildNamed(2, "columns",
                    withChildNamed(3, "columnref",
                                   "alias", "FTINYINT",
                                   "column", "FTINYINT",
                                   "table", "ALLTYPES",
                        withAttribute(4, "alias", "FTINYINT"),
                        withAttribute(5, "column", "FTINYINT"),
                        withIdAttribute(6, idTable),
                        withAttribute(7, "index", "0"),
                        withAttribute(8, "table", "ALLTYPES")),
                    withChildNamed(9, "columnref",
                                   "alias", "FSMALLINT",
                                   "column", "FSMALLINT",
                                   "table", "ALLTYPES",
                        withAttribute(10, "alias", "FSMALLINT"),
                        withAttribute(11, "column", "FSMALLINT"),
                        withIdAttribute(12, idTable),
                        withAttribute(13, "index", "1"),
                        withAttribute(14, "table", "ALLTYPES")),
                    withChildNamed(15, "columnref",
                                   "alias", "FINTEGER",
                                   "column", "FINTEGER",
                                   "table", "ALLTYPES",
                        withAttribute(16, "alias", "FINTEGER"),
                        withAttribute(17, "column", "FINTEGER"),
                        withIdAttribute(18, idTable),
                        withAttribute(19, "index", "2"),
                        withAttribute(20, "table", "ALLTYPES")),
                    withChildNamed(21, "columnref",
                                   "alias", "FBIGINT",
                                   "column", "FBIGINT",
                                   "table", "ALLTYPES",
                        withAttribute(22, "alias", "FBIGINT"),
                        withAttribute(23, "column", "FBIGINT"),
                        withIdAttribute(24, idTable),
                        withAttribute(25, "index", "3"),
                        withAttribute(26, "table", "ALLTYPES")),
                    withChildNamed(27, "columnref",
                                   "alias", "FFLOAT",
                                   "column", "FFLOAT",
                                   "table", "ALLTYPES",
                        withAttribute(28, "alias", "FFLOAT"),
                        withAttribute(29, "column", "FFLOAT"),
                        withIdAttribute(30, idTable),
                        withAttribute(31, "index", "4"),
                        withAttribute(32, "table", "ALLTYPES")),
                    withChildNamed(33, "columnref",
                                   "alias", "FDECIMAL",
                                   "column", "FDECIMAL",
                                   "table", "ALLTYPES",
                        withAttribute(34, "alias", "FDECIMAL"),
                        withAttribute(35, "column", "FDECIMAL"),
                        withIdAttribute(36, idTable),
                        withAttribute(37, "index", "5"),
                        withAttribute(38, "table", "ALLTYPES")),
                    withChildNamed(39, "columnref",
                                   "alias", "FTIMESTAMP",
                                   "column", "FTIMESTAMP",
                                   "table", "ALLTYPES",
                        withAttribute(40, "alias", "FTIMESTAMP"),
                        withAttribute(41, "column", "FTIMESTAMP"),
                        withIdAttribute(42, idTable),
                        withAttribute(43, "index", "6"),
                        withAttribute(44, "table", "ALLTYPES"))),
                withChildNamed(45, "parameters"),
                withChildNamed(46, "tablescans",
                    withChildNamed(47, "tablescan",
                        withAttribute(48, "jointype", "inner"),
                        withAttribute(49, "table", "ALLTYPES"),
                        withChildNamed(50, "joincond",
                            withChildNamed(51, "operation",
                                           "optype", "equal",
                                withIdAttribute(52, idTable),
                                withAttribute(53, "optype", "equal"),
                                withChildNamed(54, "operation",
                                               "optype", "add",
                                    withIdAttribute(55, idTable),
                                    withAttribute(56, "optype", "add"),
                                    withChildNamed(57, "columnref",
                                                   "alias", "FSMALLINT",
                                                   "column", "FSMALLINT",
                                                   "table", "ALLTYPES",
                                        withAttribute(58, "alias", "FSMALLINT"),
                                        withAttribute(59, "column", "FSMALLINT"),
                                        withIdAttribute(60, idTable),
                                        withAttribute(61, "index", "1"),
                                        withAttribute(62, "table", "ALLTYPES")),
                                    withChildNamed(63, "columnref",
                                                   "alias", "FBIGINT",
                                                   "column", "FBIGINT",
                                                   "table", "ALLTYPES",
                                        withAttribute(64, "alias", "FBIGINT"),
                                        withAttribute(65, "column", "FBIGINT"),
                                        withIdAttribute(66, idTable),
                                        withAttribute(67, "index", "3"),
                                        withAttribute(68, "table", "ALLTYPES"))),
                                withChildNamed(69, "value",
                                    withIdAttribute(70, idTable),
                                    withAttribute(71, "value", "0"),
                                    withAttribute(72, "valuetype", "INTEGER")))))));
    }

    // Pattern XML:
    //    //.ELEMENT: select
    //    //.[
    //    //.....ELEMENT: columns
    //    //.....[
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = FTINYINT
    //    //....|....|.column = FTINYINT
    //    //....|....|.id = 1
    //    //....|....|.index = 0
    //    //....|....|.table = ALLTYPES
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = FSMALLINT
    //    //....|....|.column = FSMALLINT
    //    //....|....|.id = 2
    //    //....|....|.index = 1
    //    //....|....|.table = ALLTYPES
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = FINTEGER
    //    //....|....|.column = FINTEGER
    //    //....|....|.id = 3
    //    //....|....|.index = 2
    //    //....|....|.table = ALLTYPES
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = FBIGINT
    //    //....|....|.column = FBIGINT
    //    //....|....|.id = 4
    //    //....|....|.index = 3
    //    //....|....|.table = ALLTYPES
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = FFLOAT
    //    //....|....|.column = FFLOAT
    //    //....|....|.id = 5
    //    //....|....|.index = 4
    //    //....|....|.table = ALLTYPES
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = FDECIMAL
    //    //....|....|.column = FDECIMAL
    //    //....|....|.id = 6
    //    //....|....|.index = 5
    //    //....|....|.table = ALLTYPES
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = FTIMESTAMP
    //    //....|....|.column = FTIMESTAMP
    //    //....|....|.id = 7
    //    //....|....|.index = 6
    //    //....|....|.table = ALLTYPES
    //    //.....ELEMENT: parameters
    //    //.....ELEMENT: tablescans
    //    //.....[
    //    //....|....ELEMENT: tablescan
    //    //....|....|.jointype = inner
    //    //....|....|.table = ALLTYPES
    //    //....|....[
    //    //....|....|...ELEMENT: joincond
    //    //....|....|...[
    //    //....|....|....|..ELEMENT: operation
    //    //....|....|....|....id = 10
    //    //....|....|....|....optype = equal
    //    //....|....|....|..[
    //    //....|....|....|....|.ELEMENT: operation
    //    //....|....|....|....|...id = 8
    //    //....|....|....|....|...optype = add
    //    //....|....|....|....|.[
    //    //....|....|....|....|.....ELEMENT: columnref
    //    //....|....|....|....|....|..alias = FFLOAT
    //    //....|....|....|....|....|..column = FFLOAT
    //    //....|....|....|....|....|..id = 5
    //    //....|....|....|....|....|..index = 4
    //    //....|....|....|....|....|..table = ALLTYPES
    //    //....|....|....|....|.....ELEMENT: columnref
    //    //....|....|....|....|....|..alias = FSMALLINT
    //    //....|....|....|....|....|..column = FSMALLINT
    //    //....|....|....|....|....|..id = 2
    //    //....|....|....|....|....|..index = 1
    //    //....|....|....|....|....|..table = ALLTYPES
    //    //....|....|....|....|.ELEMENT: value
    //    //....|....|....|....|...id = 9
    //    //....|....|....|....|...value = 0
    //    //....|....|....|....|...valuetype = INTEGER
    //    //
    //    //
    //
    //
    @SuppressWarnings("unchecked")
    @Test
    public void TestSIF() throws Exception {
        String sql    = "select * from alltypes where ffloat    + fsmallint = 0";
        IDTable idTable = new IDTable();
        VoltXMLElement element = m_HSQLInterface.getVoltXMLFromSQLUsingVoltSQLParser(sql, null, SQLKind.DQL);
        assertThat(element)
            .hasName(1, "select")
            .hasAllOf(
                withChildNamed(2, "columns",
                    withChildNamed(3, "columnref",
                                   "alias", "FTINYINT",
                                   "column", "FTINYINT",
                                   "table", "ALLTYPES",
                        withAttribute(4, "alias", "FTINYINT"),
                        withAttribute(5, "column", "FTINYINT"),
                        withIdAttribute(6, idTable),
                        withAttribute(7, "index", "0"),
                        withAttribute(8, "table", "ALLTYPES")),
                    withChildNamed(9, "columnref",
                                   "alias", "FSMALLINT",
                                   "column", "FSMALLINT",
                                   "table", "ALLTYPES",
                        withAttribute(10, "alias", "FSMALLINT"),
                        withAttribute(11, "column", "FSMALLINT"),
                        withIdAttribute(12, idTable),
                        withAttribute(13, "index", "1"),
                        withAttribute(14, "table", "ALLTYPES")),
                    withChildNamed(15, "columnref",
                                   "alias", "FINTEGER",
                                   "column", "FINTEGER",
                                   "table", "ALLTYPES",
                        withAttribute(16, "alias", "FINTEGER"),
                        withAttribute(17, "column", "FINTEGER"),
                        withIdAttribute(18, idTable),
                        withAttribute(19, "index", "2"),
                        withAttribute(20, "table", "ALLTYPES")),
                    withChildNamed(21, "columnref",
                                   "alias", "FBIGINT",
                                   "column", "FBIGINT",
                                   "table", "ALLTYPES",
                        withAttribute(22, "alias", "FBIGINT"),
                        withAttribute(23, "column", "FBIGINT"),
                        withIdAttribute(24, idTable),
                        withAttribute(25, "index", "3"),
                        withAttribute(26, "table", "ALLTYPES")),
                    withChildNamed(27, "columnref",
                                   "alias", "FFLOAT",
                                   "column", "FFLOAT",
                                   "table", "ALLTYPES",
                        withAttribute(28, "alias", "FFLOAT"),
                        withAttribute(29, "column", "FFLOAT"),
                        withIdAttribute(30, idTable),
                        withAttribute(31, "index", "4"),
                        withAttribute(32, "table", "ALLTYPES")),
                    withChildNamed(33, "columnref",
                                   "alias", "FDECIMAL",
                                   "column", "FDECIMAL",
                                   "table", "ALLTYPES",
                        withAttribute(34, "alias", "FDECIMAL"),
                        withAttribute(35, "column", "FDECIMAL"),
                        withIdAttribute(36, idTable),
                        withAttribute(37, "index", "5"),
                        withAttribute(38, "table", "ALLTYPES")),
                    withChildNamed(39, "columnref",
                                   "alias", "FTIMESTAMP",
                                   "column", "FTIMESTAMP",
                                   "table", "ALLTYPES",
                        withAttribute(40, "alias", "FTIMESTAMP"),
                        withAttribute(41, "column", "FTIMESTAMP"),
                        withIdAttribute(42, idTable),
                        withAttribute(43, "index", "6"),
                        withAttribute(44, "table", "ALLTYPES"))),
                withChildNamed(45, "parameters"),
                withChildNamed(46, "tablescans",
                    withChildNamed(47, "tablescan",
                        withAttribute(48, "jointype", "inner"),
                        withAttribute(49, "table", "ALLTYPES"),
                        withChildNamed(50, "joincond",
                            withChildNamed(51, "operation",
                                           "optype", "equal",
                                withIdAttribute(52, idTable),
                                withAttribute(53, "optype", "equal"),
                                withChildNamed(54, "operation",
                                               "optype", "add",
                                    withIdAttribute(55, idTable),
                                    withAttribute(56, "optype", "add"),
                                    withChildNamed(57, "columnref",
                                                   "alias", "FFLOAT",
                                                   "column", "FFLOAT",
                                                   "table", "ALLTYPES",
                                        withAttribute(58, "alias", "FFLOAT"),
                                        withAttribute(59, "column", "FFLOAT"),
                                        withIdAttribute(60, idTable),
                                        withAttribute(61, "index", "4"),
                                        withAttribute(62, "table", "ALLTYPES")),
                                    withChildNamed(63, "columnref",
                                                   "alias", "FSMALLINT",
                                                   "column", "FSMALLINT",
                                                   "table", "ALLTYPES",
                                        withAttribute(64, "alias", "FSMALLINT"),
                                        withAttribute(65, "column", "FSMALLINT"),
                                        withIdAttribute(66, idTable),
                                        withAttribute(67, "index", "1"),
                                        withAttribute(68, "table", "ALLTYPES"))),
                                withChildNamed(69, "value",
                                    withIdAttribute(70, idTable),
                                    withAttribute(71, "value", "0"),
                                    withAttribute(72, "valuetype", "INTEGER")))))));
    }

    // Pattern XML:
    //    //.ELEMENT: select
    //    //.[
    //    //.....ELEMENT: columns
    //    //.....[
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = FTINYINT
    //    //....|....|.column = FTINYINT
    //    //....|....|.id = 1
    //    //....|....|.index = 0
    //    //....|....|.table = ALLTYPES
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = FSMALLINT
    //    //....|....|.column = FSMALLINT
    //    //....|....|.id = 2
    //    //....|....|.index = 1
    //    //....|....|.table = ALLTYPES
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = FINTEGER
    //    //....|....|.column = FINTEGER
    //    //....|....|.id = 3
    //    //....|....|.index = 2
    //    //....|....|.table = ALLTYPES
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = FBIGINT
    //    //....|....|.column = FBIGINT
    //    //....|....|.id = 4
    //    //....|....|.index = 3
    //    //....|....|.table = ALLTYPES
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = FFLOAT
    //    //....|....|.column = FFLOAT
    //    //....|....|.id = 5
    //    //....|....|.index = 4
    //    //....|....|.table = ALLTYPES
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = FDECIMAL
    //    //....|....|.column = FDECIMAL
    //    //....|....|.id = 6
    //    //....|....|.index = 5
    //    //....|....|.table = ALLTYPES
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = FTIMESTAMP
    //    //....|....|.column = FTIMESTAMP
    //    //....|....|.id = 7
    //    //....|....|.index = 6
    //    //....|....|.table = ALLTYPES
    //    //.....ELEMENT: parameters
    //    //.....ELEMENT: tablescans
    //    //.....[
    //    //....|....ELEMENT: tablescan
    //    //....|....|.jointype = inner
    //    //....|....|.table = ALLTYPES
    //    //....|....[
    //    //....|....|...ELEMENT: joincond
    //    //....|....|...[
    //    //....|....|....|..ELEMENT: operation
    //    //....|....|....|....id = 10
    //    //....|....|....|....optype = equal
    //    //....|....|....|..[
    //    //....|....|....|....|.ELEMENT: operation
    //    //....|....|....|....|...id = 8
    //    //....|....|....|....|...optype = add
    //    //....|....|....|....|.[
    //    //....|....|....|....|.....ELEMENT: columnref
    //    //....|....|....|....|....|..alias = FSMALLINT
    //    //....|....|....|....|....|..column = FSMALLINT
    //    //....|....|....|....|....|..id = 2
    //    //....|....|....|....|....|..index = 1
    //    //....|....|....|....|....|..table = ALLTYPES
    //    //....|....|....|....|.....ELEMENT: columnref
    //    //....|....|....|....|....|..alias = FFLOAT
    //    //....|....|....|....|....|..column = FFLOAT
    //    //....|....|....|....|....|..id = 5
    //    //....|....|....|....|....|..index = 4
    //    //....|....|....|....|....|..table = ALLTYPES
    //    //....|....|....|....|.ELEMENT: value
    //    //....|....|....|....|...id = 9
    //    //....|....|....|....|...value = 0
    //    //....|....|....|....|...valuetype = INTEGER
    //    //
    //    //
    //
    //
    @SuppressWarnings("unchecked")
    @Test
    public void TestFSI() throws Exception {
        String sql    = "select * from alltypes where fsmallint + ffloat    = 0";
        IDTable idTable = new IDTable();
        VoltXMLElement element = m_HSQLInterface.getVoltXMLFromSQLUsingVoltSQLParser(sql, null, SQLKind.DQL);
        assertThat(element)
            .hasName(1, "select")
            .hasAllOf(
                withChildNamed(2, "columns",
                    withChildNamed(3, "columnref",
                                   "alias", "FTINYINT",
                                   "column", "FTINYINT",
                                   "table", "ALLTYPES",
                        withAttribute(4, "alias", "FTINYINT"),
                        withAttribute(5, "column", "FTINYINT"),
                        withIdAttribute(6, idTable),
                        withAttribute(7, "index", "0"),
                        withAttribute(8, "table", "ALLTYPES")),
                    withChildNamed(9, "columnref",
                                   "alias", "FSMALLINT",
                                   "column", "FSMALLINT",
                                   "table", "ALLTYPES",
                        withAttribute(10, "alias", "FSMALLINT"),
                        withAttribute(11, "column", "FSMALLINT"),
                        withIdAttribute(12, idTable),
                        withAttribute(13, "index", "1"),
                        withAttribute(14, "table", "ALLTYPES")),
                    withChildNamed(15, "columnref",
                                   "alias", "FINTEGER",
                                   "column", "FINTEGER",
                                   "table", "ALLTYPES",
                        withAttribute(16, "alias", "FINTEGER"),
                        withAttribute(17, "column", "FINTEGER"),
                        withIdAttribute(18, idTable),
                        withAttribute(19, "index", "2"),
                        withAttribute(20, "table", "ALLTYPES")),
                    withChildNamed(21, "columnref",
                                   "alias", "FBIGINT",
                                   "column", "FBIGINT",
                                   "table", "ALLTYPES",
                        withAttribute(22, "alias", "FBIGINT"),
                        withAttribute(23, "column", "FBIGINT"),
                        withIdAttribute(24, idTable),
                        withAttribute(25, "index", "3"),
                        withAttribute(26, "table", "ALLTYPES")),
                    withChildNamed(27, "columnref",
                                   "alias", "FFLOAT",
                                   "column", "FFLOAT",
                                   "table", "ALLTYPES",
                        withAttribute(28, "alias", "FFLOAT"),
                        withAttribute(29, "column", "FFLOAT"),
                        withIdAttribute(30, idTable),
                        withAttribute(31, "index", "4"),
                        withAttribute(32, "table", "ALLTYPES")),
                    withChildNamed(33, "columnref",
                                   "alias", "FDECIMAL",
                                   "column", "FDECIMAL",
                                   "table", "ALLTYPES",
                        withAttribute(34, "alias", "FDECIMAL"),
                        withAttribute(35, "column", "FDECIMAL"),
                        withIdAttribute(36, idTable),
                        withAttribute(37, "index", "5"),
                        withAttribute(38, "table", "ALLTYPES")),
                    withChildNamed(39, "columnref",
                                   "alias", "FTIMESTAMP",
                                   "column", "FTIMESTAMP",
                                   "table", "ALLTYPES",
                        withAttribute(40, "alias", "FTIMESTAMP"),
                        withAttribute(41, "column", "FTIMESTAMP"),
                        withIdAttribute(42, idTable),
                        withAttribute(43, "index", "6"),
                        withAttribute(44, "table", "ALLTYPES"))),
                withChildNamed(45, "parameters"),
                withChildNamed(46, "tablescans",
                    withChildNamed(47, "tablescan",
                        withAttribute(48, "jointype", "inner"),
                        withAttribute(49, "table", "ALLTYPES"),
                        withChildNamed(50, "joincond",
                            withChildNamed(51, "operation",
                                           "optype", "equal",
                                withIdAttribute(52, idTable),
                                withAttribute(53, "optype", "equal"),
                                withChildNamed(54, "operation",
                                               "optype", "add",
                                    withIdAttribute(55, idTable),
                                    withAttribute(56, "optype", "add"),
                                    withChildNamed(57, "columnref",
                                                   "alias", "FSMALLINT",
                                                   "column", "FSMALLINT",
                                                   "table", "ALLTYPES",
                                        withAttribute(58, "alias", "FSMALLINT"),
                                        withAttribute(59, "column", "FSMALLINT"),
                                        withIdAttribute(60, idTable),
                                        withAttribute(61, "index", "1"),
                                        withAttribute(62, "table", "ALLTYPES")),
                                    withChildNamed(63, "columnref",
                                                   "alias", "FFLOAT",
                                                   "column", "FFLOAT",
                                                   "table", "ALLTYPES",
                                        withAttribute(64, "alias", "FFLOAT"),
                                        withAttribute(65, "column", "FFLOAT"),
                                        withIdAttribute(66, idTable),
                                        withAttribute(67, "index", "4"),
                                        withAttribute(68, "table", "ALLTYPES"))),
                                withChildNamed(69, "value",
                                    withIdAttribute(70, idTable),
                                    withAttribute(71, "value", "0"),
                                    withAttribute(72, "valuetype", "INTEGER")))))));
    }

    // Pattern XML:
    //    //.ELEMENT: select
    //    //.[
    //    //.....ELEMENT: columns
    //    //.....[
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = FTINYINT
    //    //....|....|.column = FTINYINT
    //    //....|....|.id = 1
    //    //....|....|.index = 0
    //    //....|....|.table = ALLTYPES
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = FSMALLINT
    //    //....|....|.column = FSMALLINT
    //    //....|....|.id = 2
    //    //....|....|.index = 1
    //    //....|....|.table = ALLTYPES
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = FINTEGER
    //    //....|....|.column = FINTEGER
    //    //....|....|.id = 3
    //    //....|....|.index = 2
    //    //....|....|.table = ALLTYPES
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = FBIGINT
    //    //....|....|.column = FBIGINT
    //    //....|....|.id = 4
    //    //....|....|.index = 3
    //    //....|....|.table = ALLTYPES
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = FFLOAT
    //    //....|....|.column = FFLOAT
    //    //....|....|.id = 5
    //    //....|....|.index = 4
    //    //....|....|.table = ALLTYPES
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = FDECIMAL
    //    //....|....|.column = FDECIMAL
    //    //....|....|.id = 6
    //    //....|....|.index = 5
    //    //....|....|.table = ALLTYPES
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = FTIMESTAMP
    //    //....|....|.column = FTIMESTAMP
    //    //....|....|.id = 7
    //    //....|....|.index = 6
    //    //....|....|.table = ALLTYPES
    //    //.....ELEMENT: parameters
    //    //.....ELEMENT: tablescans
    //    //.....[
    //    //....|....ELEMENT: tablescan
    //    //....|....|.jointype = inner
    //    //....|....|.table = ALLTYPES
    //    //....|....[
    //    //....|....|...ELEMENT: joincond
    //    //....|....|...[
    //    //....|....|....|..ELEMENT: operation
    //    //....|....|....|....id = 10
    //    //....|....|....|....optype = equal
    //    //....|....|....|..[
    //    //....|....|....|....|.ELEMENT: operation
    //    //....|....|....|....|...id = 8
    //    //....|....|....|....|...optype = add
    //    //....|....|....|....|.[
    //    //....|....|....|....|.....ELEMENT: columnref
    //    //....|....|....|....|....|..alias = FDECIMAL
    //    //....|....|....|....|....|..column = FDECIMAL
    //    //....|....|....|....|....|..id = 6
    //    //....|....|....|....|....|..index = 5
    //    //....|....|....|....|....|..table = ALLTYPES
    //    //....|....|....|....|.....ELEMENT: columnref
    //    //....|....|....|....|....|..alias = FSMALLINT
    //    //....|....|....|....|....|..column = FSMALLINT
    //    //....|....|....|....|....|..id = 2
    //    //....|....|....|....|....|..index = 1
    //    //....|....|....|....|....|..table = ALLTYPES
    //    //....|....|....|....|.ELEMENT: value
    //    //....|....|....|....|...id = 9
    //    //....|....|....|....|...value = 0
    //    //....|....|....|....|...valuetype = INTEGER
    //    //
    //    //
    //
    //
    @SuppressWarnings("unchecked")
    @Test
    public void TestSID() throws Exception {
        String sql    = "select * from alltypes where fdecimal  + fsmallint = 0";
        IDTable idTable = new IDTable();
        VoltXMLElement element = m_HSQLInterface.getVoltXMLFromSQLUsingVoltSQLParser(sql, null, SQLKind.DQL);
        assertThat(element)
            .hasName(1, "select")
            .hasAllOf(
                withChildNamed(2, "columns",
                    withChildNamed(3, "columnref",
                                   "alias", "FTINYINT",
                                   "column", "FTINYINT",
                                   "table", "ALLTYPES",
                        withAttribute(4, "alias", "FTINYINT"),
                        withAttribute(5, "column", "FTINYINT"),
                        withIdAttribute(6, idTable),
                        withAttribute(7, "index", "0"),
                        withAttribute(8, "table", "ALLTYPES")),
                    withChildNamed(9, "columnref",
                                   "alias", "FSMALLINT",
                                   "column", "FSMALLINT",
                                   "table", "ALLTYPES",
                        withAttribute(10, "alias", "FSMALLINT"),
                        withAttribute(11, "column", "FSMALLINT"),
                        withIdAttribute(12, idTable),
                        withAttribute(13, "index", "1"),
                        withAttribute(14, "table", "ALLTYPES")),
                    withChildNamed(15, "columnref",
                                   "alias", "FINTEGER",
                                   "column", "FINTEGER",
                                   "table", "ALLTYPES",
                        withAttribute(16, "alias", "FINTEGER"),
                        withAttribute(17, "column", "FINTEGER"),
                        withIdAttribute(18, idTable),
                        withAttribute(19, "index", "2"),
                        withAttribute(20, "table", "ALLTYPES")),
                    withChildNamed(21, "columnref",
                                   "alias", "FBIGINT",
                                   "column", "FBIGINT",
                                   "table", "ALLTYPES",
                        withAttribute(22, "alias", "FBIGINT"),
                        withAttribute(23, "column", "FBIGINT"),
                        withIdAttribute(24, idTable),
                        withAttribute(25, "index", "3"),
                        withAttribute(26, "table", "ALLTYPES")),
                    withChildNamed(27, "columnref",
                                   "alias", "FFLOAT",
                                   "column", "FFLOAT",
                                   "table", "ALLTYPES",
                        withAttribute(28, "alias", "FFLOAT"),
                        withAttribute(29, "column", "FFLOAT"),
                        withIdAttribute(30, idTable),
                        withAttribute(31, "index", "4"),
                        withAttribute(32, "table", "ALLTYPES")),
                    withChildNamed(33, "columnref",
                                   "alias", "FDECIMAL",
                                   "column", "FDECIMAL",
                                   "table", "ALLTYPES",
                        withAttribute(34, "alias", "FDECIMAL"),
                        withAttribute(35, "column", "FDECIMAL"),
                        withIdAttribute(36, idTable),
                        withAttribute(37, "index", "5"),
                        withAttribute(38, "table", "ALLTYPES")),
                    withChildNamed(39, "columnref",
                                   "alias", "FTIMESTAMP",
                                   "column", "FTIMESTAMP",
                                   "table", "ALLTYPES",
                        withAttribute(40, "alias", "FTIMESTAMP"),
                        withAttribute(41, "column", "FTIMESTAMP"),
                        withIdAttribute(42, idTable),
                        withAttribute(43, "index", "6"),
                        withAttribute(44, "table", "ALLTYPES"))),
                withChildNamed(45, "parameters"),
                withChildNamed(46, "tablescans",
                    withChildNamed(47, "tablescan",
                        withAttribute(48, "jointype", "inner"),
                        withAttribute(49, "table", "ALLTYPES"),
                        withChildNamed(50, "joincond",
                            withChildNamed(51, "operation",
                                           "optype", "equal",
                                withIdAttribute(52, idTable),
                                withAttribute(53, "optype", "equal"),
                                withChildNamed(54, "operation",
                                               "optype", "add",
                                    withIdAttribute(55, idTable),
                                    withAttribute(56, "optype", "add"),
                                    withChildNamed(57, "columnref",
                                                   "alias", "FDECIMAL",
                                                   "column", "FDECIMAL",
                                                   "table", "ALLTYPES",
                                        withAttribute(58, "alias", "FDECIMAL"),
                                        withAttribute(59, "column", "FDECIMAL"),
                                        withIdAttribute(60, idTable),
                                        withAttribute(61, "index", "5"),
                                        withAttribute(62, "table", "ALLTYPES")),
                                    withChildNamed(63, "columnref",
                                                   "alias", "FSMALLINT",
                                                   "column", "FSMALLINT",
                                                   "table", "ALLTYPES",
                                        withAttribute(64, "alias", "FSMALLINT"),
                                        withAttribute(65, "column", "FSMALLINT"),
                                        withIdAttribute(66, idTable),
                                        withAttribute(67, "index", "1"),
                                        withAttribute(68, "table", "ALLTYPES"))),
                                withChildNamed(69, "value",
                                    withIdAttribute(70, idTable),
                                    withAttribute(71, "value", "0"),
                                    withAttribute(72, "valuetype", "INTEGER")))))));
    }

    // Pattern XML:
    //    //.ELEMENT: select
    //    //.[
    //    //.....ELEMENT: columns
    //    //.....[
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = FTINYINT
    //    //....|....|.column = FTINYINT
    //    //....|....|.id = 1
    //    //....|....|.index = 0
    //    //....|....|.table = ALLTYPES
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = FSMALLINT
    //    //....|....|.column = FSMALLINT
    //    //....|....|.id = 2
    //    //....|....|.index = 1
    //    //....|....|.table = ALLTYPES
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = FINTEGER
    //    //....|....|.column = FINTEGER
    //    //....|....|.id = 3
    //    //....|....|.index = 2
    //    //....|....|.table = ALLTYPES
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = FBIGINT
    //    //....|....|.column = FBIGINT
    //    //....|....|.id = 4
    //    //....|....|.index = 3
    //    //....|....|.table = ALLTYPES
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = FFLOAT
    //    //....|....|.column = FFLOAT
    //    //....|....|.id = 5
    //    //....|....|.index = 4
    //    //....|....|.table = ALLTYPES
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = FDECIMAL
    //    //....|....|.column = FDECIMAL
    //    //....|....|.id = 6
    //    //....|....|.index = 5
    //    //....|....|.table = ALLTYPES
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = FTIMESTAMP
    //    //....|....|.column = FTIMESTAMP
    //    //....|....|.id = 7
    //    //....|....|.index = 6
    //    //....|....|.table = ALLTYPES
    //    //.....ELEMENT: parameters
    //    //.....ELEMENT: tablescans
    //    //.....[
    //    //....|....ELEMENT: tablescan
    //    //....|....|.jointype = inner
    //    //....|....|.table = ALLTYPES
    //    //....|....[
    //    //....|....|...ELEMENT: joincond
    //    //....|....|...[
    //    //....|....|....|..ELEMENT: operation
    //    //....|....|....|....id = 10
    //    //....|....|....|....optype = equal
    //    //....|....|....|..[
    //    //....|....|....|....|.ELEMENT: operation
    //    //....|....|....|....|...id = 8
    //    //....|....|....|....|...optype = add
    //    //....|....|....|....|.[
    //    //....|....|....|....|.....ELEMENT: columnref
    //    //....|....|....|....|....|..alias = FSMALLINT
    //    //....|....|....|....|....|..column = FSMALLINT
    //    //....|....|....|....|....|..id = 2
    //    //....|....|....|....|....|..index = 1
    //    //....|....|....|....|....|..table = ALLTYPES
    //    //....|....|....|....|.....ELEMENT: columnref
    //    //....|....|....|....|....|..alias = FDECIMAL
    //    //....|....|....|....|....|..column = FDECIMAL
    //    //....|....|....|....|....|..id = 6
    //    //....|....|....|....|....|..index = 5
    //    //....|....|....|....|....|..table = ALLTYPES
    //    //....|....|....|....|.ELEMENT: value
    //    //....|....|....|....|...id = 9
    //    //....|....|....|....|...value = 0
    //    //....|....|....|....|...valuetype = INTEGER
    //    //
    //    //
    //
    //
    @SuppressWarnings("unchecked")
    @Test
    public void TestDSI() throws Exception {
        String sql    = "select * from alltypes where fsmallint + fdecimal  = 0";
        IDTable idTable = new IDTable();
        VoltXMLElement element = m_HSQLInterface.getVoltXMLFromSQLUsingVoltSQLParser(sql, null, SQLKind.DQL);
        assertThat(element)
            .hasName(1, "select")
            .hasAllOf(
                withChildNamed(2, "columns",
                    withChildNamed(3, "columnref",
                                   "alias", "FTINYINT",
                                   "column", "FTINYINT",
                                   "table", "ALLTYPES",
                        withAttribute(4, "alias", "FTINYINT"),
                        withAttribute(5, "column", "FTINYINT"),
                        withIdAttribute(6, idTable),
                        withAttribute(7, "index", "0"),
                        withAttribute(8, "table", "ALLTYPES")),
                    withChildNamed(9, "columnref",
                                   "alias", "FSMALLINT",
                                   "column", "FSMALLINT",
                                   "table", "ALLTYPES",
                        withAttribute(10, "alias", "FSMALLINT"),
                        withAttribute(11, "column", "FSMALLINT"),
                        withIdAttribute(12, idTable),
                        withAttribute(13, "index", "1"),
                        withAttribute(14, "table", "ALLTYPES")),
                    withChildNamed(15, "columnref",
                                   "alias", "FINTEGER",
                                   "column", "FINTEGER",
                                   "table", "ALLTYPES",
                        withAttribute(16, "alias", "FINTEGER"),
                        withAttribute(17, "column", "FINTEGER"),
                        withIdAttribute(18, idTable),
                        withAttribute(19, "index", "2"),
                        withAttribute(20, "table", "ALLTYPES")),
                    withChildNamed(21, "columnref",
                                   "alias", "FBIGINT",
                                   "column", "FBIGINT",
                                   "table", "ALLTYPES",
                        withAttribute(22, "alias", "FBIGINT"),
                        withAttribute(23, "column", "FBIGINT"),
                        withIdAttribute(24, idTable),
                        withAttribute(25, "index", "3"),
                        withAttribute(26, "table", "ALLTYPES")),
                    withChildNamed(27, "columnref",
                                   "alias", "FFLOAT",
                                   "column", "FFLOAT",
                                   "table", "ALLTYPES",
                        withAttribute(28, "alias", "FFLOAT"),
                        withAttribute(29, "column", "FFLOAT"),
                        withIdAttribute(30, idTable),
                        withAttribute(31, "index", "4"),
                        withAttribute(32, "table", "ALLTYPES")),
                    withChildNamed(33, "columnref",
                                   "alias", "FDECIMAL",
                                   "column", "FDECIMAL",
                                   "table", "ALLTYPES",
                        withAttribute(34, "alias", "FDECIMAL"),
                        withAttribute(35, "column", "FDECIMAL"),
                        withIdAttribute(36, idTable),
                        withAttribute(37, "index", "5"),
                        withAttribute(38, "table", "ALLTYPES")),
                    withChildNamed(39, "columnref",
                                   "alias", "FTIMESTAMP",
                                   "column", "FTIMESTAMP",
                                   "table", "ALLTYPES",
                        withAttribute(40, "alias", "FTIMESTAMP"),
                        withAttribute(41, "column", "FTIMESTAMP"),
                        withIdAttribute(42, idTable),
                        withAttribute(43, "index", "6"),
                        withAttribute(44, "table", "ALLTYPES"))),
                withChildNamed(45, "parameters"),
                withChildNamed(46, "tablescans",
                    withChildNamed(47, "tablescan",
                        withAttribute(48, "jointype", "inner"),
                        withAttribute(49, "table", "ALLTYPES"),
                        withChildNamed(50, "joincond",
                            withChildNamed(51, "operation",
                                           "optype", "equal",
                                withIdAttribute(52, idTable),
                                withAttribute(53, "optype", "equal"),
                                withChildNamed(54, "operation",
                                               "optype", "add",
                                    withIdAttribute(55, idTable),
                                    withAttribute(56, "optype", "add"),
                                    withChildNamed(57, "columnref",
                                                   "alias", "FSMALLINT",
                                                   "column", "FSMALLINT",
                                                   "table", "ALLTYPES",
                                        withAttribute(58, "alias", "FSMALLINT"),
                                        withAttribute(59, "column", "FSMALLINT"),
                                        withIdAttribute(60, idTable),
                                        withAttribute(61, "index", "1"),
                                        withAttribute(62, "table", "ALLTYPES")),
                                    withChildNamed(63, "columnref",
                                                   "alias", "FDECIMAL",
                                                   "column", "FDECIMAL",
                                                   "table", "ALLTYPES",
                                        withAttribute(64, "alias", "FDECIMAL"),
                                        withAttribute(65, "column", "FDECIMAL"),
                                        withIdAttribute(66, idTable),
                                        withAttribute(67, "index", "5"),
                                        withAttribute(68, "table", "ALLTYPES"))),
                                withChildNamed(69, "value",
                                    withIdAttribute(70, idTable),
                                    withAttribute(71, "value", "0"),
                                    withAttribute(72, "valuetype", "INTEGER")))))));
    }

    // Pattern XML:
    //    //.ELEMENT: select
    //    //.[
    //    //.....ELEMENT: columns
    //    //.....[
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = FTINYINT
    //    //....|....|.column = FTINYINT
    //    //....|....|.id = 1
    //    //....|....|.index = 0
    //    //....|....|.table = ALLTYPES
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = FSMALLINT
    //    //....|....|.column = FSMALLINT
    //    //....|....|.id = 2
    //    //....|....|.index = 1
    //    //....|....|.table = ALLTYPES
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = FINTEGER
    //    //....|....|.column = FINTEGER
    //    //....|....|.id = 3
    //    //....|....|.index = 2
    //    //....|....|.table = ALLTYPES
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = FBIGINT
    //    //....|....|.column = FBIGINT
    //    //....|....|.id = 4
    //    //....|....|.index = 3
    //    //....|....|.table = ALLTYPES
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = FFLOAT
    //    //....|....|.column = FFLOAT
    //    //....|....|.id = 5
    //    //....|....|.index = 4
    //    //....|....|.table = ALLTYPES
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = FDECIMAL
    //    //....|....|.column = FDECIMAL
    //    //....|....|.id = 6
    //    //....|....|.index = 5
    //    //....|....|.table = ALLTYPES
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = FTIMESTAMP
    //    //....|....|.column = FTIMESTAMP
    //    //....|....|.id = 7
    //    //....|....|.index = 6
    //    //....|....|.table = ALLTYPES
    //    //.....ELEMENT: parameters
    //    //.....ELEMENT: tablescans
    //    //.....[
    //    //....|....ELEMENT: tablescan
    //    //....|....|.jointype = inner
    //    //....|....|.table = ALLTYPES
    //    //....|....[
    //    //....|....|...ELEMENT: joincond
    //    //....|....|...[
    //    //....|....|....|..ELEMENT: operation
    //    //....|....|....|....id = 10
    //    //....|....|....|....optype = equal
    //    //....|....|....|..[
    //    //....|....|....|....|.ELEMENT: operation
    //    //....|....|....|....|...id = 8
    //    //....|....|....|....|...optype = add
    //    //....|....|....|....|.[
    //    //....|....|....|....|.....ELEMENT: columnref
    //    //....|....|....|....|....|..alias = FBIGINT
    //    //....|....|....|....|....|..column = FBIGINT
    //    //....|....|....|....|....|..id = 4
    //    //....|....|....|....|....|..index = 3
    //    //....|....|....|....|....|..table = ALLTYPES
    //    //....|....|....|....|.....ELEMENT: columnref
    //    //....|....|....|....|....|..alias = FINTEGER
    //    //....|....|....|....|....|..column = FINTEGER
    //    //....|....|....|....|....|..id = 3
    //    //....|....|....|....|....|..index = 2
    //    //....|....|....|....|....|..table = ALLTYPES
    //    //....|....|....|....|.ELEMENT: value
    //    //....|....|....|....|...id = 9
    //    //....|....|....|....|...value = 0
    //    //....|....|....|....|...valuetype = INTEGER
    //    //
    //    //
    //
    //
    @SuppressWarnings("unchecked")
    @Test
    public void TestIBI() throws Exception {
        String sql    = "select * from alltypes where fbigint   + finteger  = 0";
        IDTable idTable = new IDTable();
        VoltXMLElement element = m_HSQLInterface.getVoltXMLFromSQLUsingVoltSQLParser(sql, null, SQLKind.DQL);
        assertThat(element)
            .hasName(1, "select")
            .hasAllOf(
                withChildNamed(2, "columns",
                    withChildNamed(3, "columnref",
                                   "alias", "FTINYINT",
                                   "column", "FTINYINT",
                                   "table", "ALLTYPES",
                        withAttribute(4, "alias", "FTINYINT"),
                        withAttribute(5, "column", "FTINYINT"),
                        withIdAttribute(6, idTable),
                        withAttribute(7, "index", "0"),
                        withAttribute(8, "table", "ALLTYPES")),
                    withChildNamed(9, "columnref",
                                   "alias", "FSMALLINT",
                                   "column", "FSMALLINT",
                                   "table", "ALLTYPES",
                        withAttribute(10, "alias", "FSMALLINT"),
                        withAttribute(11, "column", "FSMALLINT"),
                        withIdAttribute(12, idTable),
                        withAttribute(13, "index", "1"),
                        withAttribute(14, "table", "ALLTYPES")),
                    withChildNamed(15, "columnref",
                                   "alias", "FINTEGER",
                                   "column", "FINTEGER",
                                   "table", "ALLTYPES",
                        withAttribute(16, "alias", "FINTEGER"),
                        withAttribute(17, "column", "FINTEGER"),
                        withIdAttribute(18, idTable),
                        withAttribute(19, "index", "2"),
                        withAttribute(20, "table", "ALLTYPES")),
                    withChildNamed(21, "columnref",
                                   "alias", "FBIGINT",
                                   "column", "FBIGINT",
                                   "table", "ALLTYPES",
                        withAttribute(22, "alias", "FBIGINT"),
                        withAttribute(23, "column", "FBIGINT"),
                        withIdAttribute(24, idTable),
                        withAttribute(25, "index", "3"),
                        withAttribute(26, "table", "ALLTYPES")),
                    withChildNamed(27, "columnref",
                                   "alias", "FFLOAT",
                                   "column", "FFLOAT",
                                   "table", "ALLTYPES",
                        withAttribute(28, "alias", "FFLOAT"),
                        withAttribute(29, "column", "FFLOAT"),
                        withIdAttribute(30, idTable),
                        withAttribute(31, "index", "4"),
                        withAttribute(32, "table", "ALLTYPES")),
                    withChildNamed(33, "columnref",
                                   "alias", "FDECIMAL",
                                   "column", "FDECIMAL",
                                   "table", "ALLTYPES",
                        withAttribute(34, "alias", "FDECIMAL"),
                        withAttribute(35, "column", "FDECIMAL"),
                        withIdAttribute(36, idTable),
                        withAttribute(37, "index", "5"),
                        withAttribute(38, "table", "ALLTYPES")),
                    withChildNamed(39, "columnref",
                                   "alias", "FTIMESTAMP",
                                   "column", "FTIMESTAMP",
                                   "table", "ALLTYPES",
                        withAttribute(40, "alias", "FTIMESTAMP"),
                        withAttribute(41, "column", "FTIMESTAMP"),
                        withIdAttribute(42, idTable),
                        withAttribute(43, "index", "6"),
                        withAttribute(44, "table", "ALLTYPES"))),
                withChildNamed(45, "parameters"),
                withChildNamed(46, "tablescans",
                    withChildNamed(47, "tablescan",
                        withAttribute(48, "jointype", "inner"),
                        withAttribute(49, "table", "ALLTYPES"),
                        withChildNamed(50, "joincond",
                            withChildNamed(51, "operation",
                                           "optype", "equal",
                                withIdAttribute(52, idTable),
                                withAttribute(53, "optype", "equal"),
                                withChildNamed(54, "operation",
                                               "optype", "add",
                                    withIdAttribute(55, idTable),
                                    withAttribute(56, "optype", "add"),
                                    withChildNamed(57, "columnref",
                                                   "alias", "FBIGINT",
                                                   "column", "FBIGINT",
                                                   "table", "ALLTYPES",
                                        withAttribute(58, "alias", "FBIGINT"),
                                        withAttribute(59, "column", "FBIGINT"),
                                        withIdAttribute(60, idTable),
                                        withAttribute(61, "index", "3"),
                                        withAttribute(62, "table", "ALLTYPES")),
                                    withChildNamed(63, "columnref",
                                                   "alias", "FINTEGER",
                                                   "column", "FINTEGER",
                                                   "table", "ALLTYPES",
                                        withAttribute(64, "alias", "FINTEGER"),
                                        withAttribute(65, "column", "FINTEGER"),
                                        withIdAttribute(66, idTable),
                                        withAttribute(67, "index", "2"),
                                        withAttribute(68, "table", "ALLTYPES"))),
                                withChildNamed(69, "value",
                                    withIdAttribute(70, idTable),
                                    withAttribute(71, "value", "0"),
                                    withAttribute(72, "valuetype", "INTEGER")))))));
    }

    // Pattern XML:
    //    //.ELEMENT: select
    //    //.[
    //    //.....ELEMENT: columns
    //    //.....[
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = FTINYINT
    //    //....|....|.column = FTINYINT
    //    //....|....|.id = 1
    //    //....|....|.index = 0
    //    //....|....|.table = ALLTYPES
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = FSMALLINT
    //    //....|....|.column = FSMALLINT
    //    //....|....|.id = 2
    //    //....|....|.index = 1
    //    //....|....|.table = ALLTYPES
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = FINTEGER
    //    //....|....|.column = FINTEGER
    //    //....|....|.id = 3
    //    //....|....|.index = 2
    //    //....|....|.table = ALLTYPES
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = FBIGINT
    //    //....|....|.column = FBIGINT
    //    //....|....|.id = 4
    //    //....|....|.index = 3
    //    //....|....|.table = ALLTYPES
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = FFLOAT
    //    //....|....|.column = FFLOAT
    //    //....|....|.id = 5
    //    //....|....|.index = 4
    //    //....|....|.table = ALLTYPES
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = FDECIMAL
    //    //....|....|.column = FDECIMAL
    //    //....|....|.id = 6
    //    //....|....|.index = 5
    //    //....|....|.table = ALLTYPES
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = FTIMESTAMP
    //    //....|....|.column = FTIMESTAMP
    //    //....|....|.id = 7
    //    //....|....|.index = 6
    //    //....|....|.table = ALLTYPES
    //    //.....ELEMENT: parameters
    //    //.....ELEMENT: tablescans
    //    //.....[
    //    //....|....ELEMENT: tablescan
    //    //....|....|.jointype = inner
    //    //....|....|.table = ALLTYPES
    //    //....|....[
    //    //....|....|...ELEMENT: joincond
    //    //....|....|...[
    //    //....|....|....|..ELEMENT: operation
    //    //....|....|....|....id = 10
    //    //....|....|....|....optype = equal
    //    //....|....|....|..[
    //    //....|....|....|....|.ELEMENT: operation
    //    //....|....|....|....|...id = 8
    //    //....|....|....|....|...optype = add
    //    //....|....|....|....|.[
    //    //....|....|....|....|.....ELEMENT: columnref
    //    //....|....|....|....|....|..alias = FINTEGER
    //    //....|....|....|....|....|..column = FINTEGER
    //    //....|....|....|....|....|..id = 3
    //    //....|....|....|....|....|..index = 2
    //    //....|....|....|....|....|..table = ALLTYPES
    //    //....|....|....|....|.....ELEMENT: columnref
    //    //....|....|....|....|....|..alias = FBIGINT
    //    //....|....|....|....|....|..column = FBIGINT
    //    //....|....|....|....|....|..id = 4
    //    //....|....|....|....|....|..index = 3
    //    //....|....|....|....|....|..table = ALLTYPES
    //    //....|....|....|....|.ELEMENT: value
    //    //....|....|....|....|...id = 9
    //    //....|....|....|....|...value = 0
    //    //....|....|....|....|...valuetype = INTEGER
    //    //
    //    //
    //
    //
    @SuppressWarnings("unchecked")
    @Test
    public void TestBII() throws Exception {
        String sql    = "select * from alltypes where finteger  + fbigint   = 0";
        IDTable idTable = new IDTable();
        VoltXMLElement element = m_HSQLInterface.getVoltXMLFromSQLUsingVoltSQLParser(sql, null, SQLKind.DQL);
        assertThat(element)
            .hasName(1, "select")
            .hasAllOf(
                withChildNamed(2, "columns",
                    withChildNamed(3, "columnref",
                                   "alias", "FTINYINT",
                                   "column", "FTINYINT",
                                   "table", "ALLTYPES",
                        withAttribute(4, "alias", "FTINYINT"),
                        withAttribute(5, "column", "FTINYINT"),
                        withIdAttribute(6, idTable),
                        withAttribute(7, "index", "0"),
                        withAttribute(8, "table", "ALLTYPES")),
                    withChildNamed(9, "columnref",
                                   "alias", "FSMALLINT",
                                   "column", "FSMALLINT",
                                   "table", "ALLTYPES",
                        withAttribute(10, "alias", "FSMALLINT"),
                        withAttribute(11, "column", "FSMALLINT"),
                        withIdAttribute(12, idTable),
                        withAttribute(13, "index", "1"),
                        withAttribute(14, "table", "ALLTYPES")),
                    withChildNamed(15, "columnref",
                                   "alias", "FINTEGER",
                                   "column", "FINTEGER",
                                   "table", "ALLTYPES",
                        withAttribute(16, "alias", "FINTEGER"),
                        withAttribute(17, "column", "FINTEGER"),
                        withIdAttribute(18, idTable),
                        withAttribute(19, "index", "2"),
                        withAttribute(20, "table", "ALLTYPES")),
                    withChildNamed(21, "columnref",
                                   "alias", "FBIGINT",
                                   "column", "FBIGINT",
                                   "table", "ALLTYPES",
                        withAttribute(22, "alias", "FBIGINT"),
                        withAttribute(23, "column", "FBIGINT"),
                        withIdAttribute(24, idTable),
                        withAttribute(25, "index", "3"),
                        withAttribute(26, "table", "ALLTYPES")),
                    withChildNamed(27, "columnref",
                                   "alias", "FFLOAT",
                                   "column", "FFLOAT",
                                   "table", "ALLTYPES",
                        withAttribute(28, "alias", "FFLOAT"),
                        withAttribute(29, "column", "FFLOAT"),
                        withIdAttribute(30, idTable),
                        withAttribute(31, "index", "4"),
                        withAttribute(32, "table", "ALLTYPES")),
                    withChildNamed(33, "columnref",
                                   "alias", "FDECIMAL",
                                   "column", "FDECIMAL",
                                   "table", "ALLTYPES",
                        withAttribute(34, "alias", "FDECIMAL"),
                        withAttribute(35, "column", "FDECIMAL"),
                        withIdAttribute(36, idTable),
                        withAttribute(37, "index", "5"),
                        withAttribute(38, "table", "ALLTYPES")),
                    withChildNamed(39, "columnref",
                                   "alias", "FTIMESTAMP",
                                   "column", "FTIMESTAMP",
                                   "table", "ALLTYPES",
                        withAttribute(40, "alias", "FTIMESTAMP"),
                        withAttribute(41, "column", "FTIMESTAMP"),
                        withIdAttribute(42, idTable),
                        withAttribute(43, "index", "6"),
                        withAttribute(44, "table", "ALLTYPES"))),
                withChildNamed(45, "parameters"),
                withChildNamed(46, "tablescans",
                    withChildNamed(47, "tablescan",
                        withAttribute(48, "jointype", "inner"),
                        withAttribute(49, "table", "ALLTYPES"),
                        withChildNamed(50, "joincond",
                            withChildNamed(51, "operation",
                                           "optype", "equal",
                                withIdAttribute(52, idTable),
                                withAttribute(53, "optype", "equal"),
                                withChildNamed(54, "operation",
                                               "optype", "add",
                                    withIdAttribute(55, idTable),
                                    withAttribute(56, "optype", "add"),
                                    withChildNamed(57, "columnref",
                                                   "alias", "FINTEGER",
                                                   "column", "FINTEGER",
                                                   "table", "ALLTYPES",
                                        withAttribute(58, "alias", "FINTEGER"),
                                        withAttribute(59, "column", "FINTEGER"),
                                        withIdAttribute(60, idTable),
                                        withAttribute(61, "index", "2"),
                                        withAttribute(62, "table", "ALLTYPES")),
                                    withChildNamed(63, "columnref",
                                                   "alias", "FBIGINT",
                                                   "column", "FBIGINT",
                                                   "table", "ALLTYPES",
                                        withAttribute(64, "alias", "FBIGINT"),
                                        withAttribute(65, "column", "FBIGINT"),
                                        withIdAttribute(66, idTable),
                                        withAttribute(67, "index", "3"),
                                        withAttribute(68, "table", "ALLTYPES"))),
                                withChildNamed(69, "value",
                                    withIdAttribute(70, idTable),
                                    withAttribute(71, "value", "0"),
                                    withAttribute(72, "valuetype", "INTEGER")))))));
    }

    // Pattern XML:
    //    //.ELEMENT: select
    //    //.[
    //    //.....ELEMENT: columns
    //    //.....[
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = FTINYINT
    //    //....|....|.column = FTINYINT
    //    //....|....|.id = 1
    //    //....|....|.index = 0
    //    //....|....|.table = ALLTYPES
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = FSMALLINT
    //    //....|....|.column = FSMALLINT
    //    //....|....|.id = 2
    //    //....|....|.index = 1
    //    //....|....|.table = ALLTYPES
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = FINTEGER
    //    //....|....|.column = FINTEGER
    //    //....|....|.id = 3
    //    //....|....|.index = 2
    //    //....|....|.table = ALLTYPES
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = FBIGINT
    //    //....|....|.column = FBIGINT
    //    //....|....|.id = 4
    //    //....|....|.index = 3
    //    //....|....|.table = ALLTYPES
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = FFLOAT
    //    //....|....|.column = FFLOAT
    //    //....|....|.id = 5
    //    //....|....|.index = 4
    //    //....|....|.table = ALLTYPES
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = FDECIMAL
    //    //....|....|.column = FDECIMAL
    //    //....|....|.id = 6
    //    //....|....|.index = 5
    //    //....|....|.table = ALLTYPES
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = FTIMESTAMP
    //    //....|....|.column = FTIMESTAMP
    //    //....|....|.id = 7
    //    //....|....|.index = 6
    //    //....|....|.table = ALLTYPES
    //    //.....ELEMENT: parameters
    //    //.....ELEMENT: tablescans
    //    //.....[
    //    //....|....ELEMENT: tablescan
    //    //....|....|.jointype = inner
    //    //....|....|.table = ALLTYPES
    //    //....|....[
    //    //....|....|...ELEMENT: joincond
    //    //....|....|...[
    //    //....|....|....|..ELEMENT: operation
    //    //....|....|....|....id = 10
    //    //....|....|....|....optype = equal
    //    //....|....|....|..[
    //    //....|....|....|....|.ELEMENT: operation
    //    //....|....|....|....|...id = 8
    //    //....|....|....|....|...optype = add
    //    //....|....|....|....|.[
    //    //....|....|....|....|.....ELEMENT: columnref
    //    //....|....|....|....|....|..alias = FFLOAT
    //    //....|....|....|....|....|..column = FFLOAT
    //    //....|....|....|....|....|..id = 5
    //    //....|....|....|....|....|..index = 4
    //    //....|....|....|....|....|..table = ALLTYPES
    //    //....|....|....|....|.....ELEMENT: columnref
    //    //....|....|....|....|....|..alias = FINTEGER
    //    //....|....|....|....|....|..column = FINTEGER
    //    //....|....|....|....|....|..id = 3
    //    //....|....|....|....|....|..index = 2
    //    //....|....|....|....|....|..table = ALLTYPES
    //    //....|....|....|....|.ELEMENT: value
    //    //....|....|....|....|...id = 9
    //    //....|....|....|....|...value = 0
    //    //....|....|....|....|...valuetype = INTEGER
    //    //
    //    //
    //
    //
    @SuppressWarnings("unchecked")
    @Test
    public void TestIF() throws Exception {
        String sql    = "select * from alltypes where ffloat    + finteger  = 0";
        IDTable idTable = new IDTable();
        VoltXMLElement element = m_HSQLInterface.getVoltXMLFromSQLUsingVoltSQLParser(sql, null, SQLKind.DQL);
        assertThat(element)
            .hasName(1, "select")
            .hasAllOf(
                withChildNamed(2, "columns",
                    withChildNamed(3, "columnref",
                                   "alias", "FTINYINT",
                                   "column", "FTINYINT",
                                   "table", "ALLTYPES",
                        withAttribute(4, "alias", "FTINYINT"),
                        withAttribute(5, "column", "FTINYINT"),
                        withIdAttribute(6, idTable),
                        withAttribute(7, "index", "0"),
                        withAttribute(8, "table", "ALLTYPES")),
                    withChildNamed(9, "columnref",
                                   "alias", "FSMALLINT",
                                   "column", "FSMALLINT",
                                   "table", "ALLTYPES",
                        withAttribute(10, "alias", "FSMALLINT"),
                        withAttribute(11, "column", "FSMALLINT"),
                        withIdAttribute(12, idTable),
                        withAttribute(13, "index", "1"),
                        withAttribute(14, "table", "ALLTYPES")),
                    withChildNamed(15, "columnref",
                                   "alias", "FINTEGER",
                                   "column", "FINTEGER",
                                   "table", "ALLTYPES",
                        withAttribute(16, "alias", "FINTEGER"),
                        withAttribute(17, "column", "FINTEGER"),
                        withIdAttribute(18, idTable),
                        withAttribute(19, "index", "2"),
                        withAttribute(20, "table", "ALLTYPES")),
                    withChildNamed(21, "columnref",
                                   "alias", "FBIGINT",
                                   "column", "FBIGINT",
                                   "table", "ALLTYPES",
                        withAttribute(22, "alias", "FBIGINT"),
                        withAttribute(23, "column", "FBIGINT"),
                        withIdAttribute(24, idTable),
                        withAttribute(25, "index", "3"),
                        withAttribute(26, "table", "ALLTYPES")),
                    withChildNamed(27, "columnref",
                                   "alias", "FFLOAT",
                                   "column", "FFLOAT",
                                   "table", "ALLTYPES",
                        withAttribute(28, "alias", "FFLOAT"),
                        withAttribute(29, "column", "FFLOAT"),
                        withIdAttribute(30, idTable),
                        withAttribute(31, "index", "4"),
                        withAttribute(32, "table", "ALLTYPES")),
                    withChildNamed(33, "columnref",
                                   "alias", "FDECIMAL",
                                   "column", "FDECIMAL",
                                   "table", "ALLTYPES",
                        withAttribute(34, "alias", "FDECIMAL"),
                        withAttribute(35, "column", "FDECIMAL"),
                        withIdAttribute(36, idTable),
                        withAttribute(37, "index", "5"),
                        withAttribute(38, "table", "ALLTYPES")),
                    withChildNamed(39, "columnref",
                                   "alias", "FTIMESTAMP",
                                   "column", "FTIMESTAMP",
                                   "table", "ALLTYPES",
                        withAttribute(40, "alias", "FTIMESTAMP"),
                        withAttribute(41, "column", "FTIMESTAMP"),
                        withIdAttribute(42, idTable),
                        withAttribute(43, "index", "6"),
                        withAttribute(44, "table", "ALLTYPES"))),
                withChildNamed(45, "parameters"),
                withChildNamed(46, "tablescans",
                    withChildNamed(47, "tablescan",
                        withAttribute(48, "jointype", "inner"),
                        withAttribute(49, "table", "ALLTYPES"),
                        withChildNamed(50, "joincond",
                            withChildNamed(51, "operation",
                                           "optype", "equal",
                                withIdAttribute(52, idTable),
                                withAttribute(53, "optype", "equal"),
                                withChildNamed(54, "operation",
                                               "optype", "add",
                                    withIdAttribute(55, idTable),
                                    withAttribute(56, "optype", "add"),
                                    withChildNamed(57, "columnref",
                                                   "alias", "FFLOAT",
                                                   "column", "FFLOAT",
                                                   "table", "ALLTYPES",
                                        withAttribute(58, "alias", "FFLOAT"),
                                        withAttribute(59, "column", "FFLOAT"),
                                        withIdAttribute(60, idTable),
                                        withAttribute(61, "index", "4"),
                                        withAttribute(62, "table", "ALLTYPES")),
                                    withChildNamed(63, "columnref",
                                                   "alias", "FINTEGER",
                                                   "column", "FINTEGER",
                                                   "table", "ALLTYPES",
                                        withAttribute(64, "alias", "FINTEGER"),
                                        withAttribute(65, "column", "FINTEGER"),
                                        withIdAttribute(66, idTable),
                                        withAttribute(67, "index", "2"),
                                        withAttribute(68, "table", "ALLTYPES"))),
                                withChildNamed(69, "value",
                                    withIdAttribute(70, idTable),
                                    withAttribute(71, "value", "0"),
                                    withAttribute(72, "valuetype", "INTEGER")))))));
    }

    // Pattern XML:
    //    //.ELEMENT: select
    //    //.[
    //    //.....ELEMENT: columns
    //    //.....[
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = FTINYINT
    //    //....|....|.column = FTINYINT
    //    //....|....|.id = 1
    //    //....|....|.index = 0
    //    //....|....|.table = ALLTYPES
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = FSMALLINT
    //    //....|....|.column = FSMALLINT
    //    //....|....|.id = 2
    //    //....|....|.index = 1
    //    //....|....|.table = ALLTYPES
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = FINTEGER
    //    //....|....|.column = FINTEGER
    //    //....|....|.id = 3
    //    //....|....|.index = 2
    //    //....|....|.table = ALLTYPES
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = FBIGINT
    //    //....|....|.column = FBIGINT
    //    //....|....|.id = 4
    //    //....|....|.index = 3
    //    //....|....|.table = ALLTYPES
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = FFLOAT
    //    //....|....|.column = FFLOAT
    //    //....|....|.id = 5
    //    //....|....|.index = 4
    //    //....|....|.table = ALLTYPES
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = FDECIMAL
    //    //....|....|.column = FDECIMAL
    //    //....|....|.id = 6
    //    //....|....|.index = 5
    //    //....|....|.table = ALLTYPES
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = FTIMESTAMP
    //    //....|....|.column = FTIMESTAMP
    //    //....|....|.id = 7
    //    //....|....|.index = 6
    //    //....|....|.table = ALLTYPES
    //    //.....ELEMENT: parameters
    //    //.....ELEMENT: tablescans
    //    //.....[
    //    //....|....ELEMENT: tablescan
    //    //....|....|.jointype = inner
    //    //....|....|.table = ALLTYPES
    //    //....|....[
    //    //....|....|...ELEMENT: joincond
    //    //....|....|...[
    //    //....|....|....|..ELEMENT: operation
    //    //....|....|....|....id = 10
    //    //....|....|....|....optype = equal
    //    //....|....|....|..[
    //    //....|....|....|....|.ELEMENT: operation
    //    //....|....|....|....|...id = 8
    //    //....|....|....|....|...optype = add
    //    //....|....|....|....|.[
    //    //....|....|....|....|.....ELEMENT: columnref
    //    //....|....|....|....|....|..alias = FINTEGER
    //    //....|....|....|....|....|..column = FINTEGER
    //    //....|....|....|....|....|..id = 3
    //    //....|....|....|....|....|..index = 2
    //    //....|....|....|....|....|..table = ALLTYPES
    //    //....|....|....|....|.....ELEMENT: columnref
    //    //....|....|....|....|....|..alias = FFLOAT
    //    //....|....|....|....|....|..column = FFLOAT
    //    //....|....|....|....|....|..id = 5
    //    //....|....|....|....|....|..index = 4
    //    //....|....|....|....|....|..table = ALLTYPES
    //    //....|....|....|....|.ELEMENT: value
    //    //....|....|....|....|...id = 9
    //    //....|....|....|....|...value = 0
    //    //....|....|....|....|...valuetype = INTEGER
    //    //
    //    //
    //
    //
    @SuppressWarnings("unchecked")
    @Test
    public void TestFI() throws Exception {
        String sql    = "select * from alltypes where finteger  + ffloat    = 0";
        IDTable idTable = new IDTable();
        VoltXMLElement element = m_HSQLInterface.getVoltXMLFromSQLUsingVoltSQLParser(sql, null, SQLKind.DQL);
        assertThat(element)
            .hasName(1, "select")
            .hasAllOf(
                withChildNamed(2, "columns",
                    withChildNamed(3, "columnref",
                                   "alias", "FTINYINT",
                                   "column", "FTINYINT",
                                   "table", "ALLTYPES",
                        withAttribute(4, "alias", "FTINYINT"),
                        withAttribute(5, "column", "FTINYINT"),
                        withIdAttribute(6, idTable),
                        withAttribute(7, "index", "0"),
                        withAttribute(8, "table", "ALLTYPES")),
                    withChildNamed(9, "columnref",
                                   "alias", "FSMALLINT",
                                   "column", "FSMALLINT",
                                   "table", "ALLTYPES",
                        withAttribute(10, "alias", "FSMALLINT"),
                        withAttribute(11, "column", "FSMALLINT"),
                        withIdAttribute(12, idTable),
                        withAttribute(13, "index", "1"),
                        withAttribute(14, "table", "ALLTYPES")),
                    withChildNamed(15, "columnref",
                                   "alias", "FINTEGER",
                                   "column", "FINTEGER",
                                   "table", "ALLTYPES",
                        withAttribute(16, "alias", "FINTEGER"),
                        withAttribute(17, "column", "FINTEGER"),
                        withIdAttribute(18, idTable),
                        withAttribute(19, "index", "2"),
                        withAttribute(20, "table", "ALLTYPES")),
                    withChildNamed(21, "columnref",
                                   "alias", "FBIGINT",
                                   "column", "FBIGINT",
                                   "table", "ALLTYPES",
                        withAttribute(22, "alias", "FBIGINT"),
                        withAttribute(23, "column", "FBIGINT"),
                        withIdAttribute(24, idTable),
                        withAttribute(25, "index", "3"),
                        withAttribute(26, "table", "ALLTYPES")),
                    withChildNamed(27, "columnref",
                                   "alias", "FFLOAT",
                                   "column", "FFLOAT",
                                   "table", "ALLTYPES",
                        withAttribute(28, "alias", "FFLOAT"),
                        withAttribute(29, "column", "FFLOAT"),
                        withIdAttribute(30, idTable),
                        withAttribute(31, "index", "4"),
                        withAttribute(32, "table", "ALLTYPES")),
                    withChildNamed(33, "columnref",
                                   "alias", "FDECIMAL",
                                   "column", "FDECIMAL",
                                   "table", "ALLTYPES",
                        withAttribute(34, "alias", "FDECIMAL"),
                        withAttribute(35, "column", "FDECIMAL"),
                        withIdAttribute(36, idTable),
                        withAttribute(37, "index", "5"),
                        withAttribute(38, "table", "ALLTYPES")),
                    withChildNamed(39, "columnref",
                                   "alias", "FTIMESTAMP",
                                   "column", "FTIMESTAMP",
                                   "table", "ALLTYPES",
                        withAttribute(40, "alias", "FTIMESTAMP"),
                        withAttribute(41, "column", "FTIMESTAMP"),
                        withIdAttribute(42, idTable),
                        withAttribute(43, "index", "6"),
                        withAttribute(44, "table", "ALLTYPES"))),
                withChildNamed(45, "parameters"),
                withChildNamed(46, "tablescans",
                    withChildNamed(47, "tablescan",
                        withAttribute(48, "jointype", "inner"),
                        withAttribute(49, "table", "ALLTYPES"),
                        withChildNamed(50, "joincond",
                            withChildNamed(51, "operation",
                                           "optype", "equal",
                                withIdAttribute(52, idTable),
                                withAttribute(53, "optype", "equal"),
                                withChildNamed(54, "operation",
                                               "optype", "add",
                                    withIdAttribute(55, idTable),
                                    withAttribute(56, "optype", "add"),
                                    withChildNamed(57, "columnref",
                                                   "alias", "FINTEGER",
                                                   "column", "FINTEGER",
                                                   "table", "ALLTYPES",
                                        withAttribute(58, "alias", "FINTEGER"),
                                        withAttribute(59, "column", "FINTEGER"),
                                        withIdAttribute(60, idTable),
                                        withAttribute(61, "index", "2"),
                                        withAttribute(62, "table", "ALLTYPES")),
                                    withChildNamed(63, "columnref",
                                                   "alias", "FFLOAT",
                                                   "column", "FFLOAT",
                                                   "table", "ALLTYPES",
                                        withAttribute(64, "alias", "FFLOAT"),
                                        withAttribute(65, "column", "FFLOAT"),
                                        withIdAttribute(66, idTable),
                                        withAttribute(67, "index", "4"),
                                        withAttribute(68, "table", "ALLTYPES"))),
                                withChildNamed(69, "value",
                                    withIdAttribute(70, idTable),
                                    withAttribute(71, "value", "0"),
                                    withAttribute(72, "valuetype", "INTEGER")))))));
    }

    // Pattern XML:
    //    //.ELEMENT: select
    //    //.[
    //    //.....ELEMENT: columns
    //    //.....[
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = FTINYINT
    //    //....|....|.column = FTINYINT
    //    //....|....|.id = 1
    //    //....|....|.index = 0
    //    //....|....|.table = ALLTYPES
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = FSMALLINT
    //    //....|....|.column = FSMALLINT
    //    //....|....|.id = 2
    //    //....|....|.index = 1
    //    //....|....|.table = ALLTYPES
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = FINTEGER
    //    //....|....|.column = FINTEGER
    //    //....|....|.id = 3
    //    //....|....|.index = 2
    //    //....|....|.table = ALLTYPES
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = FBIGINT
    //    //....|....|.column = FBIGINT
    //    //....|....|.id = 4
    //    //....|....|.index = 3
    //    //....|....|.table = ALLTYPES
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = FFLOAT
    //    //....|....|.column = FFLOAT
    //    //....|....|.id = 5
    //    //....|....|.index = 4
    //    //....|....|.table = ALLTYPES
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = FDECIMAL
    //    //....|....|.column = FDECIMAL
    //    //....|....|.id = 6
    //    //....|....|.index = 5
    //    //....|....|.table = ALLTYPES
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = FTIMESTAMP
    //    //....|....|.column = FTIMESTAMP
    //    //....|....|.id = 7
    //    //....|....|.index = 6
    //    //....|....|.table = ALLTYPES
    //    //.....ELEMENT: parameters
    //    //.....ELEMENT: tablescans
    //    //.....[
    //    //....|....ELEMENT: tablescan
    //    //....|....|.jointype = inner
    //    //....|....|.table = ALLTYPES
    //    //....|....[
    //    //....|....|...ELEMENT: joincond
    //    //....|....|...[
    //    //....|....|....|..ELEMENT: operation
    //    //....|....|....|....id = 10
    //    //....|....|....|....optype = equal
    //    //....|....|....|..[
    //    //....|....|....|....|.ELEMENT: operation
    //    //....|....|....|....|...id = 8
    //    //....|....|....|....|...optype = add
    //    //....|....|....|....|.[
    //    //....|....|....|....|.....ELEMENT: columnref
    //    //....|....|....|....|....|..alias = FDECIMAL
    //    //....|....|....|....|....|..column = FDECIMAL
    //    //....|....|....|....|....|..id = 6
    //    //....|....|....|....|....|..index = 5
    //    //....|....|....|....|....|..table = ALLTYPES
    //    //....|....|....|....|.....ELEMENT: columnref
    //    //....|....|....|....|....|..alias = FINTEGER
    //    //....|....|....|....|....|..column = FINTEGER
    //    //....|....|....|....|....|..id = 3
    //    //....|....|....|....|....|..index = 2
    //    //....|....|....|....|....|..table = ALLTYPES
    //    //....|....|....|....|.ELEMENT: value
    //    //....|....|....|....|...id = 9
    //    //....|....|....|....|...value = 0
    //    //....|....|....|....|...valuetype = INTEGER
    //    //
    //    //
    //
    //
    @SuppressWarnings("unchecked")
    @Test
    public void TestID() throws Exception {
        String sql    = "select * from alltypes where fdecimal  + finteger  = 0";
        IDTable idTable = new IDTable();
        VoltXMLElement element = m_HSQLInterface.getVoltXMLFromSQLUsingVoltSQLParser(sql, null, SQLKind.DQL);
        assertThat(element)
            .hasName(1, "select")
            .hasAllOf(
                withChildNamed(2, "columns",
                    withChildNamed(3, "columnref",
                                   "alias", "FTINYINT",
                                   "column", "FTINYINT",
                                   "table", "ALLTYPES",
                        withAttribute(4, "alias", "FTINYINT"),
                        withAttribute(5, "column", "FTINYINT"),
                        withIdAttribute(6, idTable),
                        withAttribute(7, "index", "0"),
                        withAttribute(8, "table", "ALLTYPES")),
                    withChildNamed(9, "columnref",
                                   "alias", "FSMALLINT",
                                   "column", "FSMALLINT",
                                   "table", "ALLTYPES",
                        withAttribute(10, "alias", "FSMALLINT"),
                        withAttribute(11, "column", "FSMALLINT"),
                        withIdAttribute(12, idTable),
                        withAttribute(13, "index", "1"),
                        withAttribute(14, "table", "ALLTYPES")),
                    withChildNamed(15, "columnref",
                                   "alias", "FINTEGER",
                                   "column", "FINTEGER",
                                   "table", "ALLTYPES",
                        withAttribute(16, "alias", "FINTEGER"),
                        withAttribute(17, "column", "FINTEGER"),
                        withIdAttribute(18, idTable),
                        withAttribute(19, "index", "2"),
                        withAttribute(20, "table", "ALLTYPES")),
                    withChildNamed(21, "columnref",
                                   "alias", "FBIGINT",
                                   "column", "FBIGINT",
                                   "table", "ALLTYPES",
                        withAttribute(22, "alias", "FBIGINT"),
                        withAttribute(23, "column", "FBIGINT"),
                        withIdAttribute(24, idTable),
                        withAttribute(25, "index", "3"),
                        withAttribute(26, "table", "ALLTYPES")),
                    withChildNamed(27, "columnref",
                                   "alias", "FFLOAT",
                                   "column", "FFLOAT",
                                   "table", "ALLTYPES",
                        withAttribute(28, "alias", "FFLOAT"),
                        withAttribute(29, "column", "FFLOAT"),
                        withIdAttribute(30, idTable),
                        withAttribute(31, "index", "4"),
                        withAttribute(32, "table", "ALLTYPES")),
                    withChildNamed(33, "columnref",
                                   "alias", "FDECIMAL",
                                   "column", "FDECIMAL",
                                   "table", "ALLTYPES",
                        withAttribute(34, "alias", "FDECIMAL"),
                        withAttribute(35, "column", "FDECIMAL"),
                        withIdAttribute(36, idTable),
                        withAttribute(37, "index", "5"),
                        withAttribute(38, "table", "ALLTYPES")),
                    withChildNamed(39, "columnref",
                                   "alias", "FTIMESTAMP",
                                   "column", "FTIMESTAMP",
                                   "table", "ALLTYPES",
                        withAttribute(40, "alias", "FTIMESTAMP"),
                        withAttribute(41, "column", "FTIMESTAMP"),
                        withIdAttribute(42, idTable),
                        withAttribute(43, "index", "6"),
                        withAttribute(44, "table", "ALLTYPES"))),
                withChildNamed(45, "parameters"),
                withChildNamed(46, "tablescans",
                    withChildNamed(47, "tablescan",
                        withAttribute(48, "jointype", "inner"),
                        withAttribute(49, "table", "ALLTYPES"),
                        withChildNamed(50, "joincond",
                            withChildNamed(51, "operation",
                                           "optype", "equal",
                                withIdAttribute(52, idTable),
                                withAttribute(53, "optype", "equal"),
                                withChildNamed(54, "operation",
                                               "optype", "add",
                                    withIdAttribute(55, idTable),
                                    withAttribute(56, "optype", "add"),
                                    withChildNamed(57, "columnref",
                                                   "alias", "FDECIMAL",
                                                   "column", "FDECIMAL",
                                                   "table", "ALLTYPES",
                                        withAttribute(58, "alias", "FDECIMAL"),
                                        withAttribute(59, "column", "FDECIMAL"),
                                        withIdAttribute(60, idTable),
                                        withAttribute(61, "index", "5"),
                                        withAttribute(62, "table", "ALLTYPES")),
                                    withChildNamed(63, "columnref",
                                                   "alias", "FINTEGER",
                                                   "column", "FINTEGER",
                                                   "table", "ALLTYPES",
                                        withAttribute(64, "alias", "FINTEGER"),
                                        withAttribute(65, "column", "FINTEGER"),
                                        withIdAttribute(66, idTable),
                                        withAttribute(67, "index", "2"),
                                        withAttribute(68, "table", "ALLTYPES"))),
                                withChildNamed(69, "value",
                                    withIdAttribute(70, idTable),
                                    withAttribute(71, "value", "0"),
                                    withAttribute(72, "valuetype", "INTEGER")))))));
    }

    // Pattern XML:
    //    //.ELEMENT: select
    //    //.[
    //    //.....ELEMENT: columns
    //    //.....[
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = FTINYINT
    //    //....|....|.column = FTINYINT
    //    //....|....|.id = 1
    //    //....|....|.index = 0
    //    //....|....|.table = ALLTYPES
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = FSMALLINT
    //    //....|....|.column = FSMALLINT
    //    //....|....|.id = 2
    //    //....|....|.index = 1
    //    //....|....|.table = ALLTYPES
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = FINTEGER
    //    //....|....|.column = FINTEGER
    //    //....|....|.id = 3
    //    //....|....|.index = 2
    //    //....|....|.table = ALLTYPES
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = FBIGINT
    //    //....|....|.column = FBIGINT
    //    //....|....|.id = 4
    //    //....|....|.index = 3
    //    //....|....|.table = ALLTYPES
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = FFLOAT
    //    //....|....|.column = FFLOAT
    //    //....|....|.id = 5
    //    //....|....|.index = 4
    //    //....|....|.table = ALLTYPES
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = FDECIMAL
    //    //....|....|.column = FDECIMAL
    //    //....|....|.id = 6
    //    //....|....|.index = 5
    //    //....|....|.table = ALLTYPES
    //    //....|....ELEMENT: columnref
    //    //....|....|.alias = FTIMESTAMP
    //    //....|....|.column = FTIMESTAMP
    //    //....|....|.id = 7
    //    //....|....|.index = 6
    //    //....|....|.table = ALLTYPES
    //    //.....ELEMENT: parameters
    //    //.....ELEMENT: tablescans
    //    //.....[
    //    //....|....ELEMENT: tablescan
    //    //....|....|.jointype = inner
    //    //....|....|.table = ALLTYPES
    //    //....|....[
    //    //....|....|...ELEMENT: joincond
    //    //....|....|...[
    //    //....|....|....|..ELEMENT: operation
    //    //....|....|....|....id = 10
    //    //....|....|....|....optype = equal
    //    //....|....|....|..[
    //    //....|....|....|....|.ELEMENT: operation
    //    //....|....|....|....|...id = 8
    //    //....|....|....|....|...optype = add
    //    //....|....|....|....|.[
    //    //....|....|....|....|.....ELEMENT: columnref
    //    //....|....|....|....|....|..alias = FINTEGER
    //    //....|....|....|....|....|..column = FINTEGER
    //    //....|....|....|....|....|..id = 3
    //    //....|....|....|....|....|..index = 2
    //    //....|....|....|....|....|..table = ALLTYPES
    //    //....|....|....|....|.....ELEMENT: columnref
    //    //....|....|....|....|....|..alias = FDECIMAL
    //    //....|....|....|....|....|..column = FDECIMAL
    //    //....|....|....|....|....|..id = 6
    //    //....|....|....|....|....|..index = 5
    //    //....|....|....|....|....|..table = ALLTYPES
    //    //....|....|....|....|.ELEMENT: value
    //    //....|....|....|....|...id = 9
    //    //....|....|....|....|...value = 0
    //    //....|....|....|....|...valuetype = INTEGER
    //    //
    //    //
    //
    //
    @SuppressWarnings("unchecked")
    @Test
    public void TestDI() throws Exception {
        String sql    = "select * from alltypes where finteger  + fdecimal  = 0";
        IDTable idTable = new IDTable();
        VoltXMLElement element = m_HSQLInterface.getVoltXMLFromSQLUsingVoltSQLParser(sql, null, SQLKind.DQL);
        assertThat(element)
            .hasName(1, "select")
            .hasAllOf(
                withChildNamed(2, "columns",
                    withChildNamed(3, "columnref",
                                   "alias", "FTINYINT",
                                   "column", "FTINYINT",
                                   "table", "ALLTYPES",
                        withAttribute(4, "alias", "FTINYINT"),
                        withAttribute(5, "column", "FTINYINT"),
                        withIdAttribute(6, idTable),
                        withAttribute(7, "index", "0"),
                        withAttribute(8, "table", "ALLTYPES")),
                    withChildNamed(9, "columnref",
                                   "alias", "FSMALLINT",
                                   "column", "FSMALLINT",
                                   "table", "ALLTYPES",
                        withAttribute(10, "alias", "FSMALLINT"),
                        withAttribute(11, "column", "FSMALLINT"),
                        withIdAttribute(12, idTable),
                        withAttribute(13, "index", "1"),
                        withAttribute(14, "table", "ALLTYPES")),
                    withChildNamed(15, "columnref",
                                   "alias", "FINTEGER",
                                   "column", "FINTEGER",
                                   "table", "ALLTYPES",
                        withAttribute(16, "alias", "FINTEGER"),
                        withAttribute(17, "column", "FINTEGER"),
                        withIdAttribute(18, idTable),
                        withAttribute(19, "index", "2"),
                        withAttribute(20, "table", "ALLTYPES")),
                    withChildNamed(21, "columnref",
                                   "alias", "FBIGINT",
                                   "column", "FBIGINT",
                                   "table", "ALLTYPES",
                        withAttribute(22, "alias", "FBIGINT"),
                        withAttribute(23, "column", "FBIGINT"),
                        withIdAttribute(24, idTable),
                        withAttribute(25, "index", "3"),
                        withAttribute(26, "table", "ALLTYPES")),
                    withChildNamed(27, "columnref",
                                   "alias", "FFLOAT",
                                   "column", "FFLOAT",
                                   "table", "ALLTYPES",
                        withAttribute(28, "alias", "FFLOAT"),
                        withAttribute(29, "column", "FFLOAT"),
                        withIdAttribute(30, idTable),
                        withAttribute(31, "index", "4"),
                        withAttribute(32, "table", "ALLTYPES")),
                    withChildNamed(33, "columnref",
                                   "alias", "FDECIMAL",
                                   "column", "FDECIMAL",
                                   "table", "ALLTYPES",
                        withAttribute(34, "alias", "FDECIMAL"),
                        withAttribute(35, "column", "FDECIMAL"),
                        withIdAttribute(36, idTable),
                        withAttribute(37, "index", "5"),
                        withAttribute(38, "table", "ALLTYPES")),
                    withChildNamed(39, "columnref",
                                   "alias", "FTIMESTAMP",
                                   "column", "FTIMESTAMP",
                                   "table", "ALLTYPES",
                        withAttribute(40, "alias", "FTIMESTAMP"),
                        withAttribute(41, "column", "FTIMESTAMP"),
                        withIdAttribute(42, idTable),
                        withAttribute(43, "index", "6"),
                        withAttribute(44, "table", "ALLTYPES"))),
                withChildNamed(45, "parameters"),
                withChildNamed(46, "tablescans",
                    withChildNamed(47, "tablescan",
                        withAttribute(48, "jointype", "inner"),
                        withAttribute(49, "table", "ALLTYPES"),
                        withChildNamed(50, "joincond",
                            withChildNamed(51, "operation",
                                           "optype", "equal",
                                withIdAttribute(52, idTable),
                                withAttribute(53, "optype", "equal"),
                                withChildNamed(54, "operation",
                                               "optype", "add",
                                    withIdAttribute(55, idTable),
                                    withAttribute(56, "optype", "add"),
                                    withChildNamed(57, "columnref",
                                                   "alias", "FINTEGER",
                                                   "column", "FINTEGER",
                                                   "table", "ALLTYPES",
                                        withAttribute(58, "alias", "FINTEGER"),
                                        withAttribute(59, "column", "FINTEGER"),
                                        withIdAttribute(60, idTable),
                                        withAttribute(61, "index", "2"),
                                        withAttribute(62, "table", "ALLTYPES")),
                                    withChildNamed(63, "columnref",
                                                   "alias", "FDECIMAL",
                                                   "column", "FDECIMAL",
                                                   "table", "ALLTYPES",
                                        withAttribute(64, "alias", "FDECIMAL"),
                                        withAttribute(65, "column", "FDECIMAL"),
                                        withIdAttribute(66, idTable),
                                        withAttribute(67, "index", "5"),
                                        withAttribute(68, "table", "ALLTYPES"))),
                                withChildNamed(69, "value",
                                    withIdAttribute(70, idTable),
                                    withAttribute(71, "value", "0"),
                                    withAttribute(72, "valuetype", "INTEGER")))))));
    }
}

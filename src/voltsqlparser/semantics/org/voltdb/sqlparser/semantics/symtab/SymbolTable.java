/* This file is part of VoltDB.
 * Copyright (C) 2008-2017 VoltDB Inc.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with VoltDB.  If not, see <http://www.gnu.org/licenses/>.
 */
/* This file is part of VoltDB.
 * Copyright (C) 2008-2016 VoltDB Inc.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with VoltDB.  If not, see <http://www.gnu.org/licenses/>.
 */
/* This file is part of VoltDB.
 * Copyright (C) 2008-2015 VoltDB Inc.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with VoltDB.  If not, see <http://www.gnu.org/licenses/>.
 */
 /* This file is part of VoltDB.
 * Copyright (C) 2008-2015 VoltDB Inc.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with VoltDB.  If not, see <http://www.gnu.org/licenses/>.
 */
 package org.voltdb.sqlparser.semantics.symtab;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.voltdb.sqlparser.syntax.symtab.IColumn;
import org.voltdb.sqlparser.syntax.symtab.ISourceLocation;
import org.voltdb.sqlparser.syntax.symtab.ISymbolTable;
import org.voltdb.sqlparser.syntax.symtab.ITop;
import org.voltdb.sqlparser.syntax.symtab.TypeKind;

/**
 * A SymbolTable associates values and types with
 * strings.  SymbolTables may be nested.  Tables are
 * a kind of SymbolTable, since they have columns
 * and columns have names.  All entities have numeric
 * ids as well.  Numeric IDs with value less than 1000
 * are pre-defined entities, such as pre-defined types.
 * IDs with values more than 1000 are user defined entities,
 * such as columns and tables.
 *
 * @author bwhite
 *
 */
public class SymbolTable implements ISymbolTable {
    private static final SourceLocation m_noSourceLocation = new SourceLocation(-1, -1);
    private static final ErrorType      m_errorType        = new ErrorType(m_noSourceLocation, "$error", TypeKind.ERROR);
    private static final BooleanType    m_booleanType      = new BooleanType(m_noSourceLocation, "boolean", TypeKind.BOOLEAN);
    private static final VoidType       m_voidType         = new VoidType(m_noSourceLocation, "$void", TypeKind.VOID);

    public static VoidType getVoidType() {
        return m_voidType;
    }

    public static final ErrorType getErrorType() {
        return m_errorType;
    }
    public static final BooleanType getBooleanType() {
        return m_booleanType;
    }
    ISymbolTable m_parent = null;
    public class TablePair {
        Table m_table;
        String m_alias;
        public TablePair(Table aTable, String aAlias) {
            m_table = aTable;
            m_alias = aAlias;
        }
        public final Table getTable() {
            return m_table;
        }
        public final String getAlias() {
            return m_alias;
        }
    }
    List<TablePair> m_tables = new ArrayList<>();
    Map<String, Top> m_lookup = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

    public SymbolTable(SymbolTable aParent) {
        m_parent = aParent;
    }

    /* (non-Javadoc)
     * @see org.voltdb.sqlparser.symtab.ISymbolTable#define(org.voltdb.sqlparser.symtab.Top)
     */
    @Override
    public void define(ITop aDefiniens) {
        String definiendum = aDefiniens.getName();
        if (definiendum != null) {
            m_lookup.put(definiendum, (Top) aDefiniens);
        }
        if (aDefiniens instanceof Table) {
            m_tables.add(new TablePair((Table)aDefiniens, definiendum));
        }
    }

    @Override
    public String toString() {
        return m_lookup.toString();
    }

    public void addTable(Table aTable,String aAlias) {
        m_lookup.put(aAlias, aTable);
        m_tables.add(new TablePair(aTable, aAlias));
    }

    /* (non-Javadoc)
     * @see org.voltdb.sqlparser.symtab.ISymbolTable#size()
     */
    @Override
    public int size() {
        return m_lookup.size();
    }

    /**
     * called with input tables as arguments, so that this table knows what to do.
     * @param args
     */
    public void buildLookup(String[] args) {

    }

    /* (non-Javadoc)
     * @see org.voltdb.sqlparser.symtab.ISymbolTable#isEmpty()
     */
    @Override
    public boolean isEmpty() {
        return m_lookup.size() == 0;
    }
    /* (non-Javadoc)
     * @see org.voltdb.sqlparser.symtab.ISymbolTable#get(java.lang.String)
     */
    @Override
    public final Top get(String aName) {
        Top ret = m_lookup.get(aName);
        if (ret == null && m_parent != null) {
            ret = (Top) m_parent.get(aName);
        }
        return ret;
    }

    /* (non-Javadoc)
     * @see org.voltdb.sqlparser.symtab.ISymbolTable#getType(java.lang.String)
     */
    @Override
    public final Type getType(String aName) { // is it illegal to name tables the same thing as types? I don't think that would work here.
        Top answer = get(aName);
        if (answer != null && answer instanceof Type) {
            return (Type)answer;
        } else if (m_parent != null) {
            return (Type) m_parent.getType(aName);
        } else {
            return null;
        }
    }

    /* (non-Javadoc)
     * @see org.voltdb.sqlparser.symtab.ISymbolTable#getValue(java.lang.String)
     */
    @Override
    public final Value getValue(String aName) {
        Top answer = get(aName);
        if (answer != null && answer instanceof Value) {
            return (Value)answer;
        }
        return null;
    }

    @Override
    public final Table getTable(String aName) {
        Top table = get(aName);
        if (table != null && table instanceof Table)
                return (Table)table;
        return null;
    }

    public static ISymbolTable newStandardPrelude() {
        ISourceLocation noSourceLocation = new SourceLocation(-1, -1);
        ISymbolTable answer = new SymbolTable(null);
        answer.define(new IntegerType(noSourceLocation, "bigint",      TypeKind.BIGINT));
        answer.define(new IntegerType(noSourceLocation, "integer",     TypeKind.INTEGER));
        answer.define(new IntegerType(noSourceLocation, "tinyint",     TypeKind.TINYINT));
        answer.define(new IntegerType(noSourceLocation, "smallint",    TypeKind.SMALLINT));
        answer.define(new DecimalType(noSourceLocation, "decimal",     TypeKind.DECIMAL));
        answer.define(new FloatingPointType(noSourceLocation, "float", TypeKind.FLOAT));
        answer.define(new StringType(noSourceLocation, "varchar",      TypeKind.VARCHAR));
        answer.define(new StringType(noSourceLocation, "varbinary",    TypeKind.VARBINARY));
        answer.define(new TimestampType(noSourceLocation, "timestamp", TypeKind.TIMESTAMP));
        answer.define(new GeographyPointType(noSourceLocation, "geography_point",
                                             TypeKind.GEOPOINT));
        answer.define(new GeographyType(noSourceLocation, "geography", TypeKind.GEOGRAPHY));
        answer.define(new ErrorType(noSourceLocation, "$error",        TypeKind.ERROR));
        answer.define(new BooleanType(noSourceLocation, "boolean",     TypeKind.BOOLEAN));
        answer.define(new VoidType(noSourceLocation, "$void",          TypeKind.VOID));
        return answer;
    }

    @Override
    public String getTableAliasByColumn(String aColName) {
        for (TablePair tp : m_tables) {
            IColumn col = tp.getTable().getColumnByName(aColName);
            if (col != null) {
                if (tp.getAlias() == null) {
                    return tp.getTable().getName();
                }
                return tp.getAlias();
            }
        }
        return null;
    }

    public String getTableNameByColumn(String aColName) {
        for (TablePair tp : m_tables) {
            IColumn col = tp.getTable().getColumnByName(aColName);
            if (col != null) {
                return tp.getTable().getName();
            }
        }
        return null;
    }

    public final List<TablePair> getTables() {
        return m_tables;
    }

    public final int getSize() {
        return m_lookup.size();
    }

    private static String m_conversions[][] = {
    /*                 VOID      ERROR     BOOLEAN     TINYINT     SMALLINT     INTEGER     BIGINT     FLOAT     VARCHR     VARBINARY     DECIMAL     TIMESTAMP */
    /* VOID      */   {"$VOID",  null,     null,       null,       null,        null,       null,      null,     null,      null,         null,       null},
    /* ERROR     */   {null,     "$ERROR", null,       null,       null,        null,       null,      null,     null,      null,         null,       null},
    /* BOOLEAN   */   {null,     null,     "$BOOLEAN", null,       null,        null,       null,      null,     null,      null,         null,       null},
    /* TINYINT   */   {null,     null,     null,       "TINYINT",  "SMALLINT",  "INTEGER",  "BIGINT",  "FLOAT",  null,      null,         "DECIMAL",  null},
    /* SMALLINT  */   {null,     null,     null,       "SMALLINT", "SMALLINT",  "INTEGER",  "BIGINT",  "FLOAT",  null,      null,         "DECIMAL",  null},
    /* INTEGER   */   {null,     null,     null,       "INTEGER",  "INTEGER",   "INTEGER",  "BIGINT",  "FLOAT",  null,      null,         "DECIMAL",  null},
    /* BIGINT    */   {null,     null,     null,       "BIGINT",   "BIGINT",    "BIGINT",   "BIGINT",  null,     null,      null,         null     ,  null},
    /* FLOAT     */   {null,     null,     null,       "FLOAT",    "FLOAT",     "FLOAT",    null,      "FLOAT",  null,      null,         null,       null},
    /* VARCHAR   */   {null,     null,     null,       null,       null,        null,       null,      null,     "VARCHAR", null,         null,       null},
    /* VARBINARY */   {null,     null,     null,       null,       null,        null,       null,      null,     null,      "VARBINARY",  null,       null},
    /* DECIMAL   */   {null,     null,     null,       "DECIMAL",  "DECIMAL",   "DECIMAL",  null,      null,     null,      null,         "DECIMAL",  null},
    /* TIMESTAMP */   {null,     null,     null,       null,       null,        null,       null,      null,     null,      null,         null,       "TIMESTAMP"},
    };
    /**
     * This calculates type conversions.
     *
     * @param leftType
     * @param rightType
     * @return
     */
    public Type hasSuperType(Type aLeftType, Type aRightType) {
        TypeKind leftKind = aLeftType.getTypeKind();
        TypeKind rightKind = aRightType.getTypeKind();
        // If one is a string.
        if (leftKind.isString() || rightKind.isString()) {
            // If both are a string.
            if (leftKind.isString() && rightKind.isString()) {
                // If both are varchar or both are varbinary
                if (leftKind.isUnicode() == rightKind.isUnicode()) {
                    // Return the one with the largest maxsize.
                    assert(aLeftType instanceof StringType);
                    assert(aRightType instanceof StringType);
                    if (((StringType)aLeftType).getMaxSize() <= ((StringType)aRightType).getMaxSize()) {
                        return aRightType;
                    } else {
                        return aLeftType;
                    }
                } else {
                    // One is varchar and one is varbinary.
                    return null;
                }
            } else {
                // One is string but not both.
                return null;
            }
        } else if (leftKind.isTimeStamp() || rightKind.isTimeStamp()) {
            if (leftKind.isTimeStamp() && rightKind.isTimeStamp()) {
                // Both are timestamp.
                return aLeftType;
            } else {
                // One is timestamp and one is not.
                return null;
            }
        }
        String conversionName = m_conversions[leftKind.ordinal()][leftKind.ordinal()];
        if (conversionName == null) {
            return null;
        }
        return getType(conversionName);
    }

    @Override
    public ISourceLocation getNoSourceLocation() {
        return m_noSourceLocation;
    }

}

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
 /**
 *
 */
package org.voltdb.sqlparser.semantics.symtab;

import java.util.HashMap;
import java.util.Map;

import org.voltdb.sqlparser.semantics.grammar.InsertStatement;
import org.voltdb.sqlparser.syntax.grammar.ICatalogAdapter;
import org.voltdb.sqlparser.syntax.grammar.IColumnIdent;
import org.voltdb.sqlparser.syntax.grammar.IInsertStatement;
import org.voltdb.sqlparser.syntax.grammar.IJoinTree;
import org.voltdb.sqlparser.syntax.grammar.IOperator;
import org.voltdb.sqlparser.syntax.grammar.ISelectQuery;
import org.voltdb.sqlparser.syntax.grammar.ISemantino;
import org.voltdb.sqlparser.syntax.symtab.IColumn;
import org.voltdb.sqlparser.syntax.symtab.IExpressionParser;
import org.voltdb.sqlparser.syntax.symtab.IParserFactory;
import org.voltdb.sqlparser.syntax.symtab.ISourceLocation;
import org.voltdb.sqlparser.syntax.symtab.ISymbolTable;
import org.voltdb.sqlparser.syntax.symtab.ITable;
import org.voltdb.sqlparser.syntax.symtab.IType;
import org.voltdb.sqlparser.syntax.util.ErrorMessageSet;

/**
 * This is a generic implementation of the SQL Parser's
 * semantic operations.  It is not complete, but it will
 * be completed downstream.
 *
 * @author bwhite
 *
 */
public abstract class ParserFactory implements IParserFactory {
    ICatalogAdapter m_catalog;
    private static Map<String, IOperator> m_operatorMap = initOperatorMap();
    private Type m_booleanType = null;
    private static ISymbolTable m_stdPrelude = SymbolTable.newStandardPrelude();
    private ErrorMessageSet m_errorMessages = new ErrorMessageSet();

    private static Map<String, IOperator> initOperatorMap() {
        HashMap<String, IOperator> answer = new HashMap<>();
        for (Operator op : Operator.values()) {
            answer.put(op.getOperation(), op);
        }
        return answer;
    }

    public ParserFactory(ICatalogAdapter aCatalog) {
        m_catalog = aCatalog;
    }

    /**
     * This is used for operations that make no sense, or which are not
     * implemented in the mock factory.
     *
     * @param aFuncName
     */
    protected void unimplementedOperation(String aFuncName) {
        throw new AssertionError("Unimplemented ParserFactory Method " + aFuncName);
    }

    /* (non-Javadoc)
     * @see org.voltdb.sqlparser.symtab.IParserFactory#getStandardPrelude()
     */
    @Override
    public ISymbolTable getStandardPrelude() {
        return m_stdPrelude;
    }

    @Override
    public IColumn newColumn(ISourceLocation aLoc, String aColName, IType aColType) {
        assert(aColType instanceof Type);
        return new Column(aLoc, aColName, (Type)aColType);
    }

    @Override
    public ITable newTable(ISourceLocation aLoc, String aTableName) {
        return new Table(aLoc, aTableName);
    }

    @Override
    public ICatalogAdapter getCatalog() {
        return m_catalog;
    }

    @Override
    public IJoinTree newTableReference(String aTableName, String aTableAlias) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IJoinTree newDerivedJoinTree(ISelectQuery derivedTable, String tableName) {
        // TODO Auto-generated method stub
        return null;
    }
    /**
     * Process a query.
     */
    @Override
    public void processQuery(ISelectQuery aSelectQuery) {
        // put projections onto semantino stack.
        aSelectQuery.setAST(makeQueryAST(aSelectQuery.getProjections(),
                                         aSelectQuery.getWhereCondition(),
                                         aSelectQuery.getTables()));
    }

    @Override
    public IInsertStatement newInsertStatement() {
        return new InsertStatement();
    }

    @Override
    public IOperator getExpressionOperator(String aText) {
        return m_operatorMap.get(aText);
    }

    /*
     * The usual arithmetic conversions.
     */
    @Override
    public Semantino[] tuac(ISemantino ileft, ISemantino iright, ISymbolTable aSymbolTable) {
        assert aSymbolTable instanceof SymbolTable;
        SymbolTable context = (SymbolTable)aSymbolTable;
        Semantino left = (Semantino)ileft;
        Semantino right = (Semantino)iright;
        Type leftType = (Type) left.getType();
        Type rightType = (Type) right.getType();
        if (leftType.isEqualType(rightType)) {
                return new Semantino[]{left,right};
        } else {
            Type convertedType = context.hasSuperType(leftType, rightType);
            if (convertedType != null) {
                Semantino lconverted, rconverted;
                if (convertedType.isEqualType(leftType)) {
                    lconverted = left;
                } else {
                    lconverted = new Semantino(convertedType,
                                               addTypeConversion(left.getAST(),
                                                                 leftType,
                                                                 convertedType));
                }
                if (convertedType.isEqualType(rightType)) {
                    rconverted = right;
                } else {
                    rconverted = new Semantino(convertedType,
                                               addTypeConversion(right.getAST(),
                                                                 rightType,
                                                                 convertedType));
                }
                return new Semantino[]{lconverted, rconverted};
            } else {
                m_errorMessages.addError(newSourceLocation(-1, -1), "Can't convert type \"%s\" to \"%s\"",
                                         leftType, rightType);
                return new Semantino[]{(Semantino) getErrorSemantino(),
                                       (Semantino) getErrorSemantino()};
            }
        }
    }

    @Override
    public ErrorMessageSet getErrorMessages() {
        return m_errorMessages;
    }

    @Override
    public IType getBooleanType() {
        return SymbolTable.getBooleanType();
    }

    @Override
    public IType getErrorType() {
        return SymbolTable.getErrorType();
    }

    @Override
    public IColumnIdent makeColumnRef(String aColName,
                                      int    aColLineNo,
                                      int    aColColNo) {
        return new ColumnIdent(aColName, aColLineNo, aColColNo);
    }

    @Override
    public IExpressionParser makeExpressionParser(ISymbolTable aSymbolTable) {
        return new ExpressionParser(this, aSymbolTable);
    }

    @Override
    public ISemantino getErrorSemantino() {
        return Semantino.getErrorSemantino();
    }

    @Override
    public ISourceLocation newSourceLocation(int aLineNumber, int aColumnNumber) {
        return new SourceLocation(aLineNumber, aColumnNumber);
    }

}

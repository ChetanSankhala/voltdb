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
package org.voltdb.sqlparser.semantics.symtab;

import org.voltdb.sqlparser.syntax.grammar.IJoinTree;
import org.voltdb.sqlparser.syntax.grammar.ISemantino;
import org.voltdb.sqlparser.syntax.grammar.JoinOperator;
import org.voltdb.sqlparser.syntax.symtab.ISourceLocation;

/**
 * This is the common base class of TableReference and
 * JoinedTable.  It implements the default methods.
 *
 * @author Bill White
 *
 */
public abstract class JoinTree extends Top implements IJoinTree {

    JoinTree(ISourceLocation aLoc, String aName) {
        super(aLoc, aName);
    }

    @Override
    public String getTableName() {
        return null;
    }

    @Override
    public String getTableAlias() {
        return null;
    }

    @Override
    public JoinOperator getJoinOperator() {
        return null;
    }

    @Override
    public IJoinTree getLeft() {
        return null;
    }

    @Override
    public IJoinTree getRight() {
        return null;
    }

    @Override
    public ISemantino getJoinCondition() {
        return null;
    }
}

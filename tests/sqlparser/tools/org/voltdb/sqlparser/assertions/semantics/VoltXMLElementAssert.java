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
 * Copyright (C) 2008-2016 VoltDB Inc.
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
 * Copyright (C) 2008-2015 VoltDB Inc.
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
 */

package org.voltdb.sqlparser.assertions.semantics;

import static java.lang.String.format;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Condition;
import org.assertj.core.api.Fail;
import org.hsqldb_voltpatches.VoltXMLElement;

/**
 * {@link VoltXMLElement} specific assertions - Generated by CustomAssertionGenerator.
 */
public class VoltXMLElementAssert extends AbstractAssert<VoltXMLElementAssert, VoltXMLElement> {

    public static class IDTable {
        Set<Integer> m_ids = new HashSet<Integer>();
        public Boolean addId(String aID) {
            Integer id = Integer.parseInt(aID);
            if (m_ids.contains(id)) {
                return false;
            } else {
                m_ids.add(id);
                return true;
            }
        }
    }
    /**
     * Creates a new </code>{@link VoltXMLElementAssert}</code> to make assertions on actual VoltXMLElement.
     * @param actual the VoltXMLElement we want to make assertions on.
     */
    public VoltXMLElementAssert(VoltXMLElement actual) {
        super(actual, VoltXMLElementAssert.class);
    }

    /**
     * An entry point for VoltXMLElementAssert to follow AssertJ standard <code>assertThat()</code> statements.<br>
     * With a static import, one's can write directly : <code>assertThat(myVoltXMLElement)</code> and get specific assertion with code completion.
     * @param actual the VoltXMLElement we want to make assertions on.
     * @return a new </code>{@link VoltXMLElementAssert}</code>
     */
    public static VoltXMLElementAssert assertThat(VoltXMLElement actual) {
        return new VoltXMLElementAssert(actual);
    }

    @SuppressWarnings("unchecked")
    public VoltXMLElementAssert hasAllOf(Condition<VoltXMLElement> ... aConditions) {
        isNotNull();
        for (Condition<VoltXMLElement> cond : aConditions) {
            if (!cond.matches(actual)) {
                failWithMessage("Condition failed to match.  That's all we've got.\n");
            }
        }
        return this;
    }
    public VoltXMLElementAssert hasName(int aTestId, String name) {
        isNotNull();
        if (name == null) {
            failWithMessage("Expected null name");
        }
        if (!name.equals(actual.name)) {
            failWithMessage("Test %d: Expected name to be:\n    <%s>\nnot:\n    <%s>",
                            aTestId, name, actual.getUniqueName());
        }
        return this;
    }

    public VoltXMLElementAssert hasAttribute(int    aTestId,
                                             String aAttributeName,
                                             String aAttributeValue) {
        // check that actual VoltXMLElement we want to make assertions on is not null.
        isNotNull();

        String value = actual.attributes.get(aAttributeName);
        String idValue = actual.attributes.get("id");
        if (idValue == null) {
            idValue = "<undefined>";
        }
        if (value == null) {
            throw new AssertionError(format("\nTest %d: In node with id=\"%s\", expected attribute named \"%s\", but it was undefined.", aTestId, idValue, aAttributeName));
        }
        // we overrides the default error message with a more explicit one
        String errorMessage = format("\nTest %d: In node with id=<%s>, expected attribute named \"%s\" to be:\n  \"%s\"\n but was:\n  \"%s\"",
                                     aTestId,
                                     idValue,
                                     aAttributeName,
                                     aAttributeValue,
                                     value);
        if (!value.equals(aAttributeValue)) {
            throw new AssertionError(errorMessage);
        }

        // return the current assertion for method chaining
        return this;
    }

    @SuppressWarnings("unchecked")
    public VoltXMLElementAssert hasChildNamed(int aTestId,
                                              String aChildName,
                                              Condition<VoltXMLElement> ... aConditions) {
        isNotNull();
        VoltXMLElement child = findUniqueChildNamed(aTestId, actual, aChildName);
        for (Condition<VoltXMLElement> cond : aConditions) {
            assertThat(child).has(cond);
        }
        return this;
    }

    /**
     * Assert that a parent element has a child with the given element name, a
     * given set of attribute values and a given set of conditions.  The
     * parameters are the child's element name, followed by a sequence
     * of pairs of attributes and values, followed by a sequence of
     * Condition<VoltXMLElement> conditions.
     *
     * @param aChildName
     * @param aMatchConds
     * @return
     */
    @SafeVarargs
    public static Condition<VoltXMLElement> withChildNamed(final int aTestId,
                                                           final String aChildName,
                                                           final Object ... aMatchConds) {
        return new Condition<VoltXMLElement>() {

            @SuppressWarnings("unchecked")
            @Override
            public boolean matches(VoltXMLElement aParent) {
                final String parentId = aParent.attributes.get("id");
                List<String> attributes                    = new ArrayList<String>();
                List<String> values                        = new ArrayList<String>();
                ArrayList<Condition<VoltXMLElement>> conditions = new ArrayList<Condition<VoltXMLElement>>();
                // Sort out the attributes, values and conditions.
                for (int idx = 0; idx < aMatchConds.length; idx += 1) {
                    Object obj = aMatchConds[idx++];
                    if (obj instanceof String) {
                        attributes.add((String)obj);
                        if (idx < aMatchConds.length  && aMatchConds[idx] instanceof String) {
                            values.add((String)aMatchConds[idx]);
                        }
                    } else if (obj instanceof Condition<?>) {
                        conditions.add((Condition<VoltXMLElement>)obj);
                    } else {
                        Fail.fail(String.format("In node with id \"%s\", bad Match condition\n",
                                                (parentId == null) ? "<none>" : parentId));
                    }
                }

                List<VoltXMLElement> children = aParent.findChildren(aChildName);
                if (children == null) {
                    Fail.fail(String.format("Can't find child named: <%s>", aChildName));
                }
                VoltXMLElement matchingChild = null;
                for (VoltXMLElement child : children) {
                    boolean attrsMatch = true;
                    for (int idx = 0; idx < attributes.size(); idx += 1) {
                        String attrName     = attributes.get(idx);
                        String expValue     = values.get(idx);
                        String elementValue = child.attributes.get(attrName);
                        if (elementValue == null || expValue.equals(elementValue) == false) {
                            attrsMatch = false;
                            break;
                        }
                    }
                    if (attrsMatch == true) {
                        matchingChild = child;
                        break;
                    }
                }
                if (matchingChild == null) {
                    Fail.fail(String.format("Test %d: In node with id \"%s\", can't find child with name \"%s\"\n  attrs: %s\n",
                                            aTestId, parentId, aChildName, attrToString(attributes, values)));
                }
                assertThat(matchingChild).hasAllOf((Condition<VoltXMLElement>[]) conditions.toArray(new Condition<?>[conditions.size()]));
                return true;
            }

        };
    }

    public static Condition<VoltXMLElement> withAttribute(final int aTestId, final String aName, final String aValue) {
        return new Condition<VoltXMLElement>() {

            @Override
            public boolean matches(VoltXMLElement arg0) {
                assertThat(arg0).hasAttribute(aTestId, aName, aValue);
                return true;
            }
        };
    }

    public static Condition<VoltXMLElement> withIdAttribute(final int aTestId, final IDTable aIDs) {
        return new Condition<VoltXMLElement>() {

            @Override
            public boolean matches(VoltXMLElement arg0) {
                String idValue = arg0.attributes.get("id");
                if (idValue == null) {
                    Fail.fail(String.format("Test %d: Expected an \"id\" attribute", aTestId));
                } else if (aIDs.addId(idValue) == false) {
                    Fail.fail(String.format("Duplicate \"id\" value: \"%s\"", idValue));
                }
                return true;
            }
        };
    }

    protected static VoltXMLElement findUniqueChildNamed(final int aTestId,
                                                         VoltXMLElement aParent,
                                                         String aChildName) {
        List<VoltXMLElement> children = aParent.findChildren(aChildName);
        if (children.size() != 1) {
            Fail.fail(String.format("Expected a unique child named \"%s\"", aChildName));
        }
        return children.get(0);
    }

    private static String attrToString(List<String> aAttrs, List<String> aVals) {
        StringBuffer sb = new StringBuffer();
        for (int idx = 0; idx < aAttrs.size() && idx < aVals.size(); idx += 1) {
            sb.append(aAttrs.get(idx))
              .append(" -> ")
              .append(aVals.get(idx));
        }
        return sb.toString();
    }
}

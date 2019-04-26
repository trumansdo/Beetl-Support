/*

 * Copyright 2019 trumansdo

 *

 * Licensed under the Apache License, Version 2.0 (the "License");

 * you may not use this file except in compliance with the License.

 * You may obtain a copy of the License at

 *

 * http://www.apache.org/licenses/LICENSE-2.0

 *

 * Unless required by applicable law or agreed to in writing, software

 * distributed under the License is distributed on an "AS IS" BASIS,

 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.

 * See the License for the specific language governing permissions and

 * limitations under the License.

 */



// This is a generated file. Not intended for manual editing.
package com.intellij.ibeetl.generated.psi;

import com.intellij.psi.tree.IElementType;
import com.intellij.psi.PsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.ibeetl.lang.psi.BeetlLazyIElementType;
import com.intellij.ibeetl.lang.psi.BeetlTokenType;
import com.intellij.ibeetl.generated.psi.impl.*;
import java.util.Collections;
import java.util.Set;
import java.util.LinkedHashMap;
import com.intellij.psi.impl.source.tree.CompositePsiElement;

public interface BeetlTypes {

  IElementType BTL_BINARY_EXPRESSION = new BeetlLazyIElementType("BTL_BINARY_EXPRESSION");
  IElementType BTL_LITERAL = new BeetlLazyIElementType("BTL_LITERAL");

  IElementType BTL_BLOCK_COMMENT = new BeetlTokenType("block_comment");
  IElementType BTL_EQ = new BeetlTokenType("=");
  IElementType BTL_ID = new BeetlTokenType("id");
  IElementType BTL_LDT = new BeetlTokenType("<%");
  IElementType BTL_LINE_COMMENT = new BeetlTokenType("line_comment");
  IElementType BTL_NUMBER = new BeetlTokenType("number");
  IElementType BTL_PLUS = new BeetlTokenType("+");
  IElementType BTL_RDT = new BeetlTokenType("%>");
  IElementType BTL_STRING = new BeetlTokenType("string");

  class Classes {

    public static Class<?> findClass(IElementType elementType) {
      return ourMap.get(elementType);
    }

    public static Set<IElementType> elementTypes() {
      return Collections.unmodifiableSet(ourMap.keySet());
    }

    private static final LinkedHashMap<IElementType, Class<?>> ourMap = new LinkedHashMap<IElementType, Class<?>>();

    static {
      ourMap.put(BTL_BINARY_EXPRESSION, BeetlBinaryExpressionImpl.class);
      ourMap.put(BTL_LITERAL, BeetlLiteralImpl.class);
    }
  }

  class Factory {
    public static CompositePsiElement createElement(ASTNode node) {
      IElementType type = node.getElementType();
      if (type == BTL_BINARY_EXPRESSION) {
        return new BeetlBinaryExpressionImpl(type);
      }
      else if (type == BTL_LITERAL) {
        return new BeetlLiteralImpl(type);
      }
      throw new AssertionError("Unknown element node: " + node);
    }
  }
}

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
package com.intellij.ibeetl.lang.lexer;

import com.intellij.ibeetl.lang.base.BeetlCompositeElement;
import com.intellij.ibeetl.lang.base.BeetlIElementType;
import com.intellij.ibeetl.lang.base.BeetlTokenType;
import com.intellij.lang.ASTNode;
import com.intellij.psi.tree.IElementType;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Set;

public interface BeetlTokenTypes {

	IElementType BT_LPLACEHOLDER = new BeetlIElementType("beetl[lplaceholder]");
	IElementType BT_RPLACEHOLDER = new BeetlIElementType("beetl[rplaceholder]");

	IElementType BT_LDELIMITER = new BeetlTokenType("beetl[<%]");
	IElementType BT_RDELIMITER = new BeetlTokenType("beetl[%>]");

	IElementType BT_HTML_TAG_END = new BeetlTokenType("beetl[html_tag_end]");
	IElementType BT_HTML_TAG_START = new BeetlTokenType("beetl[html_tag_start]");

	IElementType BT_AJAX = new BeetlTokenType("beetl[#ajax]");
	IElementType BT_ASSIGN = new BeetlTokenType("beetl[=]");
	IElementType BT_AT = new BeetlTokenType("beetl[@]");
	IElementType BT_BIT_AND = new BeetlTokenType("beetl[&]");
	IElementType BT_BIT_AND_ASSIGN = new BeetlTokenType("beetl[&=]");
	IElementType BT_BIT_OR = new BeetlTokenType("beetl[|]");
	IElementType BT_BIT_OR_ASSIGN = new BeetlTokenType("beetl[|=]");
	IElementType BT_BIT_XOR = new BeetlTokenType("beetl[^]");
	IElementType BT_BIT_XOR_ASSIGN = new BeetlTokenType("beetl[^=]");
	IElementType BT_BREAK = new BeetlTokenType("beetl[break]");
	IElementType BT_CASE = new BeetlTokenType("beetl[case]");
	IElementType BT_CATCH = new BeetlTokenType("beetl[catch]");
	IElementType BT_COLON = new BeetlTokenType("beetl[:]");
	IElementType BT_COMMA = new BeetlTokenType("beetl[,]");
	IElementType BT_COND_AND = new BeetlTokenType("beetl[&&]");
	IElementType BT_COND_OR = new BeetlTokenType("beetl[||]");
	IElementType BT_CONST = new BeetlTokenType("beetl[const]");
	IElementType BT_CONTINUE = new BeetlTokenType("beetl[continue]");
	IElementType BT_DECREASE = new BeetlTokenType("beetl[--]");
	IElementType BT_DEFAULT = new BeetlTokenType("beetl[default]");
	IElementType BT_DIRECTIVE = new BeetlTokenType("beetl[directive]");
	IElementType BT_DOT = new BeetlTokenType("beetl[.]");
	IElementType BT_ELSE = new BeetlTokenType("beetl[else]");
	IElementType BT_ELSE_FOR = new BeetlTokenType("beetl[elsefor]");
	IElementType BT_EQ = new BeetlTokenType("beetl[==]");
	IElementType BT_FALSE = new BeetlTokenType("beetl[false]");
	IElementType BT_FOR = new BeetlTokenType("beetl[for]");
	IElementType BT_FOR_IN = new BeetlTokenType("beetl[in]");
	IElementType BT_FRAGMENT = new BeetlTokenType("beetl[#fragment]");
	IElementType BT_GREATER = new BeetlTokenType("beetl[>]");
	IElementType BT_GREATER_OR_EQUAL = new BeetlTokenType("beetl[>=]");

	IElementType BT_IF = new BeetlTokenType("beetl[if]");
	IElementType BT_INCREASE = new BeetlTokenType("beetl[++]");
	IElementType BT_INTERFACE = new BeetlTokenType("beetl[interface]");
	IElementType BT_LBRACE = new BeetlTokenType("beetl[{]");
	IElementType BT_LBRACK = new BeetlTokenType("beetl[[]");


	IElementType BT_LESS = new BeetlTokenType("beetl[<]");
	IElementType BT_LESS_OR_EQUAL = new BeetlTokenType("beetl[<=]");

	IElementType BT_LPAREN = new BeetlTokenType("beetl[(]");
	IElementType BT_RPAREN = new BeetlTokenType("beetl[)]");

	IElementType BT_MINUS = new BeetlTokenType("beetl[-]");
	IElementType BT_MINUS_ASSIGN = new BeetlTokenType("beetl[-=]");
	IElementType BT_MUL = new BeetlTokenType("beetl[*]");
	IElementType BT_MUL_ASSIGN = new BeetlTokenType("beetl[*=]");
	IElementType BT_NOT = new BeetlTokenType("beetl[!]");
	IElementType BT_NOT_EQ = new BeetlTokenType("beetl[!=]");
	IElementType BT_NULL = new BeetlTokenType("beetl[null]");
	IElementType BT_NUMBER = new BeetlTokenType("beetl[number]");
	IElementType BT_PLUS = new BeetlTokenType("beetl[+]");
	IElementType BT_PLUS_ASSIGN = new BeetlTokenType("beetl[+=]");
	IElementType BT_QUESTOIN = new BeetlTokenType("beetl[?]");
	IElementType BT_QUOTIENT = new BeetlTokenType("beetl[/]");
	IElementType BT_QUOTIENT_ASSIGN = new BeetlTokenType("beetl[/=]");
	IElementType BT_RBRACE = new BeetlTokenType("beetl[}]");
	IElementType BT_RBRACK = new BeetlTokenType("beetl[]]");

	IElementType BT_REMAINDER = new BeetlTokenType("beetl[%]");
	IElementType BT_REMAINDER_ASSIGN = new BeetlTokenType("beetl[%=]");
	IElementType BT_RETURN = new BeetlTokenType("beetl[return]");
	IElementType BT_SELECT = new BeetlTokenType("beetl[select]");
	IElementType BT_SEMICOLON = new BeetlTokenType("beetl[;]");
	IElementType BT_SHIFT_LEFT = new BeetlTokenType("beetl[<<]");
	IElementType BT_SHIFT_RIGHT = new BeetlTokenType("beetl[>>]");

	IElementType BT_SWITCH = new BeetlTokenType("beetl[switch]");
	IElementType BT_TRUE = new BeetlTokenType("beetl[true]");
	IElementType BT_TRY = new BeetlTokenType("beetl[try]");
	IElementType BT_TYPE_ = new BeetlTokenType("beetl[@type]");
	IElementType BT_VAR = new BeetlTokenType("beetl[var]");
	IElementType BT_VIRTUAL = new BeetlTokenType("beetl[.~]");
	IElementType BT_WHILE = new BeetlTokenType("beetl[while]");

	IElementType BT_IDENTIFIER = new BeetlTokenType("beetl[identifier]");
	IElementType BT_INT = new BeetlTokenType("beetl[integer]");
	IElementType BT_ATTRIBUTE_NAME = new BeetlTokenType("beetl[attribute_name]");
	IElementType BT_PLACEHOLDER_VALUE = new BeetlTokenType("beetl[placeholder_value]");
	IElementType BT_FLOAT = new BeetlTokenType("beetl[float]");
	IElementType BT_OCT = new BeetlTokenType("beetl[oct]");
	IElementType BT_ATTRIBUTE_VALUE = new BeetlTokenType("beetl[attribute_value]");
	IElementType BT_HEX = new BeetlTokenType("beetl[hex]");
	IElementType BT_STRING = new BeetlTokenType("beetl[string]");

	class Classes {

		public static Class<?> findClass(IElementType elementType) {
			return ourMap.get(elementType);
		}

		public static Set<IElementType> elementTypes() {
			return Collections.unmodifiableSet(ourMap.keySet());
		}

		private static final LinkedHashMap<IElementType, Class<?>> ourMap = new LinkedHashMap<IElementType, Class<?>>();

		static {
		}
	}

	class Factory {
		public static BeetlCompositeElement createElement(ASTNode astNode) {
			return null;
		}
	}
}

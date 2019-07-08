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

import com.intellij.ibeetl.lang.base.BeetlCompositePsiElement;
import com.intellij.ibeetl.lang.base.BeetlIElementType;
import com.intellij.ibeetl.lang.base.BeetlTokenType;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiType;
import com.intellij.psi.TokenType;
import com.intellij.psi.impl.PsiElementBase;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.TokenSet;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Set;

public interface BeetlTokenTypes {

	BeetlTokenType TEMPORARY = new BeetlTokenType("beetl[TEMPORARY]");

	BeetlTokenType WHITE_SPACE = new BeetlTokenType("beetl[white_space]");
	BeetlTokenType NEW_LINE = new BeetlTokenType("beetl[new_line]");
	BeetlTokenType HTML_NEW_LINE = new BeetlTokenType("beetl[template_text_new_line]");

	BeetlTokenType LINE_COMMENT = new BeetlTokenType("beetl[line_comment]");
	BeetlTokenType MULTILINE_COMMENT = new BeetlTokenType("beetl[multiline_comment]");

	BeetlTokenType BT_LPLACEHOLDER = new BeetlTokenType("beetl[lplaceholder]");
	BeetlTokenType BT_RPLACEHOLDER = new BeetlTokenType("beetl[rplaceholder]");

	BeetlTokenType BT_LDELIMITER = new BeetlTokenType("beetl[ldelimiter]");
	BeetlTokenType BT_RDELIMITER = new BeetlTokenType("beetl[rdelimiter]");

	BeetlTokenType BT_HTML_TAG_END = new BeetlTokenType("beetl[html_tag_end]");
	BeetlTokenType BT_HTML_TAG_START = new BeetlTokenType("beetl[html_tag_start]");

	BeetlTokenType BT_AJAX = new BeetlTokenType("beetl[#ajax]");
	BeetlTokenType BT_ASSIGN = new BeetlTokenType("beetl[=]");
	BeetlTokenType BT_AT = new BeetlTokenType("beetl[@]");
	BeetlTokenType BT_BIT_AND = new BeetlTokenType("beetl[&]");
	BeetlTokenType BT_BIT_AND_ASSIGN = new BeetlTokenType("beetl[&=]");
	BeetlTokenType BT_BIT_OR = new BeetlTokenType("beetl[|]");
	BeetlTokenType BT_BIT_OR_ASSIGN = new BeetlTokenType("beetl[|=]");
	BeetlTokenType BT_BIT_XOR = new BeetlTokenType("beetl[^]");
	BeetlTokenType BT_BIT_XOR_ASSIGN = new BeetlTokenType("beetl[^=]");
	BeetlTokenType BT_BREAK = new BeetlTokenType("beetl[break]");
	BeetlTokenType BT_CASE = new BeetlTokenType("beetl[case]");
	BeetlTokenType BT_CATCH = new BeetlTokenType("beetl[catch]");
	BeetlTokenType BT_COLON = new BeetlTokenType("beetl[:]");
	BeetlTokenType BT_COMMA = new BeetlTokenType("beetl[,]");
	BeetlTokenType BT_COND_AND = new BeetlTokenType("beetl[&&]");
	BeetlTokenType BT_COND_OR = new BeetlTokenType("beetl[||]");
	BeetlTokenType BT_CONST = new BeetlTokenType("beetl[const]");
	BeetlTokenType BT_CONTINUE = new BeetlTokenType("beetl[continue]");
	BeetlTokenType BT_DECREASE = new BeetlTokenType("beetl[--]");
	BeetlTokenType BT_DEFAULT = new BeetlTokenType("beetl[default]");
	BeetlTokenType BT_DIRECTIVE = new BeetlTokenType("beetl[directive]");
	BeetlTokenType BT_DOT = new BeetlTokenType("beetl[.]");
	BeetlTokenType BT_ELSE = new BeetlTokenType("beetl[else]");
	BeetlTokenType BT_ELSE_FOR = new BeetlTokenType("beetl[elsefor]");
	BeetlTokenType BT_EQ = new BeetlTokenType("beetl[==]");
	BeetlTokenType BT_FALSE = new BeetlTokenType("beetl[false]");
	BeetlTokenType BT_FOR = new BeetlTokenType("beetl[for]");
	BeetlTokenType BT_FOR_IN = new BeetlTokenType("beetl[in]");
	BeetlTokenType BT_FRAGMENT = new BeetlTokenType("beetl[#fragment]");
	BeetlTokenType BT_GREATER = new BeetlTokenType("beetl[>]");
	BeetlTokenType BT_GREATER_OR_EQUAL = new BeetlTokenType("beetl[>=]");

	BeetlTokenType BT_IF = new BeetlTokenType("beetl[if]");
	BeetlTokenType BT_INCREASE = new BeetlTokenType("beetl[++]");
	BeetlTokenType BT_INTERFACE = new BeetlTokenType("beetl[interface]");
	BeetlTokenType BT_LBRACE = new BeetlTokenType("beetl[{]");
	BeetlTokenType BT_LBRACK = new BeetlTokenType("beetl[[]");


	BeetlTokenType BT_LESS = new BeetlTokenType("beetl[<]");
	BeetlTokenType BT_LESS_OR_EQUAL = new BeetlTokenType("beetl[<=]");

	BeetlTokenType BT_LPAREN = new BeetlTokenType("beetl[(]");
	BeetlTokenType BT_RPAREN = new BeetlTokenType("beetl[)]");

	BeetlTokenType BT_MINUS = new BeetlTokenType("beetl[-]");
	BeetlTokenType BT_MINUS_ASSIGN = new BeetlTokenType("beetl[-=]");
	BeetlTokenType BT_MUL = new BeetlTokenType("beetl[*]");
	BeetlTokenType BT_MUL_ASSIGN = new BeetlTokenType("beetl[*=]");
	BeetlTokenType BT_NOT = new BeetlTokenType("beetl[!]");
	BeetlTokenType BT_NOT_EQ = new BeetlTokenType("beetl[!=]");
	BeetlTokenType BT_NULL = new BeetlTokenType("beetl[null]");
	BeetlTokenType BT_NUMBER = new BeetlTokenType("beetl[number]");
	BeetlTokenType BT_PLUS = new BeetlTokenType("beetl[+]");
	BeetlTokenType BT_PLUS_ASSIGN = new BeetlTokenType("beetl[+=]");
	BeetlTokenType BT_QUESTOIN = new BeetlTokenType("beetl[?]");
	BeetlTokenType BT_QUOTIENT = new BeetlTokenType("beetl[/]");
	BeetlTokenType BT_QUOTIENT_ASSIGN = new BeetlTokenType("beetl[/=]");
	BeetlTokenType BT_RBRACE = new BeetlTokenType("beetl[}]");
	BeetlTokenType BT_RBRACK = new BeetlTokenType("beetl[]]");

	BeetlTokenType BT_REMAINDER = new BeetlTokenType("beetl[%]");
	BeetlTokenType BT_REMAINDER_ASSIGN = new BeetlTokenType("beetl[%=]");
	BeetlTokenType BT_RETURN = new BeetlTokenType("beetl[return]");
	BeetlTokenType BT_SELECT = new BeetlTokenType("beetl[select]");
	BeetlTokenType BT_SEMICOLON = new BeetlTokenType("beetl[;]");
	BeetlTokenType BT_SHIFT_LEFT = new BeetlTokenType("beetl[<<]");
	BeetlTokenType BT_SHIFT_RIGHT = new BeetlTokenType("beetl[>>]");

	BeetlTokenType BT_SWITCH = new BeetlTokenType("beetl[switch]");
	BeetlTokenType BT_TRUE = new BeetlTokenType("beetl[true]");
	BeetlTokenType BT_TRY = new BeetlTokenType("beetl[try]");
	BeetlTokenType BT_TYPE_ = new BeetlTokenType("beetl[@type]");
	BeetlTokenType BT_VAR = new BeetlTokenType("beetl[var]");
	BeetlTokenType BT_VIRTUAL = new BeetlTokenType("beetl[.~]");
	BeetlTokenType BT_WHILE = new BeetlTokenType("beetl[while]");

	BeetlTokenType BT_IDENTIFIER = new BeetlTokenType("beetl[identifier]");
	BeetlTokenType BT_INT = new BeetlTokenType("beetl[integer]");
	BeetlTokenType BT_ATTRIBUTE_NAME = new BeetlTokenType("beetl[attribute_name]");
	BeetlTokenType BT_PLACEHOLDER_VALUE = new BeetlTokenType("beetl[placeholder_value]");
	BeetlTokenType BT_FLOAT = new BeetlTokenType("beetl[float]");
	BeetlTokenType BT_OCT = new BeetlTokenType("beetl[oct]");
	BeetlTokenType BT_ATTRIBUTE_VALUE = new BeetlTokenType("beetl[attribute_value]");
	BeetlTokenType BT_HEX = new BeetlTokenType("beetl[hex]");
	BeetlTokenType BT_STRING = new BeetlTokenType("beetl[string]");

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
		public static PsiElement createElement(ASTNode astNode) {
			IElementType elementType = astNode.getElementType();
			if(elementType instanceof BeetlTokenType){
				return ((BeetlTokenType) elementType).createPsiElement(astNode);
			}
			return null;
		}
	}
}

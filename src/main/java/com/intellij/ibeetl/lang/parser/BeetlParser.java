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


package com.intellij.ibeetl.lang.parser;

import com.intellij.lang.pratt.*;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.Nullable;

import static com.intellij.ibeetl.lang.lexer.BeetlIElementTypes.BTL_TEMPLATE_HTML_TEXT;
import static com.intellij.ibeetl.lang.lexer.BeetlIElementTypes.VIRTUAL_ROOT;
import static com.intellij.ibeetl.lang.lexer.BeetlTokenTypes.*;
import static com.intellij.ibeetl.lang.parser.BeetlPrattRegistry.REGISTRY;
import static com.intellij.ibeetl.lang.parser.BeetlPrattRegistry.registerParser;
import static com.intellij.ibeetl.lang.parser.BeetlPsiElementTypes.BEETL_BLOCK;

public class BeetlParser extends PrattParser {
	public static final int INIT_LEVEL=0;

	@Override
	protected void parse(PrattBuilder builder) {
		MutableMarker rootTag = builder.mark();
		if (!builder.isEof()) {
			super.parse(builder);
		}
		rootTag.finish(VIRTUAL_ROOT);
	}

	@Override
	protected PrattRegistry getRegistry() {
		return REGISTRY;
	}

	static {
		registerParser(BTL_TEMPLATE_HTML_TEXT, 1, AppendTokenParser.JUST_APPEND);
//		registerParser(BT_RDELIMITER, 1, AppendTokenParser.JUST_APPEND);
		registerParser(BT_LDELIMITER, 1, new TokenParser() {
			@Override
			public boolean parseToken(PrattBuilder prattBuilder) {
				MutableMarker mark = prattBuilder.mark();
				prattBuilder.advance();
				prattBuilder.createChildBuilder(INIT_LEVEL).parse();
				prattBuilder.checkToken(BT_RDELIMITER);
				mark.finish(BEETL_BLOCK);
				return true;
			}
		});

		registerParser(BT_IDENTIFIER, 1, new AppendTokenParser() {
			@Nullable
			@Override
			protected IElementType parseAppend(PrattBuilder prattBuilder) {
				return BeetlPsiElementTypes.IDENTIFIER;
			}
		});
		registerParser(BT_STRING, 1, AppendTokenParser.JUST_APPEND);
		registerParser(BT_LPAREN, 1, AppendTokenParser.JUST_APPEND);
		registerParser(BT_RPAREN, 1, AppendTokenParser.JUST_APPEND);
		registerParser(BT_LBRACE, 1, AppendTokenParser.JUST_APPEND);
		registerParser(BT_RBRACE, 1, AppendTokenParser.JUST_APPEND);
		registerParser(BT_LBRACK, 1, AppendTokenParser.JUST_APPEND);
		registerParser(BT_RBRACK, 1, AppendTokenParser.JUST_APPEND);
		registerParser(BT_LPLACEHOLDER, 1, AppendTokenParser.JUST_APPEND);
		registerParser(BT_RPLACEHOLDER, 1, AppendTokenParser.JUST_APPEND);
		registerParser(BT_PLACEHOLDER_VALUE, 1, AppendTokenParser.JUST_APPEND);
		registerParser(BT_HTML_TAG_START, 1, AppendTokenParser.JUST_APPEND);
		registerParser(BT_HTML_TAG_END, 1, AppendTokenParser.JUST_APPEND);
		registerParser(BT_ASSIGN, 1, TokenParser.infix(11,BeetlPsiElementTypes.VAR_DEFINIED));
		registerParser(BT_ATTRIBUTE_NAME, 1, AppendTokenParser.JUST_APPEND);
		registerParser(BT_ATTRIBUTE_VALUE, 1, AppendTokenParser.JUST_APPEND);
		registerParser(BT_SEMICOLON, 1, AppendTokenParser.JUST_APPEND);
		registerParser(BT_VAR, 1, AppendTokenParser.JUST_APPEND);
		registerParser(BT_FOR, 1, AppendTokenParser.JUST_APPEND);
		registerParser(BT_FOR_IN, 1, AppendTokenParser.JUST_APPEND);
		registerParser(BT_IF, 1, AppendTokenParser.JUST_APPEND);
		registerParser(BT_ELSE, 1, AppendTokenParser.JUST_APPEND);
		registerParser(BT_INT, 12, AppendTokenParser.JUST_APPEND);
		registerParser(BT_PLUS, 57, TokenParser.infix(10,BeetlPsiElementTypes.BINARY_EXPRESSION));
		registerParser(BT_INCREASE, 1, AppendTokenParser.JUST_APPEND);
		registerParser(BT_DOT, 1, AppendTokenParser.JUST_APPEND);
	}

}

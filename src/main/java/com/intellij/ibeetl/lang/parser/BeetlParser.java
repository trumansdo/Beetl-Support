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

import static com.intellij.ibeetl.lang.lexer.BeetlIElementTypes.BTL_TEMPLATE_HTML_TEXT;
import static com.intellij.ibeetl.lang.lexer.BeetlTokenTypes.*;
import static com.intellij.ibeetl.lang.psi.BeetlPrattRegistry.REGISTRY;
import static com.intellij.ibeetl.lang.psi.BeetlPrattRegistry.registerParser;

public class BeetlParser extends PrattParser {


	@Override
	protected void parse(PrattBuilder builder) {
		MutableMarker rootTag = builder.mark();
		if (!builder.isEof()) {
			super.parse(builder);
		}
		rootTag.drop();
	}

	@Override
	protected PrattRegistry getRegistry() {
		return REGISTRY;
	}

	static {
		registerParser(BTL_TEMPLATE_HTML_TEXT, 1, AppendTokenParser.JUST_APPEND);
		registerParser(BT_LDELIMITER, 1, AppendTokenParser.JUST_APPEND);
		registerParser(BT_RDELIMITER, 1, AppendTokenParser.JUST_APPEND);
		registerParser(BT_IDENTIFIER, 1, AppendTokenParser.JUST_APPEND);
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
		registerParser(BT_ASSIGN, 1, AppendTokenParser.JUST_APPEND);
		registerParser(BT_ATTRIBUTE_NAME, 1, AppendTokenParser.JUST_APPEND);
		registerParser(BT_ATTRIBUTE_VALUE, 1, AppendTokenParser.JUST_APPEND);
		registerParser(BT_FOR, 1, AppendTokenParser.JUST_APPEND);
		registerParser(BT_FOR_IN, 1, AppendTokenParser.JUST_APPEND);
		registerParser(BT_IF, 1, AppendTokenParser.JUST_APPEND);
		registerParser(BT_ELSE, 1, AppendTokenParser.JUST_APPEND);
		registerParser(BT_NUMBER, 1, AppendTokenParser.JUST_APPEND);
		registerParser(BT_PLUS, 1, AppendTokenParser.JUST_APPEND);
		registerParser(BT_DOT, 1, AppendTokenParser.JUST_APPEND);
	}

}

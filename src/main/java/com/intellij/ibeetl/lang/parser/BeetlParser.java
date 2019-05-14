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

import com.intellij.ibeetl.lang.lexer.BeetlIElementTypes;
import com.intellij.ibeetl.lang.lexer.BeetlTokenTypes;
import com.intellij.ibeetl.lang.psi.BeetlPrattRegistry;
import com.intellij.lang.pratt.*;

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
		return BeetlPrattRegistry.REGISTRY;
	}

	static {
		BeetlPrattRegistry.REGISTRY.registerParser(BeetlIElementTypes.BTL_TEMPLATE_HTML_TEXT, 1, AppendTokenParser.JUST_APPEND);
		BeetlPrattRegistry.REGISTRY.registerParser(BeetlTokenTypes.BT_LDELIMITER, 1, AppendTokenParser.JUST_APPEND);
		BeetlPrattRegistry.REGISTRY.registerParser(BeetlTokenTypes.BT_NUMBER, 1, AppendTokenParser.JUST_APPEND);
		BeetlPrattRegistry.REGISTRY.registerParser(BeetlTokenTypes.BT_PLUS, 1, AppendTokenParser.JUST_APPEND);
	}

}

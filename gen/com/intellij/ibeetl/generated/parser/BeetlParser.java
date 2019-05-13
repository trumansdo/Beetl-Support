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


package com.intellij.ibeetl.generated.parser;

import com.intellij.ibeetl.generated.psi.BeetlTypes;
import com.intellij.ibeetl.lang.lexer.BeetlFileElementType;
import com.intellij.ibeetl.lang.psi.BeetlPrattRegistry;
import com.intellij.ibeetl.lang.psi.BeetlTokenType;
import com.intellij.lang.pratt.*;

import static com.intellij.ibeetl.generated.psi.BeetlTypes.BTL_RDT;

public class BeetlParser extends PrattParser {

  static {
    BeetlPrattRegistry.REGISTRY.registerParser(BeetlTypes.BTL_TEMPLATE_HTML_TEXT, 1, AppendTokenParser.JUST_APPEND);
  }

  @Override
  protected void parse(PrattBuilder builder) {
    MutableMarker rootTag = builder.mark();
    if (!builder.isEof()) {
      super.parse(builder);
    }
    rootTag.finish(BTL_RDT);
  }

  @Override
  protected PrattRegistry getRegistry() {
    return BeetlPrattRegistry.REGISTRY;
  }


}

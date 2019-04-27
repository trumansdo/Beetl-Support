package com.intellij.ibeetl.lang.lexer;

import com.intellij.ibeetl.generated.lexer._BeetlLexer;
import com.intellij.lexer.FlexAdapter;


public class BeetlLexer extends FlexAdapter {

	public BeetlLexer() {
		super(new _BeetlLexer(null));
	}
}

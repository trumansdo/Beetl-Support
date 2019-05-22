package com.intellij.ibeetl.lang.lexer;

import com.intellij.ibeetl.generated.lexer._BeetlLexer;
import com.intellij.lexer.FlexAdapter;
import com.intellij.lexer.LookAheadLexer;


public class BeetlLexer extends LookAheadLexer {

	public BeetlLexer() {
		super(new FlexAdapter(new _BeetlLexer()));
	}
}

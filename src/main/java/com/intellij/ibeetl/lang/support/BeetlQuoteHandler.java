package com.intellij.ibeetl.lang.support;

import com.intellij.codeInsight.editorActions.SimpleTokenSetQuoteHandler;
import com.intellij.ibeetl.lang.lexer.BeetlTokenSets;
import com.intellij.ibeetl.lang.lexer.BeetlTokenTypes;
import com.intellij.psi.tree.TokenSet;

public class BeetlQuoteHandler extends SimpleTokenSetQuoteHandler {
	public BeetlQuoteHandler() {
		super(BeetlTokenSets.STRINGS);
	}
}

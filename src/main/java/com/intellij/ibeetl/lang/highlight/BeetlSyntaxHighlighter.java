package com.intellij.ibeetl.lang.highlight;

import com.intellij.ibeetl.lang.lexer.BeetlLexer;
import com.intellij.lexer.Lexer;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;

public class BeetlSyntaxHighlighter extends SyntaxHighlighterBase {
	@NotNull
	@Override
	public Lexer getHighlightingLexer() {
		return new BeetlLexer();
	}

	@NotNull
	@Override
	public TextAttributesKey[] getTokenHighlights(IElementType tokenType) {
		return new TextAttributesKey[0];
	}
}

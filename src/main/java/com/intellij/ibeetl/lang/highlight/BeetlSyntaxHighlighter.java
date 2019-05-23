package com.intellij.ibeetl.lang.highlight;

import com.intellij.ibeetl.lang.lexer.BeetlLexer;
import com.intellij.ibeetl.lang.lexer.BeetlTokenSets;
import com.intellij.lexer.Lexer;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.TokenSet;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

import static com.intellij.ibeetl.lang.highlight.BeetlSyntaxHighlighterColors.*;
import static com.intellij.ibeetl.lang.lexer.BeetlTokenTypes.*;

public class BeetlSyntaxHighlighter extends SyntaxHighlighterBase {
	protected static final Map<IElementType, TextAttributesKey> keys1 = new HashMap();
	protected static final Map<IElementType, TextAttributesKey> keys2 = new HashMap();

	static {
		fillMap(BeetlTokenSets.KEYWORDS, BTL_KEYWORD);
		fillMap(BeetlTokenSets.OPERATIONS, BTL_OPERATIONS);
		fillMap(BeetlTokenSets.STRINGS, BTL_STRING);
		fillMap(BeetlTokenSets.LITERAL, BTL_LITERALS);
		fillMap(BeetlTokenSets.NUMBER, BTL_NUMBER);
		fillMap(BeetlTokenSets.DELIMITERS, BTL_DELIMITERS);
		fillMap(BeetlTokenSets.PLACEHOLDERS, BTL_PLACEHOLDERS);
		fillMap(BeetlTokenSets.PARENTHS, BTL_PARENTHS);
		fillMap(BeetlTokenSets.BRACKETS, BTL_BRACKETS);
		fillMap(BeetlTokenSets.BRACES, BTL_BRACES);
		fillMap(BeetlTokenSets.DELIMITERS, BTL_DELIMITERS);

		putTokenType(BT_DOT, BTL_DOT);
		putTokenType(BT_SEMICOLON, BTL_SEMICOLON);
		putTokenType(BT_COMMA, BTL_COMMA);
		putTokenType(BT_IDENTIFIER, BTL_IDENT);
		putTokenType(BT_ATTRIBUTE_NAME, BTL_REFERENCE);
		putTokenType(BT_PLACEHOLDER_VALUE, BTL_KEYWORD);
		putTokenType(LINE_COMMENT, BTL_LINE_COMMENT);
		putTokenType(MULTILINE_COMMENT, BTL_BLOCK_COMMENT);
	}

	public BeetlSyntaxHighlighter() {

	}

	/*多个tokenset集合*/
	protected static void fillMap(TokenSet tokenSet, TextAttributesKey key) {
		SyntaxHighlighterBase.fillMap(keys1, tokenSet, key);
		SyntaxHighlighterBase.fillMap(keys2, tokenSet, BTL_BACKGROUND);
	}

	/*单个的token*/
	protected static void putTokenType(IElementType elementType, TextAttributesKey key) {
		keys1.put(elementType, key);
		keys2.put(elementType, BTL_BACKGROUND);
	}

	@NotNull
	@Override
	public Lexer getHighlightingLexer() {
		return new BeetlLexer();
	}

	@NotNull
	@Override
	public TextAttributesKey[] getTokenHighlights(IElementType tokenType) {
		TextAttributesKey[] textAttributesKeys = SyntaxHighlighterBase.pack(this.keys2.get(tokenType), this.keys1.get(tokenType));
		return textAttributesKeys;
	}

}

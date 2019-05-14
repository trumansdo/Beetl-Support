package com.intellij.ibeetl.lang.highlight;

import com.intellij.ibeetl.lang.lexer.BeetlLexer;
import com.intellij.ibeetl.lang.lexer.BeetlTokenSets;
import com.intellij.lexer.Lexer;
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.TokenSet;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class BeetlSyntaxHighlighter extends SyntaxHighlighterBase {
	protected final Map<IElementType, TextAttributesKey> keys1 = new HashMap();
	protected final Map<IElementType, TextAttributesKey> keys2 = new HashMap();

	public static TextAttributesKey BTL_BACKGROUND;
	public static TextAttributesKey BTL_IDENT;
	public static TextAttributesKey BTL_KEYWORD;
	public static TextAttributesKey BTL_STRING;
	public static TextAttributesKey BTL_NUMBER;
	public static TextAttributesKey BTL_PARENTHS;
	public static TextAttributesKey BTL_DOT;
	public static TextAttributesKey BTL_COMMA;
	public static TextAttributesKey BTL_BRACKETS;
	public static TextAttributesKey BTL_BOUNDS;

	static {
		BTL_BACKGROUND = TextAttributesKey.createTextAttributesKey("BTL.BACKGROUND", DefaultLanguageHighlighterColors.TEMPLATE_LANGUAGE_COLOR);
		BTL_IDENT = TextAttributesKey.createTextAttributesKey("BTL.IDENT", DefaultLanguageHighlighterColors.LOCAL_VARIABLE);
		BTL_KEYWORD = TextAttributesKey.createTextAttributesKey("BTL.KEYWORD", DefaultLanguageHighlighterColors.KEYWORD);
		BTL_STRING = TextAttributesKey.createTextAttributesKey("BTL.STRING", DefaultLanguageHighlighterColors.STRING);
		BTL_NUMBER = TextAttributesKey.createTextAttributesKey("BTL.NUMBER", DefaultLanguageHighlighterColors.NUMBER);
		BTL_PARENTHS = TextAttributesKey.createTextAttributesKey("BTL.PARENTHS", DefaultLanguageHighlighterColors.PARENTHESES);
		BTL_DOT = TextAttributesKey.createTextAttributesKey("BTL.DOT", DefaultLanguageHighlighterColors.DOT);
		BTL_COMMA = TextAttributesKey.createTextAttributesKey("BTL.COMMA", DefaultLanguageHighlighterColors.COMMA);
		BTL_BRACKETS = TextAttributesKey.createTextAttributesKey("BTL.BRACKETS", DefaultLanguageHighlighterColors.BRACKETS);
		BTL_BOUNDS = TextAttributesKey.createTextAttributesKey("BTL.BOUNDS", DefaultLanguageHighlighterColors.KEYWORD);
	}

	public BeetlSyntaxHighlighter() {
		this.fillMap(BeetlTokenSets.KEYWORDS, BTL_KEYWORD);
		this.fillMap(BeetlTokenSets.UNARY_OPERATIONS, BTL_BOUNDS);
		this.fillMap(BeetlTokenSets.STRING_LITERALS, BTL_STRING);
		this.fillMap(BeetlTokenSets.NUMBER, BTL_NUMBER);
		this.fillMap(BeetlTokenSets.BINARY_OPERATIONS, BTL_KEYWORD);
	}

	protected void fillMap(TokenSet tokenSet, TextAttributesKey key) {
		SyntaxHighlighterBase.fillMap(this.keys1, tokenSet, key);
		SyntaxHighlighterBase.fillMap(this.keys2, tokenSet, BTL_BACKGROUND);
	}

	protected void putTokenType(IElementType elementType, TextAttributesKey key) {
		this.keys1.put(elementType, key);
		this.keys2.put(elementType, BTL_BACKGROUND);
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

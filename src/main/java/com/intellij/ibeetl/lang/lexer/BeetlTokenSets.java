package com.intellij.ibeetl.lang.lexer;

import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.TokenSet;

import static com.intellij.ibeetl.lang.BeetlParserDefinition.WHITE_SPACES;
import static com.intellij.ibeetl.lang.lexer.BeetlTokenTypes.*;

public interface BeetlTokenSets {
	TokenSet WHITESPACES = TokenSet.create(new IElementType[]{WHITE_SPACES});
	TokenSet STRINGS = TokenSet.create(new IElementType[]{BT_STRING, BT_ATTRIBUTE_VALUE});
	TokenSet LITERAL = TokenSet.create(new IElementType[]{BT_NULL, BT_TRUE, BT_FALSE});
	TokenSet NUMBER = TokenSet.create(new IElementType[]{BT_FLOAT, BT_INT, BT_OCT, BT_HEX});
	TokenSet OPERATIONS = TokenSet.create(new IElementType[]{BT_NOT, BT_INCREASE, BT_DECREASE, BT_AT, BT_ASSIGN, BT_MINUS_ASSIGN, BT_MUL_ASSIGN, BT_PLUS_ASSIGN,
			BT_QUOTIENT_ASSIGN, BT_REMAINDER_ASSIGN, BT_BIT_AND, BT_COLON, BT_EQ, BT_NOT_EQ, BT_BIT_OR, BT_MINUS, BT_COND_OR, BT_BIT_OR_ASSIGN, BT_COND_AND,
			BT_BIT_AND_ASSIGN, BT_SHIFT_LEFT, BT_LESS_OR_EQUAL, BT_LESS, BT_BIT_XOR, BT_BIT_XOR_ASSIGN, BT_MUL, BT_QUOTIENT,
			BT_REMAINDER, BT_SHIFT_RIGHT, BT_GREATER, BT_GREATER_OR_EQUAL, BT_VIRTUAL, BT_QUESTOIN});
	TokenSet KEYWORDS = TokenSet.create(new IElementType[]{BT_BREAK, BT_RETURN, BT_CONTINUE, BT_DEFAULT, BT_INTERFACE, BT_SWITCH, BT_SELECT, BT_CASE, BT_CONST, BT_IF,
			BT_FOR, BT_ELSE_FOR, BT_ELSE, BT_WHILE, BT_DIRECTIVE, BT_TYPE_, BT_VAR, BT_TRY, BT_CATCH, BT_AJAX, BT_FRAGMENT, BT_FOR_IN});

	TokenSet DELIMITERS = TokenSet.create(new IElementType[]{BT_LDELIMITER, BT_RDELIMITER,BT_HTML_TAG_START,BT_HTML_TAG_END});
	TokenSet PLACEHOLDERS = TokenSet.create(new IElementType[]{BT_LPLACEHOLDER, BT_RPLACEHOLDER});

	TokenSet PARENTHS = TokenSet.create(new IElementType[]{BT_LPAREN, BT_RPAREN});
	TokenSet BRACKETS = TokenSet.create(new IElementType[]{BT_LBRACK, BT_RBRACK});
	TokenSet BRACES = TokenSet.create(new IElementType[]{BT_LBRACE, BT_RBRACE});
}

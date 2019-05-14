package com.intellij.ibeetl.lang.lexer;

import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.TokenSet;

import static com.intellij.ibeetl.lang.BeetlParserDefinition.WHITE_SPACES;
import static com.intellij.ibeetl.lang.lexer.BeetlTokenTypes.*;

public interface BeetlTokenSets {
	TokenSet WHITESPACES = TokenSet.create(new IElementType[]{WHITE_SPACES});
	TokenSet STRING_LITERALS = TokenSet.create(new IElementType[]{BT_STRING,BT_ATTRIBUTE_VALUE});
	TokenSet NUMBER = TokenSet.create(new IElementType[]{BT_NUMBER,BT_FLOAT,BT_INT});
	TokenSet UNARY_OPERATIONS = TokenSet.create(new IElementType[]{BT_NOT,BT_INCREASE,BT_DECREASE,BT_AT});
	TokenSet KEYWORDS = TokenSet.create(new IElementType[]{BT_IF,BT_ELSE,BT_FOR,BT_FOR_IN,BT_WHILE,BT_SWITCH,BT_ATTRIBUTE_NAME});
	TokenSet BINARY_OPERATIONS = TokenSet.create(new IElementType[]{BT_PLUS,BT_ASSIGN});

}

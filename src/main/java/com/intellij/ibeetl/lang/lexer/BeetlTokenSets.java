package com.intellij.ibeetl.lang.lexer;

import com.intellij.ibeetl.generated.psi.BeetlTypes;
import com.intellij.psi.TokenType;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.TokenSet;

public interface BeetlTokenSets {
	TokenSet WHITESPACES = TokenSet.create(new IElementType[]{TokenType.WHITE_SPACE});
	TokenSet STRING_LITERALS = TokenSet.create(new IElementType[]{BeetlTypes.BTL_STRING});
	TokenSet NUMBER = TokenSet.create(new IElementType[]{BeetlTypes.BTL_NUMBER});
	TokenSet UNARY_OPERATIONS = TokenSet.create(new IElementType[]{BeetlTypes.BTL_LDT,BeetlTypes.BTL_RDT});
	TokenSet KEYWORDS = TokenSet.create(new IElementType[]{BeetlTypes.BTL_ID});
	TokenSet BINARY_OPERATIONS = TokenSet.create(new IElementType[]{BeetlTypes.BTL_PLUS,BeetlTypes.BTL_EQ});

}

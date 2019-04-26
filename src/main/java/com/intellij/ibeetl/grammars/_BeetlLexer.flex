package com.intellij.ibeetl.generated.lexer;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;

import static com.intellij.psi.TokenType.BAD_CHARACTER;
import static com.intellij.psi.TokenType.WHITE_SPACE;
import static com.intellij.ibeetl.generated.psi.BeetlTypes.*;

%%

%{
  public _BeetlLexer() {
    this((java.io.Reader)null);
  }
%}

%public
%class _BeetlLexer
%implements FlexLexer
%function advance
%type IElementType
%unicode

EOL=\R
WHITE_SPACE=\s+

SPACE=[ \t\n\x0B\f\r]+
NUMBER=[0-9]+
STRING=('([^'\\]|\\.)*'|\"([^\"\\]|\\\"|\\'|\\)*\")
ID=[a-zA-Z_0-9]+
LINE_COMMENT="//".*
BLOCK_COMMENT="/"\*(.|\n)*\*"/"

%%
<YYINITIAL> {
  {WHITE_SPACE}        { return WHITE_SPACE; }

  "="                  { return BTL_EQ; }
  "+"                  { return BTL_PLUS; }
  "<%"                 { return BTL_LDT; }
  "%>"                 { return BTL_RDT; }

  {SPACE}              { return BTL_SPACE; }
  {NUMBER}             { return BTL_NUMBER; }
  {STRING}             { return BTL_STRING; }
  {ID}                 { return BTL_ID; }
  {LINE_COMMENT}       { return BTL_LINE_COMMENT; }
  {BLOCK_COMMENT}      { return BTL_BLOCK_COMMENT; }

}

[^] { return BAD_CHARACTER; }

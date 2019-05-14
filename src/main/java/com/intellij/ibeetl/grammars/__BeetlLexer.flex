package com.intellij.ibeetl.grammars;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;

import static com.intellij.psi.TokenType.BAD_CHARACTER;
import static com.intellij.psi.TokenType.WHITE_SPACE;
import static com.intellij.ibeetl.generated.lexer._BeetlTokenTypes.*;

%%

%{
  public __BeetlLexer() {
    this((java.io.Reader)null);
  }
%}

%public
%class __BeetlLexer
%implements FlexLexer
%function advance
%type IElementType
%unicode

EOL=\R
WHITE_SPACE=\s+

HTML_TAG_START=<#|<"/"#
HTML_TAG_END=>|"/">

%%
<YYINITIAL> {
  {WHITE_SPACE}         { return WHITE_SPACE; }

  "<%"                  { return BTLDELIMITER; }
  "%>"                  { return BTRDELIMITER; }
  "."                   { return BTDOT; }
  "${"                  { return BTLPLACEHOLDER; }
  "}"                   { return BTRPLACEHOLDER; }
  "{"                   { return BTLBRACE; }
  "}"                   { return BTRBRACE; }
  "["                   { return BTLBRACK; }
  "]"                   { return BTRBRACK; }
  "("                   { return BTLPAREN; }
  ")"                   { return BTRPAREN; }
  ":"                   { return BTCOLON; }
  ";"                   { return BTSEMICOLON; }
  ","                   { return BTCOMMA; }
  "=="                  { return BTEQ; }
  "="                   { return BTASSIGN; }
  "!="                  { return BTNOT_EQ; }
  "!"                   { return BTNOT; }
  "|"                   { return BTBIT_OR; }
  "++"                  { return BTINCREASE; }
  "+="                  { return BTPLUS_ASSIGN; }
  "+"                   { return BTPLUS; }
  "--"                  { return BTDECREASE; }
  "-="                  { return BTMINUS_ASSIGN; }
  "-"                   { return BTMINUS; }
  "||"                  { return BTCOND_OR; }
  "|="                  { return BTBIT_OR_ASSIGN; }
  "&&"                  { return BTCOND_AND; }
  "&="                  { return BTBIT_AND_ASSIGN; }
  "&"                   { return BTBIT_AND; }
  "<<"                  { return BTSHIFT_LEFT; }
  "<="                  { return BTLESS_OR_EQUAL; }
  "<"                   { return BTLESS; }
  "^="                  { return BTBIT_XOR_ASSIGN; }
  "^"                   { return BTBIT_XOR; }
  "*="                  { return BTMUL_ASSIGN; }
  "*"                   { return BTMUL; }
  "/="                  { return BTQUOTIENT_ASSIGN; }
  "/"                   { return BTQUOTIENT; }
  "%="                  { return BTREMAINDER_ASSIGN; }
  "%"                   { return BTREMAINDER; }
  ">>"                  { return BTSHIFT_RIGHT; }
  ">="                  { return BTGREATER_OR_EQUAL; }
  ">"                   { return BTGREATER; }
  "break"               { return BTBREAK; }
  "return"              { return BTRETURN; }
  "continue"            { return BTCONTINUE; }
  "default"             { return BTDEFAULT; }
  "interface"           { return BTINTERFACE; }
  "switch"              { return BTSWITCH; }
  "select"              { return BTSELECT; }
  "case"                { return BTCASE; }
  "const"               { return BTCONST; }
  "if"                  { return BTIF; }
  "for"                 { return BTFOR; }
  "elsefor"             { return BTELSE_FOR; }
  "else"                { return BTELSE; }
  "while"               { return BTWHILE; }
  "DIRECTIVE"           { return BTDIRECTIVE; }
  "@type"               { return BTTYPE_; }
  "var"                 { return BTVAR; }
  "try"                 { return BTTRY; }
  "catch"               { return BTCATCH; }
  "#ajax"               { return BTAJAX; }
  "#fragment"           { return BTFRAGMENT; }
  ".~"                  { return BTVIRTUAL; }
  "?"                   { return BTQUESTOIN; }
  "@"                   { return BTAT; }
  "null"                { return BTNULL; }
  "true"                { return BTTRUE; }
  "false"               { return BTFALSE; }
  "in"                  { return BTFOR_IN; }

  {HTML_TAG_START}      { return BTHTML_TAG_START; }
  {HTML_TAG_END}        { return BTHTML_TAG_END; }

}

[^] { return BAD_CHARACTER; }

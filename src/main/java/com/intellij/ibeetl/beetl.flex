package com.intellij.ibeetl.lexer;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;
import com.intellij.ibeetl.GoTypes;
import static com.intellij.psi.TokenType.BAD_CHARACTER;
import static com.intellij.ibeetl.GoParserDefinition.*;

%%

%{
  public _BeetlLexer() {
    this((java.io.Reader)null);
 }
%}

%class _BeetlLexer
%implements FlexLexer
%unicode
%public

%function advance
%type IElementType

NL = \R
WS = [\ \t\f]

LINE_COMMENT = "//" [^\r\n]*
MULTILINE_COMMENT = "/*" ( ([^"*"]|[\r\n])* ("*"+ [^"*""/"] )? )* ("*" | "*"+"/")?
//Java首字母允许的符号
LETTER = [:jletter:]
DIGIT =  [0-9]

HEX_DIGIT = [0-9A-Fa-f]
OCT_DIGIT = [0-7]

/*是否高精度数值*/
NUM_TYPE_SUFFIX = [hH]
//十进制
NUM_INT = "0" | ([1-9] {DIGIT}*) {NUM_TYPE_SUFFIX}?
//十六进制
NUM_HEX = ("0x" | "0X") {HEX_DIGIT}+ {NUM_TYPE_SUFFIX}?
//八进制
NUM_OCT = "0" {OCT_DIGIT}+  {NUM_TYPE_SUFFIX}?

FLOAT_EXPONENT = [eE] [+-]? {DIGIT}+

NUM_FLOAT = (
				({DIGIT}+ "." {DIGIT}*)
				{FLOAT_EXPONENT}?
				{NUM_TYPE_SUFFIX}?
			)
			|
			(
				"." {DIGIT}+ {FLOAT_EXPONENT}? {NUM_TYPE_SUFFIX}?
			)
			|
			(
				{DIGIT}+ {FLOAT_EXPONENT} {NUM_TYPE_SUFFIX}?
			)
			|
			(
				{DIGIT}+ {NUM_TYPE_SUFFIX}
			)
			|
			(
				("0x" | "0X") {HEX_DIGIT}*
				("." {HEX_DIGIT}*)?
				("p" | "P")
				("+" | "-")?
				{DIGIT}+
				{NUM_TYPE_SUFFIX}?
			)
/*
[:jletterdigit:]
Java允许的非首字母的字符
*/
IDENTIFIER = [:jletter:] [:jletterdigit:]*
/*双引号和单引号*/
QUOTE_STR = [\u0022\u0027]
/*Unicode 转义 序列*/
UNICODE_ESCAPE = "\\" {HEX_DIGIT} {HEX_DIGIT} {HEX_DIGIT} {HEX_DIGIT}
/*八进制转义序列*/
OCTAL_ESCAPE = "\\" [0-3]? [0-7]? [0-7]
/*转义序列*/
ESCAPE_SEQUENCE = "\\" [btnfr\"\'\\] | {UNICODE_ESCAPE} | {OCTAL_ESCAPE}
STRING = {QUOTE_STR} ( {ESCAPE_SEQUENCE} | [^\"\'\\\n\r] )* {QUOTE_STR}?

%state MAYBE_SEMICOLON

%%

<YYINITIAL> {
	{WS}                                      { return WS; }
	{NL}+                                     { return NLS; }

	{LINE_COMMENT}                            { return LINE_COMMENT; }
	{MULTILINE_COMMENT}                       { return MULTILINE_COMMENT; }

	{STRING}                                  { yybegin(MAYBE_SEMICOLON); return STRING; }

	"'\\'"                                    { yybegin(MAYBE_SEMICOLON); return BAD_CHARACTER; }
	"'" [^\\] "'"?                            { yybegin(MAYBE_SEMICOLON); return CHAR; }
	"'" \n "'"?                               { yybegin(MAYBE_SEMICOLON); return CHAR; }
	"'\\"  {OCT_DIGIT} {3} "'"?               { yybegin(MAYBE_SEMICOLON); return CHAR; }
	"'\\x" {HEX_DIGIT} {2} "'"?               { yybegin(MAYBE_SEMICOLON); return CHAR; }
	"'\\u" {HEX_DIGIT} {4} "'"?               { yybegin(MAYBE_SEMICOLON); return CHAR; }
	"'\\U" {HEX_DIGIT} {8} "'"?               { yybegin(MAYBE_SEMICOLON); return CHAR; }

	"`" [^`]* "`"?                            { yybegin(MAYBE_SEMICOLON); return RAW_STRING; }
	"..."                                     { return TRIPLE_DOT; }
	"."                                       { return DOT; }
	"|"                                       { return BIT_OR; }
	"{"                                       { return LBRACE; }
	"}"                                       { yybegin(MAYBE_SEMICOLON); return RBRACE; }

	"["                                       { return LBRACK; }
	"]"                                       { yybegin(MAYBE_SEMICOLON); return RBRACK; }

	"("                                       { return LPAREN; }
	")"                                       { yybegin(MAYBE_SEMICOLON); return RPAREN; }

	":"                                       { return COLON; }
	";"                                       { return SEMICOLON; }
	","                                       { return COMMA; }

	"=="                                      { return EQ; }
	"="                                       { return ASSIGN; }

	"!="                                      { return NOT_EQ; }
	"!"                                       { return NOT; }

	"++"                                      { yybegin(MAYBE_SEMICOLON); return PLUS_PLUS; }
	"+="                                      { return PLUS_ASSIGN; }
	"+"                                       { return PLUS; }

	"--"                                      { yybegin(MAYBE_SEMICOLON); return MINUS_MINUS; }
	"-="                                      { return MINUS_ASSIGN; }
	"-"                                       { return MINUS; }

	"||"                                      { return COND_OR; }
	"|="                                      { return BIT_OR_ASSIGN; }

	"&^="                                     { return BIT_CLEAR_ASSIGN; }
	"&^"                                      { return BIT_CLEAR; }
	"&&"                                      { return COND_AND; }

	"&="                                      { return BIT_AND_ASSIGN; }
	"&"                                       { return BIT_AND; }

	"<<="                                     { return SHIFT_LEFT_ASSIGN; }
	"<<"                                      { return SHIFT_LEFT; }
	"<-"                                      { return SEND_CHANNEL; }
	"<="                                      { return LESS_OR_EQUAL; }
	"<"                                       { return LESS; }

	"^="                                      { return BIT_XOR_ASSIGN; }
	"^"                                       { return BIT_XOR; }

	"*="                                      { return MUL_ASSIGN; }
	"*"                                       { return MUL; }

	"/="                                      { return QUOTIENT_ASSIGN; }
	"/"                                       { return QUOTIENT; }

	"%="                                      { return REMAINDER_ASSIGN; }
	"%"                                       { return REMAINDER; }

	">>="                                     { return SHIFT_RIGHT_ASSIGN; }
	">>"                                      { return SHIFT_RIGHT; }
	">="                                      { return GREATER_OR_EQUAL; }
	">"                                       { return GREATER; }

	":="                                      { return VAR_ASSIGN; }

	"break"                                   { yybegin(MAYBE_SEMICOLON); return BREAK; }
	"return"                                  { yybegin(MAYBE_SEMICOLON); return RETURN ; }
	"continue"                                { yybegin(MAYBE_SEMICOLON); return CONTINUE ; }

	"default"                                 { return DEFAULT; }
	"interface"                               { return INTERFACE; }

	"case"                                    { return CASE; }

	"else"                                    { return ELSE; }
	"switch"                                  { return SWITCH; }
	"select"                                  { return SELECT; }
	"const"                                   { return CONST; }

	"if"                                      { return IF ; }
	"for"                                     { return FOR ; }
	"elsefor"                                 { return ELSE_FOR ; }
	"while"                                   { return WHILE ; }

	"DIRECTIVE"                               { return DIRECTIVE; }
	"directive"                               { return DIRECTIVE; }
	"type"                                    { return TYPE_; }
	"var"                                     { return VAR; }

	"try"                                     { return TRY; }
	"catch"                                   { return CATCH; }
	"#ajax"                                   { return AJAX; }
	"#fragment"                               { return FRAGMENT; }

	".~"                                      { return VIRTUAL; }
	"?"                                       { return QUESTOIN; }
	"@"                                       { return AT; }
	"null"                                    { return NULL; }
	"true"                                    { return TRUE; }
	"false"                                   { return FALSE; }
	"in"                                      { return FOR_IN; }

	{IDENTIFIER}                              { yybegin(MAYBE_SEMICOLON); return IDENTIFIER; }

	{NUM_FLOAT}                               { yybegin(MAYBE_SEMICOLON); return FLOAT; }
	{NUM_OCT}                                 { yybegin(MAYBE_SEMICOLON); return OCT; }
	{NUM_HEX}                                 { yybegin(MAYBE_SEMICOLON); return HEX; }
	{NUM_INT}                                 { yybegin(MAYBE_SEMICOLON); return INT; }

	.                                         { return BAD_CHARACTER; }
}

<MAYBE_SEMICOLON> {
	{WS}                                      { return WS; }
	{NL}                                      { yybegin(YYINITIAL); yypushback(yytext().length()); return SEMICOLON_SYNTHETIC; }
	{LINE_COMMENT}                            { return LINE_COMMENT; }
	{MULTILINE_COMMENT}                       { return MULTILINE_COMMENT; }
	.                                         { yybegin(YYINITIAL); yypushback(yytext().length()); }
}

package com.intellij.ibeetl.lexer;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.TokenType;
import com.intellij.psi.tree.IElementType;
import com.intellij.ibeetl.BtlTypes;
import static com.intellij.psi.TokenType.BAD_CHARACTER;
import static com.intellij.ibeetl.BtlParserDefinition.*;

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
%line
%column

%function advance
%type IElementType

NL = \R
WS = [\ \r\t\f]

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
/*双引号和单引号，暂时没用*/
QUOTE_STR = [\u0022\u0027]
/*Unicode 转义 序列*/
UNICODE_ESCAPE = "\\" {HEX_DIGIT} {HEX_DIGIT} {HEX_DIGIT} {HEX_DIGIT}
/*八进制转义序列*/
OCTAL_ESCAPE = "\\" [0-3]? [0-7]? [0-7]
/*转义序列*/
ESCAPE_SEQUENCE = "\\" [btnfr\"\'\\] | {UNICODE_ESCAPE} | {OCTAL_ESCAPE}
STRING = ("\"" ( {ESCAPE_SEQUENCE} | [^\"\'\\\n\r] )* "\"")
		| ("'" ( {ESCAPE_SEQUENCE} | [^\"\'\\\n\r] )* "'")

%state MAYBE_SEMICOLON

%%
/*
yybegin()方法是切换分析器状态
目前有两个状态：YYINITIAL、MAYBE_SEMICOLON
MAYBE_SEMICOLON：表明当前匹配的字符之后可能具有的词法状态
*/
<YYINITIAL> {
	{WS}                                      { return BtlParserDefinition.WS; }
	{NL}+                                     { return BtlParserDefinition.NLS; }

	{LINE_COMMENT}                            { return BtlParserDefinition.LINE_COMMENT; }
	{MULTILINE_COMMENT}                       { return BtlParserDefinition.MULTILINE_COMMENT; }

	{STRING}                                  { return BtlTypes.STRING; }

	"."                                       { return BtlTypes.DOT; }
	"{"                                       { return BtlTypes.LBRACE; }
	"}"                                       { yybegin(MAYBE_SEMICOLON); return BtlTypes.RBRACE; }

	"["                                       { return BtlTypes.LBRACK; }
	"]"                                       { yybegin(MAYBE_SEMICOLON); return BtlTypes.RBRACK; }

	"("                                       { return BtlTypes.LPAREN; }
	")"                                       { yybegin(MAYBE_SEMICOLON); return BtlTypes.RPAREN; }
	/*定界符*/
	"<!--#"                                   { return BtlTypes.LDELIMITER; }
	"-->"                                     { yybegin(MAYBE_SEMICOLON); return BtlTypes.RDELIMITER; }
	"layui:"                                  { return BtlTypes.HTMLTAG; }

	":"                                       { return BtlTypes.COLON; }
	";"                                       { return BtlTypes.SEMICOLON; }
	","                                       { return BtlTypes.COMMA; }

	"=="                                      { return BtlTypes.EQ; }
	"="                                       { return BtlTypes.ASSIGN; }

	"!="                                      { return BtlTypes.NOT_EQ; }
	"!"                                       { return BtlTypes.NOT; }
	"|"                                       { return BtlTypes.BIT_OR; }

	"++"                                      { return BtlTypes.INCREASE; }
	"+="                                      { return BtlTypes.PLUS_ASSIGN; }
	"+"                                       { return BtlTypes.PLUS; }

	"--"                                      { return BtlTypes.DECREASE; }
	"-="                                      { return BtlTypes.MINUS_ASSIGN; }
	"-"                                       { return BtlTypes.MINUS; }

	"||"                                      { return BtlTypes.COND_OR; }
	"|="                                      { return BtlTypes.BIT_OR_ASSIGN; }

	"&&"                                      { return BtlTypes.COND_AND; }
	"&="                                      { return BtlTypes.BIT_AND_ASSIGN; }
	"&"                                       { return BtlTypes.BIT_AND; }

	"<<"                                      { return BtlTypes.SHIFT_LEFT; }
	"<="                                      { return BtlTypes.LESS_OR_EQUAL; }
	"<"                                       { return BtlTypes.LESS; }

	"^="                                      { return BtlTypes.BIT_XOR_ASSIGN; }
	"^"                                       { return BtlTypes.BIT_XOR; }

	"*="                                      { return BtlTypes.MUL_ASSIGN; }
	"*"                                       { return BtlTypes.MUL; }

	"/="                                      { return BtlTypes.QUOTIENT_ASSIGN; }
	"/"                                       { return BtlTypes.QUOTIENT; }

	"%="                                      { return BtlTypes.REMAINDER_ASSIGN; }
	"%"                                       { return BtlTypes.REMAINDER; }

	">>"                                      { return BtlTypes.SHIFT_RIGHT; }
	">="                                      { return BtlTypes.GREATER_OR_EQUAL; }
	">"                                       { return BtlTypes.GREATER; }

	"break"                                   { yybegin(MAYBE_SEMICOLON); return BtlTypes.BREAK; }
	"return"                                  { yybegin(MAYBE_SEMICOLON); return BtlTypes.RETURN ; }
	"continue"                                { yybegin(MAYBE_SEMICOLON); return BtlTypes.CONTINUE ; }

	"default"                                 { return BtlTypes.DEFAULT; }
	"interface"                               { return BtlTypes.INTERFACE; }

	"switch"                                  { return BtlTypes.SWITCH; }
	"select"                                  { return BtlTypes.SELECT; }
	"case"                                    { return BtlTypes.CASE; }
	"const"                                   { return BtlTypes.CONST; }

	"if"                                      { return BtlTypes.IF; }
	"for"                                     { return BtlTypes.FOR; }
	"elsefor"                                 { return BtlTypes.ELSE_FOR; }
	"else"                                    { return BtlTypes.ELSE; }
	"while"                                   { return BtlTypes.WHILE; }

	"DIRECTIVE" | "directive"                 { return BtlTypes.DIRECTIVE; }
	"type"                                    { return BtlTypes.TYPE_; }
	"var"                                     { return BtlTypes.VAR; }

	"try"                                     { return BtlTypes.TRY; }
	"catch"                                   { return BtlTypes.CATCH; }
	"#ajax"                                   { return BtlTypes.AJAX; }
	"#fragment"                               { return BtlTypes.FRAGMENT; }

	".~"                                      { return BtlTypes.VIRTUAL; }
	"?"                                       { return BtlTypes.QUESTOIN; }
	"@"                                       { return BtlTypes.AT; }
	"null"                                    { return BtlTypes.NULL; }
	"true"                                    { return BtlTypes.TRUE; }
	"false"                                   { return BtlTypes.FALSE; }
	"in"                                      { return BtlTypes.FOR_IN; }

	{IDENTIFIER}                              { yybegin(MAYBE_SEMICOLON); return BtlTypes.IDENTIFIER; }

	{NUM_FLOAT}                               { yybegin(MAYBE_SEMICOLON); return BtlTypes.FLOAT; }
	{NUM_OCT}                                 { yybegin(MAYBE_SEMICOLON); return BtlTypes.OCT; }
	{NUM_HEX}                                 { yybegin(MAYBE_SEMICOLON); return BtlTypes.HEX; }
	{NUM_INT}                                 { yybegin(MAYBE_SEMICOLON); return BtlTypes.INT; }

	.                                         { return TokenType.BAD_CHARACTER; }
}

<MAYBE_SEMICOLON> {
	{WS}                                      { return BtlParserDefinition.WS; }
	{NL}                                      { yybegin(YYINITIAL); yypushback(yytext().length()); return SEMICOLON_SYNTHETIC; }

	{LINE_COMMENT}                            { return BtlParserDefinition.LINE_COMMENT; }
	{MULTILINE_COMMENT}                       { return BtlParserDefinition.MULTILINE_COMMENT; }
	.                                         { yybegin(YYINITIAL); yypushback(yytext().length()); }
}

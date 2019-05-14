package com.intellij.ibeetl.generated.lexer;

import com.intellij.ibeetl.lang.BeetlParserDefinition;
import com.intellij.ibeetl.lang.lexer.BeetlTokenTypes;
import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;
import org.apache.commons.lang3.StringUtils;

import static com.intellij.ibeetl.lang.lexer.BeetlIElementTypes.*;
import static com.intellij.psi.TokenType.*;

%%

%{
	public _BeetlLexer() {
			this((java.io.Reader)null);
		}
	private IElementType lexerTemplateData(){
		int i = StringUtils.indexOf(zzBuffer, "<%", zzCurrentPos);
		if(-1!=i){
			zzMarkedPos=i;
		}else {
			int j = StringUtils.indexOf(zzBuffer, "<#", zzCurrentPos);
			if(-1!=j){
				zzMarkedPos=j;
			}else {
				int k = StringUtils.indexOf(zzBuffer, "</#", zzCurrentPos);
				if(-1!=k){
					zzMarkedPos=k;
				}else {
					zzMarkedPos=zzEndRead;
				}
			}
		}
		System.out.println("----------------lexerTemplateData start");
		System.out.println("zzBuffer : "+this.zzBuffer);
		System.out.println("content:"+yytext());
		System.out.println("content length:"+yylength());

		System.out.println("zzState : "+this.zzState);
		System.out.println("lexer state: "+yystate());
		System.out.println("zzLexicalState : "+this.zzLexicalState);

		System.out.println("zzCurrentPos : "+this.zzCurrentPos);
		System.out.println("zzCurrentPos char : "+this.yycharat(this.zzCurrentPos));
		System.out.println("zzMarkedPos : "+this.zzMarkedPos);
		System.out.println("zzMarkedPos char: "+this.yycharat(this.zzMarkedPos));

		System.out.println("zzStartRead : "+this.zzStartRead);
		System.out.println("zzStartRead char: "+this.yycharat(this.zzStartRead));
		System.out.println("zzEndRead : "+this.zzEndRead);
		System.out.println("zzEndRead char: "+this.yycharat(this.zzEndRead));
		//  	System.out.println(yyline);
		//  	System.out.println(yychar);
		//  	System.out.println(yycolumn);
		System.out.println("----------------lexerTemplateData end");
		return TEMPLATE_HTML_TEXT;
	}
%}

%debug

%public
%class _BeetlLexer
%implements FlexLexer
%function advance
%type IElementType
%unicode
%eof{  return;
%eof}

NEW_LINE = (\r\n | \R | \r})+
WHITE_SPACE = \s+

LINE_COMMENT = "//" [^\r\n]*
MULTILINE_COMMENT = "/*" ( ([^"*"]|[\r\n])* ("*"+ [^"*""/"] )? )* ("*" | "*"+"/")?
//Java首字母允许的符号
LETTER = [:jletter:]
DIGIT =  [0-9]

HEX_DIGIT = [0-9A-Fa-f]
OCT_DIGIT = [0-7]

/*高精度数值表示后缀*/
BIGDECIMAL_SUFFIX = [hH]
//十进制
NUM_INT = "0" | ([1-9] {DIGIT}*) {BIGDECIMAL_SUFFIX}?
//十六进制
NUM_HEX = ("0x" | "0X") {HEX_DIGIT}+ {BIGDECIMAL_SUFFIX}?
//八进制
NUM_OCT = "0" {OCT_DIGIT}+  {BIGDECIMAL_SUFFIX}?
//浮点数表示后缀
FLOAT_EXPONENT = [eE] [+-]? {DIGIT}+

NUM_FLOAT = (
				({DIGIT}+ "." {DIGIT}*)
				{FLOAT_EXPONENT}?
				{BIGDECIMAL_SUFFIX}?
			)
			|
			(
				"." {DIGIT}+ {FLOAT_EXPONENT}? {BIGDECIMAL_SUFFIX}?
			)
			|
			(
				{DIGIT}+ {FLOAT_EXPONENT} {BIGDECIMAL_SUFFIX}?
			)
			|
			(
				{DIGIT}+ {BIGDECIMAL_SUFFIX}
			)
			|
			(
				("0x" | "0X") {HEX_DIGIT}*
				("." {HEX_DIGIT}*)?
				("p" | "P")
				("+" | "-")?
				{DIGIT}+
				{BIGDECIMAL_SUFFIX}?
			)
/*
[:jletterdigit:]
Java允许的首字母非数字标识符
*/
IDENTIFIER = [:jletter:] [:jletterdigit:]*
ATTRIBUTE_NAME = [\w\-\:]+
ATTRIBUTE_VALUE = ("\"" ([^\\\n\r] )* "\"") | ("'" ([^\\\n\r] )* "'")

/*双引号和单引号，暂时没用*/
QUOTE_STR = [\u0022\u0027]

/*Unicode 转义序列*/
UNICODE_ESCAPE = "\\" {HEX_DIGIT} {HEX_DIGIT} {HEX_DIGIT} {HEX_DIGIT}
/*八进制转义序列*/
OCTAL_ESCAPE = "\\" [0-3]? [0-7]? [0-7]
/*转义序列*/
ESCAPE_SEQUENCE = "\\" [btnfr\"\'\\] | {UNICODE_ESCAPE} | {OCTAL_ESCAPE}

STRING = ("\"" ( {ESCAPE_SEQUENCE} | [^\"\'\\\n\r] )* "\"")
		| ("'" ( {ESCAPE_SEQUENCE} | [^\"\'\\\n\r] )* "'")

%state BTL_LEX
%state BTL_HTML_LEX
%state BTL_PLACEHOLDER
%%
/*
yybegin()方法是切换分析器词法状态
目前有两个状态：YYINITIAL、MAYBE_SEMICOLON
MAYBE_SEMICOLON：表明当前匹配的字符之后可能具有的词法状态
*/
<YYINITIAL> {
	{WHITE_SPACE}                             { return BeetlParserDefinition.WHITE_SPACES; }
	{NEW_LINE}                                { return BeetlParserDefinition.NEW_LINES; }

	{LINE_COMMENT}                            { return BeetlParserDefinition.LINE_COMMENT; }
	{MULTILINE_COMMENT}                       { return BeetlParserDefinition.MULTILINE_COMMENT; }
	/*定界符*/
    "${"                                      { yybegin(BTL_PLACEHOLDER); return BeetlTokenTypes.BT_LPLACEHOLDER; }
	"<%"                                      { yybegin(BTL_LEX); return BeetlTokenTypes.BT_LDELIMITER; }
	"<#" | "</#"                              { yybegin(BTL_HTML_LEX); return BeetlTokenTypes.BT_HTML_TAG_START; }
    .                                         { return lexerTemplateData(); }
}

<BTL_LEX>{
	{WHITE_SPACE}                             { return BeetlParserDefinition.WHITE_SPACES; }
	{NEW_LINE}                                { return BeetlParserDefinition.NEW_LINES; }

	{LINE_COMMENT}                            { return BeetlParserDefinition.LINE_COMMENT; }
	{MULTILINE_COMMENT}                       { return BeetlParserDefinition.MULTILINE_COMMENT; }

	"."                                       { return BeetlTokenTypes.BT_DOT; }

    "${"                                      { yybegin(BTL_PLACEHOLDER); return BeetlTokenTypes.BT_LPLACEHOLDER; }

	"{"                                       { return BeetlTokenTypes.BT_LBRACE; }
	"}"                                       { return BeetlTokenTypes.BT_RBRACE; }

	"["                                       { return BeetlTokenTypes.BT_LBRACK; }
	"]"                                       { return BeetlTokenTypes.BT_RBRACK; }

	"("                                       { return BeetlTokenTypes.BT_LPAREN; }
	")"                                       { return BeetlTokenTypes.BT_RPAREN; }

	":"                                       { return BeetlTokenTypes.BT_COLON; }
	";"                                       { return BeetlTokenTypes.BT_SEMICOLON; }
	","                                       { return BeetlTokenTypes.BT_COMMA; }

	"=="                                      { return BeetlTokenTypes.BT_EQ; }
	"="                                       { return BeetlTokenTypes.BT_ASSIGN; }

	"!="                                      { return BeetlTokenTypes.BT_NOT_EQ; }
	"!"                                       { return BeetlTokenTypes.BT_NOT; }
	"|"                                       { return BeetlTokenTypes.BT_BIT_OR; }

	"++"                                      { return BeetlTokenTypes.BT_INCREASE; }
	"+="                                      { return BeetlTokenTypes.BT_PLUS_ASSIGN; }
	"+"                                       { return BeetlTokenTypes.BT_PLUS; }

	"--"                                      { return BeetlTokenTypes.BT_DECREASE; }
	"-="                                      { return BeetlTokenTypes.BT_MINUS_ASSIGN; }
	"-"                                       { return BeetlTokenTypes.BT_MINUS; }

	"||"                                      { return BeetlTokenTypes.BT_COND_OR; }
	"|="                                      { return BeetlTokenTypes.BT_BIT_OR_ASSIGN; }

	"&&"                                      { return BeetlTokenTypes.BT_COND_AND; }
	"&="                                      { return BeetlTokenTypes.BT_BIT_AND_ASSIGN; }
	"&"                                       { return BeetlTokenTypes.BT_BIT_AND; }

	"<<"                                      { return BeetlTokenTypes.BT_SHIFT_LEFT; }
	"<="                                      { return BeetlTokenTypes.BT_LESS_OR_EQUAL; }
	"<"                                       { return BeetlTokenTypes.BT_LESS; }

	"^="                                      { return BeetlTokenTypes.BT_BIT_XOR_ASSIGN; }
	"^"                                       { return BeetlTokenTypes.BT_BIT_XOR; }

	"*="                                      { return BeetlTokenTypes.BT_MUL_ASSIGN; }
	"*"                                       { return BeetlTokenTypes.BT_MUL; }

	"/="                                      { return BeetlTokenTypes.BT_QUOTIENT_ASSIGN; }
	"/"                                       { return BeetlTokenTypes.BT_QUOTIENT; }
	/*因为是%开头，所以放在下面的求余符号前*/
	"%>"                                      { yybegin(YYINITIAL); return BeetlTokenTypes.BT_RDELIMITER; }

	"%="                                      { return BeetlTokenTypes.BT_REMAINDER_ASSIGN; }
	"%"                                       { return BeetlTokenTypes.BT_REMAINDER; }

	">>"                                      { return BeetlTokenTypes.BT_SHIFT_RIGHT; }
	">="                                      { return BeetlTokenTypes.BT_GREATER_OR_EQUAL; }
	">"                                       { return BeetlTokenTypes.BT_GREATER; }

	"break"                                   { return BeetlTokenTypes.BT_BREAK; }
	"return"                                  { return BeetlTokenTypes.BT_RETURN ; }
	"continue"                                { return BeetlTokenTypes.BT_CONTINUE ; }

	"default"                                 { return BeetlTokenTypes.BT_DEFAULT; }
	"interface"                               { return BeetlTokenTypes.BT_INTERFACE; }

	"switch"                                  { return BeetlTokenTypes.BT_SWITCH; }
	"select"                                  { return BeetlTokenTypes.BT_SELECT; }
	"case"                                    { return BeetlTokenTypes.BT_CASE; }
	"const"                                   { return BeetlTokenTypes.BT_CONST; }

	"if"                                      { return BeetlTokenTypes.BT_IF; }
	"for"                                     { return BeetlTokenTypes.BT_FOR; }
	"elsefor"                                 { return BeetlTokenTypes.BT_ELSE_FOR; }
	"else"                                    { return BeetlTokenTypes.BT_ELSE; }
	"while"                                   { return BeetlTokenTypes.BT_WHILE; }

	"DIRECTIVE" | "directive"                 { return BeetlTokenTypes.BT_DIRECTIVE; }
	"@type"                                   { return BeetlTokenTypes.BT_TYPE_; }
	"var"                                     { return BeetlTokenTypes.BT_VAR; }

	"try"                                     { return BeetlTokenTypes.BT_TRY; }
	"catch"                                   { return BeetlTokenTypes.BT_CATCH; }
	"#ajax"                                   { return BeetlTokenTypes.BT_AJAX; }
	"#fragment"                               { return BeetlTokenTypes.BT_FRAGMENT; }

	".~"                                      { return BeetlTokenTypes.BT_VIRTUAL; }
	"?"                                       { return BeetlTokenTypes.BT_QUESTOIN; }
	"@"                                       { return BeetlTokenTypes.BT_AT; }
	"null"                                    { return BeetlTokenTypes.BT_NULL; }
	"true"                                    { return BeetlTokenTypes.BT_TRUE; }
	"false"                                   { return BeetlTokenTypes.BT_FALSE; }
	"in"                                      { return BeetlTokenTypes.BT_FOR_IN; }

	{IDENTIFIER}                              { return BeetlTokenTypes.BT_IDENTIFIER; }

	{STRING}                                  { return BeetlTokenTypes.BT_STRING; }

	{NUM_FLOAT}                               { return BeetlTokenTypes.BT_FLOAT; }
	{NUM_OCT}                                 { return BeetlTokenTypes.BT_OCT; }
	{NUM_HEX}                                 { return BeetlTokenTypes.BT_HEX; }
	{NUM_INT}                                 { return BeetlTokenTypes.BT_INT; }

    .                                         { return BAD_CHARACTER; }
}
<BTL_HTML_LEX>{
	{WHITE_SPACE}                             { return BeetlParserDefinition.WHITE_SPACES; }
	{NEW_LINE}                                { return BeetlParserDefinition.NEW_LINES; }

	{LINE_COMMENT}                            { return BeetlParserDefinition.LINE_COMMENT; }
	{MULTILINE_COMMENT}                       { return BeetlParserDefinition.MULTILINE_COMMENT; }

	{IDENTIFIER}                              { return BeetlTokenTypes.BT_IDENTIFIER; }

	"${"                                      { yybegin(BTL_PLACEHOLDER); return BeetlTokenTypes.BT_LPLACEHOLDER; }

	"="                                       { return BeetlTokenTypes.BT_ASSIGN; }

	{ATTRIBUTE_NAME}                          { return BeetlTokenTypes.BT_ATTRIBUTE_NAME; }
	{ATTRIBUTE_VALUE}                         { return BeetlTokenTypes.BT_ATTRIBUTE_VALUE; }

    ">" | "/>"                                { yybegin(YYINITIAL); return BeetlTokenTypes.BT_HTML_TAG_END; }
    .                                         { return BAD_CHARACTER; }
}

<BTL_PLACEHOLDER> {
	[^\n\r\}]+                                { return BeetlTokenTypes.BT_PLACEHOLDER_VALUE; }
	"}"                                       { yybegin(YYINITIAL); return BeetlTokenTypes.BT_RPLACEHOLDER; }
    .                                         { return BAD_CHARACTER; }
}
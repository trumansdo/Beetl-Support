package com.intellij.ibeetl.generated.lexer;

import com.intellij.psi.tree.IElementType;
import com.intellij.ibeetl.generated.lexer.BtlTokenTypes;
import static com.intellij.psi.TokenType.BAD_CHARACTER;
import static com.intellij.ibeetl.BtlParserDefinition.*;

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

%line
%column
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
WHITE_SPACE = ( \s| \S | \t | \f )+

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
ATTRIBUTE_NAME = [\w-\:]+
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
	{WHITE_SPACE}                             { return BtlParserDefinition.WHITE_SPACES; }
	{NEW_LINE}                                { return BtlParserDefinition.NEW_LINES; }

	{LINE_COMMENT}                            { return BtlParserDefinition.LINE_COMMENT; }
	{MULTILINE_COMMENT}                       { return BtlParserDefinition.MULTILINE_COMMENT; }
	/*定界符*/
    "${"                                      { yybegin(BTL_PLACEHOLDER); return BtlTypes.LPLACEHOLDER; }
	"<%"                                      { yybegin(BTL_LEX); return BtlTypes.LDELIMITER; }
	"<#" | "</#"                              { yybegin(BTL_HTML_LEX); return BtlTypes.HTML_TAG_START; }
    .                                         { return lexerTemplateData(); }
}

<BTL_LEX>{
	{WHITE_SPACE}                             { return BtlParserDefinition.WHITE_SPACES; }
	{NEW_LINE}                                { return BtlParserDefinition.NEW_LINES; }

	{LINE_COMMENT}                            { return BtlParserDefinition.LINE_COMMENT; }
	{MULTILINE_COMMENT}                       { return BtlParserDefinition.MULTILINE_COMMENT; }

	"."                                       { return BtlTypes.DOT; }

    "${"                                      { yybegin(BTL_PLACEHOLDER); return BtlTypes.LPLACEHOLDER; }

	"{"                                       { return BtlTypes.LBRACE; }
	"}"                                       { return BtlTypes.RBRACE; }

	"["                                       { return BtlTypes.LBRACK; }
	"]"                                       { return BtlTypes.RBRACK; }

	"("                                       { return BtlTypes.LPAREN; }
	")"                                       { return BtlTypes.RPAREN; }

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
	/*因为是%开头，所以放在下面的求余符号前*/
	"%>"                                      { yybegin(YYINITIAL); return BtlTypes.RDELIMITER; }

	"%="                                      { return BtlTypes.REMAINDER_ASSIGN; }
	"%"                                       { return BtlTypes.REMAINDER; }

	">>"                                      { return BtlTypes.SHIFT_RIGHT; }
	">="                                      { return BtlTypes.GREATER_OR_EQUAL; }
	">"                                       { return BtlTypes.GREATER; }

	"break"                                   { return BtlTypes.BREAK; }
	"return"                                  { return BtlTypes.RETURN ; }
	"continue"                                { return BtlTypes.CONTINUE ; }

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

	{IDENTIFIER}                              { return BtlTypes.IDENTIFIER; }

	{STRING}                                  { return BtlTypes.STRING; }

	{NUM_FLOAT}                               { return BtlTypes.FLOAT; }
	{NUM_OCT}                                 { return BtlTypes.OCT; }
	{NUM_HEX}                                 { return BtlTypes.HEX; }
	{NUM_INT}                                 { return BtlTypes.INT; }

    .                                         { return BAD_CHARACTER; }
}
<BTL_HTML_LEX>{
	{WHITE_SPACE}                             { return BtlParserDefinition.WHITE_SPACES; }
	{NEW_LINE}                                { return BtlParserDefinition.NEW_LINES; }

	{LINE_COMMENT}                            { return BtlParserDefinition.LINE_COMMENT; }
	{MULTILINE_COMMENT}                       { return BtlParserDefinition.MULTILINE_COMMENT; }

	{IDENTIFIER}                              { return BtlTypes.IDENTIFIER; }

	"${"                                      { yybegin(BTL_PLACEHOLDER); return BtlTypes.LPLACEHOLDER; }

	"="                                       { return BtlTypes.ASSIGN; }

	{ATTRIBUTE_NAME}                          { return BtlTypes.ATTRIBUTE_NAME; }
	{ATTRIBUTE_VALUE}                         { return BtlTypes.ATTRIBUTE_VALUE; }

    ">" | "/>"                                { yybegin(YYINITIAL); return BtlTypes.HTML_TAG_END; }
    .                                         { return BAD_CHARACTER; }
}

<BTL_PLACEHOLDER> {
	[^\n\r\}]+                                { return BtlTypes.PLACEHOLDER_VALUE; }
	"}"                                       { yybegin(YYINITIAL); return BtlTypes.RPLACEHOLDER; }
    .                                         { return BAD_CHARACTER; }
}
package com.intellij.ibeetl.generated.lexer;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;

import static com.intellij.ibeetl.generated.psi.BeetlTypes.*;
import static com.intellij.psi.TokenType.BAD_CHARACTER;
import static com.intellij.psi.TokenType.WHITE_SPACE;

%%

%{
	public _BeetlLexer() {
		this((java.io.Reader)null);
	}
	private IElementType test(){
		int indexOf = StringUtils.indexOf(zzBuffer, "<%", zzCurrentPos);
		System.out.println(yycharat(indexOf));
		zzMarkedPos=indexOf;
		System.out.println("----------------test start");
		System.out.println("zzBuffer : "+this.zzBuffer);
		System.out.println("content:"+yytext());
		System.out.println("content length:"+yylength());

		System.out.println("zzState : "+this.zzState);
		System.out.println("lexer state: "+yystate());
		System.out.println("zzLexicalState : "+this.zzLexicalState);

		System.out.println("zzCurrentPos : "+this.zzCurrentPos);
		System.out.println("zzMarkedPos : "+this.zzMarkedPos);

		System.out.println("zzStartRead : "+this.zzStartRead);
		System.out.println("zzEndRead : "+this.zzEndRead);
		//  	System.out.println(yyline);
		//  	System.out.println(yychar);
		//  	System.out.println(yycolumn);
		System.out.println("----------------test end");
		return TEMPLATE_HTML_TEXT;
	}
%}

//%line
//%char
//%column

%public
%class _BeetlLexer
%implements FlexLexer
%function advance
%type IElementType
%unicode
%eof{  return;
%eof}
%debug

NEW_LINE = \R
WHITE_SPACE = [ \t\f]

NUMBER=[0-9]+
STRING=('([^'\\]|\\.)*'|\"([^\"\\]|\\\"|\\'|\\)*\")
ID=[a-zA-Z_0-9]+
LINE_COMMENT = "//" [^\r\n]*
MULTILINE_COMMENT = "/*" ( ([^"*"]|[\r\n])* ("*"+ [^"*""/"] )? )* ("*" | "*"+"/")?
%state BTL_LEX
%state BTL_HTML_LEX
%%
<YYINITIAL> {
    {WHITE_SPACE}+        { return WHITE_SPACE; }
    {NEW_LINE}+        { return WHITE_SPACE; }
    {LINE_COMMENT}       { return BTL_LINE_COMMENT; }
    {MULTILINE_COMMENT}      { return BTL_BLOCK_COMMENT; }
	"<%"                 { yybegin(BTL_LEX); return BTL_LDT; }
	.                   { test(); }
}
<BTL_LEX> {
    {WHITE_SPACE}+        { return WHITE_SPACE; }
    {NEW_LINE}+        { return WHITE_SPACE; }
    {LINE_COMMENT}       { return BTL_LINE_COMMENT; }
    {MULTILINE_COMMENT}      { return BTL_BLOCK_COMMENT; }
	"+"                  { return BTL_PLUS; }
	{NUMBER}             { return BTL_NUMBER; }
	{ID}                 { return BTL_ID; }
	"%>"                 { yybegin(YYINITIAL); return BTL_RDT; }
}

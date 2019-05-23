package com.intellij.ibeetl.lang.lexer;

import com.intellij.ibeetl.generated.lexer._BeetlLexer;
import com.intellij.ibeetl.utils.StrUtil;
import com.intellij.lexer.FlexAdapter;
import com.intellij.lexer.Lexer;
import com.intellij.lexer.LookAheadLexer;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.TokenSet;
import jdk.nashorn.internal.ir.ReturnNode;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.util.*;

import static com.intellij.ibeetl.generated.lexer._BeetlLexer.*;
import static com.intellij.ibeetl.lang.lexer.BeetlTokenTypes.*;
import static com.intellij.psi.TokenType.BAD_CHARACTER;


/**
 * 具有前瞻性的词法解析器。
 */
public class BeetlLexer extends LookAheadLexer {
	public static final CharSequence[] SPECIAL_MARKS_UP_START = new CharSequence[]{"<%","<#","</#","${"};
	public static final CharSequence[] SPECIAL_MARKS_UP_END = new CharSequence[]{"%>",">","}"};
	public static final Map<String,Ternary> MARKS_MAP = new HashMap<>();
	public static final Ternary BAD = new Ternary(BAD_CHARACTER, 0);
	static {
		MARKS_MAP.put("<%", new Ternary(BT_LDELIMITER, BTL_LEX));
		MARKS_MAP.put("%>", new Ternary(BT_RDELIMITER, YYINITIAL));
		MARKS_MAP.put("<#", new Ternary(BT_HTML_TAG_START, BTL_HTML_LEX));
		MARKS_MAP.put("</#", new Ternary(BT_HTML_TAG_START, BTL_HTML_LEX) );
		MARKS_MAP.put(">", new Ternary(BT_HTML_TAG_END, YYINITIAL));
		MARKS_MAP.put("${", new Ternary(BT_LPLACEHOLDER, BTL_PLACEHOLDER));
		MARKS_MAP.put("}", new Ternary(BT_RPLACEHOLDER, YYINITIAL));
	}

	public BeetlLexer() {
		super(new FlexAdapter(new _BeetlLexer()));
	}
	/**
	 * 主要是在每一个token解析完成之后，被调用，可以实现在下一个token解析之前前瞻性。
	 * 本质就是改变两个东西。一个是token的结束位置，一个是tokentype的值。
	 * @param baseLexer
	 */
	@Override
	protected void lookAhead(@NotNull Lexer baseLexer) {
		if(null==baseLexer && baseLexer instanceof FlexAdapter) return;
		FlexAdapter beetlFlex = (FlexAdapter) baseLexer;
		IElementType currentToken = beetlFlex.getTokenType();
		int curIndex = baseLexer.getTokenStart();
		int end = this.getBufferEnd();
		CharSequence bufferSequence = baseLexer.getBufferSequence();
		if(null==currentToken){
			addToken(curIndex, BAD_CHARACTER);
			super.lookAhead(baseLexer);
		}else if(TEMPORARY_SET.contains(currentToken)){/*处理非beetl词法中的token，*/
			Ternary ternary = calcuMark(bufferSequence, curIndex);
			if(curIndex==ternary.index){/*相等就说明当前位置正好是定界符、占位符，HTML标签*/
				beetlFlex.start(bufferSequence, ternary.index+ternary.length, end, ternary.lexicalState);
				addToken(ternary.index+ternary.length, ternary.token);
				System.out.println(bufferSequence.subSequence(curIndex, ternary.index+ternary.length));
			}else {
				beetlFlex.start(bufferSequence, ternary.index, end, ternary.lexicalState);
				addToken(ternary.index, BeetlIElementTypes.BTL_TEMPLATE_HTML_TEXT);
				System.out.println(bufferSequence.subSequence(curIndex, ternary.index));
			}
		}else {
			Ternary ternary = calcuMark(bufferSequence, curIndex);
			if(curIndex==ternary.index){/*相等就说明当前位置正好是定界符、占位符，HTML标签*/
				beetlFlex.start(bufferSequence, ternary.index+ternary.length, end, ternary.lexicalState);
				addToken(ternary.index+ternary.length, ternary.token);
				System.out.println(bufferSequence.subSequence(curIndex, ternary.index+ternary.length));
			}else
				advanceLexer(baseLexer);
		}
	}

	private static Ternary calcuMark(final CharSequence str, final int startPos){
		Ternary ternaryStart = searchTernary(str, startPos, SPECIAL_MARKS_UP_START);
		Ternary ternaryEnd = searchTernary(str, startPos, SPECIAL_MARKS_UP_END);
		if(ternaryStart.index <= ternaryEnd.index){
			return ternaryStart;
		}
		return ternaryEnd;
	}

	private static Ternary searchTernary(final CharSequence str, final int startPos, final CharSequence... searchStrs) {
		if (str == null || searchStrs == null) {
			return BAD;
		}
		final int sz = searchStrs.length;

		// String's can't have a MAX_VALUEth index.
		int ret = Integer.MAX_VALUE;

		int tmp = 0;
		Ternary ternary = BAD;
		for (int i = 0; i < sz; i++) {
			final CharSequence search = searchStrs[i];
			if (search == null) {
				continue;
			}
			tmp = StringUtils.indexOf(str, search, startPos);
			if (tmp == -1) {
				continue;
			}

			if (tmp < ret) {
				ret = tmp;
				ternary=MARKS_MAP.get(search.toString());
				ternary.index=tmp;
				ternary.length=search.length();
			}
		}

		return ret == Integer.MAX_VALUE ? BAD : ternary;
	}

	private static class Ternary{
		int index;
		IElementType token;
		int lexicalState;
		int length;

		public Ternary(IElementType token, int lexicalState) {
			this.token = token;
			this.lexicalState = lexicalState;
		}
	}
}

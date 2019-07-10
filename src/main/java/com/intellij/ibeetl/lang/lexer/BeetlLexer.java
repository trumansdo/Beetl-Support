package com.intellij.ibeetl.lang.lexer;

import com.intellij.ibeetl.generated.lexer._BeetlLexer;
import com.intellij.lexer.FlexAdapter;
import com.intellij.lexer.Lexer;
import com.intellij.lexer.LookAheadLexer;
import com.intellij.psi.tree.IElementType;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

import static com.intellij.ibeetl.generated.lexer._BeetlLexer.*;
import static com.intellij.ibeetl.lang.lexer.BeetlTokenTypes.*;
import static com.intellij.ibeetl.setting.BeetlConfigure.*;
import static com.intellij.psi.TokenType.BAD_CHARACTER;
import static com.intellij.psi.TokenType.ERROR_ELEMENT;


/**
 * 具有前瞻性的词法解析器。
 */
public class BeetlLexer extends LookAheadLexer {
	/*定义所有的定界符，HTML标签标识符，占位符*/
	public static String DELIMITER_LEFT = DELIMITER_STATEMENT_START;
	public static String DELIMITER_RIGHT = "".equals(StringUtils.trimToEmpty(DELIMITER_STATEMENT_END)) ? "\n" : DELIMITER_STATEMENT_END;
	public static String HTML_START1 = "<" + HTML_TAG_FLAG;
	public static String HTML_START2 = "</" + HTML_TAG_FLAG;
	public static String HTML_END1 = ">";
	public static String HTML_END2 = "/>";
	public static String PLACEHOLDER_LEFT = DELIMITER_PLACEHOLDER_START;
	public static String PLACEHOLDER_RIGHT = DELIMITER_PLACEHOLDER_END;

	public static final Map<String, Ternary> MARKS_MAP = new HashMap<>();
	public final Ternary BAD = new Ternary(BAD_CHARACTER, YYINITIAL);
	public boolean isHtmlToPlace = false;
	public int ATTR_VALUE_END = Integer.MIN_VALUE;

	static {
		MARKS_MAP.put(DELIMITER_LEFT, new Ternary(BT_LDELIMITER, BTL_LEX));
		MARKS_MAP.put(DELIMITER_RIGHT, new Ternary(BT_RDELIMITER, YYINITIAL));
		MARKS_MAP.put(HTML_START1, new Ternary(BT_HTML_TAG_START, BTL_HTML_LEX));
		MARKS_MAP.put(HTML_START2, new Ternary(BT_HTML_TAG_START, BTL_HTML_LEX));
		MARKS_MAP.put(HTML_END1, new Ternary(BT_HTML_TAG_END, YYINITIAL));
		MARKS_MAP.put(HTML_END2, new Ternary(BT_HTML_TAG_END, YYINITIAL));
		MARKS_MAP.put(PLACEHOLDER_LEFT, new Ternary(BT_LPLACEHOLDER, BTL_PLACEHOLDER));
		MARKS_MAP.put(PLACEHOLDER_RIGHT, new Ternary(BT_RPLACEHOLDER, YYINITIAL));
	}
	public static void resetConfig(){
		/*定义所有的定界符，HTML标签标识符，占位符*/
		DELIMITER_LEFT = DELIMITER_STATEMENT_START;
		DELIMITER_RIGHT = "".equals(StringUtils.trimToEmpty(DELIMITER_STATEMENT_END)) ? "\n" : DELIMITER_STATEMENT_END;
		HTML_START1 = "<" + HTML_TAG_FLAG;
		HTML_START2 = "</" + HTML_TAG_FLAG;
		HTML_END1 = ">";
		HTML_END2 = "/>";
		PLACEHOLDER_LEFT = DELIMITER_PLACEHOLDER_START;
		PLACEHOLDER_RIGHT = DELIMITER_PLACEHOLDER_END;
		MARKS_MAP.clear();
		MARKS_MAP.put(DELIMITER_LEFT, new Ternary(BT_LDELIMITER, BTL_LEX));
		MARKS_MAP.put(DELIMITER_RIGHT, new Ternary(BT_RDELIMITER, YYINITIAL));
		MARKS_MAP.put(HTML_START1, new Ternary(BT_HTML_TAG_START, BTL_HTML_LEX));
		MARKS_MAP.put(HTML_START2, new Ternary(BT_HTML_TAG_START, BTL_HTML_LEX));
		MARKS_MAP.put(HTML_END1, new Ternary(BT_HTML_TAG_END, YYINITIAL));
		MARKS_MAP.put(HTML_END2, new Ternary(BT_HTML_TAG_END, YYINITIAL));
		MARKS_MAP.put(PLACEHOLDER_LEFT, new Ternary(BT_LPLACEHOLDER, BTL_PLACEHOLDER));
		MARKS_MAP.put(PLACEHOLDER_RIGHT, new Ternary(BT_RPLACEHOLDER, YYINITIAL));
	}
	public BeetlLexer() {
		super(new FlexAdapter(new _BeetlLexer()));
	}

	/**
	 * 主要是在每一个token解析完成之后，被调用，可以实现在下一个token解析之前前瞻性。
	 * 本质就是改变两个东西。一个是token的结束位置，一个是tokentype的值。
	 *
	 * @param baseLexer
	 */
	@Override
	protected void lookAhead(@NotNull Lexer baseLexer) {
		if (null == baseLexer && baseLexer instanceof FlexAdapter) return;
		FlexAdapter beetlFlex = (FlexAdapter) baseLexer;
		IElementType currentToken = beetlFlex.getTokenType();
		int curIndex = baseLexer.getTokenStart();
		int end = this.getBufferEnd();
		CharSequence bufferSequence = baseLexer.getBufferSequence();
		int lexicalState = baseLexer.getState();
		if (null == currentToken) {
			super.lookAhead(baseLexer);
		} else if (currentToken == TEMPORARY && lexicalState == YYINITIAL) {/*当前token是非beetl词法状态并且是无法识别的token（既是TEMPORARY）*/
			Ternary ternary = searchTernary(bufferSequence, curIndex, new CharSequence[]{DELIMITER_LEFT, HTML_START1, HTML_START2, PLACEHOLDER_LEFT});
			if (curIndex == ternary.index) {/*相等就说明当前位置正好是定界符、占位符，HTML标签*/
				beetlFlex.start(bufferSequence, curIndex + ternary.length, end, ternary.lexicalState);
				super.addToken(curIndex + ternary.length, ternary.token);
			} else {
				if (BAD.equals(ternary)) {/*当前位置至剩下的字符中没有beetl语法*/
					beetlFlex.start(bufferSequence, end, end, ternary.lexicalState);
					super.addToken(end, BeetlIElementTypes.BTL_TEMPLATE_HTML_TEXT);
				} else {
					beetlFlex.start(bufferSequence, ternary.index, end, ternary.lexicalState);
					super.addToken(ternary.index, BeetlIElementTypes.BTL_TEMPLATE_HTML_TEXT);
				}
			}
		} else if (lexicalState == BTL_LEX) {/*定界符词法状态*/
			/*若在定界符中写占位符，用非法token代替，致使报错。
			注：本应词法解析器不应该具有逻辑检查功能，但是由于词法解析器的设计原因，我并未将beetl的定界符这类字符识别出来，所以只能将错误检查提前，这是一种妥协的方案。*/
			if (StringUtils.startsWith(bufferSequence.subSequence(curIndex, bufferSequence.length()), PLACEHOLDER_LEFT)) {
				beetlFlex.start(bufferSequence, curIndex + PLACEHOLDER_LEFT.length(), end, lexicalState);
				super.addToken(curIndex + PLACEHOLDER_LEFT.length(), ERROR_ELEMENT);
				return;
			}
			Ternary ternary = searchTernary(bufferSequence, curIndex, new CharSequence[]{DELIMITER_LEFT, DELIMITER_RIGHT});
			if (curIndex == ternary.index) {/*相等就说明当前位置正好是定界符结束*/
				beetlFlex.start(bufferSequence, curIndex + ternary.length, end, ternary.lexicalState);
				super.addToken(curIndex + ternary.length, ternary.token);
			} else {
				super.advanceLexer(baseLexer);
			}
		} else if (lexicalState == BTL_HTML_LEX) {/*HTML标签词法状态*/
			Ternary ternary = searchTernary(bufferSequence, curIndex, new CharSequence[]{HTML_START1, HTML_START2, HTML_END2, HTML_END1, PLACEHOLDER_LEFT});
			if (curIndex == ternary.index) {/*相等就说明当前位置正好是定界符、占位符，HTML标签*/
				beetlFlex.start(bufferSequence, curIndex + ternary.length, end, ternary.lexicalState);
				super.addToken(curIndex + ternary.length, ternary.token);
			} else if (currentToken == BT_ATTRIBUTE_VALUE && ternary.token == BT_LPLACEHOLDER && ternary.index < beetlFlex.getTokenEnd()) {
				isHtmlToPlace = true;//在HTML标签中的字符串中解析到开始占位符
				ATTR_VALUE_END = baseLexer.getTokenEnd();
				beetlFlex.start(bufferSequence, ternary.index, end, ternary.lexicalState);
				super.addToken(ternary.index, BT_ATTRIBUTE_VALUE);
			} else {
				if (ATTR_VALUE_END != Integer.MIN_VALUE) {
					beetlFlex.start(bufferSequence, ATTR_VALUE_END, end, BTL_HTML_LEX);
					super.addToken(ATTR_VALUE_END, BT_ATTRIBUTE_VALUE);
					ATTR_VALUE_END = Integer.MIN_VALUE;
				} else {
					super.advanceLexer(baseLexer);
				}
			}
		} else if (lexicalState == BTL_PLACEHOLDER) {/*占位符词法状态*/
			Ternary ternary = searchTernary(bufferSequence, curIndex, new CharSequence[]{PLACEHOLDER_LEFT, PLACEHOLDER_RIGHT});
			if (curIndex == ternary.index) {/*相等就说明当前位置正好是占位符结束*/
				if (isHtmlToPlace && ternary.token == BT_RPLACEHOLDER) {
					beetlFlex.start(bufferSequence, curIndex + ternary.length, end, BTL_HTML_LEX);
					super.addToken(curIndex + ternary.length, ternary.token);
					isHtmlToPlace = false;
				} else {
					beetlFlex.start(bufferSequence, curIndex + ternary.length, end, ternary.lexicalState);
					super.addToken(curIndex + ternary.length, ternary.token);
				}
			} else {
				super.advanceLexer(baseLexer);
			}
		} else {
			super.advanceLexer(baseLexer);
		}
	}

	private Ternary searchTernary(final CharSequence str, final int startPos, final CharSequence... searchStrs) {
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
				ternary = MARKS_MAP.get(search.toString());
				ternary.index = tmp;
				ternary.length = search.length();
			}
		}

		return ret == Integer.MAX_VALUE ? BAD : ternary;
	}

	private static class Ternary {
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

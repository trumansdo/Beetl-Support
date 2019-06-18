/*

 * Copyright 2019 trumansdo

 *

 * Licensed under the Apache License, Version 2.0 (the "License");

 * you may not use this file except in compliance with the License.

 * You may obtain a copy of the License at

 *

 * http://www.apache.org/licenses/LICENSE-2.0

 *

 * Unless required by applicable law or agreed to in writing, software

 * distributed under the License is distributed on an "AS IS" BASIS,

 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.

 * See the License for the specific language governing permissions and

 * limitations under the License.

 */


package com.intellij.ibeetl.lang.parser;

import com.intellij.ibeetl.lang.lexer.BeetlTokenSets;
import com.intellij.lang.pratt.*;
import com.intellij.patterns.StandardPatterns;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.Nullable;

import java.util.ListIterator;

import static com.intellij.ibeetl.lang.lexer.BeetlIElementTypes.BTL_TEMPLATE_HTML_TEXT;
import static com.intellij.ibeetl.lang.lexer.BeetlIElementTypes.VIRTUAL_ROOT;
import static com.intellij.ibeetl.lang.lexer.BeetlTokenTypes.*;
import static com.intellij.ibeetl.lang.parser.BeetlPrattRegistry.REGISTRY;
import static com.intellij.ibeetl.lang.parser.BeetlPrattRegistry.registerParser;
import static com.intellij.ibeetl.lang.parser.BeetlPsiElementTypes.*;
import static com.intellij.lang.pratt.PathPattern.path;
import static com.intellij.lang.pratt.TokenParser.infix;
import static com.intellij.lang.pratt.TokenParser.postfix;
import static com.intellij.patterns.StandardPatterns.object;
import static com.intellij.patterns.StandardPatterns.or;

public class BeetlParser extends PrattParser {
	/* 定义整个语法解析中的等级层次。这里只是定义了一种跨度，在具体的解析等级时，需要在所属等级向上调整 */
	/**
	 * 常量等级最高，可以出现在任意语法语句中。
	 */
	public static final int CONSTANT_LEVEL = 1000;

	/**
	 * 一元表达式等级，大致包括自增、自减，对于安全表达式应该独立判断
	 */
	public static final int UNARY_LEVEL = 800;

	/**
	 * 二元表达式等级，一元和二元表达式，可以互相混写，所以初始应该一致，后由确切的token来判断
	 */
	public static final int EXPRESSION_LEVEL = 800;
	/**
	 * 语句等级，包括但不限：定义，赋值，选择、分支、循环，函数调用等
	 */
	public static final int STATEMENT_LEVEL = 60;
	/**
	 * 插值等级，也就是占位符。应该包括，正常的表达式，常量，内置函数调用。
	 */
	public static final int INTERPOLATION_LEVEL = 40;
	/**
	 * 初始化等级最低，表示接受所有的token
	 */
	public static final int INIT_LEVEL = 0;


	@Override
	protected void parse(PrattBuilder builder) {
		MutableMarker rootTag = builder.mark();
		if (!builder.isEof()) {
			super.parse(builder);
		}
		rootTag.finish(VIRTUAL_ROOT);
	}

	@Override
	protected PrattRegistry getRegistry() {
		return REGISTRY;
	}

	static {
		/*
		 * PrattBuilder常用方法解释：
		 * prattBuilder.advance(); 先将当前的token加入左兄弟元素集合中，并且向前推动token流。该方法的作用是推进token流，直接将token返回给解析器包装为ASTNode。
		 * prattBuilder.createChildBuilder(INIT_LEVEL).parse(); 以指定优先级创建一个子解析器，子解析器将只能解析高于指定优先级的token
		 * prattBuilder.checkToken(BT_RDELIMITER); 先判断当前的token是否是指定token，为真调用advance()方法。
		 *
		 * MutableMarker常用方法解释：
		 * mark.setResultType(BEETL_BLOCK); 给标记设置一个IElementType，表明这个标记中的所有的节点属于指定的IElementType。
		 *       体现在psi tree中就是，该标记作为父节点，指定的IElementType是父节点的名称；标记前后之间所有的token或者新的IElementType，都作为其子节点。
		 * mark.finish(BEETL_BLOCK); 实际上内部调用了setResultType和finish两个。finish方法将判断ResultType是否为null。如果为null，表示标记删除，不作为psi树节点。
		 *       不为null，参照setResultType方法说明。
		 *
		 * PrattParsingUtil方法说明：
		 * PrattParsingUtil.searchFor(prattBuilder, false, BT_RDELIMITER);在当前的token位置开始向前搜索，直到搜索到指定的token位置。
		 *    第二个参数是否消费搜索到的指定token，也就是搜索到指定token后，向前推进一位。
		 * */
		registerParser(BT_LDELIMITER, -10, new TokenParser() {
			@Override
			public boolean parseToken(PrattBuilder prattBuilder) {
				MutableMarker mark = prattBuilder.mark();
				prattBuilder.advance();
				prattBuilder.createChildBuilder(INIT_LEVEL).parse();
				prattBuilder.checkToken(BT_RDELIMITER);
				mark.finish(null);
				return true;//返回值只是决定是否继续当前解析器解析过程，false为终止解析。
			}
		});
		registerParser(BT_LPLACEHOLDER, -10, new TokenParser() {
			@Override
			public boolean parseToken(PrattBuilder prattBuilder) {
				MutableMarker mark = prattBuilder.mark();
				prattBuilder.advance();
//				todo 解析占位符子解析器等级要改
				prattBuilder.createChildBuilder(INIT_LEVEL).parse();
				prattBuilder.checkToken(BT_RPLACEHOLDER);
				mark.finish(INTERPOLATION);
				return true;//返回值只是决定是否继续当前解析器解析过程，false为终止解析。
			}
		});
		registerParser(BT_HTML_TAG_START, -10, new TokenParser() {
			@Override
			public boolean parseToken(PrattBuilder prattBuilder) {
				MutableMarker mark = prattBuilder.mark();
				prattBuilder.advance();
//				todo 解析HTML标签子解析器等级要改
				prattBuilder.createChildBuilder(INIT_LEVEL).parse();
				prattBuilder.checkToken(BT_HTML_TAG_END);
				mark.finish(HTML_TAG);
				return true;//返回值只是决定是否继续当前解析器解析过程，false为终止解析。
			}
		});
		registerParser(BT_IDENTIFIER, 1000, new TokenParser() {
			@Override
			public boolean parseToken(PrattBuilder prattBuilder) {
				MutableMarker marker = prattBuilder.mark();
				prattBuilder.advance();
				marker.finish(REFERENCE_EXPRESSION);
				return true;
			}
		});
		/*圆括号*/
		registerParser(BT_LPAREN, 900,path().left(REFERENCE_EXPRESSION), new TokenParser() {
			@Override
			public boolean parseToken(PrattBuilder prattBuilder) {
				MutableMarker mark = prattBuilder.mark();
				prattBuilder.advance();
				while (!prattBuilder.isToken(BT_RBRACK) && !prattBuilder.isToken(BT_RPAREN) && !prattBuilder.isToken(BT_RBRACE) && !prattBuilder.isEof()
						&& prattBuilder.createChildBuilder(890).parse()!=null){
					prattBuilder.checkToken(BT_COMMA);
				}
				prattBuilder.assertToken(BT_RPAREN);
				mark.finish(PARAMETER_LIST);
				return true;
			}
		});
		/*大括号*/
		registerParser(BT_LBRACE, 900,path().up(), new TokenParser() {
			@Override
			public boolean parseToken(PrattBuilder prattBuilder) {
				MutableMarker mark = prattBuilder.mark();
				prattBuilder.advance();
				while (!prattBuilder.isToken(BT_RBRACK) && !prattBuilder.isToken(BT_RPAREN) && !prattBuilder.isToken(BT_RBRACE) && !prattBuilder.isEof()
						&& prattBuilder.createChildBuilder(890).parse()!=null){
					prattBuilder.checkToken(BT_COMMA);
				}
				prattBuilder.checkToken(BT_RBRACE);
				mark.finish(JSON_EXPRESSION);
				return true;
			}
		});
		registerParser(BT_LBRACE, 900,path().left(PARENTHESIZED_EXPRESSION), new TokenParser() {
			@Override
			public boolean parseToken(PrattBuilder prattBuilder) {
				MutableMarker mark = prattBuilder.mark();
				PrattParsingUtil.searchFor(prattBuilder, BT_RBRACE);
				mark.finish(SYNTAX_BODY);
				return true;
			}
		});
		registerParser(BT_RBRACE, 800, postfix(SYNTAX_BODY));
		/*中括号*/
		registerParser(BT_LBRACK, 900, path().up(), new TokenParser() {
			@Override
			public boolean parseToken(PrattBuilder prattBuilder) {
				MutableMarker mark = prattBuilder.mark();
				prattBuilder.advance();
				while (!prattBuilder.isToken(BT_RBRACK) && !prattBuilder.isToken(BT_RPAREN) && !prattBuilder.isToken(BT_RBRACE) && !prattBuilder.isEof()
						&& prattBuilder.createChildBuilder(890).parse()!=null){
					prattBuilder.checkToken(BT_COMMA);
				}
				prattBuilder.assertToken(BT_RBRACK);
				mark.finish(LIST_DEFINITION);
				return true;
			}
		});
		registerParser(BT_LBRACK, 900, path().left(REFERENCE_EXPRESSION), new TokenParser() {
			@Override
			public boolean parseToken(PrattBuilder prattBuilder) {
				prattBuilder.advance();
				prattBuilder.createChildBuilder(940).parse();
				prattBuilder.assertToken(BT_RBRACK);
				prattBuilder.reduce(INDEX_EXPRESSION);
				return true;
			}
		});

		registerParser(BT_VAR, 800, new TokenParser() {
			@Override
			public boolean parseToken(PrattBuilder prattBuilder) {
				prattBuilder.advance();
				while (prattBuilder.createChildBuilder(850).parse()!=null){
					if(!prattBuilder.checkToken(BT_COMMA) || prattBuilder.checkToken(BT_SEMICOLON)){
						break;
					}
				}
				prattBuilder.reduce(VAR_DEFINITION_EXPRESSION);
				return true;
			}
		});
		registerParser(BT_ASSIGN, 870, path().left(StandardPatterns.object(REFERENCE_EXPRESSION)), infix(890, BeetlPsiElementTypes.ASSIGNMENT_EXPRESSION));
		registerParser(BT_ATTRIBUTE_NAME, 10, AppendTokenParser.JUST_APPEND);
		registerParser(BT_ATTRIBUTE_VALUE, 10, AppendTokenParser.JUST_APPEND);
		registerParser(BT_SEMICOLON, 800, new TokenParser() {
			@Override
			public boolean parseToken(PrattBuilder prattBuilder) {
				prattBuilder.advance();
				prattBuilder.createChildBuilder(0).parse();
				return true;
			}
		});

		registerParser(BT_PLUS, 900, infix(900, BeetlPsiElementTypes.BINARY_EXPRESSION));
		registerParser(BT_INCREASE, 900, AppendTokenParser.JUST_APPEND);
		/*冒号*/
		registerParser(BT_COLON, 950, path().left().up(), new TokenParser() {
			@Override
			public boolean parseToken(PrattBuilder prattBuilder) {
				prattBuilder.advance();
				prattBuilder.createChildBuilder(890).parse();
				prattBuilder.reduce(KEY_VALUE_PAIR);
				return true;
			}
		});
		/*点号*/
		registerParser(BT_DOT, 950, path().left(or(object(BT_IDENTIFIER),object(REFERENCE_EXPRESSION))), new ReducingParser() {
			@Nullable
			@Override
			public IElementType parseFurther(PrattBuilder prattBuilder) {
				prattBuilder.assertToken(BT_IDENTIFIER);
				return REFERENCE_EXPRESSION;
			}
		});

		registerParser(BT_LESS, 950, AppendTokenParser.JUST_APPEND);

		registerParser(BT_INT, 1000, new AppendTokenParser() {
			@Nullable
			@Override
			protected IElementType parseAppend(PrattBuilder prattBuilder) {
				return NUMBER;
			}
		});
		registerParser(BT_FLOAT, 1000,  new AppendTokenParser() {
			@Nullable
			@Override
			protected IElementType parseAppend(PrattBuilder prattBuilder) {
				return NUMBER;
			}
		});
		registerParser(BT_OCT, 1000,  new AppendTokenParser() {
			@Nullable
			@Override
			protected IElementType parseAppend(PrattBuilder prattBuilder) {
				return NUMBER;
			}
		});
		registerParser(BT_HEX, 1000,  new AppendTokenParser() {
			@Nullable
			@Override
			protected IElementType parseAppend(PrattBuilder prattBuilder) {
				return NUMBER;
			}
		});
		registerParser(BT_STRING, 1000,  new AppendTokenParser() {
			@Nullable
			@Override
			protected IElementType parseAppend(PrattBuilder prattBuilder) {
				return STRING_LITERAL;
			}
		});
		/**
		 * 因为有可能将换行作为定界符，所以没有在ParserDefinition中将换行加入到空白符集。因为空白符在解析时会被忽视。所以这里暂定为直接加到语法树中。
		 * 慎重：不建议将换行及控制字符作为定界符，这代表不可预料的bug。推荐将可见字符作为定界符。
		* */
		registerParser(NEW_LINE, 1000, AppendTokenParser.JUST_APPEND);
		registerParser(HTML_NEW_LINE, 1000, AppendTokenParser.JUST_APPEND);
		registerParser(BeetlTokenSets.KEYWORDS.getTypes(), 10, AppendTokenParser.JUST_APPEND);
		registerParser(BTL_TEMPLATE_HTML_TEXT, -10, AppendTokenParser.JUST_APPEND);
	}

}

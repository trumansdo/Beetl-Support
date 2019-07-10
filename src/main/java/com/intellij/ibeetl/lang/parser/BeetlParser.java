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

import com.intellij.lang.pratt.*;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.Nullable;

import static com.intellij.ibeetl.lang.lexer.BeetlIElementTypes.BTL_TEMPLATE_HTML_TEXT;
import static com.intellij.ibeetl.lang.lexer.BeetlIElementTypes.VIRTUAL_ROOT;
import static com.intellij.ibeetl.lang.lexer.BeetlTokenTypes.*;
import static com.intellij.ibeetl.lang.parser.BeetlPrattRegistry.REGISTRY;
import static com.intellij.ibeetl.lang.parser.BeetlPrattRegistry.registerParser;
import static com.intellij.ibeetl.lang.parser.BeetlPsiElementTypes.*;
import static com.intellij.lang.pratt.PathPattern.path;
import static com.intellij.lang.pratt.TokenParser.infix;
import static com.intellij.lang.pratt.TokenParser.postfix;
import static com.intellij.patterns.StandardPatterns.*;
import static com.intellij.psi.TokenType.ERROR_ELEMENT;

/**
 * 定界符不作为语法的一部分，会更好构建语法树。但是得考虑到后期的错误修正，这个难度可能会更大。
 * 在设计语法树的时候，应当从抽象的角度考虑语法。比如最常见的设计：语法块 block > 语句 > 表达式 > 常量、标识符、符号
 * 当然我大致遵循了这些。因为beetl要考虑到HTML 标签，和html函数。
 */
public class BeetlParser extends PrattParser {
	/* 定义整个语法解析中的等级层次。这里只是定义了一种跨度，在具体的解析等级时，需要在所属等级向上调整。但是......我没用到这些常量。哈哈红红火火恍恍惚惚 */
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

	static {
		/*
		 * PrattBuilder常用方法解释：
		 * prattBuilder.advance(); 先将当前的token加入左兄弟元素集合中，并且向前推动token流。该方法的作用是推进token流，直接将token返回给解析器包装为ASTNode。
		 * prattBuilder.createChildBuilder(INIT_LEVEL).parse(); 以指定优先级创建一个子解析器，子解析器将只能解析高于指定优先级的token。这个返回值若为null，说明子表达式不是一个完整的节点。
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
		registerParser(BT_LDELIMITER, 1000, AppendTokenParser.JUST_APPEND);
		registerParser(BT_RDELIMITER, 1000, AppendTokenParser.JUST_APPEND);
		/*占位符*/
		registerParser(BT_LPLACEHOLDER, 400, new TokenParser() {
			@Override
			public boolean parseToken(PrattBuilder prattBuilder) {
				MutableMarker mark = prattBuilder.mark();
				prattBuilder.advance();
				prattBuilder.createChildBuilder(400).parse();
				prattBuilder.checkToken(BT_RPLACEHOLDER);
				mark.finish(INTERPOLATION);
				return true;//返回值只是决定是否继续当前解析器解析过程，false为终止解析。
			}
		});
		/*HTML标签*/
		registerParser(BT_HTML_TAG_START, 400, new TokenParser() {
			@Override
			public boolean parseToken(PrattBuilder prattBuilder) {
				MutableMarker mark = prattBuilder.mark();
				prattBuilder.advance();
				prattBuilder.createChildBuilder(100).parse();
				prattBuilder.checkToken(BT_HTML_TAG_END);
				mark.finish(HTML_TAG);
				return true;//返回值只是决定是否继续当前解析器解析过程，false为终止解析。
			}
		});
		/*圆括号*/
		registerParser(BT_LPAREN, 900, path().up().left(or(object(BT_IDENTIFIER), object(REFERENCE_EXPRESSION))), new TokenParser() {
			@Override
			public boolean parseToken(PrattBuilder prattBuilder) {
				MutableMarker mark = prattBuilder.mark();
				prattBuilder.advance();
				while (!prattBuilder.isEof() && !prattBuilder.checkToken(BT_RPAREN) && prattBuilder.createChildBuilder(890).parse() != null) {
					if (prattBuilder.checkToken(BT_RPAREN)) {
						break;
					}
					if (!prattBuilder.checkToken(BT_COMMA)) {
						mark.finish(null);
						prattBuilder.error("Broken parentheses correctly. expect syntax : (x,x) or (x)");
						return false;
					}
				}
				mark.finish(PARAMETER_LIST);
				return true;
			}
		});
		registerParser(BT_LPAREN, 900, path().up().left(and(not(object(BT_IDENTIFIER)), not(object(REFERENCE_EXPRESSION)))), new TokenParser() {
			@Override
			public boolean parseToken(PrattBuilder prattBuilder) {
				MutableMarker mark = prattBuilder.mark();
				prattBuilder.advance();
				while (!prattBuilder.isEof() && !prattBuilder.checkToken(BT_RPAREN)) {
					IElementType elementType = prattBuilder.createChildBuilder(790).parse();
					if (prattBuilder.isToken(BT_RPAREN)) {
						break;
					}
					prattBuilder.checkToken(BT_SEMICOLON);
				}
				prattBuilder.checkToken(BT_RPAREN);
				mark.finish(PARENTHESIZED_EXPRESSION);
				return true;
			}
		});
		/*大括号，json形式的语法*/
		registerParser(BT_LBRACE, 900, path().up().left(and(not(object(BT_ELSE)), not(object(BT_ELSE_FOR)))), new TokenParser() {
			@Override
			public boolean parseToken(PrattBuilder prattBuilder) {
				MutableMarker mark = prattBuilder.mark();
				prattBuilder.advance();
				while (!prattBuilder.isEof() && !prattBuilder.checkToken(BT_RBRACE) && prattBuilder.createChildBuilder(890).parse() != null) {
					if (prattBuilder.checkToken(BT_RBRACE)) {
						break;
					}
					if (!prattBuilder.checkToken(BT_COMMA)) {
						mark.finish(null);
						prattBuilder.error("Broken json expression correctly. expect syntax : {x:y,x:y...} or {x:y}");
						return false;
					}
				}
				mark.finish(JSON_EXPRESSION);
				return true;
			}
		});
		/*冒号，一种json的k:v*/
		registerParser(BT_COLON, 950, path().left().up().left(or(object(BT_LBRACE), object(BT_COMMA))), new TokenParser() {
			@Override
			public boolean parseToken(PrattBuilder prattBuilder) {
				prattBuilder.advance();
				prattBuilder.createChildBuilder(890).parse();
				prattBuilder.reduce(KEY_VALUE_PAIR);
				return true;
			}
		});
		/*大括号，html函数或者if等语句的语法体*/
		registerParser(BT_LBRACE, 900, path().left(or(object(PARAMETER_LIST), object(PARENTHESIZED_EXPRESSION))), new TokenParser() {
			@Override
			public boolean parseToken(PrattBuilder prattBuilder) {
				MutableMarker mark = prattBuilder.mark();
				prattBuilder.advance();
				prattBuilder.createChildBuilder(9).parse();
				prattBuilder.checkToken(BT_RBRACE);
				mark.finish(SYNTAX_BODY);
				return true;
			}
		});
		registerParser(BT_LBRACE, 900, path().up().left(or(object(BT_ELSE), object(BT_ELSE_FOR))), new TokenParser() {
			@Override
			public boolean parseToken(PrattBuilder prattBuilder) {
				MutableMarker mark = prattBuilder.mark();
				prattBuilder.advance();
				prattBuilder.createChildBuilder(9).parse();
				prattBuilder.checkToken(BT_RBRACE);
				mark.finish(SYNTAX_BODY);
				return true;
			}
		});
		/*修正空的HTML函数体不报错*/
		registerParser(BT_RBRACE, 900, path().up().left(BT_LBRACE), new AppendTokenParser() {
			@Nullable
			@Override
			protected IElementType parseAppend(PrattBuilder prattBuilder) {
				prattBuilder.withLowestPriority(Integer.MAX_VALUE);
				return null;
			}
		});
		/*中括号：集合定义*/
		registerParser(BT_LBRACK, 900, path().up(), new TokenParser() {
			@Override
			public boolean parseToken(PrattBuilder prattBuilder) {
				MutableMarker mark = prattBuilder.mark();
				prattBuilder.advance();
				while (!prattBuilder.isEof() && !prattBuilder.isToken(BT_RBRACK) && prattBuilder.createChildBuilder(890).parse() != null) {
					prattBuilder.checkToken(BT_COMMA);
				}
				prattBuilder.assertToken(BT_RBRACK);
				mark.finish(LIST_DEFINITION);
				return true;
			}
		});
		/*中括号：集合索引语法*/
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

		/*变量定义语法*/
		registerParser(BT_VAR, 800, new TokenParser() {
			@Override
			public boolean parseToken(PrattBuilder prattBuilder) {
				MutableMarker mark = prattBuilder.mark();
				prattBuilder.advance();
				while (!prattBuilder.isEof() && !prattBuilder.isToken(BT_SEMICOLON) && prattBuilder.createChildBuilder(850).parse() != null) {
					if (!prattBuilder.checkToken(BT_COMMA) && !prattBuilder.isToken(BT_SEMICOLON)) {
						mark.finish(null);
						return false;
					}
				}
				mark.finish(VAR_DEFINITION_EXPRESSION);
				return true;
			}
		});
		/*赋值语法*/
		registerParser(new IElementType[]{BT_ASSIGN, BT_PLUS_ASSIGN, BT_MINUS_ASSIGN, BT_MUL_ASSIGN, BT_QUOTIENT_ASSIGN}, 870, path().left(or(object(REFERENCE_EXPRESSION), object(BT_IDENTIFIER))), new TokenParser() {
			@Override
			public boolean parseToken(PrattBuilder prattBuilder) {
				prattBuilder.advance();
				prattBuilder.createChildBuilder(890).parse();
				prattBuilder.reduce(ASSIGNMENT_EXPRESSION);
				return true;
			}
		});
		/*html标签中的name  value对语法*/
		registerParser(BT_ATTRIBUTE_NAME, 1000, AppendTokenParser.JUST_APPEND);
		registerParser(BT_ASSIGN, 870, path().left(BT_ATTRIBUTE_NAME), new TokenParser() {
			@Override
			public boolean parseToken(PrattBuilder prattBuilder) {
				MutableMarker mark = prattBuilder.mark();
				prattBuilder.advance();
				prattBuilder.createChildBuilder(100).parse();
				mark.finish(NAME_VALUE_PAIR);
				return true;
			}
		});

		registerParser(BT_ATTRIBUTE_VALUE, 1000, AppendTokenParser.JUST_APPEND);

		registerParser(BT_SEMICOLON, 110, new TokenParser() {
			@Override
			public boolean parseToken(PrattBuilder prattBuilder) {
				prattBuilder.advance();
				prattBuilder.createChildBuilder(10).parse();
				return true;
			}
		});
		/*自增自减语法*/
//		registerParser(new IElementType[]{BT_INCREASE, BT_DECREASE}, 950, path().up(), infix(950, SELF_OVERLAY_EXPRESSION));
		registerParser(new IElementType[]{BT_INCREASE, BT_DECREASE}, 950, AppendTokenParser.JUST_APPEND);

		/*点号*/
		registerParser(BT_DOT, 950, path().left(), new TokenParser() {
			@Override
			public boolean parseToken(PrattBuilder prattBuilder) {
				MutableMarker mark = prattBuilder.mark();
				prattBuilder.advance();
				prattBuilder.assertToken(BT_IDENTIFIER, "expected identifier.but this is " + prattBuilder.getTokenText());
				while (prattBuilder.checkToken(BT_DOT)) {
					prattBuilder.assertToken(BT_IDENTIFIER, "expected identifier.but this is " + prattBuilder.getTokenText());
				}
				mark.finish(REFERENCE_EXPRESSION);
				return true;
			}
		});
		/*感叹号，安全表达式  ref!*/
		registerParser(BT_NOT, 950, path().left(or(object(BT_IDENTIFIER), object(REFERENCE_EXPRESSION))).up(), postfix(SAFETY_OUTPUT));
		/*感叹号，绝对安全表达式：!(ref)*/
		registerParser(BT_NOT, 950, path().up(), new TokenParser() {
			@Override
			public boolean parseToken(PrattBuilder prattBuilder) {
				MutableMarker mark = prattBuilder.mark();
				prattBuilder.advance();
				if (prattBuilder.isToken(BT_LPAREN)) {
					prattBuilder.createChildBuilder(890).parse();
					mark.finish(SAFETY_OUTPUT);
					return true;
				}
				mark.finish(null);
				return true;
			}
		});
		/*三元表达式 true 的部分*/
		registerParser(BT_QUESTOIN, 950, path().left(or(object(BT_IDENTIFIER), object(REFERENCE_EXPRESSION))), new TokenParser() {
			@Override
			public boolean parseToken(PrattBuilder prattBuilder) {
				MutableMarker mark = prattBuilder.mark();
				prattBuilder.advance();
				prattBuilder.createChildBuilder(890).parse();
				mark.finish(TERNARY_TRUE);
				return true;
			}
		});

		/*三元表达式 false 的部分*/
		registerParser(BT_COLON, 950, path().left(TERNARY_TRUE), new TokenParser() {
			@Override
			public boolean parseToken(PrattBuilder prattBuilder) {
				MutableMarker mark = prattBuilder.mark();
				prattBuilder.advance();
				prattBuilder.createChildBuilder(890).parse();
				mark.finish(TERNARY_FALSE);
				return true;
			}
		});

		/*二元运算符*/
		registerParser(new IElementType[]{BT_PLUS, BT_MINUS, BT_MUL, BT_REMAINDER, BT_QUOTIENT, BT_COND_AND, BT_COND_OR}, 950,
				path().left(or(object(BT_IDENTIFIER), object(REFERENCE_EXPRESSION), object(NUMBER), object(STRING_LITERAL))), new TokenParser() {
					@Override
					public boolean parseToken(PrattBuilder prattBuilder) {
						prattBuilder.advance();
						prattBuilder.createChildBuilder(900).parse();
						prattBuilder.reduce(BINARY_EXPRESSION);
						return true;
					}
				});
		/*逻辑表达式*/
		registerParser(new IElementType[]{BT_LESS, BT_LESS_OR_EQUAL, BT_GREATER, BT_GREATER_OR_EQUAL, BT_EQ, BT_NOT_EQ}, 950,
				path().left(or(object(BT_IDENTIFIER), object(REFERENCE_EXPRESSION), object(NUMBER), object(STRING_LITERAL))), infix(895, LOGICAL_EXPRESSION));
		/*变量和引用语法*/
		registerParser(BT_IDENTIFIER, 1000, new TokenParser() {
			@Override
			public boolean parseToken(PrattBuilder prattBuilder) {
				MutableMarker outMarker = prattBuilder.mark();
				MutableMarker innerMarker = prattBuilder.mark();
				prattBuilder.advance();
				while (prattBuilder.checkToken(BT_DOT)) {
					prattBuilder.assertToken(BT_IDENTIFIER);
				}
				innerMarker.finish(REFERENCE_EXPRESSION);
				if (prattBuilder.isToken(BT_LPAREN)) {
					prattBuilder.createChildBuilder(890).parse();
					outMarker.finish(METHOD_CALLED);
				} else {
					outMarker.drop();
				}
				return true;
			}
		});
		registerParser(BT_IF, 100, new TokenParser() {
			@Override
			public boolean parseToken(PrattBuilder prattBuilder) {
				MutableMarker mark = prattBuilder.mark();
				prattBuilder.advance();
				prattBuilder.createChildBuilder(100).parse();
				mark.finish(IF_STATEMENT);
				return true;
			}
		});
		registerParser(BT_ELSE, 100, new TokenParser() {
			@Override
			public boolean parseToken(PrattBuilder prattBuilder) {
				MutableMarker mark = prattBuilder.mark();
				prattBuilder.advance();
				prattBuilder.checkToken(BT_IF);
				prattBuilder.createChildBuilder(100).parse();
				mark.finish(ELSE_STATEMENT);
				return true;
			}
		});
		registerParser(BT_FOR, 100, new TokenParser() {
			@Override
			public boolean parseToken(PrattBuilder prattBuilder) {
				MutableMarker mark = prattBuilder.mark();
				prattBuilder.advance();
				prattBuilder.createChildBuilder(100).parse();
				mark.finish(FOR_STATEMENT);
				return true;
			}
		});
		registerParser(BT_FOR, 100, new TokenParser() {
			@Override
			public boolean parseToken(PrattBuilder prattBuilder) {
				MutableMarker mark = prattBuilder.mark();
				prattBuilder.advance();
				prattBuilder.createChildBuilder(100).parse();
				mark.finish(ELSEFOR_STATEMENT);
				return true;
			}
		});
		registerParser(BT_WHILE, 100, new TokenParser() {
			@Override
			public boolean parseToken(PrattBuilder prattBuilder) {
				MutableMarker mark = prattBuilder.mark();
				prattBuilder.advance();
				prattBuilder.createChildBuilder(100).parse();
				mark.finish(WHILE_STATEMENT);
				return true;
			}
		});
		registerParser(BT_SWITCH, 100, new TokenParser() {
			@Override
			public boolean parseToken(PrattBuilder prattBuilder) {
				MutableMarker mark = prattBuilder.mark();
				prattBuilder.advance();
				prattBuilder.createChildBuilder(100).parse();
				mark.finish(SWITCH_STATEMENT);
				return true;
			}
		});
		registerParser(BT_SELECT, 100, new TokenParser() {
			@Override
			public boolean parseToken(PrattBuilder prattBuilder) {
				MutableMarker mark = prattBuilder.mark();
				prattBuilder.advance();
				prattBuilder.createChildBuilder(100).parse();
				mark.finish(SELECT_STATEMENT);
				return true;
			}
		});
		registerParser(BT_CASE, 100, new TokenParser() {
			@Override
			public boolean parseToken(PrattBuilder prattBuilder) {
				MutableMarker mark = prattBuilder.mark();
				prattBuilder.advance();
				prattBuilder.createChildBuilder(100).parse();
				mark.finish(CASE_SEGMENT);
				return true;
			}
		});
		/*冒号，第二种是case 及ajax 后标记语法块*/
		registerParser(BT_COLON, 950, path().left().up().left(BT_CASE), new TokenParser() {
			@Override
			public boolean parseToken(PrattBuilder prattBuilder) {
				prattBuilder.advance();
				prattBuilder.createChildBuilder(100).parse();
				prattBuilder.reduce(SYMBOL_BODY);
				return true;
			}
		});
		/*ajax片段*/
		registerParser(new IElementType[]{BT_AJAX, BT_FRAGMENT}, 100, new TokenParser() {
			@Override
			public boolean parseToken(PrattBuilder prattBuilder) {
				MutableMarker mark = prattBuilder.mark();
				prattBuilder.advance();
				prattBuilder.createChildBuilder(9).parse();
				mark.finish(AJAX_STATEMENT);
				return true;
			}
		});

		registerParser(new IElementType[]{BT_INT, BT_FLOAT, BT_OCT, BT_HEX}, 1000, new AppendTokenParser() {
			@Nullable
			@Override
			protected IElementType parseAppend(PrattBuilder prattBuilder) {
				return NUMBER;
			}
		});
		registerParser(new IElementType[]{BT_TRUE, BT_FALSE, BT_NULL}, 1000, AppendTokenParser.JUST_APPEND);
		registerParser(BT_STRING, 1000, new AppendTokenParser() {
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
		registerParser(new IElementType[]{BT_BREAK, BT_RETURN, BT_CONTINUE, BT_DEFAULT, BT_VAR, BT_FOR_IN}, 800, AppendTokenParser.JUST_APPEND);
		registerParser(BTL_TEMPLATE_HTML_TEXT, 1000, AppendTokenParser.JUST_APPEND);

		registerParser(ERROR_ELEMENT, 1000, new TokenParser() {
			@Override
			public boolean parseToken(PrattBuilder prattBuilder) {
				return false;
			}
		});
	}

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

}

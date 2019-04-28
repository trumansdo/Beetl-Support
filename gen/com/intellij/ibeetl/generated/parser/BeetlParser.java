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

// This is a generated file. Not intended for manual editing.
package com.intellij.ibeetl.generated.parser;

import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiBuilder.Marker;
import static com.intellij.ibeetl.generated.psi.BeetlTypes.*;
import static com.intellij.ibeetl.lang.parser.BeetlParserUtil.*;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.IFileElementType;
import com.intellij.lang.ASTNode;
import com.intellij.psi.tree.TokenSet;
import com.intellij.lang.PsiParser;
import com.intellij.lang.LightPsiParser;

@SuppressWarnings({"SimplifiableIfStatement", "UnusedAssignment"})
public class BeetlParser implements PsiParser, LightPsiParser {

  public ASTNode parse(IElementType type, PsiBuilder builder) {
    parseLight(type, builder);
    return builder.getTreeBuilt();
  }

  public void parseLight(IElementType type, PsiBuilder builder) {
    boolean result;
    builder = adapt_builder_(type, builder, this, null);
    Marker marker = enter_section_(builder, 0, _COLLAPSE_, null);
    if (type instanceof IFileElementType) {
      result = parse_root_(type, builder, 0);
    }
    else {
      result = false;
    }
    exit_section_(builder, 0, marker, type, result, true, TRUE_CONDITION);
  }

  protected boolean parse_root_(IElementType type, PsiBuilder builder, int level) {
    return file(builder, level + 1);
  }

  /* ********************************************************** */
  // literal '+' literal
  public static boolean binary_expression(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "binary_expression")) return false;
    if (!nextTokenIs(builder, "<binary expression>", BTL_NUMBER, BTL_STRING)) return false;
    boolean result;
    Marker marker = enter_section_(builder, level, _NONE_, BTL_BINARY_EXPRESSION, "<binary expression>");
    result = literal(builder, level + 1);
    result = result && consumeToken(builder, BTL_PLUS);
    result = result && literal(builder, level + 1);
    exit_section_(builder, level, marker, result, false, null);
    return result;
  }

  /* ********************************************************** */
  // !(literal)
  static boolean binary_expression_recover(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "binary_expression_recover")) return false;
    boolean result;
    Marker marker = enter_section_(builder, level, _NOT_);
    result = !binary_expression_recover_0(builder, level + 1);
    exit_section_(builder, level, marker, result, false, null);
    return result;
  }

  // (literal)
  private static boolean binary_expression_recover_0(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "binary_expression_recover_0")) return false;
    boolean result;
    Marker marker = enter_section_(builder);
    result = literal(builder, level + 1);
    exit_section_(builder, marker, null, result);
    return result;
  }

  /* ********************************************************** */
  // binary_expression (';' binary_expression?)*
  static boolean file(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "file")) return false;
    boolean result, pinned;
    Marker marker = enter_section_(builder, level, _NONE_);
    result = binary_expression(builder, level + 1);
    pinned = result; // pin = 1
    result = result && file_1(builder, level + 1);
    exit_section_(builder, level, marker, result, pinned, BeetlParser::binary_expression_recover);
    return result || pinned;
  }

  // (';' binary_expression?)*
  private static boolean file_1(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "file_1")) return false;
    while (true) {
      int pos = current_position_(builder);
      if (!file_1_0(builder, level + 1)) break;
      if (!empty_element_parsed_guard_(builder, "file_1", pos)) break;
    }
    return true;
  }

  // ';' binary_expression?
  private static boolean file_1_0(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "file_1_0")) return false;
    boolean result;
    Marker marker = enter_section_(builder);
    result = consumeToken(builder, ";");
    result = result && file_1_0_1(builder, level + 1);
    exit_section_(builder, marker, null, result);
    return result;
  }

  // binary_expression?
  private static boolean file_1_0_1(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "file_1_0_1")) return false;
    binary_expression(builder, level + 1);
    return true;
  }

  /* ********************************************************** */
  // string | number
  public static boolean literal(PsiBuilder builder, int level) {
    if (!recursion_guard_(builder, level, "literal")) return false;
    if (!nextTokenIs(builder, "<literal>", BTL_NUMBER, BTL_STRING)) return false;
    boolean result;
    Marker marker = enter_section_(builder, level, _NONE_, BTL_LITERAL, "<literal>");
    result = consumeToken(builder, BTL_STRING);
    if (!result) result = consumeToken(builder, BTL_NUMBER);
    exit_section_(builder, level, marker, result, false, null);
    return result;
  }

}

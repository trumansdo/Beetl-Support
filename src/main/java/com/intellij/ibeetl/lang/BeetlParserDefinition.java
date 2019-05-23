package com.intellij.ibeetl.lang;

import com.intellij.ibeetl.lang.base.BeetlTokenType;
import com.intellij.ibeetl.lang.lexer.BeetlLexer;
import com.intellij.ibeetl.lang.lexer.BeetlTokenTypes;
import com.intellij.ibeetl.lang.parser.BeetlParser;
import com.intellij.lang.ASTNode;
import com.intellij.lang.ParserDefinition;
import com.intellij.lang.PsiParser;
import com.intellij.lexer.Lexer;
import com.intellij.openapi.project.Project;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.TokenType;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.IFileElementType;
import com.intellij.psi.tree.TokenSet;
import org.jetbrains.annotations.NotNull;

import static com.intellij.ibeetl.lang.lexer.BeetlTokenTypes.LINE_COMMENT;
import static com.intellij.ibeetl.lang.lexer.BeetlTokenTypes.MULTILINE_COMMENT;

public class BeetlParserDefinition implements ParserDefinition {

	public static final IFileElementType file = new IFileElementType("plus_file", BeetlLanguage.INSTANCE);

	public static final TokenSet WS = TokenSet.create(TokenType.WHITE_SPACE);

	public static final TokenSet COMMENTS = TokenSet.create(LINE_COMMENT, MULTILINE_COMMENT);

	public static final TokenSet STRING_LITERAL = TokenSet.create(BeetlTokenTypes.BT_STRING);


	@NotNull
	@Override
	public Lexer createLexer(Project project) {
		return new BeetlLexer();
	}

	@Override
	public PsiParser createParser(Project project) {
		return new BeetlParser();
	}

	@Override
	public IFileElementType getFileNodeType() {
		return file;
	}

	@NotNull
	@Override
	public TokenSet getWhitespaceTokens() {
		return WS;
	}

	@NotNull
	@Override
	public TokenSet getCommentTokens() {
		return COMMENTS;
	}

	@NotNull
	@Override
	public TokenSet getStringLiteralElements() {
		return TokenSet.EMPTY;
	}

	@NotNull
	@Override
	public PsiElement createElement(ASTNode astNode) {
		return BeetlTokenTypes.Factory.createElement(astNode);
	}

	@Override
	public PsiFile createFile(FileViewProvider fileViewProvider) {
		return new BeetlFile(fileViewProvider);
	}
}

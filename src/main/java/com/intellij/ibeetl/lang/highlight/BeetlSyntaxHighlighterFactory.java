package com.intellij.ibeetl.lang.highlight;


import com.intellij.openapi.fileTypes.SyntaxHighlighter;
import com.intellij.openapi.fileTypes.SyntaxHighlighterFactory;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;

/**
 * 语法高亮有五级：
 * 1. lexer级别
 * 2. parser级别（基本不用）
 * 3. annotator级别（最丰富的高亮）
 * 4. external tool级别（基本不用，很慢）
 * 5. color scheme 用户设置界面级别（麻烦）
 * 在配置文件中可找
 */
public class BeetlSyntaxHighlighterFactory extends SyntaxHighlighterFactory {
	@NotNull
	@Override
	public SyntaxHighlighter getSyntaxHighlighter(Project project, VirtualFile virtualFile) {
		return new BeetlSyntaxHighlighter();
	}
}
package com.intellij.ibeetl.lang.support;

import com.intellij.codeInsight.completion.*;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.util.ProcessingContext;
import org.jetbrains.annotations.NotNull;
/*
* 暂定为对beetl的配置文件提供提示功能
* */
public class BeetlCompletionContributor extends CompletionContributor {
	public BeetlCompletionContributor() {
		extend(CompletionType.BASIC,
				PlatformPatterns.psiElement(),
				new CompletionProvider<CompletionParameters>() {
					public void addCompletions(@NotNull CompletionParameters parameters,
					                           ProcessingContext context,
					                           @NotNull CompletionResultSet resultSet) {
						resultSet.addElement(LookupElementBuilder.create("Hello"));
					}
				}
		);
	}
}

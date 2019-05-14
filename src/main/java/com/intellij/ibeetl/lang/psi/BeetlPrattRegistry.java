package com.intellij.ibeetl.lang.psi;

import com.intellij.lang.pratt.PathPattern;
import com.intellij.lang.pratt.PrattRegistry;
import com.intellij.lang.pratt.TokenParser;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;

/**
 * pratt  解析器的注册中心。pratt  解析器是一个组合解析器，将解析任务给不同的token parser实现组合解析。更容易实现解析器
 */
public class BeetlPrattRegistry {
	public static final PrattRegistry REGISTRY = new PrattRegistry();


	public static void registerParser(@NotNull IElementType type, int priority, TokenParser parser) {
		REGISTRY.registerParser(type, priority, parser);
	}

	public static void registerParser(@NotNull IElementType type, int priority, PathPattern pattern, TokenParser parser) {
		REGISTRY.registerParser(type, priority, pattern, parser);
	}
}

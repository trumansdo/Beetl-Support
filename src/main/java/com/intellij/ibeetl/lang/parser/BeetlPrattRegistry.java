package com.intellij.ibeetl.lang.parser;

import com.intellij.lang.pratt.PathPattern;
import com.intellij.lang.pratt.PrattRegistry;
import com.intellij.lang.pratt.TokenParser;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;

import java.util.stream.Stream;

/**
 * pratt  解析器的注册中心。pratt  解析器是一个组合解析器，将解析任务给不同的token parser实现组合解析。更容易实现解析器
 */
public class BeetlPrattRegistry {
	/*先注册的先查找*/
	public static final PrattRegistry REGISTRY = new PrattRegistry();


	public static void registerParser(@NotNull IElementType type, int priority, TokenParser parser) {
		REGISTRY.registerParser(type, priority, parser);
	}

	public static void registerParser(@NotNull IElementType type, int priority, PathPattern pattern, TokenParser parser) {
		REGISTRY.registerParser(type, priority, pattern, parser);
	}

	public static void registerParser(@NotNull IElementType[] types, int priority, TokenParser parser) {
		Stream.of(types).parallel().forEach(type -> {
			REGISTRY.registerParser(type, priority, parser);
		});
	}

	public static void registerParser(@NotNull IElementType[] types, int priority, PathPattern pattern, TokenParser parser) {
		Stream.of(types).parallel().forEach(type -> {
			REGISTRY.registerParser(type, priority, pattern, parser);
		});
	}
}

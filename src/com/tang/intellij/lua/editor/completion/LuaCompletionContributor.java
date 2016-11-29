package com.tang.intellij.lua.editor.completion;

import com.intellij.codeInsight.completion.*;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.openapi.project.Project;
import com.intellij.patterns.PsiElementPattern;
import com.intellij.psi.PsiElement;
import com.intellij.util.ProcessingContext;
import com.tang.intellij.lua.psi.index.LuaClassIndex;
import com.tang.intellij.lua.psi.index.LuaGlobalFuncIndex;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

import static com.intellij.patterns.PlatformPatterns.psiElement;

/**
 *
 * Created by tangzx on 2016/11/27.
 */
public class LuaCompletionContributor extends CompletionContributor {

    private static final PsiElementPattern.Capture<PsiElement> AFTER_DOT = psiElement().afterLeaf(".", ":");

    public LuaCompletionContributor() {
        extend(CompletionType.BASIC, AFTER_DOT, new CompletionProvider<CompletionParameters>() {
            @Override
            protected void addCompletions(@NotNull CompletionParameters completionParameters, ProcessingContext processingContext, @NotNull CompletionResultSet completionResultSet) {
                Project project = completionParameters.getOriginalFile().getProject();

                //global functions
                Collection<String> all = LuaGlobalFuncIndex.getInstance().getAllKeys(completionParameters.getOriginalFile().getProject());
                all.forEach(name -> completionResultSet.addElement(LookupElementBuilder.create(name)));

                //class
                Collection<String> allClasses = LuaClassIndex.getInstance().getAllKeys(project);
                allClasses.forEach(className -> completionResultSet.addElement(LookupElementBuilder.create(className)));
            }
        });
    }

}
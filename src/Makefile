SUPPORTED_PLATFORMS = JAVAWIN JAVABSD

# SUPPORTED_PLATFORMS = WIN32 FREEBSD FREEBSDCLANG WIN32VC WIN64VC LINUX RASPBERRYPI JAVAWIN JAVABSD

ifeq (,$(findstring $(OS),$(SUPPORTED_PLATFORMS)))

all:
	@echo 
	@echo The OS environment variable is set to [$(OS)].
	@echo Please set the OS environment variable to one of the following:
	@echo $(SUPPORTED_PLATFORMS)
	@echo 

else


all: build


include Makefile.$(OS)

SOURCEFILES = at/tspi/ebnf/compiler/ASTBuilder.java \
	at/tspi/ebnf/compiler/ASTBuilderISO14977.java \
	at/tspi/ebnf/compiler/ASTBuilderISO14977Gen.java \
	at/tspi/ebnf/compiler/ASTCombiner.java \
	at/tspi/ebnf/compiler/ASTException.java \
	at/tspi/ebnf/compiler/ASTOperation.java \
	at/tspi/ebnf/compiler/CompilerState.java \
	at/tspi/ebnf/compiler/KeepfileReader.java \
	at/tspi/ebnf/compiler/ast/ASTChoice.java \
	at/tspi/ebnf/compiler/ast/ASTConstantExpression.java \
	at/tspi/ebnf/compiler/ast/ASTEmpty.java \
	at/tspi/ebnf/compiler/ast/ASTNode.java \
	at/tspi/ebnf/compiler/ast/ASTOptional.java \
	at/tspi/ebnf/compiler/ast/ASTProduction.java \
	at/tspi/ebnf/compiler/ast/ASTProductionReference.java \
	at/tspi/ebnf/compiler/ast/ASTRepeat.java \
	at/tspi/ebnf/compiler/ast/ASTRoot.java \
	at/tspi/ebnf/compiler/ast/ASTSequential.java \
	at/tspi/ebnf/compiler/ast/ASTSingleTerminal.java \
	at/tspi/ebnf/compiler/ast/ASTSingleTerminalCollection.java \
	at/tspi/ebnf/compiler/ast/ASTSpecialSequence.java \
	at/tspi/ebnf/compiler/ast/ASTSyntacticException.java \
	at/tspi/ebnf/compiler/codegen/ParserCodeGenerator.java \
	at/tspi/ebnf/compiler/codegen/javasimpledescent/CodeGenJavaSimpleRecursiveDescent.java \
	at/tspi/ebnf/compiler/codegen/railroadsvg/CodeGenRailroadSVG.java \
	at/tspi/ebnf/compiler/codegen/railroadsvg/RailroadSVG_Choice.java \
	at/tspi/ebnf/compiler/codegen/railroadsvg/RailroadSVG_CodegenSettings.java \
	at/tspi/ebnf/compiler/codegen/railroadsvg/RailroadSVG_Coordinate.java \
	at/tspi/ebnf/compiler/codegen/railroadsvg/RailroadSVG_Diagram.java \
	at/tspi/ebnf/compiler/codegen/railroadsvg/RailroadSVG_Node.java \
	at/tspi/ebnf/compiler/codegen/railroadsvg/RailroadSVG_Nonterminal.java \
	at/tspi/ebnf/compiler/codegen/railroadsvg/RailroadSVG_Sequential.java \
	at/tspi/ebnf/compiler/codegen/railroadsvg/RailroadSVG_Terminal.java \
	at/tspi/ebnf/compiler/operations/ASTCheckProductionReferences.java \
	at/tspi/ebnf/compiler/operations/ASTCollectSingleTerminalChoices.java \
	at/tspi/ebnf/compiler/operations/ASTEmptyChoice.java \
	at/tspi/ebnf/compiler/operations/ASTIndexProductions.java \
	at/tspi/ebnf/compiler/operations/ASTReduceLiteralExceptions.java \
	at/tspi/ebnf/compiler/operations/ASTReduceLiteralOnlyProductions.java \
	at/tspi/ebnf/compiler/operations/ASTReduceSingleChoiceAndSequence.java \
	at/tspi/ebnf/compiler/operations/ASTReplaceEmptyStrings.java \
	at/tspi/ebnf/ebnfparser/EbnfElement_ConcatenateSymbol.java \
	at/tspi/ebnf/ebnfparser/EbnfElement_DefiningSymbol.java \
	at/tspi/ebnf/ebnfparser/EbnfElement_DefinitionSeparatorSymbol.java \
	at/tspi/ebnf/ebnfparser/EbnfElement_DefinitionsList.java \
	at/tspi/ebnf/ebnfparser/EbnfElement_DefinitionsList_1.java \
	at/tspi/ebnf/ebnfparser/EbnfElement_DefinitionsList_2.java \
	at/tspi/ebnf/ebnfparser/EbnfElement_EbnfFile.java \
	at/tspi/ebnf/ebnfparser/EbnfElement_EndGroupSymbol.java \
	at/tspi/ebnf/ebnfparser/EbnfElement_EndOptionalSymbol.java \
	at/tspi/ebnf/ebnfparser/EbnfElement_EndRepeatSymbol.java \
	at/tspi/ebnf/ebnfparser/EbnfElement_ExceptSymbol.java \
	at/tspi/ebnf/ebnfparser/EbnfElement_GroupedSequence.java \
	at/tspi/ebnf/ebnfparser/EbnfElement_Integer.java \
	at/tspi/ebnf/ebnfparser/EbnfElement_Letter.java \
	at/tspi/ebnf/ebnfparser/EbnfElement_MetaIdentifier.java \
	at/tspi/ebnf/ebnfparser/EbnfElement_MetaIdentifierCharacters.java \
	at/tspi/ebnf/ebnfparser/EbnfElement_OptionalSequence.java \
	at/tspi/ebnf/ebnfparser/EbnfElement_QuoteSymbolFirst.java \
	at/tspi/ebnf/ebnfparser/EbnfElement_QuoteSymbolSecond.java \
	at/tspi/ebnf/ebnfparser/EbnfElement_RepeatedSequence.java \
	at/tspi/ebnf/ebnfparser/EbnfElement_RepetitionSymbol.java \
	at/tspi/ebnf/ebnfparser/EbnfElement_SingleDefinition.java \
	at/tspi/ebnf/ebnfparser/EbnfElement_SingleDefinition_1.java \
	at/tspi/ebnf/ebnfparser/EbnfElement_SingleDefinition_2.java \
	at/tspi/ebnf/ebnfparser/EbnfElement_SpecialSequence.java \
	at/tspi/ebnf/ebnfparser/EbnfElement_SpecialSequenceSequence.java \
	at/tspi/ebnf/ebnfparser/EbnfElement_SpecialSequenceSymbol.java \
	at/tspi/ebnf/ebnfparser/EbnfElement_StartGroupSymbol.java \
	at/tspi/ebnf/ebnfparser/EbnfElement_StartOptionalSymbol.java \
	at/tspi/ebnf/ebnfparser/EbnfElement_StartRepeatSymbol.java \
	at/tspi/ebnf/ebnfparser/EbnfElement_SyntacticException.java \
	at/tspi/ebnf/ebnfparser/EbnfElement_SyntacticFactor.java \
	at/tspi/ebnf/ebnfparser/EbnfElement_SyntacticFactor_1.java \
	at/tspi/ebnf/ebnfparser/EbnfElement_SyntacticPrimary.java \
	at/tspi/ebnf/ebnfparser/EbnfElement_SyntacticTerm.java \
	at/tspi/ebnf/ebnfparser/EbnfElement_SyntacticTerm_1.java \
	at/tspi/ebnf/ebnfparser/EbnfElement_SyntaxRule.java \
	at/tspi/ebnf/ebnfparser/EbnfElement_TerminalString.java \
	at/tspi/ebnf/ebnfparser/EbnfElement_TerminalStringFirst.java \
	at/tspi/ebnf/ebnfparser/EbnfElement_TerminalStringFirstDelim.java \
	at/tspi/ebnf/ebnfparser/EbnfElement_TerminalStringSecond.java \
	at/tspi/ebnf/ebnfparser/EbnfElement_TerminalStringSecondDelim.java \
	at/tspi/ebnf/ebnfparser/EbnfElement_TerminatorSymbol.java \
	at/tspi/ebnf/ebnfparser/EbnfParser.java \
	at/tspi/ebnf/ebnfparser/EbnfPreprocessor.java \
	at/tspi/ebnf/genisoebnfparser/EbnfElement_definitionslist.java \
	at/tspi/ebnf/genisoebnfparser/EbnfElement_definitionslist_10.java \
	at/tspi/ebnf/genisoebnfparser/EbnfElement_definitionslist_8.java \
	at/tspi/ebnf/genisoebnfparser/EbnfElement_definitionslist_9.java \
	at/tspi/ebnf/genisoebnfparser/EbnfElement_emptysequence.java \
	at/tspi/ebnf/genisoebnfparser/EbnfElement_groupedsequence.java \
	at/tspi/ebnf/genisoebnfparser/EbnfElement_groupedsequence_43.java \
	at/tspi/ebnf/genisoebnfparser/EbnfElement_groupedsequence_45.java \
	at/tspi/ebnf/genisoebnfparser/EbnfElement_integer.java \
	at/tspi/ebnf/genisoebnfparser/EbnfElement_integer_28.java \
	at/tspi/ebnf/genisoebnfparser/EbnfElement_integer_29.java \
	at/tspi/ebnf/genisoebnfparser/EbnfElement_metaidentifier.java \
	at/tspi/ebnf/genisoebnfparser/EbnfElement_metaidentifier_46.java \
	at/tspi/ebnf/genisoebnfparser/EbnfElement_metaidentifier_47.java \
	at/tspi/ebnf/genisoebnfparser/EbnfElement_optionalsequence.java \
	at/tspi/ebnf/genisoebnfparser/EbnfElement_optionalsequence_37.java \
	at/tspi/ebnf/genisoebnfparser/EbnfElement_optionalsequence_39.java \
	at/tspi/ebnf/genisoebnfparser/EbnfElement_Parser.java \
	at/tspi/ebnf/genisoebnfparser/EbnfElement_repeatedsequence.java \
	at/tspi/ebnf/genisoebnfparser/EbnfElement_repeatedsequence_40.java \
	at/tspi/ebnf/genisoebnfparser/EbnfElement_repeatedsequence_42.java \
	at/tspi/ebnf/genisoebnfparser/EbnfElement_singledefinition.java \
	at/tspi/ebnf/genisoebnfparser/EbnfElement_singledefinition_13.java \
	at/tspi/ebnf/genisoebnfparser/EbnfElement_singledefinition_14.java \
	at/tspi/ebnf/genisoebnfparser/EbnfElement_singledefinition_15.java \
	at/tspi/ebnf/genisoebnfparser/EbnfElement_specialsequence.java \
	at/tspi/ebnf/genisoebnfparser/EbnfElement_specialsequencevalue.java \
	at/tspi/ebnf/genisoebnfparser/EbnfElement_specialsequence_60.java \
	at/tspi/ebnf/genisoebnfparser/EbnfElement_specialsequence_62.java \
	at/tspi/ebnf/genisoebnfparser/EbnfElement_syntacticexception.java \
	at/tspi/ebnf/genisoebnfparser/EbnfElement_syntacticfactor.java \
	at/tspi/ebnf/genisoebnfparser/EbnfElement_syntacticfactorrepeatedfactor.java \
	at/tspi/ebnf/genisoebnfparser/EbnfElement_syntacticfactorrepeatedfactor_26.java \
	at/tspi/ebnf/genisoebnfparser/EbnfElement_syntacticprimary.java \
	at/tspi/ebnf/genisoebnfparser/EbnfElement_syntacticterm.java \
	at/tspi/ebnf/genisoebnfparser/EbnfElement_syntacticterm_18.java \
	at/tspi/ebnf/genisoebnfparser/EbnfElement_syntacticterm_19.java \
	at/tspi/ebnf/genisoebnfparser/EbnfElement_syntacticterm_20.java \
	at/tspi/ebnf/genisoebnfparser/EbnfElement_syntacticterm_21.java \
	at/tspi/ebnf/genisoebnfparser/EbnfElement_syntax.java \
	at/tspi/ebnf/genisoebnfparser/EbnfElement_syntaxrule.java \
	at/tspi/ebnf/genisoebnfparser/EbnfElement_syntaxrule_4.java \
	at/tspi/ebnf/genisoebnfparser/EbnfElement_syntaxrule_6.java \
	at/tspi/ebnf/genisoebnfparser/EbnfElement_syntax_1.java \
	at/tspi/ebnf/genisoebnfparser/EbnfElement_terminalstring.java \
	at/tspi/ebnf/genisoebnfparser/EbnfElement_terminalstringfirst.java \
	at/tspi/ebnf/genisoebnfparser/EbnfElement_terminalstringfirst_56.java \
	at/tspi/ebnf/genisoebnfparser/EbnfElement_terminalstringfirst_57.java \
	at/tspi/ebnf/genisoebnfparser/EbnfElement_terminalstringsecond.java \
	at/tspi/ebnf/genisoebnfparser/EbnfElement_terminalstringsecond_58.java \
	at/tspi/ebnf/genisoebnfparser/EbnfElement_terminalstringsecond_59.java \
	at/tspi/ebnf/genisoebnfparser/EbnfElement_terminalstring_48.java \
	at/tspi/ebnf/genisoebnfparser/EbnfElement_terminalstring_49.java \
	at/tspi/ebnf/genisoebnfparser/EbnfElement_terminalstring_51.java \
	at/tspi/ebnf/genisoebnfparser/EbnfElement_terminalstring_52.java \
	at/tspi/ebnf/genisoebnfparser/EbnfElement_terminalstring_53.java \
	at/tspi/ebnf/genisoebnfparser/EbnfElement_terminalstring_55.java \
	at/tspi/ebnf/genkeepfileparser/KeepParser_keep.java \
	at/tspi/ebnf/genkeepfileparser/KeepParser_keepfilesyntax.java \
	at/tspi/ebnf/genkeepfileparser/KeepParser_keepfilesyntax_17.java \
	at/tspi/ebnf/genkeepfileparser/KeepParser_metaidentifier.java \
	at/tspi/ebnf/genkeepfileparser/KeepParser_metaidentifier_0.java \
	at/tspi/ebnf/genkeepfileparser/KeepParser_metaidentifier_1.java \
	at/tspi/ebnf/genkeepfileparser/KeepParser_Parser.java \
	at/tspi/ebnf/genkeepfileparser/KeepParser_prodoption.java \
	at/tspi/ebnf/genkeepfileparser/KeepParser_productionproperty.java \
	at/tspi/ebnf/genkeepfileparser/KeepParser_productionproperty_11.java \
	at/tspi/ebnf/genkeepfileparser/KeepParser_productionproperty_12.java \
	at/tspi/ebnf/genkeepfileparser/KeepParser_productionproperty_13.java \
	at/tspi/ebnf/genkeepfileparser/KeepParser_productionproperty_15.java \
	at/tspi/ebnf/genkeepfileparser/KeepParser_starter.java \
	at/tspi/ebnf/genkeepfileparser/KeepParser_whitespacemode.java \
	at/tspi/ebnf/genkeepfileparser/KeepParser_whitespacemodeidentifier.java \
	at/tspi/ebnf/genkeepfileparser/KeepParser_whitespacemodeignore.java \
	at/tspi/ebnf/genkeepfileparser/KeepParser_whitespacemodereduce.java \
	at/tspi/ebnf/genkeepfileparser/KeepParser_whitespacemodestrip.java \
	at/tspi/ebnf/genkeepfileparser/KeepParser_whitespacemode_2.java \
	at/tspi/ebnf/parser/ASCIIFileSource.java \
	at/tspi/ebnf/parser/Parser.java \
	at/tspi/ebnf/parser/ParserDataSource.java \
	at/tspi/ebnf/parser/ParserElement.java \
	at/tspi/ebnf/parser/ParserElementChoice.java \
	at/tspi/ebnf/parser/ParserElementConstant.java \
	at/tspi/ebnf/parser/ParserElementEmpty.java \
	at/tspi/ebnf/parser/ParserElementRepeatOptional.java \
	at/tspi/ebnf/parser/ParserElementSequential.java \
	at/tspi/ebnf/parser/ParserElementTerminal.java \
	at/tspi/ebnf/parser/ParserException.java \
	at/tspi/ebnf/parser/ParserGraphemeCluster.java \
	at/tspi/ebnf/parser/ParserLogger.java \
	at/tspi/ebnf/parser/ParserPreprocessorException.java \
	at/tspi/ebnf/parser/Preprocessor.java \
	at/tspi/ebnf/parser/PreprocessorSimpleWhitespace.java \
	ebnf/ebnf.ebnf \
	ebnf/ebnf.keep \
	ebnf/keepfile.ebnf \
	ebnf/keepfile.keep \
	jebnfc.java

CPATH=.$(JCLASSPATHSEPCHAR)../external/jcliargs/bin/jcliargs.jar
	
build: clean $(SOURCEFILES)

	javac -cp ${CPATH} jebnfc.java

runtest: all

	java -cp ${CPATH} jebnfc


# clean: osspecclean

clean: 



.PHONY: clean runtest

endif

package at.tspi.ebnf.parser;



public interface Preprocessor {
	public static final int			PROPERTY_WHITESPACE 						= 1;


	public static final int			VALUE_WHITESPACE__NOCHANGE					= 0;
	public static final int			VALUE_WHITESPACE__IGNORE					= 1;
	public static final int			VALUE_WHITESPACE__REDUCE					= 2;
	public static final int			VALUE_WHITESPACE__STRIP						= 3;

	public static final int			VALUE_WHITESPACE__TOGGLE_STRIP_IGNORE		= 10;

	public void 				setParserDataSource(ParserDataSource src);
	public ParserDataSource 	getCurrentSource();

	public void 				setMode(int property, int value) throws ParserPreprocessorException;
	public int					getMode(int property) throws ParserPreprocessorException;
}

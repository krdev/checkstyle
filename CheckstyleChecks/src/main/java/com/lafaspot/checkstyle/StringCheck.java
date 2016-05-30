/**
 *
 */
package com.lafaspot.checkstyle;

import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * Checker to catch construction of String object when Charset is represented as a string.
 *
 * @author kraman
 *
 */
public class StringCheck extends Check {

    /** To identify the String constructor with 3 parameters - including commas. */
    private static final int STR_CTOR_WITH_3_PARAMS = 3;
    /** To identify the String constructor with 7 parameters - including commas. */
    private static final int STR_CTOR_WITH_7_PARAMS = 7;

    @Override
    public int[] getDefaultTokens() {
        return new int[] { TokenTypes.LITERAL_NEW };
    }

    @Override
    public void visitToken(DetailAST ast) {
        DetailAST identAst = ast.findFirstToken(TokenTypes.IDENT);
        if (null != identAst) {
            if ("String".equals(identAst.getText())) {
                DetailAST strParams = ast.findFirstToken(TokenTypes.ELIST);
                if (null != strParams && strParams.getChildCount() > 0) {
                    if (STR_CTOR_WITH_3_PARAMS == strParams.getChildCount()) {
                        DetailAST lastElem = strParams.getLastChild();
                        if (null != lastElem && lastElem.getType() == TokenTypes.EXPR) {
                            DetailAST lastParam = lastElem.getFirstChild();
                            if (null != lastParam && lastParam.getType() == TokenTypes.STRING_LITERAL) {
                                log(ast.getLineNo(),
 "Illegal use of String constructor with charset name - use Charset object instead.");
                            }
                        }
                    } else if (STR_CTOR_WITH_7_PARAMS == strParams.getChildCount()) {
                        DetailAST lastElem = strParams.getLastChild();
                        if (null != lastElem && lastElem.getType() == TokenTypes.EXPR) {
                            DetailAST lastParam = lastElem.getFirstChild();
                            if (null != lastParam && lastParam.getType() == TokenTypes.STRING_LITERAL) {
                                log(ast.getLineNo(),
 "Illegal use of String constructor with charset name - use Charset object instead.");
                            }
                        }
                    }
                }
            }
        }
    }
}

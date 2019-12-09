import java.util.Stack;

public class CodeGenerator 
{
	static Stack<String> codeStack = new Stack<String>();
    static String declarations = "int resultado;" + "\n";
    static String inputDataCode = "";
    static String newLine = "\n";
    static char quote = '"';
	//
	
	
	public static String getObjectiveCode()
	{
		String code;
		//
		code = "";
		if(!codeStack.isEmpty())
		    code = codeStack.pop();
		//end if
		return code;
	}//end getObjectiveCode
	
	
	private static String startingCode()
	{
		String startingCode;
        //
	    
		startingCode =
	  		"import java.util.Scanner;"+ newLine +
	  		"public class ObjectiveCode" + newLine +
	  		"{" + newLine + newLine +
	  		"public static int readInt() " + newLine +
	  		"{" + newLine +
	   		"Scanner entrada;" + newLine +
			"int dato;" + newLine +
	    	"entrada = new Scanner(System.in);" + newLine +
	    	"dato = entrada.nextInt();" + newLine +
	    	"return dato; " + newLine +
	  		"}//end readInt"  + newLine + newLine +
	    	"public static void main(String[] args)" + newLine +
	  		"{" + newLine;
	  return startingCode;
	}//end startingCode
	
	
	private static String declarationCode()
	{
	  String declarationCode;
      //
	  declarationCode = declarations;
	  return declarationCode;
	}//end declarationCode
	
	
	private static String inputCode()
	{
	  String inputCode;
      //
	  inputCode = inputDataCode;
	  return inputCode;
	}//end inputCode
	
	
	private static String outputCode()
	{
	  String outputCode;
      //
	  outputCode = "System.out.print(" + quote +
			  "El Resultado es " + quote + ");" + newLine +
			  "System.out.println(resultado);" + newLine ;
	  return outputCode;
	}//end outputCode
	
	
	private static String endingCode()
	{
	  String endingCode;
      //
	  endingCode =
	  "}//end main"  + newLine +
	  "}//end class" + newLine;
	
	  return endingCode;
	}//end startingCode
	
	
	public static void translation(int ruleNumber, Stack<Token> symbolStack)
	{
		String firstToken;
		String secondToken;
	    String code;
        //
		switch(ruleNumber)
		{
		case 0:
		{
			// compilamos los pedazos de codigo objetivo
			firstToken = codeStack.pop();
			code = startingCode() +
            		    declarationCode() +
            		    inputCode() + 
            		    "resultado = "+ firstToken + ";" + newLine +
            		    outputCode() +
            		    endingCode();
			codeStack.push(code);
		}//end case0
		break;
		
		case 1:
		{
			secondToken = codeStack.pop();
			firstToken = codeStack.pop();
			code = firstToken + " + " + secondToken;
			codeStack.push(code);
		}//end case1
		break;
		
		case 3:
		{
			secondToken = codeStack.pop();
			firstToken = codeStack.pop();
			code = firstToken + " * " + secondToken;
			codeStack.push(code);
		}//end case3
		break;
		
		case 5:
		{
			firstToken = codeStack.pop();
			code = "(" + firstToken + ")";
			codeStack.push(code);
		}//end case5
		break;
		
		case 6:
		{
			firstToken = symbolStack.peek().getContent();
            if(!declarations.contains("int " + firstToken + ";"))
            {
				declarations = declarations +
						       "int " + firstToken + ";" + newLine;
				inputDataCode = inputDataCode +
            		          "System.out.print(" + quote + "Introduce "
            		           + firstToken + " " + quote +
            		           ");" + newLine +
						firstToken +
    		                   " = readInt();" + newLine;
		    }//end if
			code = firstToken;
			codeStack.push(code);
		}//end case6
		break;
		
		}//end switch
	}//end translation
	
}//end class
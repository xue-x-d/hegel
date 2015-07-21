package shomop.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

public class TagDemo1 extends TagSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2405480312678130912L;  

	
	@Override
	public int doStartTag() throws JspException {
		// TODO Auto-generated method stub
		 return TagSupport.EVAL_BODY_INCLUDE;//输出标签体内容  
	}
}

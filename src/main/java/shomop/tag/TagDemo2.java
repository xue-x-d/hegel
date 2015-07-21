package shomop.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

@SuppressWarnings("serial")
public class TagDemo2 extends TagSupport{

	@Override
	public int doStartTag() throws JspException {
		// TODO Auto-generated method stub
		return Tag.EVAL_BODY_INCLUDE;
	}
	
	@Override
	public int doEndTag() throws JspException {
		// TODO Auto-generated method stub
		return TagSupport.SKIP_PAGE;// 剩下的jsp不执行
	}
	
}

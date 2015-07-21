package shomop.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/** 
 * 控制标签体重复执行 
 * @author weijiang204321 
 * 
 */  
public class TagDemo3 extends TagSupport{  
  
    private int count = 5;  
      
    @Override  
    public int doStartTag() throws JspException {  
        return TagSupport.EVAL_BODY_INCLUDE; // 默认返回0 不执行标签体
    }  
      
    @Override  
    public int doAfterBody() throws JspException {  
        count--;  
        if(count > 0){  
            return TagSupport.EVAL_BODY_AGAIN; //执行完之后接着执行doAfterBody()方法  
        }else{  
            return TagSupport.SKIP_BODY;  
        }  
    }  
  
    @Override  
    public int doEndTag() throws JspException {  
        return TagSupport.SKIP_BODY;  
    }  
} 
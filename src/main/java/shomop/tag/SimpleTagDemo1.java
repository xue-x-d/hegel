package shomop.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.JspFragment;
import javax.servlet.jsp.tagext.SimpleTagSupport;
  
/** 
 * 控制标签体是否执行 
 * @author weijiang204321 
 * 
 */  
public class SimpleTagDemo1 extends SimpleTagSupport{  
  
    @Override  
    public void doTag() throws JspException, IOException {  
        JspFragment jf = this.getJspBody();  
        //相当于jf.invoke(null);  
        jf.invoke(this.getJspContext().getOut());  
        //这里如果不想输出标签体内容的话，不调用invoke方法即可  
    }  
  
}  
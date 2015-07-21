package shomop.tag;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class ViewIpTag extends TagSupport{  
  
    private static final long serialVersionUID = 1L;  
  
    @Override  
    public int doStartTag() throws JspException {  
		// 内置一个pageContext对象，我们之前说到pageContext对象，它里面是封装了9个隐式对象
        HttpServletRequest request = (HttpServletRequest)this.pageContext.getRequest();  
        JspWriter out = this.pageContext.getOut();  
        String ip = request.getRemoteAddr();  
        try {  
            out.print(ip);  
        } catch (IOException e) {  
            throw new RuntimeException(e);  
        }  
        return super.doStartTag();  
    }  
  
}  
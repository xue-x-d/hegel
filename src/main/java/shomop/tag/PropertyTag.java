package shomop.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.JspFragment;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class PropertyTag extends SimpleTagSupport{  
  
    private int count = 0;  
      
    public void setCount(int count){  
        this.count = count;  
    }  
      
	@Override
	public void doTag() throws JspException, IOException {
		JspFragment jf = this.getJspBody();
		for (int i = 0; i < count; i++) {
			jf.invoke(null);
		}
	} 
  
}  
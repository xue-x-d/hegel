<%@ attribute name="input" required="true" %>  
<%!  
  //定义方法  
  private String encodeHtmlTag(String tag) {  
    if (tag==null)  
      return null;  
    int length = tag.length();  
    StringBuffer encodedTag = new StringBuffer(2 * length);  
    for (int i=0; i<length; i++) {  
      char c = tag.charAt(i);  
      if (c=='<')  
        encodedTag.append("<");  
      else if (c=='>')  
        encodedTag.append(">");  
      else if (c=='&')  
        encodedTag.append("&");  
      else if (c=='"')  
        encodedTag.append("\"");  
      else if (c==' ')  
        encodedTag.append(" ");  
      else  
        encodedTag.append(c);  
  
    }  
    return encodedTag.toString();  
  }  
%>  
<%=encodeHtmlTag(input)%><!--根据传入的参数input调用方法encodeHtmlTag  -->
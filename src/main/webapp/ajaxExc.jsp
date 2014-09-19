<%@ page language="java" contentType="text/html; charset=UTF-8"  
    pageEncoding="UTF-8"%>  
<%@ include file="/WEB-INF/jstl/taglib.jsp" %>
<%@ include file="/WEB-INF/jstl/path.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">  
<html>  
 <head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">  
  <title>spring login information</title>
  <!--<script src="//code.jquery.com/jquery-1.11.0.min.js"></script>-->
  <script src="./js/jquery-1.11.1.js"></script>
  <script type="text/javascript">  
    function ajaxTest()  
    {  
        $.ajax( {  
            type : 'GET',  
            contentType : 'application/json',     
            url : '<%=basePath%>/ajaxExc.do',     
            async: false,//禁止ajax的异步操作，使之顺序执行。  
            dataType : 'json',  
            success : function(data,textStatus){  
                alert(JSON.stringify(data));  
            },  
            error : function(data,textstatus){  
                alert(data.responseText);  
            }  
        });  
    }  
  </script>  
 </head>  
 <body>
   <%=basePath%> 
    <table cellpadding="0" cellspacing="0" style="width:100%;">  
      <tr>  
          <td rowspan="2" style="width:30px;">  
          </td>  
          <td style="height:72px;">  
              <div>  
                  spring login front information  
              </div>  
          </td>  
           <td style="height:72px;">  
              <div>  
                <input type=button value="Ajax Exception Test" onclick="ajaxTest();"></input>  
             </div>  
          </td>  
      </tr>  
    </table>  
 </body>  
</html>  
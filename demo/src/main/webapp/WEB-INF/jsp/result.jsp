<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
		<title>Lucene Search</title>
    </head>
    <body>
        <h3>Search Result for : ${search.content}</h3>
             <table>
<tbody>

</tbody>
		<c:forEach var = "s" items="${search.fierceNews}">
		    <tr>
               <td><h3>${s.title}</h3></td>
             </tr> 
			<tr>
				<td>URL: <a href="${s.urlLink}"/>${s.urlLink}</td>
			</tr>
			<tr>
				<td>Published Date: ${s.datePublished}</td>
			</tr>
		</c:forEach>
                <tr>
                    <a href="/search">Submit another query</a>
                </tr>
            </table>
    </body>
</html>
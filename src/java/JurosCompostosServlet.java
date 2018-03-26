import static java.awt.Color.red;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "JurosCompostosServlet", urlPatterns = {"/index.html"})
public class JurosCompostosServlet extends HttpServlet {

     @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        Double valor = 5000.0;
        Double valorUsuario = 0.0;
        int qtddMeses;
        double porcentJuros;
        try (PrintWriter out = resp.getWriter()) {
            if (req.getParameter("qtddMeses") != null && !"".equals(req.getParameter("qtddMeses")))
            {
                qtddMeses = Integer.parseInt(req.getParameter("qtddMeses"));
            }
            else 
            {
                qtddMeses = 12;
            }
            if (req.getParameter("porcentJuros") != null && !"".equals(req.getParameter("porcentJuros"))) 
            {
                porcentJuros = Double.parseDouble(req.getParameter("porcentJuros")) / 100;
            }
            else 
            {
                porcentJuros = 0.010;
            }
            if(req.getParameter("valorUsuario") != null && !"".equals(req.getParameter("valorUsuario")))
            {
                valorUsuario = Double.parseDouble(req.getParameter("valorUsuario"));
            }
            else
            {
                valor = 5000.0;
            }
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title> Disposição dos Juros </title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<p> Simulação dos juros </p>");
            if (valorUsuario != 0.0)
            {
                out.println("<p> Para um investimento inicial de R$" + valorUsuario + " a uma taxa de juros compostos de " + (porcentJuros*100) + "% ao mês,"
                        + " você terá R$" + calcularValorFinal(valorUsuario, porcentJuros, qtddMeses) +  " ao final de " + qtddMeses + " meses! </p>");
                if (((calcularValorFinal(valorUsuario, porcentJuros, qtddMeses) - valorUsuario)/qtddMeses) > 200)
                {
                    out.println("<p><FONT COLOR="+"green"+">Bom negócio!</FONT></p>");
                }
                else
                {
                    out.println("<p><FONT COLOR="+"red"+">Mau negócio!</FONT></p>");
                }
            }
            else
            {
                out.println("<p> Para um investimento inicial de R$" + valor + " a uma taxa de juros compostos de " + (porcentJuros*100) + "% ao mês,"
                        + " você terá R$" + calcularValorFinal(valor, 0.010, 12) +  " ao final de " + qtddMeses + " meses! </p>");
                out.println("<p><FONT COLOR="+"red"+">Mau negócio!</FONT></p>");
            }
            out.println("<form>");
            out.println("<label>Juros pretendido: </label><input type=\"text\" name=\"porcentJuros\"><br>");
            out.println("<label>Meses pretendido: </label><input type=\"text\" name=\"qtddMeses\"><br>");
            out.println("<label>Valor pretendido: </label><input type=\"text\" name=\"valorUsuario\"><br>");
            out.println("<button type=\"submit\">Submeter</button>");
            out.println("</form>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    private Double calcularValorFinal(Double valor, Double juros, Integer meses) {
       return valor * Math.pow((1 + juros),meses);
    }
}

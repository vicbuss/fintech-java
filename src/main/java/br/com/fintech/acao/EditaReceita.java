package br.com.fintech.acao;

import java.io.IOException;
import java.time.LocalDateTime;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.com.fintech.dao.RegistroDAO;
import br.com.fintech.entities.Registro;
import br.com.fintech.entities.Tipo;
import br.com.fintech.entities.Usuario;
import br.com.fintech.utils.DateParser;

public class EditaReceita implements Acao {

	@Override
	public String executa(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		HttpSession sessao = request.getSession(); 
		
		Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");
		
		Integer codigo = Integer.valueOf(request.getParameter("id"));
		
		Registro receita = new Registro();
		receita.setCodigo(codigo);
		
		receita.setUsuario(usuario);
		
		Tipo tipo = new Tipo();
		tipo.setCodigo(1);
		
		receita.setTipo(tipo);
		
		LocalDateTime dataRegistro = DateParser.StringToLocalDateTime(request.getParameter("dataRegistro"), "yyyy-MM-dd");
		receita.setDataRegistro(dataRegistro);
		
		Double valor = Double.valueOf(request.getParameter("valor"));
		receita.setValor(valor);
		
		String categoria = request.getParameter("categoria");
		receita.setCategoria(categoria);
		
		String descricao = request.getParameter("descricao");
		receita.setDescricao(descricao);
		
		
		RegistroDAO dao = new RegistroDAO();
		dao.update(receita);
		
		return "redirect:entrada?acao=ListaReceitas";
		
	}

}

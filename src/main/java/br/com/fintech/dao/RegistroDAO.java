package br.com.fintech.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.fintech.data.OracleDBConnection;
import br.com.fintech.entities.Registro;
import br.com.fintech.entities.Tipo;
import br.com.fintech.entities.Usuario;
import br.com.fintech.utils.DateParser;

public class RegistroDAO {
	private Connection connection;
	
	private Registro handleQuery(ResultSet res) throws SQLException {
		Usuario usuario = new Usuario();
		usuario.setCodigo(res.getInt("cd_usuario"));
		
		Tipo tipo = new Tipo();
		tipo.setCodigo(res.getInt("cd_tipo"));
		
		Registro registro = new Registro();
		registro.setCodigo(res.getInt("cd_registro"));
		registro.setUsuario(usuario);
		registro.setValor(res.getDouble("vl_registro"));
		registro.setDataRegistro(DateParser.SQLToLocalDateTime(res.getDate("dt_registro")));
		registro.setTipo(tipo);
		registro.setCategoria(res.getString("ds_categoria"));
		registro.setDescricao(res.getString("ds_registro"));
		
		return registro;
	}
	
	public void insert(Registro registro) {
		PreparedStatement stmt = null;
		Date dataRegistro = DateParser.localDateTimeToSQL(registro.getDataRegistro());

		try {
			connection = OracleDBConnection.getInstance().getConnection();
			
			String sql = "INSERT INTO T_FINTECH_REGISTRO "
					+ "(cd_registro, cd_usuario, vl_registro, dt_registro, "
					+ "cd_tipo, ds_categoria, ds_registro) "
					+ "VALUES (SQ_REGISTRO.nextval, ?, ?, ?, ?, ?, ?)";

			stmt = connection.prepareStatement(sql);

			stmt.setInt(1, registro.getUsuario().getCodigo());
			stmt.setDouble(2, registro.getValor());
			stmt.setDate(3, dataRegistro);
			stmt.setInt(4, registro.getTipo().getCodigo());
			stmt.setString(5, registro.getCategoria());
			stmt.setString(6, registro.getDescricao());
			
			stmt.executeUpdate();
			System.out.println("Registro incluído no bd");

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(stmt != null) {
					stmt.close();
				}
				if(connection != null) {
					connection.close();				
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}


	public List<Registro> listAllFromUser(int codigoUsuario) {
		List<Registro> list = new ArrayList<Registro>();
		PreparedStatement stmt = null;
		ResultSet res = null;

		try {
			connection = OracleDBConnection.getInstance().getConnection();
			stmt = connection.prepareStatement("SELECT * FROM T_FINTECH_REGISTRO "
					+ "WHERE cd_usuario = ? ORDER BY dt_registro DESC");
			
			stmt.setInt(1, codigoUsuario);
			
			res = stmt.executeQuery();

			while(res.next()) {
				
				Registro registro = this.handleQuery(res);

				list.add(registro);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(stmt != null) {
					stmt.close();
				}
				if(connection != null) {
					connection.close();				
				}
				if(res != null) {
					res.close();					
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	public Registro getById(int codigoRegistro, int codigoUsuario) {
		PreparedStatement stmt = null;
		ResultSet res = null;
		Registro registro = null;

		try {
			connection = OracleDBConnection.getInstance().getConnection();
			stmt = connection.prepareStatement("SELECT cd_registro, cd_usuario, vl_registro, "
					+ "dt_registro, cd_tipo, ds_categoria, ds_registro "
					+ "FROM T_FINTECH_REGISTRO "
					+ "WHERE cd_usuario = ?"  
					+ " AND cd_registro = ?");
			
			stmt.setInt(1, codigoUsuario);
			stmt.setInt(2, codigoRegistro);
			
			res = stmt.executeQuery();

			if(res.next()) {
				registro = this.handleQuery(res);
			} 

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(stmt != null) {
					stmt.close();
				}
				if(connection != null) {
					connection.close();				
				}
				if(res != null) {
					res.close();					
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return registro;
	}

	public void update(Registro registro) {
		PreparedStatement stmt = null;
		Date dataRegistro = DateParser.localDateTimeToSQL(registro.getDataRegistro());

		try {
			connection = OracleDBConnection.getInstance().getConnection();
			String sql = "UPDATE T_FINTECH_REGISTRO "
					+ "SET "
					+ "vl_registro = ?, "
					+ "dt_registro = ?, "
					+ "ds_categoria = ?, "
					+ "ds_registro = ? "
					+ "WHERE cd_registro = ?";

			stmt = connection.prepareStatement(sql);


			stmt.setDouble(1, registro.getValor());
			stmt.setDate(2, dataRegistro);
			stmt.setString(3, registro.getCategoria());
			stmt.setString(4, registro.getDescricao());
			stmt.setInt(5, registro.getCodigo());
			
			stmt.executeUpdate();
			System.out.println("Registro atualizado");

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(stmt != null) {
					stmt.close();
				}
				if(connection != null) {
					connection.close();				
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void delete (int codigoRegistro, int codigoUsuario) {
		PreparedStatement stmt = null;
		try {
			connection = OracleDBConnection.getInstance().getConnection();
			String sql = "DELETE FROM T_FINTECH_REGISTRO WHERE cd_registro = ?"  
					+ " AND cd_usuario = ?";
			stmt = connection.prepareStatement(sql);
			
			stmt.setInt(1, codigoRegistro);
			stmt.setInt(2, codigoUsuario);
			
			stmt.executeUpdate();
			System.out.println("Registro deletado");

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(stmt != null) {
					stmt.close();
				}
				if(connection != null) {
					connection.close();				
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}  
}

